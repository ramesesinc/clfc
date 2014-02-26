package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rameses.client.android.SessionContext;
import com.rameses.client.interfaces.UserProfile;
import com.rameses.service.ServiceProxy;

public class LocationTracker {
	private ProjectApplication application;
	private Context context;
	private MySQLiteHelper dbHelper;
	
	private Runnable locationTrackerRunnable = new Runnable() {
		@Override
		public void run() {
			if (application.getLongitude() > 0 && application.getLatitude() > 0) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				UserProfile profile = SessionContext.getProfile();
				String collectorid = (profile == null? null : profile.getUserId());//dbHelper.getCollectorid(db);
				if (collectorid != null) {
					//System.out.println("collectorid = "+collectorid);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("objid", "TRCK"+UUID.randomUUID());
					params.put("seqno", dbHelper.countLocationTrackerByCollectorid(db, collectorid)+1);
					params.put("trackerid", dbHelper.getTrackerid(db));
					params.put("collectorid", collectorid);
					params.put("longitude", application.getLongitude());
					params.put("latitude", application.getLatitude());
					
					dbHelper.insertLocationTracker(db, params);
					db.close();
				}
			}
			try {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				int timeout = dbHelper.getTrackerTimeout(db);
				db.close();
				Thread.sleep(timeout*1000);
			} catch (Exception e) { 
				Log.w("locationTrackerRunnable", "");
				LogUtil.log("locationTrackerRunnable");
			}
		}
	};
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
	};
	
	public LocationTracker(ProjectApplication application) {
		this.application = application;
		context = application;
		dbHelper= new MySQLiteHelper(context);
	}
	
	public void startLocationTracker() {
		application.getTaskManager().schedule(locationTrackerRunnable, 0, 500);
	}
}
