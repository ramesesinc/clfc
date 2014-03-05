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
	private SQLTransaction trackerdb;
	private DBContext clfcdb;
	private DBContext trackerdb2 = new DBContext("clfctracker.db");
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private String trackerid;
	private Map map;
	private Map params = new HashMap();
	private Map response = new HashMap();
	private int listSize;
	private LoanLocationService svc = new LoanLocationService();
	private List<Map> list; 
	private boolean hasLocationTrackers = false;
	
	public boolean serviceStarted = false;
	
	public BroadcastLocationService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() { 
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
		} 
		if (serviceStarted == false) {
			serviceStarted = true;
			Platform.getTaskManager().schedule(runnableImpl, 0);
		}
	}

	private Runnable runnableImpl = new Runnable() 
	{
		public void run() {
//			System.out.println("PostLocationTracker");
			synchronized (TrackerDB.LOCK) {
				trackerdb = new SQLTransaction("clfctracker.db");
				try {
					trackerdb.beginTransaction();
					execTracker(trackerdb);
					
					locationTracker.setDBContext(trackerdb.getContext());
					hasLocationTrackers = locationTracker.hasLocationTrackers();
					
					trackerdb.commit();
				} catch (Throwable t) {
					t.printStackTrace();
				} finally { 
					trackerdb.endTransaction();
				}
			}
			
			if (hasLocationTrackers) {
				Platform.getTaskManager().schedule(runnableImpl, 5000);
			} else {
				serviceStarted = false;
			}
		}
		
		private void execTracker(SQLTransaction trackerdb) throws Exception {
			locationTracker.setDBContext(trackerdb.getContext());
			
			list = locationTracker.getLocationTrackers(5);
			if (!list.isEmpty()) {
				listSize = list.size();
				for (int i=0; i<listSize; i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("objid", map.get("objid").toString());
					params.put("trackerid", map.get("trackeridb").toString());
					params.put("longitude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitude", Double.parseDouble(map.get("latitude").toString()));
					params.put("state", 1);
					
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
	};
	
}
