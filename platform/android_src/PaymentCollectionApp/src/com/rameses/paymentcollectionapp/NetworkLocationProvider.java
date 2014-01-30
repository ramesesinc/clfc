package com.rameses.paymentcollectionapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.rameses.client.android.UIApplication;

public class NetworkLocationProvider {
	private ProjectApplication application;
	private LocationManager locationManager;
	private String NETWORK = LocationManager.NETWORK_PROVIDER;

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
				application.setLocation(location);
			}
		}
	};
	
	public NetworkLocationProvider(ProjectApplication application) {
		this.application = application;
		locationManager = (LocationManager) ((Context) application).getSystemService(Context.LOCATION_SERVICE);
	} 
	
	public void startNetworkLocationLocator() {
		application.getTaskManager().schedule(networkLocationRunnable, 0, 2000);
	}
	
	private class DeviceLocationListener implements LocationListener {

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
