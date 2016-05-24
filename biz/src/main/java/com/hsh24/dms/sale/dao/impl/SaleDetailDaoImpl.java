package com.hsh24.dms.sale.dao.impl;

import java.util.List;

import com.hsh24.dms.api.sale.bo.SaleDetail;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.sale.dao.ISaleDetailDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleDetailDaoImpl extends BaseDaoImpl implements ISaleDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDetail> getSaleDetailList(SaleDetail saleDetail) {
		return (List<SaleDetail>) getSqlMapClientTemplate().queryForList("sale.detail.getSaleDetailList", saleDetail);
	}

}
