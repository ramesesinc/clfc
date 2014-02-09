package com.rameses.clfc.android.main;

import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIActivity;
import com.rameses.client.android.UIApplication;
import com.rameses.client.android.UIDialog;
import com.rameses.client.services.LogoutService;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

class LogoutController 
{
	private UIActivity activity;
	private ProgressDialog progressDialog;
	
	LogoutController(UIActivity activity, ProgressDialog progressDialog) {
		this.activity = activity;
		this.progressDialog = progressDialog;
	}
	
	void execute() throws Exception {
		DBPaymentService ps = new DBPaymentService();
		if (ps.hasUnpostedTransactions()) { 
			ApplicationUtil.showShortMsg("Cannot logout. There are still unposted transactions");
			
		} else {
			UIDialog dialog = new UIDialog(activity) {
				
				public void onApprove() {
					progressDialog.setMessage("Logging out...");
					if (!progressDialog.isShowing()) progressDialog.show();
					
					Platform.runAsync(new LogoutActionProcess()); 
				}
			};
			dialog.confirm("Are you sure you want to logout?");
		}		
	}
	

	
	private Handler errorhandler = new Handler() {  
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog.isShowing()) progressDialog.dismiss();
			
			Bundle data = msg.getData();			
			Object o = data.getSerializable("response"); 
			if (o instanceof Throwable) {
				Throwable t = (Throwable)o;
				ApplicationUtil.showShortMsg("[ERROR] " + t.getMessage());
			} else {
				ApplicationUtil.showShortMsg("[ERROR] " + o);	
			} 
		} 
	}; 
	
	private Handler successhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog.isShowing()) progressDialog.dismiss();

			UIApplication uiapp = Platform.getApplication();
			uiapp.getAppSettings().put("collector_state", "logout");
			uiapp.logout(); 
		}
	};	
	
	private class LogoutActionProcess implements Runnable 
	{
		public void run() {
			Bundle data = new Bundle();			
			Handler handler = null;
			Message message = null;
			try {
				runImpl();
				data.putString("response", "success");
				handler = successhandler;
				message = handler.obtainMessage();
				
			} catch(Throwable t) { 
				data.putSerializable("response", t);
				handler = errorhandler;
				message = handler.obtainMessage();				
				t.printStackTrace();
			} 
			
			message.setData(data);
			handler.sendMessage(message); 
		}
		
		private void runImpl() throws Exception {
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				execute(txn);
				txn.commit();
			} catch(Exception e) {
				throw e; 
			} finally { 
				txn.endTransaction();  
			} 
		}
				
		private void execute(SQLTransaction txn) throws Exception {
			
			String collectorid = SessionContext.getProfile().getUserId();
			List<Map> routes = txn.getList("SELECT * FROM route WHERE collectorid='"+collectorid+"' AND state='REMITTED'");
			while (!routes.isEmpty()) {
				Map data = routes.remove(0);
				String routecode = MapProxy.getString(data, "routecode");
				processRoute(txn, routecode);
				txn.delete("route", "routecode=?", new Object[]{routecode});
			}
						
			txn.delete("location_tracker", "collectorid=?", new Object[]{collectorid});			
			try { 
				new LogoutService().logout(); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			}			
		} 
		
		private void processRoute(SQLTransaction txn, String routecode) throws Exception {
			String sql = "SELECT * FROM collectionsheet WHERE routecode=? ORDER BY seqno";
			List<Map> sheets = txn.getList(sql, new Object[]{routecode});
			while (!sheets.isEmpty()) {
				Map data = sheets.remove(0);
				String loanappid = MapProxy.getString(data, "loanappid");
				txn.delete("void", "loanappid=?", new Object[]{loanappid});
				txn.delete("payment", "loanappid=?", new Object[]{loanappid});
				txn.delete("notes", "loanappid=?", new Object[]{loanappid});
				txn.delete("remarks", "loanappid=?", new Object[]{loanappid});
				txn.delete("remarks_removed", "loanappid=?", new Object[]{loanappid});
				txn.delete("collectionsheet", "loanappid=?", new Object[]{loanappid});
			} 
		}
	}	
}
