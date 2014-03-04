package com.example.testapp2;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.widget.Toast;

import com.rameses.client.android.Platform;
import com.rameses.client.android.UIActivity;
import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

public class TrackerService 
{
	private TrackerApplication application;
	private String sessionid = "";
	private ServiceProxy proxy;
	private ConnectivityManager connectivityManager;
	private LocationManager locationManager;
	
	private double longitude = 0.00;
	private double latitude = 0.00;
	private boolean isThreadRunning = false;
	private Handler handler = new Handler();
	
	TrackerService(TrackerApplication application) { 
		this.application = application;
		sessionid = UUID.randomUUID().toString();
		connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
		System.out.println("location manager = "+locationManager);
		
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("app.context", "clfc");
//		config.put("app.host", "121.97.60.200:8070");
		config.put("app.host", "clfc.ramesesinc.com");
		config.put("app.cluster", "osiris3");
		config.put("readTimeout", "20000");
		LogUtil.log("passing 5\n");
		
		proxy = (ServiceProxy) new ScriptServiceContext(config).create("TestMobileTrackerService03");
		LogUtil.log("Service starting");
		
		System.out.println("current activity -> "+application.getCurrentActivity());		
//		NetworkLocationProvider.setCurrentActivity(((TrackerApplication) Platform.getApplication()).getCurrentActivity());
		NetworkLocationProvider.setHandler(handler);  
		NetworkLocationProvider.setEnabled(true); 
		LogUtil.log("network location provider started");
	}
	
	public void start() {
		Platform.getTaskManager().schedule(new ActionProcess(), 10000, 10000);
	}
	
	private class ActionProcess implements Runnable
	{
		public void run() {
			try {
				execute();
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}
		
		private void execute() {
			Location loc = NetworkLocationProvider.getLocation();
			if (loc == null) {
				final String str = "Location is null. retrieve on next schedule";
				final UIActivity activity = ((TrackerApplication) Platform.getApplication()).getCurrentActivity();
				activity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
					}
				});
				System.out.println(str);
				return;
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objid", sessionid);
			params.put("accuracy", loc.getAccuracy());			
			params.put("lng", loc.getLongitude());
			params.put("lat", loc.getLatitude());
			params.put("time", loc.getTime());
			
			for (int i = 0; i < 10; i++) {
				try {
					proxy.invoke("log", new Object[]{params});
					break;
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}		
		}
		
	}
	
}
