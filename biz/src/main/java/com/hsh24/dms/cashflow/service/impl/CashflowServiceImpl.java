package com.hsh24.dms.cashflow.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.cashflow.ICashflowService;
import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.cashflow.dao.ICashflowDao;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowServiceImpl implements ICashflowService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CashflowServiceImpl.class);

	private ICashflowDao cashflowDao;

	@Override
	public BooleanResult createCashflow(Long shopId, Cashflow cashflow, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空。");
			return result;
		}

		if (cashflow == null) {
			result.setCode("资金流水信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		cashflow.setShopId(shopId);
		cashflow.setModifyUser(modifyUser);

		try {
			cashflowDao.createCashflow(cashflow);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cashflow), e);

			result.setCode("添加资金流水信息失败，请稍后再试。");
		}

		if (result.getResult()) {
			result.setCode("添加成功。");
		}

		return result;
	}

	@Override
	public Cashflow getCashflowStats(Long shopId) {
		if (shopId == null) {
			return null;
		}

		Cashflow cashflow = new Cashflow();
		cashflow.setShopId(shopId);

		try {
			return cashflowDao.getCashflowStats(cashflow);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cashflow), e);
		}

		return null;
	}

	@Override
	public int getCashflowCount(Long shopId, Cashflow cashflow) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Cashflow> getCashflowList(Long shopId, Cashflow cashflow) {
		if (shopId == null || cashflow == null) {
			return null;
		}

		cashflow.setShopId(shopId);

		// 暂不分页
		cashflow.setLimit(10);
		cashflow.setOffset(0);
		cashflow.setSort("CREATE_DATE");
		cashflow.setOrder("DESC");

		try {
			return cashflowDao.getCashflowList(cashflow);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cashflow), e);
		}

		return null;
	}

	public ICashflowDao getCashflowDao() {
		return cashflowDao;
	}

	public void setCashflowDao(ICashflowDao cashflowDao) {
		this.cashflowDao = cashflowDao;
	}

}
