package com.hsh24.dms.receipt.service.impl;

import org.apache.commons.lang.StringUtils;

import com.hsh24.dms.api.receipt.IReceiptLogService;
import com.hsh24.dms.api.receipt.bo.ReceiptLog;
import com.hsh24.dms.framework.bo.BooleanResult;
import com.hsh24.dms.framework.log.Logger4jCollection;
import com.hsh24.dms.framework.log.Logger4jExtend;
import com.hsh24.dms.framework.util.LogUtil;
import com.hsh24.dms.receipt.dao.IReceiptLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptLogServiceImpl implements IReceiptLogService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptLogServiceImpl.class);

	private IReceiptLogDao receiptLogDao;

	@Override
	public BooleanResult createReceiptLog(Long receiptId, String type, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (receiptId == null) {
			result.setCode("收货信息不能为空。");
			return result;
		}

		if (StringUtils.isBlank(type)) {
			result.setCode("收货类型不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		ReceiptLog receiptLog = new ReceiptLog();
		receiptLog.setReceiptId(receiptId);
		receiptLog.setType(type.trim());
		receiptLog.setModifyUser(modifyUser);

		try {
			receiptLogDao.createReceiptLog(receiptLog);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receiptLog), e);

			result.setCode("记录收货日志信息失败，请稍后再试。");
		}

		if (result.getResult()) {
			result.setCode("操作成功。");
		}

		return result;
	}

	public IReceiptLogDao getReceiptLogDao() {
		return receiptLogDao;
	}

	public void setReceiptLogDao(IReceiptLogDao receiptLogDao) {
		this.receiptLogDao = receiptLogDao;
	}

}
