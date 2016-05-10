package com.hsh24.dms.cashflow.dao;

import com.hsh24.dms.api.cashflow.bo.Cashflow;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICashflowDao {

	/**
	 * 
	 * @param cashflow
	 * @return
	 */
	Long createCashflow(Cashflow cashflow);

}
