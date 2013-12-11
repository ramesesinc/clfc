package com.rameses.paymentcollectionapp;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentParcelable implements Parcelable {
	private String refno = "";
	private String txndate = "";
	private String type = "";
	private String amount = "";
	
	public PaymentParcelable() {}
	public PaymentParcelable(Parcel source) {
		this.refno = source.readString();
		this.txndate = source.readString();
		this.type = source.readString();
		this.amount = source.readString();
	}
	
	public void setRefno(String refno) {
		this.refno = refno;
	}
	
	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getRefno() { return this.refno; }
	public String getTxndate() { return this.txndate; }
	public String getType() { return this.type; }
	public String getAmount() { return this.amount; }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(refno);
		dest.writeString(txndate);
		dest.writeString(type);
		dest.writeString(amount);
	}

	public class CreatePayment implements Parcelable.Creator<PaymentParcelable> {
		@Override
		public PaymentParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new PaymentParcelable(source);
		}

		@Override
		public PaymentParcelable[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PaymentParcelable[size];
		}
		
	}
}
