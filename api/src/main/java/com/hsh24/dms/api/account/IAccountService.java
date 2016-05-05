package com.hsh24.dms.api.account;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IAccountService {

	/**
	 * 验证码找回密码.
	 * 
	 * @param passport
	 * @return
	 */
	BooleanResult generateCheckCode(String passport);

	/**
	 * 验证验证码.
	 * 
	 * @param passport
	 * @param checkCode
	 * @return
	 */
	BooleanResult validateCheckCode(String passport, String checkCode);

	/**
	 * 忘记密码.
	 * 
	 * @param checkCode
	 * @param password
	 * @return
	 */
	BooleanResult setPassword(String checkCode, String password);

}
