package com.hsh24.dms.receipt.dao;

import com.hsh24.dms.api.receipt.bo.ReceiptLog;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptLogDao {

	/**
	 * 
	 * @param receiptLog
	 * @return
	 */
	int createReceiptLog(ReceiptLog receiptLog);

}
