package com.rameses.clfc.android.db;

import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBRemarksRemoved extends AbstractDBMapper 
{
	public String getTableName() { return "remarks_removed"; }
	
	public List<Map> getPendingRemarksRemoved() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = " SELECT rr.*, cs.detailid " +
						 " FROM "+getTableName()+" rr " +
						 "    INNER JOIN collectionsheet cs ON rr.loanappid=cs.loanappid " +
						 " WHERE rr.state='PENDING'";
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
}
