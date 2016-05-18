package com.hsh24.dms.user.dao;

import com.hsh24.dms.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserDao {

	/**
	 * 
	 * @param passport
	 * @return
	 */
	User getUserByPassport(String passport);

	/**
	 * 
	 * @param user
	 * @return
	 */
	int updateUser(User user);

}
