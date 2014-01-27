package com.rameses.paymentcollectionapp;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PostingCollectionSheet extends ControlActivity {
	private Context context = this;
	private LayoutInflater inflater;
	private EditText et_search;
	/*private ExecutorService threadPool;
	private Thread loadThread = new Thread() {
		@Override
		public void run() {
			SQLiteDatabase db = getDbHelper().getReadableDatabase();
			final int unpostedpayments = getDbHelper().getPendingPayments(db).getCount();
			final int unpostednotes = getDbHelper().getPendingNotes(db).getCount();
			final int unpostedremarks = getDbHelper().getPendingRemarks(db).getCount();
			db.close();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ArrayList<String> list = new ArrayList<String>();
					list.add("Unposted Payments: "+unpostedpayments);
					list.add("Unposted Notes: "+unpostednotes);
					list.add("Unposted Remarks: "+unpostedremarks);
					
					ListView listview = (ListView) findViewById(R.id.lv_route);
					listview.setAdapter(new StringAdapter(context, list));
				}
			});
			
			threadPool.submit(loadThread);
		}
	};*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_footer);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_posting, container, true);
		RelativeLayout layout_posted = (RelativeLayout) findViewById(R.id.layout_posted);
		inflater.inflate(R.layout.section_posted, layout_posted, true);
		RelativeLayout layout_unposted = (RelativeLayout) findViewById(R.id.layout_unposted);
		inflater.inflate(R.layout.section_unposted, layout_unposted, true);
		//((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ac)
		setTitle("Posting");
		et_search = (EditText) findViewById(R.id.et_search);
		if (getDbHelper() == null) setDbHelper(new MySQLiteHelper(context));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getApp().setCurrentActivity(this);
		
		ImageButton ib_refresh = (ImageButton) findViewById(R.id.ib_refresh);
		ib_refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.drawable.list_selector_background);
				load();
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				load();
			}
		});
		load();
	}
	
	private void load() {
		String searchtext = et_search.getText().toString();
		int noOfPosted = 0;
		int noOfUnposted = 0;

		SQLiteDatabase db = getDbHelper().getReadableDatabase();
		String collectorid = getDbHelper().getCollectorid(db);
		Cursor cursor = getDbHelper().getPostedCollectionSheets(db, searchtext, collectorid);
		
		//ArrayList<String> list = new ArrayList<String>();
		View child = null;
		LinearLayout ll_posted = (LinearLayout) findViewById(R.id.ll_posted);
		ll_posted.removeAllViews();
		if (cursor != null && cursor.getCount() > 0) {
			noOfPosted = cursor.getCount();
			cursor.moveToFirst();
			do {
				child = inflater.inflate(R.layout.item_string, null);
				((TextView) child.findViewById(R.id.tv_item_str)).setText(cursor.getString(cursor.getColumnIndex("acctname")));
				ll_posted.addView(child);
				//list.add(cursor.getString(cursor.getColumnIndex("acctname")));
			} while(cursor.moveToNext());
		}
		//((ListView) findViewById(R.id.lv_posted)).setAdapter(new StringAdapter(context, list));
		//list.clear();
		
		cursor = getDbHelper().getUnpostedCollectionSheets(db, searchtext, collectorid);
		LinearLayout ll_unposted = (LinearLayout) findViewById(R.id.ll_unposted);
		ll_unposted.removeAllViewsInLayout();
		if (cursor != null && cursor.getCount() > 0) {
			noOfUnposted = cursor.getCount();
			cursor.moveToFirst();
			do {
				child = inflater.inflate(R.layout.item_string, null);
				((TextView) child.findViewById(R.id.tv_item_str)).setText(cursor.getString(cursor.getColumnIndex("acctname")));
				ll_unposted.addView(child);
				//list.add(cursor.getString(cursor.getColumnIndex("acctname")));
			} while(cursor.moveToNext());
		}
		//((ListView) findViewById(R.id.lv_unposted)).setAdapter(new StringAdapter(context, list));
		cursor.close();
		db.close();
		
		((TextView) findViewById(R.id.tv_posted)).setText("No. of Posted: "+noOfPosted);
		((TextView) findViewById(R.id.tv_unposted)).setText("No. of Unposted: "+noOfUnposted);
	}
}
