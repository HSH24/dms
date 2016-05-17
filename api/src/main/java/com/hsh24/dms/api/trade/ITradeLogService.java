package com.hsh24.dms.api.trade;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeLogService {

	/**
	 * 
	 * @param tradeId
	 * @param type
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createTradeLog(Long tradeId, String type, String modifyUser);

}
