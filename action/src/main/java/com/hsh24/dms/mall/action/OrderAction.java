package com.hsh24.dms.mall.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.framework.action.BaseAction;

/**
 * 消费者订单推送亭主.
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class OrderAction extends BaseAction {

	private static final long serialVersionUID = -676612995326260494L;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		return SUCCESS;
	}

}
