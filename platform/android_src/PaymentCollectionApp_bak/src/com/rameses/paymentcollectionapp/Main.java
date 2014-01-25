package com.rameses.paymentcollectionapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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
import android.os.Looper;
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
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends ControlActivity {
	private Context context = this;
	//private boolean isNetworkEnabled;
	//private LocationManager locationManager;
	//private NetworkInfo networkInfo;
	//private ServiceProxy svcProxy;
	private ProgressDialog progressDialog;
	private String trackerid = "";
	private String collectorid = "";
	private String txndate = "";
	private AlertDialog dialog;
	//private ServiceHelper svcHelper = new ServiceHelper(context);
	//private LocationListener locationListener = new DeviceLocationListener();
	//private ExecutorService threadPool = Executors.newFixedThreadPool(2); 
	/*private Handler locationHandler = new Handler();
	private Thread locationThread = new Thread() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Location location = null;
			String provider = "";
			if (isNetworkEnabled) {
				provider = LocationManager.NETWORK_PROVIDER;
				locationManager.requestLocationUpdates(provider, 0, 0, locationListener, Looper.getMainLooper());
				location = locationManager.getLastKnownLocation(provider);
				if (getApp() != null && location != null) {
					getApp().setLongitude(location.getLongitude());
					getApp().setLatitude(location.getLatitude());
				}
			}
			locationManager.removeUpdates(locationListener);
			//Executors.newSingleThreadExecutor().submit(new PublishLocationRunnable());
			//params.put("payments", list);
			Map<String, Object> params = new HashMap<String, Object>();
			//if (!db.isOpen) db.openDb();
			//params.put("sessions", db.getSessionid());
			if (trackerid == null || trackerid.equals("")) setTrackerid();
			params.put("trackerid", trackerid);
			//if (db.isOpen) db.closeDb();
			String terminalkey = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if (terminalkey == null) {
				terminalkey = Build.ID;
			}
			params.put("terminalkey", terminalkey);
			params.put("longitude", getApp().getLongitude());
			params.put("latitude", getApp().getLatitude());
			params.put("remarks", "");
			ServiceProxy locationProxy = ApplicationUtil.getServiceProxy(context, "DeviceLocationService");
			try {
				locationProxy.invoke("postLocation", new Object[]{params});
			} catch(Exception e) {}
			try {
				Thread.sleep(57000);
				threadPool.submit(locationThread);
			} catch (InterruptedException ie) {
				System.out.println("interrupted");
			} catch (Exception e) {}
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
			ServiceProxy proxy = ApplicationUtil.getServiceProxy(context, "DeviceLocationService");//svcHelper.createServiceProxy("DeviceLocationService");
			//params.put("payments", list);
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				//if (!db.isOpen) db.openDb();
				//params.put("sessions", db.getSessionid());
				if (trackerid == null || trackerid.equals("")) setTrackerid();
				params.put("trackerid", trackerid);
				//if (db.isOpen) db.closeDb();
				String terminalkey = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
				if (terminalkey == null) {
					terminalkey = Build.ID;
				}
				params.put("terminalkey", terminalkey);
				params.put("longitude", getApp().getLongitude());
				params.put("latitude", getApp().getLatitude());
				params.put("remarks", msg);
				proxy.invoke("postLocation", new Object[]{params});
			} catch(Exception e) {}
			finally { 
				if (repeat) locationHandler.postDelayed(locationRunnable, 57000); 
			}
		}
	}
	
	private Handler backgroundProcessHandler = new Handler();
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		setTitle("CLFC - Integrated Lending System");
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_main, rl_container, true);
		String mode = "NOT CONNECTED";
		int status = getIntent().getIntExtra("networkStatus", 2);
		if (status == 1) mode = "ONLINE";
		else if (status == 0) mode = "OFFLINE";
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		//locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//locationProxy = ApplicationUtil.getServiceProxy(context, "DeviceLocationService");//svcHelper.createServiceProxy("DeviceLocationService");
		//locationHandler.postDelayed(locationRunnable, 3000);
		//backgroundProcessHandler.postDelayed(backgroundProcessRunnable, 3000);
		//threadPool.submit(locationThread);
		//threadPool.submit(backgroundProcessThread);
		//threadPool.submit(backgroundProcessRunnable);
	}	
	
	private void setTrackerid() {
		if (trackerid == null || trackerid.equals("")) {
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			trackerid = getDbHelper().getTrackerid(db);
			db.close();
		}
	}
	
	private void setCollectorid() {
		if (collectorid == null || collectorid.equals("")) {
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			collectorid = getDbHelper().getCollectorid(db);
			db.close();
		}
	}
	
	/*private void setTxnDate() {
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		try {
			Date date = getDbHelper().getServerDate(db);
			if (date != null) {
				txndate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			}
		} catch (Exception e) {}
		db.close();
	}*/
	
	@Override
	public void onBackPressed() {
		if (getApp().getIsWaiterRunning()) {
			getApp().stopWaiter();
		}
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		getDbHelper().setIdleState(db);
		if (!getApp().getIsIdleDialogShowing()) {
			getApp().showIdleDialog(this);
		}
	}
		
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		if (getDbHelper().hasDownloadedCollectionsheet(db, getDbHelper().getCollectorid(db))) {
			if (getApp().getIsWaiterRunning() == false) {
				getApp().startWaiter();
			}
		}
		db.close();
		//postingProxy = ApplicationUtil.getServiceProxy(context, "DevicePostingService");//svcHelper.createServiceProxy("DevicePostingService");
		//isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		//ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
		//networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		//ListView optionLv = (ListView)findViewById(R.id.optionList);
		db = getDbHelper().getReadableDatabase();
		String txndate = "";
		try {
			String date = getDbHelper().getServerDate(db, getDbHelper().getCollectorid(db));
			if (date != null || !date.equals("")) {
				Date dt  = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				txndate = new SimpleDateFormat("MMM dd, yyyy").format(dt);
			}
		} catch(Exception e) {}
		db.close();
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(ApplicationUtil.createMenuItem("download", "Download", null, R.drawable.download));
		list.add(ApplicationUtil.createMenuItem("payment", "Payment(s)", txndate, R.drawable.payment));
		list.add(ApplicationUtil.createMenuItem("posting", "Posting", null, R.drawable.posting));
		list.add(ApplicationUtil.createMenuItem("request", "Request Special Collection", null, R.drawable.request));
		list.add(ApplicationUtil.createMenuItem("remit", "Remit", null, R.drawable.remit));
		list.add(ApplicationUtil.createMenuItem("logout", "Logout", null, R.drawable.logout));
		
		GridView gv_menu = (GridView) findViewById(R.id.gv_menu);
		
		gv_menu.setAdapter(new MenuAdapter(context, list));
		gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
				String itemId = item.get("id").toString();
				
				if (itemId.equals("logout")) {
					logoutCollector();
				} else if (itemId.equals("payment")) {
					Intent intent = new Intent(context, CollectionSheetRoute.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					startActivity(intent);
				} else if (itemId.equals("posting")) {
					Intent intent = new Intent(context, PostingCollectionSheet.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					startActivity(intent);
				} else if (itemId.equals("request")) {
					Intent intent = new Intent(context, SpecialCollection.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					startActivity(intent);
				} else if (itemId.equals("download")) {
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					boolean forupload = false;
					Cursor r = getDbHelper().getPendingPayments(db);
					//if (forupload == false && r != null && r.getCount() > 0) forupload = true;
					//r = getDbHelper().getPendingNotes(db);
					if (forupload == false && r != null && r.getCount() > 0) forupload = true;
					r = getDbHelper().getPendingRemarks(db);
					if (forupload == false && r != null && r.getCount() > 0) forupload = true;
					db.close();
					if(forupload == true) {
						//Toast.makeText(context, "There are still collection sheets to upload. Please upload the payments before downloading current billing.", Toast.LENGTH_LONG).show();
						ApplicationUtil.showLongMsg(context, "There are still collection sheets to upload. Please upload the collection sheets before downloading current collection sheets.");
					} else {
						//showLoginDialog();
						getRoutes();
					}
				} else if (itemId.equals("remit")) {
					Intent intent = new Intent(context, RemitRouteCollection.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					startActivity(intent);
				}
			}
		});
		/*arrlist.add("Download Collection Sheet(s)");
		arrlist.add("View Collection Sheet(s)");
		arrlist.add("View Unposted Info");
		//arrlist.add("Upload Collection Sheet(s)");
		arrlist.add("Special Collection");
		arrlist.add("Close Route");
		arrlist.add("Logout");
		*/
		/*optionLv.setAdapter(new StringAdapter(context, arrlist));
		optionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//String opt = parent.getItemAtPosition(position).toString();
				Intent intent = null;
				if (position == 1) {
					intent = new Intent(context, CollectionSheetRoute.class);
					startActivity(intent);
				} else if (position == 2) {
					intent = new Intent(context, UnpostedInfo.class);
					startActivity(intent);
				} else if (position == 3) {
					if (!getDbHelper().isOpen) getDbHelper().openDb();
					String collectorid = getDbHelper().getCollectorid();
					if (getDbHelper().isOpen) getDbHelper().closeDb();
					if (!collectorid.equals("")) {
						intent = new Intent(context, SpecialCollection.class);
						startActivity(intent);
					} else {
						ApplicationUtil.showShortMsg(context, "You must log in first before requesting for special collection.");
					}
				} else if (position == 4) {
					intent = new Intent(context, CloseBillingRoute.class);
					startActivity(intent);
				} else if (position == 5) {
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					getDbHelper().logoutCollector(db);
					db.close();
					if (getApp().getIsWaiterRunning()) {
							getApp().stopWaiter();
					}
					showLoginDialog();
				} else {
					if(svcProxy == null) {
						//Toast.makeText(context, "Please set the host's ip settings", Toast.LENGTH_SHORT).show();
						ApplicationUtil.showShortMsg(context, "Please set the host's ip settings.");
					} else {
						//if(!getDbHelper().isOpen) getDbHelper().openDb();
						SQLiteDatabase db = getDbHelper().getReadableDatabase();
						boolean forupload = false;
						Cursor cursor = getDbHelper().getPayments(db);
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						cursor = getDbHelper().getNotes(db);
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						cursor = getDbHelper().getRemarks(db);
						if (forupload == false && cursor != null && cursor.getCount() > 0) forupload = true;
						cursor.close();
						db.close();
						//if (getDbHelper().isOpen) getDbHelper().closeDb();
						if(position == 0) {
							if(forupload == true) {
								//Toast.makeText(context, "There are still collection sheets to upload. Please upload the payments before downloading current billing.", Toast.LENGTH_LONG).show();
								ApplicationUtil.showLongMsg(context, "There are still collection sheets to upload. Please upload the collection sheets before downloading current collection sheets.");
							} else {
								//showLoginDialog();
								getRoutes();
							}
						} /*else if(position == 2) {
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
						}*/
		/*			}
				}
			}
		});*/
		
		//if(svcHelper.isHostSet()) svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");//svcHelper.createServiceProxy("DeviceLoanBillingService");
	}
	
	private void logoutCollector() {
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		boolean hasUnpostedPayments = getDbHelper().hasUnpostedPayments(db);
		boolean hasUnpostedRemarks = getDbHelper().hasUnpostedRemarks(db);
		boolean hasUnremittedCollections = getDbHelper().hasUnremittedCollections(db);
		db.close();
		
		if (hasUnpostedPayments == true) {
			ApplicationUtil.showShortMsg(context, "Cannot logout. There are still unposted payments.");
		} else if (hasUnpostedRemarks == true) {
			ApplicationUtil.showShortMsg(context, "Cannot logout. There are still unposted remarks.");			
		} else if (hasUnremittedCollections == true) {
			ApplicationUtil.showShortMsg(context, "Cannot logout. There are still unremitted collections.");
		} else {
			DialogInterface.OnClickListener positivelistener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					progressDialog.setMessage("Logging out");
					if (!progressDialog.isShowing()) progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new Runnable() {
						@Override
						public void run() {
							SQLiteDatabase db = getDbHelper().getReadableDatabase();
							Cursor remittedRoutes = getDbHelper().getRemittedRoutesByCollectorid(db, getDbHelper().getCollectorid(db));
							
							if (remittedRoutes != null && remittedRoutes.getCount() > 0) {
								remittedRoutes.moveToFirst();
								String routecode;
								Cursor cs;
								do {
									routecode = remittedRoutes.getString(remittedRoutes.getColumnIndex("routecode"));
									cs = getDbHelper().getCollectionsheetsByRoute(db, routecode);
									if (cs != null && cs.getCount() > 0) {
										cs.moveToFirst();
										String loanappid = "";
										do {
											loanappid = cs.getString(cs.getColumnIndex("loanappid"));
											getDbHelper().removeVoidPaymentByAppid(db, loanappid);
											getDbHelper().removePaymentByAppid(db, loanappid);
											getDbHelper().removeNotesByAppid(db, loanappid);
											getDbHelper().removeRemarksByAppid(db, loanappid);
											getDbHelper().removeRemarksRemovedByAppid(db, loanappid);
											getDbHelper().removeCollectionsheetByLoanappid(db, loanappid);
										} while(cs.moveToNext());
									}
									getDbHelper().removeRouteByCode(db, routecode);
								} while(remittedRoutes.moveToNext());
							}
							
							getDbHelper().logoutCollector(db);
							db.close();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (progressDialog.isShowing()) progressDialog.dismiss();
									finish();
								}
							});
						}
					});
				}
			};
			String msg = "Are you sure you want to logout?";
			ApplicationUtil.showConfirmationDialog(context, msg, positivelistener, null);
		}
	}
		
	@Override
	protected void onDestroy() {
		if (getApp().getIsWaiterRunning()) {
			getApp().stopWaiter();
		}
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
					Intent intent=new Intent(context, Settings.class);
					startActivity(intent);
					break;
		}
		return true;
	}
	
	private Handler routesHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			Intent intent = new Intent(context, Route.class);
			intent.putExtra("bundle", bundle);
			intent.putExtra("networkStatus", getApp().getNetworkStatus());
			if (progressDialog.isShowing()) progressDialog.dismiss();
			startActivity(intent);
		}
	};
	
	private void getRoutes() {
		Map<String, Object> params = new HashMap<String, Object>();
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		params.put("collectorid", getDbHelper().getCollectorid(db));
		db.close();
		Bundle bundle = new Bundle();
		Message msg = responseHandler.obtainMessage();
		boolean status = false;
		ServiceProxy svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");
		try {
			Object response = svcProxy.invoke("getRoutes", new Object[]{params});
			Map<String, Object> result = (Map<String, Object>) response;
			bundle.putParcelableArrayList("routes", ((ArrayList<RouteParcelable>) result.get("routes")));
			msg = routesHandler.obtainMessage();
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
			if(status == true) routesHandler.sendMessage(msg);
			else responseHandler.sendMessage(msg);
		}
	}
	/*
	private void showLoginDialog() {
		getApp().setIsLoginDialogShowing(true);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Login");
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_login, null);
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Cursor host = getDbHelper().getHost(db);
		db.close();
		if (host != null && host.getCount() > 0) {
			((EditText) view.findViewById(R.id.login_url)).setText(host.getString(host.getColumnIndex("ipaddress")));
		}
		builder.setView(view);
		builder.setPositiveButton("Login", null);
		dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
		Button btn_positive = (Button) dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String username = ((EditText) dialog.findViewById(R.id.login_username)).getText().toString();
				String password = ((EditText) dialog.findViewById(R.id.login_password)).getText().toString();
				String url = ((EditText) dialog.findViewById(R.id.login_url)).getText().toString();
				
				if (username.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "Username is required.");
				} else if (password.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "Password is required.");
				} else if (url.trim().equals("")) {
					ApplicationUtil.showShortMsg(context, "URL is required.");
				} else {
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					Cursor host = getDbHelper().getHost(db);
					if (host != null && host.getCount() > 0) {
						getDbHelper().updateHost(db, url, host.getString(host.getColumnIndex("port")));
						svcProxy = svcHelper.createServiceProxy("DeviceLoginService");
						locationProxy = svcHelper.createServiceProxy("DeviceLocationService");
					}
					db.close();
					if (progressDialog.isShowing()) progressDialog.dismiss();
					progressDialog.setMessage("Logging in.");
					progressDialog.show();
					Executors.newSingleThreadExecutor().submit(new LoginRunnable(username, password));
				}
			}
		});
	}*/
	/*
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("sessionid", bundle.getString("billingid"));
			//params.put("name", "serverdate");
			//params.put("value", bundle.getString("serverdate"));
			//getDbHelper().insertSystem(db, params);
			params.put("name", "collectorid");
			params.put("value", bundle.getString("collectorid"));
			getDbHelper().insertSystem(db, params);
			db.close();
			getApp().setIsLoginDialogShowing(false);
			if (!getApp().getIsWaiterRunning()) {
				getApp().startWaiter();
			}
			if (progressDialog.isShowing()) progressDialog.dismiss();
			if (dialog.isShowing()) dialog.dismiss();
			//Intent intent=new Intent(context, Route.class);
			//intent.putExtra("bundle", bundle);
			//if (progressDialog.isShowing()) progressDialog.dismiss();
			//if (dialog.isShowing()) dialog.dismiss();
			//startActivity(intent);
		}
	};*/
	/*
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
				
				//byte[] barr;
				
				//ArrayList list = (ArrayList) result.get("routes");
				//for(int i=0; i < list.size(); i++) {
				//	barr = list.get(i).toString().getBytes(); 
				//	System.out.println(barr.length);
				//}
				
				//bundle.putParcelableArrayList("routes", ((ArrayList<RouteParcelable>) result.get("routes")));
				//bundle.putString("billingid", result.get("billingid").toString());
				if (result.containsKey("collector")) {
					Map<String, Object> m = (Map<String, Object>) result.get("collector");
					SQLiteDatabase db = getDbHelper().getReadableDatabase();
					params = new HashMap<String, Object>();
					params.put("name", "collector_username");
					params.put("value", m.get("username").toString());
					getDbHelper().insertSystem(db, params);
					params = new HashMap<String, Object>();
					params.put("name", "collector_password");
					params.put("value", m.get("pwd").toString());
					getDbHelper().insertSystem(db, params);
					getDbHelper().loginCollector(db);
					db.close();
				}
				//bundle.putString("serverdate", result.get("serverdate").toString());
				bundle.putString("collectorid", result.get("collectorid").toString());
				status = "ok";
			} catch( TimeoutException te ) {
				bundle.putString("response", "Connection Timeout!");
			} catch( IOException ioe ) {
				bundle.putString("response", "Error connecting to Server.");
			} catch( Exception e ) { 
				bundle.putString("response", e.getMessage());
				e.printStackTrace(); 
			} finally {
				msg.setData(bundle);
				if(status == "ok") loginHandler.sendMessage(msg);
				else responseHandler.sendMessage(msg);
			}
		}
	}*/

	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
	/*
	public void uploadPayments() {
		if(progressDialog.isShowing()) progressDialog.dismiss();
		progressDialog.setMessage("Uploading collection sheets.");
		progressDialog.show();
		Executors.newSingleThreadExecutor().submit(new UploadPaymentsRunnable());		
	}*/
	/*
	private Handler uploadHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			String loanappid = bundle.getString("loanappid");
			ArrayList<String> list= bundle.getStringArrayList("list");
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
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
			getDbHelper().removePaymentByAppid(db, loanappid);
			getDbHelper().removeNotesByAppid(db, loanappid);
			getDbHelper().removeRemarksByAppid(db, loanappid);
			getDbHelper().removeCollectionsheetByLoanappid(db, loanappid);
			boolean finishUploading = true;
			Cursor result = getDbHelper().getPayments();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			result = getDbHelper().getNotes();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			result = getDbHelper().getRemarks();
			if (finishUploading == true && result != null && result.getCount() > 0) finishUploading = false;
			result.close();
			if (finishUploading == true) {
				getDbHelper().removeAllCollectionsheets();
				getDbHelper().removeAllPayments();
				getDbHelper().removeAllNotes();
				getDbHelper().removeAllRemarks();
				getDbHelper().removeAllUploads();
				getDbHelper().removeAllRoutes();
				getDbHelper().removeAllSessions();
				getDbHelper().removeAllSpecialCollections();
				getDbHelper().emptySystemTable();
				if (progressDialog.isShowing()) progressDialog.dismiss();
				//Toast.makeText(context, "Successfully uploaded payments!", Toast.LENGTH_SHORT).show();
				ApplicationUtil.showShortMsg(context, "Successfully uploaded collection sheets!");
			} else { uploadPayments(); }
			getDbHelper().closeDb();
		}
	};*/
	/*
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
			cursor.close();
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
			result.close();
		}
	}*/
	/*
	private class UploadPaymentsRunnable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!getDbHelper().isOpen) getDbHelper().openDb();
			String collectorid = getDbHelper().getCollectorid();
			Date serverDate = null;
			try {
				serverDate = getDbHelper().getServerDate();
			}
			catch (Exception e) { ApplicationUtil.showShortMsg(context, "Error: ParseException"); }
			if(getDbHelper().isOpen) getDbHelper().closeDb();
			String routecode = "";
			boolean forupload = false;
			Map<String, Object> collectionsheet = new HashMap<String, Object>();
			ArrayList<Map<String, Object>> payments = new ArrayList<Map<String, Object>>();
			ArrayList<Map<String, Object>> notes = new ArrayList<Map<String, Object>>();
			if(!getDbHelper().isOpen) getDbHelper().openDb();
			String sessionid = "";
			int totalcount = getDbHelper().getTotalCollectionSheetsForUpload();
			Cursor routes = getDbHelper().getRoutes();
			routes.moveToFirst();
			do {
				payments.clear();
				notes.clear();
				routecode = routes.getString(routes.getColumnIndex("routecode"));
				Cursor cs = getDbHelper().getCollectionsheetsByRoute(routecode);
				if (cs != null && cs.getCount() > 0) {
					cs.moveToFirst();
					String loanappid= "";
					do {
						loanappid = cs.getString(cs.getColumnIndex("loanappid"));
						sessionid = cs.getString(cs.getColumnIndex("sessionid"));
						collectionsheet.put("loanappid", loanappid);
						collectionsheet.put("detailid", cs.getString(cs.getColumnIndex("detailid")));
						Cursor p = getDbHelper().getPaymentsByAppid(loanappid);
						if (p != null && p.getCount() > 0) {
							setPayments(p, payments);
							forupload = true;
							p.close();
						}
						collectionsheet.put("payments", payments);
						Cursor n = getDbHelper().getNotesByAppid(loanappid);
						if (n != null && n.getCount() > 0) {
							setNotes(n, notes);
							forupload = true;
							n.close();
						}
						collectionsheet.put("notes", notes);
						String remarks = "";
						Cursor r = getDbHelper().getRemarksByAppid(loanappid);
						if (r != null && r.getCount() > 0) {
							r.moveToFirst();
							remarks = r.getString(r.getColumnIndex("remarks"));
							forupload = true;
							r.close();
						}
						collectionsheet.put("remarks", remarks);
						if (forupload == false) getDbHelper().removeCollectionsheetByLoanappid(loanappid);
						else if (forupload == true) break;
					} while(cs.moveToNext());
					cs.close();
					if (forupload == true) break;
				}
			} while(routes.moveToNext());
			routes.close();
			getDbHelper().closeDb();
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
				params.put("longitude", getApp().getLongitude());
				params.put("latitude", getApp().getLatitude());
				if (!getDbHelper().isOpen) getDbHelper().openDb();
				params.put("trackerid", getDbHelper().getTrackerid());
				if (getDbHelper().isOpen) getDbHelper().closeDb();
				
				System.out.println("params = "+params);
				Message msg = responseHandler.obtainMessage();
				Bundle bundle = new Bundle();
				boolean status = false;
				ServiceProxy svcProxy = ApplicationUtil.getServiceProxy(context, "DeviceLoanBillingService");
				try {
					Object response = svcProxy.invoke("uploadCollectionSheets", new Object[]{params});
					Map<String, Object> result = (Map<String, Object>) response;
					bundle.putString("loanappid", collectionsheet.get("loanappid").toString());
					bundle.putStringArrayList("list", ((ArrayList<String>) result.get("list")));
					status = true;
					msg=uploadHandler.obtainMessage();
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
					if(status == true) uploadHandler.sendMessage(msg);
					else responseHandler.sendMessage(msg);
				}
			}
		}
	}*/
}