package com.hsh24.dms.receipt.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.Receipt;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
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

	private IReceiptDao receiptDao;

	private IReceiptDetailDao receiptDetailDao;

	@Override
	public BooleanResult part(final Long userId, final String tradeId, final List<ReceiptDetail> receiptDetailList) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空。");
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

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long receiptId = null;

				Receipt receipt = new Receipt();
				receipt.setReceiptNo("RO" + DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(7));
				receipt.setUserId(userId);
				receipt.setTradeId(Long.valueOf(tradeId));
				receipt.setType("P");

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
					receiptDetailDao.createReceiptDetail(receiptId, receiptDetailList, userId.toString());
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
	public BooleanResult all(Long userId, String tradeId) {

		// TODO Auto-generated method stub
		return null;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
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
