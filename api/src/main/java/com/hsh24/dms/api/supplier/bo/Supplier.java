package com.hsh24.dms.api.supplier.bo;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Supplier extends SearchInfo {

	private static final long serialVersionUID = 2438290209511970364L;

	private Long supId;

	private String supName;

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

}
