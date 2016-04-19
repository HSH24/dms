package com.hsh24.dms.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.item.bo.ItemSku;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemSkuService {

	/**
	 * 
	 * @param supId
	 * @param itemId
	 * @return
	 */
	List<ItemSku> getItemSkuList(Long supId, String itemId);

	/**
	 * 
	 * @param supId
	 * @param skuId
	 * @return
	 */
	Map<Long, ItemSku> getItemSku(Long supId, String[] skuId);

}
