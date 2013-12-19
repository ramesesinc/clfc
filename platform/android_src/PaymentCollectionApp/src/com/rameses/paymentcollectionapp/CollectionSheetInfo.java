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

import com.rameses.service.ServiceProxy;

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
	private String txndate = "";
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
	private ServiceProxy postingProxy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheetinfo);
		setTitle("Collection Sheet Info");
		db = new MySQLiteHelper(context);
		
		Intent intent = getIntent();
		loanappid = intent.getStringExtra("loanappid");
		detailid = intent.getStringExtra("detailid");
		routecode = intent.getStringExtra("routecode");
		paymenttype = intent.getStringExtra("paymenttype");
		isfirstbill = intent.getIntExtra("isfirstbill", 0);
		
		postingProxy  = new ServiceHelper(context).createServiceProxy("DevicePostingService");
		rl_general = (RelativeLayout) findViewById(R.id.layout_info_general);
		rl_payment = (RelativeLayout) findViewById(R.id.layout_info_payment);
		rl_remarks = (RelativeLayout) findViewById(R.id.layout_info_remarks);
		rl_notes = (RelativeLayout) findViewById(R.id.layout_info_notes);
		
		if (!db.isOpen) db.openDb();
		try {
			txndate = new SimpleDateFormat("yyyy-MM-dd").format(db.getServerDate());
		} catch (Exception e) { ApplicationUtil.showShortMsg(context, "Error: ParseException"); }
		if (db.isOpen) db.closeDb();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (!db.isOpen) db.openDb();
		Cursor result = db.getCollectionsheetByLoanappid(loanappid);
		if (db.isOpen) db.closeDb();
				
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
			} catch (Exception e) { ApplicationUtil.showShortMsg(context, "Error: ParseException"); }
			/*catch (Exception e) { 
				e.printStackTrace();
				Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); 
			}*/
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
		payment = db.getPaymentsByAppid(loanappid);
		remarks = db.getRemarksByAppid(loanappid);
		notes = db.getNotesByAppid(loanappid);
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
							try {
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("detailid", detailid);
								postingProxy.invoke("removeRemarks", new Object[]{params});
							} catch (Exception e) {}
							finally{
								db.removeRemarksByAppid(loanappid);								
							}
							if (db.isOpen) db.closeDb();
							remarks = null;
							rl_remarks.setVisibility(View.GONE);
							ApplicationUtil.showShortMsg(context, "Successfully removed remarks.");
						}
					}
				};
				ApplicationUtil.showOptionDialog(context, items, listener);
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
				child.setTag(R.id.paymentid, payment.getString(payment.getColumnIndex("objid")));
				addPaymentProperties(child);
				if (!db.isOpen) db.openDb();
				Cursor vp = db.getVoidPaymentByPaymentidAndAppid(payment.getString(payment.getColumnIndex("objid")), loanappid);
				if (db.isOpen) db.closeDb();
				
				if (vp != null && vp.getCount() > 0) {
					vp.moveToFirst();
					String state = vp.getString(vp.getColumnIndex("state"));
					View overlay = null;
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
					layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
					System.out.println("state = "+state);
					if (state.equals("PENDING")) {
						overlay = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.overlay_void_text, null);
						((TextView) overlay).setTextColor(getResources().getColor(R.color.red));
						((TextView) overlay).setText("VOID REQUEST PENDING");
						overlay.setLayoutParams(layoutParams);
						((RelativeLayout) child).addView(overlay);
						child.setOnClickListener(null);
						child.setOnLongClickListener(null);
						child.setClickable(false);
					} else if(state.equals("APPROVED")) {
						overlay = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.overlay_void_text, null);
						((TextView) overlay).setTextColor(getResources().getColor(R.color.green));
						((TextView) overlay).setText("VOID APPROVED");
						overlay.setLayoutParams(layoutParams);
						((RelativeLayout) child).addView(overlay);
						addApprovedVoidPaymentProperies(child);
					}
				}
				
				
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
	
	private void addPaymentProperties(View child) {
		child.setClickable(true);
		child.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.drawable.list_selector_background);
			}
		});
		child.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				final View view = v;
				v.setBackgroundResource(android.R.drawable.list_selector_background);
				CharSequence[] items = {"Void"};
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int which) {
						// TODO Auto-generated method stub
						showVoidDialog(view);
					}
				};
				ApplicationUtil.showOptionDialog(context, items, listener);
				return false;
			}
		});
	}
	
	private void addApprovedVoidPaymentProperies(View child) {
		child.setClickable(true);
		child.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.drawable.list_selector_background);
			}
		});
		child.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				final View view = v;
				v.setBackgroundResource(android.R.drawable.list_selector_background);
				CharSequence[] items = {"Print"};
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int which) {
						// TODO Auto-generated method stub
						//showVoidDialog(view);
					}
				};
				ApplicationUtil.showOptionDialog(context, items, listener);
				return false;
			}
		});
	}
	
	private void showVoidDialog(View child) {
		final View payment = child;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Reason for void");
		View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remarks, null);
		builder.setView(view);
		builder.setPositiveButton("Submit", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("objid", "VOID"+UUID.randomUUID());
				params.put("paymentid", payment.getTag(R.id.paymentid));
				params.put("loanappid", loanappid);
				if (!db.isOpen) db.openDb();
				params.put("collectorid", db.getCollectorid());
				params.put("reason", ((EditText) dialog.findViewById(R.id.remarks_text)).getText().toString());
				if (db.isOpen) db.closeDb();
				try {
					postingProxy.invoke("voidPayment", new Object[]{params});
				} catch (Exception e) {}
				finally {
					params.put("state", "PENDING");
					if (!db.isOpen) db.openDb();
					db.insertVoidPayment(params);
					if (db.isOpen) db.closeDb();
				}
			}
		});
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
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
									System.out.println("noteid = "+noteid);
									Map<String, Object> params = new HashMap<String, Object>();
									params.put("noteid", noteid);
									try {
										postingProxy.invoke("removeNote", new Object[]{params});
									} catch (Exception e) {}
									finally {
										if (!db.isOpen) db.openDb();
										db.removeNoteById(noteid);
										if (db.isOpen) db.closeDb();
										removeNoteAt(idx);
										ApplicationUtil.showShortMsg(context, "Successfully removed note.");
									}
								}
							};
							ApplicationUtil.showConfirmationDialog(context, "You are about to remove this note. Continue?", positivelistener, null);
						}
					}
				};
				ApplicationUtil.showOptionDialog(context, items, listener);
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
					if (!db.isOpen) db.openDb();
					Cursor voidPayments = db.getPendingVoidPaymentsByAppid(loanappid);
					if (db.isOpen) db.closeDb();
					if (voidPayments != null && voidPayments.getCount() > 0) {
						ApplicationUtil.showLongMsg(context, "Cannot add payment. No confirmation for void requested at the moment.");
					} else {
						intent.putExtra("refno", refno);
						intent.putExtra("paymenttype", paymenttype);
						intent.putExtra("overpayment", overpayment.toString());
						startActivity(intent);
					}
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
								ApplicationUtil.showLongMsg(context, "From date must not be less than billing date: "+df.format(c.getTime()));
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
								ApplicationUtil.showLongMsg(context, "To date must not be less than from date: "+df.format(c1.getTime()));
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
				ApplicationUtil.showShortMsg(context, "Remarks is required.");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loanappid", loanappid);
				map.put("remarks", et_remarks);
				if (!db.isOpen) db.openDb();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("sessionid", db.getSessionid());
				params.put("routecode", routecode);
				params.put("txndate", txndate);
				params.put("remarks", et_remarks);
				
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				collectionsheet.put("loanappid", loanappid);
				collectionsheet.put("detailid", detailid);
				params.put("collectionsheet", collectionsheet);
				try {
					postingProxy.invoke("updateRemarks", new Object[]{params});
				} catch (Exception e) {}
				finally {
					if (mode.equals("create")) {
						db.insertRemarks(map);
						rl_remarks.setVisibility(View.VISIBLE);					
						ApplicationUtil.showShortMsg(context, "Successfully added remarks.");
					} else if (!mode.equals("create")) {
						db.updateRemarks(map); 
						ApplicationUtil.showShortMsg(context, "Successfully updated remark.");
					}
				}				
				remarks = db.getRemarksByAppid(loanappid);
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
				ApplicationUtil.showLongMsg(context, "To date must not be less than from date: "+df.format(c.getTime()));;
			} else if (notes_remarks.trim().equals("")) {
				ApplicationUtil.showShortMsg(context, "Remarks is required.");
			} else {
				Map<String, Object> note = new HashMap<String, Object>();
				note.put("objid", "NT"+UUID.randomUUID().toString());
				note.put("loanappid", loanappid);
				note.put("fromdate", sdf.format(c.getTime()));
				note.put("todate", sdf.format(c2.getTime()));
				note.put("remarks", notes_remarks);
				if (!db.isOpen) db.openDb();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("sessionid", db.getSessionid());
				params.put("txndate", txndate);
				params.put("routecode", routecode);
				Map<String, Object> collectionsheet = new HashMap<String, Object>();
				collectionsheet.put("loanappid", loanappid);
				collectionsheet.put("detailid", detailid);
				params.put("collectionsheet", collectionsheet);
				params.put("note", note);
				if (mode.equals("create")) {
					try {
						postingProxy.invoke("postNote", new Object[]{params});
					} catch(Exception e) {}
					finally {
						db.insertNotes(note);
						addNote(note);
						ApplicationUtil.showShortMsg(context, "Successfully added note.");	
					}
				} else if (!mode.equals("create")) {
					if (view != null) note.put("objid", view.getTag(R.id.noteid).toString());
					params.put("note", note);
					try {
						postingProxy.invoke("updateNote", new Object[]{params});
					} catch (Exception e) {}
					finally {
						db.updateNotes(note);
						updateNote(note, view);
						ApplicationUtil.showShortMsg(context, "Successfully updated note.");
					}
				}
				if (db.isOpen) db.closeDb();
				dialog.dismiss();
			}
		}
		
	}
}
