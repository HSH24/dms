package com.hsh24.dms.receipt.action;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptAction extends BaseAction {

	private static final long serialVersionUID = -4442075499888188764L;

	private IReceiptService receiptService;

	/**
	 * 
	 * @return
	 */
	public String part() {
		BooleanResult result = new BooleanResult();

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public IReceiptService getReceiptService() {
		return receiptService;
	}

	public void setReceiptService(IReceiptService receiptService) {
		this.receiptService = receiptService;
	}

}
