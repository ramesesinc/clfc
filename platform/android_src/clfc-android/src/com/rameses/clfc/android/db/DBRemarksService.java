package com.rameses.clfc.android.db;

import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBRemarksService extends AbstractDBMapper 
{
	public String getTableName() { return "remarks"; }
	
	public Map findRemarksByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE loanappid=?";
			return ctx.find(sql, new Object[]{loanappid});
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
	
	public List<Map> getPendingRemarks() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE state='PENDING'";
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	} 
	
	public void approveRemarksByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "UPDATE "+getTableName()+" SET state='APPROVED' WHERE loanappid='"+loanappid+"'";
			ctx.execute(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			ctx.close();
		}
	}
}
