package com.hsh24.dms.api.receipt;

import java.util.List;

import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptService {

	/**
	 * 
	 * @param userId
	 * @param tradeId
	 * @param receiptDetailList
	 * @return
	 */
	BooleanResult part(Long userId, String tradeId, List<ReceiptDetail> receiptDetailList);

	/**
	 * 
	 * @param userId
	 * @param tradeId
	 * @return
	 */
	BooleanResult all(Long userId, String tradeId);

}
