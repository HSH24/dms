package com.hsh24.dms.supplier.dao;

import java.util.List;

import com.hsh24.dms.api.supplier.bo.Supplier;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISupplierDao {

	/**
	 * 
	 * @param supplier
	 * @return
	 */
	Supplier getSupplier(Supplier supplier);

	/**
	 * 
	 * @param supplier
	 * @return
	 */
	List<Supplier> getSupplierList(Supplier supplier);

}
