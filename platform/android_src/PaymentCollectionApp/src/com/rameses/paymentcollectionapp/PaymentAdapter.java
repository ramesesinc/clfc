package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

public class PaymentAdapter extends BaseAdapter {
	private ArrayList<PaymentParcelable> list;
	private Context context;
	
	public PaymentAdapter(Context context, ArrayList<PaymentParcelable> list) {
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
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_payment, null);
		}
		TextView tv_info_refno = (TextView) v.findViewById(R.id.tv_info_refno);
		TextView tv_info_txndate = (TextView) v.findViewById(R.id.tv_info_txndate);
		TextView tv_info_type = (TextView) v.findViewById(R.id.tv_info_paymenttype);
		TextView tv_info_amount = (TextView) v.findViewById(R.id.tv_info_paymentamount);
		
		PaymentParcelable p = list.get(position);
		tv_info_refno.setText(p.getRefno());
		tv_info_txndate.setText(p.getTxndate());
		tv_info_type.setText(p.getType());
		tv_info_amount.setText(p.getAmount());
		
		return v;
	}

}
