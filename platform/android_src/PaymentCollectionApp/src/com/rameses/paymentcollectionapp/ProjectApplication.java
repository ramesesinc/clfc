package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rameses.client.android.Loaders;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIApplication;
import com.rameses.client.interfaces.UserProfile;
import com.rameses.service.ServiceProxy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ProjectApplication extends UIApplication {
	private Context context = this;
	private WaiterThread waiter;
	private TickerThread ticker;
	private double longitude = 0.00;
	private double latitude = 0.00;
	private Activity currentActivity = null;
	private ExecutorService threadPool = Executors.newSingleThreadExecutor();
	private ExecutorService tickerPool = null;
	private boolean isIdleDialogShowing = false;
	private boolean isWaiterRunning = false;
	private boolean isTickerRunning = false; 
	private int tickerCount = 0;
	private int networkStatus = 0;
	private MySQLiteHelper dbHelper = new MySQLiteHelper(context);
	private ConnectivityManager connectivityManager;
	private NetworkInfo networkInfo;
	private LocationManager locationManager;
	private Location currentLocation = null;
	private String GPS = LocationManager.GPS_PROVIDER;
	private String NETWORK = LocationManager.NETWORK_PROVIDER;
	private String PASSIVE = LocationManager.PASSIVE_PROVIDER;
	
	@Override
	protected void onCreateProcess() {
		super.onCreateProcess();
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
		super.load();
		System.out.println("calling load");
	}
	
	@Override
	protected void afterLoad() {
		startGettingLocation(); 
		getTaskManager().schedule(networkCheckerRunnable, 0, 5000);
		getTaskManager().schedule(pendingVoidPaymentsRunnable, 0, 1000);
		getTaskManager().schedule(pendingPaymentsRunnable, 0, 500);
		getTaskManager().schedule(pendingRemarksRunnable, 0, 500);
		getTaskManager().schedule(networkLocationRunnable, 0, 2000);
		getTaskManager().schedule(locationTrackerRunnable, 0, 500);
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
	
	public int getNetworkStatus() { return this.networkStatus; }
	
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	private void setLocation(Location location) {
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
	
	private Runnable pendingVoidPaymentsRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingVoidPayments = dbHelper.getPendingVoidPayments(db);
			
			if (pendingVoidPayments != null && pendingVoidPayments.getCount() > 0) {
				pendingVoidPayments.moveToFirst();
				boolean isApproved = false;
				String objid = "";
				ServiceProxy proxy = null;
				do {
					objid = pendingVoidPayments.getString(pendingVoidPayments.getColumnIndex("objid"));
					params.clear();
					params.put("voidid", objid);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						isApproved = (Boolean) proxy.invoke("isVoidPaymentApproved", new Object[]{params});
					} catch (Exception e) {}
					finally {
						if (isApproved) {
							dbHelper.approveVoidPayment(db, objid);
						}
					}
				} while(pendingVoidPayments.moveToNext());
				pendingVoidPayments.close();
			}
			db.close();
		}
	};
	private Runnable pendingPaymentsRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingPayments = dbHelper.getPendingPayments(db);
			
			if (pendingPayments != null && pendingPayments.getCount() > 0) {
				pendingPayments.moveToFirst();
				Map<String, Object> payment = new HashMap<String, Object>();
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				String loanappid = "";
				String routecode = "";
				ServiceProxy proxy = null;
				do {
					params.clear();
					payment.clear();
					collectionsheet.clear();

					loanappid = pendingPayments.getString(pendingPayments.getColumnIndex("loanappid"));
					params.put("txndate", pendingPayments.getString(pendingPayments.getColumnIndex("txndate")));
					params.put("trackerid", pendingPayments.getString(pendingPayments.getColumnIndex("trackerid")));
					params.put("collectorid", pendingPayments.getString(pendingPayments.getColumnIndex("collectorid")));
					String mode = "OFFLINE";
					if (networkStatus == 1) mode = "ONLINE";
					params.put("mode", mode);
					
					routecode = pendingPayments.getString(pendingPayments.getColumnIndex("routecode"));
					payment = new HashMap<String, Object>();
					payment.put("objid", pendingPayments.getString(pendingPayments.getColumnIndex("objid")));
					payment.put("refno", pendingPayments.getString(pendingPayments.getColumnIndex("refno")));
					payment.put("txndate", pendingPayments.getString(pendingPayments.getColumnIndex("txndate")));
					payment.put("type", pendingPayments.getString(pendingPayments.getColumnIndex("paymenttype")));
					payment.put("amount", pendingPayments.getDouble(pendingPayments.getColumnIndex("paymentamount")));
					payment.put("paidby", pendingPayments.getString(pendingPayments.getColumnIndex("paidby")));
					payment.put("loanappid", pendingPayments.getString(pendingPayments.getColumnIndex("loanappid")));
					payment.put("detailid", pendingPayments.getString(pendingPayments.getColumnIndex("detailid")));
					payment.put("routecode", routecode);
					payment.put("isfirstbill", pendingPayments.getInt(pendingPayments.getColumnIndex("isfirstbill")));
					params.put("payment", payment);
					
					params.put("longitude", pendingPayments.getDouble(pendingPayments.getColumnIndex("longitude")));
					params.put("latitude", pendingPayments.getDouble(pendingPayments.getColumnIndex("latitude")));
					params.put("routecode", routecode);
					params.put("sessionid", pendingPayments.getString(pendingPayments.getColumnIndex("sessionid")));

					collectionsheet = new HashMap<String, Object>();
					collectionsheet.put("loanappid", loanappid);
					collectionsheet.put("acctname", pendingPayments.getString(pendingPayments.getColumnIndex("acctname")));
					collectionsheet.put("detailid", pendingPayments.getString(pendingPayments.getColumnIndex("detailid")));
					params.put("collectionsheet", collectionsheet);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("postPayment", new Object[]{params});
						Map<String, Object> result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approvePayment(db, pendingPayments.getString(pendingPayments.getColumnIndex("objid")));
						}
					} catch (Exception e) {}
					
				} while(pendingPayments.moveToNext());
				pendingPayments.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingPaymentsRunnable", ""); 
				LogUtil.log("pendingPaymentsRunnable");
			}
			db.close();
		}
	};
	private Runnable pendingRemarksRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingRemarks = dbHelper.getPendingRemarks(db);
			
			if (pendingRemarks != null && pendingRemarks.getCount() > 0) {
				pendingRemarks.moveToFirst();
				String loanappid = "";
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				ServiceProxy proxy = null;
				do {
					params.clear();
					loanappid = pendingRemarks.getString(pendingRemarks.getColumnIndex("loanappid"));
					params.put("txndate", pendingRemarks.getString(pendingRemarks.getColumnIndex("txndate")));
					params.put("remarks", pendingRemarks.getString(pendingRemarks.getColumnIndex("remarks")));
					params.put("collectorid", pendingRemarks.getString(pendingRemarks.getColumnIndex("collectorid")));
					params.put("sessionid", pendingRemarks.getString(pendingRemarks.getColumnIndex("sessionid")));
					params.put("longitude", pendingRemarks.getString(pendingRemarks.getColumnIndex("longitude")));
					params.put("latitude", pendingRemarks.getString(pendingRemarks.getColumnIndex("latitude")));
					String mode = "OFFLINE";
					if (networkStatus == 1) mode = "ONLINE";
					params.put("mode", mode);
					
					collectionsheet = new HashMap<String, Object>();
					collectionsheet.put("loanappid", loanappid);
					collectionsheet.put("detailid", pendingRemarks.getString(pendingRemarks.getColumnIndex("detailid")));
					collectionsheet.put("routecode", pendingRemarks.getString(pendingRemarks.getColumnIndex("routecode")));
					params.put("collectionsheet", collectionsheet);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("updateRemarks", new Object[]{params});
						Map<String, Object> result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approveRemark(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarks.moveToNext());
				pendingRemarks.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingRemarksRunnable", ""); 
				LogUtil.log("pendingRemarksRunnable");
			}
			db.close();
		}
	};
	private Runnable pendingRemarksRemovedRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingRemarksRemoved = dbHelper.getPendingRemarksRemoved(db);
			
			if (pendingRemarksRemoved != null && pendingRemarksRemoved.getCount() > 0) {
				pendingRemarksRemoved.moveToFirst();
				String loanappid = "";
				ServiceProxy proxy = null;
				do {
					params.clear();
					loanappid = pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("loanappid"));
					params.put("detailid", pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("detailid")));
									
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("removeRemarks", new Object[]{params});
						Map<String, Object>result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.removeRemarksRemovedByAppid(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarksRemoved.moveToNext());
				pendingRemarksRemoved.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingRemarksRemovedRunnable", ""); 
				LogUtil.log("pendingRemarksRemovedRunnable");
			}
			db.close();
		}
	};
	private Runnable networkLocationRunnable = new Runnable() {
		@Override
		public void run() {
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (isNetworkEnabled == true) {
				DeviceLocationListener locationListener = new DeviceLocationListener();
				float accuracy = 0.0f;
				Location location = null;
				do {
					locationManager.requestLocationUpdates(NETWORK, 0, 0, locationListener, Looper.getMainLooper());
					location = locationManager.getLastKnownLocation(NETWORK);
					accuracy = location.getAccuracy();
					//System.out.println("location accuracy = "+location.getAccuracy());
					locationManager.removeUpdates(locationListener);
				} while(location == null || Float.compare(accuracy, 0.0f) == 0 || Float.compare(accuracy, 25.0f) > 0);
				setLocation(location);
			}
		}
	};
	private Runnable locationTrackerRunnable = new Runnable() {
		@Override
		public void run() {
			if (longitude > 0 && latitude > 0) {
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
					params.put("longitude", longitude);
					params.put("latitude", latitude);
					
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
	private Runnable networkCheckerRunnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("running network checker");
			networkInfo = connectivityManager.getActiveNetworkInfo();
			String mode = "NOT CONNECTED";
			//System.out.println("network info = "+networkInfo);
			if (networkInfo != null && networkInfo.isConnected()) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					networkStatus = 1;
					mode = "ONLINE";
				} else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					networkStatus = 0;
					mode = "OFFLINE";
				}
			}
			final String m = mode;
			if (getCurrentActivity() != null) {
				getCurrentActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//ApplicationUtil.showShortMsg(((Context) getCurrentActivity()), "mode = "+m);
						View v = getCurrentActivity().findViewById(R.id.tv_mode);
						if (v != null) {
							((TextView) v).setText(m);
						}
					}
				});
			}
		}
	};
	
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
		waiter = new WaiterThread(dbHelper.getSessionTimeout(db)*1000);
		db.close();
		threadPool = Executors.newSingleThreadExecutor();
		threadPool.submit(waiter);
	}
	
	public void touch() {
		waiter.touch();
	}
	
	public void stopWaiter() {
		isWaiterRunning = false;
		waiter.softStop();
		threadPool.shutdown();
		threadPool = null;
	}
	
	private class WaiterThread extends Thread {
		private boolean stop = false;
		private long period;
		private long lastUsed;
		
		public WaiterThread(long period) {
			this.period = period;
		}
				
		public void run() {
			long idle = 0;
			this.touch();
			MySQLiteHelper dbHelper = new MySQLiteHelper(getContext());
			do {
				idle = System.currentTimeMillis() - lastUsed;
				if (idle > period) {
					idle = 0;
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					dbHelper.setIdleState(db);
					db.close();
					break;
				}
			} while(!stop);
			if (!stop) {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				String state = dbHelper.getAppState(db);
				db.close();
				if (state.equals("idle") && !isIdleDialogShowing) {
					stopWaiter();
					showIdleDialog(getCurrentActivity());
				}
			}
		}
		
		public synchronized void touch() {
			lastUsed = System.currentTimeMillis();
		}
		
		public synchronized void softStop() {
			stop = true;
		}
	}
	
	private class DeviceLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			float accuracy = location.getAccuracy();
			if (Float.compare(accuracy, 0.0f) > 0 && Float.compare(accuracy, 15.0f) <= 0) {
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
