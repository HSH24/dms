package com.hsh24.dms.receipt.dao;

import java.util.List;

import com.hsh24.dms.api.receipt.bo.Receipt;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptDao {

	/**
	 * 
	 * @param receipt
	 * @return
	 */
	Long createReceipt(Receipt receipt);

	/**
	 * 
	 * @param receipt
	 * @return
	 */
	List<Receipt> getReceiptList(Receipt receipt);

}
