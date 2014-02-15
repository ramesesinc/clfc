package com.rameses.clfc.android.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBRouteService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.SQLTransaction;
import com.rameses.service.ServiceProxy;

public class RemitRouteCollectionActivity extends ControlActivity {
	private ProgressDialog progressDialog;
	private LayoutInflater inflater;
	
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Select a Route to remit");
		
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		inflater.inflate(R.layout.activity_routelist, rl_container, true);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		loadRoutes();
	}
	
	public void loadRoutes() {
		SQLTransaction txn = new SQLTransaction("clfc.db");
		try {
			txn.beginTransaction();
			loadRoutesImpl(txn);
			txn.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			UIDialog.showMessage(t, RemitRouteCollectionActivity.this);
		} finally {
			txn.endTransaction();
		}
	}
	
	private void loadRoutesImpl(SQLTransaction txn) throws Exception {
		DBRouteService dbRs = new DBRouteService();
		dbRs.setDBContext(txn.getContext());
		
		List<Map> list = dbRs.getRoutesByCollectorid(SessionContext.getProfile().getUserId());
		ArrayList routes = new ArrayList();
		
		if (!list.isEmpty()) {
			Map params;
			Map itm;
			for (int i=0; i<list.size(); i++) {
				itm = (Map) list.get(i);
				
				System.out.println("item -> "+itm);
				params = new HashMap();
				params.put("code", itm.get("routecode").toString());
				params.put("description", itm.get("routedescription").toString());
				params.put("area", itm.get("routearea").toString());
				params.put("state", itm.get("state").toString());
				params.put("sessionid", itm.get("sessionid").toString());
				routes.add(params);
			}
		}
		
		ListView lv_routes = (ListView) findViewById(R.id.lv_route);
		lv_routes.setAdapter(new RouteAdapter(this, routes));
		lv_routes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				selectedItem(parent, view, position, id);
//				final RouteParcelable r =  (RouteParcelable) parent.getItemAtPosition(position);
//				if (r.getState().equals("REMITTED")) {
//					/* do nothing */
//				} else {
//					String msg = "Are you sure you want to remit collection for this route?";
//					DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface d, int which) {
//							// TODO Auto-generated method stub
//							progressDialog.setMessage("Remitting collection route "+r.getArea()+" - "+r.getDescription());
//							if (!progressDialog.isShowing()) progressDialog.show();
//							Executors.newSingleThreadExecutor().submit(new RemitRunnable(r));
//						}
//					};
//					ApplicationUtil.showConfirmationDialog(context, msg, positiveListener, null);
//				}
			}
		});
	}
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) {
		Map item = (Map) parent.getItemAtPosition(position);
		final Map route = item;
		if (!item.get("state").equals("REMITTED")) {
			UIDialog dialog = new UIDialog() {
				public void onApprove() {
					remit(route);
				}
			};
			dialog.confirm("You are about to remit collections for this route. Continue?");
		}
	}
	
	private void remit(Map route) {
		try {
			new RemitRouteCollectionController(this, progressDialog, route).execute();
		} catch (Throwable t) {
			UIDialog.showMessage(t, RemitRouteCollectionActivity.this);
		}
	}
//	
}
