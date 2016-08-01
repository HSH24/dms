package com.hsh24.dms.receipt.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ReceiptAction extends BaseAction {

	private static final long serialVersionUID = -4442075499888188764L;

	@Resource
	private IReceiptService receiptService;

	private String tradeNo;

	private List<ReceiptDetail> receiptDetailList;

	/**
	 * 
	 * @return
	 */
	public String part() {
		BooleanResult result =
			receiptService.part(this.getShop().getShopId(), tradeNo, receiptDetailList, this.getUser().getUserId()
				.toString());

		if (result.getResult()) {
			this.setResourceResult("创建收货单成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String all() {
		BooleanResult result =
			receiptService.all(this.getShop().getShopId(), tradeNo, this.getUser().getUserId().toString());

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public List<ReceiptDetail> getReceiptDetailList() {
		return receiptDetailList;
	}

	public void setReceiptDetailList(List<ReceiptDetail> receiptDetailList) {
		this.receiptDetailList = receiptDetailList;
	}

}
