package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Payment extends Activity {
	private Context context = this;
	private MySQLiteHelper db;
	private String loanappid = "";
	private String refno = "";
	private TextView tv_refno;
	private TextView tv_txndate;
	private String type = "";
	private EditText et_amount;
	private RadioButton rbtn_advance;
	private RadioButton rbtn_over;
	private String routecode = "";
	private int isfirstbill = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		Intent intent = getIntent();
		loanappid = intent.getStringExtra("loanappid");
		refno = intent.getStringExtra("refno");
		routecode = intent.getStringExtra("routecode");
		type = intent.getStringExtra("paymenttype");
		isfirstbill = intent.getIntExtra("isfirstbill", 0);
		db = new MySQLiteHelper(context);
		
		Calendar c = Calendar.getInstance();
		if(!db.isOpen) db.openDb();
		try {
			c.setTime(db.getServerDate());
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		db.closeDb();			
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
		String formattedDate = df.format(c.getTime());
		
		tv_refno = (TextView) findViewById(R.id.tv_payment_refno);
		tv_txndate = (TextView) findViewById(R.id.tv_payment_txndate);
		et_amount = (EditText) findViewById(R.id.et_payment_amount);
		
		tv_refno.setText(refno);
		tv_txndate.setText(formattedDate);
		
		Button btn_ok = (Button) findViewById(R.id.btn_payment_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String amount = et_amount.getText().toString();
				if(amount == null || amount.equals("")) {
					showError("Amount is required.");
				} else {
					db.openDb();
					Map<String, Object> payment = new HashMap<String, Object>();
					payment.put("loanappid", loanappid);
					payment.put("refno", tv_refno.getText());
					payment.put("txndate", tv_txndate.getText());
					payment.put("amount", et_amount.getText());
					payment.put("routecode", routecode);
					payment.put("type", type);
					payment.put("isfirstbill", isfirstbill);
					db.insertPayment(payment);
					db.closeDb();

					finish();
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!db.isOpen) db.openDb();
		Cursor result = db.getPayment(loanappid);	
		db.closeDb();
		
		if(result != null && result.getCount() > 0) {
			tv_refno.setText(result.getString(result.getColumnIndex("refno")));
			tv_txndate.setText(result.getString(result.getColumnIndex("txndate")));
			String type = result.getString(result.getColumnIndex("paymenttype"));
			if(type.equals("advance")) {
				rbtn_advance.setChecked(true);
			} else {
				rbtn_over.setChecked(true);
			}
			et_amount.setText(result.getDouble(result.getColumnIndex("paymentamount"))+"");
		}
	}
	
	private void showError(String errmsg) {
		Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
	}
}
