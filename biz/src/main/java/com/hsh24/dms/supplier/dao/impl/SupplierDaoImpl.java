package com.hsh24.dms.supplier.dao.impl;

import java.util.List;

import com.hsh24.dms.api.supplier.bo.Supplier;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.supplier.dao.ISupplierDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SupplierDaoImpl extends BaseDaoImpl implements ISupplierDao {

	@Override
	public Supplier getSupplier(Supplier supplier) {
		return (Supplier) getSqlMapClientTemplate().queryForObject("supplier.getSupplier", supplier);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> getSupplierList(Supplier supplier) {
		return (List<Supplier>) getSqlMapClientTemplate().queryForList("supplier.getSupplierList", supplier);
	}

}
