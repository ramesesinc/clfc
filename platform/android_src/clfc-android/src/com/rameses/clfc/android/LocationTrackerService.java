package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.location.Location;
import android.os.Handler;

import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.interfaces.UserProfile;
import com.rameses.db.android.ExecutionHandler;
import com.rameses.db.android.SQLExecutor;
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
			new RunnableImpl().run(); 
		} 
	}
	
	
	private class RunnableImpl implements Runnable 
	{
		LocationTrackerService root = LocationTrackerService.this;
		
		public void run() {
			try {
				SQLTransaction txn = new SQLTransaction("clfc.db"); 
				txn.execute(new ExecutionHandlerImpl()); 
			} catch(Throwable t) {
				t.printStackTrace();
			}
			
			try {
				root.handler.postDelayed(this, 500); 
			} catch(Throwable t) {
				t.printStackTrace();
			}			
		}	
	}
	
	private class ExecutionHandlerImpl implements ExecutionHandler
	{
		@Override
		public void execute(SQLExecutor sqlexec) throws Exception {
			AppSettingsImpl sets = (AppSettingsImpl) Platform.getApplication().getAppSettings();
			
			Location loc = NetworkLocationProvider.getLocation();
			double lng = (loc == null? 0.0: loc.getLongitude());
			double lat = (loc == null? 0.0: loc.getLatitude());
			if (lng > 0.0 && lat > 0.0) {				
				UserProfile profile = SessionContext.getProfile();
				String collectorid = (profile == null? null : profile.getUserId());
				if (collectorid != null) {
					DBLocationTracker dbloc = new DBLocationTracker();
					dbloc.setDBContext(sqlexec.getContext());
					int count = dbloc.getCountByCollectorid(collectorid);
												
					Map params = new HashMap();
					params.put("objid", "TRCK"+UUID.randomUUID());
					params.put("seqno",  count+1);
					params.put("trackerid", sets.getString("trackerid"));
					params.put("collectorid", collectorid);
					params.put("longitude", lng);
					params.put("latitude", lat);
					
					DBLocationTracker lt = new DBLocationTracker();
					lt.setDBContext(sqlexec.getContext());
					lt.create(params);  
				}
			}
			
			int timeout = 1;
			try { 
				timeout = sets.getTrackerTimeout(); 
			} catch(Throwable t) {;} 
			
			try {
				Thread.sleep(timeout*1000);
			} catch(Throwable t) {;}			
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

