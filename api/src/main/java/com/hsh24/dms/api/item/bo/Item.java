package com.hsh24.dms.api.item.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hsh24.dms.api.spec.bo.SpecCat;
import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Item extends SearchInfo {

	private static final long serialVersionUID = -1284635101288984045L;

	/**
	 * 商品 ID.
	 */
	private Long itemId;

	/**
	 * 商品名称.
	 */
	private String itemName;

	/**
	 * 供应商ID.
	 */
	private Long supId;

	/**
	 * 商品所属类目ID.
	 */
	private String itemCid;

	private String barCode;

	/**
	 * 货号.
	 */
	private String itemNo;

	/**
	 * 是否需要物流 Y or N.
	 */
	private String shipment;

	/**
	 * 价格(10.00).
	 */
	private BigDecimal price;

	/**
	 * 原价格区间.
	 */
	private BigDecimal origin;

	/**
	 * 库存(合计).
	 */
	private int stock;

	/**
	 * 限购：0 代表无限购.
	 */
	private int quota;

	/**
	 * 是否上架下架 Y or N.
	 */
	private String isDisplay;

	/**
	 * 商品描述.
	 */
	private String remark;

	/**
	 * json.
	 */
	private String specCat;

	/**
	 * json.
	 */
	private String specItem;

	private List<ItemSku> skuList;

	private List<SpecCat> specCatList;

	/**
	 * 商品文件.
	 */
	private List<ItemFile> itemFileList;

	/**
	 * 状态 D:删除 U:正常.
	 */
	private String state;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	private Date createDate;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private String supName;

	/**
	 * 查询商品 区别 出售中的商品 和 已售罄的商品(soldout) 或者 type == all 即查询所有商品信息.
	 */
	private String type;

	/**
	 * 统计值.
	 */
	private int count;

	/**
	 * 商品展现 原价格.
	 */
	private String originRange;

	/**
	 * 商品展现 会员价格.
	 */
	private String priceRange;

	/**
	 * 商品标签ID.
	 */
	private String tagId;

	private int pv;

	private int uv;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public String getItemCid() {
		return itemCid;
	}

	public void setItemCid(String itemCid) {
		this.itemCid = itemCid;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOrigin() {
		return origin;
	}

	public void setOrigin(BigDecimal origin) {
		this.origin = origin;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpecCat() {
		return specCat;
	}

	public void setSpecCat(String specCat) {
		this.specCat = specCat;
	}

	public String getSpecItem() {
		return specItem;
	}

	public void setSpecItem(String specItem) {
		this.specItem = specItem;
	}

	public List<ItemSku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<ItemSku> skuList) {
		this.skuList = skuList;
	}

	public List<SpecCat> getSpecCatList() {
		return specCatList;
	}

	public void setSpecCatList(List<SpecCat> specCatList) {
		this.specCatList = specCatList;
	}

	public List<ItemFile> getItemFileList() {
		return itemFileList;
	}

	public void setItemFileList(List<ItemFile> itemFileList) {
		this.itemFileList = itemFileList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getCreateDate() {
		return createDate != null ? (Date) createDate.clone() : null;
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			this.createDate = (Date) createDate.clone();
		}
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOriginRange() {
		return originRange;
	}

	public void setOriginRange(String originRange) {
		this.originRange = originRange;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

}
