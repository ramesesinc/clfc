package com.rameses.clfc.android.db;

import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBCollectionSheet extends AbstractDBMapper 
{
	public String getTableName() { return "collectionsheet"; }
	
	public int getCountByRoutecode(String routecode) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT loanappid FROM "+getTableName()+" WHERE routecode=?";
			return ctx.getCount(sql, new Object[]{routecode});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getCollectionSheetsByRoutecodeAndSearchtext(Map params) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM collectionsheet WHERE acctname LIKE $P{searchtext} AND routecode=$P{routecode}";
			return ctx.getList(sql, params);
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getCollectionSheetsByRoutecode(String routecode) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE routecode=?";
			return ctx.getList(sql, new Object[]{routecode});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getUnremittedCollectionSheets() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = " SELECT cs.* FROM "+getTableName()+" cs " +
						 " INNER JOIN route r ON cs.routecode=r.routecode" +
						 " WHERE r.state <> 'REMITTED'";
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public Map findCollectionSheetByLoanappid(String loanappid) throws Exception {
		DBContext ctx = createDBContext();
		System.out.println("DBContext -> "+ctx);
		try {
			String sql = "SELECT * FROM "+getTableName()+ " WHERE loanappid=?";
			System.out.println("loanappid -> "+loanappid);
			return ctx.find(sql, new Object[]{loanappid});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getCollectionSheetsByCollectorid(String collectorid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = " SELECT cs.* FROM "+getTableName()+" cs " +
						 " INNER JOIN route r ON cs.routecode=r.routecode " +
						 " WHERE r.collectorid=?";
			return ctx.getList(sql, new Object[]{collectorid});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
//	public List<Map> getPostedCollectionSheets(Map params) throws Exception {
//		DBContext ctx = createDBContext();
//		try {
//			String sql = " SELECT DISTINCT(cs.acctname) " +
//						 " FROM "+getTableName()+" cs " +
//						 "     INNER JOIN route r ON cs.routecode=r.routecode " +
//						 "     LEFT JOIN payment p ON cs.loanappid=p.loanappid " +
//						 "     LEFT JOIN remarks rm ON cs.loanappid=rm.loanappid " +
//						 " WHERE r.collectorid=$P{collectorid} " +
//						 "     AND cs.acctname LIKE $P{searchtext} " +
//						 "     AND (" +
//						 "          (p.state='ACTIVE' AND rm.state='APPROVED') " +
//						 "          OR (p.state IS NOT NULL AND p.state='APPROVED') " +
//						 "          OR (rm.state IS NOT NULL AND rm.state='APPROVED')" +
//						 "     )" +
//						 " ORDER BY cs.acctname";
//			return ctx.getList(sql, params);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			ctx.close();
//		}
//	}
//	
//	public List<Map> getUnpostedCollectionSheets(Map params) throws Exception {
//		DBContext ctx = createDBContext();
//		try {
//			String sql = " SELECT DISTINCT(cs.acctname) " +
//						 " FROM "+getTableName()+" cs " +
//						 "      INNER JOIN route r ON cs.routecode=r.routecode " +
//						 "      LEFT JOIN payment p ON cs.loanappid=p.loanappid " +
//						 "      LEFT JOIN remarks rm ON cs.loanappid=rm.loanappid " +
//						 " WHERE r.collectorid=$P{collectorid} " +
//						 "      AND cs.acctname LIKE $P{searchtext} " +
//						 "      AND (" +
//						 "           (p.state IS NOT NULL AND p.state='PENDING') " +
//						 "           OR (rm.state IS NOT NULL AND rm.state='PENDING') " +
//						 "      )" +
//						 " ORDER BY cs.acctname";
//			return ctx.getList(sql, params);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			ctx.close();
//		}
//	}
//	
//	public int getTotalCollectionSheetsByRoutecode(String routecode) throws Exception {
//		DBContext ctx = createDBContext();
//		try {
//			String sql = " SELECT DISTINCT(cs.loanappid) " +
//						 " FROM "+getTableName()+" cs " +
//						 "      LEFT JOIN remarks r ON cs.loanappid=r.loanappid " +
//						 "      LEFT JOIN payment p ON cs.loanappid=p.loanappid " +
//						 " WHERE cs.routecode=? " +
//						 "      AND (p.state IS NOT NULL OR r.state IS NOT NULL)";
//			return ctx.getCount(sql, new Object[]{routecode});
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			ctx.close();
//		}
//	}
}
