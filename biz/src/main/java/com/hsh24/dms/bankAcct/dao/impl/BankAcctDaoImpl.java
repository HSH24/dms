package com.hsh24.dms.bankAcct.dao.impl;

import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.bankAcct.dao.IBankAcctDao;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;

/**
 * 
 * @author JiakunXu
 * 
 */
public class BankAcctDaoImpl extends BaseDaoImpl implements IBankAcctDao {

	@Override
	public BankAcct getBankAcct(BankAcct bankAcct) {
		return (BankAcct) getSqlMapClientTemplate().queryForObject("bankAcct.getBankAcct", bankAcct);
	}

	@Override
	public int updateBankAcct(BankAcct bankAcct) {
		return getSqlMapClientTemplate().update("bankAcct.updateBankAcct", bankAcct);
	}

}
