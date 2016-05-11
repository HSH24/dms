package com.hsh24.dms.api.cashflow;

import java.util.List;

import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICashflowService {

	/**
	 * 
	 * @param shopId
	 * @param cashflow
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createCashflow(Long shopId, Cashflow cashflow, String modifyUser);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Cashflow getCashflowStats(Long shopId);

	/**
	 * 
	 * @param shopId
	 * @param cashflow
	 * @return
	 */
	int getCashflowCount(Long shopId, Cashflow cashflow);

	/**
	 * 
	 * @param shopId
	 * @param cashflow
	 * @return
	 */
	List<Cashflow> getCashflowList(Long shopId, Cashflow cashflow);

}
