package com.hsh24.dms.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.supplier.ISupplierService;
import com.hsh24.dms.api.supplier.bo.Supplier;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.supplier.dao.ISupplierDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SupplierServiceImpl implements ISupplierService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(SupplierServiceImpl.class);

	private ISupplierDao supplierDao;

	@Override
	public Supplier getSupplier(Long supId) {
		if (supId == null) {
			return null;
		}

		Supplier supplier = new Supplier();
		supplier.setSupId(supId);

		try {
			return supplierDao.getSupplier(supplier);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(supplier), e);
		}

		return null;
	}

	@Override
	public Map<Long, Supplier> getSupplier(String[] supId) {
		if (supId == null || supId.length == 0) {
			return null;
		}

		Supplier supplier = new Supplier();
		supplier.setCodes(supId);

		List<Supplier> supplierList = getSupplierList(supplier);

		if (supplierList == null || supplierList.size() == 0) {
			return null;
		}

		Map<Long, Supplier> map = new HashMap<Long, Supplier>();

		for (Supplier sup : supplierList) {
			map.put(sup.getSupId(), sup);
		}

		return map;
	}

	/**
	 * 
	 * @param supplier
	 * @return
	 */
	private List<Supplier> getSupplierList(Supplier supplier) {
		try {
			return supplierDao.getSupplierList(supplier);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(supplier), e);
		}

		return null;
	}

	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

}
