package com.hsh24.dms.api.sale.bo;

import java.math.BigDecimal;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Sale extends SearchInfo {

	private static final long serialVersionUID = 4733426047070262299L;

	private Long shopId;

	private BigDecimal amount;

	private String itemName;

	private String unit;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
