package com.hsh24.dms.user.service.impl;

import java.util.List;

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
public class UserShopServiceImpl implements IUserShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserShopServiceImpl.class);

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

	public IUserShopDao getUserShopDao() {
		return userShopDao;
	}

	public void setUserShopDao(IUserShopDao userShopDao) {
		this.userShopDao = userShopDao;
	}

}
