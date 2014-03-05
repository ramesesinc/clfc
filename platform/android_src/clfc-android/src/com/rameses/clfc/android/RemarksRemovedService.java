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
import com.rameses.util.MapProxy;

public class RemarksRemovedService 
{
	private ApplicationImpl app;
	private Handler handler;
	private SQLTransaction remarksremoveddb;
	private DBRemarksRemoved remarksRemoved = new DBRemarksRemoved();
	private LoanPostingService svc = new LoanPostingService();
	private MapProxy proxy;
	private Map params = new HashMap();
	private Map response = new HashMap();
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
		}  
		if (serviceStarted == false) {
			serviceStarted = true;
			Platform.getTaskManager().schedule(runnableImpl, 0);
		}
	}
	
	private Runnable runnableImpl = new Runnable() 
	{
		public void run() {
			synchronized (RemarksRemovedDB.LOCK) {
				remarksremoveddb = new SQLTransaction("clfcremarksremoved.db");
				try {
					remarksremoveddb.beginTransaction();
					execRemarksRemoved(remarksremoveddb);
					
					remarksRemoved.setDBContext(remarksremoveddb.getContext());
					hasPendingRemarksRemoved = remarksRemoved.hasPendingRemarksRemoved();

					remarksremoveddb.commit();
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					remarksremoveddb.endTransaction();
				}
			}

			if (hasPendingRemarksRemoved == true) {
				delay = ((AppSettingsImpl) Platform.getApplication().getAppSettings()).getUploadTimeout();
				Platform.getTaskManager().schedule(runnableImpl, delay*1000);	
			} else if (hasPendingRemarksRemoved == false) {
				serviceStarted = false;
			}
		}
		
		private void execRemarksRemoved(SQLTransaction remarksremoveddb) throws Exception {
			remarksRemoved.setDBContext(remarksremoveddb.getContext());
			
			list = remarksRemoved.getPendingRemarksRemoved(5);			
			if (!list.isEmpty()) {				
				
				size = list.size();
				for (int i=0; i<size; i++) {
					proxy = new MapProxy((Map) list.get(i));

					params.clear();
					params.put("detailid", proxy.getString("detailid"));
					
					response.clear();
					for (int j=0; i<10; j++) {
						try {
							response = svc.removeRemarks(params);
							break;
						} catch (Throwable e) {;}
					}
					
					if (response.containsKey("response") && response.get("response").toString().toLowerCase().equals("success")) {
						remarksremoveddb.delete("remarks_removed", "loanappid=?", new Object[]{proxy.getString("loanappid")});
					}
				}
			}
		}
	};
}
