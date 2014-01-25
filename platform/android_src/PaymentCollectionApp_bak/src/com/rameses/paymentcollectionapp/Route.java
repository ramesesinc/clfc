package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Route extends ControlActivity {
	private Context context = this;
	private Bundle bundle;
	//private ServiceProxy svcProxy;
	private ProgressDialog progressDialog;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_route, rl_container, true);
		setTitle("Select a Route: ");
		Intent intent = getIntent();
		int status = intent.getIntExtra("networkStatus", 2);
		String mode = "NOT CONNECTED";
		if (status == 1) mode = "ONLINE";
		else if (status == 0) mode = "OFFLINE";
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
		bundle = intent.getBundleExtra("bundle");
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		progressDialog = new ProgressDialog(context);
		
		ArrayList bundleList = bundle.getParcelableArrayList("routes");
		ArrayList<RouteParcelable> list=new ArrayList<RouteParcelable>();
		Map<String, Object> map;
		RouteParcelable r;
		for(int i=0; i<bundleList.size(); i++) {
			map = (Map<String, Object>) bundleList.get(i);
			r = new RouteParcelable();
			r.setCode(map.get("code").toString());
			r.setDescription(map.get("description").toString());
			r.setArea(map.get("area").toString());
			r.setSessionid(map.get("billingid").toString());
			list.add(r);
		}
		
		ListView lv_route = (ListView) findViewById(R.id.lv_route);
		lv_route.setAdapter(new RouteAdapter(context, list));
		lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				RouteParcelable r = (RouteParcelable) parent.getItemAtPosition(position);
				SQLiteDatabase db = getDbHelper().getReadableDatabase();
				getCollectionsheets(r);
			}
		});
		
		//if (svcHelper.isHostSet()) svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");//svcHelper.createServiceProxy("DeviceLoanBillingService");
	}
	
	private void getCollectionsheets(RouteParcelable r) {
		if(progressDialog.isShowing()) progressDialog.dismiss();
		progressDialog.setMessage("Downloading billing for route "+r.getDescription()+" - "+r.getArea());
		progressDialog.show();
		Executors.newSingleThreadExecutor().submit(new MyRunnable(r));
	}

	private Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			//String sessionid = bundle.getString("sessionid");
			//String serverdate = bundle.getString("serverdate");
			ArrayList bundleList = (ArrayList) bundle.getParcelableArrayList("collectionsheets");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("routecode", bundle.getString("routecode"));
			params.put("state", "ACTIVE");
			params.put("routedescription", bundle.getString("routedescription"));
			params.put("routearea", bundle.getString("routearea"));
			params.put("sessionid", bundle.getString("sessionid"));
			
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			Date date = null;
			try {
				String dt = getDbHelper().getServerDate(db, getDbHelper().getCollectorid(db));
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dt);
			} catch (Exception e) {}
			if (date == null) {
				map.clear();
				map.put("serverdate", bundle.getString("serverdate"));
				map.put("collectorid", getDbHelper().getCollectorid(db));
				getDbHelper().insertCollectionDate(db, map);
				if (getApp().getIsTickerRunning()) {
					getApp().stopTicker();
				}
			}
			
			if (!getApp().getIsTickerRunning()) {
				getApp().startTicker();
			} 
			//Cursor r = getDbHelper().getSystemByName("trackerid");
			String trackerid = getDbHelper().getTrackerid(db);
			if (trackerid == null || trackerid.equals("")) {
				map.clear();
				map.put("name", "trackerid");
				map.put("value", bundle.getString("trackerid"));
				getDbHelper().insertSystem(db, map);
			}
			//r = getDbHelper().findSessionById(bundle.getString("sessionid"));
			//if (r == null || r.getCount() == 0) getDbHelper().insertSession(bundle.getString("sessionid"));
			params.put("collectorid", getDbHelper().getCollectorid(db));
			getDbHelper().insertRoute(db, params);
			/*Cursor cs = db.getCollectionsheetsByRoute(params.get("routecode").toString);
			if (cs != null && cs.getCount() > 0) {
				cs.moveToFirst();
				String loanappid  = cs.getString(cs.getColumnIndex("loanappid"));
				do {
					db.removePaymentByAppid(loanappid);
					db.removeNotesByAppid(loanappid);
					db.removeRemarksByAppid(loanappid);
					db.removeCollectionsheetByLoanappid(loanappid);
				} while(cs.moveToNext());
			}*/
			getDbHelper().removeCollectionsheetsByRoute(db, params.get("routecode").toString());
			//db.insertSystem(sessionid, serverdate);
			//byte[] barr;
			Cursor itm = null;
			ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> m;
			for(int i=0; i<bundleList.size(); i++) {
				map = (Map<String, Object>) bundleList.get(i);
				//barr = map.toString().getBytes();
				//BigDecimal a = new BigDecimal(barr.length+"").setScale(2);
				//System.out.println("cs "+map.get("acctid").toString()+" size: "+a.divide(new BigDecimal("1024").setScale(0), BigDecimal.ROUND_HALF_UP).setScale(2)+"kb");
				map.put("routecode", params.get("routecode").toString());
				map.put("sessionid", bundle.getString("sessionid"));
				map.put("type", "");
				itm = getDbHelper().getCollectionsheetByLoanappid(db, map.get("loanappid").toString());
				if (itm == null || itm.getCount() == 0) getDbHelper().insertCollectionsheet(db, map);
				if (map.containsKey("payments")) {
					list = (ArrayList<Map<String, Object>>) map.get("payments");
					for(int j=0; j<list.size(); j++) {
						m = (Map<String, Object>) list.get(j);
						getDbHelper().insertPayment(db, m);
					}
				}
				if (map.containsKey("notes")) {
					list = (ArrayList<Map<String, Object>>) map.get("notes");
					for (int j=0; j<list.size(); j++) {
						m = (Map<String, Object>) list.get(j);
						getDbHelper().insertNotes(db, m);
					}
				}
				if (map.containsKey("remarks")) {
					m = new HashMap<String, Object>();
					m.put("loanappid", map.get("loanappid").toString());
					m.put("remarks", map.get("remarks").toString());
					getDbHelper().insertRemarks(db, m);
				}
			}
			db.close();
			itm.close();
			
			if (progressDialog.isShowing()) progressDialog.dismiss();
			if (!getApp().getIsBackgroundProcessRunning()) {
				getApp().startBackgroundProcess();
			}
			ApplicationUtil.showShortMsg(context, "Successfully downloaded billing!");
		}
	};
	
	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showLongMsg(context, bundle.getString("response"));
		}
	};
	
	private void insertToSystem(String name, String value) {
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("value", value);
		getDbHelper().insertSystem(db, params);
		db.close();
	}
	
	private class MyRunnable implements Runnable {
		private RouteParcelable route;
		
		public MyRunnable(RouteParcelable route) {
			this.route = route;
		}
		
		@Override
		public void run() {
			Message msg = responseHandler.obtainMessage();
			Bundle xbundle = new Bundle();
			boolean status = false;
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("route_code", route.getCode());
			params.put("route_description", route.getDescription());
			params.put("route_area", route.getArea());
			params.put("billingid", route.getSessionid());
			params.put("longitude", getApp().getLongitude());
			params.put("latitude", getApp().getLatitude());
			String terminalid = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if (terminalid == null) {
				terminalid = Build.ID;
			}
			params.put("terminalid", terminalid);
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			params.put("userid", getDbHelper().getCollectorid(db));
			params.put("trackerid", getDbHelper().getTrackerid(db));
			db.close();
			ServiceProxy svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");
			try {
				//ArrayList<CollectionSheetParcelable> list = (ArrayList<CollectionSheetParcelable>)sp1.invoke("getCollectionsheets", new Object[]{params});
				Object result = svcProxy.invoke("downloadBilling", new Object[]{params});
				Map<String, Object> map = (Map<String, Object>) result;
				//xbundle.putString("sessionid", map.get("sessionid").toString());
				//xbundle.putString("serverdate", map.get("serverdate").toString());
				if (map.containsKey("settings")) {
					Map<String, Object> settings = (Map<String, Object>) map.get("settings");
					insertToSystem("host_online", settings.get("onlinehost").toString());
					insertToSystem("host_offline", settings.get("offlinehost").toString());
					insertToSystem("host_port", settings.get("port").toString());
					insertToSystem("timeout_session", settings.get("sessiontimeout").toString());
					insertToSystem("timeout_upload", settings.get("uploadtimeout").toString());
					insertToSystem("timeout_tracker", settings.get("trackertimeout").toString());
				}
				
				xbundle.putString("routecode", route.getCode());
				xbundle.putString("routedescription", route.getDescription());
				xbundle.putString("routearea", route.getArea());
				xbundle.putString("sessionid", route.getSessionid());
				xbundle.putParcelableArrayList("collectionsheets", ((ArrayList<CollectionSheetParcelable>)map.get("list")));
				xbundle.putString("trackerid", map.get("trackerid").toString());
				xbundle.putString("serverdate", map.get("serverdate").toString());
				status = true;
				msg=handler.obtainMessage();
			}
			catch( TimeoutException te ) {
				xbundle.putString("response", "Connection Timeout!");
			}
			catch( IOException ioe ) {
				xbundle.putString("response", "Error connecting to Server.");
			}
			catch( Exception e ) { 
				xbundle.putString("response", e.getMessage());
				e.printStackTrace();
			}
			finally {
				msg.setData(xbundle);
				if(status == true) handler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}	
}