package com.hsh24.dms.receipt.dao;

import java.util.List;

import com.hsh24.dms.api.receipt.bo.ReceiptDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptDetailDao {

	/**
	 * 
	 * @param receiptId
	 * @param receiptDetailList
	 * @param modifyUser
	 * @return
	 */
	int createReceiptDetail(Long receiptId, List<ReceiptDetail> receiptDetailList, String modifyUser);

}
