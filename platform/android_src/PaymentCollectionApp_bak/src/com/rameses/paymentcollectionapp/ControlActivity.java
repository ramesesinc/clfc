package com.rameses.paymentcollectionapp;

import android.app.Activity;

public class ControlActivity extends Activity {
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
	public void onUserInteraction() {
		super.onUserInteraction();
		if (getApp().getIsWaiterRunning()) {
			getApp().touch();
		}
	}
}
