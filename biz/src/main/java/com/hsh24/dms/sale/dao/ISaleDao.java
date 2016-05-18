package com.hsh24.dms.sale.dao;

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

}
