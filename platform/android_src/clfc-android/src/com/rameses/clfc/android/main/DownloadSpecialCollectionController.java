package com.rameses.clfc.android.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBSpecialCollection;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.services.LoanBillingService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIActivity;
import com.rameses.db.android.SQLTransaction;

public class DownloadSpecialCollectionController 
{
	private UIActivity activity;
	private ProgressDialog progressDialog;
	private Map params;

	DownloadSpecialCollectionController(UIActivity activity, ProgressDialog progressDialog, Map params) {
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.params = params;
	}
	
	void execute() throws Exception {
		Platform.runAsync(new DownloadActionProcess());
	}

	private Handler errorhandler = new Handler() {  
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog.isShowing()) progressDialog.dismiss();
			
			Bundle data = msg.getData();			
			Object o = data.getSerializable("response"); 
			if (o instanceof Throwable) {
				Throwable t = (Throwable)o;
				ApplicationUtil.showShortMsg("[ERROR] " + t.getMessage());		
			} else {
				ApplicationUtil.showShortMsg("[ERROR] " + o);	
			} 
		} 
	}; 
	
	private Handler successhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			activity.getHandler().post(new Runnable() {
				public void run() {
					((SpecialCollectionActivity) activity).loadRequests();
				}
			});
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg("Successfully downloaded billing!", activity);
		}
	};	
	private class DownloadActionProcess implements Runnable {
		public void run() {
			Bundle data = new Bundle();
			Message message = null;
			Handler handler = null;
			try {
				progressDialog.setMessage("Downloading special collection..");
				activity.runOnUiThread(new Runnable() {
					public void run() {
						if (!progressDialog.isShowing()) progressDialog.show();
					}
				});
				
				LoanBillingService svc = new LoanBillingService();
				Map response = svc.downloadSpecialCollection(params);
				saveSpecialCollection(response);
				
				data.putString("response", "success");
				message = successhandler.obtainMessage();
				handler = successhandler;
			} catch (Throwable t) {
				t.printStackTrace();
				Platform.getLogger().log(t);
				data.putSerializable("response", t);
				message = errorhandler.obtainMessage();
				handler = errorhandler;
			}
			
			message.setData(data);
			handler.sendMessage(message);
		}

		private void saveSpecialCollection(Map map) throws Exception {
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				saveSpecialCollectionImpl(txn, map);
				txn.commit();
			} catch(Exception e) {
				throw e;
			} finally {
				txn.endTransaction();
			}
		}
		
		private void saveSpecialCollectionImpl(SQLTransaction txn, Map map) throws Exception {
			DBSpecialCollection dbSc = new DBSpecialCollection();
			dbSc.setDBContext(txn.getContext());
			
			DBSystemService dbSys = new DBSystemService();
			dbSys.setDBContext(txn.getContext());
			
			DBCollectionSheet dbCs = new DBCollectionSheet();
			dbCs.setDBContext(txn.getContext());
			
			String billingid = dbSys.getBillingid();
			String collectorid = SessionContext.getProfile().getUserId();
			
			Map params = new HashMap();
			Map item = (Map) map.get("request");
			
			params.clear();
			params.put("objid", item.get("objid").toString());
			params.put("state", item.get("state").toString());
			System.out.println("params -> "+params);
			dbSc.changeStateById(params);
			
			List<Map> list = (List<Map>) map.get("routes");

			if (!list.isEmpty()) {
				Map m;
				for (int i=0; i<list.size(); i++) {
					m = (Map) list.get(i);
					
					params.clear();
					params.put("routecode", m.get("code").toString());
					params.put("routedescription", m.get("description").toString());
					params.put("routearea", m.get("area").toString());
					params.put("state", "ACTIVE");
					params.put("sessionid", billingid);
					params.put("collectorid", collectorid);
					txn.insert("route", params);
				}
			}
			
			list = (List<Map>) map.get("list");
			
			if (!list.isEmpty()) {				
				Map m;
				for (int i=0; i<list.size(); i++) {
					m = (Map) list.get(i);
										
					params.clear();
					params.put("loanappid", m.get("loanappid").toString());
					params.put("detailid", m.get("objid").toString());
					params.put("seqno", dbCs.getCountByRoutecode(m.get("routecode").toString()));
					params.put("appno", m.get("appno").toString());
					params.put("acctid", m.get("acctid").toString());
					params.put("acctname", m.get("acctname").toString());
					params.put("loanamount", Double.parseDouble(m.get("loanamount").toString()));
					params.put("term", Integer.parseInt(m.get("term").toString()));
					params.put("balance", Double.parseDouble(m.get("balance").toString()));
					params.put("dailydue", Double.parseDouble(m.get("dailydue").toString()));
					params.put("amountdue", Double.parseDouble(m.get("amountdue").toString()));
					params.put("interest" ,Double.parseDouble(m.get("interest").toString()));
					params.put("penalty", Double.parseDouble(m.get("penalty").toString()));
					params.put("others", Double.parseDouble(m.get("others").toString()));
					params.put("overpaymentamount", Double.parseDouble(m.get("overpaymentamount").toString()));
					params.put("refno", m.get("refno").toString());
					params.put("routecode", m.get("routecode").toString());
					params.put("duedate", m.get("dtmatured").toString());
					params.put("isfirstbill", Integer.parseInt(m.get("isfirstbill").toString()));
					params.put("paymentmethod", m.get("paymentmethod").toString());
					params.put("homeaddress", m.get("homeaddress").toString());
					params.put("collectionaddress", m.get("collectionaddress").toString());
					params.put("sessionid", m.get("sessionid").toString());
					params.put("type", "");
					txn.insert("collectionsheet", params);
				}
			}
		}
	}
}
