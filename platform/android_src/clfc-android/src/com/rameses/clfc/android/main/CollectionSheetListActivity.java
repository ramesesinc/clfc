package com.rameses.clfc.android.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBCollectionSheet;

public class CollectionSheetListActivity extends ControlActivity 
{
	private Context context = this;
	private ListView lv_collectionsheet;
	private String routecode = "";
	private String type = "";
	private Map item;
	private int isfirstbill;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Collection Sheet");
		
		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_collectionsheet, rl_container, true);
		
		Intent intent = getIntent();
		routecode = intent.getStringExtra("routecode");
	}
	
	@Override
	protected void onStartProcess() {
		super.onStartProcess();

		lv_collectionsheet = (ListView) findViewById(R.id.lv_collectionsheet);
		loadCollectionSheets();
		lv_collectionsheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				selectedItem(parent, view, position, id);
//				CollectionSheetParcelable cs = (CollectionSheetParcelable) parent.getItemAtPosition(position);
//				
//				if (cs.getIsfirstbill() == 1) showDialog(cs);
//				else showCollectionSheetInfo(cs);
				//System.out.println(cs.getAcctname());
			}
		});
		
		EditText et_search = (EditText) findViewById(R.id.et_search);
		et_search.addTextChangedListener(new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				loadCollectionSheets(s+"");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void selectedItem(AdapterView<?> parent, View view, int position, long id) {
		item = (Map) parent.getItemAtPosition(position);
		isfirstbill = Integer.parseInt(item.get("isfirstbill").toString());
		if (isfirstbill != 1) {
			Intent intent = new Intent(this, CollectionSheetInfoActivity.class);
			intent.putExtra("acctname", item.get("acctname").toString());
			intent.putExtra("loanappid", item.get("loanappid").toString());
			intent.putExtra("detailid", item.get("detailid").toString());
			intent.putExtra("appno", item.get("appno").toString());
			intent.putExtra("amountdue", Double.parseDouble(item.get("amountdue").toString()));
			intent.putExtra("routecode", item.get("routecode").toString());
			intent.putExtra("paymenttype", item.get("type").toString());
			intent.putExtra("isfirstbill", Integer.parseInt(item.get("isfirstbill").toString()));
			startActivity(intent);
		} else if (isfirstbill == 0) {
			showPaymentTypeDialog(item);
		}
	}
	
	private void loadCollectionSheets() {
		loadCollectionSheets("");
	}
	
	private void loadCollectionSheets(String searchtext) {
		DBCollectionSheet dbCs = new DBCollectionSheet();		
		try {
			Map params = new HashMap();
			params.put("routecode", routecode);
			searchtext = (!searchtext.equals("")? searchtext+"%" : "%");
			params.put("searchtext", searchtext);
			List<Map> list = dbCs.getCollectionSheetsByRoutecodeAndSearchtext(params);//txn.getList(, params);
			lv_collectionsheet.setAdapter(new CollectionSheetAdapter(this, list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void showPaymentTypeDialog(final Map map) {
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
							showCollectionSheetInfo(map);
						}
					}
				});
			}
		});
		dialog.show();		
	}
	
	private void showCollectionSheetInfo(Map map) {
		Intent intent = new Intent(context, CollectionSheetInfoActivity.class);
		intent.putExtra("acctname", map.get("acctname").toString());
		intent.putExtra("loanappid", map.get("loanappid").toString());
		intent.putExtra("detailid", map.get("detailid").toString());
		intent.putExtra("appno", map.get("appno").toString());
		intent.putExtra("amountdue", Double.parseDouble(map.get("amountdue").toString()));
		intent.putExtra("routecode", routecode);
		intent.putExtra("paymenttype", type);
		intent.putExtra("isfirstbill", Integer.parseInt(map.get("isfirstbill").toString()));
		startActivity(intent);		
	}
//
//	private void loadCollectionsheets(Cursor list) {
//		ArrayList<CollectionSheetParcelable> collectionsheets=new ArrayList<CollectionSheetParcelable>();
//		CollectionSheetParcelable cs;
//		if(list.getCount() > 0) {
//			list.moveToFirst();
//			do {
//				cs=new CollectionSheetParcelable();
//				cs.setLoanappid(list.getString(list.getColumnIndex("loanappid")));
//				cs.setDetailid(list.getString(list.getColumnIndex("detailid")));
//				cs.setAppno(list.getString(list.getColumnIndex("appno")));
//				cs.setAcctname(list.getString(list.getColumnIndex("acctname")));
//				cs.setAmountdue(list.getDouble(list.getColumnIndex("amountdue")));
//				cs.setIsfirstbill(list.getInt(list.getColumnIndex("isfirstbill")));
//				collectionsheets.add(cs);
//			} while(list.moveToNext());
//			list.close();
//		}
//		
//		lv_collectionsheet.setAdapter(new CollectionSheetAdapter(context, collectionsheets));
//	}
}
