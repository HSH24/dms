package com.hsh24.dms.api.weixin;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITicketService {

	/**
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	BooleanResult getTicket(String appId, String appSecret);

	/**
	 * 
	 * @param corpId
	 * @param corpSecret
	 * @return
	 */
	BooleanResult getTicket4Corp(String corpId, String corpSecret);

}
