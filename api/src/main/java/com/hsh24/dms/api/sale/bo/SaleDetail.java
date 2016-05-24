package com.hsh24.dms.api.sale.bo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleDetail {

	private String itemName;

	private String unit;

	private int quantity;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private Long shopId;

	private String tradeNo;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}
