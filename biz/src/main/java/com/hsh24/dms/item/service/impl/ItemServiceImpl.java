package com.hsh24.dms.item.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hsh24.dms.api.item.IItemFileService;
import com.hsh24.dms.api.item.IItemPriceService;
import com.hsh24.dms.api.item.IItemRegionService;
import com.hsh24.dms.api.item.IItemService;
import com.hsh24.dms.api.item.IItemSkuService;
import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.api.item.bo.ItemPrice;
import com.hsh24.dms.api.item.bo.ItemRegion;
import com.hsh24.dms.api.item.bo.ItemSku;
import com.hsh24.dms.api.region.IRegionService;
import com.hsh24.dms.api.shop.IShopService;
import com.hsh24.dms.api.shop.bo.Shop;
import com.hsh24.dms.api.spec.ISpecService;
import com.hsh24.dms.api.spec.bo.SpecCat;
import com.hsh24.dms.api.spec.bo.SpecItem;
import com.hsh24.dms.api.supplier.ISupplierService;
import com.hsh24.dms.api.supplier.bo.Supplier;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.item.dao.IItemDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ItemServiceImpl implements IItemService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemServiceImpl.class);

	@Resource
	private IItemRegionService itemRegionService;

	@Resource
	private IItemFileService itemFileService;

	@Resource
	private IItemSkuService itemSkuService;

	@Resource
	private IItemPriceService itemPriceService;

	@Resource
	private ISpecService specService;

	@Resource
	private IShopService shopService;

	@Resource
	private IRegionService regionService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private IItemDao itemDao;

	@Override
	public int getItemCount(Long shopId, Item item) {
		if (shopId == null || item == null) {
			return 0;
		}

		item.setSupId(shopId);

		return 0;
	}

	@Override
	public List<Item> getItemList(Long shopId, Item item) {
		if (shopId == null || item == null) {
			return null;
		}

		Shop shop = shopService.getShop(shopId);
		if (shop == null) {
			return null;
		}

		return getItem(shop.getRegionId(), item);
	}

	/**
	 * 根据区域查询供应商投放商品信息.
	 * 
	 * @param regionId
	 * @return
	 */
	private List<Item> getItem(Long regionId, Item item) {
		String[] region = regionService.getRegion(regionId);
		if (region == null || region.length == 0) {
			return null;
		}

		List<ItemRegion> list = itemRegionService.getItemRegionList(region, item);
		if (list == null || list.size() == 0) {
			return null;
		}

		List<Item> itemList = new ArrayList<Item>();

		// 获取商品价格(item)
		for (ItemRegion itemRegion : list) {
			Item it = getItem(itemRegion.getItemId());
			if (it == null) {
				continue;
			}

			// 获得 itemRegionId 因为 存在 不同 regionId 维护 相同 itemId
			Long itemRegionId = getItemRegionId(itemRegion.getItemId(), region);
			if (itemRegionId == null) {
				continue;
			}

			// 获取商品价格(item_price)
			List<ItemSku> skuList = it.getSkuList();
			if (skuList != null && !skuList.isEmpty()) {
				for (ItemSku sku : skuList) {
					ItemPrice itemPrice = itemPriceService.getItemPrice(itemRegionId, sku.getSkuId());
					if (itemPrice != null) {
						sku.setPrice(itemPrice.getPrice());
					}
				}
			} else {
				// sku = 0 维护的价格
				ItemPrice itemPrice = itemPriceService.getItemPrice(itemRegionId, 0L);
				if (itemPrice != null) {
					it.setPrice(itemPrice.getPrice());
				}
			}

			// 获取供应商信息
			Supplier sup = supplierService.getSupplier(it.getSupId());
			if (sup != null) {
				it.setSupName(sup.getSupName());
			}

			itemList.add(it);
		}

		return itemList;
	}

	/**
	 * 
	 * @param itemId
	 * @param region
	 * @return
	 */
	private Long getItemRegionId(Long itemId, String[] region) {
		for (String regionId : region) {
			ItemRegion itemRegion = itemRegionService.getItemRegion(itemId, Long.valueOf(regionId));
			if (itemRegion != null) {
				return itemRegion.getItemRegionId();
			}
		}

		return null;
	}

	@Override
	public Item getItem(String itemId) {
		if (StringUtils.isBlank(itemId)) {
			return null;
		}

		try {
			return getItem(Long.valueOf(itemId));
		} catch (NumberFormatException e) {
			logger.error(itemId);
		}

		return null;
	}

	/**
	 * 
	 * @param itemId
	 * @return
	 */
	private Item getItem(Long itemId) {
		// 1. 获取商品基本信息
		Item item = new Item();
		item.setItemId(itemId);
		item = getItem(item);

		if (item == null) {
			return null;
		}

		// 2. 获取商品文件信息
		item.setItemFileList(itemFileService.getItemFileList(itemId));

		// 3. 获取商品 sku 信息
		List<ItemSku> skuList = itemSkuService.getItemSkuList(itemId);

		// 不存在 sku 信息 直接返回
		if (skuList == null || skuList.isEmpty()) {
			// 设置价格区间 = item 总表价格
			item.setOriginRange(item.getOrigin().toString());
			item.setPriceRange(item.getPrice().toString());

			return item;
		}

		// titleList 获取第一个 sku 从中获取 specCat 信息
		ItemSku sku = skuList.get(0);
		String[] properties = sku.getProperties().split(";");

		String[] specCId = new String[properties.length];
		int i = 0;
		for (String id : properties) {
			String[] cid = id.split(":");

			// 兼容 正常不会执行
			if (cid.length == 1) {
				item.setSkuList(skuList);
				return item;
			}

			specCId[i++] = cid[0];
		}

		List<SpecCat> specCatList = specService.getSpecCatList(specCId);
		// 根据 specCId[] 重新排序
		if (specCatList != null && !specCatList.isEmpty()) {
			Map<Long, SpecCat> map = new HashMap<Long, SpecCat>();
			for (SpecCat sc : specCatList) {
				map.put(sc.getSpecCId(), sc);
			}

			specCatList = new ArrayList<SpecCat>();
			for (String cid : specCId) {
				specCatList.add(map.get(cid));
			}
		}

		// 遍历所有的已有 sku 获取 specItem 信息；遍历的同时，统计 金额最大最小
		// 原始价格 max0 min0
		// 会员价格 max1 min1
		BigDecimal max0 = BigDecimal.ZERO;
		BigDecimal min0 = new BigDecimal("100000000");
		BigDecimal max1 = BigDecimal.ZERO;
		BigDecimal min1 = new BigDecimal("100000000");

		String[] specItemId = new String[properties.length * skuList.size()];
		int j = 0;
		// 遍历所有
		for (ItemSku sk : skuList) {
			String[] ps = sk.getProperties().split(";");
			for (String id : ps) {
				String[] cid = id.split(":");
				specItemId[j++] = cid[1];
			}

			// 判断最大最小
			if (sk.getOrigin().compareTo(min0) == -1) {
				min0 = sk.getOrigin();
			}

			if (sk.getOrigin().compareTo(max0) == 1) {
				max0 = sk.getOrigin();
			}

			if (sk.getPrice().compareTo(min1) == -1) {
				min1 = sk.getPrice();
			}

			if (sk.getPrice().compareTo(max1) == 1) {
				max1 = sk.getPrice();
			}
		}

		// 设置价格区间 = item 总表价格
		item.setOriginRange(min0.toString() + " - " + max0.toString());
		item.setPriceRange(min1.toString() + " - " + max1.toString());

		List<SpecItem> specItemList = specService.getSpecItemList(specItemId);

		// 规格组合 黑色 大 ／ 红色 大
		if (specItemList != null && !specItemList.isEmpty()) {
			Map<Long, String> map = new HashMap<Long, String>();

			for (SpecItem specItem : specItemList) {
				map.put(specItem.getSpecItemId(), specItem.getSpecItemValue());
			}

			for (ItemSku sk : skuList) {
				List<String> specItemValueList = new ArrayList<String>();
				String[] ps = sk.getProperties().split(";");
				for (String id : ps) {
					String[] cid = id.split(":");
					specItemValueList.add(map.get(cid[1]));
				}

				sk.setSpecItemValueList(specItemValueList);
			}
		}

		// 选中的规格信息
		item.setSpecCat(JSON.toJSONString(specCatList).replace("'", "\\'"));
		item.setSpecItem(JSON.toJSONString(specItemList).replace("'", "\\'"));

		// sku 明细
		item.setSkuList(skuList);
		// 规格列表中 规格组合信息
		item.setSpecCatList(specCatList);

		return item;
	}

	@Override
	public Map<Long, Item> getItem(String[] itemId) {
		if (itemId == null || itemId.length == 0) {
			return null;
		}

		Item item = new Item();
		item.setCodes(itemId);
		item.setLimit(itemId.length);
		item.setOffset(0);

		List<Item> itemList = getItemList(item);

		if (itemList == null || itemList.isEmpty()) {
			return null;
		}

		Map<Long, Item> map = new HashMap<Long, Item>();

		for (Item ietm : itemList) {
			map.put(ietm.getItemId(), ietm);
		}

		return map;
	}

	@Override
	public BooleanResult validate(Long itemId, Long skuId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (itemId == null) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (skuId == null) {
			result.setCode("商品SKU信息不能为空");
			return result;
		}

		// 某一商品价格
		BigDecimal price = BigDecimal.ZERO;

		List<ItemSku> itemSkuList = itemSkuService.getItemSkuList(itemId);

		if (itemSkuList != null && itemSkuList.size() > 0) {
			// 根据 skuId 获得 item 并 验证
			Map<Long, ItemSku> map = itemSkuService.getItemSku(new String[] { skuId.toString() });
			if (map == null || map.size() == 0) {
				result.setCode("SKU信息不存在");
				return result;
			}

			ItemSku itemSku = map.get(skuId);
			if (itemSku == null) {
				result.setCode("SKU信息不存在");
				return result;
			}

			if (!itemId.equals(itemSku.getItemId())) {
				result.setCode("商品和SKU信息不匹配");
				return result;
			}

			price = itemSku.getPrice();
		} else {
			skuId = 0L;
		}

		// 根据 itemId 获得 item
		Map<Long, Item> map = getItem(new String[] { itemId.toString() });
		if (map == null || map.size() == 0) {
			result.setCode("商品信息不存在");
			return result;
		}

		Item item = map.get(itemId);
		if (item == null) {
			result.setCode("商品信息不存在");
			return result;
		}

		result.setCode(item.getSupId().toString() + "&" + (skuId.equals(0L) ? item.getPrice() : price));
		result.setResult(true);
		return result;
	}

	private List<Item> getItemList(Item item) {
		try {
			return itemDao.getItemList(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

	private Item getItem(Item item) {
		try {
			return itemDao.getItem(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

}
