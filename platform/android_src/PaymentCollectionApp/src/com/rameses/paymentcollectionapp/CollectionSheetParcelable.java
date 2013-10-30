package com.rameses.paymentcollectionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectionSheetParcelable implements Parcelable {
	private String loanappid = "";
	private String appno = "";
	private String acctname = "";
	private Double amountdue = 0.0;
	private int isfirstbill = 0;
	
	public CollectionSheetParcelable() {}
	public CollectionSheetParcelable(Parcel source) {
		this.loanappid = source.readString();
		this.appno = source.readString();
		this.acctname = source.readString();
		this.amountdue = source.readDouble();
		this.isfirstbill = source.readInt();
	}
	
	public void setLoanappid(String loanappid) {
		this.loanappid = loanappid;
	}
	
	public void setAppno(String appno) {
		this.appno = appno;
	}
	
	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	
	public void setAmountdue(Double amountdue) {
		this.amountdue = amountdue;
	}
	
	public void setIsfirstbill(int isfirstbill) {
		this.isfirstbill = isfirstbill;
	}
	
	public String getLoanappid() { return this.loanappid; }
	public String getAppno() { return appno; }
	public String getAcctname() { return this.acctname; }
	public Double getAmountdue() { return this.amountdue; }
	public int getIsfirstbill() { return this.isfirstbill; }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(loanappid);
		dest.writeString(appno);
		dest.writeString(acctname);
		dest.writeDouble(amountdue);
		dest.writeInt(isfirstbill);
	}

	public class MyCreate implements Parcelable.Creator<CollectionSheetParcelable> {
		@Override
		public CollectionSheetParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CollectionSheetParcelable(source);
		}

		@Override
		public CollectionSheetParcelable[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CollectionSheetParcelable[size];
		}
	}
}
