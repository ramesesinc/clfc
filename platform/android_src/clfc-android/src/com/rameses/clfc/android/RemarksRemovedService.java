package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBRemarksRemoved;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.SQLTransaction;

public class RemarksRemovedService 
{
	private ApplicationImpl app;
	private Handler handler;
	
	public RemarksRemovedService(ApplicationImpl app) {
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
//			System.out.println("PostPendingRemarksRemoved");
			SQLTransaction remarksremoveddb = new SQLTransaction("clfcremarksremoved.db");
			SQLTransaction clfcdb = new SQLTransaction("clfc.db");
			try {
				remarksremoveddb.beginTransaction();
				clfcdb.beginTransaction();
				runImpl(remarksremoveddb, clfcdb);
				remarksremoveddb.commit();
				clfcdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				System.out.println("[RemarksRemovedService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
			} finally {
				remarksremoveddb.endTransaction();
				clfcdb.endTransaction();
			}

			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new RunnableImpl(), delay*1000);
		}
		
		private void runImpl(SQLTransaction remarksremoveddb, SQLTransaction clfcdb) throws Exception {
			DBRemarksRemoved dbRr = new DBRemarksRemoved();
			dbRr.setDBContext(remarksremoveddb.getContext());
			
			List<Map> list = dbRr.getPendingRemarksRemoved();
			
			if (!list.isEmpty()) {				
				Map map;
				Map params = new HashMap();
				Map response = new HashMap();
				LoanPostingService svc = new LoanPostingService();
				
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(clfcdb.getContext());
				Map collectionSheet = new HashMap();
				
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);

					params.clear();
					collectionSheet = dbCs.findCollectionSheetByLoanappid(map.get("loanappid").toString());
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
	}
}
