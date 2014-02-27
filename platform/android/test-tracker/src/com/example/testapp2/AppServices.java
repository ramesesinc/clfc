package com.example.testapp2;

import com.rameses.client.android.UIApplication;

class AppServices implements Runnable
{
	private TrackerService trackerSvc;
	
	AppServices(TrackerApplication app) {
		trackerSvc = new TrackerService(app);
	} 
	
	public void run() { 
		trackerSvc.start();
//		voidRequest.start();
//		payment.start();
//		remarks.start();
//		remarksRemoved.start();
//		broadcastLocation.start();
	}
}
