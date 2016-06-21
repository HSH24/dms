package com.hsh24.dms.bankAcct.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.dms.api.bankAcct.IBankAcctService;
import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.bankAcct.dao.IBankAcctDao;
import com.hsh24.dms.framework.bo.BooleanResult;
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

	@Override
	public BooleanResult updateBankAcct(Long shopId, Long bankAcctId, BigDecimal amount, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		BankAcct bankAcct = new BankAcct();

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}
		bankAcct.setShopId(shopId);

		if (bankAcctId == null) {
			result.setCode("资金账户信息不能为空");
			return result;
		}
		bankAcct.setBankAcctId(bankAcctId);

		if (amount == null) {
			result.setCode("金额信息不能为空");
			return result;
		}
		bankAcct.setAmount(amount);

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}
		bankAcct.setModifyUser(modifyUser);

		try {
			int c = bankAcctDao.updateBankAcct(bankAcct);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新账户信息失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bankAcct), e);
			result.setCode("更新账户信息表失败");
		}

		return result;
	}

	public IBankAcctDao getBankAcctDao() {
		return bankAcctDao;
	}

	public void setBankAcctDao(IBankAcctDao bankAcctDao) {
		this.bankAcctDao = bankAcctDao;
	}

}
