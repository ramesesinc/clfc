package com.rameses.clfc.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class PaymentService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction paymentdb;
	private DBPaymentService paymentSvc = new DBPaymentService();
	
	private String mode = "";
	private Map params = new HashMap();
	private Map loanapp = new HashMap();
	private Map payment = new HashMap(); 
	private Map response = new HashMap();
	private Map borrower = new HashMap();
	private Map collector = new HashMap();
	private Map collectionSheet = new HashMap();
	private int networkStatus = 0;
	private MapProxy proxy;
	private int size;
	private int delay;
	private LoanPostingService svc = new LoanPostingService();
	private boolean hasUnpostedPayments = false;

	private boolean serviceStarted = false;
	
	public PaymentService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
		} 
		if (serviceStarted == false) {
			serviceStarted = true;
			Platform.getTaskManager().schedule(runnableImpl, 0);
		}
	}
	
	private Runnable runnableImpl = new Runnable() {
		public void run() {
			List<Map> list = new ArrayList<Map>();
			synchronized (PaymentDB.LOCK) {
				paymentdb = new SQLTransaction("clfcpayment.db");
				paymentSvc.setDBContext(paymentdb.getContext());
				try {
					paymentdb.beginTransaction();
					list = paymentSvc.getPendingPayments(5);
					paymentdb.commit();
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					paymentdb.endTransaction();
				}
			}

			execPayment(list);

			synchronized (PaymentDB.LOCK) {
				DBContext ctx = new DBContext("clfcpayment.db");
				paymentSvc.setDBContext(ctx);
				try {
					hasUnpostedPayments = paymentSvc.hasUnpostedPayments();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
//			paymentdb = new SQLTransaction("clfcpayment.db");
//			clfcdb = new DBContext("clfc.db");
////			System.out.println("PostPendingPayments");
////			paymentdb = new SQLTransaction("clfcpayment.db");
////			clfcdb = new DBContext("clfc.db"); 
////			System.out.println("clfcdb -> "+clfcdb);
//			try {
//				paymentdb.beginTransaction();
////				clfcdb.beginTransaction();
//				runImpl();
//				paymentdb.commit();
////				clfcdb.commit();
////				SQLTransaction txn = new SQLTransaction("clfc.db");
////				txn.execute(this);
//			} catch (Throwable t) {
//				System.out.println("[PaymentService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
//				t.printStackTrace();
//			}  finally {
//				paymentdb.endTransaction();
//				clfcdb.close();
//			}
//
//			paymentdb2 = new DBContext("clfcpayment.db");
//			try {
//				paymentSvc.setDBContext(paymentdb2);
//				hasUnpostedPayments = paymentSvc.hasUnpostedPayments();
//			} catch (Exception e) {;}
			
			if (hasUnpostedPayments == true) {
				delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
				Platform.getTaskManager().schedule(runnableImpl, delay*1000);				
			} else if (hasUnpostedPayments == false) {
				serviceStarted = false;
			}
		}
		
		private void execPayment(List<Map> list) {
			
			if (!list.isEmpty()) {
				size = list.size();
				for(int i=0; i<size; i++) {
					proxy = new MapProxy((Map) list.get(i));
					
					mode = "ONLINE_WIFI";
					networkStatus = app.getNetworkStatus();
					if (networkStatus == 1) {
						mode = "ONLINE_MOBILE";
					}
					
					collector.clear();
					collector.put("objid", proxy.getString("collectorid"));
					collector.put("name", proxy.getString("collectorname"));
					
//					System.out.println("loanappid -> "+map.containsKey("loanappid"));
//					mCollectionSheet = collectionSheetSvc.findCollectionSheetByLoanappid(proxy.getString("loanappid"));//clfcdb.find(sql, new Object[]{proxy.getString("loanappid")});//dbCs.findCollectionSheetByLoanappid();
					collectionSheet.clear();
					collectionSheet.put("detailid", proxy.getString("detailid"));
					
					loanapp.clear();
					loanapp.put("objid", proxy.getString("loanappid"));
					loanapp.put("appno", proxy.getString("appno"));
					collectionSheet.put("loanapp", loanapp);
					
					borrower.clear();
					borrower.put("objid", proxy.getString("borrowerid"));
					borrower.put("name", proxy.getString("borrowername"));
					collectionSheet.put("borrower", borrower);
					
					payment.clear();
					payment.put("objid", proxy.getString("objid"));
					payment.put("refno", proxy.getString("refno"));
					payment.put("txndate", proxy.getString("txndate"));
					payment.put("type", proxy.getString("paymenttype"));
					payment.put("amount", proxy.getString("paymentamount"));
					payment.put("paidby", proxy.getString("paidby"));
					
					params.clear();
					params.put("type", proxy.getString("cstype"));
					params.put("sessionid", proxy.getString("sessionid"));
					params.put("routecode", proxy.getString("routecode"));
					params.put("mode", mode);
					params.put("trackerid", proxy.getString("trackerid"));
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
						synchronized (PaymentDB.LOCK) {
							paymentdb = new SQLTransaction("clfcpayment.db");
							paymentSvc.setDBContext(paymentdb.getContext());
							try {
								paymentdb.beginTransaction();
								paymentSvc.approvePaymentById(proxy.getString("objid"));
								paymentdb.commit();
							} catch (Throwable t) {
								t.printStackTrace();
							} finally {
								paymentdb.endTransaction();
							}
						}
					}
				}
			}
		}
		
//		private void runImpl() throws Exception {
//			paymentSvc.setDBContext(paymentdb.getContext());
//			
////			System.out.println("pass 1");
//			list = paymentSvc.getPendingPayments();
//			if (!list.isEmpty()) {						
//				collectionSheetSvc.setDBContext(clfcdb); 
//				collectionSheetSvc.setCloseable(false);
//				
//				systemSvc.setDBContext(clfcdb); 
//				systemSvc.setCloseable(false);
//
////				System.out.println("pass 2");
//				trackerid = systemSvc.getTrackerid();
//				
////				System.out.println("pass 3");
//				size = list.size();
//				for (int i=0; i<size; i++) { 
////					System.out.println("loop PaymentService items");
//					proxy = new MapProxy((Map) list.get(i));
//					
//					mode = "ONLINE_WIFI";
//					networkStatus = app.getNetworkStatus();
//					if (networkStatus == 1) {
//						mode = "ONLINE_MOBILE";
//					}
//					
//					collector.clear();
//					collector.put("objid", proxy.getString("collectorid"));
//					collector.put("name", proxy.getString("collectorname"));
//					
////					System.out.println("loanappid -> "+map.containsKey("loanappid"));
//					mCollectionSheet = collectionSheetSvc.findCollectionSheetByLoanappid(proxy.getString("loanappid"));//clfcdb.find(sql, new Object[]{proxy.getString("loanappid")});//dbCs.findCollectionSheetByLoanappid();
//					collectionSheet.clear();
//					collectionSheet.put("detailid", mCollectionSheet.get("detailid").toString());
//					
//					loanapp.clear();
//					loanapp.put("objid", mCollectionSheet.get("loanappid").toString());
//					loanapp.put("appno", mCollectionSheet.get("appno").toString());
//					collectionSheet.put("loanapp", loanapp);
//					
//					System.out.println("cs -> "+mCollectionSheet);
//					borrower.clear();
//					borrower.put("objid", mCollectionSheet.get("acctid").toString());
//					borrower.put("name", mCollectionSheet.get("acctname").toString());
//					collectionSheet.put("borrower", borrower);
//					
//					payment.clear();
//					payment.put("objid", proxy.getString("objid"));
//					payment.put("refno", proxy.getString("refno"));
//					payment.put("txndate", proxy.getString("txndate"));
//					payment.put("type", proxy.getString("paymenttype"));
//					payment.put("amount", proxy.getString("paymentamount"));
//					payment.put("paidby", proxy.getString("paidby"));
//					
//					params.clear();
//					params.put("type", mCollectionSheet.get("type").toString());
//					params.put("sessionid", mCollectionSheet.get("sessionid").toString());
//					params.put("routecode", proxy.getString("routecode"));
//					params.put("mode", mode);
//					params.put("trackerid", trackerid);
//					params.put("longitude", proxy.getDouble("longitude"));
//					params.put("latitude", proxy.getDouble("latitude"));
//					params.put("collector", collector);
//					params.put("collectionsheet", collectionSheet);
//					params.put("payment", payment);
//					
//					response.clear();
//					for (int j=0; j<10; j++) {
//						try {
//							response = svc.postPayment(params);
//							break;
//						} catch (Throwable e) {;} 
//					}
//					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
//						paymentSvc.approvePaymentById(proxy.getString("objid"));
//					}
//				}
//			}
//		}
	};
}
