package com.rameses.clfc.android.db;

import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;
import com.rameses.util.MapProxy;

public class DBLocationTracker extends AbstractDBMapper  
{
	public String getTableName() { return "location_tracker"; }

//	public int getCountByCollectorid(String id) throws Exception {
//		String sql = "select * from "+ getTableName() +" where collectorid=?";
//		DBContext ctx = createDBContext();
//		try {
//			return ctx.getCount(sql, new Object[]{id});
//		} catch(Exception e) {
//			throw e; 
//		} finally {
//			if (isCloseable()) ctx.close(); 
//		}
//	}
	
	public int getLastSeqnoByCollectorid(String id) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE collectorid=? ORDER BY seqno DESC LIMIT 1";
			List<Map> list = ctx.getList(sql, new Object[]{id});
			if (list == null || list.isEmpty()) return 0;
			else {
				Map item = list.get(0);
				return MapProxy.getInteger(item, "seqno");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getLocationTrackers(int limit) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" ORDER BY seqno ";
			if (limit > 0) sql += "LIMIT "+limit;
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
	
	public List<Map> getLocationTrackers() throws Exception {
		return getLocationTrackers(0);
	}
	
	public boolean hasLocationTrackers() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" LIMIT 1";
			return (ctx.getCount(sql, new Object[]{}) > 0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
}
