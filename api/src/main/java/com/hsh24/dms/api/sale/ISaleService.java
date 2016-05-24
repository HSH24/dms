package com.hsh24.dms.api.sale;

import java.util.List;

import com.hsh24.dms.api.sale.bo.Sale;
import com.hsh24.dms.api.sale.bo.SaleDetail;

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

	/**
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	List<SaleDetail> getSaleDetailList(Long shopId, String tradeNo);

}
