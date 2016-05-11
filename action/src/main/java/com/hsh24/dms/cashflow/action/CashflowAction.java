package com.hsh24.dms.cashflow.action;

import java.util.List;

import com.hsh24.dms.api.cashflow.ICashflowService;
import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowAction extends BaseAction {

	private static final long serialVersionUID = -3907855176084554755L;

	private ICashflowService cashflowService;

	private List<Cashflow> cashflowList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		cashflowList = cashflowService.getCashflowList(this.getShop().getShopId(), new Cashflow());

		return SUCCESS;
	}

	public ICashflowService getCashflowService() {
		return cashflowService;
	}

	public void setCashflowService(ICashflowService cashflowService) {
		this.cashflowService = cashflowService;
	}

	public List<Cashflow> getCashflowList() {
		return cashflowList;
	}

	public void setCashflowList(List<Cashflow> cashflowList) {
		this.cashflowList = cashflowList;
	}

}
