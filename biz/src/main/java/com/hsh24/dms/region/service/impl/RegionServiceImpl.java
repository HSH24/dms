package com.hsh24.dms.region.service.impl;

import com.hsh24.dms.api.region.IRegionService;
import com.hsh24.dms.api.region.bo.Region;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.region.dao.IRegionDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class RegionServiceImpl implements IRegionService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(RegionServiceImpl.class);

	private IRegionDao regionDao;

	@Override
	public String[] getRegion(Long regionId) {
		if (regionId == null) {
			return null;
		}

		String[] regionList = new String[5];

		Region region = new Region();
		region.setRegionId(regionId);

		region = getRegion(region);

		if (region == null) {
			return null;
		}

		int i = 0;

		while (region != null) {
			regionList[i++] = region.getRegionId().toString();

			region.setRegionId(region.getParentRegionId());
			region = getRegion(region);
		}

		return regionList;
	}

	/**
	 * 
	 * @param region
	 * @return
	 */
	private Region getRegion(Region region) {
		try {
			return regionDao.getRegion(region);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(region), e);
		}

		return null;
	}

	public IRegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}

}
