package com.hsh24.dms.item.dao.impl;

import java.util.List;

import com.hsh24.dms.api.item.bo.Item;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.item.dao.IItemRegionDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemRegionDaoImpl extends BaseDaoImpl implements IItemRegionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemList(Item item) {
		return (List<Item>) getSqlMapClientTemplate().queryForList("item.region.getItemList", item);
	}

}
