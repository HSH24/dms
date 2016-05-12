package com.hsh24.dms.api.item;

import java.util.List;

import com.hsh24.dms.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemRegionService {

	/**
	 * 
	 * @param region
	 * @param item
	 * @return
	 */
	List<Item> getItemList(String[] region, Item item);

}
