package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
	private EditText et_overpayment;
	private RadioButton rbtn_advance;
	private RadioButton rbtn_over;
	private String routecode = "";
	private int isfirstbill = 0;
	private int totaldays = 0;
	private BigDecimal overpayment = new BigDecimal("0").setScale(2);
	
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
		totaldays = intent.getIntExtra("totaldays", 0);
		String amt = intent.getStringExtra("overpayment");
		overpayment = new BigDecimal(amt).setScale(2);
		db = new MySQLiteHelper(context);		
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		Calendar c = Calendar.getInstance();
		if(!db.isOpen) db.openDb();
		try {
			c.setTime(db.getServerDate());
		}
		catch (Exception e) { Toast.makeText(context, "Error: ParseException", Toast.LENGTH_SHORT).show(); }
		db.closeDb();			
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
		String formattedDate = df.format(c.getTime());
		
		RelativeLayout rl_overpayment = (RelativeLayout) findViewById(R.id.rl_overpayment);
		rl_overpayment.setVisibility(View.GONE);
		if (type.equals("over")) rl_overpayment.setVisibility(View.VISIBLE);
		
		et_overpayment = (EditText) findViewById(R.id.et_overpayment);
		et_overpayment.setEnabled(false);
		if (isfirstbill == 1) et_overpayment.setEnabled(true);
		if (overpayment.compareTo(new BigDecimal("0").setScale(2)) > 0) {
			DecimalFormat decf = new DecimalFormat("#,##0.00");
			StringBuffer sb = new StringBuffer();
			FieldPosition fp = new FieldPosition(0);
			decf.format(overpayment, sb, fp);
			et_overpayment.setText(sb.toString());
		}
		
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
					boolean flag = true;
					BigDecimal amt = new BigDecimal(amount).setScale(2);
					BigDecimal amt2 = new BigDecimal(0).setScale(2);
					if (type.equals("over")) {
						amt2 = new BigDecimal(et_overpayment.getText().toString()).setScale(2);
						if (isfirstbill == 1) {
							int td = amt.divide(amt2).intValue();
							if (td < totaldays) {
								Toast.makeText(context, "Amount paid could not cover up to current date based on overpayment amount.", Toast.LENGTH_SHORT).show();
								flag = false;
							}
						}
					}
					if (flag == true) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("amount", amt.toString());
						params.put("overpayment", amt2.toString());
						showConfirmationDialog(params);					
					}
				}
			}
		});
	}
	
	private void showConfirmationDialog(Map<String, Object> params) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		String message = "Amount Paid: "+params.get("amount").toString();
		if (type.equals("over") && isfirstbill == 1) message += "\nOverpayment: "+params.get("overpayment").toString();
		message += "\n\nAre all the information correct?";
		builder.setMessage(message);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface d, int which) {
				// TODO Auto-generated method stub
				savePayment();
			}
		});
		builder.setNegativeButton("No", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void savePayment() {
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
	
	private void showError(String errmsg) {
		Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
	}
}
