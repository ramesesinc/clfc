package com.rameses.clfc.android;

import android.widget.TextView;

import com.rameses.client.android.Platform;
import com.rameses.client.android.UIActivity;

public class ControlActivity extends UIActivity 
{
	
	public ApplicationImpl getApp() {
		return (ApplicationImpl) Platform.getApplication(); 
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		String mode = "NOT CONNECTED";		
		int networkStatus = getApp().getNetworkStatus();
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
		Platform.getApplication().restartSuspendTimer(); 
	}
}
