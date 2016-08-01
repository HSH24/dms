package com.hsh24.dms.user.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.dms.api.ca.ICAService;
import com.hsh24.dms.api.ca.bo.ValidateResult;
import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.sms.ISMSService;
import com.hsh24.dms.api.user.IUserAcctService;
import com.hsh24.dms.api.user.IUserService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserAcctServiceImpl implements IUserAcctService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserAcctServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserService userService;

	@Resource
	private ISMSService smsService;

	@Resource
	private ICAService caService;

	@Override
	public BooleanResult generateCheckCode(String passport) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("登录帐号不能为空");
			return result;
		}

		// 验证登录账号是否存在
		User user = userService.getUser(passport);
		if (user == null) {
			result.setCode("当前登录帐号在系统中不存在");
			return result;
		}

		String token = String.valueOf(new Random().nextInt(999999));

		String key = passport.trim();

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key, token,
				IMemcachedCacheService.CACHE_KEY_CHECK_CODE_DEFAULT_EXP);
		} catch (ServiceException e) {
			try {
				token = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key);
			} catch (ServiceException se) {
				logger.error(se);

				result.setCode("系统正忙，请稍后再试");
				return result;
			}
		}

		result = smsService.send("好社惠", "SMS_8550784", "{\"code\":\"" + token + "\"}", passport, null);

		return result;
	}

	@Override
	public BooleanResult validateCheckCode(String passport, String checkCode) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(passport)) {
			result.setCode("登录帐号不能为空");
			return result;
		}

		if (StringUtils.isBlank(checkCode)) {
			result.setCode("验证码不能为空");
			return result;
		}

		String value = null;

		String key = passport.trim();

		try {
			value = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode("系统正忙，请稍后再试");
			return result;
		}

		if (StringUtils.isEmpty(value)) {
			result.setCode("验证码已失效，请稍后再试");
			return result;
		}

		if (!value.equals(checkCode.trim())) {
			result.setCode("验证码不正确，请稍后再试");
			return result;
		}

		// invalid
		invalidCheckCode(key);

		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult validatePassword(String passport, String password) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		ValidateResult res = caService.validateUser(passport, password);

		// 验证失败
		if (ICAService.RESULT_FAILED.equals(res.getResultCode()) || ICAService.RESULT_ERROR.equals(res.getResultCode())) {
			result.setCode(res.getMessage());
			return result;
		}

		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult setPassword(String passport, String password) {
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

		// TODO
		// 验证令牌 修改密码必须 1.验证密码 2.验证短信

		return userService.setPassword(passport.trim(), password, passport);
	}

	/**
	 * 
	 * @param key
	 */
	private void invalidCheckCode(String key) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key);
		} catch (Exception e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_CHECK_CODE + key, e);
		}
	}

}
