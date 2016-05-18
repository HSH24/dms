package com.hsh24.dms.user.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsh24.dms.api.user.IUserAcctService;
import com.hsh24.dms.api.user.IUserService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.annotation.ActionMonitor;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserAcctAction extends BaseAction {

	private static final long serialVersionUID = 8267298257804000897L;

	private IUserAcctService userAcctService;

	private IUserService userService;

	private String passport;

	private String password;

	/**
	 * 验证码.
	 */
	private String checkCode;

	/**
	 * 找回登录密码 发送验证码.
	 * 
	 * @return
	 */
	public String sendCheckCode() {
		BooleanResult result = userAcctService.generateCheckCode(passport);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 验证验证码.
	 * 
	 * @return
	 */
	public String validateCheckCode() {
		BooleanResult result = userAcctService.validateCheckCode(passport, checkCode);

		if (result.getResult()) {
			// 登录成功
			User u = userService.getUserByPassport(passport);

			HttpSession session = this.getSession();
			session.setAttribute("ACEGI_SECURITY_LAST_PASSPORT", u.getPassport());
			session.setAttribute("ACEGI_SECURITY_LAST_LOGINUSER", u);

			HttpServletResponse response = getServletResponse();
			if (response != null) {
				Cookie ps = new Cookie("PS", u.getPassport());
				// ps.setMaxAge(-1);
				ps.setPath("/");
				ps.setDomain(env.getProperty("domain"));
				response.addCookie(ps);
			}
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 修改密码.
	 * 
	 * @return
	 */
	public String setPassword() {
		return SUCCESS;
	}

	@ActionMonitor(actionName = "密码重置")
	public String updatePassword() {
		User user = this.getUser();
		if (user == null) {
			this.getServletResponse().setStatus(599);
			this.setResourceResult("用户信息不存在。");
			return RESULT_MESSAGE;
		}

		BooleanResult result = userAcctService.setPassword(user.getPassport(), password);

		if (result.getResult()) {
			this.setResourceResult("成功修改密码。");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESULT_MESSAGE;
	}

	public IUserAcctService getUserAcctService() {
		return userAcctService;
	}

	public void setUserAcctService(IUserAcctService userAcctService) {
		this.userAcctService = userAcctService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

}
