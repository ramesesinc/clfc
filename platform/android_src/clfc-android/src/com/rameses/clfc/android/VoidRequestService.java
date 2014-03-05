package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class VoidRequestService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction requestdb = new SQLTransaction("clfcrequest.db");
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
			synchronized (VoidRequestDB.LOCK) {
				requestdb = new SQLTransaction("clfcrequest.db");
//				System.out.println("ApprovePendingVoidRequest");
				try {
					requestdb.beginTransaction();
					runImpl(requestdb);
					
					voidService.setDBContext(requestdb.getContext());
					hasPendingRequest = voidService.hasPendingVoidRequest();
					
					requestdb.commit();
//					SQLTransaction txn = new SQLTransaction("clfc.db");
//					txn.execute(this);
				} catch (Throwable t) {
					System.out.println("[ApprovePendingVoidRequest] error caused by " + t.getClass().getName() + ": " + t.getMessage()); 
				} finally {
					requestdb.endTransaction();
				}
				
			}
			
			if (hasPendingRequest == true) {
				Platform.getTaskManager().schedule(runnableImpl, 2000);
			} else if (hasPendingRequest == false) {
				serviceStarted = false;
			}
		}
		
		private void runImpl(SQLTransaction requestdb) throws Exception {
			voidService.setDBContext(requestdb.getContext());
			
			list = voidService.getPendingVoidRequests(5);
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
