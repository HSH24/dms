package com.hsh24.dms.account.service.impl;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.account.IAccountService;
import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
public class AccountServiceImpl implements IAccountService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(AccountServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	@Override
	public BooleanResult generateCheckCode(String passport) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("登录帐号不能为空！");
			return result;
		}

		String token = String.valueOf(new Random().nextInt(99999));

		String key = passport.trim() + "@" + token;

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key, passport,
				IMemcachedCacheService.CACHE_KEY_CHECK_CODE_DEFAULT_EXP);
		} catch (Exception e) {
			logger.error(e);

			result.setCode("系统正忙，请稍后再试！");
			return result;
		}

		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult setPassword(String checkCode, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

}
