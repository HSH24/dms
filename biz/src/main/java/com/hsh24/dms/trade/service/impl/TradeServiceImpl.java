package com.hsh24.dms.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.dms.api.bankAcct.IBankAcctService;
import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.cart.ICartService;
import com.hsh24.dms.api.cart.bo.Cart;
import com.hsh24.dms.api.cashflow.ICashflowService;
import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.api.pay.IPayService;
import com.hsh24.dms.api.supplier.ISupplierService;
import com.hsh24.dms.api.supplier.bo.Supplier;
import com.hsh24.dms.api.trade.IOrderRefundService;
import com.hsh24.dms.api.trade.IOrderService;
import com.hsh24.dms.api.trade.ITradeLogService;
import com.hsh24.dms.api.trade.ITradeService;
import com.hsh24.dms.api.trade.bo.Order;
import com.hsh24.dms.api.trade.bo.OrderRefund;
import com.hsh24.dms.api.trade.bo.Trade;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.DateUtil;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.framework.util.UUIDUtil;
import com.hsh24.dms.trade.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class TradeServiceImpl implements ITradeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IOrderService orderService;

	@Resource
	private IOrderRefundService orderRefundService;

	@Resource
	private ICartService cartService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private IItemService itemService;

	@Resource
	private IBankAcctService bankAcctService;

	@Resource
	private ICashflowService cashflowService;

	@Resource
	private ITradeLogService tradeLogService;

	@Resource
	private ITradeDao tradeDao;

	@Override
	public BooleanResult createTrade(final Long shopId, final String itemId, final String skuId, final String quantity,
		final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(itemId)) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(skuId)) {
			result.setCode("商品SKU信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(quantity)) {
			result.setCode("数量信息不能为空");
			return result;
		}
		try {
			Integer.valueOf(quantity);
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("数量信息不正确");
			return result;
		}

		try {
			result = itemService.validate(Long.valueOf(itemId), Long.valueOf(skuId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("商品，SKU信息不存在");
			return result;
		}

		if (!result.getResult()) {
			return result;
		}

		String code[] = result.getCode().split("&");
		final Long supId = Long.valueOf(code[0]);
		final BigDecimal price = new BigDecimal(code[1]).multiply(new BigDecimal(quantity));

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		final BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
		if (bankAcct == null) {
			result.setCode("资金账户信息不能为空");
			return result;
		}

		if (price.compareTo(bankAcct.getCurBal()) == 1) {
			result.setCode("资金账户余额不足");
			return result;
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				// 1. 创建交易
				// create trade
				Long tradeId = null;

				Trade trade = new Trade();
				trade.setShopId(shopId);
				trade.setSupId(supId);
				// 交易价格
				trade.setTradePrice(price);
				trade.setChange(BigDecimal.ZERO);
				// 亭主下单
				trade.setType(ITradeService.TO_SEND);
				// 14位日期 ＋ 11位随机数
				trade.setTradeNo("PO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(11));
				// 支付方式
				trade.setPayType(IPayService.PAY_TYPE_AP);
				trade.setModifyUser(modifyUser);

				try {
					tradeDao.createTrade(trade);
					tradeId = trade.getTradeId();
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(trade), e);
					ts.setRollbackOnly();

					result.setCode("创建采购单失败");
					return result;
				}

				// 2. 创建订单
				result = orderService.createOrder(supId, tradeId, itemId, skuId, quantity, modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 3. 记录现金流水账
				result =
					createCashflow(shopId, bankAcct.getBankAcctId(), trade.getPrice(), trade.getTradeNo(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 4. 记录资金账户余额
				result = bankAcctService.updateBankAcct(shopId, bankAcct.getBankAcctId(), price.negate(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 5. 记录交易日志
				result = tradeLogService.createTradeLog(tradeId, trade.getType(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				result.setCode(trade.getTradeNo());
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult createTrade(final Long shopId, final Long userId, final String[] cartId,
		final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车不能为空");
			return result;
		}

		// 获取选中商品明细
		List<Cart> cartList = cartService.getCartList(userId, shopId, cartId);

		if (cartList == null || cartList.size() == 0) {
			result.setCode("购物车信息不存在");
			return result;
		}

		// 遍历 根据 supId 统计金额 -> 拆分订单
		final Map<Long, List<Long>> map1 = new HashMap<Long, List<Long>>();
		final Map<Long, BigDecimal> map2 = new HashMap<Long, BigDecimal>();
		// 统计 总金额 用于 判断 是否 大于 账户余额
		BigDecimal price = BigDecimal.ZERO;

		for (Cart cart : cartList) {
			Long cratId = cart.getCartId();
			Long supId = cart.getSupId();
			BigDecimal p = cart.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));

			if (map1.containsKey(supId)) {
				map1.get(supId).add(cratId);

				map2.put(supId, map2.get(supId).add(p));
			} else {
				List<Long> list = new ArrayList<Long>();
				list.add(cratId);
				map1.put(supId, list);

				map2.put(supId, p);
			}

			price = price.add(p);
		}

		if (map1.size() == 0) {
			result.setCode("购物车商品总价为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		final BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
		if (bankAcct == null) {
			result.setCode("资金账户信息不能为空");
			return result;
		}

		if (price.compareTo(bankAcct.getCurBal()) == 1) {
			result.setCode("资金账户余额不足");
			return result;
		}

		final BigDecimal amount = price;

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				StringBuilder tradeNo = new StringBuilder();

				for (Map.Entry<Long, List<Long>> m : map1.entrySet()) {
					Long supId = m.getKey();
					List<Long> list = m.getValue();
					String[] cartId = new String[list.size()];
					int i = 0;
					for (Long cid : list) {
						cartId[i++] = cid.toString();
					}

					// 1. 创建交易
					// create trade
					Long tradeId = null;

					Trade trade = new Trade();
					trade.setShopId(shopId);
					trade.setSupId(supId);
					// 交易价格
					trade.setTradePrice(map2.get(supId));
					trade.setChange(BigDecimal.ZERO);
					// 亭主下单
					trade.setType(ITradeService.TO_SEND);
					// 14位日期 ＋ 11位随机数
					trade.setTradeNo("PO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(11));
					// 支付方式
					trade.setPayType(IPayService.PAY_TYPE_AP);
					trade.setModifyUser(modifyUser);

					// 交易订单 关联 购物车
					StringBuilder sb = new StringBuilder();
					for (String id : cartId) {
						if (sb.length() > 0) {
							sb.append(",");
						}
						sb.append(id);
					}
					trade.setCartId(sb.toString());

					try {
						tradeDao.createTrade(trade);
						tradeId = trade.getTradeId();
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(trade), e);
						ts.setRollbackOnly();

						result.setCode("创建采购单失败");
						return result;
					}

					// 2. 创建订单
					result = orderService.createOrder(supId, tradeId, cartId, shopId.toString());
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}

					// 3. 记录现金流水账
					result =
						createCashflow(shopId, bankAcct.getBankAcctId(), trade.getPrice(), trade.getTradeNo(),
							modifyUser);
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}

					// 4. 记录交易日志
					result = tradeLogService.createTradeLog(tradeId, trade.getType(), modifyUser);
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}

					if (tradeNo.length() > 0) {
						tradeNo.append(",");
					}
					tradeNo.append(trade.getTradeNo());
				}

				// 5. 记录资金账户余额
				result = bankAcctService.updateBankAcct(shopId, bankAcct.getBankAcctId(), amount.negate(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 6. 修改购物车状态
				if (cartId != null && cartId.length > 0) {
					result = cartService.finishCart(userId, shopId, cartId);
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}
				}

				result.setCode(tradeNo.toString());
				return result;
			}
		});

		return res;
	}

	/**
	 * 
	 * @param shopId
	 * @param bankAcctId
	 * @param price
	 * @param tradeDate
	 * @param tradeNo
	 * @param modifyUser
	 * @return
	 */
	private BooleanResult createCashflow(Long shopId, Long bankAcctId, BigDecimal price, String tradeNo,
		String modifyUser) {
		Cashflow cashflow = new Cashflow();
		cashflow.setCashflowCId(2L);
		cashflow.setBankAcctId(bankAcctId);
		cashflow.setSummary("[支出] 商品采购,业务交易号:" + tradeNo);
		cashflow.setCrAmount(price);
		cashflow.setDrAmount(BigDecimal.ZERO);
		cashflow.setTradeDate(DateUtil.getNowDatetimeStr());
		cashflow.setTradeNo(tradeNo);

		return cashflowService.createCashflow(shopId, cashflow, modifyUser);
	}

	@Override
	public BigDecimal getTradePrice(Long shopId, String[] type) {
		if (shopId == null) {
			return BigDecimal.ZERO;
		}

		Trade trade = new Trade();
		trade.setShopId(shopId);
		trade.setCodes(type);

		try {
			return tradeDao.getTradePrice(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return BigDecimal.ZERO;
	}

	@Override
	public int getTradeCount(Long shopId, String[] type) {
		if (shopId == null) {
			return 0;
		}

		Trade trade = new Trade();
		trade.setShopId(shopId);
		trade.setCodes(type);

		return getTradeCount(trade);
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private int getTradeCount(Trade trade) {
		try {
			return tradeDao.getTradeCount(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return 0;
	}

	@Override
	public List<Trade> getTradeList(Long shopId, String[] type) {
		if (shopId == null) {
			return null;
		}

		Trade t = new Trade();
		t.setShopId(shopId);
		t.setCodes(type);

		// 暂不分页
		t.setLimit(99);
		t.setOffset(0);
		t.setSort("CREATE_DATE");
		t.setOrder("DESC");

		List<Trade> tradeList = getTradeList(t);

		if (tradeList == null || tradeList.size() == 0) {
			return null;
		}

		String[] supId = new String[tradeList.size()];
		int i = 0;

		for (Trade trade : tradeList) {
			supId[i++] = trade.getSupId().toString();
		}

		// 获取供应商信息
		Map<Long, Supplier> map = supplierService.getSupplier(supId);

		for (Trade trade : tradeList) {
			Supplier supplier = map.get(trade.getSupId());
			if (supplier != null) {
				trade.setSupName(supplier.getSupName());
			}

			trade.setOrderList(orderService.getOrderList(shopId, trade.getTradeId()));
		}

		return tradeList;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private List<Trade> getTradeList(Trade trade) {
		try {
			return tradeDao.getTradeList(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	@Override
	public Trade getTrade(Long shopId, String tradeNo) {
		if (shopId == null || StringUtils.isBlank(tradeNo)) {
			return null;
		}

		Trade t = new Trade();
		t.setShopId(shopId);
		t.setTradeNo(tradeNo.trim());

		Trade trade = getTrade(t);

		if (trade == null) {
			return null;
		}

		Supplier supplier = supplierService.getSupplier(trade.getSupId());

		if (supplier != null) {
			trade.setSupName(supplier.getSupName());
		}

		List<Order> orderList = orderService.getOrderList(shopId, trade.getTradeId());

		if (orderList != null && orderList.size() > 0) {
			trade.setOrderList(orderList);
		}

		return trade;
	}

	@Override
	public BooleanResult cancelTrade(Long shopId, String tradeNo, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		final Trade trade = new Trade();

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}
		trade.setShopId(shopId);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("采购单信息不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}
		trade.setModifyUser(modifyUser);

		// 锁定当前订单
		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + tradeNo.trim(), tradeNo,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			result.setCode("当前采购单已被锁定，请稍后再试");
			return result;
		}

		// 0. 查询 未付款交易订单
		Trade t = getTrade(trade);
		if (t == null) {
			result.setCode("当前采购单不存在");
			return result;
		}

		if (!ITradeService.TO_SEND.equals(t.getType())) {
			result.setCode("当前采购单已发货或取消");
			return result;
		}

		// 处理 订单明细数据 需要用到
		trade.setTradeId(t.getTradeId());
		trade.setTradePrice(t.getPrice());

		BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
		if (bankAcct == null) {
			result.setCode("资金账户信息不能为空");
			return result;
		}

		return cancelTrade(shopId, trade, bankAcct.getBankAcctId(), modifyUser);
	}

	@Override
	public Trade getOrder(Long shopId, String tradeNo, String orderId) {
		if (shopId == null || StringUtils.isBlank(tradeNo) || StringUtils.isBlank(orderId)) {
			return null;
		}

		Long id = null;

		try {
			id = Long.valueOf(orderId);
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		Trade t = new Trade();
		t.setShopId(shopId);
		t.setTradeNo(tradeNo.trim());

		Trade trade = getTrade(t);

		if (trade == null) {
			return null;
		}

		Order order = orderService.getOrder(shopId, trade.getTradeId(), id);

		if (order != null) {
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			trade.setOrderList(orderList);
		}

		return trade;
	}

	@Override
	public BooleanResult createOrderRefund(Long shopId, String tradeNo, String refundNo, Long orderId,
		OrderRefund orderRefund, String modifyUser) {
		return orderRefundService.createOrderRefund(shopId, tradeNo, refundNo, orderId, orderRefund, modifyUser);
	}

	@Override
	public BooleanResult signTrade(Long shopId, String tradeNo, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();
		// 签收
		trade.setType(ITradeService.SIGN);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}
		trade.setShopId(shopId);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("采购单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		trade.setModifyUser(shopId.toString());

		result = updateTrade(trade);

		if (result.getResult()) {
			result.setCode("操作成功");
		}
		return result;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private Trade getTrade(Trade trade) {
		try {
			return tradeDao.getTrade(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	private BooleanResult updateTrade(Trade trade) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		try {
			int c = tradeDao.updateTrade(trade);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新交易失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
			result.setCode("更新交易表失败");
		}

		return result;
	}

	/**
	 * 
	 * @param trade
	 * @param type
	 *            用来 判断 是否 需要退还资金.
	 * @param modifyUser
	 * @return
	 */
	private BooleanResult cancelTrade(final Long shopId, final Trade trade, final Long bankAcctId,
		final String modifyUser) {
		trade.setType(ITradeService.TO_CANCEL);

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				// 1. 订单状态取消
				BooleanResult result = updateTrade(trade);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 2. 记录现金流水账
				Cashflow cashflow = new Cashflow();
				cashflow.setCashflowCId(3L);
				cashflow.setBankAcctId(bankAcctId);
				cashflow.setSummary("[退款] 采购订单:" + trade.getTradeNo() + "取消");
				cashflow.setCrAmount(BigDecimal.ZERO);
				cashflow.setDrAmount(trade.getPrice());
				cashflow.setTradeDate(DateUtil.getNowDatetimeStr());
				cashflow.setTradeNo(trade.getTradeNo());

				// TODO
				// result = cashflowService.createCashflow(shopId, cashflow, modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 3. 记录资金账户余额
				// result = bankAcctService.updateBankAcct(shopId, bankAcctId,
				// trade.getPrice(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 4. 记录交易日志
				result = tradeLogService.createTradeLog(trade.getTradeId(), trade.getType(), modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				return result;
			}
		});

		if (res.getResult()) {
			res.setCode("取消采购单成功");
		}
		return res;
	}

}
