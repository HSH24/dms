package com.hsh24.dms.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemService {

	/**
	 * 
	 * @param shopId
	 *            门店编号.
	 * @param item
	 * @return
	 */
	int getItemCount(Long shopId, Item item);

	/**
	 * 
	 * @param shopId
	 *            门店编号.
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Long shopId, Item item);

	/**
	 * 
	 * @param shopId
	 *            门店编号.
	 * @param itemId
	 * @return
	 */
	Item getItem(Long shopId, String itemId);

	/**
	 * 
	 * @param itemId
	 * @return
	 */
	Map<Long, Item> getItem(String[] itemId);

	/**
	 * 验证 itemId 和 skuId 并返回 supId.
	 * 
	 * @param itemId
	 * @param skuId
	 * @return
	 */
	BooleanResult validate(Long itemId, Long skuId);

}
