package com.hsh24.dms.trade.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.receipt.IReceiptService;
import com.hsh24.dms.api.receipt.bo.Receipt;
import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.api.trade.ITradeService;
import com.hsh24.dms.api.trade.bo.Order;
import com.hsh24.dms.api.trade.bo.Trade;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.util.FormatUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeAction extends BaseAction {

	private static final long serialVersionUID = -912767004509511731L;

	private ITradeService tradeService;

	private IReceiptService receiptService;

	private String itemId;

	private String skuId;

	private String quantity;

	/**
	 * 购物车.
	 */
	private String[] cartIds;

	/**
	 * 交易编号(第三方支付).
	 */
	private String tradeNo;

	/**
	 * 订单类型.
	 */
	private String type;

	private List<Trade> tradeList;

	private Trade trade;

	private List<Receipt> receiptList;

	/**
	 * 订单明细编号.
	 */
	private String orderId;

	/**
	 * 创建订单.
	 * 
	 * @return
	 */
	public String create() {
		BooleanResult result = null;

		String passport = this.getUser().getPassport();

		// 直接购买
		if (cartIds == null || cartIds.length == 0) {
			result = tradeService.createTrade(this.getShop().getShopId(), itemId, skuId, quantity, passport);
		} else {
			result =
				tradeService.createTrade(this.getShop().getShopId(), this.getUser().getUserId(), cartIds, passport);
		}

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 订单数据统计.
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Long shopId = this.getShop().getShopId();

		if ("tosend".equals(type)) {
			sb.append(FormatUtil.getAmountFormat(tradeService.getTradePrice(shopId, new String[] { "tosend" })))
				.append("&");
			sb.append(tradeService.getTradeCount(shopId, new String[] { "tosend" }));

			this.setResourceResult(sb.toString());
		} else if ("send".equals(type)) {
			sb.append(FormatUtil.getAmountFormat(tradeService.getTradePrice(shopId, new String[] { "send" }))).append(
				"&");
			sb.append(tradeService.getTradeCount(shopId, new String[] { "send" }));

			this.setResourceResult(sb.toString());
		} else {
			sb.append(tradeService.getTradeCount(shopId, new String[] { "check", "topay" })).append("&");
			sb.append(tradeService.getTradeCount(shopId, new String[] { "tosend" })).append("&");
			sb.append(tradeService.getTradeCount(shopId, new String[] { "send" })).append("&");
			sb.append(tradeService.getTradeCount(shopId, new String[] { "sign" }));

			this.setResourceResult(sb.toString());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 订单列表.
	 * 
	 * @return
	 */
	public String list() {
		Long shopId = this.getShop().getShopId();

		// 待付款
		if ("topay".equals(type)) {
			tradeList = tradeService.getTradeList(shopId, new String[] { "check", "topay" });
		} else if ("tosend".equals(type)) {
			tradeList = tradeService.getTradeList(shopId, new String[] { "tosend" });
		} else if ("send".equals(type)) {
			tradeList = tradeService.getTradeList(shopId, new String[] { "send" });
		} else if ("sign".equals(type)) {
			tradeList = tradeService.getTradeList(shopId, new String[] { "sign" });
		} else {
			tradeList = tradeService.getTradeList(shopId, null);
		}

		return SUCCESS;
	}

	/**
	 * 取消尚未付款的订单.
	 * 
	 * @return
	 */
	public String cancel() {
		BooleanResult result =
			tradeService.cancelTrade(this.getShop().getShopId(), tradeNo, this.getUser().getUserId().toString());

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
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
	public String detail() {
		Long shopId = this.getShop().getShopId();

		trade = tradeService.getTrade(shopId, tradeNo);

		if (trade != null) {
			receiptList = receiptService.getReceiptList(shopId, trade.getTradeId());
		}

		if (receiptList == null || receiptList.size() == 0) {
			return SUCCESS;
		}

		Map<Long, Integer> map = new HashMap<Long, Integer>();

		for (Receipt receipt : receiptList) {
			List<ReceiptDetail> receiptDetailList = receipt.getReceiptDetailList();
			if (receiptDetailList == null || receiptDetailList.size() == 0) {
				continue;
			}

			for (ReceiptDetail receiptDetail : receiptDetailList) {
				Long orderId = receiptDetail.getOrderId();
				int quantity = receiptDetail.getQuantity();
				if (map.containsKey(orderId)) {
					map.put(orderId, map.get(orderId) + quantity);
				} else {
					map.put(orderId, quantity);
				}
			}
		}

		if (map.size() == 0) {
			return SUCCESS;
		}

		for (Order order : trade.getOrderList()) {
			Long orderId = order.getOrderId();
			if (map.containsKey(orderId)) {
				order.setReceiptedQuantity(map.get(orderId));
			}
		}

		return SUCCESS;
	}

	/**
	 * 申请退款.
	 * 
	 * @return
	 */
	public String refund() {
		trade = tradeService.getOrder(this.getShop().getShopId(), tradeNo, orderId);

		return SUCCESS;
	}

	public ITradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public IReceiptService getReceiptService() {
		return receiptService;
	}

	public void setReceiptService(IReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String[] getCartIds() {
		return cartIds;
	}

	public void setCartIds(String[] cartIds) {
		this.cartIds = cartIds;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public List<Receipt> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<Receipt> receiptList) {
		this.receiptList = receiptList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
