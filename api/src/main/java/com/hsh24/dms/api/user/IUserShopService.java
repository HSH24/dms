package com.hsh24.dms.api.user;

import java.util.List;

import com.hsh24.dms.api.shop.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserShopService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Shop> getShopList(Long userId);

}
