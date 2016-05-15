package com.hsh24.dms.receipt.service.impl;

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
import com.hsh24.dms.api.trade.bo.Order;
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

	private IOrderService orderService;

	private IReceiptDao receiptDao;

	private IReceiptDetailDao receiptDetailDao;

	@Override
	public BooleanResult part(Long shopId, String tradeId, final List<ReceiptDetail> receiptDetailList,
		final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空。");
			return result;
		}

		if (StringUtils.isBlank(tradeId)) {
			result.setCode("订单信息不能为空。");
			return result;
		}

		if (receiptDetailList == null || receiptDetailList.size() == 0) {
			result.setCode("收货信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		final Receipt receipt = new Receipt();
		receipt.setReceiptNo("RO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(11));
		receipt.setShopId(shopId);
		receipt.setTradeId(Long.valueOf(tradeId));
		receipt.setType("P");
		receipt.setModifyUser(modifyUser);

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
	public BooleanResult all(Long shop, String tradeId, String modifyUser) {
		// TODO Auto-generated method stub
		return null;
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

		for (Receipt reciept : receiptList) {
			reciept.setReceiptDetailList(getReceiptDetailList(reciept.getReceiptId(), shopId, tradeId));
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
	 * @return
	 */
	private List<ReceiptDetail> getReceiptDetailList(Long receiptId, Long shopId, Long tradeId) {
		ReceiptDetail receiptDetail = new ReceiptDetail();
		receiptDetail.setReceiptId(receiptId);

		List<ReceiptDetail> receiptDetailList = getReceiptDetailList(receiptDetail);

		if (receiptDetailList == null || receiptDetailList.size() == 0) {
			return null;
		}

		List<Order> orderList = orderService.getOrderList(shopId, tradeId);

		if (orderList == null || orderList.size() == 0) {
			return null;
		}

		Map<Long, Order> map = new HashMap<Long, Order>();
		for (Order order : orderList) {
			map.put(order.getOrderId(), order);
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
