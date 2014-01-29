package com.rameses.paymentcollectionapp;

import com.rameses.client.android.ClientContext;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIActivity;
import com.rameses.client.android.UIStartActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Splash extends UIStartActivity {
	private Context context = this;
	private MySQLiteHelper dbHelper = null;
	private ProjectApplication application = null;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.activity_splash);
		application = (ProjectApplication) getApplicationContext();
		dbHelper = new MySQLiteHelper(context);
	}
	
	@Override
	protected void onResumeProcess() {
		super.onResumeProcess();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String terminalid = dbHelper.getTerminalid(db);
		db.close();
		if (terminalid == null || terminalid.equals("")) {
			application.load();
		} else {
			System.out.println("sessionid before");
			String sessionid = SessionContext.getSessionId();
			System.out.println("sessionid after = "+sessionid);
			if (sessionid == null || sessionid.equals("")) {
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			} else {
				System.out.println("move task to back");
				moveTaskToBack(true);
			}
		}
	}
}
