package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.rameses.client.android.Platform;
import com.rameses.client.android.UIActivity;

public class MainActivity extends UIActivity 
{
	private Handler handler = new Handler();;
	
	protected void onCreateProcess(Bundle savedInstanceState) {
		super.onCreateProcess(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.e("on create", "tracker test");	
		
//		NetworkLocationProvider.setCurrentActivity(((TrackerApplication) Platform.getApplication()).getCurrentActivity());
//		NetworkLocationProvider.setHandler(handler);  
//		NetworkLocationProvider.setEnabled(true); 
//		LogUtil.log("network location provider started");
	}
	
	public void onBackPressedProcess() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		((TrackerApplication) getApplicationContext()).setCurrentActivity(this);
		LogUtil.log("About to start service");
//		startService(new Intent(context, TrackerService.class));
		LogUtil.log("Started service"); 
		System.out.println("on start");
	}
}
