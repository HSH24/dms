package com.hsh24.dms.stock.dao;

import java.util.List;

import com.hsh24.dms.api.stock.bo.Stock;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IStockDao {

	/**
	 * 
	 * @param stock
	 * @return
	 */
	Stock getStats(Stock stock);

	/**
	 * 
	 * @param stock
	 * @return
	 */
	List<Stock> getStockList(Stock stock);

}
