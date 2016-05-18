package com.hsh24.dms.stock.action;

import com.hsh24.dms.api.stock.IStockService;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class StockAction extends BaseAction {

	private static final long serialVersionUID = 690121423498348832L;

	private IStockService stockService;

	/**
	 * 首页 库存统计.
	 * 
	 * @return
	 */
	public String stats() {
		this.setResourceResult(stockService.getStats(this.getShop().getShopId()));

		return RESOURCE_RESULT;
	}

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

	public IStockService getStockService() {
		return stockService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

}
