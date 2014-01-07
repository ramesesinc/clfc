package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.apache.http.impl.conn.tsccm.RouteSpecificPool;

import com.rameses.service.ServiceProxy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SpecialCollection extends Activity {
	private Context context = this;
	private MySQLiteHelper db = new MySQLiteHelper(context);
	private ServiceProxy postingProxy = null;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specialcollection);;
		setTitle("Special Collection");
		postingProxy = new ServiceHelper(context).createServiceProxy("DevicePostingService");
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loadRequests();
	}
	
	private void loadRequests() {
		if (!db.isOpen) db.openDb();
		Cursor result = db.getSpecialCollections();
		if (db.isOpen) db.closeDb();
		ListView lv_specialcollection = (ListView) findViewById(R.id.lv_specialcollection);

		ArrayList<SpecialCollectionParcelable> list = new ArrayList<SpecialCollectionParcelable>();
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			SpecialCollectionParcelable scp = null;
			do {
				scp = new SpecialCollectionParcelable();
				scp.setObjid(result.getString(result.getColumnIndex("objid")));
				scp.setState(result.getString(result.getColumnIndex("state")));
				scp.setRemarks(result.getString(result.getColumnIndex("remarks")));
				list.add(scp);
			} while(result.moveToNext());
		}
		
		lv_specialcollection.setAdapter(new SpecialCollectionAdapter(context, list));
		lv_specialcollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				SpecialCollectionParcelable scp = (SpecialCollectionParcelable) parent.getItemAtPosition(position);
				progressDialog.setMessage("Downloading collection sheet(s) requested.");
				if (!progressDialog.isShowing()) progressDialog.show();
				Executors.newSingleThreadExecutor().submit(new SpecialCollectionRunnable(scp));
			}
		});
	}
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
	
	private Handler specialCollectionHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			ArrayList collectionsheets = (ArrayList) bundle.getParcelableArrayList("collectionsheets");
			ArrayList routes = (ArrayList) bundle.getParcelableArrayList("routes");
			
			Map<String, Object> map;
			if (!db.isOpen) db.openDb();
			for (int i=0; i<routes.size(); i++) {
				map = (Map<String, Object>) routes.get(i);
				if (db.findRouteByCode(map.get("routecode").toString()).getCount() == 0) {
					db.insertRoute(map);
				} 
			}
			for (int i=0; i<collectionsheets.size(); i++) {
				map = (Map<String, Object>) collectionsheets.get(i);
				map.put("seqno", db.countCollectionSheetsByRoutecode(map.get("routecode").toString())+1);
				db.insertCollectionsheet(map);
			}
			db.insertSession(bundle.getString("sessionid"));
			db.approveSpecialCollection(bundle.getString("requestid"));
			if (db.isOpen) db.closeDb();
			if (progressDialog.isShowing()) progressDialog.dismiss();
			loadRequests();
		}
	};
	private class SpecialCollectionRunnable implements Runnable {
		private SpecialCollectionParcelable scp;
		
		public SpecialCollectionRunnable() {}
		public SpecialCollectionRunnable(SpecialCollectionParcelable scp) {
			this.scp = scp;
		}
		@Override
		public void run() {
			Message msg = responseHandler.obtainMessage();
			Bundle bundle = new Bundle();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objid", scp.getObjid());
			Boolean status = false;
			try {
				Object response = postingProxy.invoke("downloadSpecialCollection", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				bundle.putParcelableArrayList("routes", ((ArrayList<RouteParcelable>) result.get("routes")));
				bundle.putParcelableArrayList("collectionsheets", ((ArrayList<CollectionSheetParcelable>) result.get("list")));
				Map<String, Object> request = (Map<String, Object>) result.get("request");
				bundle.putString("sessionid", request.get("billingid").toString());
				bundle.putString("requestid", request.get("objid").toString());
				msg = specialCollectionHandler.obtainMessage();
				status = true;
			}
			catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			}
			catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			}
			catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
				e.printStackTrace();
			}
			finally {
				msg.setData(bundle);
				if(status == true) specialCollectionHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!db.isOpen) db.openDb();
	}
	
	@Override
	protected void onPause() {
		if (db.isOpen) db.closeDb();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if (db.isOpen) db.closeDb();
		super.onDestroy();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.specialcollection, menu);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch(menuItem.getItemId()) {
			case R.id.specialcollection_new: showSpecialCollectionDialog();
		}
		return true;
	}
	
	private void showSpecialCollectionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Special Collection Request");
		builder.setView(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remarks, null));
		builder.setPositiveButton("Ok", null);
		builder.setNegativeButton("Cancel", null);
		final AlertDialog dialog = builder.create();
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String remarks = ((EditText) dialog.findViewById(R.id.remarks_text)).getText().toString();
				if (remarks == null || remarks.equals("")) {
					ApplicationUtil.showShortMsg(context, "Remarks is required.");
				} else {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("objid", "SCR"+UUID.randomUUID());
					if (!db.isOpen) db.openDb();
					params.put("collectorid", db.getCollectorid());
					if (db.isOpen) db.closeDb();
					params.put("state", "PENDING");
					params.put("remarks", remarks);
					try {
						postingProxy.invoke("postSpecialCollectionRequest", new Object[]{params});
						if (!db.isOpen) db.openDb();
						db.insertSpecialCollection(params);
						if (db.isOpen) db.closeDb();
					} catch (Exception e) {
						ApplicationUtil.showShortMsg(context, "Error requesting for special collection.");
					}
					loadRequests();
					if (dialog.isShowing()) dialog.dismiss();
				}
			}
		});
	}
	
}
