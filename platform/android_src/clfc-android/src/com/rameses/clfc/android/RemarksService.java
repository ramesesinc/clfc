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
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class RemarksService 
{
	private ApplicationImpl app;
	private AppSettingsImpl appSettings;
	private Handler handler;
	private SQLTransaction remarksdb = new SQLTransaction("clfcremarks.db");
	private DBContext clfcdb = new DBContext("clfc.db");
	private DBContext remarksdb2 = new DBContext("clfcremarks.db");
	private DBRemarksService remarksSvc = new DBRemarksService();
	private DBCollectionSheet dbCollectionSheet = new DBCollectionSheet();
	private DBSystemService systemSvc = new DBSystemService();
	private List<Map> list;
	private String mode = "";
	private String trackerid;
	private Map map = new HashMap();
	private Map mCollectionSheet = new HashMap();
	private Map params = new HashMap();
	private Map loanapp = new HashMap();
	private Map borrower = new HashMap();
	private Map response = new HashMap();
	private Map collector = new HashMap();
	private Map collectionSheet = new HashMap();
	private int networkStatus = 0;
	private int delay;
	private int size;
	private LoanPostingService svc = new LoanPostingService();
	private boolean hasUnpostedRemarks = false;
	
	public static boolean serviceStarted = false;
	
	public RemarksService(ApplicationImpl app) {
		this.app = app;
		appSettings = (AppSettingsImpl) app.getAppSettings();
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
//			System.out.println("PostPendingRemarks");
			try {
				remarksdb.beginTransaction();
//				clfcdb.beginTransaction();
				runImpl();
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
			
			try {
				remarksSvc.setDBContext(remarksdb2);
				hasUnpostedRemarks = remarksSvc.hasUnpostedRemarks();
			} catch (Exception e) {;}
			
			if (hasUnpostedRemarks == true) {
				delay = appSettings.getUploadTimeout();
				Platform.getTaskManager().schedule(runnableImpl, delay*1000);				
			} else if (hasUnpostedRemarks == false) {
				serviceStarted = false;
			}
		}
		
		private void runImpl() throws Exception {
			remarksSvc.setDBContext(remarksdb.getContext());
			
			list = remarksSvc.getPendingRemarks();
//			System.out.println("list -> "+list);
			if (!list.isEmpty()) {
				dbCollectionSheet.setDBContext(clfcdb);	
				dbCollectionSheet.setCloseable(false);
				
				systemSvc.setDBContext(clfcdb);
				systemSvc.setCloseable(false);
				
				trackerid = systemSvc.getTrackerid();
				size = list.size();
				for (int i=0; i<list.size(); i++) {
					map = (Map) list.get(i);
					
					mode = "ONLINE_WIFI";
					networkStatus = app.getNetworkStatus();
					if (networkStatus == 1) {
						mode = "ONLINE_MOBILE";
					}
					
					collector.put("objid", map.get("collectorid").toString());
					collector.put("name", map.get("collectorname").toString());
					
					mCollectionSheet = dbCollectionSheet.findCollectionSheetByLoanappid(map.get("loanappid").toString());
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
					params.put("trackerid", trackerid);
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
						remarksSvc.approveRemarksByLoanappid(map.get("loanappid").toString());
					}
				}
			}
		}
	};
}
