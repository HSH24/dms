package com.hsh24.dms.trade.dao;

import com.hsh24.dms.api.trade.bo.OrderRefund;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderRefundDao {

	/**
	 * 
	 * @param orderRefund
	 * @return
	 */
	int createOrderRefund(OrderRefund orderRefund);

}
