package com.rameses.clfc.android;

import android.os.Bundle;
import android.os.Handler;

import com.rameses.client.android.Platform;
import com.rameses.client.android.UIMain;


public class SplashActivity extends UIMain  
{

	protected void onCreateProcess(Bundle savedInstanceState) {
    	setContentView(R.layout.activity_splash);
    } 
    
	protected void afterRegister() { 
		
		Platform.getTaskManager().schedule(new Runnable() {

			public void run() { 
				try {
					SplashActivity.this.load();
				} catch(Throwable t) {
					t.printStackTrace();
				}
			}
			
		}, 1000);
	}

	protected void afterDestroy() {  
    }     

	private void load() {
		System.out.println("splash activity loaded");
    	ApplicationImpl app = (ApplicationImpl) Platform.getApplication();
    	app.getAppSettings();
    	app.load(); 
    }
}