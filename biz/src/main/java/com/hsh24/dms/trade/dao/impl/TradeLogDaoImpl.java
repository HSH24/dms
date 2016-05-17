package com.hsh24.dms.trade.dao.impl;

import com.hsh24.dms.api.trade.bo.TradeLog;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.trade.dao.ITradeLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeLogDaoImpl extends BaseDaoImpl implements ITradeLogDao {

	@Override
	public Long createTradeLog(TradeLog tradeLog) {
		return (Long) getSqlMapClientTemplate().insert("trade.log.createTradeLog", tradeLog);
	}

}
