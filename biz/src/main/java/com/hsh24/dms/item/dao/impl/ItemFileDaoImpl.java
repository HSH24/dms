package com.hsh24.dms.item.dao.impl;

import java.util.List;

import com.hsh24.dms.api.item.bo.ItemFile;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.item.dao.IItemFileDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemFileDaoImpl extends BaseDaoImpl implements IItemFileDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemFile> getItemFileList(ItemFile itemFile) {
		return (List<ItemFile>) getSqlMapClientTemplate().queryForList("item.file.getItemFileList", itemFile);
	}

}
