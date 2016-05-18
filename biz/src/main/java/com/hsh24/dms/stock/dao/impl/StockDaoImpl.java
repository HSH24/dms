package com.hsh24.dms.stock.dao.impl;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> getStockList(Stock stock) {
		return (List<Stock>) getSqlMapClientTemplate().queryForList("stock.getStockList", stock);
	}

}
