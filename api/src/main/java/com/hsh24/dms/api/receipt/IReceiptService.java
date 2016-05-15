package com.hsh24.dms.api.receipt;

import java.util.List;

import com.hsh24.dms.api.receipt.bo.Receipt;
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
	 * @param shopId
	 * @param tradeId
	 * @param receiptDetailList
	 * @return
	 */
	BooleanResult part(Long shopId, String tradeId, List<ReceiptDetail> receiptDetailList, String modifyUser);

	/**
	 * 
	 * @param shopId
	 * @param tradeId
	 * @return
	 */
	BooleanResult all(Long shopId, String tradeId, String modifyUser);

	/**
	 * 收货单 包含明细.
	 * 
	 * @param shopId
	 * @param tradeId
	 * @return
	 */
	List<Receipt> getReceiptList(Long shopId, Long tradeId);

}
