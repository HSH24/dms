package com.hsh24.dms.api.cashflow;

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

}
