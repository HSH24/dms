package com.hsh24.dms.api.item.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemRegion implements Serializable {

	private static final long serialVersionUID = 3773152586504724473L;

	private Long itemRegionId;

	private Long itemId;

	private Long regionId;

	public Long getItemRegionId() {
		return itemRegionId;
	}

	public void setItemRegionId(Long itemRegionId) {
		this.itemRegionId = itemRegionId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

}
