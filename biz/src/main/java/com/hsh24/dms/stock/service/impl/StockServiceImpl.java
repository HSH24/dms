package com.hsh24.dms.stock.service.impl;

import com.hsh24.dms.api.stock.IStockService;
import com.hsh24.dms.api.stock.bo.Stock;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.FormatUtil;
import com.hsh24.dms.stock.dao.IStockDao;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class StockServiceImpl implements IStockService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(StockServiceImpl.class);

	private IStockDao stockDao;

	@Override
	public String getStats(Long shopId) {
		if (shopId == null) {
			return null;
		}

		Stock stock = new Stock();
		stock.setShopId(shopId);

		try {
			stock = stockDao.getStats(stock);

			return stock == null ? "0.00" : FormatUtil.getAmountFormat(stock.getAmount());
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(stock), e);
		}

		return null;
	}

	public IStockDao getStockDao() {
		return stockDao;
	}

	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}

}
