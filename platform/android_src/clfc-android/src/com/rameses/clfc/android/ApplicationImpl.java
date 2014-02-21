package com.rameses.clfc.android;

import java.io.File;
import java.util.Properties;

import android.os.Environment;
import android.os.Handler;

import com.rameses.client.android.AbstractActivity;
import com.rameses.client.android.AppSettings;
import com.rameses.client.android.Logger;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIApplication;

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

	public File getLogFile() {
		// TODO Auto-generated method stub
		File dir = Environment.getExternalStorageDirectory();
		return new File(dir, "clfclog.txt");
	}
	
	protected void init() {
		super.init();
//		System.out.println(getClass().getProtectionDomain());
	}

	protected void onCreateProcess() {
		super.onCreateProcess();
		
//		Platform.setDebug(true);
		NetworkLocationProvider.setEnabled(true);
		maindb = new MainDB(this, "clfc.db", 1);
		trackerdb = new TrackerDB(this, "clfctracker.db", 1);
		paymentdb = new PaymentDB(this, "clfcpayment.db", 1);
		requestdb = new VoidRequestDB(this, "clfcrequest.db", 1);
		remarksdb = new RemarksDB(this, "clfcremarks.db", 1);
		remarksremoveddb = new RemarksRemovedDB(this, "clfcremarksremoved.db", 1);
		
		AppServices services = new AppServices(this);
		new Handler().postDelayed(services, 1);

		AppSettingsImpl sets = (AppSettingsImpl) Platform.getApplication().getAppSettings();
		boolean flag = false;
		if ("true".equals(sets.getDebugEnabled())) {
			flag = true;
		}
		Platform.setDebug(flag);
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
