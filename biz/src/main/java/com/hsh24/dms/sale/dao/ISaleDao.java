package com.hsh24.dms.sale.dao;

import java.util.List;

import com.hsh24.dms.api.sale.bo.Sale;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISaleDao {

	/**
	 * 
	 * @param stock
	 * @return
	 */
	Sale getStats(Sale sale);

	/**
	 * 
	 * @param sale
	 * @return
	 */
	List<Sale> getSaleList(Sale sale);

}
