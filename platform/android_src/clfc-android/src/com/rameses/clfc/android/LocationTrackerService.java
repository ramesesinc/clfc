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
	private Handler handler;
	
	public LocationTrackerService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			Platform.getTaskManager().schedule(new RunnableImpl(), 0);
		} 
	}
	
	
	private class RunnableImpl implements Runnable 
	{
		LocationTrackerService root = LocationTrackerService.this;
		
		public void run() {
			SQLTransaction trackerdb = new SQLTransaction("clfctracker.db");
			DBContext clfcdb = new DBContext("clfc.db");
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

			AppSettingsImpl sets = (AppSettingsImpl) Platform.getApplication().getAppSettings();
			int timeout = 1;
			try { 
				timeout = sets.getTrackerTimeout(); 
			} catch(Throwable t) {;} 
			
			Platform.getTaskManager().schedule(new RunnableImpl(), timeout*1000);		
		}	
		
		private void runImpl(SQLTransaction trackerdb, DBContext clfcdb) throws Exception {
			List tables = trackerdb.getList("SELECT * FROM sqlite_master WHERE type='table'");
			
			Location loc = NetworkLocationProvider.getLocation();
			double lng = (loc == null? 0.0: loc.getLongitude());
			double lat = (loc == null? 0.0: loc.getLatitude());
			if (lng > 0.0 && lat > 0.0) {				
				UserProfile profile = SessionContext.getProfile();
				String collectorid = (profile == null? null : profile.getUserId());
				if (collectorid != null) {
					DBLocationTracker dbloc = new DBLocationTracker();
					dbloc.setDBContext(trackerdb.getContext());
					int count = dbloc.getCountByCollectorid(collectorid);
					
					DBSystemService dbSys = new DBSystemService();
					dbSys.setDBContext(clfcdb);
					dbSys.setCloseable(false);
					
					String trackerid = dbSys.getTrackerid();
					if (trackerid == null) return;
																	
					Map params = new HashMap();
					params.put("objid", "TRCK"+UUID.randomUUID());
					params.put("seqno",  count+1);
					params.put("trackerid", trackerid);
					params.put("collectorid", collectorid);
					params.put("longitude", lng);
					params.put("latitude", lat);
					params.put("state", loc.getStatus());
					
					trackerdb.insert("location_tracker", params);
				}
			}	
		}
	}
	
	
	/*
	private Runnable locationTrackerBroadcastRunnable = new Runnable() {
		@Override
		public void run() {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor locationTracker = dbHelper.getLocationTrackerByCollectorid(db, SessionContext.getProfile().getUserId()+"");
			db.close();
			
			if (locationTracker != null && locationTracker.getCount() > 0) {
				locationTracker.moveToFirst();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("objid", locationTracker.getString(locationTracker.getColumnIndex("objid")));
				params.put("trackerid", locationTracker.getString(locationTracker.getColumnIndex("trackerid")));
				params.put("longitude", locationTracker.getString(locationTracker.getColumnIndex("longitude")));
				params.put("latitude", locationTracker.getString(locationTracker.getColumnIndex("latitude")));
				
				ServiceProxy proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
				for (int i = 0; i < 10; i++) {
					try {
						Object response = proxy.invoke("postLocation", new Object[]{params});
						Map<String, Object> result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							db = dbHelper.getReadableDatabase();
							dbHelper.removeLocationTrackerById(db, params.get("objid").toString());
							db.close();
						}
						break;
					} catch (Exception e) { 
						Log.w("locationTrackerBroadcastRunnable", ""); 
						LogUtil.log("locationTrackerBroadcastRunnable");
					}
				}
				locationTracker.close();
			}
		}
	};*/
	
}

