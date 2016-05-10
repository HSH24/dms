package com.hsh24.dms.login.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsh24.dms.api.ca.ICAService;
import com.hsh24.dms.api.ca.bo.ValidateResult;
import com.hsh24.dms.api.cache.IMemcachedCacheService;
import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.user.IUserShopService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.annotation.ActionMonitor;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;

/**
 * 登录模块.
 * 
 * @author JiakunXu
 * 
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 7498561926934442624L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(LoginAction.class);

	private IMemcachedCacheService memcachedCacheService;

	private ICAService caService;

	private IUserShopService userShopService;

	private String passport;

	private String password;

	private List<Shop> shopList;

	/**
	 * 登录验证.
	 * 
	 * @return
	 */
	@ActionMonitor(actionName = "系统登录")
	public String login() {
		ValidateResult result = caService.validateUser(passport, password);

		// 验证失败
		if (ICAService.RESULT_FAILED.equals(result.getResultCode())
			|| ICAService.RESULT_ERROR.equals(result.getResultCode())) {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getMessage());

			return RESOURCE_RESULT;
		}

		// 验证通过
		User u = result.getUser();

		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_PASSPORT", u.getPassport());
		session.setAttribute("ACEGI_SECURITY_LAST_LOGINUSER", u);

		HttpServletResponse response = getServletResponse();
		if (response != null) {
			Cookie ps = new Cookie("PS", u.getPassport());
			// ps.setMaxAge(-1);
			ps.setPath("/");
			ps.setDomain(env.getProperty("domain"));
			response.addCookie(ps);
		}

		// 判断是否关联多个店铺
		shopList = userShopService.getShopList(u.getUserId());

		if (shopList == null || shopList.size() == 0) {
			this.getServletResponse().setStatus(599);
			this.setResourceResult("当前账号没有关联店铺。");

			return RESOURCE_RESULT;
		}

		if (shopList.size() == 1) {
			session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shopList.get(0).getShopId());

			this.setResourceResult(env.getProperty("appUrl") + "/home.htm");
		} else {
			this.setResourceResult("");
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 退出系统.
	 * 
	 * @return
	 */
	@ActionMonitor(actionName = "系统退出")
	public String logout() {
		HttpSession session = this.getSession();

		// 清空cache中session信息
		try {
			memcachedCacheService.remove(session.getId());
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			// login
			session.removeAttribute("ACEGI_SECURITY_LAST_PASSPORT");
			session.removeAttribute("ACEGI_SECURITY_LAST_LOGINUSER");

			session.invalidate();
		} catch (Exception e) {
			logger.error(e);
		}

		HttpServletResponse response = getServletResponse();
		if (response != null) {
			Cookie j = new Cookie("JSESSIONID", null);
			j.setMaxAge(0);
			j.setPath("/");
			response.addCookie(j);
		}

		return LOGOUT;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public ICAService getCaService() {
		return caService;
	}

	public void setCaService(ICAService caService) {
		this.caService = caService;
	}

	public IUserShopService getUserShopService() {
		return userShopService;
	}

	public void setUserShopService(IUserShopService userShopService) {
		this.userShopService = userShopService;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
