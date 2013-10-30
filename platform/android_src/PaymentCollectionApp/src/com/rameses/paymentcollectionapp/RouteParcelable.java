package com.rameses.paymentcollectionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteParcelable implements Parcelable {
	private String code = "";
	private String description = "";
	private String area = "";
	
	
	public RouteParcelable() {}
	public RouteParcelable(Parcel source) {
		this.code = source.readString();
		this.description = source.readString();
		this.area = source.readString();
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCode() { return this.code; }
	public String getDescription() { return this.description; }
	public String getArea() { return this.area; }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(code);
		dest.writeString(description);
		dest.writeString(area);
	}

	public class MyCreate implements Parcelable.Creator<RouteParcelable> {
		@Override
		public RouteParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new RouteParcelable(source);
		}

		@Override
		public RouteParcelable[] newArray(int size) {
			// TODO Auto-generated method stub
			return new RouteParcelable[size];
		}
	}
}
