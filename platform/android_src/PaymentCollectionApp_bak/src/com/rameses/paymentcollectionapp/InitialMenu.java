package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.service.ServiceProxy;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InitialMenu extends ControlActivity {
	private Context context = this;
	private ProgressDialog progressDialog;
	private AlertDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		setTitle("CLFC - Integrated Lending System");
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_main, rl_container, true);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	public void onBackPressed() {
		/*if (getApp().getIsNetworkCheckerRunning()) {
			getApp().stopNetworkChecker();
		}
		super.onBackPressed();*/
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		
		if (!getApp().getIsLocationTrackerRunning()) {
			getApp().startLocationTracker();
		}
		
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Cursor cs = getDbHelper().getCollectionsheets(db);
		db.close();
		
		if (cs != null && cs.getCount() > 0) {
			if (!getApp().getIsBackgroundProcessRunning()) {
				getApp().startBackgroundProcess();
			}
		}
		
		GridView gv_menu = (GridView) findViewById(R.id.gv_menu);
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(ApplicationUtil.createMenuItem("login", "Login", null, R.drawable.login));
		list.add(ApplicationUtil.createMenuItem("settings", "Settings", null, R.drawable.settings));
		
		gv_menu.setAdapter(new MenuAdapter(context, list));
		gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Map<String, Object> m = (Map<String, Object>) parent.getItemAtPosition(position);
				String text = m.get("text").toString().toLowerCase();
				if (text.equals("login")) {
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					boolean isSet = getDbHelper().isSettingsSet(db);
					if (isSet == true) {
						showLoginDialog();
					} else {
						ApplicationUtil.showShortMsg(context, "Application settings not set.");
					}
				} else if (text.equals("settings")) {
					Intent intent = new Intent(context, Settings.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					startActivity(intent);
					/*progressDialog.setMessage("Getting application settings from server.");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new SettingsRunnable());*/
				}
			}
		});
	}
	
	private Handler settingsHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			
			if (progressDialog.isShowing()) progressDialog.dismiss();
			/*if (data.getBoolean("status") == false) {
				ApplicationUtil.showShortMsg(context, "Default settings not loaded.");
			}*/
			Intent intent = new Intent(context, Settings.class); 
			intent.putExtra("networkStatus", getApp().getNetworkStatus());
			intent.putExtra("onlinehost", data.getString("onlinehost"));
			intent.putExtra("offlinehost", data.getString("offlinehost"));
			intent.putExtra("port", data.getString("port"));
			intent.putExtra("sessiontimeout", data.getString("sessiontimeout"));
			intent.putExtra("uploadtimeout", data.getString("uploadtimeout"));
			intent.putExtra("trackertimeout", data.getString("trackertimeout"));
			startActivity(intent);
		}
	};
	
	private class SettingsRunnable implements Runnable {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("terminalid", ApplicationUtil.getTerminalid(context));
			
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			String onlinehost = getDbHelper().getOnlineHost(db);
			String offlinehost = getDbHelper().getOfflineHost(db);
			String port = getDbHelper().getPort(db);
			String sessiontimeout = getDbHelper().getSessionTimeout(db)+"";
			String uploadtimeout = getDbHelper().getUploadTimeout(db)+"";
			String trackertimeout = getDbHelper().getTrackerTimeout(db)+"";
			db.close();
			boolean status = false; 
			Message msg = settingsHandler.obtainMessage();
			Bundle data = new Bundle();
			ServiceProxy proxy = ApplicationUtil.getServiceProxy(context, "DeviceSettingsService");
			try {
				Object response = proxy.invoke("getSettings", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				
				onlinehost = result.get("onlinehost").toString();
				offlinehost = result.get("offlinehost").toString();
				port = result.get("port").toString();
				sessiontimeout = result.get("sessiontimeout").toString();
				uploadtimeout = result.get("uploadtimeout").toString();
				trackertimeout = result.get("trackertimeout").toString();
				status = true;
			} catch (Exception e) {
			}
			finally {
				if (progressDialog.isShowing()) progressDialog.dismiss();
				
				data.putString("onlinehost", onlinehost);
				data.putString("offlinehost", offlinehost);
				data.putString("port", port);
				data.putString("sessiontimeout", sessiontimeout);
				data.putString("uploadtimeout", uploadtimeout);
				data.putString("trackertimeout", trackertimeout);
				data.putBoolean("status", status);
				msg.setData(data);
				settingsHandler.sendMessage(msg);
			}
			
		}
	}
	
	/*private void getSettings() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalid", ApplicationUtil.getTerminalid(context));
		
		try {
			Object response = ApplicationUtil.getServiceProxy(context, "DeviceSettingsService").invoke("getSettings", new Object[]{params});
			Map<String, Object> result = (Map<String, Object>) response;
			
			et_online.setText(result.get("onlinehost").toString());
			et_offline.setText(result.get("offlinehost").toString());
			et_port.setText(result.get("port").toString());
			et_session.setText(result.get("sessiontimeout").toString());
			et_upload.setText(result.get("uploadtimeout").toString());
			et_tracker.setText(result.get("trackertimeout").toString());
			
		} catch (Exception e) {}
		finally {
			if (progressDialog.isShowing()) progressDialog.dismiss();
		}
	}*/
		
	private void showLoginDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Login");
		View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_login, null);		
		builder.setView(view);
		builder.setPositiveButton("Login", null);
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = ((EditText) dialog.findViewById(R.id.login_username)).getText().toString();
				String password = ((EditText) dialog.findViewById(R.id.login_password)).getText().toString();
				
				if (username == null || username.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "Username is required.");
				} else if (password == null || password.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "Password is required.");
				} else {
					progressDialog.setMessage("Loggin in to server.");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));
				}
			}
		});
	}
	
	private class LoginRunnable implements Runnable {
		private String username;
		private String password;
		
		LoginRunnable(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		@Override
		public void run() {
			ServiceProxy proxy = ApplicationUtil.getServiceProxy(context, "DeviceLoginService");//new ServiceHelper(context).createServiceProxy("DeviceLoginService");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", username);
			params.put("password", password);
			
			Message msg = responseHandler.obtainMessage();
			Bundle bundle = new Bundle();
			boolean status = false;
			try {
				Object response = proxy.invoke("login", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				if (result.containsKey("collector")) {
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					Map<String, Object> user = (Map<String, Object>) result.get("collector");
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("name", "collector_username");
					m.put("value", user.get("username").toString());
					getDbHelper().insertSystem(db, m);
					m = new HashMap<String, Object>();
					m.put("name", "collector_password");
					m.put("value", user.get("pwd").toString());
					getDbHelper().insertSystem(db, m);
					m = new HashMap<String, Object>();
					m.put("name", "collector_name");
					m.put("value", user.get("name").toString());
					getDbHelper().insertSystem(db, m);
					db.close();
				}
				msg = loginHandler.obtainMessage();
				bundle.putString("collectorid", result.get("collectorid").toString());
				status = true;
			} catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			} catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			} catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
				e.printStackTrace(); 
			} finally {
				msg.setData(bundle);
				if(status == true) loginHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	};
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
	
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", "collectorid");
			params.put("value", bundle.getString("collectorid"));
			getDbHelper().insertSystem(db, params);
			getDbHelper().loginCollector(db);
			db.close();
			
			if (progressDialog.isShowing()) progressDialog.dismiss();
			if (dialog.isShowing()) dialog.dismiss();
			
			Intent intent = new Intent(context, Main.class);
			intent.putExtra("networkStatus", getApp().getNetworkStatus());
			startActivity(intent);
		}
	};

}
