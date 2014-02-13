package com.rameses.clfc.android.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rameses.db.android.DBContext;

public class DBRouteService extends AbstractDBMapper 
{
	public String getTableName() { return "route"; }
	
	public List<Map> getRouteByCollectorid(String collectorid) throws Exception {
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT * FROM "+getTableName()+" ORDER BY routedescription";
			Map params = new HashMap();
			params.put("collectorid", collectorid);
			return ctx.getList(sql, params);
		} catch(Exception e) {
			throw e; 
		} finally {
			ctx.close(); 
		}	
	}
}
