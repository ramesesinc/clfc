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
import android.app.ActionBar.LayoutParams;
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
import android.view.MotionEvent;
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
	private Cursor remarks;
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
		RelativeLayout remarks_child = (RelativeLayout) findViewById(R.id.rl_info_remarks);
				remarks_child.setClickable(true);
		remarks_child.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.drawable.list_selector_background);
			}
		});
		remarks_child.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				final View view = v;
				v.setBackgroundResource(android.R.drawable.list_selector_background);
				CharSequence[] items = {"Edit Remarks", "Remove Remarks"};
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							showRemarksDialog("edit");
						} else if (which == 1) {
							if (!db.isOpen) db.openDb();
							db.removeRemarksByAppid(loanappid);
							if (db.isOpen) db.closeDb();
							remarks = null;
							rl_remarks.setVisibility(View.GONE);
							showShortError("Successfully removed remarks.");
						}
					}
				};
				showOptionDialog(items, listener);
				return false;
			}
		});
		if (remarks != null && remarks.getCount() > 0) {
			rl_remarks.setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.tv_info_remarks)).setText(remarks.getString(remarks.getColumnIndex("remarks")));
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
		LinearLayout ll_notes = (LinearLayout) findViewById(R.id.ll_info_notes);
		if (notes != null && notes.getCount() > 0) {
			rl_notes.setVisibility(View.VISIBLE);
			notes.moveToFirst();
			ll_notes.removeAllViewsInLayout();
			View child = null;
			Date date = null;
			String str = "";
			int idx = 0;
			do {
				child = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_note, null);
				addNoteProperties(child);
								str = "";
				date = parseDate(notes.getString(notes.getColumnIndex("fromdate")));
				if (date != null) str = df.format(date);
				((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(str);
				str = "";
				date = parseDate(notes.getString(notes.getColumnIndex("todate")));
				if (date != null) str = df.format(date);
				((TextView) child.findViewById(R.id.tv_info_todate)).setText(str);
				((TextView) child.findViewById(R.id.tv_info_remarks)).setText(notes.getString(notes.getColumnIndex("remarks")));
				child.setTag(R.id.noteid, notes.getString(notes.getColumnIndex("objid")));
				child.setTag(R.id.idx, idx);
				idx++;
				ll_notes.addView(child);
			} while(notes.moveToNext());
		}
	}	
	
	private void showConfirmationDialog(String msg, DialogInterface.OnClickListener positivelistener, DialogInterface.OnClickListener negativelistener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Confirmation");
		builder.setMessage(msg);
		builder.setPositiveButton("Yes", positivelistener);
		builder.setNegativeButton("No", negativelistener);
		AlertDialog d = builder.create();
		d.show();
	}
	
	private void showOptionDialog(CharSequence[] items, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Options");
		builder.setItems(items, listener);
		AlertDialog d = builder.create();
		d.show();
	}
	
	private void addNoteProperties(View child) {
		child.setClickable(true);
		child.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//RelativeLayout rl = (RelativeLayout) v;
				v.setBackgroundResource(android.R.drawable.list_selector_background);
			}
		});				
		child.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//showNotesDialog("edit", v);
				final View view = v;
				v.setBackgroundResource(android.R.drawable.list_selector_background);
				CharSequence[] items = {"Edit Note", "Remove Note"};
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							showNotesDialog("edit", view);
						} else if (which == 1) {
							DialogInterface.OnClickListener positivelistener = new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface di, int which) {
									// TODO Auto-generated method stub

									String noteid = view.getTag(R.id.noteid).toString();
									int idx = Integer.parseInt(view.getTag(R.id.idx).toString());
									if (!db.isOpen) db.openDb();
									db.removeNoteById(noteid);
									if (db.isOpen) db.closeDb();
									removeNoteAt(idx);
									showShortError("Successfully removed note.");
								}
							};
							showConfirmationDialog("You are about to remove this note. Continue?", positivelistener, null);
						}
					}
				};
				showOptionDialog(items, listener);
				return false;
			}
		});
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
		if (remarks != null && remarks.getCount() > 0) {
			((MenuItem) menu.findItem(R.id.payment_addremarks)).setVisible(false);
		}
		//menuItem.setVisible(false);*/
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
					showRemarksDialog("create");
					break;
			case R.id.payment_addnotes:
					showNotesDialog("create", null);
					break;
		}
		return true;
	}

	public void showRemarksDialog(String mode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Remarks");
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remarks, null);
		builder.setView(view);
		builder.setPositiveButton("Ok", null);		
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		if (!mode.equals("create")) {
			((EditText) dialog.findViewById(R.id.remarks_text)).setText(remarks.getString(remarks.getColumnIndex("remarks")));
		}
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new RemarksValidationListener(dialog, mode));
	}
	
	private void showNotesDialog(String mode, View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Notes");
		View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_notes, null);
		builder.setView(view);
		builder.setPositiveButton("Ok", null);
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new NotesDateValidationListener(dialog, mode, v));
		final Calendar c = Calendar.getInstance();
		if (!db.isOpen) db.openDb();
		try {
			c.setTime(db.getServerDate());
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		if (db.isOpen) db.closeDb();
		setFromDateValue(df.format(c.getTime()));
		setToDateValue(df.format(c.getTime()));
		if (v != null) {
			Calendar cc = Calendar.getInstance();
			String date = ((TextView) v.findViewById(R.id.tv_info_fromdate)).getText().toString();
			cc.setTime(parseDateFromView(date));
			setFromDateValue(df.format(cc.getTime()));
			date = ((TextView) v.findViewById(R.id.tv_info_todate)).getText().toString();
			cc.setTime(parseDateFromView(date));
			setToDateValue(df.format(cc.getTime()));
			((EditText) dialog.findViewById(R.id.notes_text)).setText(((TextView) v.findViewById(R.id.tv_info_remarks)).getText());
			
		}
		Button btn_changefromdate = (Button) dialog.findViewById(R.id.notes_btn_changefromdate);
		btn_changefromdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				Date date = getFromDateValue();
				if (date != null) {
					Calendar fromdate = Calendar.getInstance();
					fromdate.setTime(date);
					year = fromdate.get(Calendar.YEAR);
					month = fromdate.get(Calendar.MONTH);
					day = fromdate.get(Calendar.DAY_OF_MONTH);
				}
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
								setFromDateValue(df.format(cc.getTime()));
							}
						}
					}, year, month, day);
				dpd.show();
			}
		});
		Button btn_changetodate = (Button) dialog.findViewById(R.id.notes_btn_changetodate);
		btn_changetodate.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				Date date = getToDateValue();
				if (date != null) {
					Calendar todate = Calendar.getInstance();
					todate.setTime(date);
					year = todate.get(Calendar.YEAR);
					month = todate.get(Calendar.MONTH);
					day = todate.get(Calendar.DAY_OF_MONTH);
				}
				DatePickerDialog dpd  = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							//System.out.println("year = "+year+" month = "+monthOfYear+" day = "+dayOfMonth);
							Calendar c1 = Calendar.getInstance();
							c1.setTime(getFromDateValue());
							Calendar c2 = Calendar.getInstance();
							c2.set(year, monthOfYear, dayOfMonth);
							if (c2.compareTo(c1) < 0) {
								showLongError("To date must not be less than from date: "+df.format(c1.getTime()));
							} else {
								setToDateValue(df.format(c2.getTime()));
							}
						}
					}, year, month, day);
				dpd.show();
			}
		});
	}
	
	private void setFromDateValue(String str) {
		((TextView) dialog.findViewById(R.id.notes_fromdate)).setText(str);
	}
	
	private void setToDateValue(String str) {
		((TextView) dialog.findViewById(R.id.notes_todate)).setText(str);
	}
	
	private Date getFromDateValue() {
		String date = ((TextView) dialog.findViewById(R.id.notes_fromdate)).getText().toString();
		return parseDateFromView(date);
	}
	
	private Date getToDateValue() {
		String date = ((TextView) dialog.findViewById(R.id.notes_todate)).getText().toString();
		return parseDateFromView(date);
	}
	
	private Date parseDateFromView(String date) {
		try {
			return df.parse(date);
		} catch (Exception e) { return null; }
	}
	
	private Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e) { return null; }
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
		Date date = parseDate(params.get("fromdate").toString());
		((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(df.format(date));
		date = parseDate(params.get("todate").toString());
		((TextView) child.findViewById(R.id.tv_info_todate)).setText(df.format(date));
		((TextView) child.findViewById(R.id.tv_info_remarks)).setText(params.get("remarks").toString());
		addNoteProperties(child);
		child.setTag(R.id.noteid, params.get("objid").toString());
		child.setTag(R.id.idx, ll_notes.getChildCount());
		ll_notes.addView(child);
		if (rl_notes.getVisibility() == View.GONE && ll_notes.getChildCount() > 0) rl_notes.setVisibility(View.VISIBLE);
	}
	
	private void updateNote(Map<String, Object> params, View view) {
		LinearLayout ll_notes = (LinearLayout) findViewById(R.id.ll_info_notes);
		int idx = Integer.parseInt(view.getTag(R.id.idx).toString());
		View child = ll_notes.getChildAt(idx);
		if (child != null) {
			ll_notes.removeViewAt(idx);
			Date date = parseDate(params.get("fromdate").toString());
			((TextView) child.findViewById(R.id.tv_info_fromdate)).setText(df.format(date));
			date = parseDate(params.get("todate").toString());
			((TextView) child.findViewById(R.id.tv_info_todate)).setText(df.format(date));
			((TextView) child.findViewById(R.id.tv_info_remarks)).setTag(params.get("remarks").toString());
			ll_notes.addView(child, idx);
		}
	}
	
	private void removeNoteAt(int index) {
		LinearLayout ll_notes = (LinearLayout) findViewById(R.id.ll_info_notes);
		ll_notes.removeViewAt(index);
		if (ll_notes.getChildCount() == 0) rl_notes.setVisibility(View.GONE);
	}
	
	class RemarksValidationListener implements View.OnClickListener {
		private final Dialog dialog;
		private final String mode;
		public RemarksValidationListener(Dialog dialog, String mode) {
			this.dialog = dialog;
			this.mode = mode;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String et_remarks = ((EditText) dialog.findViewById(R.id.remarks_text)).getText().toString();
			if (et_remarks.trim().equals("")) {
				showShortError("Remarks is required.");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loanappid", loanappid);
				map.put("remarks", et_remarks);
				if (!db.isOpen) db.openDb();
				if (mode.equals("create")) {
					db.insertRemarks(map);
					rl_remarks.setVisibility(View.VISIBLE);					
					showShortError("Successfully added remarks.");
				} else if (!mode.equals("create")) {
					db.updateRemarks(map); 
					showShortError("Successfully updated remark.");
				}
				remarks = db.getRemarks(loanappid);
				if (db.isOpen) db.closeDb();
				((TextView) findViewById(R.id.tv_info_remarks)).setText(map.get("remarks").toString());
				dialog.dismiss();
			}
		}
	}
	
	class NotesDateValidationListener implements View.OnClickListener {
		private final Dialog dialog;
		private final String mode;
		private final View view;
		public NotesDateValidationListener(Dialog dialog, String mode, View view) {
			this.dialog = dialog;
			this.mode = mode;
			this.view = view;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Calendar c = Calendar.getInstance();
			c.setTime(getFromDateValue());
			Calendar c2 = Calendar.getInstance();
			c2.setTime(getToDateValue());
			String notes_remarks = ((TextView) dialog.findViewById(R.id.notes_text)).getText().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (c2.compareTo(c) < 0) {
				showLongError("To date must not be less than from date: "+df.format(c.getTime()));;
			} else if (notes_remarks.trim().equals("")) {
				showShortError("Remarks is required.");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("objid", "NT"+UUID.randomUUID().toString());
				map.put("loanappid", loanappid);
				map.put("fromdate", sdf.format(c.getTime()));
				map.put("todate", sdf.format(c2.getTime()));
				map.put("remarks", notes_remarks);
				if (!db.isOpen) db.openDb();
				if (mode.equals("create")) {
					db.insertNotes(map);
					addNote(map);
					showShortError("Successfully added note.");
				} else if (!mode.equals("create")) {
					if (view != null) map.put("objid", view.getTag(R.id.noteid).toString());
					db.updateNotes(map);
					updateNote(map, view);
					showShortError("Successfully updated note.");
				}
				if (db.isOpen) db.closeDb();
				dialog.dismiss();
			}
		}
		
	}
}
