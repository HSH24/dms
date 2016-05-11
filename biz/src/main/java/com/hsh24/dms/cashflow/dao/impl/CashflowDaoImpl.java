package com.hsh24.dms.cashflow.dao.impl;

import java.util.List;

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

	@Override
	public int getCashflowCount(Cashflow cashflow) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cashflow> getCashflowList(Cashflow cashflow) {
		return (List<Cashflow>) getSqlMapClientTemplate().queryForList("cashflow.getCashflowList", cashflow);
	}

}
