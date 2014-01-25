package com.rameses.paymentcollectionapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rameses.client.android.DeviceAppLoader;
import com.rameses.client.interfaces.DeviceContext;
import com.rameses.service.ServiceProxy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectApplication extends Application implements DeviceContext {
	private Context context = this;
	private WaiterThread waiter;
	private NetworkCheckerThread networkChecker;
	//private LocationTrackerThread locationTracker;
	//private LocationTrackerBroadcastThread locationTrackerBroadcast;
	//private LocationGetterThread locationGetter;
	private TickerThread ticker;
	private double longitude = 0.00;
	private double latitude = 0.00;
	private Activity currentActivity = null;
	private ExecutorService threadPool = Executors.newSingleThreadExecutor();
	private ExecutorService networkThreadPool = null;
	//private ExecutorService locationUpdaterPool = null;
	//private ExecutorService backgroundProcessPool = null;
	//private ExecutorService locationTrackerPool = null;
	//private ExecutorService locationTrackerBroadcasPool = null;
	//private ExecutorService locationGetterPool = null;
	private ExecutorService tickerPool = null;
	private boolean isIdleDialogShowing = false;
	private boolean isLoginDialogShowing = false;
	//private boolean isBackgroundProcessRunning = false;
	//private boolean isLocationTrackerRunning = false;
	//private boolean isLocationTrackerBroadcastRunning = false;
	private boolean isWaiterRunning = false;
	private boolean isNetworkCheckerRunning = false;
	//private boolean isLocationGetterRunning = false;
	private boolean isTickerRunning = false; 
	private int tickerCount = 0;
	private int networkStatus = 0;
	private MySQLiteHelper dbHelper = new MySQLiteHelper(context);
	private ConnectivityManager connectivityManager;
	private NetworkInfo networkInfo;
	//private LocationManager locationManager;
	//private DeviceLocationListener locationListener = new DeviceLocationListener();
	
	@Override
	public void onCreate() {
		super.onCreate();
		Map<String, Object> env = new HashMap<String, Object>();
		env.put("app.context", "clfc");
		env.put("app.cluster", "osiris3");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		env.put("app.host", dbHelper.getOnlineHost(db)+":"+dbHelper.getPort(db));
		DeviceAppLoader.load(env, this);
		//connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		startNetworkChecker();
		//startLocationGetter();
		//startLocationTrackerBroadcast();
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() { return this.longitude; }
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() { return this.latitude; }
	
	public void setIsIdleDialogShowing(boolean isIdleDialogShowing) {
		this.isIdleDialogShowing = isIdleDialogShowing;
	}	
	
	public boolean getIsIdleDialogShowing() { return this.isIdleDialogShowing; }
	
	public void setIsLoginDialogShowing(boolean isLoginDialogShowing) {
		this.isLoginDialogShowing = isLoginDialogShowing;
	}
	
	public boolean getIsLoginDialogShowing() { return this.isLoginDialogShowing; }	
	//public boolean getIsBackgroundProcessRunning() { return this.isBackgroundProcessRunning; }
	//public boolean getIsLocationGetterRunnig() { return this.isLocationGetterRunning; }
	//public boolean getIsLocationTrackerRunning() { return this.isLocationTrackerRunning; }
	//public boolean getIsLocationTrackerBroadcastRunning() { return this.isLocationTrackerBroadcastRunning; }
	public boolean getIsWaiterRunning() { return this.isWaiterRunning; }	
	//public boolean getIsNetworkCheckerRunning() { return this.isNetworkCheckerRunning; }
	public boolean getIsTickerRunning() { return this.isTickerRunning; }
	
	public int getTickerCount() { return this.tickerCount; }
	
	public int getNetworkStatus() { return this.networkStatus; }
	
	public boolean getIsSettingsSet() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		boolean flag = dbHelper.isSettingsSet(db);
		db.close();
		return flag;
	}
	
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
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
				
				String content = "Collector: "+dbHelper.getCollectorName(db)+"\n\nTo resume your session, enter your password";
				ApplicationUtil.showIdleDialog(a, content);
			}
		});
	}	
	
	/*public void startBackgroundProcess() {
		isBackgroundProcessRunning = true;
		backgroundProcessPool = Executors.newSingleThreadExecutor();
		backgroundProcessPool.submit(backgroundProcessThread)
;	}
	
	public void stopBackgroundProcess() {
		isBackgroundProcessRunning = false;
		backgroundProcessPool.shutdown();
		backgroundProcessPool = null;
	}
	
	private Thread backgroundProcessThread = new Thread() {
		@Override
		public void run() {
			ServiceProxy postingProxy = null;
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Map<String, Object> params;
			Cursor voidPayments = dbHelper.getPendingVoidPayments(db);
			
			if (voidPayments != null && voidPayments.getCount() > 0) {
				voidPayments.moveToFirst();
				Boolean isApproved = false;
				String objid = "";
				do {
					params = new HashMap<String, Object>();
					objid = voidPayments.getString(voidPayments.getColumnIndex("objid"));
					params.put("voidid", objid);
					
					postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						isApproved = (Boolean) postingProxy.invoke("isVoidPaymentApproved", new Object[]{params});
					} catch (Exception e) {}
					finally {
						if (isApproved) {
							dbHelper.approveVoidPayment(db, objid);
						}
					}
				} while(voidPayments.moveToNext());
				voidPayments.close();
			}
			
			Cursor pendingPayments = dbHelper.getPendingPayments(db);
			
			Map<String, Object> collectionsheet;
			Map<String, Object> payment;
			Map<String, Object> note;
			Map<String, Object> remark;
			Object response;
			Map<String, Object> result;
			String loanappid = "";
			String routecode = ""; 
			
			if (pendingPayments != null && pendingPayments.getCount() > 0) {
				pendingPayments.moveToFirst();
				do {
					params = new HashMap<String, Object>();
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
					
					postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						response = postingProxy.invoke("postPayment", new Object[]{params});
						result = (Map<String, Object>) response;
						if (result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approvePayment(db, pendingPayments.getString(pendingPayments.getColumnIndex("objid")));
						}
					} catch (Exception e) {}
				} while(pendingPayments.moveToNext());
				pendingPayments.close();
			}

			Cursor pendingRemarks = dbHelper.getPendingRemarks(db);
			
			if (pendingRemarks != null && pendingRemarks.getCount() > 0) {
				pendingRemarks.moveToFirst();
				do {
					params = new HashMap<String, Object>();
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
					
					postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						response = postingProxy.invoke("updateRemarks", new Object[]{params});
						result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approveRemark(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarks.moveToNext());
				pendingRemarks.close();
			}

			Cursor pendingRemarksRemoved = dbHelper.getPendingRemarksRemoved(db);
			
			if (pendingRemarksRemoved != null && pendingRemarksRemoved.getCount() > 0) {
				pendingRemarksRemoved.moveToFirst();
				do {
					params = new HashMap<String, Object>();
					loanappid = pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("loanappid"));
					params.put("detailid", pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("detailid")));
									
					postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						response = postingProxy.invoke("removeRemarks", new Object[]{params});
						result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.removeRemarksRemovedByAppid(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarksRemoved.moveToNext());
				pendingRemarksRemoved.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
				db.close();
				backgroundProcessPool.submit(backgroundProcessThread);
			} catch (InterruptedException ie) { 
				System.out.println("interrupted");
			} catch (Exception e) { }
			db.close();
			//backgroundProcessHandler.postDelayed(backgroundProcessRunnable, 3000);
			//Executors.newSingleThreadExecutor().submit(new VoidPaymentStatusRunnable(voidPayments));			
		}
	};*/
	
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
	
	/*public void startLocationGetter() {
		isLocationGetterRunning = false;
		locationGetterPool = Executors.newSingleThreadExecutor();
		locationGetter = new LocationGetterThread();
		locationGetterPool.submit(locationGetter);
	}
	
	public void stopLocationGetter() {
		isLocationGetterRunning = false;
		locationGetter.softStop();
		locationGetterPool.shutdown();
		locationGetterPool = null;
	}
	
	private class LocationGetterThread extends Thread {
		private boolean stop = false;
		
		@Override
		public void run() {
			Location location = null;
			String provider = "";
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (isNetworkEnabled == true) {
				provider = LocationManager.NETWORK_PROVIDER;
				locationManager.requestLocationUpdates(provider, 0, 0, locationListener, Looper.getMainLooper());
				location = locationManager.getLastKnownLocation(provider);
				if (location != null) {
					longitude = location.getLongitude();
					latitude = location.getLatitude();
				}
				locationManager.removeUpdates(locationListener);
			}
			if (!stop) {
				try {
					Thread.sleep(2000);
					locationGetterPool.submit(locationGetter);
				} catch (Exception e) {
					
				}
			}
		}
		
		public synchronized void softStop() {
			stop = true;
		}
	}*/
	
	public void startNetworkChecker() {
		isNetworkCheckerRunning = true;
		networkThreadPool = Executors.newSingleThreadExecutor();
		networkChecker = new NetworkCheckerThread();
		networkThreadPool.submit(networkChecker);
	}
	
	public void stopNetworkChecker() {
		isNetworkCheckerRunning = false;
		networkChecker.softStop();
		networkThreadPool.shutdown();
		networkThreadPool = null;
	}
	
	private class NetworkCheckerThread extends Thread {
		private boolean stop = false;
		
		@Override
		public void run() {
			networkInfo = connectivityManager.getActiveNetworkInfo();
			String mode = "NOT CONNECTED";
			if (networkInfo.isConnected()) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					networkStatus = 1;
					mode = "ONLINE";
					//System.out.println("status = ONLINE");
					//online
				} else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					networkStatus = 0;
					mode = "OFFLINE";
					//System.out.println("status = OFFLINE");
					//offline
				}
			}
			if (getCurrentActivity() != null) {
				getCurrentActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						View v = getCurrentActivity().findViewById(R.id.tv_mode);
						if (v != null) {
							String mode = "NOT CONNECTED";
							if (networkStatus == 1) {
								mode = "ONLINE";
							} else if (networkStatus == 0) {
								mode = "OFFLINE";
							}
							((TextView) v).setText(mode);
						}
					}
				});
			}
			if (!stop) {
				try {
					Thread.sleep(1000);
					networkThreadPool.submit(networkChecker);
				} catch(Exception e) {}
			}
		}
		
		public synchronized void softStop() {
			stop = true;
		}
	}
	
	/*public void startLocationTracker() {
		isLocationTrackerRunning = true;
		locationTrackerPool = Executors.newSingleThreadExecutor();
		locationTracker = new LocationTrackerThread();
		locationTrackerPool.submit(locationTracker);
	}
	
	public void stopLocationTracker() {
		isLocationTrackerRunning = false;
		locationTracker.softStop();
		locationTrackerPool.shutdown();
		locationTrackerPool = null;
	}
	
	private class LocationTrackerThread extends Thread {
		private boolean stop = false;
		
		@Override
		public void run() {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (longitude > 0 && latitude > 0) {
				
				String collectorid = dbHelper.getCollectorid(db);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("objid", "TRCK"+UUID.randomUUID());
				params.put("seqno", dbHelper.countLocationTrackerByCollectorid(db, collectorid)+1);
				params.put("trackerid", dbHelper.getTrackerid(db));
				params.put("collectorid", collectorid);
				params.put("longitude", longitude);
				params.put("latitude", latitude);
				
				dbHelper.insertLocationTracker(db, params);
			}
			
			if (!stop) {
				try {
					Thread.sleep(dbHelper.getTrackerTimeout(db)*1000);
					db.close();
					locationTrackerPool.submit(locationTracker);
				} catch (InterruptedException ie) {
					System.out.println("interrupted");
				} catch (Exception e) {}
			}
			db.close();
		}
		
		public synchronized void softStop() {
			stop = true;
		}
	}*/
	
	/*public void startLocationTrackerBroadcast() {
		isLocationTrackerBroadcastRunning = true;
		locationTrackerBroadcasPool = Executors.newSingleThreadExecutor();
		locationTrackerBroadcast = new LocationTrackerBroadcastThread();
		locationTrackerBroadcasPool.submit(locationTrackerBroadcast);
	}
	
	public void stopLocationTrackerBroadcast() {
		isLocationTrackerBroadcastRunning = false;
		locationTrackerBroadcast.softStop();
		locationTrackerBroadcasPool.shutdown();
		locationTrackerBroadcasPool = null;
	}
	
	private class LocationTrackerBroadcastThread extends Thread {
		private boolean stop = false;
		
		@Override
		public void run() {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor locationTracker = dbHelper.getLocationTrackerByCollectorid(db, dbHelper.getCollectorid(db));
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
					} catch (Exception e) {}
				}
			}
			if (!stop) {
				try { 
					Thread.sleep(1000);
					locationTrackerBroadcasPool.submit(locationTrackerBroadcast);
				} catch (Exception e) {}
			}
		}
		
		public synchronized void softStop() {
			stop = true;
		}
	}*/
	
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
					SQLiteDatabase db = dbHelper.getReadableDatabase();
					dbHelper.setIdleState(db);
					db.close();
					break;
				}
			} while(!stop);
			if (!stop) {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				String state = dbHelper.getAppState(db);
				db.close();
				if (state.equals("idle") && !isIdleDialogShowing && !getIsLoginDialogShowing()) {
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
}
