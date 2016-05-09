package com.hsh24.dms.receipt.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.hsh24.dms.api.receipt.bo.ReceiptDetail;
import com.hsh24.dms.framework.dao.impl.BaseDaoImpl;
import com.hsh24.dms.receipt.dao.IReceiptDetailDao;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDetailDaoImpl extends BaseDaoImpl implements IReceiptDetailDao {

	@Override
	public int createReceiptDetail(final Long receiptId, final List<ReceiptDetail> receiptDetailList,
		final String modifyUser) {
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int count = 0;
				executor.startBatch();
				for (ReceiptDetail receiptDetail : receiptDetailList) {
					receiptDetail.setReceiptId(receiptId);
					receiptDetail.setModifyUser(modifyUser);

					executor.insert("receipt.detail.createReceiptDetail", receiptDetail);
					count++;
				}
				executor.executeBatch();

				return count;
			}
		});
	}

}
