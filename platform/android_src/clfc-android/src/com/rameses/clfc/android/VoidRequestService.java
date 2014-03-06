package com.rameses.clfc.android;

import java.util.ArrayList;
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
	private SQLTransaction requestdb = new SQLTransaction("clfcrequest.db");
	private DBVoidService voidService = new DBVoidService();
	private LoanPostingService svc = new LoanPostingService();
	private Map map;
	private Map params = new HashMap();
	private Map response = new HashMap();
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
			List<Map> list = new ArrayList<Map>();
			synchronized (VoidRequestDB.LOCK) {
				requestdb = new SQLTransaction("clfcrequest.db");
				voidService.setDBContext(requestdb.getContext());
//				System.out.println("ApprovePendingVoidRequest");
				try {
					requestdb.beginTransaction();
					list = voidService.getPendingVoidRequests(5); 
					requestdb.commit();
				} catch (Throwable t) {
					System.out.println("[ApprovePendingVoidRequest] error caused by " + t.getClass().getName() + ": " + t.getMessage()); 
				} finally {
					requestdb.endTransaction();
				}
				
			}
			
			execRequests(list);
			
			synchronized (VoidRequestDB.LOCK) {
				DBContext ctx = new DBContext("clfcrequest.db");
				voidService.setDBContext(ctx);
				try {
					hasPendingRequest = voidService.hasPendingVoidRequest();
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			if (hasPendingRequest == true) {
				Platform.getTaskManager().schedule(runnableImpl, 2000);
			} else if (hasPendingRequest == false) {
				serviceStarted = false;
			}
		}
		
		private void execRequests(List<Map> list) {
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
					
					synchronized (VoidRequestDB.LOCK) {
						String objid = map.get("objid").toString();
						requestdb = new SQLTransaction("clfcrequest.db");
						voidService.setDBContext(requestdb.getContext());
						try {
							requestdb.beginTransaction();
							if ("APPROVED".equals(state)) {
								voidService.approveVoidPaymentById(objid);
							} else if ("DISAPPROVED".equals(state)) {
								voidService.disapproveVoidPaymentById(objid);
							}
							requestdb.commit();
						} catch (Throwable t) {
							t.printStackTrace();
						} finally {
							requestdb.endTransaction();
						}
					}
				}
			}
		}
	};
}
