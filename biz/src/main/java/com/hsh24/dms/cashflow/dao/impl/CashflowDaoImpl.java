package com.hsh24.dms.cashflow.dao.impl;

import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.cashflow.dao.ICashflowDao;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowDaoImpl extends BaseDaoImpl implements ICashflowDao {

	@Override
	public Long createCashflow(Cashflow cashflow) {
		return (Long) getSqlMapClientTemplate().insert("cashflow.createCashflow", cashflow);
	}

}
