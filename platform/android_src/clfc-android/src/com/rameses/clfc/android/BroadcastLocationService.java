package com.rameses.clfc.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.clfc.android.services.LoanLocationService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

public class BroadcastLocationService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction trackerdb;
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private Map map;
	private Map params = new HashMap();
	private Map response = new HashMap();
	private int listSize;
	private LoanLocationService svc = new LoanLocationService();
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
			List<Map> list = new ArrayList<Map>();
			synchronized (TrackerDB.LOCK) {
				trackerdb = new SQLTransaction("clfctracker.db");
				locationTracker.setDBContext(trackerdb.getContext());
				try {
					trackerdb.beginTransaction();
					list = locationTracker.getLocationTrackers(5);					
					trackerdb.commit();
				} catch (Throwable t) {
					t.printStackTrace();
				} finally { 
					trackerdb.endTransaction();
				}
			}
			
			execTracker(list);
			
			synchronized (TrackerDB.LOCK) {
				DBContext ctx = new DBContext("clfctracker.db");
				locationTracker.setDBContext(ctx);
				try {
					hasLocationTrackers = locationTracker.hasLocationTrackers();
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			if (hasLocationTrackers == true) {
				Platform.getTaskManager().schedule(runnableImpl, 5000);
			} else if (hasLocationTrackers == false) {
				serviceStarted = false;
			}
		}
		
		private void execTracker(List<Map> list) {			
			if (!list.isEmpty()) {
				listSize = list.size();
				for (int i=0; i<listSize; i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("objid", map.get("objid").toString());
					params.put("trackerid", map.get("trackerid").toString());
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
						synchronized (TrackerDB.LOCK) {
							trackerdb = new SQLTransaction("clfctracker.db");
							locationTracker.setDBContext(trackerdb.getContext());
							try {
								trackerdb.beginTransaction();
								trackerdb.delete("location_tracker", "objid=?", new Object[]{map.get("objid").toString()});
								trackerdb.commit();
							} catch (Throwable t) {
								t.printStackTrace();
							} finally {
								trackerdb.endTransaction();
							}
						}
					}
				}
			}
		}
	};
	
}
