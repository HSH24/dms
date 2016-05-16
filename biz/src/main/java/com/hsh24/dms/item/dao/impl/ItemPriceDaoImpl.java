package com.hsh24.dms.item.dao.impl;

import com.hsh24.dms.api.item.bo.ItemPrice;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.item.dao.IItemPriceDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemPriceDaoImpl extends BaseDaoImpl implements IItemPriceDao {

	@Override
	public ItemPrice getItemPrice(ItemPrice itemPrice) {
		return (ItemPrice) getSqlMapClientTemplate().queryForObject("item.price.getItemPrice", itemPrice);
	}

}
