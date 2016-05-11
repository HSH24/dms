package com.hsh24.dms.api.bankAcct;

import java.math.BigDecimal;

import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.framework.bo.BooleanResult;

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

	/**
	 * 
	 * @param shopId
	 * @param bankAcctId
	 * @param amount
	 * @param modifyUser
	 * @return
	 */
	BooleanResult updateBankAcct(Long shopId, Long bankAcctId, BigDecimal amount, String modifyUser);

}
