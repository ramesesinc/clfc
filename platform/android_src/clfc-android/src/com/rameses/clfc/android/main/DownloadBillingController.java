package com.rameses.clfc.android.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.services.LoanBillingService;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.TerminalManager;
import com.rameses.client.android.UIActivity;
import com.rameses.db.android.SQLTransaction;

public class DownloadBillingController 
{
	private UIActivity activity;
	private ProgressDialog progressDialog;
	private Map route;

	DownloadBillingController(UIActivity activity, ProgressDialog progressDialog, Map route) {
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.route = route;
	}
	
	void execute() throws Exception {
		Platform.runAsync(new ActionProcess());
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
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg("Successfully downloaded billing!", activity);
		}
	};	
	
	private class ActionProcess implements Runnable 
	{
		public void run() {
			Bundle data = new Bundle();			
			Handler handler = null;
			Message message = null;
			try {
				progressDialog.setMessage("Downloading route "+route.get("description").toString()+" - "+route.get("area").toString());
				activity.runOnUiThread(new Runnable() {
					public void run() {
						if (!progressDialog.isShowing()) progressDialog.show();
					}
				});
				
				Map params = new HashMap();
				params.put("billingid", route.get("billingid").toString());
				params.put("route_code", route.get("code").toString());
				params.put("terminalid", TerminalManager.getTerminalId());
				params.put("userid", SessionContext.getProfile().getUserId());
				
				Location location = NetworkLocationProvider.getLocation();
				params.put("longitude", (location != null? location.getLongitude() : 0.00));
				params.put("latitude", (location != null? location.getLatitude() : 0.00));
				
				LoanBillingService svc = new LoanBillingService();
				Map map = svc.downloadBilling(params);
				runImpl(map);
				
				data.putString("response", "success");
				handler = successhandler;
				message = handler.obtainMessage();
				
			} catch(Throwable t) { 
				data.putSerializable("response", t);
				handler = errorhandler;
				message = handler.obtainMessage();				
				t.printStackTrace();
			} 
			
			message.setData(data);
			handler.sendMessage(message); 
		}
		
		private void runImpl(Map map) throws Exception {
			SQLTransaction txn = new SQLTransaction("clfc.db");
			try {
				txn.beginTransaction();
				execute(txn, map);
				txn.commit();
			} catch (Exception e) {
				throw e;
			} finally {
				txn.endTransaction();
			}
		}
		
		private void execute(SQLTransaction txn, Map map) throws Exception {
			Map params = new HashMap();
			String sql = "SELECT * FROM system WHERE name='trackerid'";
			Map record = txn.find(sql);
			if (record == null || record.isEmpty()) {
				params.clear();
				params.put("name", "trackerid");
				params.put("value", map.get("trackerid").toString());
				txn.insert("system", params);
			}

			String sessionid = route.get("billingid").toString();
			sql = "SELECT * FROM system WHERE name='billingid'";
			record = txn.find(sql);
			if (record == null || record.isEmpty()) {
				params.clear();
				params.put("name", "billingid");
				params.put("value", sessionid);
				txn.insert("system", params);
			}
			
			params.clear();
			params.put("routecode",route.get("code").toString());
			params.put("state", "ACTIVE");
			params.put("routedescription", route.get("description").toString());
			params.put("routearea", route.get("area").toString());
			params.put("sessionid", sessionid);
			params.put("collectorid", SessionContext.getProfile().getUserId());
			txn.insert("route", params);
			
			DBCollectionSheet dbCs = new DBCollectionSheet();
			dbCs.setDBContext(txn.getContext());
			ArrayList billings = (ArrayList) map.get("billings");
			ArrayList list;
			String loanappid;
			for (int i=0; i<billings.size(); i++) {
				params = (Map) billings.get(i);
				
				System.out.println("loanappid -> "+params.get("loanappid").toString());
				record = dbCs.findCollectionSheetByLoanappid(params.get("loanappid").toString());
				if (record == null || record.isEmpty()) {
					Map cs = new HashMap();
					cs.put("loanappid", params.get("loanappid").toString());
					cs.put("detailid", params.get("objid").toString());
					cs.put("seqno", Integer.parseInt(params.get("seqno").toString()));
					cs.put("appno", params.get("appno").toString());
					cs.put("acctid", params.get("acctid").toString());
					cs.put("acctname", params.get("acctname").toString());
					cs.put("loanamount", Double.parseDouble(params.get("loanamount").toString()));
					cs.put("term", Integer.parseInt(params.get("term").toString()));
					cs.put("balance", Double.parseDouble(params.get("balance").toString()));
					cs.put("dailydue", Double.parseDouble(params.get("dailydue").toString()));
					cs.put("amountdue", Double.parseDouble(params.get("amountdue").toString()));
					cs.put("interest" ,Double.parseDouble(params.get("interest").toString()));
					cs.put("penalty", Double.parseDouble(params.get("penalty").toString()));
					cs.put("others", Double.parseDouble(params.get("others").toString()));
					cs.put("overpaymentamount", Double.parseDouble(params.get("overpaymentamount").toString()));
					cs.put("refno", params.get("refno").toString());
					cs.put("routecode", route.get("code").toString());
					cs.put("duedate", params.get("dtmatured").toString());
					cs.put("isfirstbill", Integer.parseInt(params.get("isfirstbill").toString()));
					cs.put("paymentmethod", params.get("paymentmethod").toString());
					cs.put("homeaddress", params.get("homeaddress").toString());
					cs.put("collectionaddress", params.get("collectionaddress").toString());
					cs.put("sessionid", sessionid);
					cs.put("type", "");
					txn.insert("collectionsheet", cs);
				}
				
				
				if (params.containsKey("payments")) {
					list = (ArrayList<Map>) params.get("payments");
					for(int j=0; j<list.size(); j++) {
						map = (Map) list.get(j);
						Map p = new HashMap();
						p.put("objid", map.get("objid").toString());
						p.put("state", map.get("state").toString()); 
						p.put("loanappid", map.get("loanappid").toString());
						p.put("detailid", map.get("detailid").toString());
						p.put("refno", map.get("refno").toString());
						p.put("txndate", map.get("txndate").toString());
						p.put("paymentamount", Double.parseDouble(map.get("amount").toString()));
						p.put("paymenttype", map.get("type").toString());
						p.put("routecode", map.get("routecode").toString());
						p.put("isfirstbill", Integer.parseInt(map.get("isfirstbill").toString()));
						p.put("longitude", map.get("longitude").toString());
						p.put("latitude", map.get("latitude").toString());
						p.put("paidby", map.get("paidby").toString());
						p.put("trackerid", map.get("trackerid").toString());
						p.put("collectorid", map.get("collectorid").toString());
						txn.insert("payment", p);
					}
				}
				
				if (params.containsKey("notes")) {
					list = (ArrayList<Map>) params.get("notes");
					for (int j=0; j<list.size(); j++) {
						map = (Map) list.get(j);
						Map n = new HashMap();
						n.put("objid", map.get("objid").toString());
						n.put("state", map.get("state").toString());
						n.put("loanappid", map.get("loanappid").toString());
						n.put("fromdate", map.get("fromdate").toString());
						n.put("todate", map.get("todate").toString());
						n.put("remarks", map.get("remarks").toString());
						txn.insert("notes", map);
					}
				}
				if (params.containsKey("remarks")) {
					map = new HashMap();
					map.put("loanappid", params.get("loanappid").toString());
					map.put("remarks", params.get("remarks").toString());
					txn.insert("remarks", map);
				}
			}
		}
	}
}
