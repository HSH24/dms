package com.hsh24.dms.api.stock.bo;

import java.math.BigDecimal;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Stock {

	private Long shopId;

	private BigDecimal amount;

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

}
