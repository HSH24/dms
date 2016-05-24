package com.hsh24.dms.sale.dao;

import java.util.List;

import com.hsh24.dms.api.sale.bo.SaleDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISaleDetailDao {

	/**
	 * 
	 * @param saleDetail
	 * @return
	 */
	List<SaleDetail> getSaleDetailList(SaleDetail saleDetail);

}
