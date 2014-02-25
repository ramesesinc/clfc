package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.os.Handler;

import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.client.android.Location;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.interfaces.UserProfile;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

class LocationTrackerService 
{
	private ApplicationImpl app;
	private AppSettingsImpl appSettings;
	private Handler handler;
	private SQLTransaction trackerdb = new SQLTransaction("clfctracker.db");
	private DBContext clfcdb = new DBContext("clfc.db");
	private List tables;
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private DBSystemService systemSvc = new DBSystemService();
	private int count;
	private int timeout;
	private String trackerid;
	private String collectorid;
	private Location location;
	private UserProfile profile;
	private double lng;
	private double lat;
	private Map params = new HashMap();
	
	public LocationTrackerService(ApplicationImpl app) {
		this.app = app;
		appSettings = (AppSettingsImpl) app.getAppSettings();
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			Platform.getTaskManager().schedule(runnableImpl, 0);
		} 
	}	
	
	private Runnable runnableImpl = new Runnable() {
//		LocationTrackerService root = LocationTrackerService.this;
		
		public void run() {
			try {
				trackerdb.beginTransaction();
				runImpl();
				trackerdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfctracker.db"); 
//				txn.execute(new ExecutionHandlerImpl()); 
			} catch(Throwable t) {
				t.printStackTrace();
			} finally {
				trackerdb.endTransaction();
				clfcdb.close();
			}
			
			timeout = 1;
			try { 
				timeout = appSettings.getTrackerTimeout(); 
			} catch(Throwable t) {;} 
			
			Platform.getTaskManager().schedule(runnableImpl, timeout*1000);		
		}	
		
		private void runImpl() throws Exception {
			tables = trackerdb.getList("SELECT * FROM sqlite_master WHERE type='table'");
			
			location = NetworkLocationProvider.getLocation();
			lng = (location == null? 0.0: location.getLongitude());
			lat = (location == null? 0.0: location.getLatitude());
			if (lng > 0.0 && lat > 0.0) {				
				profile = SessionContext.getProfile();
				collectorid = (profile == null? null : profile.getUserId());
				if (collectorid != null) {					
					locationTracker.setDBContext(trackerdb.getContext());
					count = locationTracker.getCountByCollectorid(collectorid);					
					
					systemSvc.setDBContext(clfcdb);
					systemSvc.setCloseable(false);
					
					trackerid = systemSvc.getTrackerid();
					if (trackerid == null) return;
																	
					params.clear();
					params.put("objid", "TRCK"+UUID.randomUUID());
					params.put("seqno",  count+1);
					params.put("trackerid", trackerid);
					params.put("collectorid", collectorid);
					params.put("longitude", lng);
					params.put("latitude", lat);
					params.put("state", location.getStatus());
					
					trackerdb.insert("location_tracker", params);
				}
			}	
		}
	};
	
}

