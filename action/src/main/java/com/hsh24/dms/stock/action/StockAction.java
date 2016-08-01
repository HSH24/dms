package com.hsh24.dms.stock.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.dms.api.stock.IStockService;
import com.hsh24.dms.api.stock.bo.Stock;
import com.hsh24.dms.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class StockAction extends BaseAction {

	private static final long serialVersionUID = 690121423498348832L;

	@Resource
	private IStockService stockService;

	private List<Stock> stockList;

	/**
	 * 首页 库存统计.
	 * 
	 * @return
	 */
	public String stats() {
		Stock stock = stockService.getStats(this.getShop().getShopId());
		this.setResourceResult(stock == null ? "0" : String.valueOf(stock.getStock()));

		return RESOURCE_RESULT;
	}

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String list() {
		stockList = stockService.getStockList(this.getShop().getShopId(), new Stock());

		return SUCCESS;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

}
