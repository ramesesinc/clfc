package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class RegistrationOption extends SettingsMenuActivity {
	private Context context = this;
	private LayoutInflater inflater;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater.inflate(R.layout.activity_registration_option, container, true);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		Button btn_next = (Button) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub 
				RadioGroup rg_option = (RadioGroup) findViewById(R.id.rg_registration);
				int selectedId = rg_option.getCheckedRadioButtonId();
				String tag = ((RadioButton) findViewById(selectedId)).getTag().toString();
				if (tag.equals("register")) {
					Intent intent = new Intent(context, Registration.class);
					startActivity(intent);
				} else if (tag.equals("recover")) {
					progressDialog.setMessage("Recovering terminal.");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(recoverRunnable);
				}
			}
		});
	}
	
	private Handler recoverHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			String terminalid = data.getString("terminalid");
			SQLiteDatabase db = getDbHelper().getWritableDatabase();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", "terminalid");
			params.put("value", terminalid);
			getDbHelper().insertSystem(db, params);
			db.close();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, "Terminal successfully recovered.");
			Intent intent = new Intent(context, Login.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, data.getString("response"));
		}
	};
	
	private Runnable recoverRunnable = new Runnable() {
		@Override
		public void run() {
			Map env = ClientContext.getCurrentContext().getAppEnv();
			env.put("app.host", ApplicationUtil.getAppHost(context, getApp().getNetworkStatus()));
			TerminalService svc = new TerminalService();
			String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

			Message msg = responseHandler.obtainMessage();
			Bundle data = new Bundle();
			boolean flag = false;
			try {
				Map result = svc.recover(deviceId);
				if (result == null || result.isEmpty()) {
					data.putString("response", "This device is not yet registered.");
				} else {
					msg = recoverHandler.obtainMessage();
					data.putString("terminalid", result.get("terminalid").toString());
					flag = true;
					/*msg.setData(data);
					recoverHandler.sendMessage(msg);*/
					/*MySQLiteHelper dbHelper = new MySQLiteHelper(context);
					SQLiteDatabase db = dbHelper.getReadableDatabase();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("name", "terminalid");
					params.put("value", result.get("terminalid").toString());
					dbHelper.insertSystem(db, params);
					db.close();*/
				}
			} catch(Exception e) {
				data.putString("response", e.getMessage());
				//ApplicationUtil.showShortMsg(context, e.getMessage());
			} finally {
				msg.setData(data);
				if (flag == true) recoverHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	};
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
}
