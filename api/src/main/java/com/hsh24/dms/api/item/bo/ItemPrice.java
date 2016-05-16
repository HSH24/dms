package com.hsh24.dms.api.item.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemPrice implements Serializable {

	private static final long serialVersionUID = -6813198383731239434L;

	private Long itemPriceId;

	private Long skuId;

	private BigDecimal price;

	public Long getItemPriceId() {
		return itemPriceId;
	}

	public void setItemPriceId(Long itemPriceId) {
		this.itemPriceId = itemPriceId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
