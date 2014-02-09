package com.rameses.clfc.android.db;

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
			ctx.close(); 
		}
	}
	
}
