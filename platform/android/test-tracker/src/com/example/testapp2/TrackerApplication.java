package com.example.testapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TrackerApplication extends Application {
	private static TrackerApplication instance;
	private Context context = this;
	private AlertDialog dialog;
	private Activity currentActivity;
	private LayoutInflater inflater;
	private boolean isApplicationLock = false;
	private String prevDataConn = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public static TrackerApplication getInstance() {
		return instance;
	}
	
	public void setPrevDataConn(String prevDataConn) {
		this.prevDataConn = prevDataConn;
	}
	
	public String getPrevDataConn() { return this.prevDataConn; }
	
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	public Activity getCurrentActivity() { return this.currentActivity; }
	
	public boolean getIsApplicationLock() { return this.isApplicationLock; }
	
	
	public void showLockDialog(Activity activity) {
		if (activity != null) {
			isApplicationLock = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
			builder.setTitle("Warning!");
			View view = inflater.inflate(R.layout.dialog_layout, null);
			((TextView) view.findViewById(R.id.tv_dialog_text)).setText("Your application is locked. Please contact your system administrator to unlock your application.");
			builder.setView(view);
			dialog = builder.create();
			dialog.setCancelable(false);
			dialog.show();
		}
	}
	
	public void closeLockDialog() {
		isApplicationLock = false;
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	private class GPSNetworkLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
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
			
		}
		
	}
	
	private class GPSSateliteLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
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
			
		}
		
	}
}
