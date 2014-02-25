package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.location.Location;
import android.os.Handler;

import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.clfc.android.db.DBPrevLocation;
import com.rameses.clfc.android.db.DBSystemService;
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
//	private List tables;
	private SQLTransaction trackerdb;
	private DBContext clfcdb;
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private DBSystemService systemSvc = new DBSystemService();
	private DBPrevLocation prevLocation = new DBPrevLocation();
	private int count;
	private int timeout;
	private String trackerid;
	private String collectorid;
	private Location location;
	private UserProfile profile;
	private double lng;
	private double lat;
	private double prevlng;
	private double prevlat;
	private Map params = new HashMap();
	private Map prevlocation;
	
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
			trackerdb = new SQLTransaction("clfctracker.db");
			clfcdb = new DBContext("clfc.db");
			try {
				trackerdb.beginTransaction();
				runImpl(trackerdb, clfcdb);
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
		
		private void runImpl(SQLTransaction trackerdb, DBContext clfcdb) throws Exception {
//			tables = trackerdb.getList("SELECT * FROM sqlite_master WHERE type='table'");
			
			location = NetworkLocationProvider.getLocation();
			lng = (location == null? 0.0: location.getLongitude());
			lat = (location == null? 0.0: location.getLatitude());

//			prevLocation.setDBContext(trackerdb.getContext());
//			prevLocation.setCloseable(false);			
//			prevlocation = prevLocation.getPrevLocation();			
//			if (prevlocation != null && !prevlocation.isEmpty()) {
//				prevlng = Double.parseDouble(prevlocation.get("longitude").toString());
//				prevlat = Double.parseDouble(prevlocation.get("latitude").toString());
//			}
			System.out.println("location -> "+location);
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
					
					System.out.println("inserting paramas = "+params);
					trackerdb.insert("location_tracker", params);
					
					Platform.getMainActivity().getHandler().post(new Runnable() {
						public void run() {
							app.broadcastLocationSvc.start();
						}
					});
//					params.clear();
//					params.put("longitude", lng);
//					params.put("latitude", lng);
//					if (prevlocation == null || prevlocation.isEmpty()) {
//						trackerdb.insert("prevlocation", params);
//					} else if (prevlocation != null && !prevlocation.isEmpty()) {
//						trackerdb.update("prev_location", null, params);
//					}
				}
			}	
		}
	};
	
}

