package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLExecutor;
import com.rameses.db.android.SQLTransaction;

public class RemarksService 
{
	private ApplicationImpl app;
	private Handler handler;
	
	public RemarksService(ApplicationImpl app) {
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
//			System.out.println("PostPendingRemarks");
			SQLTransaction remarksdb = new SQLTransaction("clfcremarks.db");
			DBContext clfcdb = new DBContext("clfc.db");
			try {
				remarksdb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl(remarksdb, clfcdb);
				remarksdb.commit();
//				clfcdb.commit();
//				SQLTransaction txn = new SQLTransaction("clfc.db");
//				txn.execute(this);
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("[RemarksService.RunnableImpl] error caused by " + t.getClass().getName() + ": " + t.getMessage());
			} finally {
				remarksdb.endTransaction();
				clfcdb.close();
			}

			int delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
			Platform.getTaskManager().schedule(new RunnableImpl(), delay*1000);
		}
		
		private void runImpl(SQLTransaction remarksdb, DBContext clfcdb) throws Exception {
			DBRemarksService dbRs = new DBRemarksService();
			dbRs.setDBContext(remarksdb.getContext());
			
			List<Map> list = dbRs.getPendingRemarks();
//			System.out.println("list -> "+list);
			if (!list.isEmpty()) {
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(clfcdb);	
				dbCs.setCloseable(false);
				
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(clfcdb);
				dbSys.setCloseable(false);
				
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
					
					borrower.clear();
					borrower.put("objid", mCollectionSheet.get("acctid").toString());
					borrower.put("name", mCollectionSheet.get("acctname").toString());
					collectionSheet.put("borrower", borrower);
					
					params.clear();
					params.put("sessionid", mCollectionSheet.get("sessionid").toString());
					params.put("routecode", mCollectionSheet.get("routecode").toString());
					params.put("mode", mode);
					params.put("trackerid",dbSys.getTrackerid());
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
						} catch (Throwable e) {;}
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						dbRs.approveRemarksByLoanappid(map.get("loanappid").toString());
					}
				}
			}
		}
	}
}
