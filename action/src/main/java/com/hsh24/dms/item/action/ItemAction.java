package com.hsh24.dms.item.action;

import java.util.List;

import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	private IItemService itemService;

	private List<Item> itemList;

	/**
	 * 
	 * @return
	 */
	public String list() {
		itemList = itemService.getItemList(this.getShop().getShopId(), new Item());

		return SUCCESS;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

}
