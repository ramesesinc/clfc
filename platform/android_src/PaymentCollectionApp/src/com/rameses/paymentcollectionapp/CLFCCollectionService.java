package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.rameses.service.ServiceProxy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.Looper;

public class CLFCCollectionService extends Service {
	private Context context = this;
	private MySQLiteHelper dbHelper = new MySQLiteHelper(context);
	private ScheduledExecutorService threadPool;
	private ProjectApplication application = (ProjectApplication) getApplication();
	private boolean isServiceRunning = false;
	private ConnectivityManager connectivityManager;
	private LocationManager locationManager;
	private DeviceLocationListener locationListener = new DeviceLocationListener();
	private Runnable pendingVoidPaymentsRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getReadableDatabase();
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
			SQLiteDatabase db = dbHelper.getReadableDatabase();
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
					if (application.getNetworkStatus() == 1) mode = "ONLINE";
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
			db.close();
		}
	};
	private Runnable pendingRemarksRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getReadableDatabase();
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
					if (application.getNetworkStatus() == 1) mode = "ONLINE";
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
			db.close();
		}
	};
	private Runnable pendingRemarksRemovedRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getReadableDatabase();
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
			db.close();
		}
	};
	private Runnable locationGetterRunnable = new Runnable() {
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
					application.setLongitude(location.getLongitude());
					application.setLatitude(location.getLatitude());
				}
				locationManager.removeUpdates(locationListener);
			}
		}
	};
	private Runnable locationTrackerRunnable = new Runnable() {
		@Override
		public void run() {
			if (application.getLongitude() > 0 && application.getLatitude() > 0) {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				
				String collectorid = dbHelper.getCollectorid(db);
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
	};
	private Runnable locationTrackerBroadcastRunnable = new Runnable() {
		@Override
		public void run() {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor locationTracker = dbHelper.getLocationTrackerByCollectorid(db, dbHelper.getCollectorid(db));
			
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
						}
						break;
					} catch (Exception e) {}
				}
				locationTracker.close();
			}
			db.close();
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!isServiceRunning) {
			startService();
		}
		return START_STICKY;
	}
	
	private void startService() {
		isServiceRunning = true;
		threadPool = Executors.newScheduledThreadPool(100);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		threadPool.scheduleWithFixedDelay(pendingVoidPaymentsRunnable, 0, 1, TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(pendingPaymentsRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(pendingRemarksRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(pendingRemarksRemovedRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(locationGetterRunnable, 0, 2, TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(locationTrackerRunnable, 0, dbHelper.getTrackerTimeout(db), TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(locationTrackerBroadcastRunnable, 0, 1, TimeUnit.SECONDS);
		db.close();
	}
	
	@Override
	public void onDestroy() {
		isServiceRunning = false;
		threadPool.shutdown();
		threadPool = null;
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
