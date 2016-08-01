package com.hsh24.dms.trade.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.dms.api.trade.ITradeLogService;
import com.hsh24.dms.api.trade.bo.TradeLog;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.trade.dao.ITradeLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class TradeLogServiceImpl implements ITradeLogService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeLogServiceImpl.class);

	@Resource
	private ITradeLogDao tradeLogDao;

	@Override
	public BooleanResult createTradeLog(Long tradeId, String type, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (tradeId == null) {
			result.setCode("交易信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(type)) {
			result.setCode("交易类型不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		TradeLog tradeLog = new TradeLog();
		tradeLog.setTradeId(tradeId);
		tradeLog.setType(type.trim());
		tradeLog.setModifyUser(modifyUser);

		try {
			tradeLogDao.createTradeLog(tradeLog);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(tradeLog), e);

			result.setCode("记录交易日志信息失败，请稍后再试");
		}

		if (result.getResult()) {
			result.setCode("操作成功");
		}

		return result;
	}

}
