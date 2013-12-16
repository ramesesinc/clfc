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

public class ShutdownBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
			System.out.println("Shutting down");
			MySQLiteHelper db = new MySQLiteHelper(context);
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			System.out.println(" longitude = "+location.getLongitude());
			/*if (!db.isOpen) db.openDb();
			Cursor host = db.getHost();
			if (db.isOpen) db.closeDb();
			String url = "";
			String port = "";
			if (host != null && host.getCount() > 0) {
				host.moveToFirst();
				url = host.getString(host.getColumnIndex("ipaddress"));
				port = host.getString(host.getColumnIndex("port"));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			ScriptServiceContext sp = new ScriptServiceContext(map);
			ServiceProxy service = (ServiceProxy) sp.create("DeviceLocationService");
			Map<String, Object> params = new HashMap<String, Object>();
			if (wifiManager.isWifiEnabled()) {
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				params.put("longitude", location.getLongitude());
				params.put("latitude", location.getLatitude());
				params.put("remarks", "Device is shutting down.");
			}
			
			try {
				service.invoke("postLocation", new Object[]{params});				
			} catch (Exception e) {}*/
		}
	}

}
