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
	 * @param supId
	 * @param tradeId
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrder(Long supId, Long tradeId, String itemId, String skuId, String quantity, String modifyUser);

	/**
	 * 根据购物车批量创建订单明细信息.
	 * 
	 * @param supId
	 * @param tradeId
	 * @param cartId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrder(Long supId, Long tradeId, String[] cartId, String modifyUser);

	/**
	 * 卖家查询(首先调用 ITradeService.getTrade).
	 * 
	 * @param shopId
	 * @param tradeId
	 * @return
	 */
	List<Order> getOrderList(Long shopId, Long tradeId);

	/**
	 * 
	 * @param shopId
	 * @param tradeId
	 * @param orderId
	 * @return
	 */
	Order getOrder(Long shopId, Long tradeId, Long orderId);

}
