package com.hsh24.dms.sale.service.impl;

import java.util.List;

import com.hsh24.dms.api.sale.ISaleService;
import com.hsh24.dms.api.sale.bo.Sale;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.sale.dao.ISaleDao;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleServiceImpl implements ISaleService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(SaleServiceImpl.class);

	private ISaleDao saleDao;

	@Override
	public Sale getStats(Long shopId, Sale sale) {
		if (shopId == null || sale == null) {
			return null;
		}

		sale.setShopId(shopId);

		try {
			return saleDao.getStats(sale);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(sale), e);
		}

		return null;
	}

	@Override
	public List<Sale> getSaleList(Long shopId, Sale sale) {
		if (shopId == null || sale == null) {
			return null;
		}

		sale.setShopId(shopId);

		try {
			return saleDao.getSaleList(sale);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(sale), e);
		}

		return null;
	}

	public ISaleDao getSaleDao() {
		return saleDao;
	}

	public void setSaleDao(ISaleDao saleDao) {
		this.saleDao = saleDao;
	}

}
