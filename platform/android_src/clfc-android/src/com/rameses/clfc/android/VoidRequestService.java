package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.SQLTransaction;

public class VoidRequestService 
{
	private ApplicationImpl app;
	private Handler handler;
	
	public VoidRequestService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			Platform.getTaskManager().schedule(new RunnableImpl(), 0, 2000);
		} 
	}

	private class RunnableImpl implements Runnable
	{
		public void run() {
//			System.out.println("ApprovePendingVoidRequest");
			SQLTransaction txn = new SQLTransaction("clfcrequest.db");
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
}
