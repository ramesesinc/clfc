package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteAdapter extends BaseAdapter 
{
	private ArrayList<RouteParcelable> list;
	private Context context;
	
	public RouteAdapter(Context context, ArrayList<RouteParcelable> list) {
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
		if(v == null) {
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflater.inflate(R.layout.item_route, null);
		}
		TextView name = (TextView) v.findViewById(R.id.tv_str2);
		RouteParcelable route = list.get(position);
		name.setText(route.getDescription()+" - "+route.getArea());
		return v;
	}

}
