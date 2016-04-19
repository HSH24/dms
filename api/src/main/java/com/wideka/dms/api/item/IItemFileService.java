package com.wideka.dms.api.item;

import java.util.List;

import com.wideka.dms.api.item.bo.ItemFile;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemFileService {

	/**
	 * 获取商品文件.
	 * 
	 * @param supId
	 * @param itemId
	 * @return
	 */
	List<ItemFile> getItemFileList(Long supId, String itemId);

}
