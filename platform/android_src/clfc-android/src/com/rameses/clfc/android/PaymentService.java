package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.db.android.SQLExecutor;
import com.rameses.db.android.SQLTransaction;

public class PaymentService 
{

	private ApplicationImpl app;
	private Handler handler;
	
	public PaymentService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			Platform.getTaskManager().schedule(new RunnableImpl(), 0);
		} 
	}
	
	private class RunnableImpl implements Runnable
	{

		public void run() {
//			System.out.println("PostPendingPayments");
			SQLTransaction paymentdb = new SQLTransaction("clfcpayment.db");
			SQLTransaction clfcdb = new SQLTransaction("clfc.db"); 
			try {
				paymentdb.beginTransaction();
				clfcdb.beginTransaction();
				runImpl(paymentdb, clfcdb);
				paymentdb.commit();
				clfcdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				System.out.println("[PaymentService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
//				t.printStackTrace();
			}  finally {
				paymentdb.endTransaction();
				clfcdb.endTransaction();
			}
			
			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new RunnableImpl(), delay*1000);
		}
		
		private void runImpl(SQLTransaction paymentdb, SQLTransaction clfcdb) throws Exception {
			DBPaymentService dbPs = new DBPaymentService();
			dbPs.setDBContext(paymentdb.getContext());
			
			List<Map> list = dbPs.getPendingPayments();
			if (!list.isEmpty()) {				
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(clfcdb.getContext());
				
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(clfcdb.getContext());
				
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
				int networkStatus = 0;
				LoanPostingService svc = new LoanPostingService();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					mode = "OFFLINE";
					networkStatus = ((ApplicationImpl) Platform.getApplication()).getNetworkStatus();
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
					params.put("longitude", Double.parseDouble(map.get("longitude").toString()));
					params.put("latitude", Double.parseDouble(map.get("latitude").toString()));
					params.put("collector", collector);
					params.put("collectionsheet", collectionSheet);
					params.put("payment", payment);
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.postPayment(params);
							break;
						} catch (Throwable e) {;} 
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						dbPs.approvePaymentById(map.get("objid").toString());
					}
				}
			}
		}
	}
}
