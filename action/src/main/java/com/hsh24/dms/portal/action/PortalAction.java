package com.hsh24.dms.portal.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.api.weixin.IWeixinService;
import com.hsh24.dms.api.weixin.bo.Ticket;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2191525146456353749L;

	@Resource
	private IWeixinService weixinService;

	private Ticket ticket;

	/**
	 * 登录首页.
	 * 
	 * @return
	 */
	public String index() {
		User user = this.getUser();
		if (user != null) {
			return NONE;
		}

		return SUCCESS;
	}

	public String home() {
		String requestURL = env.getProperty("appUrl") + "/home.htm";
		HttpServletRequest request = getServletRequest();
		String queryString = request.getQueryString();

		ticket =
			weixinService
				.getTicket4Corp(StringUtils.isEmpty(queryString) ? requestURL : requestURL + "?" + queryString);

		return SUCCESS;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
