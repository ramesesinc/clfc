package com.rameses.paymentcollectionapp;

import java.util.HashMap;
import java.util.Map;

import com.rameses.service.ServiceProxy;
import com.rameses.util.Encoder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
	
	public static final void showIdleDialog(Context context, String content) {
		final Context c = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Session Timeout");
		View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_idle, null);
		((TextView) view.findViewById(R.id.idle_text)).setText(content);
		builder.setView(view);
		builder.setPositiveButton("Resume", null);
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		final MySQLiteHelper dbHelper = new MySQLiteHelper(context);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				String username = dbHelper.getCollectorUsername(db);
				String password = dbHelper.getCollectorPassword(db);

				String et_password = ((EditText) dialog.findViewById(R.id.login_password)).getText().toString();
				String encval = Encoder.MD5.encode(et_password, username);
				if (!encval.equals(password)) {
					showShortMsg(c, "Password incorrect!");
				} else if (encval.equals(password)) {
					ProjectApplication app = ((ControlActivity) c).getApp();
					if (!app.getIsWaiterRunning()) {
						app.startWaiter();
					}
					app.setIsIdleDialogShowing(false);
					dbHelper.setActiveState(db);
					dialog.dismiss();
				}
				db.close();
			}
		});
	}
	
	public static final Map<String, Object> createMenuItem(String id, String text, String subtext, int iconid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("iconid", iconid);
		
		String t = "";
		if (text != null) t = text;
		map.put("text", t);
		
		String s = "";
		if (subtext != null) s = subtext;
		map.put("subtext", s);
		
		return map;
	}
	
	public static final ServiceProxy getServiceProxy(Context context, String serviceName) {
		int networkStatus = ((ProjectApplication) context.getApplicationContext()).getNetworkStatus();
		String type = "online";
		if (networkStatus == 0) {
			type = "offline";
		}
		return (new ServiceHelper(context)).createServiceProxy(serviceName, type);
	}
	
	public static final String getTerminalid(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
}
