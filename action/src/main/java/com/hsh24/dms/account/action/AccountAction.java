package com.hsh24.dms.account.action;

import com.hsh24.dms.api.account.IAccountService;
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
public class AccountAction extends BaseAction {

	private static final long serialVersionUID = 8267298257804000897L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(AccountAction.class);

	private IAccountService accountService;

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
		BooleanResult result = accountService.generateCheckCode(passport);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String validateCheckCode() {
		return SUCCESS;
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
		BooleanResult result = accountService.setPassword(checkCode, password);

		if (result.getResult()) {
			this.setSuccessMessage("成功修改密码！");
		} else {
			this.setFailMessage(result.getCode());
		}

		return RESULT_MESSAGE;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
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