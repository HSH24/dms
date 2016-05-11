package com.hsh24.dms.cashflow.action;

import java.util.List;

import com.hsh24.dms.api.bankAcct.IBankAcctService;
import com.hsh24.dms.api.bankAcct.bo.BankAcct;
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

	private IBankAcctService bankAcctService;

	private Cashflow cashflow;

	private List<Cashflow> cashflowList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		Long shopId = this.getShop().getShopId();

		stats();

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

		BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
		cashflow.setCurBal(bankAcct.getCurBal());

		sb.append(FormatUtil.getAmountFormat(cashflow.getDrAmount())).append("&");
		sb.append(FormatUtil.getAmountFormat(cashflow.getCrAmount())).append("&");
		sb.append(FormatUtil.getAmountFormat(cashflow.getCurBal()));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	public ICashflowService getCashflowService() {
		return cashflowService;
	}

	public void setCashflowService(ICashflowService cashflowService) {
		this.cashflowService = cashflowService;
	}

	public IBankAcctService getBankAcctService() {
		return bankAcctService;
	}

	public void setBankAcctService(IBankAcctService bankAcctService) {
		this.bankAcctService = bankAcctService;
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
