package com.hsh24.dms.shop.dao.impl;

import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.shop.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopDaoImpl extends BaseDaoImpl implements IShopDao {

	@Override
	public Shop getShop(Shop shop) {
		return (Shop) getSqlMapClientTemplate().queryForObject("shop.getShop", shop);
	}

}
