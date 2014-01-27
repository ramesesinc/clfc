package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends ControlActivity {
	private Context context = this;
	private EditText et_online = null;
	private EditText et_offline = null;
	private EditText et_port = null;
	private EditText et_session = null;
	private EditText et_upload = null;
	private EditText et_tracker = null;
	private ProgressDialog progressDialog;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		setTitle("Settings");
		intent = getIntent();
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_settings, rl_container, true);
		String mode = "NOT CONNECTED";
		int status = intent.getIntExtra("networkStatus", 2);
		if (status == 1) mode = "ONLINE";
		else if (status == 0) mode = "OFFLINE";
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
		progressDialog = new ProgressDialog(context);
		
		et_online = (EditText) findViewById(R.id.et_host_online);
		et_offline = (EditText) findViewById(R.id.et_host_offline);
		et_port = (EditText) findViewById(R.id.et_host_port);
		et_session = (EditText) findViewById(R.id.et_timeout_session);
		et_upload = (EditText) findViewById(R.id.et_timeout_upload);
		et_tracker = (EditText) findViewById(R.id.et_timeout_tracker);
		et_online.requestFocus();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("onlinehost", getDbHelper().getOnlineHost(db));
		params.put("offlinehost", getDbHelper().getOfflineHost(db));
		params.put("port", getDbHelper().getPort(db));
		params.put("sessiontimeout", getDbHelper().getSessionTimeout(db)+"");
		params.put("uploadtimeout", getDbHelper().getUploadTimeout(db)+"");
		params.put("trackertimeout", getDbHelper().getTrackerTimeout(db)+"");
		loadSettings(params);
		
		Button btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (et_online.getText().toString().trim().equals("")) {
					et_online.requestFocus();
					ApplicationUtil.showShortMsg(context, "Host for online is required.");
				} else if (et_offline.getText().toString().trim().equals("")) {
					et_offline.requestFocus();
					ApplicationUtil.showShortMsg(context, "Host for offline is required.");
				} else if (et_port.getText().toString().trim().equals("")) {
					et_port.requestFocus();
					ApplicationUtil.showShortMsg(context, "Port for host is required.");
				} else if (et_session.getText().toString().trim().equals("")) {
					et_session.requestFocus();
					ApplicationUtil.showShortMsg(context, "Time out for session is required.");
				} else if (et_upload.getText().toString().trim().equals("")) {
					et_upload.requestFocus();
					ApplicationUtil.showShortMsg(context, "Time out for upload is required.");
				} else if (et_tracker.getText().toString().trim().equals("")) {
					et_tracker.requestFocus();
					ApplicationUtil.showShortMsg(context, "Time out for tracker is required.");
				} else {
					progressDialog.setMessage("Saving settings");
					if (!progressDialog.isShowing()) progressDialog.show();
					/*et_online.setText(host_online);
					et_offline.setText(host_offline);
					et_port.setText(port);
					et_session.setText(timeout_session);
					et_upload.setText(timeout_upload);
					et_tracker.setText(timeout_tracker);*/

					insertToSystem("host_online", et_online.getText().toString());
					insertToSystem("host_offline", et_offline.getText().toString());
					insertToSystem("host_port", et_port.getText().toString());
					insertToSystem("timeout_session", et_session.getText().toString());
					insertToSystem("timeout_upload", et_upload.getText().toString());
					insertToSystem("timeout_tracker", et_tracker.getText().toString());
					
					if (progressDialog.isShowing()) progressDialog.dismiss();
					ApplicationUtil.showShortMsg(context, "Settings successfully saved!");
					/*if (getApp().getNetworkStatus() == 1) {
						Executors.newSingleThreadExecutor().submit(new SaveOnlineRunnable());
						//saveOnline();
					} else {
						Executors.newSingleThreadExecutor().submit(new SaveOfflineRunnable());
						//saveOffline();
					}*/
				}
			}
		});
	}
	
	private void loadSettings(Map<String, Object> params) {
		et_online.setText(params.get("onlinehost").toString());
		et_offline.setText(params.get("offlinehost").toString());
		et_port.setText(params.get("port").toString());
		et_session.setText(params.get("sessiontimeout").toString());
		et_upload.setText(params.get("uploadtimeout").toString());
		et_tracker.setText(params.get("trackertimeout").toString());
	}
	
	private Handler saveHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			
			String host_online = data.getString("onlinehost");
			String host_offline = data.getString("offlinehost");
			String port = data.getString("port");
			String timeout_session = data.getString("sessiontimeout");
			String timeout_upload = data.getString("uploadtimeout");
			String timeout_tracker = data.getString("trackertimeout");
			
			et_online.setText(host_online);
			et_offline.setText(host_offline);
			et_port.setText(port);
			et_session.setText(timeout_session);
			et_upload.setText(timeout_upload);
			et_tracker.setText(timeout_tracker);

			insertToSystem("host_online", host_online);
			insertToSystem("host_offline", host_offline);
			insertToSystem("host_port", port);
			insertToSystem("timeout_session", timeout_session);
			insertToSystem("timeout_upload", timeout_upload);
			insertToSystem("timeout_tracker", timeout_tracker);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("onlinehost", host_online);
			params.put("offlinehost", host_offline);
			params.put("port", port);
			params.put("sessiontimeout", timeout_session);
			params.put("uploadtimeout", timeout_upload);
			params.put("trackertimeout", timeout_tracker);
			loadSettings(params);
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, "Settings successfully saved!");
		}
	};
	
	private class SaveOnlineRunnable implements Runnable {
		@Override
		public void run() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("app.context", "clfc");
			map.put("app.host", et_online.getText().toString()+":"+et_port.getText().toString());
			map.put("app.cluster", "osiris3");
			
			ServiceProxy proxy = (ServiceProxy) new ScriptServiceContext(map).create("DeviceSettingsService");		
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("terminalid", ApplicationUtil.getTerminalid(context));
			params.put("onlinehost", et_online.getText().toString());
			params.put("offlinehost", et_offline.getText().toString());
			params.put("port", Integer.parseInt(et_port.getText().toString()));
			params.put("sessiontimeout", Integer.parseInt(et_session.getText().toString()));
			params.put("uploadtimeout", Integer.parseInt(et_upload.getText().toString()));
			params.put("trackertimeout", Integer.parseInt(et_tracker.getText().toString()));
			
			Bundle bundle = new Bundle();
			Message msg = responseHandler.obtainMessage();
			boolean status = false;
			try {
				Object response = proxy.invoke("saveSettings", new Object[]{params});//ApplicationUtil.getServiceProxy(context, "DeviceSettingsService").invoke("saveSettings", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				
				bundle.putString("onlinehost", result.get("onlinehost").toString());
				bundle.putString("offlinehost", result.get("offlinehost").toString());
				bundle.putString("port", result.get("port").toString());
				bundle.putString("sessiontimeout", result.get("sessionttimeout").toString());
				bundle.putString("uploadtimeout", result.get("uploadtimeout").toString());
				bundle.putString("trackertimeout", result.get("trackertimeout").toString());
				msg = saveHandler.obtainMessage();
				status = true;
			} catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			} catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			} catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
			} finally {
				msg.setData(bundle);
				if (status == true) saveHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
	
	private class SaveOfflineRunnable implements Runnable {
		@Override
		public void run() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("app.context", "clfc");
			map.put("app.host", et_offline.getText().toString()+":"+et_port.getText().toString());
			map.put("app.cluster", "osiris3");
			
			ServiceProxy proxy = (ServiceProxy) new ScriptServiceContext(map).create("DeviceSettingsService");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("terminalid", ApplicationUtil.getTerminalid(context));
			params.put("onlinehost", et_online.getText().toString());
			params.put("offlinehost", et_offline.getText().toString());
			params.put("port", Integer.parseInt(et_port.getText().toString()));
			params.put("sessiontimeout", Integer.parseInt(et_session.getText().toString()));
			params.put("uploadtimeout", Integer.parseInt(et_upload.getText().toString()));
			params.put("trackertimeout", Integer.parseInt(et_tracker.getText().toString()));
			
			Bundle bundle = new Bundle();
			Message msg = responseHandler.obtainMessage();
			boolean status = false;
			try {
				Object response = proxy.invoke("saveSettings", new Object[]{params});//ApplicationUtil.getServiceProxy(context, "DeviceSettingsService").invoke("saveSettings", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				
				bundle.putString("onlinehost", result.get("onlinehost").toString());
				bundle.putString("offlinehost", result.get("offlinehost").toString());
				bundle.putString("port", result.get("port").toString());
				bundle.putString("sessiontimeout", result.get("sessiontimeout").toString());
				bundle.putString("uploadtimeout", result.get("uploadtimeout").toString());
				bundle.putString("trackertimeout", result.get("trackertimeout").toString());
				msg = saveHandler.obtainMessage();
				status = true;
			} catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			} catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			} catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
			} finally {
				msg.setData(bundle);
				if (status == true) saveHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
	
	private void insertToSystem(String name, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("value", value);
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		getDbHelper().insertSystem(db, map);
		db.close();
	}
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
}
