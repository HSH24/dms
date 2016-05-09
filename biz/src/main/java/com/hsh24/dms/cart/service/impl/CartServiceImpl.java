package com.hsh24.dms.cart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.cart.ICartService;
import com.hsh24.dms.api.cart.bo.Cart;
import com.hsh24.dms.api.item.IItemFileService;
import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.api.item.IItemSkuService;
import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.api.item.bo.ItemFile;
import com.hsh24.dms.api.item.bo.ItemSku;
import com.hsh24.dms.api.supplier.ISupplierService;
import com.hsh24.dms.api.supplier.bo.Supplier;
import com.hsh24.dms.cart.dao.ICartDao;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CartServiceImpl implements ICartService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CartServiceImpl.class);

	private IItemService itemService;

	private IItemSkuService itemSkuService;

	private IItemFileService itemFileService;

	private ISupplierService supplierService;

	private ICartDao cartDao;

	@Override
	public BooleanResult createCart(Long userId, String itemId, String skuId, String quantity) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空。");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (StringUtils.isBlank(itemId)) {
			result.setCode("商品信息不能为空。");
			return result;
		}
		try {
			cart.setItemId(Long.valueOf(itemId));
		} catch (NumberFormatException e) {
			logger.error(itemId, e);

			result.setCode("商品信息错误。");
			return result;
		}

		if (StringUtils.isNotBlank(skuId)) {
			try {
				cart.setSkuId(Long.valueOf(skuId));
			} catch (NumberFormatException e) {
				logger.error(skuId, e);

				result.setCode("SKU信息错误。");
				return result;
			}
		}

		// 验证 itemId skuId 并获取 supId
		result = itemService.validate(cart.getItemId(), cart.getSkuId());
		if (!result.getResult()) {
			return result;
		} else {
			result.setResult(false);
		}

		cart.setSupId(Long.valueOf(result.getCode()));

		if (StringUtils.isBlank(quantity)) {
			result.setCode("购买商品数量不能为空。");
			return result;
		}

		int q;

		try {
			q = Integer.parseInt(quantity);
		} catch (Exception e) {
			logger.error(quantity, e);
			result.setCode("购买商品数量非数字类型。");
			return result;
		}

		if (q == 0 || q < 1) {
			result.setCode("数量不能为0或负。");
			return result;
		}

		cart.setQuantity(q);

		// 1. 更新购物车(相同规格商品 只增加数量)
		if (checkCart(cart) == 1) {
			result.setCode("添加成功。");
			result.setResult(true);
			return result;
		}

		// 2. 创建购物车
		try {
			cartDao.createCart(cart);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);

			result.setCode("添加购物车失败，请稍后再试。");
		}

		if (result.getResult()) {
			result.setCode("添加成功。");
		}

		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int checkCart(Cart cart) {
		try {
			return cartDao.checkCart(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

	@Override
	public int getCartCount(Long userId) {
		// userId 必填
		if (userId == null) {
			return 0;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);

		try {
			return cartDao.getCartCount(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return 0;
	}

	@Override
	public List<Cart> getCartList(Long userId) {
		return getCartList(userId, null);
	}

	@Override
	public List<Cart> getCartList(Long userId, String[] cartId) {
		// userId 必填
		if (userId == null) {
			return null;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setCodes(cartId);

		List<Cart> cartList = getCartList(cart);

		if (cartList == null || cartList.size() == 0) {
			return null;
		}

		// 供应商信息
		String[] supId = new String[cartList.size()];
		int i = 0;

		// 商品信息
		String[] itemId = new String[cartList.size()];
		int j = 0;

		// sku信息
		String[] skuId = new String[cartList.size()];
		int k = 0;

		for (Cart ca : cartList) {
			// 供应商
			supId[i++] = ca.getSupId().toString();

			// 商品
			itemId[j++] = ca.getItemId().toString();

			// sku
			Long id = ca.getSkuId();
			if (!id.equals(0L)) {
				skuId[k++] = id.toString();
			}
		}

		// 1. 获取供应商信息
		Map<Long, Supplier> supplierMap = supplierService.getSupplier(supId);

		// 2. 获取商品信息
		Map<Long, Item> itemMap = itemService.getItem(itemId);

		// 3. 获取sku信息
		Map<Long, ItemSku> itemSkuMap = k == 0 ? new HashMap<Long, ItemSku>() : itemSkuService.getItemSku(skuId);

		// 4. 获取商品文件信息
		Map<Long, List<ItemFile>> itemFileMap = itemFileService.getItemFileList(itemId);

		for (Cart ca : cartList) {
			// 供应商信息
			Supplier supplier = supplierMap.get(ca.getSupId());
			if (supplier != null) {
				ca.setSupName(supplier.getSupName());
			}

			// 商品名称 & 商品价格
			Item item = itemMap.get(ca.getItemId());
			if (item != null) {
				ca.setItemName(item.getItemName());
				ca.setPrice(item.getPrice());
			}

			// sku名称 & 商品价格
			Long id = ca.getSkuId();
			if (id != 0L) {
				ItemSku sku = itemSkuMap.get(id);
				if (sku != null) {
					ca.setPropertiesName(sku.getPropertiesName());
					ca.setPrice(sku.getPrice());
				}
			}

			// 商品文件信息
			if (itemFileMap != null && !itemFileMap.isEmpty()) {
				ca.setItemFileList(itemFileMap.get(ca.getItemId()));
			}
		}

		return cartList;
	}

	@Override
	public BooleanResult removeCart(Long userId, String[] cartId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空！");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车商品信息不能为空！");
			return result;
		}
		cart.setCodes(cartId);

		cart.setState(ICartService.STATE_REMOVE);

		int n = updateCart(cart);
		if (n == -1) {
			result.setCode("购物车更新失败！");
			return result;
		}

		result.setCode("删除成功。");
		result.setResult(true);

		return result;
	}

	@Override
	public BooleanResult updateQuantity(Long userId, String cartId, String quantity) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("0");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (StringUtils.isBlank(cartId)) {
			result.setCode("0");
			return result;
		}
		try {
			cart.setCartId(Long.valueOf(cartId));
		} catch (NumberFormatException e) {
			logger.error(cartId, e);

			result.setCode("0");
			return result;
		}

		if (StringUtils.isBlank(quantity)) {
			result.setCode("1");
			return result;
		}

		int q;

		try {
			q = Integer.parseInt(quantity);
		} catch (Exception e) {
			logger.error(quantity, e);
			result.setCode("1");
			return result;
		}

		if (q == 0 || q < 1) {
			result.setCode("1");
			return result;
		}

		cart.setQuantity(q);

		int n = updateQuantity(cart);
		if (n != 1) {
			result.setCode(String.valueOf(q - 1));
			return result;
		}

		result.setCode(String.valueOf(q));
		result.setResult(true);

		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int updateQuantity(Cart cart) {
		try {
			return cartDao.updateQuantity(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

	@Override
	public BooleanResult finishCart(Long userId, String[] cartId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空！");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车商品信息不能为空！");
			return result;
		}
		cart.setCodes(cartId);

		cart.setState(ICartService.STATE_FINISH);

		int n = updateCart(cart);
		if (n == -1) {
			result.setCode("购物车更新失败！");
			return result;
		}

		result.setResult(true);
		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private List<Cart> getCartList(Cart cart) {
		try {
			return cartDao.getCartList(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return null;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int updateCart(Cart cart) {
		try {
			return cartDao.updateCart(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public IItemSkuService getItemSkuService() {
		return itemSkuService;
	}

	public void setItemSkuService(IItemSkuService itemSkuService) {
		this.itemSkuService = itemSkuService;
	}

	public IItemFileService getItemFileService() {
		return itemFileService;
	}

	public void setItemFileService(IItemFileService itemFileService) {
		this.itemFileService = itemFileService;
	}

	public ISupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public ICartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(ICartDao cartDao) {
		this.cartDao = cartDao;
	}

}
