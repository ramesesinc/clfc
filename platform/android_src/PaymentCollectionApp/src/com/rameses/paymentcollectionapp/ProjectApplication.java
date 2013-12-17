package com.rameses.paymentcollectionapp;

import android.app.Application;

public class ProjectApplication extends Application {
	private double longitude = 0.00;
	private double latitude = 0.00;
		
	public void setLongitude(double longitude) {
		this.longitude = longitude;		
	}
	
	public double getLongitude() { return longitude; }
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLatitude() { return latitude; }	
}
