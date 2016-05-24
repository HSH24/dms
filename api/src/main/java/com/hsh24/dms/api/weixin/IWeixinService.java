package com.hsh24.dms.api.weixin;

import com.hsh24.dms.api.weixin.bo.Ticket;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IWeixinService {

	/**
	 * 
	 * @param url
	 * @return
	 */
	Ticket getTicket(String url);

	/**
	 * 
	 * @param url
	 * @return
	 */
	Ticket getTicket4Corp(String url);

}
