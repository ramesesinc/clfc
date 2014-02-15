package com.rameses.clfc.android.main;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.db.android.SQLTransaction;
import com.rameses.util.MapProxy;

public class CollectionSheetAdapter extends BaseAdapter 
{
	private Activity activity;
	private List<Map> list;
	
	public CollectionSheetAdapter(Activity activity, List<Map> list) {
		this.activity = activity;
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
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = view;
		if(v == null) {
			v = inflater.inflate(R.layout.item_collectionsheet, null);
		}
		//CheckedTextView ctv_name = (CheckedTextView) v.findViewById(R.id.ctv_info_name);
		TextView tv_info_name = (TextView) v.findViewById(R.id.tv_specialcollection_remarks);
		ImageView iv_info_paid = (ImageView) v.findViewById(R.id.iv_specialcollection_approved);
		
		MapProxy item = new MapProxy(list.get(position));		
		tv_info_name.setText(item.get("acctname").toString());
		
		String loanappid = item.get("loanappid").toString();
		SQLTransaction txn = new SQLTransaction("clfc.db");
		DBPaymentService dbPs = new DBPaymentService();
		dbPs.setDBContext(txn.getContext());
		
		int noOfPayments = 0;
		try {
			noOfPayments = dbPs.noOfPaymentsByLoanappid(loanappid);
		} catch (Exception e) {
			e.printStackTrace();
			noOfPayments = 0;
		}
		
		DBVoidService dbVs = new DBVoidService();
		dbVs.setDBContext(txn.getContext());
		
		int noOfVoids = 0;
		try { 
			noOfVoids = dbVs.noOfVoidPaymentsByLoanappid(loanappid);
		} catch (Exception e) {
			noOfVoids = 0;
		}

		iv_info_paid.setVisibility(View.GONE);
		System.out.println("no of voids -> "+noOfVoids+" no of payments -> "+noOfPayments);
		if (noOfPayments > 0 && noOfPayments > noOfVoids) {
			iv_info_paid.setVisibility(View.VISIBLE);
		}
		return v;
	}

}
