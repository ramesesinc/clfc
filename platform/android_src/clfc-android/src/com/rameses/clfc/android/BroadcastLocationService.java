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
	private SQLTransaction trackerdb = new SQLTransaction("clfctracker.db");
	private DBContext clfcdb = new DBContext("clfc.db");
	private DBContext trackerdb2 = new DBContext("clfctracker.db");
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private DBSystemService systemSvc = new DBSystemService();
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
			if (serviceStarted == false) {
				serviceStarted = true;
				Platform.getTaskManager().schedule(runnableImpl, 0);
			}
		} 
	}

	private Runnable runnableImpl = new Runnable() 
	{
		public void run() {
//			System.out.println("PostLocationTracker");
			try {
				trackerdb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl();
				trackerdb.commit();
//				clfcdb.commit();
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("[BroadcastLocation.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
			} finally {
				trackerdb.endTransaction();
				clfcdb.close();
			}
			
			try {
				locationTracker.setDBContext(trackerdb2);
				hasLocationTrackers = locationTracker.hasLocationTrackers();
			} catch (Exception e) {;}
			
			if (hasLocationTrackers) {
				Platform.getTaskManager().schedule(runnableImpl, 5000);
			} else {
				serviceStarted = false;
			}
		}
		
		private void runImpl() throws Exception {
			locationTracker.setDBContext(trackerdb.getContext());
			
			list = locationTracker.getLocationTrackers();
			if (!list.isEmpty()) {
				systemSvc.setDBContext(clfcdb);
				systemSvc.setCloseable(false);
				
				trackerid = systemSvc.getTrackerid();
				listSize = list.size();
				for (int i=0; i<listSize; i++) {
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
	};
	
}
