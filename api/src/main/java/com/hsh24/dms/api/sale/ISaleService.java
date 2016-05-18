package com.hsh24.dms.api.sale;

import java.util.List;

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

	/**
	 * 
	 * @param shopId
	 * @param sale
	 * @return
	 */
	List<Sale> getSaleList(Long shopId, Sale sale);

}
