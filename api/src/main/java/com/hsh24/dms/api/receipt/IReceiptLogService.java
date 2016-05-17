package com.hsh24.dms.api.receipt;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptLogService {

	/**
	 * 
	 * @param receiptId
	 * @param type
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createReceiptLog(Long receiptId, String type, String modifyUser);

}
