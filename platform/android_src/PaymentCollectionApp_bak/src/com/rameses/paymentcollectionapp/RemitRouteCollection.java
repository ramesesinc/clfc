package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.service.ServiceProxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RemitRouteCollection extends ControlActivity {
	private Context context = this;
	private ServiceProxy postingProxy;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_route, rl_container, true);
		setTitle("Select a Route to close");
		int status = getIntent().getIntExtra("networkStatus", 2);
		String mode = "NOT CONNECTED";
		if (status == 1) mode = "ONLINE";
		else if (status == 0) mode = "OFFLINE";
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		loadRoutes();
	}

	
	private void loadRoutes() {
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Cursor routes = getDbHelper().getRoutes(db);
		db.close();
		
		ArrayList<RouteParcelable> list = new ArrayList<RouteParcelable>();
		RouteParcelable r;
		if (routes != null && routes.getCount() > 0) {
			routes.moveToFirst();
			do {
				r = new RouteParcelable();
				r.setCode(routes.getString(routes.getColumnIndex("routecode")));
				r.setDescription(routes.getString(routes.getColumnIndex("routedescription")));
				r.setArea(routes.getString(routes.getColumnIndex("routearea")));
				r.setSessionid(routes.getString(routes.getColumnIndex("sessionid")));
				r.setState(routes.getString(routes.getColumnIndex("state")));
				list.add(r);
			} while(routes.moveToNext());
			routes.close();
		}
		
		ListView lv_routes = (ListView) findViewById(R.id.lv_route);
		lv_routes.setAdapter(new RouteAdapter(context, list));
		lv_routes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				final RouteParcelable r =  (RouteParcelable) parent.getItemAtPosition(position);
				if (r.getState().equals("REMITTED")) {
					/* do nothing */
				} else {
					String msg = "Are you sure you want to remit collection for this route?";
					DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface d, int which) {
							// TODO Auto-generated method stub
							progressDialog.setMessage("Remitting collection route "+r.getArea()+" - "+r.getDescription());
							if (!progressDialog.isShowing()) progressDialog.show();
							Executors.newSingleThreadExecutor().submit(new RemitRunnable(r));
						}
					};
					ApplicationUtil.showConfirmationDialog(context, msg, positiveListener, null);
				}
			}
		});
	}

	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
	
	private Handler remitHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String description = bundle.getString("routedescription");
			String area = bundle.getString("routearea");
			loadRoutes();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, "Successfully remitted collection for route "+description+" - "+area);
		}
	};

	private class RemitRunnable implements Runnable {
		private RouteParcelable route;
		
		RemitRunnable(RouteParcelable route) {
			this.route = route;
		}
		
		@Override
		public void run() {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("routecode", route.getCode());
			params.put("sessionid", route.getSessionid());
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			params.put("totalcollection", getDbHelper().getTotalCollectionSheetsByRoutecode(db, route.getCode()));
			params.put("totalamount", getDbHelper().getTotalCollectionsByRoutecode(db, route.getCode()));
			db.close();
			boolean status = false;
			Message msg = responseHandler.obtainMessage();
			Bundle bundle = new Bundle();
			ServiceProxy postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");
			try {
				Object response  = postingProxy.invoke("remitRouteCollection", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				if (result.containsKey("response") && result.get("response").toString().toLowerCase().equals("success")) {
					db = getDbHelper().getReadableDatabase();
					getDbHelper().remitRouteByCode(db, route.getCode());
					db.close();
				}
				bundle.putString("routedescription", route.getDescription());
				bundle.putString("routearea", route.getArea());
				msg = remitHandler.obtainMessage();
				//bundle.putString("response", "Successfully remitted collection for route "+route.getDescription()+" - "+route.getArea());
				status = true;
			} catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			} catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			} catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
				e.printStackTrace(); 
			} finally {
				msg.setData(bundle);
				if (status == true) remitHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
}
