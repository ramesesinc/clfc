package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rameses.client.android.AppLoader;
import com.rameses.client.android.AppLoaderCaller;
import com.rameses.client.android.ClientContext;
import com.rameses.client.android.InvokerProxy;
import com.rameses.service.ServiceProxy;

public class RegistrationLoader implements AppLoader {
	private AppLoaderCaller caller;
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return -1000;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		MySQLiteHelper dbHelper = new MySQLiteHelper((Context) ClientContext.getCurrentContext().getDeviceContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		params.put("terminalid", dbHelper.getTerminalid(db));
		db.close();
		ServiceProxy proxy = InvokerProxy.create("TerminalService");
		try {
			Object response = proxy.invoke("findTerminal", new Object[]{params});
			System.out.println("response = "+response);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void setCaller(AppLoaderCaller caller) {
		// TODO Auto-generated method stub
		this.caller = caller;
	}

}
