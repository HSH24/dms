package com.hsh24.dms.api.receipt.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDetail implements Serializable {

	private static final long serialVersionUID = -2044548908163290158L;

	private Long detailId;

	private Long receiptId;

	private Long orderId;

	/**
	 * 收货数量.
	 */
	private int quantity;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
