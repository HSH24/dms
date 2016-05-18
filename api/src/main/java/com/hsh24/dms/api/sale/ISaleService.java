package com.hsh24.dms.api.sale;

import com.hsh24.dms.api.sale.bo.Sale;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISaleService {

	/**
	 * 
	 * @param shopId
	 * @param sale
	 * @return
	 */
	Sale getStats(Long shopId, Sale sale);

}
