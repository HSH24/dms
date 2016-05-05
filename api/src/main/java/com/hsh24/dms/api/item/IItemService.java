package com.hsh24.dms.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemService {

	/**
	 * 
	 * @param storeId
	 *            门店编号.
	 * @param item
	 * @return
	 */
	int getItemCount(Long storeId, Item item);

	/**
	 * 
	 * @param storeId
	 *            门店编号.
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Long storeId, Item item);

	/**
	 * 
	 * @param storeId
	 *            门店编号.
	 * @param itemId
	 * @return
	 */
	Item getItem(Long storeId, String itemId);

	/**
	 * 
	 * @param storeId
	 *            门店编号.
	 * @param itemId
	 * @return
	 */
	Map<Long, Item> getItem(Long storeId, String[] itemId);

}
