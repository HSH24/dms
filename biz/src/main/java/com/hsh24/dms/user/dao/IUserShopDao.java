package com.hsh24.dms.user.dao;

import java.util.List;

import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserShopDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	List<Shop> getShopList(User user);

}
