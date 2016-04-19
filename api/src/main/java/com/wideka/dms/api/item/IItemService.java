package com.wideka.dms.api.item;

import java.util.List;
import java.util.Map;

import com.wideka.dms.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemService {

	/**
	 * 
	 * @param supId
	 * @param item
	 * @return
	 */
	int getItemCount(Long supId, Item item);

	/**
	 * 
	 * @param supId
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Long supId, Item item);

	/**
	 * 
	 * @param supId
	 * @param itemId
	 * @return
	 */
	Item getItem(Long supId, String itemId);

	/**
	 * 
	 * @param supId
	 * @param itemId
	 * @return
	 */
	Map<Long, Item> getItem(Long supId, String[] itemId);

}
