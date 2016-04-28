package com.hsh24.dms.mall.action;

import com.hsh24.dms.api.mall.IOrderService;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderAction extends BaseAction {

	private static final long serialVersionUID = -676612995326260494L;

	private IOrderService orderService;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

}
