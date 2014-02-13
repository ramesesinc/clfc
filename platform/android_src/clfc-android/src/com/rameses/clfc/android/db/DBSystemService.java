package com.rameses.clfc.android.db;

import java.util.HashMap;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBSystemService extends AbstractDBMapper 
{
	public String getTableName() { return "system"; }
	
	public String getTrackerid() throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT value FROM "+ getTableName() +" WHERE name=?";
			Map map = ctx.find(sql, new Object[]{"trackerid"});
			System.out.println("map -> "+map);
			return map.get("value").toString();
			//return (ctx.getCount(sql, new Object[]{}) > 0);
		} catch(Exception e) {
			throw e; 
		} finally {
			ctx.close(); 
		}	
	}
}
