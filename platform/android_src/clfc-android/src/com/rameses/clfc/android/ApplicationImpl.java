package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.os.Handler;
import android.os.SystemClock;

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
import com.rameses.db.android.SQLTransaction;

public class ApplicationImpl extends UIApplication 
{
	private MainDB maindb;
	private int networkStatus;
	private AppSettingsImpl appSettings;
	
	protected void onCreateProcess() {
		super.onCreateProcess();

		NetworkLocationProvider.setEnabled(true);
		maindb = new MainDB(this, "clfc.db", 1);
		
		AppServices services = new AppServices(this);
		new Handler().postDelayed(services, 1);
		
		Platform.getTaskManager().schedule(new ApprovePendingVoidRequest(), 0, 2000);
		Platform.getTaskManager().schedule(new PostPendingPayments(), 0);
		Platform.getTaskManager().schedule(new PostPendingRemarks(), 0);
		Platform.getTaskManager().schedule(new PostPendingRemarksRemoved(), 0);
		Platform.getTaskManager().schedule(new PostLocationTracker(), 0, 2000);
	}
	
	protected AppSettings createAppSettings() {
		return new AppSettingsImpl(); 
	}

	public Logger createLogger() {
		return Logger.create("clfclog.txt");
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
	
	private class ApprovePendingVoidRequest implements Runnable {
		public void run() {
//			System.out.println("ApprovePendingVoidRequest");
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
			} catch (Throwable t) {;}
			finally {
				txn.endTransaction();
			}			
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			DBVoidService dbVs = new DBVoidService();
			dbVs.setDBContext(txn.getContext());
			
			List<Map> list = dbVs.getPendingVoidRequests();

			if (!list.isEmpty()) {
				LoanPostingService svc = new LoanPostingService();
				Map map;
				Map params = new HashMap();
				boolean isApproved = false;
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("voidid", map.get("objid").toString());
					
					isApproved = svc.isVoidPaymentApproved(params);
					if (isApproved) {
						dbVs.approveVoidPaymentById(map.get("objid").toString());
					}
				}
			}
		}
	}
	
	private class PostPendingPayments implements Runnable {
		public void run() {
//			System.out.println("PostPendingPayments");
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			finally {
				txn.endTransaction();
			}
			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new PostPendingPayments(), delay*1000);
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			DBPaymentService dbPs = new DBPaymentService();
			dbPs.setDBContext(txn.getContext());
			
			List<Map> list = dbPs.getPendingPayments();
			
			if (!list.isEmpty()) {				
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(txn.getContext());
				
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(txn.getContext());
				
				String trackerid = dbSys.getTrackerid();
				
				Map collector = new HashMap();
				collector.put("objid", SessionContext.getProfile().getUserId());
				collector.put("name", SessionContext.getProfile().getFullName());

				String mode = "";
				Map map;
				Map mCollectionSheet;
				Map params = new HashMap();
				Map loanapp = new HashMap();
				Map payment = new HashMap();
				Map response = new HashMap();
				Map borrower = new HashMap();
				Map collectionSheet = new HashMap();
				LoanPostingService svc = new LoanPostingService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					mode = "OFFLINE";
					if (networkStatus == 1) {
						mode = "ONLINE";
					}
					
					mCollectionSheet = dbCs.findCollectionSheetByLoanappid(map.get("loanappid").toString());
					collectionSheet.clear();
					collectionSheet.put("detailid", mCollectionSheet.get("detailid").toString());
					
					loanapp.clear();
					loanapp.put("objid", mCollectionSheet.get("loanappid").toString());
					loanapp.put("appno", mCollectionSheet.get("appno").toString());
					collectionSheet.put("loanapp", loanapp);
					
					System.out.println("cs -> "+mCollectionSheet);
					borrower.clear();
					borrower.put("objid", mCollectionSheet.get("acctid").toString());
					borrower.put("name", mCollectionSheet.get("acctname").toString());
					collectionSheet.put("borrower", borrower);
					
					payment.clear();
					payment.put("objid", map.get("objid").toString());
					payment.put("refno", map.get("refno").toString());
					payment.put("txndate", map.get("txndate").toString());
					payment.put("type", map.get("paymenttype").toString());
					payment.put("amount", map.get("paymentamount").toString());
					payment.put("paidby", map.get("paidby").toString());
					
					params.clear();
					params.put("type", mCollectionSheet.get("type").toString());
					params.put("sessionid", mCollectionSheet.get("sessionid").toString());
					params.put("routecode", map.get("routecode").toString());
					params.put("mode", mode);
					params.put("trackerid", trackerid);
					params.put("longitiude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitude", Double.parseDouble(map.get("latitude").toString()));
					params.put("collector", collector);
					params.put("collectionsheet", collectionSheet);
					params.put("payment", payment);
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.postPayment(params);
							break;
						} catch (Exception e) {;} 
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						dbPs.approvePaymentById(map.get("objid").toString());
					}
				}
			}
		}		
	}
	
	private class PostPendingRemarks implements Runnable {
		public void run() {
//			System.out.println("PostPendingRemarks");
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
			} catch (Throwable t) {;}
			finally {
				txn.endTransaction();
			}

			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new PostPendingRemarks(), delay*1000);
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			DBRemarksService dbRs = new DBRemarksService();
			dbRs.setDBContext(txn.getContext());
			
			List<Map> list = dbRs.getPendingRemarks();
			
			if (!list.isEmpty()) {
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(txn.getContext());					
				
				Map collector = new HashMap();
				collector.put("objid", SessionContext.getProfile().getUserId());
				collector.put("name", SessionContext.getProfile().getFullName());
				
				String mode = "";
				Map map;
				Map mCollectionSheet;
				Map params = new HashMap();
				Map loanapp = new HashMap();
				Map borrower = new HashMap();
				Map response = new HashMap();
				Map collectionSheet = new HashMap();
				LoanPostingService svc = new LoanPostingService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					mode = "OFFLINE";
					if (networkStatus == 1) {
						mode = "ONLINE";
					}
					
					mCollectionSheet = dbCs.findCollectionSheetByLoanappid(map.get("loanappid").toString());
					collectionSheet.clear();
					collectionSheet.put("detailid", mCollectionSheet.get("detailid").toString());
					
					loanapp.clear();
					loanapp.put("objid", mCollectionSheet.get("loanappid").toString());
					loanapp.put("appno", mCollectionSheet.get("appno").toString());
					collectionSheet.put("loanapp", loanapp);
					
					borrower.clear();
					borrower.put("objid", mCollectionSheet.get("acctid").toString());
					borrower.put("name", mCollectionSheet.get("acctname").toString());
					collectionSheet.put("borrower", borrower);
					
					params.clear();
					params.put("sessionid", mCollectionSheet.get("sessionid").toString());
					params.put("routecode", mCollectionSheet.get("routecode").toString());
					params.put("mode", mode);
					params.put("trackerid", map.get("trackerid").toString());
					params.put("longitude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitiude", Double.parseDouble(map.get("latitude").toString()));
					params.put("collector", collector);
					params.put("collectionsheet", collectionSheet);
					params.put("remarks", map.get("remarks").toString());
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.updateRemarks(params);
							break;
						} catch (Exception e) {;}
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						dbRs.approveRemarksByLoanappid(map.get("loanappid").toString());
					}
				}
			}
		}
	}
	
	private class PostPendingRemarksRemoved implements Runnable {
		public void run() {
//			System.out.println("PostPendingRemarksRemoved");
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
			} catch (Throwable t) {;}
			finally {
				txn.endTransaction();
			}
			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new PostPendingRemarksRemoved(), delay*1000);
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			DBRemarksRemoved dbRr = new DBRemarksRemoved();
			dbRr.setDBContext(txn.getContext());
			
			List<Map> list = dbRr.getPendingRemarksRemoved();
			
			if (!list.isEmpty()) {
				
				Map map;
				Map params = new HashMap();
				Map response = new HashMap();
				LoanPostingService svc = new LoanPostingService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("detailid", map.get("detailid").toString());
					
					response.clear();
					for (int j=0; i<10; j++) {
						try {
							response = svc.removeRemarks(params);
							break;
						} catch (Exception e) {;}
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						txn.delete("remarks_removed", "loanappid='"+map.get("loanappid").toString()+"'");
					}
				}
			}
		}
	}

	private class PostLocationTracker implements Runnable {
		public void run() {
//			System.out.println("PostLocationTracker");
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
			} catch (Throwable t) {;}
			finally {
				txn.endTransaction();
			}
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			DBLocationTracker dbLt = new DBLocationTracker();
			dbLt.setDBContext(txn.getContext());
			
			List<Map> list = dbLt.getLocationTrackers();
			
			if (!list.isEmpty()) {
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(txn.getContext());
				
				String trackerid = dbSys.getTrackerid();
				Map map;
				Map params = new HashMap();
				Map response = new HashMap();
				LoanLocationService svc = new LoanLocationService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("objid", map.get("objid").toString());
					params.put("trackerid", trackerid);
					params.put("longitude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitude", Double.parseDouble(map.get("latitude").toString()));
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.postLocation(params);
							break;
						} catch (Exception e) {;}
					}
					
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						txn.delete("location_tracker", "objid=?", new Object[]{map.get("objid").toString()});
					}
				}
			}
		}
	}
	
}
