package com.hsh24.dms.user.service.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.user.IUserService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.user.dao.IUserDao;
import com.wideka.weixin.framework.util.EncryptUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserServiceImpl implements IUserService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	private IUserDao userDao;

	@Override
	public User getUser(String passport) {
		if (StringUtils.isBlank(passport)) {
			return null;
		}

		String key = passport.trim().toUpperCase();

		User user = null;

		try {
			user = (User) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_PASSPORT + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_PASSPORT + key, e);
		}

		if (user != null) {
			return user;
		}

		user = getUser4Validate(passport);

		// not null then set to cache
		if (user != null) {
			try {
				memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_PASSPORT + key, user);
			} catch (ServiceException e) {
				logger.error(IMemcachedCacheService.CACHE_KEY_PASSPORT + key, e);
			}
		}

		return user;
	}

	@Override
	public User getUser4Validate(String passport) {
		if (StringUtils.isBlank(passport)) {
			return null;
		}

		try {
			return userDao.getUserByPassport(passport.trim());
		} catch (Exception e) {
			logger.error(passport, e);
		}

		return null;
	}

	@Override
	public BooleanResult setPassword(String passport, String password, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("账号信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(password)) {
			result.setCode("密码信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		User user = new User();
		user.setPassport(passport.trim());

		try {
			user.setPassword(EncryptUtil.encryptMD5(password).toUpperCase());
		} catch (IOException e) {
			logger.error("password:" + password, e);

			result.setCode("密码加密失败");
			return result;
		}

		user.setModifyUser(modifyUser);

		try {
			int c = userDao.updateUser(user);
			if (c == 1) {
				result.setResult(true);

				// remove cache
				remove(passport.trim());
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);

			result.setCode("修改用户信息失败");
		}

		return result;
	}

	@Override
	public BooleanResult setUserName(String passport, String userName, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("账号信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(userName)) {
			result.setCode("名字信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		User user = new User();
		user.setPassport(passport.trim());
		user.setUserName(userName.trim());
		user.setModifyUser(modifyUser);

		try {
			int c = userDao.updateUser(user);
			if (c == 1) {
				result.setResult(true);

				// remove cache
				remove(passport.trim());
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);

			result.setCode("修改用户信息失败");
		}

		return result;
	}

	/**
	 * remove cache.
	 * 
	 * @param userId
	 */
	private void remove(String passport) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_PASSPORT + passport);
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
