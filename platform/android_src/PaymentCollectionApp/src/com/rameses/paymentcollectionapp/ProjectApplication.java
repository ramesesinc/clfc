package com.rameses.paymentcollectionapp;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rameses.client.android.Loaders;
import com.rameses.client.android.Logger;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.Task;
import com.rameses.client.android.UIApplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class ProjectApplication extends UIApplication {
	private Context context = this;
	private WaiterTask waiter;
	private TickerThread ticker;
	private double longitude = 0.00;
	private double latitude = 0.00;
	private Activity currentActivity = null;
	private ExecutorService tickerPool = null;
	private boolean isIdleDialogShowing = false;
	private boolean isWaiterRunning = false;
	private boolean isTickerRunning = false; 
	private int tickerCount = 0;
	private int networkStatus = 0;
	private MySQLiteHelper dbHelper = new MySQLiteHelper(context);
	private LocationManager locationManager;
	private Location currentLocation = null;
	private String GPS = LocationManager.GPS_PROVIDER;
	private String PASSIVE = LocationManager.PASSIVE_PROVIDER;
	private NetworkLocationProvider networkLocationProvider;
	private NetworkChecker networkChecker;
	private BackgroundProcessUploading uploading;
	private LocationTracker locationTracker;
	private Task task = new Task() {
		@Override
		public void run() {
			System.out.println("starting new task");
		}
	};
	
	@Override
	protected void onCreateProcess() {
		super.onCreateProcess();
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		networkLocationProvider = new NetworkLocationProvider(this);
		networkChecker = new NetworkChecker(this);
		uploading = new BackgroundProcessUploading(this);
		locationTracker = new LocationTracker(this);
		Loaders.register(new LoaderRegistration());
	}
	
	@Override
	protected void beforeLoad(Properties appenv) {
		super.beforeLoad(appenv);
		System.out.println("calling application load");
		appenv.put("app.context", "clfc");
		appenv.put("app.cluster", "osiris3");
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		appenv.put("app.host", ApplicationUtil.getAppHost(context, networkStatus));
		//db.close();
	}
	
	@Override
	public void load() {
		System.out.println("calling load");
		super.load();
	}
	
	@Override
	public Logger createLogger() {
		Logger logger = Logger.create("clfclog.txt");
		logger.enable();
		return logger;
	}
	
	@Override
	protected void afterLoad() {
		startGettingLocation(); 
		//networkLocationProvider.startNetworkLocationLocator();
		networkChecker.startNetworkChecker();
		uploading.startBackgroundProcess();
		//locationTracker.startLocationTracker();
		getTaskManager().schedule(task, 0, 1000);
		//getTaskManager().schedule(locationTrackerRunnable, 0, 500);
		//getTaskManager().schedule(locationTrackerBroadcastRunnable, 0, 1000);
		System.out.println("finishing application load");
	}
	
	public double getLongitude() { return this.longitude; }
	public double getLatitude() { return this.latitude; }

	public void setIsIdleDialogShowing(boolean isIdleDialogShowing) {
		this.isIdleDialogShowing = isIdleDialogShowing;
	}	
	
	public boolean getIsIdleDialogShowing() { return this.isIdleDialogShowing; }
	public boolean getIsWaiterRunning() { return this.isWaiterRunning; }
	public boolean getIsTickerRunning() { return this.isTickerRunning; }
	
	public int getTickerCount() { return this.tickerCount; }
	
	public void setNetworkStatus(int networkStatus) {
		this.networkStatus = networkStatus;
	}
	public int getNetworkStatus() { return this.networkStatus; }
	
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	public void setLocation(Location location) {
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		currentLocation = location;
	}
	
	public Activity getCurrentActivity() { return this.currentActivity; }
	
	public Application getContext() { return this; }
	
	public void showIdleDialog(Activity activity) {
		isIdleDialogShowing = true;
		final Activity a = activity;
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				
				String content = "Collector: "+SessionContext.getProfile().getFullName()+"\n\nTo resume your session, enter your password";
				ApplicationUtil.showIdleDialog(a, content);
			}
		});
	}	
	
	public void startGettingLocation() {
		locationManager.requestLocationUpdates(GPS, 0, 0, new DeviceLocationListener());
		//locationManager.requestLocationUpdates(NETWORK,  0, 0, new DeviceLocationListener());
		locationManager.requestLocationUpdates(PASSIVE, 0, 0, new DeviceLocationListener());
	}
	
	public void startTicker() {
		isTickerRunning = true;
		tickerCount = 0;
		ticker = new TickerThread();
		tickerPool = Executors.newSingleThreadExecutor();
		tickerPool.submit(ticker);
	}
	
	public void stopTicker() {
		isTickerRunning = false;
		ticker.softStop();
		tickerPool.shutdown();
		tickerPool = null;
	}
	
	private class TickerThread extends Thread {
		private boolean stop = false;
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				tickerCount += 1;
				if (!stop) {
					tickerPool.submit(ticker);					
				}
			} catch(Exception e) {}
		}
		
		public synchronized void softStop() {
			stop = true;
		}		
	}
	
	public void startWaiter() {
		isWaiterRunning = true;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		waiter = new WaiterTask(dbHelper.getSessionTimeout(db)*1000);
		db.close();
		getTaskManager().schedule(waiter, 0, 1000); 
		//threadPool = Executors.newSingleThreadExecutor();
		//threadPool.submit(waiter);
	}
	
	public void touch() {
		waiter.touch();
	}
	
	public void stopWaiter() {
		isWaiterRunning = false;
		waiter.cancel();
	}
	
	private class WaiterTask extends Task {
		private long period;
		private long counter = 0;
		
		public WaiterTask(long period) {
			this.period = period;
			System.out.println("period = "+period);
		}

		@Override
		public void run() {
			System.out.println("starting task");
			/*try {
				System.out.println("counter = "+counter);
				if (counter >= period) {
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					dbHelper.setIdleState(db);
					db.close();
					this.cancel();
				}
				counter++;
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			System.out.println("finishing task");
		}
		
		public synchronized void touch() {
			counter = 0;
		}

		@Override
		public void cancel() {
			super.cancel();
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			String state = dbHelper.getAppState(db);
			db.close();
			if (state.equals("idle") && !isIdleDialogShowing) {
				stopWaiter();
				showIdleDialog(getCurrentActivity());
			}
		}
	}
	
	private class DeviceLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			float accuracy = location.getAccuracy();
			if (Float.compare(accuracy, 0.0f) > 0 && Float.compare(accuracy, 25.0f) <= 0) {
				if (currentLocation == null || location.getTime() > currentLocation.getTime()) {
					setLocation(location); 
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("provider = "+provider);
		}
		
	}
}
