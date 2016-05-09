package com.hsh24.dms.api.receipt.bo;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Receipt extends SearchInfo {

	private static final long serialVersionUID = -9088769504838934594L;

	private Long receiptId;

	private Long tradeId;

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

}
