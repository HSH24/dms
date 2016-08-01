package com.hsh24.dms.supplier.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class SupplierAction extends BaseAction {

	private static final long serialVersionUID = -3517081429091032617L;

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
