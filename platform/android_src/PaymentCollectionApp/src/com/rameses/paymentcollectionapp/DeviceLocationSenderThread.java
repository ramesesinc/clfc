package com.rameses.paymentcollectionapp;

import java.util.Calendar;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;

public class DeviceLocationSenderThread extends Thread {
	private Context context;
	private LocationManager locationManager = null;
	private WifiManager wifiManager = null;
	private boolean isNetworkEnabled = false;
	
	public DeviceLocationSenderThread(Context context) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DeviceLocationListener listener = new DeviceLocationListener();
		Location location = null;
		String provider = "";
		if (wifiManager.isWifiEnabled()) {
			provider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(provider, 0, 0, listener);
			location = locationManager.getLastKnownLocation(provider);
			System.out.println("time: "+Calendar.getInstance()+" longitude: "+location.getLongitude());
			System.out.println("time: "+Calendar.getInstance()+" latitude: "+location.getLatitude());
			locationManager.removeUpdates(listener);
		}
	}
	
}
