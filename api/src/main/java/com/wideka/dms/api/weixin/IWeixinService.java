package com.wideka.dms.api.weixin;

import com.wideka.dms.api.weixin.bo.Ticket;

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

}
