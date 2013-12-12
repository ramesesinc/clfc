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

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
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
	private int isfirstbill = 0;
	private RelativeLayout rl_general = null;
	private RelativeLayout rl_payment = null;
	private RelativeLayout rl_remarks = null;
	private AlertDialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheetinfo);
		setTitle("Collection Sheet Info");
		db = new MySQLiteHelper(context);
		rl_general = (RelativeLayout) findViewById(R.id.layout_info_general);
		rl_payment = (RelativeLayout) findViewById(R.id.layout_info_payment);
		rl_remarks = (RelativeLayout) findViewById(R.id.layout_info_remarks);
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
			SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
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

		String paymenttype = "";
		BigDecimal paymentamount = new BigDecimal("0").setScale(2);
		View child = null;
		TextView tv_info_refno = null;
		TextView tv_info_txndate = null;
		TextView tv_info_type = null;
		TextView tv_info_amount = null;
		if(payment != null && payment.getCount() > 0) {
			rl_payment.setVisibility(View.VISIBLE);
			do {
				child = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_payment, null);
				((TextView) child.findViewById(R.id.tv_info_refno)).setText(payment.getString(payment.getColumnIndex("refno")));
				((TextView) child.findViewById(R.id.tv_info_txndate)).setText(payment.getString(payment.getColumnIndex("txndate")));
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
			
		}
		return true;
	}

	public void showRemarksDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Remarks");
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remarks, null);
		builder.setView(view);
		if (!remarks.equals("")) {
			((EditText) dialog.findViewById(R.id.remarks_text)).setText(remarks);
		}
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				EditText et_remarks = (EditText) dialog.findViewById(R.id.remarks_text);
				if (!et_remarks.getText().equals("")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("loanappid", loanappid);
					map.put("remarks", et_remarks.getText());
					if (!db.isOpen) db.openDb();
					db.insertRemarks(map);
					if (db.isOpen) db.closeDb();
					if (rl_remarks.getVisibility() == View.GONE) rl_remarks.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.tv_info_remarks)).setText(et_remarks.getText());
				}
			}
		});	
		
		builder.setNegativeButton("Cancel", null);
		dialog = builder.create();
		dialog.show();
	}
	
	private Object saveRemarksClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface d, int which) {
			// TODO Auto-generated method stub
			
		}
	};
}
