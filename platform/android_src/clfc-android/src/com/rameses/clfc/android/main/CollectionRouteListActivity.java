package com.rameses.clfc.android.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBRouteService;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.db.android.ExecutionHandler;
import com.rameses.db.android.SQLExecutor;
import com.rameses.db.android.SQLTransaction;

public class CollectionRouteListActivity extends ControlActivity 
{	
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Collection Sheet");

		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_collectionsheet_route, rl_container);
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		
		try { 
			SQLTransaction txn = new SQLTransaction("clfc.db");
			txn.execute(new ExecutionHandlerImpl()); 
		} catch(Throwable e) {
			Platform.getLogger().log(e);
			System.out.println("[CollectionRouteListActivity] error caused by "+e.getClass().getName() + ": " + e.getMessage()); 
		}
	}
	
	private class ExecutionHandlerImpl implements ExecutionHandler 
	{
		public void execute(SQLExecutor txn) throws Exception {
			String billdate = "Collection Date: ";
			TextView tv_billdate = (TextView) findViewById(R.id.tv_billdate);
			ListView lv_route = (ListView) findViewById(R.id.lv_route);
			
			DBRouteService dbRs = new DBRouteService();
			dbRs.setDBContext(txn.getContext());
			List<Map> list = dbRs.getRoutesByCollectorid(SessionContext.getProfile().getUserId());
			List<Map> routes = new ArrayList<Map>();
			System.out.println("billdate -> "+billdate);
			if (!list.isEmpty()) {
				String str = ApplicationUtil.formatDate(Platform.getApplication().getServerDate(), "MMM dd, yyyy");//new java.text.SimpleDateFormat("MMM dd, yyyy").format(Platform.getApplication().getServerDate());
				System.out.println("str -> "+str);
				billdate += str;
				Map params;
				Map itm;
				for (int i=0; i<list.size(); i++) {
					itm = (Map) list.get(i);
					params = new HashMap();
					params.put("code", itm.get("routecode").toString());
					params.put("description", itm.get("routedescription").toString());
					params.put("area", itm.get("routearea").toString());
					params.put("state", itm.get("state").toString());
					routes.add(params);
				}
			}
			System.out.println("tv billdate -> "+tv_billdate);
			System.out.println("billdate2 -> "+billdate);
			tv_billdate.setText(billdate);
			View header = (View) getLayoutInflater().inflate(R.layout.header_route, null);
			lv_route.addHeaderView(header, null, false);
			lv_route.setAdapter(new RouteAdapter(CollectionRouteListActivity.this, routes));
			lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					selectedItem(parent, view, position, id);
				}
			}); 
		} 
	}
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) {

		Map route = (Map) parent.getItemAtPosition(position);
		if (!route.get("state").toString().equals("REMITTED")) {
			Intent intent = new Intent(this, CollectionSheetListActivity.class);
			intent.putExtra("routecode", route.get("code").toString());
			startActivity(intent);
		}
	}
}
