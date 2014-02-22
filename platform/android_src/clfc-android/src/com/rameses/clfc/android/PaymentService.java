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
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

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
			DBContext clfcdb = new DBContext("clfc.db"); 
//			System.out.println("clfcdb -> "+clfcdb);
			try {
				paymentdb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl(paymentdb, clfcdb);
				paymentdb.commit();
//				clfcdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				System.out.println("[PaymentService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
				t.printStackTrace();
			}  finally {
				paymentdb.endTransaction();
				clfcdb.close();
			}

			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new RunnableImpl(), delay*1000);
		}
		
		private void runImpl(SQLTransaction paymentdb, DBContext clfcdb) throws Exception {
//			System.out.println(PaymentService.this);
			DBPaymentService dbPs = new DBPaymentService();
			dbPs.setDBContext(paymentdb.getContext());
			
//			System.out.println("pass 1");
			List<Map> list = dbPs.getPendingPayments();
			if (!list.isEmpty()) {		
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(clfcdb); 
				dbCs.setCloseable(false);
				
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(clfcdb); 
				dbSys.setCloseable(false);

//				System.out.println("pass 2");
				String trackerid = dbSys.getTrackerid();
//				String sql = "SELECT * FROM sys_var WHERE name='trackerid'";
//				String trackerid = null;
//				try {
//					Map map = clfcdb.find(sql, new Object[]{});
//					trackerid = MapProxy.getString(map, "value");
//				} catch (Exception e) {
//					throw e;
//				}
//				String trackerid = clfcdb.fin//dbSys.getTrackerid();
				
//				if (SessionContext.getProfile() == null) return;
//				Map collector = new HashMap();
//				collector.put("objid", SessionContext.getProfile().getUserId());
//				collector.put("name", SessionContext.getProfile().getFullName());

				String mode = "";
				Map mCollectionSheet;
				Map params = new HashMap();
				Map loanapp = new HashMap();
				Map payment = new HashMap(); 
				Map response = new HashMap();
				Map borrower = new HashMap();
				Map collector = new HashMap();
				Map collectionSheet = new HashMap();
				int networkStatus = 0;
				LoanPostingService svc = new LoanPostingService();
//				System.out.println("pass 3");
				MapProxy proxy;
				for (int i=0; i<list.size(); i++) { 
//					System.out.println("loop PaymentService items");
					proxy = new MapProxy((Map) list.get(i));
					
					mode = "ONLINE_WIFI";
					networkStatus = ((ApplicationImpl) Platform.getApplication()).getNetworkStatus();
					if (networkStatus == 1) {
						mode = "ONLINE_MOBILE";
					}
					
					collector.clear();
					collector.put("objid", proxy.getString("collectorid"));
					collector.put("name", proxy.getString("collectorname"));
					
//					System.out.println("loanappid -> "+map.containsKey("loanappid"));
					mCollectionSheet = dbCs.findCollectionSheetByLoanappid(proxy.getString("loanappid"));//clfcdb.find(sql, new Object[]{proxy.getString("loanappid")});//dbCs.findCollectionSheetByLoanappid();
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
					payment.put("objid", proxy.getString("objid"));
					payment.put("refno", proxy.getString("refno"));
					payment.put("txndate", proxy.getString("txndate"));
					payment.put("type", proxy.getString("paymenttype"));
					payment.put("amount", proxy.getString("paymentamount"));
					payment.put("paidby", proxy.getString("paidby"));
					
					params.clear();
					params.put("type", mCollectionSheet.get("type").toString());
					params.put("sessionid", mCollectionSheet.get("sessionid").toString());
					params.put("routecode", proxy.getString("routecode"));
					params.put("mode", mode);
					params.put("trackerid", trackerid);
					params.put("longitude", proxy.getDouble("longitude"));
					params.put("latitude", proxy.getDouble("latitude"));
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
						dbPs.approvePaymentById(proxy.getString("objid"));
					}
				}
			}
		}
	}
}
