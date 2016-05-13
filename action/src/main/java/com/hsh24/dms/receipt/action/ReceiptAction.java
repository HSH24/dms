package com.hsh24.dms.receipt.action;

import java.util.List;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
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

	private String tradeId;

	private List<ReceiptDetail> receiptDetailList;

	/**
	 * 
	 * @return
	 */
	public String part() {
		BooleanResult result =
			receiptService.part(this.getShop().getShopId(), tradeId, receiptDetailList, this.getUser().getUserId()
				.toString());

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

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public List<ReceiptDetail> getReceiptDetailList() {
		return receiptDetailList;
	}

	public void setReceiptDetailList(List<ReceiptDetail> receiptDetailList) {
		this.receiptDetailList = receiptDetailList;
	}

}
