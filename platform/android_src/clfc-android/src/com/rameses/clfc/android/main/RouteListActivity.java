package com.rameses.clfc.android.main;

import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.DBContext;
import com.rameses.db.android.SQLTransaction;

public class RouteListActivity extends ControlActivity 
{
	private ProgressDialog progressDialog;
	private List<Map> routes;
	private ListView lv_route;
	private Map route;
	
	protected void onCreateProcess(Bundle savedInstanceState) {
		super.onCreateProcess(savedInstanceState);
		setContentView(R.layout.template_footer);
		setTitle("Select a Route: ");
		
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_routelist, rl_container, true);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		routes = (List<Map>) bundle.getSerializable("routes");
		progressDialog = new ProgressDialog(this);
		
		lv_route = (ListView) findViewById(R.id.lv_route);
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		loadRoutes();
		lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//RouteParcelable r = (RouteParcelable) parent.getItemAtPosition(position);
				//getCollectionsheets(r);
				//Map route = (Map) parent.getItemAtPosition(position);
				//System.out.println("route -> "+route);
				//new DownloadBillingController(this, progressDialog, route).execute();
				try { 
					selectedItem(parent, view, position, id); 
				} catch (Throwable t) {
					UIDialog.showMessage(t, RouteListActivity.this); 
				}
			}
		});
	}
	
	public void loadRoutes() {
		getHandler().post(new Runnable() {
			public void run() {
				try {
					lv_route.setAdapter(new RouteAdapter(RouteListActivity.this, routes));
				} catch (Throwable t) {
					UIDialog.showMessage(t, RouteListActivity.this);
				}
			}
		});
	}
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) throws Exception {
		route = (Map) parent.getItemAtPosition(position);
//		System.out.println("route -> "+route);
		if (!"1".equals(route.get("downloaded").toString())) {
			boolean flag = hasUnremittedCollections(route); 
			if (flag == true) {
				UIDialog dialog = new UIDialog() {

					public void onApprove() {
						SQLTransaction clfcdb = new SQLTransaction("clfc.db");
						SQLTransaction paymentdb = new SQLTransaction("clfcpayment.db");
						SQLTransaction remarksdb = new SQLTransaction("clfcremarks.db");
						try {
							clfcdb.beginTransaction();
							paymentdb.beginTransaction();
							remarksdb.beginTransaction();
							
							String whereClause = "routecode=?";
							Object[] params = new Object[]{route.get("code").toString()};
							clfcdb.delete("route", whereClause, params);
							clfcdb.delete("collectionsheet", whereClause, params);
							paymentdb.delete("payment", whereClause, params);
							remarksdb.delete("remarks", whereClause, params);
							
							clfcdb.commit();
							paymentdb.commit();
							remarksdb.commit();
							downloadBilling();
						} catch (Throwable t) {
							UIDialog.showMessage(t, RouteListActivity.this);
						} finally {
							clfcdb.endTransaction();
							paymentdb.endTransaction();
							remarksdb.endTransaction();
						}
					}
				};
				dialog.confirm("There are still unremitted collections for this route. Downloading new billing will remove all current collections. Do you still want to continue downloading new billing?");
			} else if (flag == false) {
				downloadBilling();
			}
		}
	}
	
	private void downloadBilling() throws Exception {
		new DownloadBillingController(this, progressDialog, route).execute();
	}
	
	private boolean hasUnremittedCollections(Map route) {
		boolean flag = false;
		String routecode = route.get("code").toString();
		DBContext ctx = new DBContext("clfcpayment.db");
		DBPaymentService paymentSvc = new DBPaymentService();
		paymentSvc.setDBContext(ctx);
		
		try {
			flag = paymentSvc.hasPaymentsByRoutecode(routecode);
		} catch (Throwable t) {
			UIDialog.showMessage(t, RouteListActivity.this);
		}
		if (flag == true) return true;
		
		ctx = new DBContext("clfcremarks.db");
		DBRemarksService remarksSvc = new DBRemarksService();
		remarksSvc.setDBContext(ctx);
		
		try {
			flag = remarksSvc.hasRemarksByRoutecode(routecode);
		} catch (Throwable t) {
			UIDialog.showMessage(t, RouteListActivity.this);
		}
		if (flag == true) return true;
		return false;
	}
	
}