package com.hsh24.dms.api.item;

import com.hsh24.dms.api.item.bo.ItemPrice;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemPriceService {

	/**
	 * 
	 * @param itemRegionId
	 * @param skuId
	 * @return
	 */
	ItemPrice getItemPrice(Long itemRegionId, Long skuId);

}
