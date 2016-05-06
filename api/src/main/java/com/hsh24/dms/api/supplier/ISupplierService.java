package com.hsh24.dms.api.supplier;

import java.util.Map;

import com.hsh24.dms.api.supplier.bo.Supplier;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISupplierService {

	/**
	 * 
	 * @param supId
	 * @return
	 */
	Supplier getSupplier(Long supId);

	/**
	 * 
	 * @param supId
	 * @return
	 */
	Map<Long, Supplier> getSupplier(String[] supId);

}
