package com.hsh24.dms.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.dms.api.item.IItemRegionService;
import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.api.item.bo.ItemRegion;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.item.dao.IItemRegionDao;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ItemRegionServiceImpl implements IItemRegionService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemRegionServiceImpl.class);

	@Resource
	private IItemRegionDao itemRegionDao;

	@Override
	public List<ItemRegion> getItemRegionList(String[] region, Item item) {
		if (region == null || region.length == 0 || item == null) {
			return null;
		}

		item.setCodes(region);

		try {
			return itemRegionDao.getItemRegionList(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

	@Override
	public ItemRegion getItemRegion(Long itemId, Long regionId) {
		if (itemId == null || regionId == null) {
			return null;
		}

		ItemRegion itemRegion = new ItemRegion();
		itemRegion.setItemId(itemId);
		itemRegion.setRegionId(regionId);

		try {
			return itemRegionDao.getItemRegion(itemRegion);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(itemRegion), e);
		}

		return null;
	}

}
