package com.hsh24.dms.api.trade.bo;

import java.math.BigDecimal;
import java.util.List;

import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 交易.
 * 
 * @author JiakunXu
 * 
 */
public class Trade extends SearchInfo {

	private static final long serialVersionUID = -1761794880999676421L;

	/**
	 * 交易ID.
	 */
	private Long tradeId;

	/**
	 * 店铺ID.
	 */
	private Long shopId;

	/**
	 * 供应商ID.
	 */
	private Long supId;

	/**
	 * 交易价格（不含折扣）.
	 */
	private BigDecimal tradePrice;

	/**
	 * 涨价或减价.
	 */
	private BigDecimal change;

	/**
	 * 星星级别，店小儿分类交易.
	 */
	private int score;

	/**
	 * 备注，店小儿分类交易.
	 */
	private String remark;

	/**
	 * tosend: 待发货; send: 已发货; sign: 标记签收; cancel: 已关闭; feedback: 维权订单; feedbacked: 已处理维权订单.
	 */
	private String type;

	/**
	 * 交易订单号.
	 */
	private String tradeNo;

	/**
	 * 状态 D:删除 U:正常.
	 */
	private String state;

	/**
	 * 下单时间.
	 */
	private String createDate;

	/**
	 * 订单最后修改时间.
	 */
	private String modifyDate;

	/**
	 * 买家付款时间.
	 */
	private String payDate;

	/**
	 * 卖家发货时间.
	 */
	private String sendDate;

	/**
	 * 物流标记签收时间.
	 */
	private String signDate;

	/**
	 * 维权时订单类型.
	 */
	private String feedbackType;

	/**
	 * 买家维权时间.
	 */
	private String feedbackDate;

	/**
	 * 卖家处理维权时间.
	 */
	private String feedbackedDate;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	/**
	 * 购物车(存在多个id: 1,2,3,4,5).
	 */
	private String cartId;

	/**
	 * 支付方式.
	 */
	private String payType;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 供应商名称.
	 */
	private String supName;

	/**
	 * 订单行项目.
	 */
	private List<Order> orderList;

	/**
	 * 订单物流信息.
	 */
	private List<TradeExpress> tradeExpressList;

	/**
	 * 统计维度 日期 2014-07-16.
	 */
	private String dates;

	/**
	 * 统计值.
	 */
	private int count;

	/**
	 * 统计值 下单数量.
	 */
	private int countOfCreate;

	/**
	 * 统计值 发货数量.
	 */
	private int countOfSend;

	/**
	 * 统计值 签收数量.
	 */
	private int countOfSign;

	/**
	 * 统计值 取消数量.
	 */
	private int countOfCancel;

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	/**
	 * 实付金额 tradePrice + (change).
	 * 
	 * @return
	 */
	public BigDecimal getPrice() {
		if (this.tradePrice != null) {
			return this.tradePrice.add(this.change);
		}

		return BigDecimal.ZERO;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackedDate() {
		return feedbackedDate;
	}

	public void setFeedbackedDate(String feedbackedDate) {
		this.feedbackedDate = feedbackedDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<TradeExpress> getTradeExpressList() {
		return tradeExpressList;
	}

	public void setTradeExpressList(List<TradeExpress> tradeExpressList) {
		this.tradeExpressList = tradeExpressList;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCountOfCreate() {
		return countOfCreate;
	}

	public void setCountOfCreate(int countOfCreate) {
		this.countOfCreate = countOfCreate;
	}

	public int getCountOfSend() {
		return countOfSend;
	}

	public void setCountOfSend(int countOfSend) {
		this.countOfSend = countOfSend;
	}

	public int getCountOfSign() {
		return countOfSign;
	}

	public void setCountOfSign(int countOfSign) {
		this.countOfSign = countOfSign;
	}

	public int getCountOfCancel() {
		return countOfCancel;
	}

	public void setCountOfCancel(int countOfCancel) {
		this.countOfCancel = countOfCancel;
	}

}
