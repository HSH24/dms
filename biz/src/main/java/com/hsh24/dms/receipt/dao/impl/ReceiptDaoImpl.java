package com.hsh24.dms.receipt.dao.impl;

import com.hsh24.dms.api.receipt.bo.Receipt;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.receipt.dao.IReceiptDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDaoImpl extends BaseDaoImpl implements IReceiptDao {

	@Override
	public Long createReceipt(Receipt receipt) {
		return (Long) getSqlMapClientTemplate().insert("receipt.createReceipt", receipt);
	}

}
