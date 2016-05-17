package com.hsh24.dms.trade.dao;

import com.hsh24.dms.api.trade.bo.TradeLog;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeLogDao {

	/**
	 * 
	 * @param tradeLog
	 * @return
	 */
	Long createTradeLog(TradeLog tradeLog);

}
