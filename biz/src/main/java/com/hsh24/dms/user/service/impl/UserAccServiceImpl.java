package com.hsh24.dms.user.service.impl;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.sms.ISMSService;
import com.hsh24.dms.api.user.IUserAccService;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserAccServiceImpl implements IUserAccService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserAccServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	private ISMSService smsService;

	@Override
	public BooleanResult generateCheckCode(String passport) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("登录帐号不能为空。");
			return result;
		}

		// TODO
		// 验证登录账号是否存在

		String token = String.valueOf(new Random().nextInt(999999));

		String key = passport.trim();

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key, token,
				IMemcachedCacheService.CACHE_KEY_CHECK_CODE_DEFAULT_EXP);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode("系统正忙，请稍后再试。");
			return result;
		}

		result = smsService.send("好社惠", "SMS_8550784", "{\"code\":\"" + token + "\"}", passport, null);

		return result;
	}

	@Override
	public BooleanResult validateCheckCode(String passport, String checkCode) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("登录帐号不能为空。");
			return result;
		}

		if (StringUtils.isBlank(checkCode)) {
			result.setCode("验证码不能为空。");
			return result;
		}

		String value = null;

		String key = passport.trim();

		try {
			value = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode("系统正忙，请稍后再试。");
			return result;
		}

		if (StringUtils.isEmpty(value)) {
			result.setCode("验证码已失效，请稍后再试。");
			return result;
		}

		if (!value.equals(checkCode.trim())) {
			result.setCode("验证码不正确，请稍后再试。");
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

	public ISMSService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISMSService smsService) {
		this.smsService = smsService;
	}

}
