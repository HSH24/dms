package com.hsh24.dms.sale.action;

import java.util.List;

import com.hsh24.dms.api.sale.ISaleService;
import com.hsh24.dms.api.sale.bo.Sale;
import com.hsh24.dms.api.sale.bo.SaleDetail;
import com.hsh24.dms.framework.action.BaseAction;
import com.hsh24.dms.framework.util.DateUtil;
import com.hsh24.dms.framework.util.FormatUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleAction extends BaseAction {

	private static final long serialVersionUID = -486178851207623062L;

	private ISaleService saleService;

	private List<Sale> saleList;

	private String tradeNo;

	private List<SaleDetail> saleDetailList;

	/**
	 * 首页 销售统计.
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Sale sale = new Sale();

		String date = DateUtil.getNowDateStr();
		sale.setGmtStart(date + " 00:00:00");
		sale.setGmtEnd(date + " 23:59:59");
		sale = saleService.getStats(this.getShop().getShopId(), sale);
		sb.append(sale == null ? "0.00" : FormatUtil.getAmountFormat(sale.getAmount())).append("&");

		sale = new Sale();

		String yyyy = String.valueOf(DateUtil.getYear());
		String mm = String.valueOf(DateUtil.getMonth());
		sale.setGmtStart(yyyy + "-" + mm + "-01 00:00:00");
		sale.setGmtEnd(yyyy + "-" + mm + "-31 23:59:59");
		sale = saleService.getStats(this.getShop().getShopId(), sale);
		sb.append(sale == null ? "0.00" : FormatUtil.getAmountFormat(sale.getAmount()));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String list() {
		Sale sale = new Sale();
		String yyyy = String.valueOf(DateUtil.getYear());
		String mm = String.valueOf(DateUtil.getMonth());
		sale.setGmtStart(yyyy + "-" + mm + "-01 00:00:00");
		sale.setGmtEnd(yyyy + "-" + mm + "-31 23:59:59");

		saleList = saleService.getSaleList(this.getShop().getShopId(), sale);

		return SUCCESS;
	}

	public String detail() {
		saleDetailList = saleService.getSaleDetailList(this.getShop().getShopId(), tradeNo);

		return SUCCESS;
	}

	public ISaleService getSaleService() {
		return saleService;
	}

	public void setSaleService(ISaleService saleService) {
		this.saleService = saleService;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public List<SaleDetail> getSaleDetailList() {
		return saleDetailList;
	}

	public void setSaleDetailList(List<SaleDetail> saleDetailList) {
		this.saleDetailList = saleDetailList;
	}

}
