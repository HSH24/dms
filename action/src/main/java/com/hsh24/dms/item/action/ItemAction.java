package com.hsh24.dms.item.action;

import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	private IItemService itemService;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

}
