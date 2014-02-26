package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rameses.service.ServiceProxy;

public class BackgroundProcessUploading {
	private ProjectApplication application;
	private MySQLiteHelper dbHelper;
	private Context context;
	
	private Runnable pendingVoidPaymentsRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingVoidPayments = dbHelper.getPendingVoidPayments(db);
			
			if (pendingVoidPayments != null && pendingVoidPayments.getCount() > 0) {
				pendingVoidPayments.moveToFirst();
				boolean isApproved = false;
				String objid = "";
				ServiceProxy proxy = null;
				do {
					objid = pendingVoidPayments.getString(pendingVoidPayments.getColumnIndex("objid"));
					params.clear();
					params.put("voidid", objid);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						isApproved = (Boolean) proxy.invoke("isVoidPaymentApproved", new Object[]{params});
					} catch (Exception e) {}
					finally {
						if (isApproved) {
							dbHelper.approveVoidPayment(db, objid);
						}
					}
				} while(pendingVoidPayments.moveToNext());
				pendingVoidPayments.close();
			}
			db.close();
		}
	};
	
	private Runnable pendingPaymentsRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingPayments = dbHelper.getPendingPayments(db);
			
			if (pendingPayments != null && pendingPayments.getCount() > 0) {
				pendingPayments.moveToFirst();
				Map<String, Object> payment = new HashMap<String, Object>();
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				String loanappid = "";
				String routecode = "";
				ServiceProxy proxy = null;
				do {
					params.clear();
					payment.clear();
					collectionsheet.clear();

					loanappid = pendingPayments.getString(pendingPayments.getColumnIndex("loanappid"));
					params.put("txndate", pendingPayments.getString(pendingPayments.getColumnIndex("txndate")));
					params.put("trackerid", pendingPayments.getString(pendingPayments.getColumnIndex("trackerid")));
					params.put("collectorid", pendingPayments.getString(pendingPayments.getColumnIndex("collectorid")));
					String mode = "OFFLINE";
					if (application.getNetworkStatus() == 1) mode = "ONLINE";
					params.put("mode", mode);
					
					routecode = pendingPayments.getString(pendingPayments.getColumnIndex("routecode"));
					payment = new HashMap<String, Object>();
					payment.put("objid", pendingPayments.getString(pendingPayments.getColumnIndex("objid")));
					payment.put("refno", pendingPayments.getString(pendingPayments.getColumnIndex("refno")));
					payment.put("txndate", pendingPayments.getString(pendingPayments.getColumnIndex("txndate")));
					payment.put("type", pendingPayments.getString(pendingPayments.getColumnIndex("paymenttype")));
					payment.put("amount", pendingPayments.getDouble(pendingPayments.getColumnIndex("paymentamount")));
					payment.put("paidby", pendingPayments.getString(pendingPayments.getColumnIndex("paidby")));
					payment.put("loanappid", pendingPayments.getString(pendingPayments.getColumnIndex("loanappid")));
					payment.put("detailid", pendingPayments.getString(pendingPayments.getColumnIndex("detailid")));
					payment.put("routecode", routecode);
					payment.put("isfirstbill", pendingPayments.getInt(pendingPayments.getColumnIndex("isfirstbill")));
					params.put("payment", payment);
					
					params.put("longitude", pendingPayments.getDouble(pendingPayments.getColumnIndex("longitude")));
					params.put("latitude", pendingPayments.getDouble(pendingPayments.getColumnIndex("latitude")));
					params.put("routecode", routecode);
					params.put("sessionid", pendingPayments.getString(pendingPayments.getColumnIndex("sessionid")));

					collectionsheet = new HashMap<String, Object>();
					collectionsheet.put("loanappid", loanappid);
					collectionsheet.put("acctname", pendingPayments.getString(pendingPayments.getColumnIndex("acctname")));
					collectionsheet.put("detailid", pendingPayments.getString(pendingPayments.getColumnIndex("detailid")));
					params.put("collectionsheet", collectionsheet);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("postPayment", new Object[]{params});
						Map<String, Object> result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approvePayment(db, pendingPayments.getString(pendingPayments.getColumnIndex("objid")));
						}
					} catch (Exception e) {}
					
				} while(pendingPayments.moveToNext());
				pendingPayments.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingPaymentsRunnable", ""); 
				LogUtil.log("pendingPaymentsRunnable");
			}
			db.close();
		}
	};
	
	private Runnable pendingRemarksRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingRemarks = dbHelper.getPendingRemarks(db);
			
			if (pendingRemarks != null && pendingRemarks.getCount() > 0) {
				pendingRemarks.moveToFirst();
				String loanappid = "";
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				ServiceProxy proxy = null;
				do {
					params.clear();
					loanappid = pendingRemarks.getString(pendingRemarks.getColumnIndex("loanappid"));
					params.put("txndate", pendingRemarks.getString(pendingRemarks.getColumnIndex("txndate")));
					params.put("remarks", pendingRemarks.getString(pendingRemarks.getColumnIndex("remarks")));
					params.put("collectorid", pendingRemarks.getString(pendingRemarks.getColumnIndex("collectorid")));
					params.put("sessionid", pendingRemarks.getString(pendingRemarks.getColumnIndex("sessionid")));
					params.put("longitude", pendingRemarks.getString(pendingRemarks.getColumnIndex("longitude")));
					params.put("latitude", pendingRemarks.getString(pendingRemarks.getColumnIndex("latitude")));
					String mode = "OFFLINE";
					if (application.getNetworkStatus() == 1) mode = "ONLINE";
					params.put("mode", mode);
					
					collectionsheet = new HashMap<String, Object>();
					collectionsheet.put("loanappid", loanappid);
					collectionsheet.put("detailid", pendingRemarks.getString(pendingRemarks.getColumnIndex("detailid")));
					collectionsheet.put("routecode", pendingRemarks.getString(pendingRemarks.getColumnIndex("routecode")));
					params.put("collectionsheet", collectionsheet);
					
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("updateRemarks", new Object[]{params});
						Map<String, Object> result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.approveRemark(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarks.moveToNext());
				pendingRemarks.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingRemarksRunnable", ""); 
				LogUtil.log("pendingRemarksRunnable");
			}
			db.close();
		}
	};
	
	private Runnable pendingRemarksRemovedRunnable = new Runnable() {
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor pendingRemarksRemoved = dbHelper.getPendingRemarksRemoved(db);
			
			if (pendingRemarksRemoved != null && pendingRemarksRemoved.getCount() > 0) {
				pendingRemarksRemoved.moveToFirst();
				String loanappid = "";
				ServiceProxy proxy = null;
				do {
					params.clear();
					loanappid = pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("loanappid"));
					params.put("detailid", pendingRemarksRemoved.getString(pendingRemarksRemoved.getColumnIndex("detailid")));
									
					proxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
					try {
						Object response = proxy.invoke("removeRemarks", new Object[]{params});
						Map<String, Object>result = (Map<String, Object>) response;
						if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
							dbHelper.removeRemarksRemovedByAppid(db, loanappid);
						}
					} catch (Exception e) {}
				} while(pendingRemarksRemoved.moveToNext());
				pendingRemarksRemoved.close();
			}
			try {
				Thread.sleep(dbHelper.getUploadTimeout(db)*1000);
			} catch (Exception e) { 
				Log.w("pendingRemarksRemovedRunnable", ""); 
				LogUtil.log("pendingRemarksRemovedRunnable");
			}
			db.close();
		}
	};
	
	public BackgroundProcessUploading(ProjectApplication application) {
		this.application = application;
		context = application.getContext();
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void startBackgroundProcess() {
		application.getTaskManager().schedule(pendingVoidPaymentsRunnable, 0, 1000);
		application.getTaskManager().schedule(pendingPaymentsRunnable, 0, 500);
		application.getTaskManager().schedule(pendingRemarksRunnable, 0, 500);
		application.getTaskManager().schedule(pendingRemarksRemovedRunnable, 0, 500);
	}
	
}
