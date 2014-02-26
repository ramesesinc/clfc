package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.client.android.ClientContext;
import com.rameses.client.services.TerminalService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Registration extends SettingsMenuActivity {
	private Context context = this;
	private ProgressDialog progressDialog;
	private LayoutInflater inflater;
	private EditText et_terminalkey = null;
	private EditText et_registeredby = null;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_registration, container, true);
		et_terminalkey = (EditText) findViewById(R.id.et_registration_terminalkey);
		et_registeredby = (EditText) findViewById(R.id.et_registration_registeredby);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		((Button) findViewById(R.id.btn_register)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String terminalkey = et_terminalkey.getText().toString();
				String registeredby = et_registeredby.getText().toString();
				if (terminalkey == null || terminalkey.trim().equals("")) {
					et_terminalkey.requestFocus();
					ApplicationUtil.showShortMsg(context, "Terminal key is required.");
				} else if (registeredby == null || registeredby.trim().equals("")) {
					et_registeredby.requestFocus();
					ApplicationUtil.showShortMsg(context, "Registered by is required.");
				} else { 
					progressDialog.setMessage("Registering device.");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new RegisterRunnable(terminalkey, registeredby));
				}
			}
		});
	}	
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, data.getString("response"));
		}
	};
	
	private Handler registerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, data.getString("response"));
			Intent intent = new Intent(context, Login.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
	
	private class RegisterRunnable implements Runnable {
		private String terminalkey = "";
		private String registeredby = "";
		
		public RegisterRunnable(String terminalkey, String registeredby) {
			this.terminalkey = terminalkey;
			this.registeredby = registeredby;
		}
		
		@Override
		public void run() {
			Map env = ClientContext.getCurrentContext().getAppEnv();
			env.put("app.host", ApplicationUtil.getAppHost(context, getApp().getNetworkStatus()));
			TerminalService svc = new TerminalService();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("terminalid", terminalkey);
			params.put("registeredby", registeredby);
			String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			params.put("macaddress", deviceId);
			//System.out.println("params = "+params);
			Bundle data = new Bundle();
			Message msg = responseHandler.obtainMessage();
			boolean status = false;
			try {
				svc.register(params);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", "terminalid");
				map.put("value", terminalkey);
				SQLiteDatabase db = getDbHelper().getWritableDatabase();
				getDbHelper().insertSystem(db, map);
				db.close();
				data.putString("response", "Device successfully registered.");
				status = true;
			} catch( Exception e ) { 
				data.putString("response", e.getMessage());
			}
			finally {
				msg.setData(data);
				if(status == true) registerHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	};
}
