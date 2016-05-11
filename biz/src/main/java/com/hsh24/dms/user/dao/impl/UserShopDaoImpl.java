package com.hsh24.dms.user.dao.impl;

import java.util.List;

import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.user.dao.IUserShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserShopDaoImpl extends BaseDaoImpl implements IUserShopDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> getShopList(User user) {
		return (List<Shop>) getSqlMapClientTemplate().queryForList("user.shop.getShopList", user);
	}

	@Override
	public Shop getShop(User user) {
		return (Shop) getSqlMapClientTemplate().queryForObject("user.shop.getShop", user);
	}

}
