package com.hsh24.dms.receipt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	int createReceiptDetail(@Param("receiptId") Long receiptId,
		@Param("receiptDetailList") List<ReceiptDetail> receiptDetailList, @Param("modifyUser") String modifyUser);

	/**
	 * 
	 * @param receiptDetail
	 * @return
	 */
	List<ReceiptDetail> getReceiptDetailList(ReceiptDetail receiptDetail);

}
