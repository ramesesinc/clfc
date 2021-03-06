package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionSheetAdapter extends BaseAdapter {
	private ArrayList<CollectionSheetParcelable> list;
	private Context context;
	
	public CollectionSheetAdapter(Context context, ArrayList<CollectionSheetParcelable> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = view;
		if(v== null) {
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflater.inflate(R.layout.item_collectionsheet, null);
		}
		//CheckedTextView ctv_name = (CheckedTextView) v.findViewById(R.id.ctv_info_name);
		TextView tv_info_name = (TextView) v.findViewById(R.id.tv_specialcollection_remarks);
		ImageView iv_info_paid = (ImageView) v.findViewById(R.id.iv_specialcollection_approved);
		CollectionSheetParcelable cs = (CollectionSheetParcelable) list.get(position);
		tv_info_name.setText(cs.getAcctname());
		
		MySQLiteHelper dbHelper = new MySQLiteHelper(v.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor result = dbHelper.getPaymentsByAppid(db, cs.getLoanappid());
		int noOfVoidPayments = dbHelper.countVoidPaymentsByAppid(db, cs.getLoanappid());
		db.close();

		iv_info_paid.setVisibility(View.GONE);
		if (result != null && result.getCount() > 0 && result.getCount() > noOfVoidPayments) {
			iv_info_paid.setVisibility(View.VISIBLE);
			//ctv_name.setChecked(true);
		}
		return v;
	}

}
