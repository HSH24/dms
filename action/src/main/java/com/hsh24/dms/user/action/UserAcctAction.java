package com.hsh24.dms.user.action;

import com.hsh24.dms.api.user.IUserAcctService;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.annotation.ActionMonitor;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserAcctAction extends BaseAction {

	private static final long serialVersionUID = 8267298257804000897L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserAcctAction.class);

	private IUserAcctService userAccService;

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
		BooleanResult result = userAccService.generateCheckCode(passport);

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
		BooleanResult result = userAccService.validateCheckCode(passport, checkCode);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
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
		BooleanResult result = userAccService.setPassword(checkCode, password);

		if (result.getResult()) {
			this.setSuccessMessage("成功修改密码！");
		} else {
			this.setFailMessage(result.getCode());
		}

		return RESULT_MESSAGE;
	}

	public IUserAcctService getUserAccService() {
		return userAccService;
	}

	public void setUserAccService(IUserAcctService userAccService) {
		this.userAccService = userAccService;
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
