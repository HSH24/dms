package com.hsh24.dms.api.bankAcct;

import com.hsh24.dms.api.bankAcct.bo.BankAcct;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBankAcctService {

	/**
	 * 
	 * @param shopId
	 * @param accCode
	 * @return
	 */
	BankAcct getBankAcct(Long shopId, String accCode);

}
