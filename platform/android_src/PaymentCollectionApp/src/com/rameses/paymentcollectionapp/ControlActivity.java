package com.rameses.paymentcollectionapp;

import com.rameses.client.android.UIActivity;

import android.app.Activity;
import android.widget.TextView;

public class ControlActivity extends UIActivity {
	private MySQLiteHelper dbHelper = null;
	
	public void setDbHelper(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public MySQLiteHelper getDbHelper() { 
		return this.dbHelper; 
	}
	/*
	public void setIdleState(Activity currentActivity) {
		MySQLiteHelper helper = new MySQLiteHelper(currentAc)
	}*/
	
	public ProjectApplication getApp() {
		return (ProjectApplication) this.getApplicationContext();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		int networkStatus = getApp().getNetworkStatus();
		String mode = "NOT CONNECTED";
		if (networkStatus == 0) {
			mode = "OFFLINE";
		} else if (networkStatus == 1) {
			mode = "ONLINE";
		}
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
	}
	
	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		if (getApp().getIsWaiterRunning()) {
			getApp().touch();
		}
	}
}
