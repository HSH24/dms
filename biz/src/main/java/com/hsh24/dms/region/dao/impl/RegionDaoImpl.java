package com.hsh24.dms.region.dao.impl;

import com.hsh24.dms.api.region.bo.Region;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.region.dao.IRegionDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class RegionDaoImpl extends BaseDaoImpl implements IRegionDao {

	@Override
	public Region getRegion(Region region) {
		return (Region) getSqlMapClientTemplate().queryForObject("region.getRegion", region);
	}

}
