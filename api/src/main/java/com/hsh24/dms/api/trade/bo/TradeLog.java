package com.hsh24.dms.api.trade.bo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeLog {

	private Long id;

	/**
	 * 交易ID.
	 */
	private Long tradeId;

	/**
	 * tosend: 待发货; send: 已发货; sign: 标记签收; cancel: 已关闭; feedback: 维权订单; feedbacked: 已处理维权订单.
	 */
	private String type;

	private String modifyUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
