package com.example.testapp2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Properties;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class MobileBroadcastReceiver extends BroadcastReceiver {
	private String HEALTH_CHECK = "1000";
	private String LOCK_CODE = "1001";
	private String UNLOCK_CODE = "1002";
	private String MY_NUM = "+639222521781";
	private String ADMIN_NUM = "+639228426692";
	private String ADMIN_NUM_GLOBE = "+639176342767";
	
	private static Properties VARS;
	static {
		if (VARS == null) {
			VARS = new Properties();
			VARS.put("prevDataConn", "");
		}
	}

	@Override
	public synchronized void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("action = "+intent.getAction());
		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			//System.out.println("is tracker app: "+(context.getApplicationContext() instanceof TrackerApplication));
			Bundle data = intent.getExtras();
			SmsMessage[] msgs;
			if (data != null) {
				Object[] pdus = (Object[]) data.get("pdus");
				msgs = new SmsMessage[pdus.length];
				//for (int i=0; i<msgs.length; i++) {
					msgs[0] = SmsMessage.createFromPdu((byte[]) pdus[0]);
					String msg_from = msgs[0].getOriginatingAddress();
					if (msg_from.equals(MY_NUM)) {
						Log.w("from: ", msg_from);
						String msgBody = msgs[0].getMessageBody().trim();
						if (msgBody.equals(HEALTH_CHECK)) {
							Log.w("CODE: ", "HEALTH CHECK");
							processHealthCheck(context, msg_from);
						} else if (msgBody.equals(LOCK_CODE)) {
							Log.w("CODE: ", "LOCK");
							lockApplication(msg_from);
						} else if (msgBody.equals(UNLOCK_CODE)) {
							Log.w("CODE: ", "UNLOCK");
							unlockApplication(msg_from);
						}
					}
					//Log.w("Message from: ", msgs[i].getOriginatingAddress());
				//}
			}
		} else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			boolean mobileDataEnabled = false;
			try {
				Class cmClass = Class.forName(connectivityManager.getClass().getName());
		        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
		        method.setAccessible(true); // Make the method callable
		        // get the setting for "mobile data"
		        mobileDataEnabled = (Boolean) method.invoke(connectivityManager);
			} catch (Exception e) {}
			String enabled = "enabled";
			if (mobileDataEnabled == false) {
				enabled = "disabled";
			}
			//System.out.println("VARS = "+VARS);
			if (!VARS.getProperty("prevDataConn").equals(enabled)) {
				VARS.put("prevDataConn", enabled);
				//System.out.println("changed");				
				String msg = "Data connection is "+enabled+".";
				
				/*try {
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(MY_NUM, null, msg, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				} */
				//sendSMS(context, MY_NUM, msg);
			}
		}
	}
	
	private void processHealthCheck(Context context, String msg_from) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		String msg = "";
		
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		msg += "GPS NETWORK: "+isNetworkEnabled+"\n";
		
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		msg += "GPS SATELITE: "+isGPSEnabled+"\n";
		
		boolean mobileDataEnabled = false;
		try {
			Class cmClass = Class.forName(connectivityManager.getClass().getName());
	        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
	        method.setAccessible(true); // Make the method callable
	        // get the setting for "mobile data"
	        mobileDataEnabled = (Boolean) method.invoke(connectivityManager);
		} catch (Exception e) {}
		msg += "Data Connection : "+mobileDataEnabled+"\n";
		
		boolean wifiEnabled = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).isWifiEnabled();
		msg += "Wifi: "+wifiEnabled;
		
		SmsManager sms = SmsManager.getDefault();
		//sms.sendTextMessage("+639228426692", null, msg, null, null);
		sms.sendTextMessage(msg_from, null, msg, null, null);
	}

	private void lockApplication(String msg_from) {
		//TrackerApplication application = intent.getap
		String msg = "The application is already locked.";
		try {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
			File file = new File(path, ".trackerlock");
			if (!file.exists()) {
				FileOutputStream fos = new FileOutputStream(file);//context.openFileOutput(".trackerlock", Context.MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				osw.flush();
				osw.close();
				msg = "The application is locked.";
			}
			msg += "\nCollector: \nRoute: ";
		} catch (Exception e) {
			msg = "Error locking application.";
			e.printStackTrace();
		}
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(msg_from, null, msg, null, null);
	}
	
	private void unlockApplication(String msg_from) {
		String msg = "The application is already unlocked.";
		try {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";			
			File file = new File(path, ".trackerlock");
			if (file.exists()) {
				boolean flag = file.delete();
				while(flag != true) {
					flag = file.delete();
				}
				msg = "The application is unlocked.";
			}
			msg += "\nCollector: \nRoute: ";
		} catch (Exception e) {
			msg = "Error unlocking application.";
			e.printStackTrace(); 
		}
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(msg_from, null, msg, null, null);
	}
}
