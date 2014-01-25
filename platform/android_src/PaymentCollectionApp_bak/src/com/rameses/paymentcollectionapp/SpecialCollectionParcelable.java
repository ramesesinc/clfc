package com.rameses.paymentcollectionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialCollectionParcelable implements Parcelable {
	private String objid = "";
	private String state = "";
	private String remarks = "";
	
	public SpecialCollectionParcelable() {}
	public SpecialCollectionParcelable(Parcel source) {
		this.objid = source.readString();
		this.state = source.readString();
		this.remarks = source.readString();
	}
	
	public void setObjid(String objid) {
		this.objid = objid;
	}
	
	public void setState(String state) {
		this.state = state;		
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getObjid() { return this.objid; }
	public String getState() { return this.state; }
	public String getRemarks() { return this.remarks; }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(objid);
		dest.writeString(state);
		dest.writeString(remarks);
	}

	
	public class Creator implements Parcelable.Creator<SpecialCollectionParcelable> {
		@Override
		public SpecialCollectionParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new SpecialCollectionParcelable(source);
		}

		@Override
		public SpecialCollectionParcelable[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SpecialCollectionParcelable[size];
		}
		
	}
}
