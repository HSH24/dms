package com.hsh24.dms.item.dao;

import com.hsh24.dms.api.item.bo.ItemPrice;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemPriceDao {

	/**
	 * 
	 * @param itemPrice
	 * @return
	 */
	ItemPrice getItemPrice(ItemPrice itemPrice);

}
