package com.rameses.clfc.android;

class AppServices implements Runnable
{
	private ApplicationImpl app;
	private NetworkCheckerService networkChecker;
	
	AppServices(ApplicationImpl app) {
		networkChecker = new NetworkCheckerService(app);
	} 
	
	public void run() {
		networkChecker.start();
	}
}
