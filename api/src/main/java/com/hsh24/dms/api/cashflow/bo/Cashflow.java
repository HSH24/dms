package com.hsh24.dms.api.cashflow.bo;

import java.math.BigDecimal;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Cashflow extends SearchInfo {

	private static final long serialVersionUID = 3180297576056212732L;

	private Long cashflowId;

	private Long shopId;

	/**
	 * 账户信息.
	 */
	private Long bankAcctId;

	/**
	 * 摘要.
	 */
	private String summary;

	private BigDecimal crAmount;

	private BigDecimal drAmount;

	/**
	 * 交易时间.
	 */
	private String tradeDate;

	private String tradeNo;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private BigDecimal curBal;

	public Long getCashflowId() {
		return cashflowId;
	}

	public void setCashflowId(Long cashflowId) {
		this.cashflowId = cashflowId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getBankAcctId() {
		return bankAcctId;
	}

	public void setBankAcctId(Long bankAcctId) {
		this.bankAcctId = bankAcctId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public BigDecimal getCrAmount() {
		return crAmount;
	}

	public void setCrAmount(BigDecimal crAmount) {
		this.crAmount = crAmount;
	}

	public BigDecimal getDrAmount() {
		return drAmount;
	}

	public void setDrAmount(BigDecimal drAmount) {
		this.drAmount = drAmount;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public BigDecimal getCurBal() {
		return curBal;
	}

	public void setCurBal(BigDecimal curBal) {
		this.curBal = curBal;
	}

}
