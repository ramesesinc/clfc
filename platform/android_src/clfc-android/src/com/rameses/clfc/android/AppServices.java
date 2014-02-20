package com.rameses.clfc.android;

class AppServices implements Runnable
{
	private ApplicationImpl app;
	private NetworkCheckerService networkChecker;
	private LocationTrackerService locationTracker;
	private VoidRequestService voidRequest;
	private PaymentService payment;
	private RemarksService remarks;
	private RemarksRemovedService remarksRemoved;
	private BroadcastLocationService broadcastLocation;
	
	AppServices(ApplicationImpl app) {
		networkChecker = new NetworkCheckerService(app);
		locationTracker = new LocationTrackerService(app);
		voidRequest = new VoidRequestService(app);
		payment = new PaymentService(app);
		remarks = new RemarksService(app);
		remarksRemoved = new RemarksRemovedService(app);
		broadcastLocation = new BroadcastLocationService(app);
	} 
	
	public void run() {
		networkChecker.start();
		locationTracker.start();
		voidRequest.start();
		payment.start();
		remarks.start();
		remarksRemoved.start();
		broadcastLocation.start();
	}
}
