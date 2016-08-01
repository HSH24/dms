package com.hsh24.dms.user.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
@Controller
@Scope("request")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 7825329647617901909L;

	@Resource
	private IUserService userService;

	private String userName;

	/**
	 * 
	 * @return
	 */
	public String detail() {
		return SUCCESS;
	}

	public String setUserName() {
		return SUCCESS;
	}

	@ActionMonitor(actionName = "名字设置")
	public String updateUserName() {
		User user = this.getUser();

		BooleanResult result = userService.setUserName(user.getPassport(), userName, user.getUserId().toString());

		if (result.getResult()) {
			user.setUserName(userName.trim());

			HttpSession session = this.getSession();
			session.setAttribute("ACEGI_SECURITY_LAST_LOGINUSER", user);

			this.setResourceResult("设置名字成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
