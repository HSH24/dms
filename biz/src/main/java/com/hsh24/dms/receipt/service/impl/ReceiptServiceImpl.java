package com.hsh24.dms.receipt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
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
	public BooleanResult part(String userId, String tradeId, List<ReceiptDetail> receiptDetailList) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(userId)) {

		}

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
