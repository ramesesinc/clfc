package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionSheetInfo extends Activity {
	private Context context = this;
	private MySQLiteHelper db;
	private String loanappid = "";
	private String detailid = "";
	private BigDecimal overpayment = new BigDecimal("0").setScale(2);
	private String routecode = "";
	private String refno = "";
	private String paymenttype = "";
	private int totaldays = 0;
	private Cursor payment;
	private String remarks = "";
	private Cursor notes;
	private int isfirstbill = 0;
	private RelativeLayout rl_general = null;
	private RelativeLayout rl_payment = null;
	private RelativeLayout rl_remarks = null;
	private RelativeLayout rl_notes = null;
	private AlertDialog dialog = null;
	private SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheetinfo);
		setTitle("Collection Sheet Info");
		db = new MySQLiteHelper(context);
		rl_general = (RelativeLayout) findViewById(R.id.layout_info_general);
		rl_payment = (RelativeLayout) findViewById(R.id.layout_info_payment);
		rl_remarks = (RelativeLayout) findViewById(R.id.layout_info_remarks);
		rl_notes = (RelativeLayout) findViewById(R.id.layout_info_notes);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = getIntent();
		loanappid = intent.getStringExtra("loanappid");
		detailid = intent.getStringExtra("detailid");
		routecode = intent.getStringExtra("routecode");
		paymenttype = intent.getStringExtra("paymenttype");
		isfirstbill = intent.getIntExtra("isfirstbill", 0);
		db.openDb();
		Cursor result = db.getCollectionsheetByLoanappid(loanappid);
		db.closeDb();
				
		String acctname = "";
		String appno = "";
		BigDecimal amountdue = new BigDecimal("0").setScale(2);
		BigDecimal loanamount = new BigDecimal("0").setScale(2);
		BigDecimal balance = new BigDecimal("0").setScale(2);
		BigDecimal dailydue = new BigDecimal("0").setScale(2);
		BigDecimal interest = new BigDecimal("0").setScale(2);
		BigDecimal penalty = new BigDecimal("0").setScale(2);
		BigDecimal others = new BigDecimal("0").setScale(2);
		int term = 0;
		String duedate = "";
		String homeaddress = "";
		String collectionaddress = "";
		
		if(result != null && result.getCount() > 0) {
			acctname = result.getString(result.getColumnIndex("acctname"));
			appno = result.getString(result.getColumnIndex("appno"));
			String amt = result.getString(result.getColumnIndex("amountdue"));
			amountdue = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("loanamount"));
			loanamount = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("balance"));
			balance = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("dailydue"));
			dailydue = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("overpaymentamount"));
			overpayment = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("interest"));
			interest = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("penalty"));
			penalty = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("others"));
			others = new BigDecimal(amt).setScale(2);
			term = result.getInt(result.getColumnIndex("term"));
			refno = result.getString(result.getColumnIndex("refno"));
			homeaddress = result.getString(result.getColumnIndex("homeaddress"));
			collectionaddress = result.getString(result.getColumnIndex("collectionaddress"));
			Calendar c = Calendar.getInstance();
			try {
				Object date = result.getString(result.getColumnIndex("duedate"));
				if (!(date instanceof Date)) date = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				c.setTime((Date) date);
			}
			catch (Exception e) { 
				e.printStackTrace();
				Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); 
			}
			duedate = df.format(c.getTime());
			totaldays = amountdue.divide(dailydue, 2, BigDecimal.ROUND_HALF_UP).intValue();
			if (paymenttype.equals(null) || paymenttype.equals("")) paymenttype = result.getString(result.getColumnIndex("paymentmethod"));
		}
		
		((TextView) findViewById(R.id.tv_info_acctname)).setText(acctname);
		((TextView) findViewById(R.id.tv_info_appno)).setText(appno);
		((TextView) findViewById(R.id.tv_info_loanamount)).setText(formatValue(loanamount));
		((TextView) findViewById(R.id.tv_info_balance)).setText(formatValue(balance));
		((TextView) findViewById(R.id.tv_info_dailydue)).setText(formatValue(dailydue));
		((TextView) findViewById(R.id.tv_info_amountdue)).setText(formatValue(amountdue));
		((TextView) findViewById(R.id.tv_info_overpayment)).setText(formatValue(overpayment));
		((TextView) findViewById(R.id.tv_info_duedate)).setText(duedate);
		((TextView) findViewById(R.id.tv_info_homeaddress)).setText(homeaddress);
		((TextView) findViewById(R.id.tv_info_collectionaddress)).setText(collectionaddress);
		((TextView) findViewById(R.id.tv_info_interest)).setText(formatValue(interest));
		((TextView) findViewById(R.id.tv_info_penalty)).setText(formatValue(penalty));
		((TextView) findViewById(R.id.tv_info_others)).setText(formatValue(others));
		((TextView) findViewById(R.id.tv_info_term)).setText(term+" days");
		
		db.openDb();
		payment = db.getBorrowerPayments(loanappid);
		remarks = db.getRemarks(loanappid);
		notes = db.getNotes(loanappid);
		db.closeDb();
		
		rl_remarks.setVisibility(View.GONE);
		if (!remarks.equals("")) {
			TextView tv_info_remarks = (TextView) findViewById(R.id.tv_info_remarks);
			tv_info_remarks.setText(remarks);
			rl_remarks.setVisibility(View.VISIBLE);
		}
		
		//ListView lv_info_payments = (ListView) findViewById(R.id.lv_info_payments);
		LinearLayout ll_info_payments = (LinearLayout) findViewById(R.id.ll_info_payments);
		ll_info_payments.removeAllViewsInLayout();
		rl_payment.setVisibility(View.GONE);

		if(payment != null && payment.getCount() > 0) {
			View child = null;
			String paymenttype = "";
			BigDecimal paymentamount = new BigDecimal("0").setScale(2);
			rl_payment.setVisibility(View.VISIBLE);
			do {
				child = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_payment, null);
				((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(payment.getString(payment.getColumnIndex("refno")));
				((TextView) child.findViewById(R.id.tv_info_todate)).setText(payment.getString(payment.getColumnIndex("txndate")));
				String type = payment.getString(payment.getColumnIndex("paymenttype"));
				if (type.equals("schedule")) paymenttype = "Schedule/Advance";
				else paymenttype = "Overpayment";
				((TextView) child.findViewById(R.id.tv_info_paymenttype)).setText(paymenttype);
				String amt = payment.getDouble(payment.getColumnIndex("paymentamount"))+"";
				paymentamount = new BigDecimal(amt).setScale(2);
				((TextView) child.findViewById(R.id.tv_info_paymentamount)).setText(formatValue(paymentamount));
				ll_info_payments.addView(child);
				//list.add(pp);
			} while(payment.moveToNext());
		}
		
		rl_notes.setVisibility(View.GONE);
		if (notes != null && notes.getCount() > 0) {
			rl_notes.setVisibility(View.VISIBLE);
			notes.moveToFirst();
			LinearLayout ll_notes = (LinearLayout) findViewById(R.id.ll_info_notes);
			ll_notes.removeAllViewsInLayout();
			View child = null;
			do {
				child = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_note, null);
				((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(df.format(notes.getString(notes.getColumnIndex("fromdate"))));
				((TextView) child.findViewById(R.id.tv_info_todate)).setText(df.format(notes.getString(notes.getColumnIndex("todate"))));
				((TextView) child.findViewById(R.id.tv_info_remarks)).setText(notes.getString(notes.getColumnIndex("remarks")));
				ll_notes.addView(child);
			} while(notes.moveToNext());
		}
	}
	
	private String formatValue(Object number) {
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		StringBuffer sb = new StringBuffer();
		FieldPosition fp = new FieldPosition(0);
		df.format(number, sb, fp);
		return sb.toString();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		/*if(payment != null && payment.getCount() > 0) 
			getMenuInflater().inflate(R.menu.empty, menu);
		else*/
		getMenuInflater().inflate(R.menu.payment, menu);
		MenuItem menuItem = null;
		if (remarks.equals(null) || remarks.equals("")) {
			menuItem = menu.findItem(R.id.payment_editremarks);
		} else {
			menuItem = menu.findItem(R.id.payment_addpayment);
		}
		menuItem.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(context, Payment.class);
		intent.putExtra("loanappid", loanappid);
		intent.putExtra("detailid", detailid);
		intent.putExtra("routecode", routecode);
		intent.putExtra("totaldays", totaldays);
		intent.putExtra("isfirstbill", isfirstbill);
		if (payment.getCount() > 0) refno += (payment.getCount()+1);
		switch(item.getItemId()) {
			case R.id.payment_addpayment:
					intent.putExtra("refno", refno);
					intent.putExtra("paymenttype", paymenttype);
					intent.putExtra("overpayment", overpayment.toString());
					startActivity(intent);
					break;
			case R.id.payment_addremarks:
					showRemarksDialog();
					break;
			case R.id.payment_editremarks:
					showRemarksDialog();
					break;
			case R.id.payment_addnotes:
					showNotesDialog();
					break;
		}
		return true;
	}

	public void showRemarksDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Remarks");
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remarks, null);
		builder.setView(view);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				/*EditText et_remarks = (EditText) dialog.findViewById(R.id.remarks_text);
				if (!et_remarks.getText().equals("")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("loanappid", loanappid);
					map.put("remarks", et_remarks.getText());
					if (!db.isOpen) db.openDb();
					if (remarks.equals("")) db.insertRemarks(map);
					else if (!remarks.equals("")) db.updateRemarks(map);
					if (db.isOpen) db.closeDb();
					if (rl_remarks.getVisibility() == View.GONE) rl_remarks.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.tv_info_remarks)).setText(et_remarks.getText());
					remarks = et_remarks.getText().toString();
				}*/
			}
		});	
		
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new RemarksValidationListener(dialog));
		if (!remarks.equals("")) {
			((EditText) dialog.findViewById(R.id.remarks_text)).setText(remarks);
		}
	}
	
	private void showNotesDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Notes");
		View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_notes, null);
		builder.setView(view);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new NotesDateValidationListener(dialog, "create"));
		final Calendar c = Calendar.getInstance();
		if (!db.isOpen) db.openDb();
		try {
			c.setTime(db.getServerDate());
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		if (db.isOpen) db.closeDb();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH);
		final int day = c.get(Calendar.DAY_OF_MONTH);
		setFromDateView(df.format(c.getTime()));
		setToDateView(df.format(c.getTime()));
		((Button) dialog.findViewById(R.id.notes_btn_changefromdate)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							//System.out.println("year = "+year+" month = "+monthOfYear+" day = "+dayOfMonth);
							Calendar cc = Calendar.getInstance();
							cc.set(year, monthOfYear, dayOfMonth);
							if (cc.getTime().compareTo(c.getTime()) < 0) {
								showLongError("From date must not be less than billing date: "+df.format(c.getTime()));
							} else {
								setFromDateView(df.format(cc.getTime()));
							}
						}
					}, year, month, day);
				dpd.show();
			}
		});
		((Button) dialog.findViewById(R.id.notes_btn_changetodate)).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd  = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							//System.out.println("year = "+year+" month = "+monthOfYear+" day = "+dayOfMonth);
							Calendar c1 = Calendar.getInstance();
							c1.setTime(getFromDateView());
							Calendar c2 = Calendar.getInstance();
							c2.set(year, monthOfYear, dayOfMonth);
							if (c2.compareTo(c1) < 0) {
								showLongError("To date must not be less than from date: "+df.format(c1.getTime()));
							} else {
								setToDateView(df.format(c2.getTime()));
							}
						}
					}, year, month, day);
				dpd.show();
			}
		});
	}
	
	private void setFromDateView(String str) {
		((TextView) dialog.findViewById(R.id.notes_fromdate)).setText(str);
	}
	
	private void setToDateView(String str) {
		((TextView) dialog.findViewById(R.id.notes_todate)).setText(str);
	}
	
	private Date getFromDateView() {
		String date = ((TextView) dialog.findViewById(R.id.notes_fromdate)).getText().toString();	
		try {
			return df.parse(date);
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		return null;
	}
	
	private Date getToDateView() {
		String date = ((TextView) dialog.findViewById(R.id.notes_todate)).getText().toString();	
		try {
			return df.parse(date);
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		return null;
	}
	
	private void showLongError(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	private void showShortError(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	private void addNote(Map<String, Object> params) {
		LinearLayout ll_notes = (LinearLayout) findViewById(R.id.ll_info_notes);
		View child = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_note, null);
		((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(df.format(params.get("fromdate").toString()));
		((TextView) child.findViewById(R.id.tv_info_todate)).setText(df.format(params.get("todate").toString()));
		((TextView) child.findViewById(R.id.tv_info_remarks)).setText(params.get("remarks").toString());
		ll_notes.addView(child);
	}
	
	class RemarksValidationListener implements View.OnClickListener {
		private final Dialog dialog;
		public RemarksValidationListener(Dialog dialog) {
			this.dialog = dialog;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EditText et_remarks = (EditText) v.findViewById(R.id.remarks_text);
			if (et_remarks.equals("")) {
				showShortError("Remarks is required.");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loanappid", loanappid);
				map.put("remarks", et_remarks.getText());
				if (!db.isOpen) db.openDb();
				if (remarks.equals("")) db.insertRemarks(map);
				else if (!remarks.equals("")) db.updateRemarks(map);
				if (db.isOpen) db.closeDb();
				if (rl_remarks.getVisibility() == View.GONE) rl_remarks.setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.tv_info_remarks)).setText(et_remarks.getText());
				remarks = et_remarks.getText().toString();
				dialog.dismiss();
			}
		}
	}
	
	class NotesDateValidationListener implements View.OnClickListener {
		private final Dialog dialog;
		private final String mode;
		public NotesDateValidationListener(Dialog dialog, String mode) {
			this.dialog = dialog;
			this.mode = mode;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("passing 1");
			Calendar c = Calendar.getInstance();
			c.setTime(getFromDateView());
			System.out.println("passing 2");
			Calendar c2 = Calendar.getInstance();
			c2.setTime(getToDateView());
			System.out.println("passing 3");
			String remarks = ((TextView) dialog.findViewById(R.id.notes_text)).getText().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (c2.compareTo(c) < 0) {
				showLongError("To date must not be less than from date: "+df.format(c.getTime()));;
			} else if (remarks.equals("")) {
				showShortError("Remarks is required.");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("objid", "NT"+UUID.randomUUID().toString());
				map.put("loanappid", loanappid);
				map.put("fromdate", sdf.format(c.getTime()));
				map.put("todate", sdf.format(c2.getTime()));
				map.put("remarks", remarks);
				if (!db.isOpen) db.openDb();
				if (mode.equals("create")) db.insertNotes(map);
				else if (!mode.equals("create")) db.updateNotes(map);
				if (db.isOpen) db.closeDb();
				addNote(map);
				dialog.dismiss();
			}
		}
		
	}
}
