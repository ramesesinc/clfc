package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBRemarksRemoved;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

public class RemarksRemovedService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction remarksremoveddb = new SQLTransaction("clfcremarksremoved.db");
	private DBContext clfcdb = new DBContext("clfc.db");
	private DBContext remarksremoveddb2 = new DBContext("clfcremarksremoved.db");
	private DBRemarksRemoved remarksRemoved = new DBRemarksRemoved();
	private DBCollectionSheet dbCollectionSheet = new DBCollectionSheet();	
	private LoanPostingService svc = new LoanPostingService();
	private Map map = new HashMap();
	private Map params = new HashMap();
	private Map response = new HashMap();
	private Map collectionSheet = new HashMap();
	private List<Map> list;
	private int delay;
	private int size;
	private boolean hasPendingRemarksRemoved = false;
	
	public static boolean serviceStarted = false;
	
	public RemarksRemovedService(ApplicationImpl app) {
		this.app = app;
	}
	
	public void start() {
		if (handler == null) { 
			handler = new Handler();
//			new RunnableImpl().run(); 
			if (serviceStarted == false) {
				serviceStarted = true;
				Platform.getTaskManager().schedule(runnableImpl, 0);
			}
		} 
	}
	
	private Runnable runnableImpl = new Runnable() 
	{
		public void run() {
//			System.out.println("PostPendingRemarksRemoved");
			try {
				remarksremoveddb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl();
				remarksremoveddb.commit();
//				clfcdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				System.out.println("[RemarksRemovedService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
			} finally {
				remarksremoveddb.endTransaction();
				clfcdb.close();
			}

			try {
				remarksRemoved.setDBContext(remarksremoveddb2);
				hasPendingRemarksRemoved = remarksRemoved.hasPendingRemarksRemoved();
			} catch (Exception e) {;}
			
			if (hasPendingRemarksRemoved == true) {
				delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
				Platform.getTaskManager().schedule(runnableImpl, delay*1000);	
			} else if (hasPendingRemarksRemoved == false) {
				serviceStarted = false;
			}
		}
		
		private void runImpl() throws Exception {
			remarksRemoved.setDBContext(remarksremoveddb.getContext());
			
			list = remarksRemoved.getPendingRemarksRemoved();			
			if (!list.isEmpty()) {				
				dbCollectionSheet.setDBContext(clfcdb);
				dbCollectionSheet.setCloseable(false);
				
				size = list.size();
				for (int i=0; i<size; i++) {
					map = (Map) list.get(i);

					params.clear();
					collectionSheet = dbCollectionSheet.findCollectionSheetByLoanappid(map.get("loanappid").toString());
					if (collectionSheet != null && !collectionSheet.isEmpty()) {
						params.put("detailid", collectionSheet.get("detailid").toString());
					} 
					
					response.clear();
					for (int j=0; i<10; j++) {
						try {
							response = svc.removeRemarks(params);
							break;
						} catch (Throwable e) {;}
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						remarksremoveddb.delete("remarks_removed", "loanappid='"+map.get("loanappid").toString()+"'");
					}
				}
			}
		}		
	};
}
