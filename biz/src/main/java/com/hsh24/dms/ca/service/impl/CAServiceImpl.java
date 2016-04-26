package com.hsh24.dms.ca.service.impl;

import com.hsh24.dms.api.ca.ICAService;
import com.hsh24.dms.api.ca.bo.ValidateResult;
import com.hsh24.dms.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CAServiceImpl implements ICAService {

	@Override
	public ValidateResult validateUser(String passport, String password) {
		ValidateResult result = new ValidateResult();

		result.setResultCode(ICAService.RESULT_SUCCESS);

		User user = new User();
		user.setUserId("oTT3bsrzIeC8zTk7m9LJpnEFqzl0");
		user.setPassport("oTT3bsrzIeC8zTk7m9LJpnEFqzl0");
		result.setUser(user);

		return result;
	}

}
