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

import android.location.Location;
import android.location.LocationListener;
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
import android.telephony.TelephonyManager;
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
	private MySQLiteHelper db = new MySQLiteHelper(context);
	private boolean isNetworkEnabled;
	private LocationManager locationManager;
	private NetworkInfo networkInfo;
	private ServiceProxy svcProxy;
	private ServiceProxy postingProxy;
	private ProgressDialog progressDialog;
	private AlertDialog dialog;
	private ServiceHelper svcHelper = new ServiceHelper(context);
	private ProjectApplication application = null;
	private LocationListener locationListener = new DeviceLocationListener();
	private Handler locationHandler = new Handler();
	private Runnable locationRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Location location = null;
			String provider = "";
			if (isNetworkEnabled) {
				provider = LocationManager.NETWORK_PROVIDER;
				locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
				location = locationManager.getLastKnownLocation(provider);
				if (application != null && location != null) {
					application.setLongitude(location.getLongitude());
					application.setLatitude(location.getLatitude());
				}
			}
			locationManager.removeUpdates(locationListener);
			Executors.newSingleThreadExecutor().submit(new PublishLocationRunnable());
		}
	};
	private Handler voidHandler = new Handler();
	private Runnable voidRunnable = new Runnable() {
		@Override
		public void run() {
			if (!db.isOpen) db.openDb();
			Cursor voidPayments = db.getPendingVoidPayments();
			if (db.isOpen) db.closeDb();
			Executors.newSingleThreadExecutor().submit(new VoidPaymentStatusRunnable(voidPayments));			
		}
	};
	
	private class PublishLocationRunnable implements Runnable {
		private String msg = "";
		private boolean repeat = true;
		
		public PublishLocationRunnable() {}
		public PublishLocationRunnable(String msg, boolean repeat) {
			this.msg = msg;
			this.repeat = repeat;
		}
		
		@Override
		public void run() {
			ServiceProxy proxy = svcHelper.createServiceProxy("DeviceLocationService");
			//params.put("payments", list);
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				if (!db.isOpen) db.openDb();
				params.put("sessionid", db.getSessionid());
				if (db.isOpen) db.closeDb();
				String terminalkey = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
				if (terminalkey == null) {
					terminalkey = Build.ID;
				}
				params.put("terminalkey", terminalkey);
				params.put("longitude", application.getLongitude());
				params.put("latitude", application.getLatitude());
				params.put("remarks", msg);
				proxy.invoke("postLocation", new Object[]{params});
			} catch(Exception e) {}
			finally { 
				if (repeat) locationHandler.postDelayed(locationRunnable, 3000); 
			}
		}
	}
	
	private class VoidPaymentStatusRunnable implements Runnable {
		private Cursor voidPayments;
		public VoidPaymentStatusRunnable(Cursor voidPayments) {
			this.voidPayments = voidPayments;
		}
		@Override
		public void run() {
			voidPayments.moveToFirst();
			Map<String, Object> params = new HashMap<String, Object>();
			Boolean isapproved = false;
			String objid = "";
			do {
				objid = voidPayments.getString(voidPayments.getColumnIndex("objid"));
				params.put("voidid", objid);
				try {
					isapproved = (Boolean) postingProxy.invoke("isVoidPaymentApproved", new Object[]{params});
				} catch (Exception e) {}
				finally {
					if (isapproved) {
						if (!db.isOpen) db.openDb();
						db.approveVoidPayment(objid);
						if (db.isOpen) db.closeDb();
					}
				}
				
			} while(voidPayments.moveToNext());
			voidHandler.postDelayed(voidRunnable, 3000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("CLFC Collection");
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		postingProxy = svcHelper.createServiceProxy("DevicePostingService");
		application = (ProjectApplication) context.getApplicationContext();
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationHandler.postDelayed(locationRunnable, 0);
		voidHandler.postDelayed(voidRunnable, 0);
	}	
		
	@Override
	protected void onStart() {
		super.onStart();
		
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
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
						ApplicationUtil.showShortMsg(context, "Please set the host's ip settings.");
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
								ApplicationUtil.showLongMsg(context, "There are still collection sheets to upload. Please upload the collection sheets before downloading current collection sheets.");
							} else {
								showLoginDialog();
							}
						} else if(position == 2) {
							if(forupload == false) {
								//Toast.makeText(context, "No payments to upload.", Toast.LENGTH_SHORT).show();
								ApplicationUtil.showShortMsg(context, "No collection sheets to upload.");
							} else {
								DialogInterface.OnClickListener positivelistener = new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface d, int which) {
										// TODO Auto-generated method stub
										uploadPayments();	
									}
								};
								ApplicationUtil.showConfirmationDialog(context, "You are about to upload the collection sheets. Continue?", positivelistener, null);
							}
						}
					}
				}
			}
		});
		
		if(svcHelper.isHostSet()) svcProxy = svcHelper.createServiceProxy("DeviceLoanBillingService");
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
		locationHandler.removeCallbacks(locationRunnable);
		locationHandler = null;
		voidHandler.removeCallbacks(voidRunnable);
		voidHandler = null;
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
					ApplicationUtil.showShortMsg(context, "Username is required.");
				} else if (password.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "Password is required.");
				} else {
					if (progressDialog.isShowing()) progressDialog.dismiss();
					progressDialog.setMessage("Getting information from server.");
					progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));
				}
			}
		});
	}
	
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (!db.isOpen) db.openDb();
			db.emptySystemTable();
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("sessionid", bundle.getString("billingid"));
			params.put("serverdate", bundle.getString("serverdate"));
			params.put("collectorid", bundle.getString("collectorid"));
			db.insertSystem(params);
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
				ServiceProxy proxy = svcHelper.createServiceProxy("DeviceLoginService");
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
				//bundle.putString("billingid", result.get("billingid").toString());
				bundle.putString("serverdate", result.get("serverdate").toString());
				bundle.putString("collectorid", result.get("collectorid").toString());
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
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
	
	public void uploadPayments() {
		if(progressDialog.isShowing()) progressDialog.dismiss();
		progressDialog.setMessage("Uploading collection sheets.");
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
					db.removeNoteById(id);
					//db.insertUploadedPayment(id);
					//db.removePaymentByLoanappid(id);
					//db.removeCollectionsheetByLoanappid(id);
				}
			}
			db.removeRemarksByAppid(loanappid);
			db.removeCollectionsheetByLoanappid(loanappid);
			boolean finishUploading = true;
			Cursor result = db.getPayments();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			result = db.getNotes();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			result = db.getRemarks();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			if (finishUploading == true) {
				db.removeAllCollectionsheets();
				db.removeAllPayments();
				db.removeAllNotes();
				db.removeAllRemarks();
				db.removeAllUploads();
				db.removeAllRoutes();
				db.removeAllSessions();
				if (progressDialog.isShowing()) progressDialog.dismiss();
				//Toast.makeText(context, "Successfully uploaded payments!", Toast.LENGTH_SHORT).show();
				ApplicationUtil.showShortMsg(context, "Successfully uploaded collection sheets!");
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
				m.put("objid", result.getString(result.getColumnIndex("objid")));
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
			String collectorid = db.getCollectorid();
			Date serverDate = null;
			try {
				serverDate = db.getServerDate();
			}
			catch (Exception e) { ApplicationUtil.showShortMsg(context, "Error: ParseException"); }
			if(db.isOpen) db.closeDb();
			String routecode = "";
			boolean forupload = false;
			Map<String, Object> collectionsheet = new HashMap<String, Object>();
			ArrayList<Map<String, Object>> payments = new ArrayList<Map<String, Object>>();
			ArrayList<Map<String, Object>> notes = new ArrayList<Map<String, Object>>();
			if(!db.isOpen) db.openDb();
			String sessionid = "";
			int totalcount = db.getTotalCollectionSheetsForUpload();
			Cursor routes = db.getRoutes();
			routes.moveToFirst();
			do {
				payments.clear();
				notes.clear();
				routecode = routes.getString(routes.getColumnIndex("routecode"));
				Cursor cs = db.getCollectionsheetsByRoute(routecode);
				if (cs != null && cs.getCount() > 0) {
					cs.moveToFirst();
					String loanappid= "";
					do {
						loanappid = cs.getString(cs.getColumnIndex("loanappid"));
						sessionid = cs.getString(cs.getColumnIndex("sessionid"));
						collectionsheet.put("loanappid", loanappid);
						collectionsheet.put("detailid", cs.getString(cs.getColumnIndex("detailid")));
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
						collectionsheet.put("notes", notes);
						String remarks = "";
						Cursor r = db.getRemarksByAppid(loanappid);
						if (r != null && r.getCount() > 0) {
							r.moveToFirst();
							remarks = r.getString(r.getColumnIndex("remarks"));
							forupload = true;
						}
						collectionsheet.put("remarks", remarks);
						if (forupload == false) db.removeCollectionsheetByLoanappid(loanappid);
						else if (forupload == true) break;
					} while(cs.moveToNext());
					if (forupload == true) break;
				}
			} while(routes.moveToNext());
			db.closeDb();
			if (forupload) {			
				Map<String, Object> map = new HashMap<String, Object>();
				BigDecimal totalamount = new BigDecimal("0").setScale(2);
				for(int i=0; i<payments.size(); i++) {
					map = (Map<String, Object>) payments.get(i);
					//System.out.println(map);
					totalamount = totalamount.add(new BigDecimal(map.get("payamount").toString()));
				}

				Map<String, Object> params=new HashMap<String, Object>();
				//params.put("payments", list);
				params.put("collectorid", collectorid);
				params.put("collectionsheet", collectionsheet);
				params.put("sessionid", sessionid);
				params.put("txndate", serverDate);
				params.put("routecode", routecode);
				params.put("totalcount", totalcount);
				params.put("totalamount", totalamount);
				
				Message msg = responseHandler.obtainMessage();
				Bundle bundle = new Bundle();
				String status = "";
				try {
					msg=uploadHandler.obtainMessage();
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