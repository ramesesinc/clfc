package com.rameses.clfc.android.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.services.LoanBillingService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIActivity;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.SQLTransaction;

class DownloadController 
{
	private UIActivity activity;
	private ProgressDialog progressDialog;
	
	DownloadController(UIActivity activity, ProgressDialog progressDialog) {
		this.activity = activity;
		this.progressDialog = progressDialog;
	}
	
	void execute() throws Exception {
		Platform.runAsync(new ActionProcess());
	}
	
	private Handler errorhandler = new Handler() {  
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog.isShowing()) progressDialog.dismiss();
			
			Bundle data = msg.getData();			
			Object o = data.getSerializable("response"); 
			if (o instanceof Throwable) {
				UIDialog.showMessage(o, activity);				
			} else {
				ApplicationUtil.showShortMsg("[ERROR] " + o);	
			} 
		} 
	}; 
	
	private Handler successhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			activity.getHandler().post(new RouteFetcher());
		}
	};	
	
	private class RouteFetcher implements Runnable 
	{
		@Override
		public void run() {
			try {
				String userid = SessionContext.getProfile().getUserId();
				Map params = new HashMap();
				params.put("collectorid", userid);				
				LoanBillingService svc = new LoanBillingService();
				ArrayList list = (ArrayList) svc.getRoutes(params);
				System.out.println("routes->" + list);
				//Intent intent = new Intent(activity, RouteListActivity.class);
				//intent.putExtra("routes", list);
				//activity.startActivity(intent);
			} catch(Throwable t) {
				UIDialog.showMessage(t, activity); 
				t.printStackTrace();
			} finally {
				if (progressDialog.isShowing()) progressDialog.dismiss();
			}			
		}
	}
	
	private class ActionProcess implements Runnable 
	{
		public void run() {
			Bundle data = new Bundle();			
			Handler handler = null;
			Message message = null;
			try {
				progressDialog.setMessage("processing...");
				
				processDB();
				new RouteFetcher().run();
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
		
		private void processDB() throws Exception {
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
			String msg = "There are still collection sheets to upload. Please upload the collection sheets before downloading current collection sheets.";
			String sql = "" +
			" SELECT p.* FROM payment p " +
			"    INNER JOIN collectionsheet cs ON p.loanappid=cs.loanappid " +
			" WHERE p.state='PENDING' LIMIT 1 ";
			List<Map> pendings = txn.getList(sql);
			if (!pendings.isEmpty()) {
				pendings.clear();
				throw new Exception(msg);
			} 
			
			sql = "" + 
			" SELECT n.* FROM notes n " +
			"    INNER JOIN collectionsheet cs ON n.loanappid=cs.loanappid " +
			" WHERE n.state='PENDING' ";			
			pendings = txn.getList(sql);
			if (!pendings.isEmpty()) {
				pendings.clear();
				throw new Exception(msg);
			} 
			
			sql = "" + 
			" SELECT r.* FROM remarks r " +
			"    INNER JOIN collectionsheet cs ON r.loanappid=cs.loanappid" +
			" WHERE r.state='PENDING' ";	
			pendings = txn.getList(sql);
			if (!pendings.isEmpty()) {
				pendings.clear();
				throw new Exception(msg);
			} 
		}
		
		private void getRoutes() {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("collectorid", SessionContext.getProfile().getUserId());
//			//SQLiteDatabase db = getDbHelper().getReadableDatabase();
//			//params.put("collectorid", getDbHelper().getCollectorid(db));
//			//db.close();
//			Bundle bundle = new Bundle();
//			Message msg = responseHandler.obtainMessage();
//			boolean status = false;
//			ServiceProxy svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");
//			try {
//				Object response = svcProxy.invoke("getRoutes", new Object[]{params});
//				Map<String, Object> result = (Map<String, Object>) response;
//				bundle.putParcelableArrayList("routes", ((ArrayList<RouteParcelable>) result.get("routes")));
//				msg = routesHandler.obtainMessage();
//				status = true;
//			} catch( TimeoutException te ) {
//				bundle.putString("response", "Connection Timeout!");
//			} catch( IOException ioe ) {
//				bundle.putString("response", "Error connecting to Server.");
//			} catch( Exception e ) { 
//				bundle.putString("response", e.getMessage());
//				e.printStackTrace(); 
//			} finally {
//				msg.setData(bundle);
//				if(status == true) routesHandler.sendMessage(msg);
//				else responseHandler.sendMessage(msg);
//			}
		} 
	}		
}
