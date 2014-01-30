package com.rameses.paymentcollectionapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

public class NetworkChecker {
	private ProjectApplication application;
	private NetworkInfo networkInfo;
	private ConnectivityManager connectivityManager;

	private Runnable networkCheckerRunnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("running network checker");
			networkInfo = connectivityManager.getActiveNetworkInfo();
			String mode = "NOT CONNECTED";
			//System.out.println("network info = "+networkInfo);
			if (networkInfo != null && networkInfo.isConnected()) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					application.setNetworkStatus(1);
					mode = "ONLINE";
				} else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					application.setNetworkStatus(0);
					mode = "OFFLINE";
				}
			}
			final String m = mode;
			if (application.getCurrentActivity() != null) {
				application.getCurrentActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//ApplicationUtil.showShortMsg(((Context) getCurrentActivity()), "mode = "+m);
						View v = application.getCurrentActivity().findViewById(R.id.tv_mode);
						if (v != null) {
							((TextView) v).setText(m);
						}
					}
				});
			}
		}
	};
	
	public NetworkChecker(ProjectApplication application) {
		this.application = application;
		connectivityManager = (ConnectivityManager) ((Context) application).getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	public void startNetworkChecker() {
		application.getTaskManager().schedule(networkCheckerRunnable, 0, 1000);
	}
}
