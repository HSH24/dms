package com.wideka.dms.monitor.dao;

import java.util.List;

import com.wideka.dms.api.cache.bo.CacheStats;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICacheMonitorDao {

	/**
	 * 
	 * @param cacheStats
	 * @return
	 */
	int getCacheMonitorCount(CacheStats cacheStats);

	/**
	 * 
	 * @param cacheStats
	 * @return
	 */
	List<CacheStats> getCacheMonitorList(CacheStats cacheStats);

	/**
	 * 
	 * @param cacheStatsList
	 * @return
	 */
	String createCacheMonitor(List<CacheStats> cacheStatsList);

}
