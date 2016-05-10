package com.hsh24.dms.bankAcct.dao;

import com.hsh24.dms.api.bankAcct.bo.BankAcct;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBankAcctDao {

	/**
	 * 
	 * @param bankAcct
	 * @return
	 */
	BankAcct getBankAcct(BankAcct bankAcct);

}
