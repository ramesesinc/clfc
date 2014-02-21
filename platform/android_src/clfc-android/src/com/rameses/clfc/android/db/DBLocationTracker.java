package com.rameses.clfc.android.db;

import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBLocationTracker extends AbstractDBMapper  
{
	public String getTableName() { return "location_tracker"; }

	public int getCountByCollectorid(String id) throws Exception {
		String sql = "select * from "+ getTableName() +" where collectorid=?";
		DBContext ctx = createDBContext();
		try {
			return ctx.getCount(sql, new Object[]{id});
		} catch(Exception e) {
			throw e; 
		} finally {
			if (isCloseable()) ctx.close(); 
		}
	}
	
	public List<Map> getLocationTrackers() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" ORDER BY seqno";
			return ctx.getList(sql, new Object[]{});
		} catch (Exception e) {
			throw e;
		} finally {
			if (isCloseable()) ctx.close();
		}
	}
}
