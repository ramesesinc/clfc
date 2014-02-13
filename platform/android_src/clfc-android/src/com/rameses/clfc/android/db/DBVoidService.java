package com.rameses.clfc.android.db;

import com.rameses.db.android.DBContext;


public class DBVoidService extends AbstractDBMapper
{
	public String getTableName() { return "void"; }
	
	public int noOfVoidPaymentsByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT objid FROM "+getTableName()+" WHERE loanappid=?";
			return ctx.getCount(sql, new Object[]{loanappid});
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
	
	public boolean hasPendingVoidRequestByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT objid FROM "+getTableName()+" WHERE state='PENDING'";
			return (ctx.getCount(sql, new Object[]{}) > 0);
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
}
