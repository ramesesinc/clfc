package com.rameses.paymentcollectionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class IPSetting extends Activity {
	private Context context = this;
	private MySQLiteHelper db;
	private EditText et_ipaddress;
	private EditText et_ipport;
	private Cursor host;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipsetting);
		setTitle("IP Setting");
		db = new MySQLiteHelper(context);
		et_ipaddress = (EditText) findViewById(R.id.et_ipaddress);
		et_ipport = (EditText) findViewById(R.id.et_ipport);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!db.isOpen) db.openDb();
		
		String ipaddress = "";
		Integer port = 0; 
		host = db.getHost();
		db.closeDb();
		if(host != null && host.getCount() > 0) {
			host.moveToFirst();
			ipaddress = host.getString(host.getColumnIndex("ipaddress"));
			port = host.getInt(host.getColumnIndex("port"));
		}
		
		et_ipaddress.setText(ipaddress);
		String str = "";
		if(port > 0) str = port+"";
		et_ipport.setText(str);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!db.isOpen) db.openDb();
	}
	
	@Override
	protected void onPause() {
		if(db.isOpen) db.closeDb();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if(db.isOpen) db.closeDb();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.ipsetting, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.ipsetting_save:
					String ipaddress = et_ipaddress.getText().toString();
					String port = et_ipport.getText().toString();
					String msg = "";
					if(ipaddress.equals("") || ipaddress == null) {
						msg = "IP Address is required.";
					} else {
						if(port.equals("") || port == null) {
							msg = "Port is required.";
						} else {
							if(!db.isOpen) db.openDb();
							if(host != null && host.getCount() > 0) {
								db.updateHost(ipaddress, port+"");
								msg = "Setting successfully updated!";
							} else {
								db.insertHost(ipaddress, port+"");
								msg = "Setting successfully saved!";
							}
							db.closeDb();
							Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
							
							finish();
						}
					}
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					break;
		}
		return true;
	}
}
