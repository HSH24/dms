package com.hsh24.dms.trade.dao.impl;

import com.hsh24.dms.api.trade.bo.OrderRefund;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.trade.dao.IOrderRefundDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderRefundDaoImpl extends BaseDaoImpl implements IOrderRefundDao {

	@Override
	public Long createOrderRefund(OrderRefund orderRefund) {
		return (Long) getSqlMapClientTemplate().insert("trade.order.refund.createOrderRefund", orderRefund);
	}

}
