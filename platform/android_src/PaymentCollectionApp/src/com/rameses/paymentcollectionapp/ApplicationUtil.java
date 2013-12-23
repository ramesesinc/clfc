package com.rameses.paymentcollectionapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class ApplicationUtil {	
	public static final void showLongMsg(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static final void showShortMsg(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static final void showConfirmationDialog(Context context, String msg, DialogInterface.OnClickListener positivelistener, DialogInterface.OnClickListener negativelistener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Confirmation");
		builder.setMessage(msg);
		builder.setPositiveButton("Yes", positivelistener);
		builder.setNegativeButton("No", negativelistener);
		AlertDialog d = builder.create();
		d.show();
	}

	public static final void showOptionDialog(Context context, CharSequence[] items, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Options");
		builder.setItems(items, listener);
		AlertDialog d = builder.create();
		d.show();
	}
}
