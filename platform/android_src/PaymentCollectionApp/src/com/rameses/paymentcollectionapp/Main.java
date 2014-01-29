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

import com.rameses.client.android.ClientContext;
import com.rameses.client.android.DeviceManager;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIApplication;
import com.rameses.client.services.LogoutService;
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
	private ProgressDialog progressDialog;
	private String trackerid = "";
	private String collectorid = "";
	private String txndate = "";
	private AlertDialog dialog;
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_main, rl_container, true);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
	}	
	
	@Override
	public void onBackPressed() {
		if (getApp().getIsWaiterRunning()) {
			getApp().stopWaiter();
		}
		SQLiteDatabase db = getDbHelper().getWritableDatabase();
		getDbHelper().setIdleState(db);
		db.close();
		if (!getApp().getIsIdleDialogShowing()) {
			getApp().showIdleDialog(this);
		}
	}
		
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		String collectorid = SessionContext.getProfile().getUserId();
		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		if (getDbHelper().hasDownloadedCollectionsheet(db, collectorid)) {
			if (getApp().getIsWaiterRunning() == false) {
				getApp().startWaiter();
			}
		}
		String txndate = "";
		try {
			String date = getDbHelper().getServerDate(db, collectorid);
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
		list.add(ApplicationUtil.createMenuItem("changepassword", "Change Password", null, R.drawable.ic_launcher));
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
				} else if (itemId.equals("changepassword")) {
					Intent intent = new Intent(context, ChangePassword.class);
					startActivity(intent);
				}
			}
		});
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
							SQLiteDatabase db = getDbHelper().getWritableDatabase();
							Cursor remittedRoutes = getDbHelper().getRemittedRoutesByCollectorid(db, SessionContext.getProfile().getUserId());
							
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
							
							String collectorid = SessionContext.getProfile().getUserId();
							getDbHelper().removeLocationTrackerByCollectorid(db, collectorid);
							getDbHelper().logoutCollector(db);
							db.close();	
							Map env = ClientContext.getCurrentContext().getAppEnv();
							env.put("app.host", ApplicationUtil.getAppHost(context, getApp().getNetworkStatus()));
							LogoutService svc = new LogoutService();
							try {
								svc.logout();
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (progressDialog.isShowing()) progressDialog.dismiss();
										DeviceManager.getInstance().closeAll();
										UIApplication.getInstance().logout();
										//DeviceManager.getInstance().showActivity(Splash.class);
									}
								});
							}
						}
					});
				}
			};
			String msg = "Are you sure you want to logout?";
			ApplicationUtil.showConfirmationDialog(context, msg, positivelistener, null);
		}
	}
		
	@Override
	protected void onDestroyProcess() {
		if (getApp().getIsWaiterRunning()) {
			getApp().stopWaiter();
		}
		//super.onDestroy();
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
		params.put("collectorid", SessionContext.getProfile().getUserId());
		//SQLiteDatabase db = getDbHelper().getReadableDatabase();
		//params.put("collectorid", getDbHelper().getCollectorid(db));
		//db.close();
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

	private Handler responseHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle=msg.getData();
			if(progressDialog.isShowing()) progressDialog.dismiss();
			ApplicationUtil.showShortMsg(context, bundle.getString("response"));
		}
	};
}