package com.rameses.clfc.android.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rameses.clfc.android.ControlActivity;
import com.rameses.clfc.android.R;
import com.rameses.clfc.android.db.DBCollectionSheet;
import com.rameses.clfc.android.db.DBPaymentService;
import com.rameses.clfc.android.db.DBRemarksService;
import com.rameses.client.android.SessionContext;
import com.rameses.client.android.UIAction;
import com.rameses.client.android.UIDialog;
import com.rameses.db.android.SQLTransaction;

public class PostingListActivity extends ControlActivity {
	private Context context = this;
	private LayoutInflater inflater;
	private EditText et_search;
	
	@Override
	protected void onCreateProcess(Bundle savedInstanceState) {
		setContentView(R.layout.template_footer);
		setTitle("Posting");
		
		RelativeLayout container = (RelativeLayout) findViewById(R.id.rl_container);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_posting, container, true);
		
		RelativeLayout layout_posted = (RelativeLayout) findViewById(R.id.layout_posted);
		inflater.inflate(R.layout.section_posted, layout_posted, true);
		
		RelativeLayout layout_unposted = (RelativeLayout) findViewById(R.id.layout_unposted);
		inflater.inflate(R.layout.section_unposted, layout_unposted, true);
		
		et_search = (EditText) findViewById(R.id.et_search);
	}
	
	@Override
	protected void onStartProcess() {
		super.onStartProcess();

		loadCollectionSheets();
		new UIAction(this, R.id.ib_refresh) {
			protected void onClick(View view) {
				view.setBackgroundResource(android.R.drawable.list_selector_background);
				loadCollectionSheets();
			}
		};
		
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {;}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {;}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				loadCollectionSheets();
			}
		});
	}
	
	private void loadCollectionSheets() {
		SQLTransaction clfcdb = new SQLTransaction("clfc.db");
		SQLTransaction paymentdb = new SQLTransaction("clfcpayment.db");
		SQLTransaction remarksdb = new SQLTransaction("clfcremarks.db");
		try {
			clfcdb.beginTransaction();
			paymentdb.beginTransaction();
			remarksdb.beginTransaction();
			loadCollectionSheetsImpl(clfcdb, paymentdb, remarksdb);
			clfcdb.commit();
			paymentdb.commit();
			remarksdb.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			UIDialog.showMessage(t, PostingListActivity.this);
		} finally {
			clfcdb.endTransaction();
			paymentdb.endTransaction();
			remarksdb.endTransaction();
		}
	}
	
	private void loadCollectionSheetsImpl(SQLTransaction clfcdb, SQLTransaction paymentdb, SQLTransaction remarksdb) throws Exception {
		DBCollectionSheet dbCs = new DBCollectionSheet();
		dbCs.setDBContext(clfcdb.getContext());
		String searchtext = et_search.getText().toString();
		if (searchtext == null || searchtext.equals("")) {
			searchtext = "%";
		} else {
			searchtext += "%";
		}
		
		int noOfPosted = 0;
		int noOfUnposted = 0;

		Map params = new HashMap();
		params.put("searchtext", searchtext);
		params.put("collectorid", SessionContext.getProfile().getUserId());
		
		View child = null;
		LinearLayout ll_posted = (LinearLayout) findViewById(R.id.ll_posted);
		ll_posted.removeAllViewsInLayout();
		
		LinearLayout ll_unposted = (LinearLayout) findViewById(R.id.ll_posted);
		ll_unposted.removeAllViewsInLayout();
		
		List<Map> list = dbCs.getCollectionSheetsByCollectorid(SessionContext.getProfile().getUserId());
		
		if (!list.isEmpty()) {
			Map map;
			String loanappid = "";
			DBPaymentService dbPs = new DBPaymentService();
			dbPs.setDBContext(paymentdb.getContext());
			
			DBRemarksService dbRs = new DBRemarksService();
			dbRs.setDBContext(remarksdb.getContext());
			boolean posted = false;
			boolean haspayment = false;
			boolean hasremarks = false;
			for (int i=0; i<list.size(); i++) {
				map = (Map) list.get(i);
				
				haspayment = false;
				hasremarks = false;
				loanappid = map.get("loanappid").toString();
				
				haspayment = dbPs.hasPaymentsByLoanappid(loanappid);
				hasremarks = dbRs.hasRemarksByLoanappi(loanappid);
				if (haspayment == true || hasremarks == true) {
					posted = false;
					if (!dbPs.hasUnpostedPaymentsByLoanappid(loanappid)) {
						posted = true;
					} else {
						posted = false;
					}
					
					if (!dbRs.hasUnpostedRemarks()) {
						posted = true;
					} else {
						posted = false;
					}

					child = inflater.inflate(R.layout.item_string, null);
					((TextView) child.findViewById(R.id.tv_item_str)).setText(map.get("acctname").toString());
					if (posted == true) {
						noOfPosted++;
						ll_posted.addView(child);
					} else {
						noOfUnposted++;
						ll_unposted.addView(child);
					}
				}
			}
		}
//		List<Map> list = dbCs.getPostedCollectionSheets(params);
//		Map map;
//		if (!list.isEmpty()) {
//			noOfPosted = list.size();
//			for (int i=0; i<list.size(); i++) {
//				map = (Map) list.get(i);
//				
//				System.out.println("map -> "+map);
//				child = inflater.inflate(R.layout.item_string, null);
//				((TextView) child.findViewById(R.id.tv_item_str)).setText(map.get("acctname").toString());
//				ll_posted.addView(child);
//			}
//		}
		//((ListView) findViewById(R.id.lv_posted)).setAdapter(new StringAdapter(context, list));
		//list.clear();

//		LinearLayout ll_unposted = (LinearLayout) findViewById(R.id.ll_unposted);
//		ll_unposted.removeAllViewsInLayout();
//		
//		list = dbCs.getUnpostedCollectionSheets(params);
//		
//		if (!list.isEmpty()) {
//			noOfUnposted = list.size();
//			for (int i=0; i<list.size(); i++) {
//				map = (Map) list.get(i);
//
//				System.out.println("map -> "+map);
//				child = inflater.inflate(R.layout.item_string, null);
//				((TextView) child.findViewById(R.id.tv_item_str)).setText(map.get("acctname").toString());
//				ll_unposted.addView(child);
//			}
//		}
		((TextView) findViewById(R.id.tv_posted)).setText("No. of Posted: "+noOfPosted);
		((TextView) findViewById(R.id.tv_unposted)).setText("No. of Unposted: "+noOfUnposted);
	}
}
