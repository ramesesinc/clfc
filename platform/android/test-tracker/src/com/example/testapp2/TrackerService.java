package com.example.testapp2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

public class TrackerService extends Service 
{
	private Context context = this;
	private String sessionid = "";
	private ServiceProxy proxy;
	private ConnectivityManager connectivityManager;
	private LocationManager locationManager;
	
	private double longitude = 0.00;
	private double latitude = 0.00;
	private boolean isThreadRunning = false;
	
	private Timer timer = new Timer();
	private Handler handler = new Handler();
	
	@Override
	public int onStartCommand(Intent intent,int flags, int startId) {
		if (!isThreadRunning) {
			startTracker();
		}
		
		return START_NOT_STICKY;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		sessionid = UUID.randomUUID().toString();
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("app.context", "clfc");
		config.put("app.host", "121.97.60.200:8070");
		config.put("app.cluster", "osiris3");
		config.put("readTimeout", "20000");
		LogUtil.log("passing 5\n");
		
		proxy = (ServiceProxy) new ScriptServiceContext(config).create("TestMobileTrackerService");
		LogUtil.log("Service starting");
		
		TrackerApplication application = (TrackerApplication) getApplication();
		NetworkLocationProvider.setCurrentActivity(application.getCurrentActivity());
		NetworkLocationProvider.setHandler(handler); 
		NetworkLocationProvider.setEnabled(true); 
		LogUtil.log("network location provider started");
	}
	
	@Override
	public void onDestroy() {
		LogUtil.log("Service stopping.");
		super.onDestroy(); 
		
		try { timer.cancel(); }catch(Throwable t){;}
		try { timer.purge(); }catch(Throwable t){;}
		timer = null;
		
		NetworkLocationProvider.setEnabled(false);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void startTracker() {
		isThreadRunning = true;
		TimerTask task = new TimerTask(){
			public void run() {
				try {
					execute();
				} catch(Throwable t) {
					t.printStackTrace();
				}
			}
		};
		timer.schedule(task, 10000, 10000); 
	}
	
	private void execute() {
		Location loc = NetworkLocationProvider.getLocation();
		if (loc == null) {
			System.out.println("Location is null. retrieve on next schedule");
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objid", sessionid);
		params.put("lng", longitude);
		params.put("lat", latitude);
		
		for (int i = 0; i < 10; i++) {
			try {
				proxy.invoke("log", new Object[]{params});
				break;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}		
	}
	
	private Runnable lockRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				String path = Environment.getExternalStorageDirectory().getAbsolutePath();
				File file = new File(path+"/", ".trackerlock");
				//System.out.println("application = "+getApplication());
				final TrackerApplication application = (TrackerApplication) getApplication();
				//TrackerApplication application = (TrackerApplication) getApplicationContext();
				final Activity activity = application.getCurrentActivity();
				if (file.exists()) {
					if (!application.getIsApplicationLock() && activity!= null) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								application.showLockDialog(activity);
							}
						});
					}
				} else {
					if (application.getIsApplicationLock()) {
						application.closeLockDialog();
					}
				}
			} catch (Exception e) { e.printStackTrace(); }
		}
	};
	
	/*
	private Runnable trackerRunnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("running runnable");
			Location location = null;
			String provider = "";
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (isNetworkEnabled == true) {
				provider = LocationManager.NETWORK_PROVIDER;
				try {
					
					locationManager.requestLocationUpdates(provider, 0, 0, locationListener, Looper.getMainLooper());
					location = locationManager.getLastKnownLocation(provider);
					System.out.println("location = "+location);
					if (location != null) {
						System.out.println("longtiude = "+location.getLongitude());
						System.out.println("latitude = "+location.getLatitude());
						longitude = location.getLongitude();
						latitude = location.getLatitude();
					}
					locationManager.removeUpdates(locationListener);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			/ **Map<String, Object> params = new HashMap<String, Object>();
			params.put("objid", sessionid);
			params.put("lng", longitude);
			params.put("lat", latitude);
			
			for (int i = 0; i < 10; i++) {
				try {
					//proxy.invoke("log", new Object[]{params});
					break;
				} catch (Exception e) {}
			}** /
		}
	};
	*/

	/*
	private class TrackerLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
	}
	*/
}
