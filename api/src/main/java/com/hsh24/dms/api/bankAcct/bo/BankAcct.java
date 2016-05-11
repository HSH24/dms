package com.hsh24.dms.api.bankAcct.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author JiakunXu
 * 
 */
public class BankAcct implements Serializable {

	private static final long serialVersionUID = 7924858537743229044L;

	private Long bankAcctId;

	private Long shopId;

	private Long accId;

	private BigDecimal curBal;

	private BigDecimal minDeposit;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private String accCode;

	private BigDecimal amount;

	public Long getBankAcctId() {
		return bankAcctId;
	}

	public void setBankAcctId(Long bankAcctId) {
		this.bankAcctId = bankAcctId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public BigDecimal getCurBal() {
		return curBal;
	}

	public void setCurBal(BigDecimal curBal) {
		this.curBal = curBal;
	}

	public BigDecimal getMinDeposit() {
		return minDeposit;
	}

	public void setMinDeposit(BigDecimal minDeposit) {
		this.minDeposit = minDeposit;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getAccCode() {
		return accCode;
	}

	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
