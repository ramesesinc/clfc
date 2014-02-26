package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ServiceHelper {
	private MySQLiteHelper dbHelper = null;
	
	public ServiceHelper(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public Map<String, Object> getServiceConfig(String type) {
		/*if (!db.isOpen) db.openDb();
		Cursor host = db.getHost();
		if (db.isOpen) db.closeDb();
		host.moveToFirst();
		String url = host.getString(host.getColumnIndex("ipaddress"));
		String port = host.getString(host.getColumnIndex("port"));*/
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String port = dbHelper.getPort(db);
		String host = "";
		if (type.equals("online")) {
			host = dbHelper.getOnlineHost(db);
		} else { 
			host = dbHelper.getOfflineHost(db);
		}
		db.close();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("app.context", "clfc");
		map.put("app.host", host+":"+port);
		map.put("app.cluster", "osiris3");
		return map;
	}
	
	public ServiceProxy createServiceProxy(String serviceName, String type) {
		ScriptServiceContext sp = new ScriptServiceContext(getServiceConfig(type));
		return (ServiceProxy) sp.create(serviceName);
	}
}
