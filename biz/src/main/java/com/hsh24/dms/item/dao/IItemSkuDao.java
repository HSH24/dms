package com.hsh24.dms.item.dao;

import java.util.List;

import com.hsh24.dms.api.item.bo.ItemSku;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemSkuDao {

	/**
	 * 
	 * @param sku
	 * @return
	 */
	List<ItemSku> getItemSkuList(ItemSku sku);

}
