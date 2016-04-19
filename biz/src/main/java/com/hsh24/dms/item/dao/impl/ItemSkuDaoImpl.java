package com.hsh24.dms.item.dao.impl;

import java.util.List;

import com.hsh24.dms.api.item.bo.ItemSku;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.item.dao.IItemSkuDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemSkuDaoImpl extends BaseDaoImpl implements IItemSkuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemSku> getItemSkuList(ItemSku sku) {
		return (List<ItemSku>) getSqlMapClientTemplate().queryForList("item.sku.getItemSkuList", sku);
	}

}
