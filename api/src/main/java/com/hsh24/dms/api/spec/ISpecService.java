package com.hsh24.dms.api.spec;

import java.util.List;

import com.hsh24.dms.api.spec.bo.SpecCat;
import com.hsh24.dms.api.spec.bo.SpecItem;

/**
 * 供应商规格类目接口.
 * 
 * @author JiakunXu
 * 
 */
public interface ISpecService {

	/**
	 * 获取供应商规格类目信息.
	 * 
	 * @param supId
	 * @param specCId
	 * @return
	 */
	List<SpecCat> getSpecCatList(Long supId, String[] specCId);

	/**
	 * 获取店铺规格类目明细信息.
	 * 
	 * @param supId
	 * @param specItemId
	 * @return
	 */
	List<SpecItem> getSpecItemList(Long supId, String[] specItemId);

}
