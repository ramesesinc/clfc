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
//			String sql = " SELECT rr.*, cs.detailid " +
//						 " FROM "+getTableName()+" rr " +
//						 "    INNER JOIN collectionsheet cs ON rr.loanappid=cs.loanappid " +
//						 " WHERE rr.state='PENDING'";
			String sql = "SELECT * FROM "+getTableName()+" WHERE state='PENDING'";
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public boolean hasPendingRemarksRemoved() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE state='PENDING' LIMIT 1";
			return (ctx.getCount(sql, new Object[]{}) > 0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
}
