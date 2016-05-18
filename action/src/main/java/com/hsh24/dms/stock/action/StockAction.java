package com.hsh24.dms.stock.action;

import java.util.List;

import com.hsh24.dms.api.stock.IStockService;
import com.hsh24.dms.api.stock.bo.Stock;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.util.FormatUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class StockAction extends BaseAction {

	private static final long serialVersionUID = 690121423498348832L;

	private IStockService stockService;

	private List<Stock> stockList;

	/**
	 * 首页 库存统计.
	 * 
	 * @return
	 */
	public String stats() {
		Stock stock = stockService.getStats(this.getShop().getShopId());
		this.setResourceResult(stock == null ? "0.00" : FormatUtil.getAmountFormat(stock.getAmount()));

		return RESOURCE_RESULT;
	}

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		stockList = stockService.getStockList(this.getShop().getShopId(), new Stock());

		return SUCCESS;
	}

	public IStockService getStockService() {
		return stockService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

}
