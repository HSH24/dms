package com.hsh24.dms.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.user.IUserShopService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.user.dao.IUserShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserShopServiceImpl implements IUserShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserShopServiceImpl.class);

	@Resource
	private IUserShopDao userShopDao;

	@Override
	public List<Shop> getShopList(Long userId) {
		if (userId == null) {
			return null;
		}

		User user = new User();
		user.setUserId(userId);

		try {
			return userShopDao.getShopList(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

	@Override
	public Shop getShop(Long userId, String shopId) {
		if (userId == null || StringUtils.isBlank(shopId)) {
			return null;
		}

		User user = new User();
		user.setUserId(userId);
		try {
			user.setShopId(Long.valueOf(shopId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		try {
			return userShopDao.getShop(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

}
