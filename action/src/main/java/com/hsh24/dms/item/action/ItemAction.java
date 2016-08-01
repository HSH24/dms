package com.hsh24.dms.item.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	@Resource
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

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

}
