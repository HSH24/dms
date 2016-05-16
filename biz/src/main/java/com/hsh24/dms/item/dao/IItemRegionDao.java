package com.hsh24.dms.item.dao;

import java.util.List;

import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.api.item.bo.ItemRegion;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemRegionDao {

	/**
	 * 
	 * @param item
	 * @return
	 */
	List<ItemRegion> getItemRegionList(Item item);

}
