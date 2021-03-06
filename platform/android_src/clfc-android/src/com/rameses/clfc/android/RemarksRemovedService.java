package com.rameses.clfc.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.rameses.clfc.android.db.DBRemarksRemoved;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.Task;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class RemarksRemovedService 
{
	private final int SIZE = 6;
	
	private ApplicationImpl app;
	private AppSettingsImpl appSettings;
	private Handler handler;
	private SQLTransaction remarksremoveddb;
	private DBRemarksRemoved remarksRemoved = new DBRemarksRemoved();
	private LoanPostingService svc = new LoanPostingService();
	private MapProxy proxy;
	private Map params = new HashMap();
	private Map response = new HashMap();
	private int delay;
	private int size;
	private boolean hasPendingRemarksRemoved = false;
	private Task actionTask;
	
	public static boolean serviceStarted = false;
	
	public RemarksRemovedService(ApplicationImpl app) {
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
			delay = appSettings.getUploadTimeout()*1000;
			createTask();
			Platform.getTaskManager().schedule(actionTask, 1000, delay);
		}
	}
	
	public void restart() {
		if (serviceStarted == true) {
			actionTask.cancel();
			actionTask = null;
			serviceStarted = false;
		}
		start();
	}
	
	private void createTask() {
		actionTask = new Task() {
			public void run() {
				List<Map> list = new ArrayList<Map>();
				synchronized (RemarksRemovedDB.LOCK) {
					remarksremoveddb = new SQLTransaction("clfcremarksremoved.db");
					remarksRemoved.setDBContext(remarksremoveddb.getContext());
					try {
						remarksremoveddb.beginTransaction();
						list = remarksRemoved.getPendingRemarksRemoved(SIZE);
						remarksremoveddb.commit();
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						remarksremoveddb.endTransaction();
					}
				}
				
				execRemarksRemoved(list);
				
				hasPendingRemarksRemoved = false;
				synchronized (RemarksRemovedDB.LOCK) {
					DBContext ctx = new DBContext("clfcremarksremoved.db");
					remarksRemoved.setDBContext(ctx);
					try {
						hasPendingRemarksRemoved = remarksRemoved.hasPendingRemarksRemoved();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
//				hasPendingRemarksRemoved = false;
//				if (list.size() == SIZE) {
//					hasPendingRemarksRemoved = true;
//				}
				
				if (hasPendingRemarksRemoved == false) {
					serviceStarted = false;
					this.cancel();
				}
//				synchronized (RemarksRemovedDB.LOCK) {
//					DBContext ctx = new DBContext("clfcremarksremoved.db");
//					remarksRemoved.setDBContext(ctx);
//					try {
//						hasPendingRemarksRemoved = remarksRemoved.hasPendingRemarksRemoved();
//					} catch (Throwable t) {
//						t.printStackTrace();
//					}
//				}

//				if (hasPendingRemarksRemoved == true) {
//					Platform.getTaskManager().schedule(runnableImpl, delay*1000);	
//				} else if (hasPendingRemarksRemoved == false) {
//					serviceStarted = false;
//				}
			}

			private void execRemarksRemoved(List<Map> list) {
					
				if (!list.isEmpty()) {
					size = (list.size() < SIZE-1? list.size() : SIZE-1);
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
							synchronized (RemarksRemovedDB.LOCK) {
								remarksremoveddb = new SQLTransaction("clfcremarksremoved.db");
								remarksRemoved.setDBContext(remarksremoveddb.getContext());
								try {
									remarksremoveddb.beginTransaction();
									remarksremoveddb.delete("remarks_removed", "loanappid=?", new Object[]{proxy.getString("loanappid")});
									remarksremoveddb.commit();
								} catch (Throwable t) {
									t.printStackTrace();
								} finally {
									remarksremoveddb.endTransaction();
								}
							}
						}
					}
				}
			}
		};
	}
}
