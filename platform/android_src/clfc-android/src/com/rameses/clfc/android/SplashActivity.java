package com.rameses.clfc.android;

import android.content.Intent;
import android.os.Bundle;

import com.rameses.client.android.Platform;
import com.rameses.client.android.UIMain;


public class SplashActivity extends UIMain  
{

	protected void onCreateProcess(Bundle savedInstanceState) {
		if (!isTaskRoot()) {
			Intent intent = getIntent();
			String action = intent.getAction();
			if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
				Platform.getLogger().log("This is not the main activity. Exiting.");
				disposeMe();
				return;
			}
		}
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