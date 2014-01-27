package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;

public class DeviceBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/*DeviceLocationListener locationListener = new DeviceLocationListener();
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Location location = null;
		String provider = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (isNetworkEnabled) {
			provider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
			location = locationManager.getLastKnownLocation(provider);
		}
		if (location != null) {
			params.put("longitude", location.getLongitude());
			params.put("latitude", location.getLatitude());	
		}
		ServiceProxy proxy = new ServiceHelper(context).createServiceProxy("DeviceLocationService");
		if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
			params.put("remarks", "Device shutdown");
		} else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			params.put("remarks", "Device booting");
		}
		try {
			proxy.invoke("postLocation", new Object[]{params});
		} catch(Exception e) {}*/
	}

}
