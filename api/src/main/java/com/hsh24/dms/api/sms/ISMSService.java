package com.hsh24.dms.api.sms;

import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISMSService {

	/**
	 * 发送短信.
	 * 
	 * @param signName
	 *            短信签名.
	 * @param templateCode
	 *            短信模板ID.
	 * @param param
	 *            短信模板变量.
	 * @param recNum
	 *            短信接收号码.
	 * @param extend
	 *            公共回传参数.
	 */
	BooleanResult send(String signName, String templateCode, String param, String recNum, String extend);

}
