package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionSheet extends Activity {
	private Context context = this;
	private MySQLiteHelper db;
	private ListView lv_collectionsheet;
	private String routecode = "";
	private String type = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheet);
		setTitle("Collection Sheet");
		Intent intent = getIntent();
		routecode = intent.getStringExtra("routecode");
		db = new MySQLiteHelper(context);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		lv_collectionsheet = (ListView) findViewById(R.id.lv_collectionsheet);
		if(!db.isOpen) db.openDb();
		final Cursor list = db.getCollectionsheets(routecode);
		db.closeDb();
		
		loadCollectionsheets(list);
		lv_collectionsheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				CollectionSheetParcelable cs = (CollectionSheetParcelable) parent.getItemAtPosition(position);
				
				if (cs.getIsfirstbill() == 1) showDialog(cs);
				else showCollectionSheetInfo(cs);
				//System.out.println(cs.getAcctname());
			}
		});
		
		EditText et_search = (EditText) findViewById(R.id.et_search);
		et_search.addTextChangedListener(new TextWatcher(){

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(!db.isOpen) db.openDb();
				Cursor result = db.searchCollectionSheets(s+"", routecode);
				db.closeDb();
				loadCollectionsheets(result);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	@SuppressLint("NewApi")
	private void showDialog(CollectionSheetParcelable c) {
		final CollectionSheetParcelable cs = c;
		CharSequence[] items = {"Schedule", "Overpayment"};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Payment Method");
		builder.setPositiveButton("OK", null);
		builder.setNegativeButton("Cancel", null);
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(which) {
					case 0: type = "schedule"; break;
					case 1: type = "over"; break;
				}
			}
		});
		
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {			
			@Override
			public void onShow(DialogInterface arg0) {
				// TODO Auto-generated method stub
				Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				b.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (type.equals(null) || type.equals("")) Toast.makeText(context, "Please select payment method.", Toast.LENGTH_SHORT).show();
						else {
							dialog.dismiss();
							showCollectionSheetInfo(cs);
						}
					}
				});
			}
		});
		dialog.show();		
	}
	
	private void showCollectionSheetInfo(CollectionSheetParcelable cs) {
		Intent intent = new Intent(context, CollectionSheetInfo.class);
		intent.putExtra("acctname", cs.getAcctname());
		intent.putExtra("loanappid", cs.getLoanappid());
		intent.putExtra("appno", cs.getAppno());
		intent.putExtra("amountdue", cs.getAmountdue());
		intent.putExtra("routecode", routecode);
		intent.putExtra("paymenttype", type);
		intent.putExtra("isfirstbill", cs.getIsfirstbill());
		startActivity(intent);		
	}

	private void loadCollectionsheets(Cursor list) {
		ArrayList<CollectionSheetParcelable> collectionsheets=new ArrayList<CollectionSheetParcelable>();
		CollectionSheetParcelable cs;
		if(list.getCount() > 0) {
			list.moveToFirst();
			do {
				cs=new CollectionSheetParcelable();
				cs.setLoanappid(list.getString(list.getColumnIndex("loanappid")));
				cs.setAppno(list.getString(list.getColumnIndex("appno")));
				cs.setAcctname(list.getString(list.getColumnIndex("acctname")));
				cs.setAmountdue(list.getDouble(list.getColumnIndex("amountdue")));
				cs.setIsfirstbill(list.getInt(list.getColumnIndex("isfirstbill")));
				collectionsheets.add(cs);
			} while(list.moveToNext());
		}
		
		lv_collectionsheet.setAdapter(new CollectionSheetAdapter(context, collectionsheets));
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
}
