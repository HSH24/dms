package com.hsh24.dms.api.cache;

import java.net.InetSocketAddress;
import java.util.List;

import com.hsh24.dms.api.cache.bo.CacheStats;
import com.hsh24.dms.framework.exception.ServiceException;
import com.hsh24.dms.framework.exception.SystemException;

/**
 * MemcachedCache.
 * 
 * @author JiakunXu
 * 
 */
public interface IMemcachedCacheService extends ICacheService<String, Object> {

	/**
	 * default_exp_time.
	 */
	int DEFAULT_EXP = 24 * 60 * 60;

	/**
	 * session.
	 */
	String CACHE_KEY_SESSION = "dms:key_session_";

	/**
	 * session.
	 */
	int CACHE_KEY_SESSION_DEFAULT_EXP = 168 * 60 * 60;

	/**
	 * session.
	 */
	int CACHE_KEY_SESSION_EXP = 3 * 60;

	// >>>>>>>>>>以下是权限相关<<<<<<<<<<

	/**
	 * sso token.
	 */
	int CACHE_KEY_SSO_TOKEN_DEFAULT_EXP = 60;

	/**
	 * check code.
	 */
	String CACHE_KEY_CHECK_CODE = "dms:key_check_code_";

	/**
	 * check code.
	 */
	int CACHE_KEY_CHECK_CODE_DEFAULT_EXP = 30 * 60;

	/**
	 * passport.
	 */
	String CACHE_KEY_PASSPORT = "dms:key_passport_";

	// >>>>>>>>>>以下是监控相关<<<<<<<<<<

	/**
	 * log monitor.
	 */
	String CACHE_KEY_LOG_MONITOR = "dms:key_log_monitor";

	int CACHE_KEY_LOG_MONITOR_DEFAULT_EXP = 0;

	/**
	 * action log.
	 */
	String CACHE_KEY_ACTION_LOG = "dms:key_action_log";

	int CACHE_KEY_ACTION_LOG_DEFAULT_EXP = 0;

	// >>>>>>>>>>以下是微信相关<<<<<<<<<<

	/**
	 * token.
	 */
	String CACHE_KEY_WX_TOKEN = "dms:key_wx_token_";

	String CACHE_KEY_WX_TICKET = "dms:key_wx_ticket_";

	// >>>>>>>>>>以下是交易相关<<<<<<<<<<

	String CACHE_KEY_TRADE_NO = "dms:key_trade_no_";

	int CACHE_KEY_TRADE_NO_DEFAULT_EXP = 3;

	String CACHE_KEY_RECEIPT_NO = "dms:key_receipt_no_";

	int CACHE_KEY_RECEIPT_NO_DEFAULT_EXP = 3;

	// >>>>>>>>>>以下是供应商相关<<<<<<<<<<

	String CACHE_KEY_SUP_ID = "dms:key_sup_id_";

	// >>>>>>>>>>以下是店铺相关<<<<<<<<<<

	String CACHE_KEY_SHOP_ID = "dms:key_shop_id_";

	// >>>>>>>>>>end<<<<<<<<<<

	/**
	 * incr.
	 * 
	 * @param key
	 * @param inc
	 * @return
	 * @throws ServiceException
	 */
	long incr(String key, int inc) throws ServiceException;

	/**
	 * incr.
	 * 
	 * @param key
	 * @param inc
	 * @return
	 * @throws ServiceException
	 */
	long incr(String key, long inc) throws ServiceException;

	/**
	 * decr.
	 * 
	 * @param key
	 * @param decr
	 * @return
	 * @throws ServiceException
	 */
	long decr(String key, int decr) throws ServiceException;

	/**
	 * decr.
	 * 
	 * @param key
	 * @param decr
	 * @return
	 * @throws ServiceException
	 */
	long decr(String key, long decr) throws ServiceException;

	/**
	 * flushAll.
	 * 
	 * @param address
	 * @throws SystemException
	 */
	void flushAll(InetSocketAddress address) throws SystemException;

	/**
	 * cache stats.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	List<CacheStats> getStats() throws ServiceException;

}
