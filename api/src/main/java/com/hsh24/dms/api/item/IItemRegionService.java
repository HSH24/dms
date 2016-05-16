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
	 * distinct item_id.
	 * 
	 * @param region
	 * @param item
	 * @return
	 */
	List<ItemRegion> getItemRegionList(String[] region, Item item);

	/**
	 * 
	 * @param itemId
	 * @param regionId
	 * @return
	 */
	ItemRegion getItemRegion(Long itemId, Long regionId);

}
