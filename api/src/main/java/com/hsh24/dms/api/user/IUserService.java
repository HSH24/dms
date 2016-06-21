package com.hsh24.dms.api.user;

import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserService {

	String SUCCESS = "success";

	String ERROR = "error";

	String ERROR_MESSAGE = "操作失败";

	String ERROR_INPUT_MESSAGE = "操作失败，输入有误";

	String ERROR_NULL_MESSAGE = "操作失败，不存在";

	String ERROR_EXIST_MESSAGE = "操作失败，已存在";

	/**
	 * 根据登陆帐号获取用户信息(存在缓存).
	 * 
	 * @param passport
	 * @return
	 */
	User getUser(String passport);

	/**
	 * 根据登陆帐号获取用户信息(不存在缓存).
	 * 
	 * @param passport
	 * @return
	 */
	User getUser4Validate(String passport);

	/**
	 * 
	 * @param passport
	 * @param password
	 * @param modifyUser
	 * @return
	 */
	BooleanResult setPassword(String passport, String password, String modifyUser);

	/**
	 * 
	 * @param passport
	 * @param userName
	 * @param modifyUser
	 * @return
	 */
	BooleanResult setUserName(String passport, String userName, String modifyUser);

}
