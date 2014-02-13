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
import com.rameses.client.android.UIDialog;

public class RouteListActivity extends ControlActivity 
{
	private ProgressDialog progressDialog;
	private List<Map> routes;
	
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
	}
	
	protected void onStartProcess() {
		super.onStartProcess();
		
		System.out.println("routes -> "+routes);
		ListView lv_route = (ListView) findViewById(R.id.lv_route);
		lv_route.setAdapter(new RouteAdapter(this, routes));
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
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) throws Exception {
		Map route = (Map) parent.getItemAtPosition(position);
		System.out.println("route -> "+route);
		new DownloadBillingController(this, progressDialog, route).execute();
	}
	
}