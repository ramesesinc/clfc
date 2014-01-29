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
	//private DeviceLocationListener locationListener = new DeviceLocationListener();
	
	
	
	
	
	
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
		//threadPool.scheduleWithFixedDelay(pendingVoidPaymentsRunnable, 0, 1, TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(pendingPaymentsRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(pendingRemarksRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(pendingRemarksRemovedRunnable, 0, dbHelper.getUploadTimeout(db), TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(locationGetterRunnable, 0, 2, TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(locationTrackerRunnable, 0, dbHelper.getTrackerTimeout(db), TimeUnit.SECONDS);
		//threadPool.scheduleWithFixedDelay(locationTrackerBroadcastRunnable, 0, 1, TimeUnit.SECONDS);
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
