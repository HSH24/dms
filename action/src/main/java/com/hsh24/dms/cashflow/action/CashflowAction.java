package com.hsh24.dms.cashflow.action;

import java.util.List;

import com.hsh24.dms.api.cashflow.ICashflowService;
import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.util.FormatUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowAction extends BaseAction {

	private static final long serialVersionUID = -3907855176084554755L;

	private ICashflowService cashflowService;

	private Cashflow cashflow;

	private List<Cashflow> cashflowList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		Long shopId = this.getShop().getShopId();

		cashflow = cashflowService.getCashflowStats(shopId);

		cashflowList = cashflowService.getCashflowList(shopId, new Cashflow());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Long shopId = this.getShop().getShopId();

		cashflow = cashflowService.getCashflowStats(shopId);

		sb.append(FormatUtil.getAmountFormat(cashflow.getDrAmount())).append("&");
		sb.append(FormatUtil.getAmountFormat(cashflow.getCrAmount()));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	public ICashflowService getCashflowService() {
		return cashflowService;
	}

	public void setCashflowService(ICashflowService cashflowService) {
		this.cashflowService = cashflowService;
	}

	public Cashflow getCashflow() {
		return cashflow;
	}

	public void setCashflow(Cashflow cashflow) {
		this.cashflow = cashflow;
	}

	public List<Cashflow> getCashflowList() {
		return cashflowList;
	}

	public void setCashflowList(List<Cashflow> cashflowList) {
		this.cashflowList = cashflowList;
	}

}
