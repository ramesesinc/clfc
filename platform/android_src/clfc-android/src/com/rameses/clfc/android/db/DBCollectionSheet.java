package com.rameses.clfc.android.db;

import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBCollectionSheet extends AbstractDBMapper 
{
	public String getTableName() { return "collectionsheet"; }
	
	public Map findCollectionSheetByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+ " WHERE loanappid=?";
			return ctx.find(sql, new Object[]{loanappid});
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
}
