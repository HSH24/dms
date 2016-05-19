package com.hsh24.dms.api.user;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserAcctService {

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
	 * 验证密码.
	 * 
	 * @param passport
	 * @param password
	 * @return
	 */
	BooleanResult validatePassword(String passport, String password);

	/**
	 * 忘记密码.
	 * 
	 * @param passport
	 * @param password
	 * @return
	 */
	BooleanResult setPassword(String passport, String password);

}
