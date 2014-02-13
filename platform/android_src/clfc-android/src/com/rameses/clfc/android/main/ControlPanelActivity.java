package com.rameses.clfc.android.main;

import java.util.ArrayList;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.system.ChangePasswordActivity;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIDialog;

public class ControlPanelActivity extends ControlActivity 
{
	private ProgressDialog progressDialog;
	
	public boolean isCloseable() { return false; }	
		
	protected void onCreateProcess(Bundle savedInstanceState) {
		super.onCreateProcess(savedInstanceState);
		
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_control_panel, rl_container, true);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
	} 
	
	protected void onStartProcess() {
		super.onStartProcess();
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(ApplicationUtil.createMenuItem("download", "Download", null, R.drawable.download));
		list.add(ApplicationUtil.createMenuItem("payment", "Payment(s)", null, R.drawable.payment));
		list.add(ApplicationUtil.createMenuItem("posting", "Posting", null, R.drawable.posting));
		list.add(ApplicationUtil.createMenuItem("request", "Request Special Collection", null, R.drawable.request));
		list.add(ApplicationUtil.createMenuItem("remit", "Remit", null, R.drawable.remit));
		list.add(ApplicationUtil.createMenuItem("changepassword", "Change Password", null, R.drawable.ic_launcher));
		list.add(ApplicationUtil.createMenuItem("logout", "Logout", null, R.drawable.logout));
		
		GridView gv_menu = (GridView) findViewById(R.id.gv_menu);		
		gv_menu.setAdapter(new MenuAdapter(this, list));
		gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try { 
					selectionChanged(parent, view, position, id); 
				} catch (Throwable t) {
					UIDialog.showMessage(t, ControlPanelActivity.this); 
				}
			} 
		}); 
	} 
	
	protected void afterActivityChanged() {
		Platform.getInstance().disposeAllExcept(this);
	}
	
	protected void afterBackPressed() {
		Platform.getApplication().suspendSuspendTimer(); 
	}
	
	private void selectionChanged(AdapterView<?> parent, View view, int position, long id) throws Exception {
		Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
		String itemId = item.get("id").toString();
		if (itemId.equals("logout")) {
			new LogoutController(this, progressDialog).execute(); 
			
		} else if (itemId.equals("changepassword")) { 
			Intent intent = new Intent(this, ChangePasswordActivity.class);  
			startActivity(intent); 
			
		} else if (itemId.equals("download")) {
			new DownloadRoutesController(this, progressDialog).execute();
						
		} else if (itemId.equals("payment")) {
			Intent intent = new Intent(this, CollectionRouteListActivity.class);
			startActivity(intent);
			
		} else if (itemId.equals("posting")) {
			//Intent intent = new Intent(context, PostingCollectionSheet.class);
			//intent.putExtra("networkStatus", getApp().getNetworkStatus());
			//startActivity(intent);
			
		} else if (itemId.equals("request")) {
			//Intent intent = new Intent(context, SpecialCollection.class);
			//intent.putExtra("networkStatus", getApp().getNetworkStatus());
			//startActivity(intent);
			
		} else if (itemId.equals("remit")) {
//			Intent intent = new Intent(context, RemitRouteCollection.class);
//			intent.putExtra("networkStatus", getApp().getNetworkStatus());
//			startActivity(intent);
			
		} 	
	} 
	
	protected void onDestroyProcess() {
//		if (getApp().getIsWaiterRunning()) {
//			getApp().stopWaiter();
//		}
	}
	
	private Handler routesHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			Bundle bundle = msg.getData();
//			Intent intent = new Intent(context, Route.class);
//			intent.putExtra("bundle", bundle);
//			intent.putExtra("networkStatus", getApp().getNetworkStatus());
//			if (progressDialog.isShowing()) progressDialog.dismiss();
//			startActivity(intent);
		}
	};	
}
