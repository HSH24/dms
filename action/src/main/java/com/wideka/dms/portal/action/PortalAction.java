package com.wideka.dms.portal.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.wideka.dms.api.weixin.IWeixinService;
import com.wideka.dms.api.weixin.bo.Ticket;
import com.wideka.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2191525146456353749L;

	private IWeixinService weixinService;

	private Ticket ticket;

	/**
	 * 登录首页.
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

	public String home() {
		String requestURL = env.getProperty("appUrl") + "/homepage.htm";
		HttpServletRequest request = getServletRequest();
		String queryString = request.getQueryString();

		ticket =
			weixinService.getTicket(StringUtils.isEmpty(queryString) ? requestURL : requestURL + "?" + queryString);

		return SUCCESS;
	}

	public IWeixinService getWeixinService() {
		return weixinService;
	}

	public void setWeixinService(IWeixinService weixinService) {
		this.weixinService = weixinService;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
