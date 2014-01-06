package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpecialCollectionAdapter extends BaseAdapter {
	private ArrayList<SpecialCollectionParcelable> list;
	private Context context;
	
	public SpecialCollectionAdapter(Context context, ArrayList<SpecialCollectionParcelable> list) {
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
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_specialcollection, null);
		}
		
		SpecialCollectionParcelable s = (SpecialCollectionParcelable) list.get(position);
		((TextView) v.findViewById(R.id.tv_specialcollection_remarks)).setText(s.getRemarks());

		if (s.getState().equals("APPROVED")) {
			v.setClickable(false);
			((ImageView) v.findViewById(R.id.iv_specialcollection_approved)).setImageResource(R.drawable.btn_check_on);
		}
		return v;
	}

}
