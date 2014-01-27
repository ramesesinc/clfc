package com.rameses.paymentcollectionapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionSheetRoute extends ControlActivity {
	private Context context = this;
	private View header = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_collectionsheet_route, rl_container, true);
		setTitle("Collection Sheet");
		int status = getIntent().getIntExtra("networkStatus", 2);
		String mode = "NOT CONNECTED";
		if (status == 1) mode = "ONLINE";
		else if (status == 0) mode = "OFFLINE";
		((TextView) findViewById(R.id.tv_mode)).setText(mode);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);

		Date date = null;
		String billdate = "Collection Date: ";
		TextView tv_billdate = (TextView) findViewById(R.id.tv_billdate);
		ListView lv_route = (ListView) findViewById(R.id.lv_route);

		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		Cursor cursor = getDbHelper().getRoutes(db);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
		ArrayList<RouteParcelable> list = new ArrayList<RouteParcelable>();
		RouteParcelable r;
		if (cursor != null && cursor.getCount() > 0) {
			try {
				String txndate = getDbHelper().getServerDate(db, getDbHelper().getCollectorid(db));
				c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(txndate));
			}
			catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
			billdate += df.format(c.getTime());
			cursor.moveToFirst();
			do {
				r = new RouteParcelable();
				r.setCode(cursor.getString(cursor.getColumnIndex("routecode")));
				r.setDescription(cursor.getString(cursor.getColumnIndex("routedescription")));
				r.setArea(cursor.getString(cursor.getColumnIndex("routearea")));
				r.setState(cursor.getString(cursor.getColumnIndex("state")));
				list.add(r);
			} while(cursor.moveToNext());			
		}
		db.close();
		tv_billdate.setText(billdate);
		if (header == null) {
			header = (View) getLayoutInflater().inflate(R.layout.header_route, null);
			lv_route.addHeaderView(header, null, false);
		}
		lv_route.setAdapter(new RouteAdapter(context, list));
		lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				RouteParcelable r = (RouteParcelable) parent.getItemAtPosition(position);
				if (r.getState().equals("REMITTED")) {
					/* do nothing */
				} else {
					Intent intent=new Intent(context, CollectionSheet.class);
					intent.putExtra("networkStatus", getApp().getNetworkStatus());
					intent.putExtra("routecode", r.getCode());
					startActivity(intent);
				}
			}
		});
	}
}
