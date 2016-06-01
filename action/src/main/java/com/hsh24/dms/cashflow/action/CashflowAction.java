package com.hsh24.dms.cashflow.action;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.dms.api.bankAcct.IBankAcctService;
import com.hsh24.dms.api.bankAcct.bo.BankAcct;
import com.hsh24.dms.api.cashflow.ICashflowService;
import com.hsh24.dms.api.cashflow.bo.Cashflow;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.util.DateUtil;
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

	private List<Cashflow> cashflowList;

	private String type;

	private String year;

	private String month;

	/**
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Long shopId = this.getShop().getShopId();

		if (StringUtils.isBlank(type)) {
			Cashflow cashflow = cashflowService.getCashflowStats(shopId);
			sb.append(FormatUtil.getAmountFormat(cashflow == null ? BigDecimal.ZERO : cashflow.getDrAmount())).append(
				"&");
			sb.append(FormatUtil.getAmountFormat(cashflow == null ? BigDecimal.ZERO : cashflow.getCrAmount())).append(
				"&");

			BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
			sb.append(FormatUtil.getAmountFormat(bankAcct == null ? BigDecimal.ZERO : bankAcct.getCurBal()));

			this.setResourceResult(sb.toString());

			return RESOURCE_RESULT;
		}

		Cashflow cashflow = cashflowService.getCashflowStats(shopId, "A");
		sb.append(FormatUtil.getAmountFormat(cashflow == null ? BigDecimal.ZERO : cashflow.getDrAmount())).append("&");

		cashflow = cashflowService.getCashflowStats(shopId, "C");
		sb.append(FormatUtil.getAmountFormat(cashflow == null ? BigDecimal.ZERO : cashflow.getDrAmount())).append("&");

		cashflow = cashflowService.getCashflowStats(shopId, "B");
		sb.append(FormatUtil.getAmountFormat(cashflow == null ? BigDecimal.ZERO : cashflow.getCrAmount())).append("&");

		BankAcct bankAcct = bankAcctService.getBankAcct(shopId, "1001");
		sb.append(FormatUtil.getAmountFormat(bankAcct == null ? BigDecimal.ZERO : bankAcct.getCurBal()));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @param cashflow
	 * @return
	 */
	private Cashflow init(Cashflow cashflow) {
		if (StringUtils.isBlank(year)) {
			year = String.valueOf(DateUtil.getYear());
		}

		if (StringUtils.isBlank(month)) {
			month = String.valueOf(DateUtil.getMonth());
		}

		cashflow.setGmtStart(year + "-" + month + "-01 00:00:00");
		cashflow.setGmtEnd(DateUtil.datetime(
			DateUtil.getLastDayOfLastMonth(Integer.parseInt(year), Integer.parseInt(month)),
			DateUtil.DEFAULT_DATE_FORMAT) + " 23:59:59");

		return cashflow;
	}

	/**
	 * 
	 * @return
	 */
	public String list() {
		Long shopId = this.getShop().getShopId();

		cashflowList = cashflowService.getCashflowList(shopId, init(new Cashflow()));

		return SUCCESS;
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

	public List<Cashflow> getCashflowList() {
		return cashflowList;
	}

	public void setCashflowList(List<Cashflow> cashflowList) {
		this.cashflowList = cashflowList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
