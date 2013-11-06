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
		setTitle("Payment Collection");
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
		
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		WifiManager wifiManager = (WifiManager)context.getSystemService(WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		ListView optionLv = (ListView)findViewById(R.id.optionList);
		ArrayList<String> arrlist = new ArrayList<String>();
		arrlist.add("Download Billing");
		arrlist.add("Collection Sheet");
		arrlist.add("Upload Payments");
		
		optionLv.setAdapter(new StringAdapter(context, arrlist));
		optionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String opt = parent.getItemAtPosition(position).toString();
				if(opt.equals("Collection Sheet")) {
					Intent intent = new Intent(context, CollectionSheetRoute.class);
					startActivity(intent);
				} else {
					if(svcProxy == null) {
						Toast.makeText(context, "Please set the host's ip settings", Toast.LENGTH_SHORT).show();
					} else {
						if(!db.isOpen) db.openDb();
						Cursor p = db.getPayments();
						db.closeDb();
						if(opt.equals("Download Billing")) {
							if(p.getCount() > 0) {
								Toast.makeText(context, "There are still payments to upload. Please upload the payments before downloading current billing.", Toast.LENGTH_LONG).show();
							} else {
								showLoginDialog();
							}
						} else if(opt.equals("Upload Payments")) {
							if(p.getCount() == 0) {
								Toast.makeText(context, "No payments to upload.", Toast.LENGTH_SHORT).show();
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
		builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				EditText et_username = (EditText) dialog.findViewById(R.id.login_username);
				EditText et_password = (EditText) dialog.findViewById(R.id.login_password);
				
				String username = et_username.getText().toString();
				String password = et_password.getText().toString();
				if (progressDialog.isShowing()) progressDialog.dismiss();
				progressDialog.setMessage("Getting information from server.");
				progressDialog.show();
				Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));				
			}
		});
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();		
		dialog.show();
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
				System.out.println("username-> "+username+" password-> "+password);
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
	
	/*private void getRoutes() {
		if(progressDialog.isShowing()) progressDialog.dismiss();
		progressDialog.setMessage("Getting routes from server.");
		progressDialog.show();
		Executors.newSingleThreadExecutor().submit(new GetRoutesRunnable());
	}

	private Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(!db.isOpen) db.openDb();
			db.emptySystemTable();
			db.removeAllCollectionsheets();
			db.closeDb();
			Intent intent=new Intent(context, Route.class);
			intent.putExtra("bundle", bundle);
			if(progressDialog.isShowing()) progressDialog.dismiss();
			startActivity(intent);
		}
	};
	
	private class GetRoutesRunnable implements Runnable {
		@Override
		public void run() {
			Message msg  = responseHandler.obtainMessage();
			Bundle bundle = new Bundle();
			String status = "";
			Message responseMsg=responseHandler.obtainMessage();
			Bundle responseData=new Bundle();
			try {
				msg = handler.obtainMessage();
				Map params=new HashMap();
				ArrayList<RouteParcelable> list = (ArrayList<RouteParcelable>) svcProxy.invoke("getRoutes", new Object[]{params});
				bundle.putParcelableArrayList("routes", list);
				msg.setData(bundle);
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
				if(status == "ok") handler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}*/
	
	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			Toast.makeText(context, bundle.getString("response"), Toast.LENGTH_SHORT).show();
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
			ArrayList<String> list= bundle.getStringArrayList("list");
			if (!db.isOpen) db.openDb();
			if (list.size() > 0) {
				String id = "";
				for(int i=0; i<list.size(); i++) {
					id = list.get(i);
					db.insertUploadedPayment(id);
					db.removePaymentByLoanappid(id);
					db.removeCollectionsheetByLoanappid(id);
				}
			}
			Cursor p = db.getPayments();
			if (p == null || p.getCount() == 0) {
				db.removeAllCollectionsheets();
				db.removeAllPayments();
				db.removeAllUploadedPayments();
				db.removeAllRoutes();
				if (progressDialog.isShowing()) progressDialog.dismiss();
				Toast.makeText(context, "Successfully uploaded payments!", Toast.LENGTH_SHORT).show();
			} else { uploadPayments(); }
			db.closeDb();
		}
	};
	
	private void setPayments(Cursor result, ArrayList<Map> list) {
		Map<String, Object> m;
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			do {
				m = new HashMap<String, Object>();
				m.put("loanappid", result.getString(result.getColumnIndex("loanappid")));
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
			catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_LONG).show(); }
			
			String routecode = "";
			if(db.isOpen) db.closeDb();
			
			ArrayList<Map> payments = new ArrayList<Map>();
			if(!db.isOpen) db.openDb();
			Cursor routes = db.getRoutes();
			routes.moveToFirst();
			do {
				routecode = routes.getString(routes.getColumnIndex("routecode"));
				Cursor p = db.getPayments(routecode);
				if (p != null && p.getCount() > 0) {
					setPayments(p, payments);
					break;
				}
			} while(routes.moveToNext());
			db.closeDb();
			
			if (payments.size() > 0) {			
				ArrayList<Map> list = new ArrayList<Map>();
				int counter = (payments.size() > 5)? 5 : payments.size(); 
				Map<String, Object> map;
				for(int i=0; i<counter; i++) {
					map = (Map<String, Object>) payments.get(i);
					list.add(map);
				}
				
				BigDecimal totalamount = new BigDecimal("0").setScale(2);
				for(int i=0; i<payments.size(); i++) {
					map = (Map<String, Object>) payments.get(i);
					totalamount = totalamount.add(new BigDecimal(map.get("payamount").toString()));
				}
				
				Message msg = responseHandler.obtainMessage();
				Bundle bundle = new Bundle();
				String status = "";
				try {
					msg=uploadHandler.obtainMessage();
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("payments", list);
					params.put("sessionid", sessionid);
					params.put("txndate", serverDate);
					params.put("routecode", routecode);
					params.put("totalcount", payments.size());
					params.put("totalamount", totalamount);
					Object response = svcProxy.invoke("uploadPayments", new Object[]{params});
					Map<String, Object> result = (Map<String, Object>) response;
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