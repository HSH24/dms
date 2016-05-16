package com.hsh24.dms.api.item;

import java.util.List;

import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.api.item.bo.ItemRegion;

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
	List<ItemRegion> getItemRegionList(String[] region, Item item);

}
