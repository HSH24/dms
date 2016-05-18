package com.hsh24.dms.user.dao.impl;

import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.user.dao.IUserDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {

	@Override
	public User getUserByPassport(String passport) {
		return (User) getSqlMapClientTemplate().queryForObject("user.getUserByPassport", passport);
	}

	@Override
	public int updateUser(User user) {
		return getSqlMapClientTemplate().update("user.updateUser", user);
	}

}
