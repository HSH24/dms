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

	private Long id;

	private Long itemRegionId;

	private Long skuId;

	private BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemRegionId() {
		return itemRegionId;
	}

	public void setItemRegionId(Long itemRegionId) {
		this.itemRegionId = itemRegionId;
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
