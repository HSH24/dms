package com.hsh24.dms.api.stock;

import java.util.List;

import com.hsh24.dms.api.stock.bo.Stock;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IStockService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	String getStats(Long shopId);

	/**
	 * 
	 * @param shopId
	 * @param stock
	 * @return
	 */
	List<Stock> getStockList(Long shopId, Stock stock);

}
