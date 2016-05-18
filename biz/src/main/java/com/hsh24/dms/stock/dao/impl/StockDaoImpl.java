package com.hsh24.dms.stock.dao.impl;

import com.hsh24.dms.api.stock.bo.Stock;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.stock.dao.IStockDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class StockDaoImpl extends BaseDaoImpl implements IStockDao {

	@Override
	public Stock getStats(Stock stock) {
		return (Stock) getSqlMapClientTemplate().queryForObject("stock.getStats", stock);
	}

}
