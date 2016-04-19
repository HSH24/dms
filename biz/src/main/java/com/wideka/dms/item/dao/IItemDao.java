package com.wideka.dms.item.dao;

import java.util.List;

import com.wideka.dms.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemDao {

	/**
	 * 
	 * @param item
	 * @return
	 */
	int getItemCount(Item item);

	/**
	 * 
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Item item);

	/**
	 * 
	 * @param item
	 * @return
	 */
	Item getItem(Item item);

}
