package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.os.Handler;

import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBLocationTracker;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBRemarksRemoved;
import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.clfc.android.services.LoanLocationService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.AbstractActivity;
import com.rameses.client.android.AppSettings;
import com.rameses.client.android.Logger;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIApplication;
import com.rameses.db.android.ExecutionHandler;
import com.rameses.db.android.SQLExecutor;
import com.rameses.db.android.SQLTransaction;

public class ApplicationImpl extends UIApplication 
{
	private MainDB maindb;
	private TrackerDB trackerdb;
	private PaymentDB paymentdb;
	private VoidRequestDB requestdb;
	private RemarksDB remarksdb;
	private RemarksRemovedDB remarksremoveddb;
	private int networkStatus;
	private AppSettingsImpl appSettings;
	
	protected void onCreateProcess() {
		super.onCreateProcess();
		
		Platform.setDebug(true);
		NetworkLocationProvider.setEnabled(true);
		maindb = new MainDB(this, "clfc.db", 1);
		trackerdb = new TrackerDB(this, "clfctracker.db", 1);
		paymentdb = new PaymentDB(this, "clfcpayment.db", 1);
		requestdb = new VoidRequestDB(this, "clfcrequest.db", 1);
		remarksdb = new RemarksDB(this, "clfcremarks.db", 1);
		remarksremoveddb = new RemarksRemovedDB(this, "clfcremarksremoved.db", 1);
		
		AppServices services = new AppServices(this);
		new Handler().postDelayed(services, 1);

	}
	
	protected AppSettings createAppSettings() {
		return new AppSettingsImpl(); 
	}

	public Logger createLogger() {
		Logger logger = Logger.create("clfclog.txt");
		//ApplicationUtil.showShortMsg("logger -> "+logger.toString());
		return logger;
	} 	

	protected void beforeLoad(Properties appenv) {
		super.beforeLoad(appenv);
		appenv.put("app.context", "clfc");
		appenv.put("app.cluster", "osiris3");
		appenv.put("app.host", ApplicationUtil.getAppHost(networkStatus));
	}

	protected void onTerminateProcess() { 
		super.onTerminateProcess(); 
		NetworkLocationProvider.setEnabled(false); 
	} 	
	
	public int getNetworkStatus() { return networkStatus; }
	void setNetworkStatus(int networkStatus) { 
		this.networkStatus = networkStatus; 
		 
		AppSettingsImpl impl = (AppSettingsImpl) getAppSettings(); 
		getAppEnv().put("app.host", impl.getAppHost(networkStatus)); 
	}

	public void suspend() {
		if (SuspendDialog.isVisible()) return;

		AbstractActivity aa = Platform.getCurrentActivity();
		if (aa == null) aa = Platform.getMainActivity();
		
		final AbstractActivity current = aa;
		current.getHandler().postAtFrontOfQueue(new Runnable() {
			@Override
			public void run() {
				String content = "Collector: "+ SessionContext.getProfile().getFullName()+"\n\nTo resume your session, please enter your password";
				SuspendDialog.show(content);
			}
		});
	}
	
}
