package com.hsh24.dms.item.service.impl;

import com.hsh24.dms.api.item.IItemPriceService;
import com.hsh24.dms.api.item.bo.ItemPrice;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.item.dao.IItemPriceDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemPriceServiceImpl implements IItemPriceService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemPriceServiceImpl.class);

	private IItemPriceDao itemPriceDao;

	@Override
	public ItemPrice getItemPrice(Long itemRegionId, Long skuId) {
		if (itemRegionId == null || skuId == null) {
			return null;
		}

		ItemPrice itemPrice = new ItemPrice();
		itemPrice.setItemRegionId(itemRegionId);
		itemPrice.setSkuId(skuId);

		try {
			return itemPriceDao.getItemPrice(itemPrice);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(itemPrice), e);
		}

		return null;
	}

	public IItemPriceDao getItemPriceDao() {
		return itemPriceDao;
	}

	public void setItemPriceDao(IItemPriceDao itemPriceDao) {
		this.itemPriceDao = itemPriceDao;
	}

}
