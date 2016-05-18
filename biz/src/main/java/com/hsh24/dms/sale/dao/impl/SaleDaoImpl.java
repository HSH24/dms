package com.hsh24.dms.sale.dao.impl;

import com.hsh24.dms.api.sale.bo.Sale;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.sale.dao.ISaleDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleDaoImpl extends BaseDaoImpl implements ISaleDao {

	@Override
	public Sale getStats(Sale sale) {
		return (Sale) getSqlMapClientTemplate().queryForObject("sale.getStats", sale);
	}

}
