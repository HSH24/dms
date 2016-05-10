package com.hsh24.dms.bankAcct.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.dms.api.bankAcct.IBankAcctService;
import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.bankAcct.dao.IBankAcctDao;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class BankAcctServiceImpl implements IBankAcctService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(BankAcctServiceImpl.class);

	private IBankAcctDao bankAcctDao;

	@Override
	public BankAcct getBankAcct(Long shopId, String accCode) {
		if (shopId == null || StringUtils.isBlank(accCode)) {
			return null;
		}

		BankAcct bankAcct = new BankAcct();
		bankAcct.setShopId(shopId);
		bankAcct.setAccCode(accCode.trim());

		try {
			return bankAcctDao.getBankAcct(bankAcct);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bankAcct), e);
		}

		return null;
	}

	public IBankAcctDao getBankAcctDao() {
		return bankAcctDao;
	}

	public void setBankAcctDao(IBankAcctDao bankAcctDao) {
		this.bankAcctDao = bankAcctDao;
	}

}
