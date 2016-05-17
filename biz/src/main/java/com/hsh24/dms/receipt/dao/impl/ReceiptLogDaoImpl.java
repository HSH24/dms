package com.hsh24.dms.receipt.dao.impl;

import com.hsh24.dms.api.receipt.bo.ReceiptLog;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.receipt.dao.IReceiptLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptLogDaoImpl extends BaseDaoImpl implements IReceiptLogDao {

	@Override
	public Long createReceiptLog(ReceiptLog receiptLog) {
		return (Long) getSqlMapClientTemplate().insert("receipt.log.createReceiptLog", receiptLog);
	}

}
