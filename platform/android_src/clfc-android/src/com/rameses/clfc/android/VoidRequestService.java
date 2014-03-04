package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class VoidRequestService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction txn = new SQLTransaction("clfcrequest.db");
	private DBContext requestdb = new DBContext("clfcrequest.db");
	private DBVoidService voidService = new DBVoidService();
	private LoanPostingService svc = new LoanPostingService();
	private Map map;
	private Map params = new HashMap();
	private Map response = new HashMap();
	private List<Map> list;
	private int size;
	private boolean hasPendingRequest = false;
	private String state = "";
	
	public static boolean serviceStarted = false;
	
	public VoidRequestService(ApplicationImpl app) {
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
	
	private Runnable runnableImpl = new Runnable()
	{
		public void run() {
			txn = new SQLTransaction("clfcrequest.db");
//			System.out.println("ApprovePendingVoidRequest");
			try {
				txn.beginTransaction();
				runImpl(txn);
				txn.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				System.out.println("[ApprovePendingVoidRequest] error caused by " + t.getClass().getName() + ": " + t.getMessage()); 
			} finally {
				txn.endTransaction();
			}

			requestdb = new DBContext("clfcrequest.db");
			try {
				voidService.setDBContext(requestdb);
				hasPendingRequest = voidService.hasPendingVoidRequest();
			} catch (Exception e) {;}
			
			if (hasPendingRequest == true) {
				Platform.getTaskManager().schedule(runnableImpl, 2000);
			} else if (hasPendingRequest == false) {
				serviceStarted = false;
			}
		}
		
		private void runImpl(SQLTransaction txn) throws Exception {
			voidService.setDBContext(txn.getContext());
			
			list = voidService.getPendingVoidRequests();
			if (!list.isEmpty()) {
				size = list.size();
				for (int i=0; i<size; i++) {
					map = (Map) list.get(i);
					
					params.clear();
					params.put("voidid", map.get("objid").toString());
					
					response = svc.checkVoidPaymentRequest(params);
					if (response != null) {
						state = MapProxy.getString(response, "state");
					}
					if ("APPROVED".equals(state)) {
						voidService.approveVoidPaymentById(map.get("objid").toString());
					} else if ("DISAPPROVED".equals(state)) {
						voidService.disapproveVoidPaymentById(map.get("objid").toString());
					}
				}
			}
		}	
	};
}
