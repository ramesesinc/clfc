package com.example.testapp2;

import java.io.File;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;

import com.rameses.client.android.UIActivity;
import com.rameses.client.android.UIApplication;

public class TrackerApplication extends UIApplication 
{
	private static TrackerApplication instance;
//	private Context context = this;
//	private AlertDialog dialog;
	private UIActivity currentActivity;
	private LayoutInflater inflater;
//	private boolean isApplicationLock = false;
//	private String prevDataConn = "";
	
	public void onCreateProcess() {
		super.onCreateProcess();
		instance = this;
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		System.out.println("oncreateprocess");
		AppServices services = new AppServices(this);
		new Handler().postDelayed(services, 1);
	}
	
	public static TrackerApplication getInstance() {
		return instance;
	}
	
//	public void setPrevDataConn(String prevDataConn) {
//		this.prevDataConn = prevDataConn;
//	}
	
//	public String getPrevDataConn() { return this.prevDataConn; }
	
	public void setCurrentActivity(UIActivity currentActivity) {
		this.currentActivity = currentActivity;
		System.out.println ("set current activity -> "+currentActivity);
	}
	
	public UIActivity getCurrentActivity() { return this.currentActivity; }
	
//	public boolean getIsApplicationLock() { return this.isApplicationLock; }
	
	
//	public void showLockDialog(Activity activity) {
//		if (activity != null) {
//			isApplicationLock = true;
//			AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
//			builder.setTitle("Warning!");
//			View view = inflater.inflate(R.layout.dialog_layout, null);
//			((TextView) view.findViewById(R.id.tv_dialog_text)).setText("Your application is locked. Please contact your system administrator to unlock your application.");
//			builder.setView(view);
//			dialog = builder.create();
//			dialog.setCancelable(false);
//			dialog.show();
//		}
//	}
	
//	public void closeLockDialog() {
//		isApplicationLock = false;
//		if (dialog != null && dialog.isShowing()) {
//			dialog.dismiss();
//		}
//	}

	@Override
	public File getLogFile() {
		// TODO Auto-generated method stub
		return null;
	}
}
