package com.rameses.paymentcollectionapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionSheetRoute extends Activity {
	private Context context=this;
	private MySQLiteHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheet_route);
		setTitle("Collection Sheet");
		db = new MySQLiteHelper(context);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		Date date = null;
		if(!db.isOpen) db.openDb();
		
		String billdate = "Bill Date: ";
		TextView tv_billdate = (TextView) findViewById(R.id.tv_billdate);
		ListView lv_route = (ListView) findViewById(R.id.lv_route);

		if (!db.isOpen) db.openDb();
		Cursor cursor = db.getRoutes();		
		if (db.isOpen) db.closeDb();

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
		ArrayList<RouteParcelable> list = new ArrayList<RouteParcelable>();
		RouteParcelable r;
		if (cursor != null && cursor.getCount() > 0) {
			if (!db.isOpen) db.openDb();
			try {
				c.setTime(db.getServerDate());
			}
			catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
			if (db.isOpen) db.closeDb();
			billdate += df.format(c.getTime());
			cursor.moveToFirst();
			do {
				r = new RouteParcelable();
				r.setCode(cursor.getString(cursor.getColumnIndex("routecode")));
				r.setDescription(cursor.getString(cursor.getColumnIndex("routedescription")));
				r.setArea(cursor.getString(cursor.getColumnIndex("routearea")));
				list.add(r);
			} while(cursor.moveToNext());			
		}
		tv_billdate.setText(billdate);
		View header = (View) getLayoutInflater().inflate(R.layout.header_route, null);
		lv_route.addHeaderView(header, null, false);
		lv_route.setAdapter(new RouteAdapter(context, list));
		lv_route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				RouteParcelable r = (RouteParcelable) parent.getItemAtPosition(position);
				Intent intent=new Intent(context, CollectionSheet.class);
				intent.putExtra("routecode", r.getCode());
				startActivity(intent);
			}
		});
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
}
