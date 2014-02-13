package com.rameses.clfc.android;

import com.rameses.client.android.AppSettings;

public class AppSettingsImpl extends AppSettings 
{
	AppSettingsImpl() {
		
	}
	
	public String getAppHost(int networkStatus) {
		String host = getOnlineHost(); 
		if (networkStatus == 0) host = getOfflineHost();
		
		return host + ":" + getPort(); 
	}
	
	public String getAppState() {  
		return getString("app_state", "idle");  
	}
	
	public String getOnlineHost() {  
		return getString("host_online", "192.168.0.178"); 
	}
	
	public String getOfflineHost() {  
		return getString("host_offline", "192.168.0.178"); 
	}
	
	public int getPort() {  
		return getInt("host_port", 8070); 
	}
	
	public int getSessionTimeout() {  
		return getInt("timeout_session", 3); 
	}
	
	public int getUploadTimeout() { 
		return getInt("timeout_upload", 5); 
	}
	
	public int getTrackerTimeout() { 
		return getInt("timeout_tracker", 10); 
	} 
	
}
