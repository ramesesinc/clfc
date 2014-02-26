package com.example.testapp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity 
{
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.e("on create", "tracker test");		
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		((TrackerApplication) getApplicationContext()).setCurrentActivity(this);
		LogUtil.log("About to start service");
		startService(new Intent(context, TrackerService.class));
		LogUtil.log("Started service"); 
		System.out.println("on start");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("on resume");
	}

	@Override
	protected void onPause() {
		System.out.println("on pause");
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
