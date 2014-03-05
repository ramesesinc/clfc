package com.rameses.clfc.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class RemarksService 
{
	private ApplicationImpl app;
	private AppSettingsImpl appSettings;
	private Handler handler;
	private SQLTransaction remarksdb = new SQLTransaction("clfcremarks.db");
	private DBRemarksService remarksSvc = new DBRemarksService();
	private List<Map> list;
	private String mode = "";
	private MapProxy proxy;
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
		} 
		if (serviceStarted == false) {
			serviceStarted = true;
			Platform.getTaskManager().schedule(runnableImpl, 0);
		}
	}
	
	private Runnable runnableImpl = new Runnable() 
	{
		public void run() {
			synchronized (RemarksDB.LOCK) {
				remarksdb = new SQLTransaction("clfcremarks.db");
				try {
					remarksdb.beginTransaction();
					execRemarks(remarksdb);
					
					remarksSvc.setDBContext(remarksdb.getContext());
					hasUnpostedRemarks = remarksSvc.hasUnpostedRemarks();
					
					remarksdb.commit();
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					remarksdb.endTransaction();
				}
			}

			if (hasUnpostedRemarks == true) {
				delay = appSettings.getUploadTimeout();
				Platform.getTaskManager().schedule(runnableImpl, delay*1000);				
			} else if (hasUnpostedRemarks == false) {
				serviceStarted = false;
			}
		}
		
		private void execRemarks(SQLTransaction remarksdb) throws Exception {
			remarksSvc.setDBContext(remarksdb.getContext());
			
			list = remarksSvc.getPendingRemarks(5);
			if (!list.isEmpty()) {
				size = list.size();
				String loanappid = "";
				for (int i=0; i<size; i++) {
					proxy = new MapProxy((Map) list.get(i));
					
					mode = "ONLINE_WIFI";
					networkStatus = app.getNetworkStatus();
					if (networkStatus == 1) {
						mode = "ONLINE_MOBILE";
					}
					
					collector.put("objid", proxy.getString("collectorid"));
					collector.put("name", proxy.getString("collectorname"));
					
//					mCollectionSheet = dbCollectionSheet.findCollectionSheetByLoanappid(map.get("loanappid").toString());
					collectionSheet.clear();
//					collectionSheet.put("detailid", mCollectionSheet.get("detailid").toString());
					collectionSheet.put("detailid", proxy.getString("detailid"));
					
					loanapp.clear();
					loanappid = proxy.getString("loanappid");
//					loanapp.put("objid", mCollectionSheet.get("loanappid").toString());
//					loanapp.put("appno", mCollectionSheet.get("appno").toString());
					loanapp.put("objid", loanappid);
					loanapp.put("appno", proxy.getString("appno"));
					collectionSheet.put("loanapp", loanapp);
					
					borrower.clear();
//					borrower.put("objid", mCollectionSheet.get("acctid").toString());
//					borrower.put("name", mCollectionSheet.get("acctname").toString());
					borrower.put("objid", proxy.getString("borrowerid"));
					borrower.put("name", proxy.getString("borrowername"));
					collectionSheet.put("borrower", borrower);
					
					params.clear();
					params.put("sessionid", proxy.getString("sessionid"));
					params.put("routecode", proxy.getString("routecode"));
					params.put("mode", mode);
					params.put("trackerid", proxy.getString("trackerid"));
					params.put("longitude", proxy.getDouble("longitude"));
					params.put("latitiude", proxy.getDouble("latitude"));
					params.put("collector", collector);
					params.put("collectionsheet", collectionSheet);
					params.put("remarks", proxy.getString("remarks"));
					
					response.clear();
					for (int j=0; j<10; j++) {
						try {
							response = svc.updateRemarks(params);
							break;
						} catch (Throwable e) {;}
					}
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						remarksSvc.approveRemarksByLoanappid(loanappid);
					}
				}
			}
		}
	};
}
