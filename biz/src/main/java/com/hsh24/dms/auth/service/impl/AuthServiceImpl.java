package com.hsh24.dms.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hsh24.dms.api.auth.IAuthService;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.wideka.weixin.api.auth.IOAuth2Service;
import com.wideka.weixin.api.auth.bo.AccessToken;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class AuthServiceImpl implements IAuthService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(AuthServiceImpl.class);

	@Resource
	private IOAuth2Service oauth2Service;

	@Value("${weixin.app.id}")
	private String appId;

	@Value("${weixin.app.secret}")
	private String appSecret;

	@Override
	public BooleanResult authorize(String redirectUrl) {
		return authorize(redirectUrl, null);
	}

	@Override
	public BooleanResult authorize(String redirectUrl, String state) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(redirectUrl)) {
			result.setCode("redirectUrl 不能为空.");
			return result;
		}

		try {
			result.setCode(oauth2Service.authorize(appId, redirectUrl, "snsapi_base", state));
			result.setResult(true);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode(e.getMessage());
		}

		return result;
	}

	@Override
	public String getOpenId(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		AccessToken accessToken = null;

		try {
			accessToken = oauth2Service.accessToken(appId, appSecret, code);
		} catch (ServiceException e) {
			logger.error(e);
		}

		if (accessToken == null) {
			return null;
		}

		return accessToken.getOpenId();
	}

}
