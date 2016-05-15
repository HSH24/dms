package com.hsh24.dms.receipt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.Receipt;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.api.trade.IOrderService;
import com.hsh24.dms.api.trade.ITradeService;
import com.hsh24.dms.api.trade.bo.Order;
import com.hsh24.dms.api.trade.bo.Trade;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.DateUtil;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.framework.util.UUIDUtil;
import com.hsh24.dms.receipt.dao.IReceiptDao;
import com.hsh24.dms.receipt.dao.IReceiptDetailDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptServiceImpl implements IReceiptService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptServiceImpl.class);

	private TransactionTemplate transactionTemplate;

	private ITradeService tradeService;

	private IOrderService orderService;

	private IReceiptDao receiptDao;

	private IReceiptDetailDao receiptDetailDao;

	@Override
	public BooleanResult part(Long shopId, String tradeNo, final List<ReceiptDetail> receiptDetailList,
		final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空。");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空。");
			return result;
		}

		if (receiptDetailList == null || receiptDetailList.size() == 0) {
			result.setCode("收货明细信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		// 判断订单状态
		Trade trade = tradeService.getTrade(shopId, tradeNo);

		if (trade == null) {
			result.setCode("当前订单不存在。");
			return result;
		}

		if (!ITradeService.SEND.equals(trade.getType())) {
			result.setCode("当前订单未发货或已完成收货。");
			return result;
		}

		final Receipt receipt = new Receipt();
		receipt.setReceiptNo("RO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(11));
		receipt.setShopId(shopId);
		receipt.setTradeId(trade.getTradeId());
		receipt.setType("P");
		receipt.setModifyUser(modifyUser);

		// 验证 收货数量 不能 超过 可收货数量

		// 订单信息
		List<Order> orderList = trade.getOrderList();

		if (orderList == null || orderList.size() == 0) {
			result.setCode("订单明细信息不存在。");
			return result;
		}

		// 订单
		Map<Long, Integer> orderMap = new HashMap<Long, Integer>();

		for (Order order : orderList) {
			orderMap.put(order.getOrderId(), order.getQuantity());
		}

		// 已收货
		Map<Long, Integer> receiptedMap = new HashMap<Long, Integer>();

		// 已收货信息
		List<Receipt> receiptList = getReceiptList(shopId, receipt.getTradeId());

		if (receiptList != null && receiptList.size() > 0) {
			for (Receipt rec : receiptList) {
				List<ReceiptDetail> receiptDetails = rec.getReceiptDetailList();
				if (receiptDetails == null || receiptDetails.size() == 0) {
					continue;
				}

				for (ReceiptDetail receiptDetail : receiptDetails) {
					Long orderId = receiptDetail.getOrderId();
					int quantity = receiptDetail.getQuantity();
					if (receiptedMap.containsKey(orderId)) {
						receiptedMap.put(orderId, receiptedMap.get(orderId) + quantity);
					} else {
						receiptedMap.put(orderId, quantity);
					}
				}
			}
		}

		for (ReceiptDetail receiptDetail : receiptDetailList) {
			Long orderId = receiptDetail.getOrderId();
			int quantity = receiptDetail.getQuantity();

			if (quantity <= 0) {
				result.setCode("[订单明细]收货数量不能小于或等于0。");
				return result;
			}

			if (receiptedMap.containsKey(orderId)) {
				if (quantity > (orderMap.get(orderId) - receiptedMap.get(orderId))) {
					result.setCode("[订单明细]收货数量不能大于可收货数量。");
					return result;
				}
			} else {
				if (quantity > (orderMap.get(orderId))) {
					result.setCode("[订单明细]收货数量不能大于可收货数量。");
					return result;
				}
			}
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long receiptId = null;

				try {
					receiptId = receiptDao.createReceipt(receipt);
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(receipt), e);
					ts.setRollbackOnly();

					result.setCode("创建收货失败。");
					return result;
				}

				// 2. 创建收货明细
				try {
					receiptDetailDao.createReceiptDetail(receiptId, receiptDetailList, modifyUser);
				} catch (Exception e) {
					logger.error("receiptId:" + receiptId + LogUtil.parserBean(receiptDetailList), e);
					ts.setRollbackOnly();

					result.setCode("创建收货明细失败。");
					return result;
				}

				result.setCode(receipt.getReceiptNo());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult all(final Long shopId, final String tradeNo, final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空。");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		// 判断订单状态
		Trade trade = tradeService.getTrade(shopId, tradeNo);

		if (trade == null) {
			result.setCode("当前订单不存在。");
			return result;
		}

		if (!ITradeService.SEND.equals(trade.getType())) {
			result.setCode("当前订单未发货或已完成收货。");
			return result;
		}

		final Receipt receipt = new Receipt();
		receipt.setReceiptNo("RO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(11));
		receipt.setShopId(shopId);
		receipt.setTradeId(trade.getTradeId());
		receipt.setType("A");
		receipt.setModifyUser(modifyUser);

		// 订单信息
		List<Order> orderList = trade.getOrderList();

		if (orderList == null || orderList.size() == 0) {
			result.setCode("订单明细信息不存在。");
			return result;
		}

		// 已收货
		Map<Long, Integer> map = new HashMap<Long, Integer>();

		// 已收货信息
		List<Receipt> receiptList = getReceiptList(shopId, receipt.getTradeId());

		if (receiptList != null && receiptList.size() > 0) {
			for (Receipt rec : receiptList) {
				List<ReceiptDetail> receiptDetails = rec.getReceiptDetailList();
				if (receiptDetails == null || receiptDetails.size() == 0) {
					continue;
				}

				for (ReceiptDetail receiptDetail : receiptDetails) {
					Long orderId = receiptDetail.getOrderId();
					int quantity = receiptDetail.getQuantity();
					if (map.containsKey(orderId)) {
						map.put(orderId, map.get(orderId) + quantity);
					} else {
						map.put(orderId, quantity);
					}
				}
			}
		}

		final List<ReceiptDetail> receiptDetailList = new ArrayList<ReceiptDetail>();

		for (Order order : orderList) {
			Long orderId = order.getOrderId();
			if (order.getQuantity() == map.get(orderId)) {
				continue;
			}

			ReceiptDetail receiptDetail = new ReceiptDetail();
			receiptDetail.setOrderId(orderId);
			receiptDetail.setQuantity(order.getQuantity() - map.get(orderId));

			receiptDetailList.add(receiptDetail);
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				if (receiptDetailList != null && receiptDetailList.size() > 0) {
					Long receiptId = null;

					try {
						receiptId = receiptDao.createReceipt(receipt);
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(receipt), e);
						ts.setRollbackOnly();

						result.setCode("创建收货失败。");
						return result;
					}

					// 2. 创建收货明细
					try {
						receiptDetailDao.createReceiptDetail(receiptId, receiptDetailList, modifyUser);
					} catch (Exception e) {
						logger.error("receiptId:" + receiptId + LogUtil.parserBean(receiptDetailList), e);
						ts.setRollbackOnly();

						result.setCode("创建收货明细失败。");
						return result;
					}
				}

				// 3. 订单总单 状态 修改
				result = tradeService.signTrade(shopId, tradeNo, modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				result.setCode(receipt.getReceiptNo());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public List<Receipt> getReceiptList(Long shopId, Long tradeId) {
		if (shopId == null || tradeId == null) {
			return null;
		}

		Receipt receipt = new Receipt();
		receipt.setShopId(shopId);
		receipt.setTradeId(tradeId);

		List<Receipt> receiptList = getReceiptList(receipt);

		if (receiptList == null || receiptList.size() == 0) {
			return null;
		}

		// 获取订单明细信息
		List<Order> orderList = orderService.getOrderList(shopId, tradeId);

		if (orderList == null || orderList.size() == 0) {
			return null;
		}

		Map<Long, Order> map = new HashMap<Long, Order>();
		for (Order order : orderList) {
			map.put(order.getOrderId(), order);
		}

		for (Receipt reciept : receiptList) {
			reciept.setReceiptDetailList(getReceiptDetailList(reciept.getReceiptId(), map));
		}

		return receiptList;
	}

	private List<Receipt> getReceiptList(Receipt receipt) {
		try {
			return receiptDao.getReceiptList(receipt);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receipt), e);
		}

		return null;
	}

	/**
	 * 收货明细，包含商品信息.
	 * 
	 * @param receiptId
	 * @param map
	 * @return
	 */
	private List<ReceiptDetail> getReceiptDetailList(Long receiptId, Map<Long, Order> map) {
		ReceiptDetail receiptDetail = new ReceiptDetail();
		receiptDetail.setReceiptId(receiptId);

		List<ReceiptDetail> receiptDetailList = getReceiptDetailList(receiptDetail);

		if (receiptDetailList == null || receiptDetailList.size() == 0) {
			return null;
		}

		for (ReceiptDetail detail : receiptDetailList) {
			Order order = map.get(detail.getOrderId());
			detail.setItemName(order.getItemName());
			detail.setPropertiesName(order.getPropertiesName());
		}

		return receiptDetailList;
	}

	private List<ReceiptDetail> getReceiptDetailList(ReceiptDetail receiptDetail) {
		try {
			return receiptDetailDao.getReceiptDetailList(receiptDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receiptDetail), e);
		}

		return null;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public ITradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public IReceiptDao getReceiptDao() {
		return receiptDao;
	}

	public void setReceiptDao(IReceiptDao receiptDao) {
		this.receiptDao = receiptDao;
	}

	public IReceiptDetailDao getReceiptDetailDao() {
		return receiptDetailDao;
	}

	public void setReceiptDetailDao(IReceiptDetailDao receiptDetailDao) {
		this.receiptDetailDao = receiptDetailDao;
	}

}
