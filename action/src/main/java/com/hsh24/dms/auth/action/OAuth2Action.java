package com.hsh24.dms.auth.action;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.api.auth.IAuthService;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller("oauth2Action")
@Scope("request")
public class OAuth2Action extends BaseAction {

	private static final long serialVersionUID = 6386474612475679175L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(OAuth2Action.class);

	@Resource
	private IAuthService authService;

	private String redirectUrl;

	public String authorize() {
		BooleanResult result = null;

		try {
			result =
				authService.authorize(URLEncoder.encode(env.getProperty("appUrl") + "/auth/redirect.htm", "UTF-8"));
		} catch (Exception e) {
			logger.error(e);
			return ERROR;
		}

		if (result.getResult()) {
			redirectUrl = result.getCode();

			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String redirect() {
		// 用户唯一标识
		String openId = authService.getOpenId(this.getCode());

		if (StringUtils.isEmpty(openId)) {
			return ERROR;
		}

		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_OPEN_ID", openId);

		return SUCCESS;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
