package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionSheetInfo extends Activity {
	private Context context = this;
	private MySQLiteHelper db;
	private String loanappid = "";
	private BigDecimal overpayment = new BigDecimal("0").setScale(2);
	private String routecode = "";
	private String refno = "";
	private String paymenttype = "";
	private Cursor payment;
	private RelativeLayout rl_general = null;
	private RelativeLayout rl_payment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionsheetinfo);
		setTitle("Collection Sheet Info");
		db = new MySQLiteHelper(context);
		rl_general = (RelativeLayout) findViewById(R.id.layout_info_general);
		rl_payment = (RelativeLayout) findViewById(R.id.layout_info_payment);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = getIntent();
		loanappid = intent.getStringExtra("loanappid");
		routecode = intent.getStringExtra("routecode");
		paymenttype = intent.getStringExtra("paymenttype");
		db.openDb();
		Cursor result = db.getCollectionsheetByLoanappid(loanappid);
		db.closeDb();
		
		String acctname = "";
		String appno = "";
		BigDecimal amountdue = new BigDecimal("0").setScale(2);
		BigDecimal loanamount = new BigDecimal("0").setScale(2);
		BigDecimal balance = new BigDecimal("0").setScale(2);
		BigDecimal dailydue = new BigDecimal("0").setScale(2);
		BigDecimal penalty = new BigDecimal("0").setScale(2);
		String duedate = "";
		
		if(result != null && result.getCount() > 0) {
			acctname = result.getString(result.getColumnIndex("acctname"));
			appno = result.getString(result.getColumnIndex("appno"));
			String amt = result.getDouble(result.getColumnIndex("amountdue"))+"";
			amountdue = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("loanamount"));
			loanamount = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("balance"));
			balance = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndexOrThrow("dailydue"));
			dailydue = new BigDecimal(amt).setScale(2);
			amt = result.getString(result.getColumnIndex("penalty"));
			penalty = new BigDecimal(amt).setScale(2);
			amt = result.getDouble(result.getColumnIndex("overpaymentamount"))+"";
			overpayment = new BigDecimal(amt).setScale(2);
			refno = result.getString(result.getColumnIndex("refno"));
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
		}
		
		TextView tv_info_acctname = (TextView) findViewById(R.id.tv_info_acctname);
		TextView tv_info_appno = (TextView) findViewById(R.id.tv_info_appno);
		TextView tv_info_loanamount = (TextView) findViewById(R.id.tv_info_loanamount);
		TextView tv_info_balance = (TextView) findViewById(R.id.tv_info_balance);
		TextView tv_info_dailydue = (TextView) findViewById(R.id.tv_info_dailydue);
		TextView tv_info_amountdue = (TextView) findViewById(R.id.tv_info_amountdue);
		TextView tv_info_penalty = (TextView) findViewById(R.id.tv_info_penalty);
		TextView tv_info_overpayment = (TextView) findViewById(R.id.tv_info_overpayment);
		TextView tv_info_duedate = (TextView) findViewById(R.id.tv_info_duedate);
		
		tv_info_acctname.setText(acctname);
		tv_info_appno.setText(appno);
		tv_info_loanamount.setText(formatValue(loanamount));
		tv_info_balance.setText(formatValue(balance));
		tv_info_dailydue.setText(formatValue(dailydue));
		tv_info_amountdue.setText(formatValue(amountdue));
		tv_info_penalty.setText(formatValue(penalty));
		tv_info_overpayment.setText(formatValue(overpayment));
		tv_info_duedate.setText(duedate);
		
		db.openDb();
		payment = db.getPayment(loanappid);
		db.closeDb();
		
		TextView tv_refno = (TextView) findViewById(R.id.tv_info_refno);
		TextView tv_txndate = (TextView) findViewById(R.id.tv_info_txndate);
		TextView tv_paymenttype = (TextView) findViewById(R.id.tv_info_paymenttype);
		TextView tv_paymentamount = (TextView) findViewById(R.id.tv_info_paymentamount);
		rl_payment.setVisibility(View.GONE);

		String txndate = "";
		String paymenttype = "";
		BigDecimal paymentamount = new BigDecimal("0").setScale(2);

		if(payment != null && payment.getCount() > 0) {
			rl_payment.setVisibility(View.VISIBLE);
			refno = payment.getString(payment.getColumnIndex("refno"));
			txndate = payment.getString(payment.getColumnIndex("txndate"));
			String type = payment.getString(payment.getColumnIndex("paymenttype"));
			if (type.equals("advance")) paymenttype = "Schedule/Advance";
			else paymenttype = "Overpayment";
			String amt = payment.getDouble(payment.getColumnIndex("paymentamount"))+"";
			paymentamount = new BigDecimal(amt).setScale(2);
		}
		
		tv_refno.setText(refno);
		tv_txndate.setText(txndate);
		tv_paymenttype.setText(paymenttype);
		tv_paymentamount.setText(paymentamount.toString());
	}
	
	private String formatValue(Object number) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		StringBuffer sb = new StringBuffer();
		FieldPosition fp = new FieldPosition(0);
		df.format(number, sb, fp);
		return sb.toString();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if(payment != null && payment.getCount() > 0) 
			getMenuInflater().inflate(R.menu.empty, menu);
		else
			getMenuInflater().inflate(R.menu.payment, menu);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(context, Payment.class);
		intent.putExtra("loanappid", loanappid);
		intent.putExtra("routecode", routecode);
		switch(item.getItemId()) {
			case R.id.payment_addpayment:
					intent.putExtra("refno", refno);
					intent.putExtra("paymenttype", paymenttype);
					startActivity(intent);
					break;
		}
		return true;
	}

}
