package com.hsh24.dms.user.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.user.IUserShopService;
import com.hsh24.dms.api.user.bo.User;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserShopAction extends BaseAction {

	private static final long serialVersionUID = -8988118077874200983L;

	private IUserShopService userShopService;

	private List<Shop> shopList;

	private String shopId;

	/**
	 * 
	 * @return
	 */
	public String index() {
		User user = this.getUser();

		if (user == null) {
			return "shop";
		}

		// 判断是否关联多个店铺
		shopList = userShopService.getShopList(user.getUserId());

		if (shopList == null || shopList.size() == 0) {
			return "shop";
		}

		if (shopList.size() == 1) {
			Shop shop = shopList.get(0);

			HttpSession session = this.getSession();
			session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shop);

			return SUCCESS;
		}

		return "shop";
	}

	/**
	 * 
	 * @return
	 */
	public String select() {
		User user = this.getUser();

		if (user == null) {
			return ERROR;
		}

		// 验证选择店铺是否属于当前操作人
		Shop shop = userShopService.getShop(user.getUserId(), shopId);

		if (shop == null) {
			return ERROR;
		}

		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shop);

		return SUCCESS;
	}

	public IUserShopService getUserShopService() {
		return userShopService;
	}

	public void setUserShopService(IUserShopService userShopService) {
		this.userShopService = userShopService;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
