package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;

import com.rameses.client.android.ClientContext;
import com.rameses.client.android.InvokerProxy;
import com.rameses.client.interfaces.AppLoader;
import com.rameses.client.interfaces.AppLoaderCaller;
import com.rameses.client.services.TerminalService;
import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

public class LoaderRegistration implements AppLoader {
	//private Context context = (Context) ClientContext.getCurrentContext().getDeviceContext();
	private AppLoaderCaller caller;
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return -1000;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		System.out.println("loader registration started");
		Context context = (Context) ClientContext.getCurrentContext().getDeviceContext();
		MySQLiteHelper dbHelper = new MySQLiteHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try { 
			//ProjectApplication application = (ProjectApplication) context.getApplicationContext();
			//Map env = ClientContext.getCurrentContext().getAppEnv();
			//env.put("app.host", ApplicationUtil.getAppHost(context, application.getNetworkStatus()));
			TerminalService svc = new TerminalService(); 
			String terminalid = dbHelper.getTerminalid(db);
			db.close();
			String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			Object response = svc.findTerminal(terminalid);
			if (response == null) {
				System.out.println("loader registration no response");
				Intent intent = new Intent(context, RegistrationOption.class);
				intent.setAction(Intent.ACTION_MAIN);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else if (response instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) response;
				String macaddress = map.get("macaddress")+"";
				if (macaddress.equals(deviceId)) {
					System.out.println("loader registration ok");
					ApplicationUtil.deviceResgistered();					
					caller.resume();
				} else {
					System.out.println("loader registration macaddress not match");
					Intent intent = new Intent(context, RegistrationOption.class);
					intent.setAction(Intent.ACTION_MAIN);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}
		} catch (Exception e) {
			System.out.println("loader registration exception");
			Intent intent = new Intent(context, RegistrationOption.class);
			intent.setAction(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
	
	@Override
	public void setCaller(AppLoaderCaller caller) {
		// TODO Auto-generated method stub
		this.caller = caller;
	}

}
