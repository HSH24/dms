package com.hsh24.dms.cashflow.dao;

import java.util.List;

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

	/**
	 * 
	 * @param cashflow
	 * @return
	 */
	int getCashflowCount(Cashflow cashflow);

	/**
	 * 
	 * @param cashflow
	 * @return
	 */
	List<Cashflow> getCashflowList(Cashflow cashflow);

}
