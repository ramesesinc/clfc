package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.client.android.ClientContext;
import com.rameses.client.android.SessionContext;
import com.rameses.client.services.LoginService;
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

public class Login extends SettingsMenuActivity {
	private Context context = this;
	private ProgressDialog progressDialog;
	private AlertDialog dialog;
	private LayoutInflater inflater;
	private EditText et_username = null;
	private EditText et_password = null;
	
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_login, rl_container, true);
		RelativeLayout dialog_container = (RelativeLayout) findViewById(R.id.login_container);
		inflater.inflate(R.layout.dialog_login, dialog_container, true);
		et_username = (EditText) findViewById(R.id.login_username);
		et_password = (EditText) findViewById(R.id.login_password);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		
		et_username.setText("");
		et_username.requestFocus();
		et_password.setText("");
		
		((Button) findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = ((EditText) findViewById(R.id.login_username)).getText().toString();
				String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
				if (username == null || username.trim().equals("")) {
					et_username.requestFocus();
					ApplicationUtil.showShortMsg(context, "Username is required.");
				} else if (password == null || password.trim().equals("")) {
					et_password.requestFocus();
					ApplicationUtil.showShortMsg(context, "Password is required.");
				} else {
					progressDialog.setMessage("Logging in.");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));
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
	
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			startActivity(new Intent(context, Main.class));
		}
	};
	
	private class LoginRunnable implements Runnable {
		private String username = "";
		private String password = "";
		
		public LoginRunnable(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		@Override
		public void run() {
			Map env = ClientContext.getCurrentContext().getAppEnv();
			env.put("app.host", ApplicationUtil.getAppHost(context, getApp().getNetworkStatus()));
			LoginService svc = new LoginService();
			Message msg = responseHandler.obtainMessage();
			Bundle data = new Bundle();
			boolean flag = false;
			try {
				svc.login(username, password);
				insertToSystem("onlinehost", SessionContext.getSettings().getOnlineHost());
				insertToSystem("offlinehost", SessionContext.getSettings().getOfflineHost());
				insertToSystem("port", SessionContext.getSettings().getPort()+"");
				insertToSystem("timeout_session", SessionContext.getSettings().getSessionTimeout()+"");
				insertToSystem("timeout_upload", SessionContext.getSettings().getUploadDelay()+"");
				insertToSystem("timeout_tracker", SessionContext.getSettings().getTrackerDelay()+"");
				msg = loginHandler.obtainMessage(); 
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				data.putString("response", e.getMessage());
			} finally {
				msg.setData(data);
				if (flag == true) loginHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
	
	private void insertToSystem(String name, String value) {
		SQLiteDatabase db = getDbHelper().getWritableDatabase();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("value", value);
		getDbHelper().insertSystem(db, params);
		db.close();
	}
}
