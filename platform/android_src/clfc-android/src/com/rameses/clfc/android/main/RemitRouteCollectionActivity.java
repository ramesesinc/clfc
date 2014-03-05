package com.rameses.clfc.android.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.MainDB;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBRouteService;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.SQLTransaction;

public class RemitRouteCollectionActivity extends ControlActivity 
{
	private ProgressDialog progressDialog;
	private LayoutInflater inflater;
	private SQLTransaction txn;
	private DBRouteService dbRs = new DBRouteService();
	private ListView lv_routes;
	private ArrayList routes = new ArrayList();
	private List<Map> list;
	private Map params;
	private Map itm;
	private int listSize;
	private Map item;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Select a Route to remit");
		
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		inflater.inflate(R.layout.activity_routelist, rl_container, true);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		
		lv_routes = (ListView) findViewById(R.id.lv_route);
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		loadRoutes();
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
	
	public void loadRoutes() {
		getHandler().post(new Runnable() {
			public void run() {
				synchronized (MainDB.LOCK) {
					txn = new SQLTransaction("clfc.db");
					try {
						txn.beginTransaction();
						runImpl(txn);
						txn.commit();
					} catch (Throwable t) {
						t.printStackTrace();
						UIDialog.showMessage(t, RemitRouteCollectionActivity.this);
					} finally {
						txn.endTransaction();
					}
				}
			}
			
			private void runImpl(SQLTransaction txn) throws Exception {
				dbRs.setDBContext(txn.getContext());
				
				list = dbRs.getRoutesByCollectorid(SessionContext.getProfile().getUserId());
				
				routes.clear();
				if (!list.isEmpty()) {
					listSize = list.size();
					for (int i=0; i<listSize; i++) {
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
				lv_routes.setAdapter(new RouteAdapter(RemitRouteCollectionActivity.this, routes));
				
			}
		});
	}
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) {
		item = (Map) parent.getItemAtPosition(position);
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
