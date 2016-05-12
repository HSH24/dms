package com.hsh24.dms.api.shop;

import com.hsh24.dms.api.shop.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IShopService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(Long shopId);

}
