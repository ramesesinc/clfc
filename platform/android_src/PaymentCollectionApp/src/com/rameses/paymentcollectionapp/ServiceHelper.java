package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.content.Context;
import android.database.Cursor;

public class ServiceHelper {
	private MySQLiteHelper db = null;
	
	public ServiceHelper(Context context) {
		db = new MySQLiteHelper(context);
	}
	
	public boolean isHostSet() {
		if (!db.isOpen) db.openDb();
		Cursor host = db.getHost();
		if (db.isOpen) db.closeDb();
		if (host != null && host.getCount() > 0) return true;
		return false;
	}
	
	public Map<String, Object> getServiceConfig() {
		if (!db.isOpen) db.openDb();
		Cursor host = db.getHost();
		if (db.isOpen) db.closeDb();
		host.moveToFirst();
		String url = host.getString(host.getColumnIndex("ipaddress"));
		String port = host.getString(host.getColumnIndex("port"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("app.context", "clfc");
		map.put("app.host", url+":"+port);
		map.put("app.cluster", "osiris3");
		return map;
	}
	
	public ServiceProxy createServiceProxy(String serviceName) {
		ScriptServiceContext sp = new ScriptServiceContext(getServiceConfig());
		return (ServiceProxy) sp.create(serviceName);
	}
}
