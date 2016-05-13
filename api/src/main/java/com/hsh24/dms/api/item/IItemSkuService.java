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
	 * 获得商品的所有SKU.
	 * 
	 * @param itemId
	 * @return
	 */
	List<ItemSku> getItemSkuList(Long itemId);

	/**
	 * 
	 * @param skuId
	 * @return
	 */
	Map<Long, ItemSku> getItemSku(String[] skuId);

}
