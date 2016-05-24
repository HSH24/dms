package com.hsh24.dms.weixin.service.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.weixin.ITicketService;
import com.hsh24.dms.api.weixin.IWeixinService;
import com.hsh24.dms.api.weixin.bo.Ticket;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.UUIDUtil;
import com.wideka.weixin.framework.util.EncryptUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WeixinServiceImpl implements IWeixinService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(WeixinServiceImpl.class);

	private ITicketService ticketService;

	private String appId;

	private String appSecret;

	private String corpId;

	private String corpSecret;

	@Override
	public Ticket getTicket(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		BooleanResult result = ticketService.getTicket(appId, appSecret);
		if (!result.getResult()) {
			return null;
		}

		String t = result.getCode();
		String nonceStr = UUIDUtil.generate();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String signature;

		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(t).append("&noncestr=").append(nonceStr).append("&timestamp=")
			.append(timestamp).append("&url=").append(url.trim());

		try {
			signature = EncryptUtil.encryptSHA(sb.toString());
		} catch (IOException e) {
			logger.error(e);
			return null;
		}

		Ticket ticket = new Ticket();
		ticket.setAppId(appId);
		ticket.setNonceStr(nonceStr);
		ticket.setTimestamp(timestamp);
		ticket.setSignature(signature);

		return ticket;
	}

	@Override
	public Ticket getTicket4Corp(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		BooleanResult result = ticketService.getTicket4Corp(corpId, corpSecret);
		if (!result.getResult()) {
			return null;
		}

		String t = result.getCode();
		String nonceStr = UUIDUtil.generate();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String signature;

		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(t).append("&noncestr=").append(nonceStr).append("&timestamp=")
			.append(timestamp).append("&url=").append(url.trim());

		try {
			signature = EncryptUtil.encryptSHA(sb.toString());
		} catch (IOException e) {
			logger.error(e);
			return null;
		}

		Ticket ticket = new Ticket();
		ticket.setAppId(corpId);
		ticket.setNonceStr(nonceStr);
		ticket.setTimestamp(timestamp);
		ticket.setSignature(signature);

		return ticket;
	}

	public ITicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(ITicketService ticketService) {
		this.ticketService = ticketService;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpSecret() {
		return corpSecret;
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

}
