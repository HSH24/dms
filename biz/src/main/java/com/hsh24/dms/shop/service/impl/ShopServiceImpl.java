package com.hsh24.dms.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.shop.IShopService;
import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.shop.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ShopServiceImpl implements IShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ShopServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IShopDao shopDao;

	@Override
	public Shop getShop(Long shopId) {
		if (shopId == null) {
			return null;
		}

		String key = shopId.toString();

		Shop shop = null;

		try {
			shop = (Shop) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, e);
		}

		if (shop != null) {
			return shop;
		}

		shop = new Shop();
		shop.setShopId(shopId);

		try {
			shop = shopDao.getShop(shop);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(shop), e);

			return null;
		}

		if (shop == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, shop);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, e);
		}

		return shop;
	}

}
