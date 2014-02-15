package com.rameses.clfc.android.main;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rameses.clfc.android.ApplicationUtil;
import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.clfc.android.db.DBSystemService;
import com.rameses.clfc.android.db.DBVoidService;
import com.rameses.clfc.android.services.LoanPostingService;
import com.rameses.client.android.NetworkLocationProvider;
import com.rameses.client.android.Platform;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.SQLTransaction;

public class CollectionSheetInfoActivity extends ControlActivity {
	private String loanappid = "";
	private String detailid = "";
	private BigDecimal overpayment = new BigDecimal("0").setScale(2);
	private BigDecimal dailydue = new BigDecimal("0").setScale(2);
	private String routecode = "";
	private String refno = "";
	private String paymenttype = "";
	private int totaldays = 0;
	private int isfirstbill = 0;
	private RelativeLayout rl_general = null;
	private RelativeLayout rl_payment = null;
	private RelativeLayout rl_remarks = null;
	private RelativeLayout rl_notes = null;
	private AlertDialog dialog = null;
	private SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
	private LayoutInflater inflater;
	private Map remarks;
	private ProgressDialog progressDialog;

	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Collection Sheet Info");

		RelativeLayout rl_container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_collectionsheetinfo, rl_container, true);

		Intent intent = getIntent();
		loanappid = intent.getStringExtra("loanappid");
		detailid = intent.getStringExtra("detailid");
		routecode = intent.getStringExtra("routecode");
		paymenttype = intent.getStringExtra("paymenttype");
		isfirstbill = intent.getIntExtra("isfirstbill", 0);

		rl_general = (RelativeLayout) findViewById(R.id.layout_info_general);
		rl_payment = (RelativeLayout) findViewById(R.id.layout_info_payment);
		rl_remarks = (RelativeLayout) findViewById(R.id.layout_info_remarks);
		rl_notes = (RelativeLayout) findViewById(R.id.layout_info_notes);

		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
	}

	protected void onStartProcess() {
		super.onStartProcess();
		SQLTransaction txn = new SQLTransaction("clfc.db");
		try {
			txn.beginTransaction();
			onStartPocessImpl(txn);
			txn.commit();
		}  catch (Throwable t) {
			t.printStackTrace();
			UIDialog.showMessage(t, CollectionSheetInfoActivity.this); 
		} finally {
			txn.endTransaction();
		}
	}

	private void onStartPocessImpl(SQLTransaction txn) throws Exception {
		DBCollectionSheet dbCs = new DBCollectionSheet();
		dbCs.setDBContext(txn.getContext());
		
		Map collectionSheet = new HashMap();
		try {
			collectionSheet = dbCs.findCollectionSheetByLoanappid(loanappid);
		} catch (Exception e) {;}
		
		String acctname = "";
		String appno = "";
		BigDecimal amountdue = new BigDecimal("0").setScale(2);
		BigDecimal loanamount = new BigDecimal("0").setScale(2);
		BigDecimal balance = new BigDecimal("0").setScale(2);
		BigDecimal interest = new BigDecimal("0").setScale(2);
		BigDecimal penalty = new BigDecimal("0").setScale(2);
		BigDecimal others = new BigDecimal("0").setScale(2);
		int term = 0;
		String duedate = "";
		String homeaddress = "";
		String collectionaddress = "";
		
		if (collectionSheet != null || !collectionSheet.isEmpty()) {
			System.out.println("collection sheet -> "+collectionSheet);
			acctname = collectionSheet.get("acctname").toString();
			appno = collectionSheet.get("appno").toString();
			amountdue = new BigDecimal(collectionSheet.get("amountdue").toString());
			loanamount = new BigDecimal(collectionSheet.get("loanamount").toString());
			balance = new BigDecimal(collectionSheet.get("balance").toString());
			dailydue = new BigDecimal(collectionSheet.get("dailydue").toString());
			overpayment = new BigDecimal(collectionSheet.get("overpaymentamount").toString());
			interest = new BigDecimal(collectionSheet.get("interest").toString());
			penalty = new BigDecimal(collectionSheet.get("penalty").toString());
			others = new BigDecimal(collectionSheet.get("others").toString());
			term = Integer.parseInt(collectionSheet.get("term").toString());
			refno = collectionSheet.get("refno").toString();
			homeaddress = collectionSheet.get("homeaddress").toString();
			collectionaddress = collectionSheet.get("collectionaddress").toString();
			try {
				duedate = df.format(new SimpleDateFormat("yyyy-MM-dd").parse(collectionSheet.get("duedate").toString()));	
			} catch (Exception e) {;}
			totaldays = amountdue.divide(dailydue, 2, BigDecimal.ROUND_HALF_UP).intValue();
			if (paymenttype == null || paymenttype.equals("")) {
				paymenttype = collectionSheet.get("paymentmethod").toString();
			}
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
		
		DBPaymentService dbPs = new DBPaymentService();
		dbPs.setDBContext(txn.getContext());
		
		ArrayList payments = new ArrayList();
		try {
			payments = (ArrayList) dbPs.getPaymentsByLoanappid(loanappid);
		} catch (Exception e) {;}

		rl_payment.setVisibility(View.GONE);
		if (payments != null && !payments.isEmpty()) {
			rl_payment.setVisibility(View.VISIBLE);
			LinearLayout ll_info_payments = (LinearLayout) findViewById(R.id.ll_info_payments);
			ll_info_payments.removeAllViewsInLayout();
			String type = "";
			String voidType = "";
			RelativeLayout child = null;
			View overlay = null;
			BigDecimal amount;
			Map payment;
			Map voidRequest;
			DBVoidService dbVs = new DBVoidService();
			dbVs.setDBContext(txn.getContext());
			for (int i=0; i<payments.size(); i++) {
				child = (RelativeLayout) inflater.inflate(R.layout.item_payment, null);
				payment = (Map) payments.get(i);

				((TextView) child.findViewById(R.id.tv_info_refno)).setText(payment.get("refno").toString());
				((TextView) child.findViewById(R.id.tv_info_txndate)).setText(payment.get("txndate").toString());
				((TextView) child.findViewById(R.id.tv_info_paidby)).setText(payment.get("paidby").toString());
				
				if (payment.get("paymenttype").toString().equals("schedule")) {
					type = "Schedule/Advance";
				} else if (payment.get("paymenttype").toString().equals("over")) {
					type = "Overpayment";
				}
				((TextView) child.findViewById(R.id.tv_info_paymenttype)).setText(type);
				
				amount = new BigDecimal(payment.get("paymentamount").toString()).setScale(2);
				((TextView) child.findViewById(R.id.tv_info_paymentamount)).setText(formatValue(amount));
				child.setTag(R.id.paymentid, payment.get("objid").toString());
				voidRequest = dbVs.findVoidRequestByPaymentid(payment.get("objid").toString());
				if (voidRequest == null || voidRequest.isEmpty()) {
					addPaymentProperties(child);
				} else {
					voidType = voidRequest.get("state").toString();
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
					layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
					overlay = inflater.inflate(R.layout.overlay_void_text, null);
					if (voidType.equals("PENDING")) {
						child.setOnClickListener(null);
						child.setOnLongClickListener(null);
						child.setClickable(false);
						((TextView) overlay).setTextColor(getResources().getColor(R.color.red));
						((TextView) overlay).setText("VOID REQUEST PENDING");
						overlay.setLayoutParams(layoutParams);
						child.addView(overlay); 
					} else if (voidType.equals("APPROVED")) {
						((TextView) overlay).setTextColor(getResources().getColor(R.color.green));
						((TextView) overlay).setText("VOID APPROVED");
						overlay.setLayoutParams(layoutParams);
						((RelativeLayout) child).addView(overlay);
						//addApprovedVoidPaymentProperies(child);
					}
				}
				ll_info_payments.addView(child);
			}
		}
		
		DBRemarksService dbRs = new DBRemarksService();
		dbRs.setDBContext(txn.getContext());
		
		try {
			remarks = dbRs.findRemarksByLoanappid(loanappid);
		} catch (Exception e) {;}
		rl_remarks.setVisibility(View.GONE);
		if (remarks != null && !remarks.isEmpty()) {
			rl_remarks.setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.tv_info_remarks)).setText(remarks.get("remarks").toString());
			RelativeLayout remarks_child = (RelativeLayout) findViewById(R.id.rl_info_remarks);
			remarks_child.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					// TODO Auto-generated method stub
					view.setBackgroundResource(android.R.drawable.list_selector_background);
				}
			});
			remarks_child.setOnLongClickListener(new View.OnLongClickListener() {
				 @Override
				 public boolean onLongClick(View v) {
				 // TODO Auto-generated method stub
					 v.setBackgroundResource(android.R.drawable.list_selector_background);
					 CharSequence[] items = {"Edit Remarks", "Remove Remarks"};
					 UIDialog dialog = new UIDialog() {
						 public void onSelectItem(int index) {
							 SQLTransaction txn = new SQLTransaction("clfc.db");
							 try {
								 txn.beginTransaction();
								 onSelectedItemImpl(txn, index);
								 txn.commit();
							 } catch (Throwable t) {
								 UIDialog.showMessage(t, CollectionSheetInfoActivity.this);
							 } finally {
								 txn.endTransaction();
							 }
						 }
						 
						 private void onSelectedItemImpl(SQLTransaction txn, int index) {
							 switch(index) {
							 	case 0:
							 		showRemarksDialog("edit");
							 		break;
							 	case 1:
							 		txn.delete("remarks", "loanappid='"+loanappid+"'");
							 		Map params = new HashMap();
							 		params.put("loanappid", loanappid);
							 		params.put("state", "PENDING");
							 		txn.insert("remarks_removed", params);
									remarks = null;
									rl_remarks.setVisibility(View.GONE);
									ApplicationUtil.showShortMsg("Successfully removed remarks.");
							 		break;
							 }
						 }
					 };
					 dialog.select(items);
					 return false;
				};
			});
		}
		rl_notes.setVisibility(View.GONE);
		
		//
		// rl_notes.setVisibility(View.GONE);
		// LinearLayout ll_notes = (LinearLayout)
		// findViewById(R.id.ll_info_notes);
		// if (notes != null && notes.getCount() > 0) {
		// rl_notes.setVisibility(View.VISIBLE);
		// notes.moveToFirst();
		// ll_notes.removeAllViewsInLayout();
		// View child = null;
		// Date date = null;
		// String str = "";
		// int idx = 0;
		// do {
		// child = ((LayoutInflater)
		// getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_note,
		// null);
		// //addNoteProperties(child);
		// str = "";
		// date = parseDate(notes.getString(notes.getColumnIndex("fromdate")));
		// if (date != null) str = df.format(date);
		// ((TextView) child.findViewById(R.id.tv_info_refno)).setText(str);
		// str = "";
		// date = parseDate(notes.getString(notes.getColumnIndex("todate")));
		// if (date != null) str = df.format(date);
		// ((TextView) child.findViewById(R.id.tv_info_txndate)).setText(str);
		// ((TextView)
		// child.findViewById(R.id.tv_info_remarks)).setText(notes.getString(notes.getColumnIndex("remarks")));
		// //child.setTag(R.id.noteid,
		// notes.getString(notes.getColumnIndex("objid")));
		// //child.setTag(R.id.idx, idx);
		// idx++;
		// ll_notes.addView(child);
		// } while(notes.moveToNext());
		// }
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
				UIDialog dialog = new UIDialog() {
					public void onSelectItem(int index) {
						switch(index) {
							case 0:
								showVoidDialog(view);
								break;
						}	
					}
				};
				dialog.select(items);
				return false;
			}
		});
	}

	// private void addApprovedVoidPaymentProperies(View child) {
	// child.setClickable(true);
	// child.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// v.setBackgroundResource(android.R.drawable.list_selector_background);
	// }
	// });
	// child.setOnLongClickListener(new View.OnLongClickListener() {
	// @Override
	// public boolean onLongClick(View v) {
	// // TODO Auto-generated method stub
	// final View view = v;
	// v.setBackgroundResource(android.R.drawable.list_selector_background);
	// CharSequence[] items = {"Print"};
	// DialogInterface.OnClickListener listener = new
	// DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface d, int which) {
	// // TODO Auto-generated method stub
	// //showVoidDialog(view);
	// }
	// };
	// ApplicationUtil.showOptionDialog(context, items, listener);
	// return false;
	// }
	// });
	// }
	
	private void showVoidDialog() {
		showVoidDialog(null);
	}

	private void showVoidDialog(View child) {
		final View payment = child;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Reason for void");
		View view = inflater.inflate(R.layout.dialog_remarks, null);
		builder.setView(view);
		builder.setPositiveButton("Submit", null);
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
		
		Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn_positive.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// TODO Auto-generated method stub
				SQLTransaction txn = new SQLTransaction("clfc.db");
				try {
					txn.beginTransaction();
					onClickImpl(txn, view);
					txn.commit();
				} catch (Throwable t) {
					UIDialog.showMessage(t, CollectionSheetInfoActivity.this);
				} finally {
					txn.endTransaction();
				}
			}
			
			private void onClickImpl(SQLTransaction txn, View view) throws Exception {
				Map params = new HashMap();
				params.put("objid", "VOID"+UUID.randomUUID());
				params.put("txndate", Platform.getApplication().getServerDate().toString());
				params.put("paymentid", payment.getTag(R.id.paymentid));
				params.put("routecode", routecode);
				params.put("state", "PENDING");
				
				Map collector = new HashMap();
				collector.put("objid", SessionContext.getProfile().getUserId());
				collector.put("name", SessionContext.getProfile().getFullName());
				params.put("collector", collector);
				
				DBCollectionSheet dbCs = new DBCollectionSheet();
				dbCs.setDBContext(txn.getContext());

				Map collectionSheet = new HashMap();
				try {
					collectionSheet = dbCs.findCollectionSheetByLoanappid(loanappid);
				} catch (Exception e) {
					throw e;
				}
				Map loanapp = new HashMap();
				loanapp.put("objid", collectionSheet.get("loanappid").toString());
				loanapp.put("appno", collectionSheet.get("appno").toString());
				params.put("loanapp", loanapp);
				
				params.put("collectionid", collectionSheet.get("sessionid").toString());
				params.put("reason", ((EditText) dialog.findViewById(R.id.remarks_text)).getText().toString());
				
				try {
					new VoidRequestController(CollectionSheetInfoActivity.this, progressDialog, params, view).execute();
				} catch (Throwable t) {
					UIDialog.showMessage(t, CollectionSheetInfoActivity.this);
				}
//				LoanPostingService svc = new LoanPostingService();
//				try {
//					
//				} catch (Exception e) {
//					throw e;
//				}
//				try { 
//					postingProxy.invoke("voidPayment", new Object[]{params});
//				} catch (Exception e) {}
//				finally {
//					params.put("state", "PENDING");
//					getDbHelper().insertVoidPayment(db, params);
//					View overlay = ((LayoutInflater)
//					getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.overlay_void_text,
//					null);
//					RelativeLayout.LayoutParams layoutParams = new
//					RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//					RelativeLayout.LayoutParams.MATCH_PARENT);
//					layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
//					((TextView) overlay).setTextColor(getResources().getColor(R.color.red));
//					((TextView) overlay).setText("VOID REQUEST PENDING");
//					overlay.setLayoutParams(layoutParams);
//					((RelativeLayout) payment).addView(overlay);
//					payment.setClickable(false);
//					payment.setOnClickListener(null);
//					payment.setOnLongClickListener(null);
//					db.close();
//				}
			}
		});
	}
	/*
	 * private void addNoteProperties(View child) { child.setClickable(true);
	 * child.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub //RelativeLayout rl = (RelativeLayout) v;
	 * v.setBackgroundResource(android.R.drawable.list_selector_background); }
	 * }); child.setOnLongClickListener(new View.OnLongClickListener() {
	 * 
	 * @Override public boolean onLongClick(View v) { // TODO Auto-generated
	 * method stub //showNotesDialog("edit", v); final View view = v;
	 * v.setBackgroundResource(android.R.drawable.list_selector_background);
	 * CharSequence[] items = {"Edit Note", "Remove Note"};
	 * DialogInterface.OnClickListener listener = new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface d, int which) { // TODO
	 * Auto-generated method stub if (which == 0) { showNotesDialog("edit",
	 * view); } else if (which == 1) { DialogInterface.OnClickListener
	 * positivelistener = new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface di, int which) { // TODO
	 * Auto-generated method stub
	 * 
	 * String noteid = view.getTag(R.id.noteid).toString(); int idx =
	 * Integer.parseInt(view.getTag(R.id.idx).toString()); Map<String, Object>
	 * params = new HashMap<String, Object>(); params.put("noteid", noteid);
	 * ServiceProxy postingProxy = ApplicationUtil.getServiceProxy(context,
	 * "DevicePostingServiec"); try { postingProxy.invoke("removeNote", new
	 * Object[]{params}); } catch (Exception e) {} finally { SQLiteDatabase db =
	 * getDbHelper().getReadableDatabase(); getDbHelper().removeNoteById(db,
	 * noteid); db.close(); removeNoteAt(idx);
	 * ApplicationUtil.showShortMsg(context, "Successfully removed note."); } }
	 * }; ApplicationUtil.showConfirmationDialog(context,
	 * "You are about to remove this note. Continue?", positivelistener, null);
	 * } } }; ApplicationUtil.showOptionDialog(context, items, listener); return
	 * false; } }); }
	 */

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
		getMenuInflater().inflate(R.menu.payment, menu);
		if (remarks != null && !remarks.isEmpty()) {
			((MenuItem) menu.findItem(R.id.payment_addremarks)).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.payment_addpayment:
				SQLTransaction txn = new SQLTransaction("clfc.db");				
				try { 
					txn.beginTransaction();
					addPaymentImpl(txn);
					txn.commit();
				} catch (Throwable t) {
					t.printStackTrace();
					UIDialog.showMessage(t, CollectionSheetInfoActivity.this); 
				} finally {
					txn.endTransaction();
				}
				break;
			case R.id.payment_addremarks:
				showRemarksDialog("create");
				break;
		}
		return true;
	}
	
	private void addPaymentImpl(SQLTransaction txn) throws Exception {
		DBVoidService dbVs = new DBVoidService();
		dbVs.setDBContext(txn.getContext());
		
		DBPaymentService dbPs = new DBPaymentService();
		dbPs.setDBContext(txn.getContext());
		if (dbVs.hasPendingVoidRequestByLoanappid(loanappid)) {
			ApplicationUtil.showShortMsg("[ERROR] Cannot add payment. No confirmation for void requested at the moment.");
			
		} else {
			Intent intent = new Intent(this, PaymentActivity.class);
			intent.putExtra("loanappid", loanappid);
			intent.putExtra("detailid", detailid);
			intent.putExtra("routecode", routecode);
			intent.putExtra("totaldays", totaldays);
			intent.putExtra("isfirstbill", isfirstbill);
			intent.putExtra("refno", refno);
			intent.putExtra("paymenttype", paymenttype);
			intent.putExtra("overpayment", overpayment.toString());
			
			if (dbPs.hasPaymentsByLoanappid(loanappid)) {
				refno += (dbPs.noOfPaymentsByLoanappid(loanappid)+1);
			}
			intent.putExtra("refno", refno);
			intent.putExtra("paymenttype", paymenttype);
			intent.putExtra("overpayment", overpayment.toString());
			
			BigDecimal amount = dailydue;
			if (paymenttype.equals("over")) {
				amount = overpayment;
			}
			intent.putExtra("amount", amount.toString());
			startActivity(intent);
		}
	}
	//
	 public void showRemarksDialog(String mode) {
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Remarks");
		 View view = inflater.inflate(R.layout.dialog_remarks, null);
		 builder.setView(view);
		 builder.setPositiveButton("  Ok  ", null);
		 builder.setNegativeButton("Cancel", null);
		 dialog = builder.create();
		 dialog.show();
		 if (!mode.equals("create")) {
			 EditText et_remarks = (EditText) dialog.findViewById(R.id.remarks_text);
			 et_remarks.setText(remarks.get("remarks").toString());
			 et_remarks.setSelection(0, et_remarks.getText().toString().length());
		 }
		 Button btn_positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		 btn_positive.setOnClickListener(new RemarksValidationListener(dialog, mode));
	 }
	 
	 class RemarksValidationListener implements View.OnClickListener 
	 {
		 private final Dialog dialog;
		 private final String mode;
		 public RemarksValidationListener(Dialog dialog, String mode) {
			 this.dialog = dialog;
			 this.mode = mode;
		 }
		 @Override
		 public void onClick(View v) {
			 // TODO Auto-generated method stub
			 	SQLTransaction txn = new SQLTransaction("clfc.db");
			 	try {
			 		txn.beginTransaction();
			 		onClickImpl(txn, mode);
			 		txn.commit();
			 	} catch (Throwable t) {
					UIDialog.showMessage(t, CollectionSheetInfoActivity.this); 			 		
			 	} finally {
			 		txn.endTransaction();
			 	}
			 }
		 }
		 
		 private void onClickImpl(SQLTransaction txn, String mode) throws Exception {
			 String mRemarks = ((EditText) dialog.findViewById(R.id.remarks_text)).getText().toString();
			 if (mRemarks.trim().equals("")) {
				 ApplicationUtil.showShortMsg("Remarks is required.");
			 } else {
				DBSystemService dbSys = new DBSystemService();
				dbSys.setDBContext(txn.getContext());
				
				Location location = NetworkLocationProvider.getLocation();
				String collectorid = SessionContext.getProfile().getUserId();
				 
				Map params = new HashMap();				 
				params.put("loanappid", loanappid);
				params.put("state", "PENDING");
				params.put("remarks", mRemarks);
				params.put("longitude", location.getLongitude());
				params.put("latitude", location.getLatitude());
				params.put("trackerid", dbSys.getTrackerid());
				params.put("collectorid", SessionContext.getProfile().getUserId());
				params.put("txndate", Platform.getApplication().getServerDate().toString());
				 
				if (mode.equals("create")) {
					txn.insert("remarks", params);
					rl_remarks.setVisibility(View.VISIBLE);
					ApplicationUtil.showShortMsg("Successfully added remark.");
				} else if (!mode.equals("create")) {
					txn.update("remarks", "loanappid='"+loanappid+"'", params);
					ApplicationUtil.showShortMsg("Successfully updated remark.");
				}
				
				DBRemarksService dbRs = new DBRemarksService();
				dbRs.setDBContext(txn.getContext());
				remarks = dbRs.findRemarksByLoanappid(loanappid);
				((TextView) findViewById(R.id.tv_info_remarks)).setText(mRemarks);
				dialog.dismiss();
		 }
	 }
}
