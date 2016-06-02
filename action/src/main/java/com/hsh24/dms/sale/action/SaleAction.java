package com.hsh24.dms.sale.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

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

	private String year;

	private String month;

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
		sale.setGmtEnd(DateUtil.datetime(DateUtil.getLastDayOfLastMonth(Integer.parseInt(yyyy), Integer.parseInt(mm)),
			DateUtil.DEFAULT_DATE_FORMAT) + " 23:59:59");
		sale = saleService.getStats(this.getShop().getShopId(), sale);
		sb.append(sale == null ? "0.00" : FormatUtil.getAmountFormat(sale.getAmount()));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @param sale
	 * @return
	 */
	private Sale init(Sale sale) {
		if (StringUtils.isBlank(year)) {
			year = String.valueOf(DateUtil.getYear());
		}

		if (StringUtils.isBlank(month)) {
			month = String.valueOf(DateUtil.getMonth());
		}

		sale.setGmtStart(year + "-" + month + "-01 00:00:00");
		sale.setGmtEnd(DateUtil.datetime(
			DateUtil.getLastDayOfLastMonth(Integer.parseInt(year), Integer.parseInt(month)),
			DateUtil.DEFAULT_DATE_FORMAT) + " 23:59:59");

		return sale;
	}

	/**
	 * 
	 * @return
	 */
	public String list() {
		saleList = saleService.getSaleList(this.getShop().getShopId(), init(new Sale()));

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
