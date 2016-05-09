package com.hsh24.dms.api.trade;

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

	String CANCEL = "cancel";

	/**
	 * 立即购买.
	 * 
	 * @param userId
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @return
	 */
	BooleanResult createTrade(Long userId, String itemId, String skuId, String quantity);

	/**
	 * 卖家下单交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param cartId
	 *            购物车id.
	 * @return
	 */
	BooleanResult createTrade(Long userId, String[] cartId);

	/**
	 * 卖家查询交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param type
	 * @return
	 */
	int getTradeCount(Long userId, String[] type);

	/**
	 * 卖家查询某店铺交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param type
	 * @return
	 */
	List<Trade> getTradeList(Long userId, String[] type);

	/**
	 * 卖家查看订单.
	 * 
	 * @param userId
	 * @param tradeNo
	 * @return
	 */
	Trade getTrade(Long userId, String tradeNo);

	/**
	 * 取消订单.
	 * 
	 * @param userId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult cancelTrade(Long userId, String tradeNo);

	/**
	 * 获取某一交易某一订单明细信息(用于退款).
	 * 
	 * @param userId
	 * @param tradeNo
	 * @param orderId
	 * @return
	 */
	Trade getOrder(Long userId, String tradeNo, String orderId);

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
	 * @param userId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult signTrade(Long userId, String tradeNo);

}
