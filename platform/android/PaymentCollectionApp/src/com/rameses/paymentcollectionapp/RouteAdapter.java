package com.rameses.paymentcollectionapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
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
		View overlay = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.overlay_void_text, null);
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_route, null);
			//inflater.inflate(R.layout.overlay_void_text, ((RelativeLayout) v));
			((RelativeLayout) v).addView(overlay);
		}
		RouteParcelable route = list.get(position);
		if (route.getState().equals("REMITTED")) {
			((TextView) overlay).setTextColor(context.getResources().getColor(R.color.green));
			((TextView) overlay).setText("REMITTED");
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			overlay.setLayoutParams(layoutParams);
			v.setClickable(false);
		}
		((TextView) v.findViewById(R.id.tv_route_description)).setText(route.getDescription());
		((TextView) v.findViewById(R.id.tv_route_area)).setText(route.getArea());
		return v;
	}

}
