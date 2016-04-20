package com.hsh24.dms.api.trade;

import java.util.List;

import com.hsh24.dms.api.trade.bo.Order;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 订单详细信息接口.
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderService {

	/**
	 * 创建订单明细信息.
	 * 
	 * @param shopId
	 * @param tradeId
	 * @param itemId
	 * @param skuId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrder(Long shopId, Long tradeId, String itemId, String skuId, String modifyUser);

	/**
	 * 根据购物车批量创建订单明细信息.
	 * 
	 * @param shopId
	 * @param tradeId
	 * @param cartId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrder(Long shopId, Long tradeId, String[] cartId, String modifyUser);

	/**
	 * 买家查询(首先调用 ITradeService.getTrade).
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeId
	 * @return
	 */
	List<Order> getOrderList(String userId, Long shopId, Long tradeId);

	/**
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeId
	 * @param orderId
	 * @return
	 */
	Order getOrder(String userId, Long shopId, Long tradeId, Long orderId);

}
