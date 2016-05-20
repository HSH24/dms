package com.hsh24.dms.api.trade;

import java.math.BigDecimal;
import java.util.List;

import com.hsh24.dms.api.trade.bo.OrderRefund;
import com.hsh24.dms.api.trade.bo.Trade;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 交易接口.
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeService {

	String CREATE = "create";

	/**
	 * 已下单.
	 */
	String TO_SEND = "tosend";

	/**
	 * 已发货.
	 */
	String SEND = "send";

	/**
	 * 已签收.
	 */
	String SIGN = "sign";

	String FEEDBACK = "feedback";

	String FEEDBACKED = "feedbacked";

	/**
	 * 申请退款.
	 */
	String TO_REFUND = "torefund";

	/**
	 * 已退款.
	 */
	String REFUND = "refund";

	/**
	 * 取消中.
	 */
	String TO_CANCEL = "tocancel";

	String CANCEL = "cancel";

	/**
	 * 立即购买.
	 * 
	 * @param shopId
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createTrade(Long shopId, String itemId, String skuId, String quantity, String modifyUser);

	/**
	 * 卖家下单交易.
	 * 
	 * @param shopId
	 *            必填.
	 * @param userId
	 * @param cartId
	 *            购物车id.
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createTrade(Long shopId, Long userId, String[] cartId, String modifyUser);

	/**
	 * 获取交易合计金额.
	 * 
	 * @param shopId
	 * @param type
	 * @return
	 */
	BigDecimal getTradePrice(Long shopId, String[] type);

	/**
	 * 卖家查询交易.
	 * 
	 * @param shopId
	 *            必填.
	 * @param type
	 * @return
	 */
	int getTradeCount(Long shopId, String[] type);

	/**
	 * 卖家查询某店铺交易.
	 * 
	 * @param shopId
	 *            必填.
	 * @param type
	 * @return
	 */
	List<Trade> getTradeList(Long shopId, String[] type);

	/**
	 * 卖家查看订单.
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	Trade getTrade(Long shopId, String tradeNo);

	/**
	 * 取消订单.
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @param modifyUser
	 * @return
	 */
	BooleanResult cancelTrade(Long shopId, String tradeNo, String modifyUser);

	/**
	 * 获取某一交易某一订单明细信息(用于退款).
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @param orderId
	 * @return
	 */
	Trade getOrder(Long shopId, String tradeNo, String orderId);

	/**
	 * 退款申请.
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @param refundNo
	 * @param orderId
	 * @param orderRefund
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrderRefund(Long shopId, String tradeNo, String refundNo, Long orderId,
		OrderRefund orderRefund, String modifyUser);

	/**
	 * 确认收货.
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @param modifyUser
	 * @return
	 */
	BooleanResult signTrade(Long shopId, String tradeNo, String modifyUser);

}
