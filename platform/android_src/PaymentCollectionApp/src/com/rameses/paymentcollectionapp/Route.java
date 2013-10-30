package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Route extends Activity {
	private Context context = this; 
	private MySQLiteHelper db;
	private Bundle bundle;
	private ServiceProxy svcProxy;
	private String ipaddress = "";
	private String port = "";
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		setTitle("Select a Route: ");
		Intent intent = getIntent();
		bundle = intent.getBundleExtra("bundle");
		db = new MySQLiteHelper(context);
	}
	
	private void buildServiceProxy() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("app.context", "clfc");
        map.put("app.host", ipaddress+":"+port);
        map.put("app.cluster","osiris3");
        ScriptServiceContext sp=new ScriptServiceContext(map);
        svcProxy = (ServiceProxy)sp.create("DeviceLoanBillingService");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
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
			list.add(r);
		}
		
		ListView lv_route = (ListView) findViewById(R.id.lv_route);
		lv_route.setAdapter(new RouteAdapter(context, list));
		lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				RouteParcelable r = (RouteParcelable) parent.getItemAtPosition(position);
				getCollectionsheets(r);
			}
		});
		
		if(!db.isOpen) db.openDb();
		Cursor host = db.getHost();
		db.closeDb();
		
		if(host != null && host.getCount() > 0) {
			host.moveToFirst();
			ipaddress = host.getString(host.getColumnIndex("ipaddress"));
			port = host.getString(host.getColumnIndex("port"));
			buildServiceProxy();
		}
	}
	
	@Override
	protected void onPause() {
		if(db.isOpen) db.closeDb();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if(db.isOpen) db.closeDb();
		super.onDestroy();
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
			Map map;
			Map<String, String> params = new HashMap<String, String>();
			params.put("routecode", bundle.getString("routecode"));
			params.put("routedescription", bundle.getString("routedescription"));
			params.put("routearea", bundle.getString("routearea"));
			
			if(!db.isOpen) db.openDb();
			db.insertRoute(params);
			db.removeCollectionsheetsByRoute(params.get("routecode").toString());
			//db.insertSystem(sessionid, serverdate);
			//byte[] barr;
			for(int i=0; i<bundleList.size(); i++) {
				map = (Map) bundleList.get(i);
				//barr = map.toString().getBytes();
				//BigDecimal a = new BigDecimal(barr.length+"").setScale(2);
				//System.out.println("cs "+map.get("acctid").toString()+" size: "+a.divide(new BigDecimal("1024").setScale(0), BigDecimal.ROUND_HALF_UP).setScale(2)+"kb");
				map.put("routecode", params.get("routecode").toString());
				db.insertCollectionsheet(map);
			}
			db.closeDb();
			
			if (progressDialog.isShowing()) progressDialog.dismiss();
			Toast.makeText(context, "Successfully downloaded billing!", Toast.LENGTH_SHORT).show();
			/*Intent intent = new Intent(context, CollectionSheet.class);
			if(progressDialog.isShowing()) progressDialog.dismiss();
			startActivity(intent);
			finish();*/
		}
	};
	
	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			Toast.makeText(context, bundle.getString("response"), Toast.LENGTH_LONG).show();
		}
	};
	
	private class MyRunnable implements Runnable {
		private RouteParcelable route;
		
		public MyRunnable(RouteParcelable route) {
			this.route = route;
		}
		
		@Override
		public void run() {
			Message msg = responseHandler.obtainMessage();
			Bundle xbundle = new Bundle();
			String status = "";
			try {
				msg=handler.obtainMessage();
				Map params=new HashMap();
				params.put("route_code", route.getCode());
				params.put("route_description", route.getDescription());
				params.put("route_area", route.getArea());
				params.put("billingid", bundle.getString("billingid"));
				//ArrayList<CollectionSheetParcelable> list = (ArrayList<CollectionSheetParcelable>)sp1.invoke("getCollectionsheets", new Object[]{params});
				Object result = svcProxy.invoke("downloadBilling", new Object[]{params});
				Map<String, Object> map = (Map<String, Object>) result;
				//xbundle.putString("sessionid", map.get("sessionid").toString());
				//xbundle.putString("serverdate", map.get("serverdate").toString());
				xbundle.putString("routecode", route.getCode());
				xbundle.putString("routedescription", route.getDescription());
				xbundle.putString("routearea", route.getArea());
				xbundle.putParcelableArrayList("collectionsheets", ((ArrayList<CollectionSheetParcelable>)map.get("list")));
				status = "ok";
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
				if(status == "ok") handler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}	
}