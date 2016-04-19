package com.wideka.dms.item.dao.impl;

import java.util.List;

import com.wideka.dms.api.item.bo.ItemSku;
import com.wideka.dms.framework.dao.impl.BaseDaoImpl;
import com.wideka.dms.item.dao.IItemSkuDao;

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
