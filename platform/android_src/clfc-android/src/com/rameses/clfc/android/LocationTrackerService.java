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
import com.rameses.client.android.Task;
import com.rameses.client.interfaces.UserProfile;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

class LocationTrackerService 
{
	private ApplicationImpl app;
	private AppSettingsImpl settings;
	private Handler handler;
//	private List tables;
	private SQLTransaction trackerdb;
	private DBContext clfcdb;
	private DBLocationTracker locationTracker = new DBLocationTracker();
	private DBSystemService systemSvc = new DBSystemService();
	private DBPrevLocation prevLocation = new DBPrevLocation();
	private int seqno;
	private int timeout;
	private String trackerid;
	private String collectorid;
	private Location location;
	private UserProfile profile;
	private double lng;
	private double lat;
	private double prevlng = 0.0;
	private double prevlat = 0.0;
	private Map params = new HashMap();
	private Map prevlocation;	
	private Task actionTask;
	
	private boolean serviceStarted = false;
	
	public LocationTrackerService(ApplicationImpl app) {
		this.app = app;
		settings = (AppSettingsImpl) app.getAppSettings();
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
		} 
		
		if (serviceStarted == false) {
			serviceStarted = true;
			timeout = settings.getTrackerTimeout()*1000;
			createTask();
			System.out.println("timeout -> "+timeout);
			Platform.getTaskManager().schedule(actionTask, 1000, timeout);
		}
	}
	
	public void restart() {
		if (serviceStarted == true) {
			actionTask.cancel();
			actionTask = null;
			serviceStarted = false;
		}
		start();
	}
	
	private void createTask() {
		actionTask = new Task() {
			public void run() {
				System.out.println("[LocationTrackerService.run]");
				String trackerid = null;
				synchronized (MainDB.LOCK) {
					clfcdb = new DBContext("clfc.db");
					systemSvc.setDBContext(clfcdb);
					
					trackerid = settings.getTrackerid();
					/*try {
						trackerid = systemSvc.getTrackerid();
						
					} catch (Throwable t) {
						t.printStackTrace();
					}*/
				}
				
				if (trackerid == null) return;
				
				synchronized (TrackerDB.LOCK) {
					trackerdb = new SQLTransaction("clfctracker.db");
					try {
						trackerdb.beginTransaction();
						execTracker(trackerdb, trackerid);
						trackerdb.commit();
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						trackerdb.endTransaction();
					}
				}
			}
					
			private void execTracker(SQLTransaction trackerdb, String trackerid) throws Exception {

				location = NetworkLocationProvider.getLocation();
				System.out.println("location -> "+location); 
				lng = (location == null? 0.0: location.getLongitude());
				lat = (location == null? 0.0: location.getLatitude());

				prevLocation.setDBContext(trackerdb.getContext());
				prevLocation.setCloseable(false);			
				prevlocation = prevLocation.getPrevLocation();			
				if (prevlocation != null && !prevlocation.isEmpty()) {
					prevlng = Double.parseDouble(prevlocation.get("longitude").toString());
					prevlat = Double.parseDouble(prevlocation.get("latitude").toString());
				}
//				System.out.println("lng->"+lng+", lat->"+lat+", prevlng->"+prevlng+", prevlat->"+prevlat);
				if (lng > 0.0 && lat > 0.0 && lng != prevlng && lat != prevlat) {				
					profile = SessionContext.getProfile();
					collectorid = (profile == null? null : profile.getUserId());
					if (collectorid != null) {					
						locationTracker.setDBContext(trackerdb.getContext());
						seqno = locationTracker.getLastSeqnoByCollectorid(collectorid);	
																		
						params.clear();
						params.put("objid", "TRCK"+UUID.randomUUID());
						params.put("seqno",  seqno+1);
						params.put("trackerid", trackerid);
						params.put("collectorid", collectorid);
						params.put("longitude", lng);
						params.put("latitude", lat);
						
//						System.out.println("inserting paramas = "+params);
						trackerdb.insert("location_tracker", params);

						params.clear();
						params.put("longitude", lng);
						params.put("latitude", lng);
						if (prevlocation == null || prevlocation.isEmpty()) {
							params.put("objid", "PL"+UUID.randomUUID().toString());
							trackerdb.insert("prev_location", params);
						} else if (prevlocation != null && !prevlocation.isEmpty()) {
							trackerdb.update("prev_location", "objid='"+prevlocation.get("objid").toString()+"'", params);
						} 
						
						Platform.getMainActivity().getHandler().post(new Runnable() {
							public void run() {
								app.broadcastLocationSvc.start();
							}
						});
					}
				}	
			}
		};
	}
}

