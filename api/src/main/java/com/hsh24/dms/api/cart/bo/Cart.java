package com.hsh24.dms.api.cart.bo;

import java.math.BigDecimal;
import java.util.List;

import com.hsh24.dms.api.item.bo.ItemFile;
import com.hsh24.dms.framework.bo.SearchInfo;

/**
 * 购物车.
 * 
 * @author JiakunXu
 * 
 */
public class Cart extends SearchInfo {

	private static final long serialVersionUID = -1870399095028606562L;

	/**
	 * 购物车ID.
	 */
	private Long cartId;

	/**
	 * 用户ID.
	 */
	private Long userId;

	/**
	 * 店铺ID.
	 */
	private Long shopId;

	/**
	 * 供应商ID.
	 */
	private Long supId;

	/**
	 * 商品ID.
	 */
	private Long itemId;

	/**
	 * SKU ID.
	 */
	private Long skuId;

	/**
	 * 购物车该sku商品的数量.
	 */
	private int quantity;

	/**
	 * 状态 D:删除 U:正常 E:已购买.
	 */
	private String state;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 供应商名称.
	 */
	private String supName;

	/**
	 * 查询结果.
	 */
	private String itemName;

	/**
	 * 商品文件.
	 */
	private List<ItemFile> itemFileList;

	/**
	 * sku所对应的销售属性的中文名字串，格式如：Pid1:vid1:pid_name1:vid_name1;Pid2:vid2:pid_name2:vid_name2.
	 */
	private String propertiesName;

	/**
	 * 价格(10.00).
	 */
	private BigDecimal price;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<ItemFile> getItemFileList() {
		return itemFileList;
	}

	public void setItemFileList(List<ItemFile> itemFileList) {
		this.itemFileList = itemFileList;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
