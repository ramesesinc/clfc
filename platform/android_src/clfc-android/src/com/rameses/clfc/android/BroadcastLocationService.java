package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.services.LoanLocationService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

public class BroadcastLocationService 
{
	private ApplicationImpl app;
	private Handler handler;
	
	public BroadcastLocationService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			Platform.getTaskManager().schedule(new RunnableImpl(), 0, 5000);
		} 
	}

	private class RunnableImpl implements Runnable
	{
		public void run() {
//			System.out.println("PostLocationTracker");
			SQLTransaction trackerdb = new SQLTransaction("clfctracker.db");
			DBContext clfcdb = new DBContext("clfc.db");
			try {
				trackerdb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl(trackerdb, clfcdb);
				trackerdb.commit();
//				clfcdb.commit();
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("[BroadcastLocation.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
			} finally {
				trackerdb.endTransaction();
				clfcdb.close();
			}
		}
		
		private void runImpl(SQLTransaction trackerdb, DBContext clfcdb) throws Exception {
			DBLocationTracker dbLt = new DBLocationTracker();
			dbLt.setDBContext(trackerdb.getContext());
			
			List<Map> list = dbLt.getLocationTrackers();
			if (!list.isEmpty()) {
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(clfcdb);
				dbSys.setCloseable(false);
				
				String trackerid = dbSys.getTrackerid();
				Map map;
				Map params = new HashMap();
				Map response = new HashMap();
				LoanLocationService svc = new LoanLocationService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("objid", map.get("objid").toString());
					params.put("trackerid", trackerid);
					params.put("longitude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitude", Double.parseDouble(map.get("latitude").toString()));
					params.put("state", map.get("state"));
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.postLocation(params);
							break;
						} catch (Throwable e) {;}
					}
					
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						trackerdb.delete("location_tracker", "objid=?", new Object[]{map.get("objid").toString()});
					}
				}
			}
		}
	}
}
