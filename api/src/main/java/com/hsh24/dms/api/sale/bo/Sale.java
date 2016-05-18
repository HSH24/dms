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
