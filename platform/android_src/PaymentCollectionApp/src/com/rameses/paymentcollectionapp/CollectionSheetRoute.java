package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CollectionSheetRoute extends Activity {
	private Context context=this;
	private MySQLiteHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		setTitle("Select a Route:");
		db = new MySQLiteHelper(context);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if (!db.isOpen) db.openDb();
		
		ListView lv_route = (ListView) findViewById(R.id.lv_route);
		
		if (!db.isOpen) db.openDb();
		Cursor cursor = db.getRoutes();
		if (db.isOpen) db.closeDb();

		ArrayList<RouteParcelable> list = new ArrayList<RouteParcelable>();
		RouteParcelable r;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				r = new RouteParcelable();
				r.setCode(cursor.getString(cursor.getColumnIndex("routecode")));
				r.setDescription(cursor.getString(cursor.getColumnIndex("routedescription")));
				r.setArea(cursor.getString(cursor.getColumnIndex("routearea")));
				list.add(r);
			} while(cursor.moveToNext());			
		}
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
