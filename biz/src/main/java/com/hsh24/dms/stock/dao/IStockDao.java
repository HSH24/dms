package com.hsh24.dms.stock.dao;

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

}
