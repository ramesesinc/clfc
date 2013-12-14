package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rameses.service.ScriptServiceContext;
import com.rameses.service.ServiceProxy;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {
	private Context context=this;
	private MySQLiteHelper db;
	private boolean isNetworkEnabled;
	private LocationManager locationManager;
	private NetworkInfo networkInfo;
	//private ArrayList<Map> payments=new ArrayList<Map>();
	private ServiceProxy svcProxy;
	private String uploadStatus;
	private String ipaddress = "";
	private String port = "";
	private ProgressDialog progressDialog;
	//private Dialog dialog;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("CLFC Collection");
		//dialog = new Dialog(context, android.R.style.Theme_WallpaperSettings);
	}
	
	private Map buildConfig() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("app.context", "clfc");
        map.put("app.host", ipaddress+":"+port);
        map.put("app.cluster","osiris3");
        return map;
	}
	
	private ServiceProxy buildServiceProxy(String serviceName) {
        ScriptServiceContext sp=new ScriptServiceContext(buildConfig());
        return (ServiceProxy) sp.create(serviceName);
	}
	
		
	@Override
	protected void onStart() {
		super.onStart();
		db=new MySQLiteHelper(context);
		locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		WifiManager wifiManager = (WifiManager)context.getSystemService(WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		ListView optionLv = (ListView)findViewById(R.id.optionList);
		ArrayList<String> arrlist = new ArrayList<String>();
		arrlist.add("Download Collection Sheet(s)");
		arrlist.add("View Collection Sheet(s)");
		arrlist.add("Upload Collection Sheet(s)");
		
		optionLv.setAdapter(new StringAdapter(context, arrlist));
		optionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//String opt = parent.getItemAtPosition(position).toString();
				if(position == 1) {
					Intent intent = new Intent(context, CollectionSheetRoute.class);
					startActivity(intent);
				} else {
					if(svcProxy == null) {
						//Toast.makeText(context, "Please set the host's ip settings", Toast.LENGTH_SHORT).show();
						showShortMsg("Please set the host's ip settings.");
					} else {
						if(!db.isOpen) db.openDb();
						boolean forupload = false;
						Cursor cursor = db.getPayments();
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						cursor = db.getNotes();
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						cursor = db.getRemarks();
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						db.closeDb();
						if(position == 0) {
							if(forupload == true) {
								//Toast.makeText(context, "There are still collection sheets to upload. Please upload the payments before downloading current billing.", Toast.LENGTH_LONG).show();
								showLongMsg("There are still collection sheets to upload. Please upload the collection sheets before downloading current collection sheets.");
							} else {
								showLoginDialog();
							}
						} else if(position == 2) {
							if(forupload == false) {
								//Toast.makeText(context, "No payments to upload.", Toast.LENGTH_SHORT).show();
								showShortMsg("No collection sheets to upload.");
							} else {
								uploadPayments();	
							}
						}
					}
				}
			}
		});
		
		if(!db.isOpen) db.openDb();
		Cursor host = db.getHost();
		db.closeDb();
		
		if(host != null && host.getCount() > 0) {
			host.moveToFirst();
			ipaddress = host.getString(host.getColumnIndex("ipaddress"));
			port = host.getString(host.getColumnIndex("port"));
			svcProxy = buildServiceProxy("DeviceLoanBillingService");
		}
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		if(!db.isOpen) db.openDb();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.ip_settings:
					Intent intent=new Intent(context, IPSetting.class);
					startActivity(intent);
					break;
		}
		return true;
	}
	
	private void showLoginDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Login");
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_login, null);
		builder.setView(view);
		builder.setPositiveButton("Login", null);
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();		
		dialog.show();
		Button btn_positive = (Button) dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String username = ((EditText) dialog.findViewById(R.id.login_username)).getText().toString();
				String password = ((EditText) dialog.findViewById(R.id.login_password)).getText().toString();
				
				if (username.trim().equals("")) {
					showShortMsg("Username is required.");
				} else if (password.trim().equals("")) {
					showShortMsg("Password is required.");
				} else {
					if (progressDialog.isShowing()) progressDialog.dismiss();
					progressDialog.setMessage("Getting information from server.");
					progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));
				}
			}
		});
	}
	
	public void showShortMsg(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void showLongMsg(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (!db.isOpen) db.openDb();
			db.emptySystemTable();
			db.insertSystem(bundle.getString("billingid"), bundle.getString("serverdate"));
			if (db.isOpen) db.closeDb();
			Intent intent=new Intent(context, Route.class);
			intent.putExtra("bundle", bundle);
			if (progressDialog.isShowing()) progressDialog.dismiss();
			if (dialog.isShowing()) dialog.dismiss();
			startActivity(intent);
		}
	};
	
	private class LoginRunnable implements Runnable
	{
		String username;
		String password;
		
		LoginRunnable(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		@Override
		public void run() {
			Message msg = responseHandler.obtainMessage();
			Bundle bundle = new Bundle();
			String status = "";
			try {
				ServiceProxy proxy = buildServiceProxy("DeviceLoginService");
				msg=loginHandler.obtainMessage();
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("username", username);
				params.put("password", password);
				//System.out.println("username-> "+username+" password-> "+password);
				Object response = proxy.invoke("login", new Object[]{params});
				Map<String, Object> result = (Map<String, Object>) response;
				
				/*byte[] barr;
				
				ArrayList list = (ArrayList) result.get("routes");
				for(int i=0; i < list.size(); i++) {
					barr = list.get(i).toString().getBytes(); 
					System.out.println(barr.length);
				}*/
				
				bundle.putParcelableArrayList("routes", ((ArrayList<RouteParcelable>) result.get("routes")));
				bundle.putString("billingid", result.get("billingid").toString());
				bundle.putString("serverdate", result.get("serverdate").toString());
				status = "ok";
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
				if(status == "ok") loginHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}

	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			showShortMsg(bundle.getString("response"));
		}
	};
	
	public void uploadPayments() {
		if(progressDialog.isShowing()) progressDialog.dismiss();
		progressDialog.setMessage("Uploading payments.");
		progressDialog.show();
		Executors.newSingleThreadExecutor().submit(new UploadPaymentsRunnable());		
	}
	
	private Handler uploadHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			String loanappid = bundle.getString("loanappid");
			ArrayList<String> list= bundle.getStringArrayList("list");
			if (!db.isOpen) db.openDb();
			if (list.size() > 0) {
				String id = "";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loanappid", loanappid);
				for(int i=0; i<list.size(); i++) {
					id = list.get(i);
					map.put("referenceid", id);
					db.insertUploads(map);
					db.removePaymentById(id);
					//db.insertUploadedPayment(id);
					//db.removePaymentByLoanappid(id);
					//db.removeCollectionsheetByLoanappid(id);
				}
			}
			Cursor p = db.getPayments();
			if (p == null || p.getCount() == 0) {
				db.removeAllCollectionsheets();
				db.removeAllPayments();
				db.removeAllUploads();
				db.removeAllRoutes();
				if (progressDialog.isShowing()) progressDialog.dismiss();
				//Toast.makeText(context, "Successfully uploaded payments!", Toast.LENGTH_SHORT).show();
				showShortMsg("Successfully uploaded payments!");
			} else { uploadPayments(); }
			db.closeDb();
		}
	};
	
	private void setNotes(Cursor cursor, ArrayList<Map<String, Object>> list) {
		Map<String, Object> m;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				m = new HashMap<String, Object>();
				m.put("objid", cursor.getString(cursor.getColumnIndex("objid")));
				m.put("loanappid", cursor.getString(cursor.getColumnIndex("loanappid")));
				m.put("fromdate", cursor.getString(cursor.getColumnIndex("fromdate")));
				m.put("todate", cursor.getString(cursor.getColumnIndex("todate")));
				m.put("remarks", cursor.getString(cursor.getColumnIndex("remarks")));
				list.add(m);
			} while(cursor.moveToNext());
		}
	}
	
	private void setPayments(Cursor result, ArrayList<Map<String, Object>> list) {
		Map<String, Object> m;
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			do {
				m = new HashMap<String, Object>();
				m.put("loanappid", result.getString(result.getColumnIndex("loanappid")));
				m.put("detailid", result.getString(result.getColumnIndex("detailid")));
				m.put("refno", result.getString(result.getColumnIndex("refno")));
				m.put("txndate", result.getString(result.getColumnIndex("txndate")));
				m.put("paytype", result.getString(result.getColumnIndex("paymenttype")));
				m.put("payamount", result.getDouble(result.getColumnIndex("paymentamount")));
				m.put("isfirstbill", result.getInt(result.getColumnIndex("isfirstbill")));
				list.add(m);
			} while(result.moveToNext());
		}
	}
	
	private class UploadPaymentsRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!db.isOpen) db.openDb();
			String sessionid = db.getSessionid();
			Date serverDate = null;
			try {
				serverDate = db.getServerDate();
			}
			catch (Exception e) { showShortMsg("Error: ParseException"); }
			
			String routecode = "";
			if(db.isOpen) db.closeDb();
			
			boolean forupload = false;
			Map<String, Object> collectionsheet = new HashMap<String, Object>();
			ArrayList<Map<String, Object>> payments = new ArrayList<Map<String, Object>>();
			ArrayList<Map<String, Object>> notes = new ArrayList<Map<String, Object>>();
			if(!db.isOpen) db.openDb();
			Cursor routes = db.getRoutes();
			routes.moveToFirst();
			do {
				payments.clear();
				notes.clear();
				routecode = routes.getString(routes.getColumnIndex("routecode"));
				Cursor cs = db.getCollectionsheetsByRoute(routecode);
				if (cs != null && cs.getCount() > 0) {
					String loanappid = cs.getString(cs.getColumnIndex("loanappid"));
					cs.moveToFirst();
					collectionsheet.put("loanappid", loanappid);
					Cursor p = db.getPaymentsByAppid(loanappid);
					if (p != null && p.getCount() > 0) {
						setPayments(p, payments);
						forupload = true;
					}
					collectionsheet.put("payments", payments);
					Cursor n = db.getNotesByAppid(loanappid);
					if (n != null && n.getCount() > 0) {
						setNotes(n, notes);
						forupload = true;
					}
					String remarks = "";
					Cursor r = db.getRemarksByAppid(loanappid);
					if (r != null && r.getCount() > 0) {
						r.moveToFirst();
						remarks = r.getString(r.getColumnIndex("remarks"));
						forupload = true;
					}
					collectionsheet.put("remarks", remarks);
					if (forupload == false) db.removeCollectionsheetByLoanappid(loanappid);
					break;
				}
			} while(routes.moveToNext());
			db.closeDb();
			
			if (forupload) {			
				/*ArrayList<Map> list = new ArrayList<Map>();
				int counter = (payments.size() > 5)? 5 : payments.size(); 
				Map<String, Object> map;
				for(int i=0; i<counter; i++) {
					map = (Map<String, Object>) payments.get(i);
					list.add(map);
				}*/
				
				Map<String, Object> map = new HashMap<String, Object>();
				BigDecimal totalamount = new BigDecimal("0").setScale(2);
				for(int i=0; i<payments.size(); i++) {
					map = (Map<String, Object>) payments.get(i);
					//System.out.println(map);
					totalamount = totalamount.add(new BigDecimal(map.get("payamount").toString()));
				}
				
				Message msg = responseHandler.obtainMessage();
				Bundle bundle = new Bundle();
				String status = "";
				try {
					msg=uploadHandler.obtainMessage();
					Map<String, Object> params=new HashMap<String, Object>();
					//params.put("payments", list);
					params.put("collectionsheet", collectionsheet);
					params.put("sessionid", sessionid);
					params.put("txndate", serverDate);
					params.put("routecode", routecode);
					params.put("totalcount", payments.size());
					params.put("totalamount", totalamount);
					Object response = svcProxy.invoke("uploadCollectionSheets", new Object[]{params});
					Map<String, Object> result = (Map<String, Object>) response;
					bundle.putString("loanappid", collectionsheet.get("loanappid").toString());
					bundle.putStringArrayList("list", ((ArrayList<String>) result.get("list")));
					status = "ok";
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
					if(status == "ok") uploadHandler.sendMessage(msg);
					else responseHandler.sendMessage(msg);
				}
			}
		}
	}
}