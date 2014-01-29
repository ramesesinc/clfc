package com.rameses.paymentcollectionapp;

import java.util.Map;
import java.util.concurrent.Executors;

import com.rameses.client.android.ClientContext;
import com.rameses.client.services.PasswordService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ChangePassword extends ControlActivity {
	private Context context = this;
	private LayoutInflater inflater;
	private EditText et_password_old;
	private EditText et_password_new;
	private EditText et_password_confirm;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater.inflate(R.layout.activity_changepassword, container, true);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		et_password_old = (EditText) findViewById(R.id.et_password_old);
		et_password_new = (EditText) findViewById(R.id.et_password_new);
		et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		
		et_password_old.setText("");
		et_password_old.requestFocus();
		et_password_new.setText("");
		et_password_confirm.setText("");
		
		((Button) findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String oldpwd = et_password_old.getText().toString();
				String newpwd = et_password_new.getText().toString();
				String confirmpwd = et_password_confirm.getText().toString();
				
				if (oldpwd.trim().equals("")) {
					et_password_old.requestFocus();
					ApplicationUtil.showShortMsg(context, "Old password is required.");
				} else if (newpwd.trim().equals("")) {
					et_password_new.requestFocus();
					ApplicationUtil.showShortMsg(context, "New password is required.");
				} else if (confirmpwd.trim().equals("")) {
					et_password_confirm.requestFocus();
					ApplicationUtil.showShortMsg(context, "Confirm password is required.");
				} else {
					progressDialog.setMessage("Changing password");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new ChangePasswordRunnable(oldpwd, newpwd, confirmpwd));
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
	
	private Handler changePasswordHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, data.getString("response"));
		}
	};
	
	private class ChangePasswordRunnable implements Runnable {
		private String oldpwd = "";
		private String newpwd = "";
		private String confirmpwd = "";
		
		public ChangePasswordRunnable(String oldpwd, String newpwd, String confirmpwd) {
			this.oldpwd = oldpwd;
			this.newpwd = newpwd;
			this.confirmpwd = confirmpwd;
		}
		
		@Override
		public void run() {
			Map env = ClientContext.getCurrentContext().getAppEnv();
			env.put("app.host", ApplicationUtil.getAppHost(context, getApp().getNetworkStatus()));
			PasswordService svc = new PasswordService();
			
			Message msg = responseHandler.obtainMessage();
			Bundle data = new Bundle();
			boolean flag = false;
			try {
				svc.changePassword(oldpwd, newpwd, confirmpwd);
				data.putString("response", "Password successfully changed.");
				msg = changePasswordHandler.obtainMessage();
				flag = true;
			} catch(Exception e) {
				data.putString("response", e.getMessage());
			} finally {
				msg.setData(data);
				if (flag == true) changePasswordHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
	
}
