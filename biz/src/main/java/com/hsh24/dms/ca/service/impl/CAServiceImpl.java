package com.hsh24.dms.ca.service.impl;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.ca.ICAService;
import com.hsh24.dms.api.ca.bo.ValidateResult;
import com.hsh24.dms.api.user.IUserService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.wideka.weixin.framework.util.EncryptUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CAServiceImpl implements ICAService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CAServiceImpl.class);

	private IUserService userService;

	@Override
	public ValidateResult validateUser(String passport, String password) {
		// 初始化返回值 状态 = 失败
		ValidateResult result = new ValidateResult();
		result.setResultCode(ICAService.RESULT_FAILED);
		result.setMessage(ICAService.INCORRECT_LOGIN);

		// 账号或密码为空
		if (StringUtils.isBlank(passport) || StringUtils.isEmpty(password)) {
			result.setMessage(ICAService.INCORRECT_NULL);
			return result;
		}

		// 根据passport查找用户信息
		User user = userService.getUserByPassport(passport);

		// 1. 判断登录用户是否在系统中
		if (user == null) {
			result.setMessage(ICAService.INCORRECT_LOGINID);
			return result;
		}

		// 2. 判斷登陸用戶是否已被禁用
		if ("F".equals(user.getState())) {
			result.setMessage(ICAService.INCORRECT_DISABLED);
			return result;
		}

		// 3. 验证账号密码
		try {
			if (((EncryptUtil.encryptMD5(password)).toUpperCase()).equals(user.getPassword())) {
				// 用户和密码正确
				return setSuccessResult(result, user);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}

	private ValidateResult setSuccessResult(ValidateResult result, User user) {
		result.setResultCode(ICAService.RESULT_SUCCESS);
		user.setPassword(null);
		result.setUser(user);
		result.setMessage(null);
		return result;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
