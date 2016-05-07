package com.hsh24.dms.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hsh24.dms.api.item.IItemFileService;
import com.hsh24.dms.api.item.bo.ItemFile;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.item.dao.IItemFileDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemFileServiceImpl implements IItemFileService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemFileServiceImpl.class);

	private IItemFileDao itemFileDao;

	@Override
	public List<ItemFile> getItemFileList(Long supId, Long itemId) {
		if (itemId == null) {
			return null;
		}

		ItemFile itemFile = new ItemFile();
		itemFile.setSupId(supId);
		itemFile.setItemId(itemId);

		return getItemFileList(itemFile);
	}

	@Override
	public Map<Long, List<ItemFile>> getItemFileList(String[] itemId) {
		if (itemId == null || itemId.length == 0) {
			return null;
		}

		Map<Long, List<ItemFile>> map = new HashMap<Long, List<ItemFile>>();

		// 调用接口 getItemFileList(Long supId, Long itemId)
		for (String id : itemId) {
			Long ietmId = null;

			try {
				ietmId = Long.valueOf(id);
			} catch (NumberFormatException e) {
				logger.error(e);

				continue;
			}

			if (map.containsKey(ietmId)) {
				continue;
			}

			map.put(ietmId, getItemFileList(null, ietmId));
		}

		return map;
	}

	/**
	 * 
	 * @param itemFile
	 * @return
	 */
	private List<ItemFile> getItemFileList(ItemFile itemFile) {
		try {
			return itemFileDao.getItemFileList(itemFile);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(itemFile), e);
		}

		return null;
	}

	public IItemFileDao getItemFileDao() {
		return itemFileDao;
	}

	public void setItemFileDao(IItemFileDao itemFileDao) {
		this.itemFileDao = itemFileDao;
	}

}
