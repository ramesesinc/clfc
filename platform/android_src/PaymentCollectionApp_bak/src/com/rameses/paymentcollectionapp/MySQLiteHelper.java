package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "collection.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_COLLECTIONSHEET = "collectionsheet";
	private static final String TABLE_PAYMENT = "payment";
	private static final String TABLE_REMARKS = "remarks";
	private static final String TABLE_REMARKS_REMOVED = "remarks_removed";
	private static final String TABLE_NOTES = "notes";
	//private static final String TABLE_NOTES_REMOVED = "notes_removed";
	//private static final String TABLE_UPLOADS = "uploads";
	//private static final String TABLE_UPLOADEDPAYMENTS = "uploaded_payments";
	private static final String TABLE_SYSTEM = "system";
	private static final String TABLE_HOST = "host";
	private static final String TABLE_ROUTE = "route";
	private static final String TABLE_VOID = "void";
	//private static final String TABLE_SESSION = "session";
	private static final String TABLE_SPECIALCOLLECTION = "specialcollection";
	private static final String TABLE_TRACKER = "tracker";
	private static final String TABLE_COLLECTION_DATE = "collection_date";
	private static final String TABLE_LOCATION_TRACKER = "location_tracker";
	
	private static final String CREATE_TABLE_COLLECTIONSHEET = "" +
			"CREATE TABLE COLLECTIONSHEET(" +
			"loanappid text PRIMARY KEY, " +
			"detailid text," +
			"seqno numeric, " +
			"appno text, " +
			"acctname text, " +
			"amountdue numeric, " +
			"overpaymentamount numeric, " +
			"refno text, " +
			"routecode text, " +
			"term numeric, " +
			"loanamount numeric, " +
			"dailydue numeric, " +
			"balance numeric, " +
			"interest numeric, "+
			"penalty numeric, " +
			"others numeric, " +
			"duedate text, " +
			"isfirstbill numeric, " +
			"paymentmethod text," +
			"homeaddress text, " +
			"collectionaddress text, " +
			"sessionid text, " +
			"type text " +
			");";
	private static final String CREATE_TABLE_PAYMENT = "" +
			"CREATE TABLE PAYMENT(" +
			"objid text PRIMARY KEY, " +
			"state text, " +
			"refno text, " +
			"txndate text, " +
			"paymenttype text, " +
			"paymentamount numeric, " +
			"paidby text, " +
			"loanappid text, " +
			"detailid text, "+
			"routecode text, " +
			"isfirstbill numeric," +
			"longitude numeric, " +
			"latitude numeric, " +
			"trackerid text, " +
			"collectorid text " +
			");";
	private static final String CREATE_TABLE_REMARKS = "CREATE TABLE REMARKS(" +
			"loanappid text PRIMARY KEY, " +
			"state text, " +
			"remarks text, " +
			"longitude text, " +
			"latitude text, " +
			"trackerid text, " +
			"collectorid text, " +
			"txndate text " +
			");";
	private static final String CREATE_TABLE_REMARKS_REMOVED = "CREATE TABLE REMARKS_REMOVED(" +
			"loanappid text PRIMARY KEY," +
			"state text" +
			");";
	private static final String CREATE_TABLE_NOTES = "CREATE TABLE NOTES(" +
			"objid text PRIMARY KEY, " +
			"state text, " +
			"loanappid text, " +
			"fromdate text, " +
			"todate text, " +
			"remarks text" +
			");";
	/*private static final String CREATE_TABLE_NOTES_REMOVED = "CREATE TABLE NOTES_REMOVED(" +
			"objid text, " +
			"state text," +
			"loanappid text" +
			");";
	private static final String CREATE_TABLE_UPLOADS = "CREATE TABLE UPLOADS(" +
			"loanappid text, " +
			"referenceid text, " +
			"PRIMARY KEY(loanappid, referenceid)" +
			");";*/
	private static final String CREATE_TABLE_SYSTEM = "CREATE TABLE SYSTEM(" +
			"name text, " +
			"value text " +
			")";
	/*private static final String CREATE_TABLE_HOST = "CREATE TABLE HOST(" +
			"ipaddress text, " +
			"port text" +
			");";*/
	private static final String CREATE_TABLE_ROUTE = "CREATE TABLE ROUTE(" +
			"routecode text PRIMARY KEY, " +
			"state text, " +
			"routedescription text, " +
			"routearea text, " +
			"sessionid text, " +
			"collectorid text" +
			");";
	private static final String CREATE_TABLE_VOID = "CREATE TABLE VOID(" +
			"objid text PRIMARY KEY, " +
			"paymentid text, " +
			"loanappid text, " +
			"state text, " +
			"collectorid text, " +
			"reason text" +
			");";
	/*private static final String CREATE_TABLE_SESSION = "CREATE TABLE SESSION(" +
			"objid text PRIMARY KEY" +
			");";*/
	private static final String CREATE_TABLE_SPECIALCOLLECTION = "CREATE TABLE SPECIALCOLLECTION(" +
			"objid text PRIMARY KEY, " +
			"state text, " +
			"remarks text " +
			");";
	private static final String CREATE_TABLE_TRACKER = "CREATE TABLE TRACKER(" +
			"trackerid text PRIMARY KEY, " +
			"collectorid text," +
			");";
	private static final String CREATE_TABLE_COLLECTION_DATE = "CREATE TABLE COLLECTION_DATE(" +
			"collectorid text," +
			"serverdate text " +
			");";
	private static final String CREATE_TABLE_LOCATION_TRACKER = "CREATE TABLE LOCATION_TRACKER(" +
			"objid text PRIMARY KEY, " +
			"seqno numeric, " +
			"trackerid text, " +
			"collectorid text, " +
			"longitude numeric, " +
			"latitude numeric" +
			")";
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e("Database", "oncreate");
		db.execSQL(CREATE_TABLE_COLLECTIONSHEET);
		db.execSQL(CREATE_TABLE_PAYMENT);
		db.execSQL(CREATE_TABLE_REMARKS);
		db.execSQL(CREATE_TABLE_REMARKS_REMOVED);
		db.execSQL(CREATE_TABLE_NOTES);
		//db.execSQL(CREATE_TABLE_NOTES_REMOVED);
		//db.execSQL(CREATE_TABLE_UPLOADEDPAYMENT);
		//db.execSQL(CREATE_TABLE_UPLOADS);
		db.execSQL(CREATE_TABLE_SYSTEM);
		//db.execSQL(CREATE_TABLE_HOST);
		db.execSQL(CREATE_TABLE_ROUTE);
		db.execSQL(CREATE_TABLE_VOID);
		//db.execSQL(CREATE_TABLE_SESSION);
		db.execSQL(CREATE_TABLE_SPECIALCOLLECTION);
		db.execSQL(CREATE_TABLE_COLLECTION_DATE);
		db.execSQL(CREATE_TABLE_LOCATION_TRACKER);
		//db.execSQL("INSERT INTO "+TABLE_HOST+" VALUES('10.10.151.139', '8070')");
		db.execSQL("INSERT INTO "+TABLE_SYSTEM+" VALUES('host_online', '121.97.60.200')");
		db.execSQL("INSERT INTO "+TABLE_SYSTEM+" VALUES('host_offline', '121.97.60.200')");
		db.execSQL("INSERT INTO "+TABLE_SYSTEM+" VALUES('host_port', '8070')");
		db.execSQL("INSERT INTO "+TABLE_SYSTEM+" VALUES('app_state', 'idle')");
		db.execSQL("INSERT INTO "+TABLE_SYSTEM+" VALUES('collector_state', 'logout')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("Database", "onupgrade");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_COLLECTIONSHEET);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_PAYMENT);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_REMARKS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_REMARKS_REMOVED);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
		//db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES_REMOVED);
		//db.execSQL("DROP TABLE IF EXISTS "+TABLE_UPLOADEDPAYMENT);
		//db.execSQL("DROP TABLE IF EXISTS "+TABLE_UPLOADS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_HOST);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROUTE);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_VOID);
		//db.execSQL("DROP TABLE IF EXISTS "+TABLE_SESSION);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_SPECIALCOLLECTION);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_COLLECTION_DATE);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOCATION_TRACKER);
		onCreate(db);
	}

	public void insertCollectionsheet(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values=new ContentValues();
		values.put("loanappid", params.get("loanappid").toString());
		values.put("detailid", params.get("objid").toString());
		values.put("seqno", Integer.parseInt(params.get("seqno").toString()));
		values.put("appno", params.get("appno").toString());
		values.put("acctname", params.get("acctname").toString());
		values.put("loanamount", Double.parseDouble(params.get("loanamount").toString()));
		values.put("term", Integer.parseInt(params.get("term").toString()));
		values.put("balance", Double.parseDouble(params.get("balance").toString()));
		values.put("dailydue", Double.parseDouble(params.get("dailydue").toString()));
		values.put("amountdue", Double.parseDouble(params.get("amountdue").toString()));
		values.put("interest" ,Double.parseDouble(params.get("interest").toString()));
		values.put("penalty", Double.parseDouble(params.get("penalty").toString()));
		values.put("others", Double.parseDouble(params.get("others").toString()));
		values.put("overpaymentamount", Double.parseDouble(params.get("overpaymentamount").toString()));
		values.put("refno", params.get("refno").toString());
		values.put("routecode", params.get("routecode").toString());
		values.put("duedate", params.get("dtmatured").toString());
		values.put("isfirstbill", Integer.parseInt(params.get("isfirstbill").toString()));
		values.put("paymentmethod", params.get("paymentmethod").toString());
		values.put("homeaddress", params.get("homeaddress").toString());
		values.put("collectionaddress", params.get("collectionaddress").toString());
		values.put("sessionid", params.get("sessionid").toString());
		values.put("type", params.get("type").toString());
		db.insert(TABLE_COLLECTIONSHEET, null, values);
	}
	
	public void insertPayment(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString()); 
		values.put("loanappid", params.get("loanappid").toString());
		values.put("detailid", params.get("detailid").toString());
		values.put("refno", params.get("refno").toString());
		values.put("txndate", params.get("txndate").toString());
		values.put("paymentamount", Double.parseDouble(params.get("amount").toString()));
		values.put("paymenttype", params.get("type").toString());
		values.put("routecode", params.get("routecode").toString());
		values.put("isfirstbill", Integer.parseInt(params.get("isfirstbill").toString()));
		values.put("longitude", params.get("longitude").toString());
		values.put("latitude", params.get("latitude").toString());
		values.put("paidby", params.get("paidby").toString());
		values.put("trackerid", params.get("trackerid").toString());
		values.put("collectorid", params.get("collectorid").toString());
		db.insert(TABLE_PAYMENT, null, values);
	}
	
	public void insertLocationTracker(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("seqno", Integer.parseInt(params.get("seqno").toString()));
		values.put("trackerid", params.get("trackerid").toString());
		values.put("collectorid", params.get("collectorid").toString());
		values.put("longitude", params.get("longitude").toString());
		values.put("latitude", params.get("latitude").toString());
		db.insert(TABLE_LOCATION_TRACKER, null, values);
	}
	
	public void insertRemarks(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("loanappid", params.get("loanappid").toString());
		values.put("state", params.get("state").toString());
		values.put("remarks", params.get("remarks").toString());
		values.put("longitude", params.get("longitude").toString());
		values.put("latitude", params.get("latitude").toString());
		values.put("trackerid", params.get("trackerid").toString());
		values.put("collectorid", params.get("collectorid").toString());
		values.put("txndate", params.get("txndate").toString());
		db.insert(TABLE_REMARKS, null, values);
	}
	
	public void insertRemarksRemoved(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("loanappid", params.get("loanappid").toString());
		values.put("state", params.get("state").toString());
		db.insert(TABLE_REMARKS_REMOVED, null, values);
	}
	
	public void updateRemarks(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		String loanappid = params.get("loanappid").toString();
		values.put("state", params.get("state").toString());
		values.put("remarks", params.get("remarks").toString());
		db.update(TABLE_REMARKS, values, "loanappid='"+loanappid+"'", null);
	}

	public void insertNotes(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("fromdate", params.get("fromdate").toString());
		values.put("todate", params.get("todate").toString());
		values.put("remarks", params.get("remarks").toString());
		db.insert(TABLE_NOTES, null, values);
	}
	/*
	public void insertNotesRemoved(Map<String, Object> params) {
		ContentValues values=new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString());
		values.put("loanappid", params.get("loanappid").toString());
		db.insert(TABLE_NOTES_REMOVED, null, values);
	}
	
	public void updateNotes(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		String objid = params.get("objid").toString();
		//values.put("objid", objid);
		values.put("state", params.get("state").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("fromdate", params.get("fromdate").toString());
		values.put("todate", params.get("todate").toString());
		values.put("remarks", params.get("remarks").toString());
		db.update(TABLE_NOTES, values, "objid='"+objid+"'", null);
	}
	*/
	/*public void insertUploadedPayment(String loanappid) {
		ContentValues values=new ContentValues();
		values.put("loanappid", loanappid);
		db.insert(TABLE_UPLOADEDPAYMENT, null, values);
	}*/
	
	public void insertCollectionDate(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("serverdate", params.get("serverdate").toString());
		values.put("collectorid", params.get("collectorid").toString());
		db.insert(TABLE_COLLECTION_DATE, null, values);
	}
	
	public void insertSystem(SQLiteDatabase db, Map<String, Object> params) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='"+params.get("name").toString()+"'", null);

		ContentValues values=new ContentValues();
		values.put("value", params.get("value").toString());
		if (result != null && result.getCount() > 0) {
			db.update(TABLE_SYSTEM, values, "name='"+params.get("name").toString()+"'", null);
		} else {
			values.put("name", params.get("name").toString());
			//values.put("sessionid", params.get("sessionid").toString());
			//values.put("serverdate", params.get("serverdate").toString());
			//values.put("collectorid", params.get("collectorid").toString());
			db.insert(TABLE_SYSTEM, null, values);
		}
	}
	
	/*public void insertSession(SQLiteDatabase db, String objid) {
		ContentValues values = new ContentValues();
		values.put("objid", objid);
		db.insert(TABLE_SESSION, null, values);
	}*/
	
	public void insertSpecialCollection(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString());
		values.put("remarks", params.get("remarks").toString());
		db.insert(TABLE_SPECIALCOLLECTION, null, values);
	}
	
	public void insertRoute(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("routecode", params.get("routecode").toString());
		values.put("state", params.get("state").toString());
		values.put("routedescription", params.get("routedescription").toString());
		values.put("routearea", params.get("routearea").toString());
		values.put("collectorid", params.get("collectorid").toString());
		values.put("sessionid", params.get("sessionid").toString());
		db.insert(TABLE_ROUTE, null, values);
	}
	
	public void insertVoidPayment(SQLiteDatabase db, Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString());
		values.put("paymentid", params.get("paymentid").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("collectorid", params.get("collectorid").toString());
		values.put("reason", params.get("reason").toString());
		db.insert(TABLE_VOID, null, values);
	}
	
	public void updateHost(SQLiteDatabase db, String ipaddress, String port) {
		ContentValues values=new ContentValues();
		values.put("ipaddress", ipaddress);
		values.put("port", port);
		db.update(TABLE_HOST, values, null, null);
	}
	
	public boolean isLoggedIn(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collector_state' AND value='login'", null);
		
		if (result != null && result.getCount() > 0) return true;
		return false;
	}
	
	public boolean isSettingsSet(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_online'", null);		
		if (result == null || result.getCount() == 0) return false;

		result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_offline'", null);		
		if (result == null || result.getCount() == 0) return false;

		result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_port'", null);		
		if (result == null || result.getCount() == 0) return false;

		result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_session'", null);		
		if (result == null || result.getCount() == 0) return false;

		result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_upload'", null);		
		if (result == null || result.getCount() == 0) return false;

		result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_tracker'", null);		
		if (result == null || result.getCount() == 0) return false;
		
		return true;
	}
	
	public void logoutCollector(SQLiteDatabase db) {
		db.execSQL("UPDATE "+TABLE_SYSTEM+" SET value='logout' WHERE name='collector_state'");
	}

	public void loginCollector(SQLiteDatabase db) {
		db.execSQL("UPDATE "+TABLE_SYSTEM+" SET value='login' WHERE name='collector_state'");
	}

	public void setIdleState(SQLiteDatabase db) {
		db.execSQL("UPDATE "+TABLE_SYSTEM+" SET value='idle' WHERE name='app_state'");
	}

	public void setActiveState(SQLiteDatabase db) {
		db.execSQL("UPDATE "+TABLE_SYSTEM+" SET value='active' WHERE name='app_state'");
	}
	
	public void approveVoidPayment(SQLiteDatabase db, String objid) {
		db.execSQL("UPDATE "+TABLE_VOID+" SET state='APPROVED' WHERE objid='"+objid+"'");
	}
	
	public void approveSpecialCollection(SQLiteDatabase db, String objid) {
		db.execSQL("UPDATE "+TABLE_SPECIALCOLLECTION+" SET state='APPROVED' WHERE objid='"+objid+"'");
	}

	public void approvePayment(SQLiteDatabase db, String objid) {
		db.execSQL("UPDATE "+TABLE_PAYMENT+" SET state='APPROVED' WHERE objid='"+objid+"'");
	}
	
	public void approveNote(SQLiteDatabase db, String objid) {
		db.execSQL("UPDATE "+TABLE_NOTES+" SET state='APPROVED' WHERE objid='"+objid+"'");
	}

	public void approveRemark(SQLiteDatabase db, String loanappid) {
		db.execSQL("UPDATE "+TABLE_REMARKS+" SET state='APPROVED' WHERE loanappid='"+loanappid+"'");
	}
	
	public void remitRouteByCode(SQLiteDatabase db, String routecode) {
		db.execSQL("UPDATE "+TABLE_ROUTE+" SET state='REMITTED' WHERE routecode='"+routecode+"'");
	}
	
	/*public Cursor findSessionById(String objid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SESSION+" WHERE objid='"+objid+"'", null);
		
		if (result != null && result.getCount() > 0) result.moveToFirst();
		return result;
	}*/
	
	public Cursor findRouteByCode(SQLiteDatabase db, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE+" WHERE routecode='"+routecode+"'", null);
		
		if (result != null && result.getCount() > 0) result.moveToFirst();
		return result;
	}
	
	public boolean hasDownloadedCollectionsheet(SQLiteDatabase db, String collectorid) {
		String sql = "SELECT route.routecode " +
				"FROM "+TABLE_ROUTE+" route " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON route.routecode=collectionsheet.routecode " +
				"WHERE route.collectorid='"+collectorid+"'" +
				"LIMIT 1";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null && result.getCount() > 0) return true;
		return false;
	}
	
	public boolean hasUnpostedPayments(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT objid FROM "+TABLE_PAYMENT+" WHERE state='PENDING' LIMIT 1", null);
		
		if (result != null && result.getCount() > 0) return true;
		return false;
	}
	
	public boolean hasUnpostedRemarks(SQLiteDatabase db) {
		boolean flag = false;
		Cursor result = db.rawQuery("SELECT loanappid FROM "+TABLE_REMARKS+" WHERE state='PENDING' LIMIT 1", null);
		if (flag == false && result != null && result.getCount() > 0) flag = true;
		
		result = db.rawQuery("SELECT loanappid FROM "+TABLE_REMARKS_REMOVED+" WHERE state='PENDING' LIMIT 1", null);
		if (flag == false && result != null && result.getCount() > 0) flag = true;
		return flag;
	}
	
	public boolean hasUnremittedCollections(SQLiteDatabase db) {
		String sql = "SELECT route.routecode " +
				"FROM "+TABLE_COLLECTIONSHEET+" collectionsheet " +
				"INNER JOIN "+TABLE_ROUTE+" route ON collectionsheet.routecode=route.routecode " +
				"LEFT JOIN "+TABLE_PAYMENT+" payment ON collectionsheet.loanappid=payment.loanappid " +
				"LEFT JOIN "+TABLE_REMARKS+" remarks ON collectionsheet.loanappid=remarks.loanappid " +
				"WHERE route.state <> 'REMITTED' " +
				"AND (payment.state IS NOT NULL OR remarks.state IS NOT NULL)" +
				"LIMIT 1";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null && result.getCount() > 0) return true;
		return false;
	}
	
	public boolean hasUnremittedCollections(SQLiteDatabase db, String routecode) {
		String sql = "";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null && result.getCount() > 0) return false;
		return false;
	}
	
	/*public ArrayList<String> getSessionid() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SESSION, null);
		
		ArrayList<String> sessionidList = new ArrayList<String>();
		if(result != null && result.getCount() > 0) {
			result.moveToFirst();
			do {
				sessionidList.add(result.getString(result.getColumnIndex("objid")));
			} while(result.moveToNext());
			return sessionidList;
		}
		return sessionidList;
	}*/
	
	public String getPort(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_port'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getOnlineHost(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_online'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getOfflineHost(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='host_offline'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getCollectorUsername(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collector_username'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		} 
		return "";
	}
	
	public String getCollectorPassword(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collector_password'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getCollectorName(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collector_name'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getCollectorState(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collector_state'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}

	public String getCollectorid(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='collectorid'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public int getTrackerTimeout(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_tracker'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return Integer.parseInt(result.getString(result.getColumnIndex("value")));
		}
		return 1;
	}
	
	public int getUploadTimeout(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_upload'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return Integer.parseInt(result.getString(result.getColumnIndex("value")));
		}
		return 1;
	}
	
	public int getSessionTimeout(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='timeout_session'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return Integer.parseInt(result.getString(result.getColumnIndex("value")));
		}
		return 1;
	}

	public String getServerDate(SQLiteDatabase db, String collectorid) {
		String sql = "SELECT * FROM "+TABLE_COLLECTION_DATE+" " +
				"WHERE collectorid='"+collectorid+"'";
		Cursor result = db.rawQuery(sql, null);
		//Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='serverdate'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("serverdate"));
			/*Object date = result.getString(result.getColumnIndex("serverdate"));
			if(!(date instanceof Date))
				return (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date.toString());
			return ((Date) date);*/
		}	
		return "";
	}
	
	public String getTrackerid(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='trackerid'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public String getAppState(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='app_state'", null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("value"));
		}
		return "";
	}
	
	public Cursor getSystemByName(SQLiteDatabase db, String name) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SYSTEM+" WHERE name='"+name+"'", null);
		
		if (result != null && result.getCount() > 0) result.moveToFirst();
		return result;
	}
	
	public Cursor getHost(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_HOST, null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getLocationTrackerByCollectorid(SQLiteDatabase db, String collectorid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_LOCATION_TRACKER+" WHERE collectorid='"+collectorid+"' ORDER BY seqno", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRemittedRoutesByCollectorid(SQLiteDatabase db, String collectorid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE+" WHERE state='REMITTED' AND collectorid='"+collectorid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRemittedRoutes(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE+" WHERE state='REMITTED'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheets(SQLiteDatabase db) {
		Cursor result=db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" ORDER BY acctname", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingVoidPayments(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE state='PENDING'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingVoidPaymentsByAppid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE state='PENDING' AND loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getVoidPaymentByPaymentidAndAppid(SQLiteDatabase db, String paymentid, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE paymentid='"+paymentid+"' AND loanappid='"+loanappid+"' LIMIT 1", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheetsByRoute(SQLiteDatabase db, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE routecode='"+routecode+"' ORDER BY seqno", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheetByLoanappid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public int getTotalCollectionSheetsByRoutecode(SQLiteDatabase db, String routecode) {
		String sql = "SELECT COUNT(DISTINCT(collectionsheet.loanappid)) " +
				"FROM "+TABLE_COLLECTIONSHEET+" collectionsheet " +
				"LEFT JOIN "+TABLE_REMARKS+" remarks ON collectionsheet.loanappid=remarks.loanappid " +
				"LEFT JOIN "+TABLE_PAYMENT+" payment ON collectionsheet.loanappid=payment.loanappid " +
				"WHERE collectionsheet.routecode='"+routecode+"' " +
				"AND (payment.state IS NOT NULL OR remarks.state IS NOT NULL)";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) return result.getCount();
		return 0;
	}
	
	public BigDecimal getTotalCollectionsByRoutecode(SQLiteDatabase db, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE routecode='"+routecode+"'", null);

		BigDecimal total = new BigDecimal("0").setScale(2);
		if (result != null) {
			result.moveToFirst();
			double amount = 0.00;
			do {
				amount = result.getDouble(result.getColumnIndex("paymentamount"));
				total = total.add(new BigDecimal(amount+"").setScale(2));
			} while(result.moveToNext());
		}
		return total;
	}
	
	public Cursor searchCollectionSheets(SQLiteDatabase db, String searchtext, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE acctname LIKE '%"+searchtext+"%' AND routecode='"+routecode+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public int countLocationTrackerByCollectorid(SQLiteDatabase db, String collectorid) {
		Cursor result = db.rawQuery("SELECT objid FROM "+TABLE_LOCATION_TRACKER+" WHERE collectorid='"+collectorid+"'", null);
		
		if (result != null && result.getCount() > 0) return result.getCount();
		return 0;
	}

	public int countVoidPaymentsByAppid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT objid FROM "+TABLE_VOID+" WHERE loanappid='"+loanappid+"'", null);
		
		if (result != null) return result.getCount();
		return 0;
	}
	
	public int countCollectionSheetsByRoutecode(SQLiteDatabase db, String routecode) {
		Cursor result = db.rawQuery("SELECT detailid FROM "+TABLE_COLLECTIONSHEET+" WHERE routecode='"+routecode+"'", null);
		
		if (result != null) return result.getCount();
		return 0;
	}
	
	public Cursor getRemarks(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_REMARKS, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRemarksByAppid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_REMARKS+" WHERE loanappid='"+loanappid+"' LIMIT 1", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingRemarksByRoutecode(SQLiteDatabase db, String routecode) {
		String sql = "SELECT remarks.* " +
				"FROM "+TABLE_REMARKS+" remarks " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON remarks.loanappid=collectionsheet.loanappid " +
				"WHERE remarks.state='PENDING' AND collectionsheet.routecode='"+routecode+"'";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingRemarks(SQLiteDatabase db) {
		String sql = "SELECT remarks.*, collectionsheet.detailid," +
				"collectionsheet.sessionid, collectionsheet.routecode " +
				"FROM "+TABLE_REMARKS+" remarks" +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON remarks.loanappid=collectionsheet.loanappid" +
				"WHERE remarks.state='PENDING'";
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_REMARKS+" WHERE state='PENDING'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingRemarksRemoved(SQLiteDatabase db) {
		String sql = "SELECT remarksRemoved.*, collectionsheet.detailid " +
				"FROM "+TABLE_REMARKS_REMOVED+" remarksRemoved " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON remarksRemoved.loanappid=collectionsheet.loanappid " +
				"WHERE remarksRemoved.state='PENDING'";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
 	
	public boolean isNoteOverlapping(SQLiteDatabase db, String fromdate, String todate, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES+" WHERE loanappid='"+loanappid+"' AND '"+fromdate+"' BETWEEN fromdate AND todate", null);
		if (result != null && result.getCount() > 0) return true;
		
		result = db.rawQuery("SELECT * FROM "+TABLE_NOTES+" WHERE loanappid='"+loanappid+"' AND '"+todate+"' BETWEEN fromdate AND todate", null);
		if (result != null && result.getCount() > 0) return true;
		return false;
	}
	
	public Cursor getNotes(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingNotesByRoutecode(SQLiteDatabase db, String routecode) {
		String sql = "SELECT notes.* " +
				"FROM "+TABLE_NOTES+" notes " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON notes.loanappid=collectionsheet.loanappid " +
				"WHERE notes.state='PENDING' AND collectionsheet.routecode ='"+routecode+"'";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingNotes(SQLiteDatabase db) {
		String sql = "SELECT note.*, collectionsheet.detailid, " +
				"collectionsheet.sessionid, collectionsheet.routecode " +
				"FROM "+TABLE_NOTES+" note " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON note.loanappid=collectionsheet.loanappid " +
				"WHERE note.state='PENDING'";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	/*public Cursor getPendingNotesRemoved(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES_REMOVED+" WHERE state='PENDING'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}*/
	
	public Cursor getNotesByAppid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES+" WHERE loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getSpecialCollections(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SPECIALCOLLECTION, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getSpecialCollectionById(SQLiteDatabase db, String objid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_SPECIALCOLLECTION+" WHERE objid='"+objid+"' LIMIT 1", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPayments(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingPaymentsByRoutecode(SQLiteDatabase db, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE state='PENDING' AND routecode='"+routecode+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingPayments(SQLiteDatabase db) {
		String sql = "SELECT payment.*, collectionsheet.acctname, collectionsheet.detailid, " +
				"collectionsheet.sessionid " +
				"FROM "+TABLE_PAYMENT+" payment " +
				"INNER JOIN "+TABLE_COLLECTIONSHEET+" collectionsheet ON payment.loanappid=collectionsheet.loanappid " +
				"WHERE payment.state='PENDING'";
		Cursor result = db.rawQuery(sql, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	/*public Cursor getPayments(String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE routecode='"+routecode+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}*/
	
	public Cursor getPaymentsByAppid(SQLiteDatabase db, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE loanappid='"+loanappid+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public int getTotalCollectionSheetsForUpload(SQLiteDatabase db) {
		String sql = "SELECT cs.loanappid AS objid, p.objid AS paymentid, n.objid AS notesid, r.loanappid AS remarksid " +
				"FROM "+TABLE_COLLECTIONSHEET+" cs "+
				"LEFT JOIN "+TABLE_PAYMENT+" p ON cs.loanappid=p.loanappid " +
				"LEFT JOIN "+TABLE_NOTES+" n ON cs.loanappid=n.loanappid " +
				"LEFT JOIN "+TABLE_REMARKS+" r ON cs.loanappid=r.loanappid";		
		Cursor result = db.rawQuery(sql, null);
		int total = 0;
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			String paymentid = "";
			String notesid = "";
			String remarksid = "";
			boolean forupload = false;
			do {
				forupload = false;
				paymentid = result.getString(result.getColumnIndex("paymentid"));
				notesid = result.getString(result.getColumnIndex("notesid"));
				remarksid = result.getString(result.getColumnIndex("remarksid"));
				if (forupload == false && paymentid != null && !paymentid.equals("")) forupload = true;
				if (forupload == false && notesid != null && !notesid.equals("")) forupload = true;
				if (forupload == false && remarksid != null && !remarksid.equals("")) forupload = true;
				if (forupload == true) total++;
			} while(result.moveToNext());
		}
		return total;
	}
	
	public Cursor getPostedCollectionSheets(SQLiteDatabase db, String searchtext, String collectorid) {
		if (searchtext == null || searchtext.trim().equals("")) {
			searchtext = "%";
		} else searchtext += "%";
		String sql = "SELECT DISTINCT(collectionsheet.acctname) " +
				"FROM "+TABLE_COLLECTIONSHEET+" collectionsheet " +
				"INNER JOIN "+TABLE_ROUTE+" route ON collectionsheet.routecode=route.routecode " +
				"LEFT JOIN "+TABLE_PAYMENT+" payment ON collectionsheet.loanappid=payment.loanappid " +
				"LEFT JOIN "+TABLE_REMARKS+" remarks ON collectionsheet.loanappid=remarks.loanappid " +
				"WHERE route.collectorid='"+collectorid+"' " +
				"AND collectionsheet.acctname LIKE '"+searchtext+"' " +
				"AND (" +
				"(payment.state='ACTIVE' AND remarks.state='APPROVED') " +
				"OR (payment.state IS NOT NULL AND payment.state='APPROVED') " +
				"OR (remarks.state IS NOT NULL AND remarks.state='APPROVED')" +
				")" +
				"ORDER BY collectionsheet.acctname";
		Cursor result = db.rawQuery(sql, null);
				
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getUnpostedCollectionSheets(SQLiteDatabase db, String searchtext, String collectorid) {
		if (searchtext == null || searchtext.trim().equals("")) {
			searchtext = "%";
		} else searchtext += "%";
		String sql = "SELECT DISTINCT(collectionsheet.acctname) " +
				"FROM "+TABLE_COLLECTIONSHEET+" collectionsheet " +
				"INNER JOIN "+TABLE_ROUTE+" route ON collectionsheet.routecode=route.routecode " +
				"LEFT JOIN "+TABLE_PAYMENT+" payment ON collectionsheet.loanappid=payment.loanappid " +
				"LEFT JOIN "+TABLE_REMARKS+" remarks ON collectionsheet.loanappid=remarks.loanappid " +
				"WHERE route.collectorid='"+collectorid+"' " +
				"AND collectionsheet.acctname LIKE '"+searchtext+"' " +
				"AND (" +
				"(payment.state IS NOT NULL AND payment.state='PENDING') " +
				"OR (remarks.state IS NOT NULL AND remarks.state='PENDING') " +
				")" +
				"ORDER BY collectionsheet.acctname";
		Cursor result = db.rawQuery(sql, null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getActiveRoutes(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE+" WHERE state='ACTIVE'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRoutes(SQLiteDatabase db) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public void emptySystemTable(SQLiteDatabase db) {
		db.delete(TABLE_SYSTEM, null, null);
	}
	
	public void removeLocationTrackerById(SQLiteDatabase db, String objid) {
		db.delete(TABLE_LOCATION_TRACKER, "objid='"+objid+"'", null);
	}
	
	public void removeCollectionsheetsByRoute(SQLiteDatabase db, String code) {
		Cursor cs = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE routecode='"+code+"'", null);
		
		if (cs != null && cs.getCount() > 0) {
			cs.moveToFirst();
			String whereClause = "loanappid='"+cs.getString(cs.getColumnIndex("loanappid"))+"'";
			do {
				db.delete(TABLE_VOID, whereClause, null);
				db.delete(TABLE_PAYMENT, whereClause, null);
				db.delete(TABLE_NOTES, whereClause, null);
			} while(cs.moveToNext());
		}
		
		db.delete(TABLE_COLLECTIONSHEET, "routecode='"+code+"'", null);
	}
	
	public void removeAllCollectionsheets(SQLiteDatabase db) {
		db.delete(TABLE_COLLECTIONSHEET, null, null);
	}
	
	public void removeCollectionsheetByLoanappid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_COLLECTIONSHEET, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllPayments(SQLiteDatabase db) {
		db.delete(TABLE_PAYMENT, null, null);
	}
	
	/*public void removePaymentByLoanappid(String loanappid) {
		db.delete(TABLE_PAYMENT, "loanappid='"+loanappid+"'", null);
	}*/
	public void removePaymentByAppid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_PAYMENT, "loanappid='"+loanappid+"'", null);
	}

	public void removePaymentById(SQLiteDatabase db, String objid) {
		db.delete(TABLE_PAYMENT, "objid='"+objid+"'", null);
	}
	
	public void removeNoteById(SQLiteDatabase db, String objid) {
		db.delete(TABLE_NOTES, "objid='"+objid+"'", null);
	}
	
	/*public void removeNotesRemovedByIdAndAppid(SQLiteDatabase db, String objid, String loanappid) {
		db.delete(TABLE_NOTES_REMOVED, "objid='"+objid+"' AND loanappid='"+loanappid+"'", null);
	}*/
	
	public void removeNotesByAppid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_NOTES, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllNotes(SQLiteDatabase db) {
		db.delete(TABLE_NOTES, null, null);
	}
	
	public void removeAllVoidPayments(SQLiteDatabase db) {
		db.delete(TABLE_VOID, null, null);
	}
	
	public void removeRemarksRemovedByAppid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_REMARKS_REMOVED, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeRemarksByAppid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_REMARKS, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllRemarks(SQLiteDatabase db) {
		db.delete(TABLE_REMARKS, null, null);
	}
	
	/*public void removeAllUploadedPayments() {
		db.delete(TABLE_UPLOADEDPAYMENT, null, null);
	}*/
	/*public void removeAllUploads() {
		db.delete(TABLE_UPLOADS, null, null);
	}*/
	
	public void removeRouteByCode(SQLiteDatabase db, String code) {
		db.delete(TABLE_ROUTE, "code ='"+code+"'", null);
	}
	
	public void removeAllRoutes(SQLiteDatabase db) {
		db.delete(TABLE_ROUTE, null, null);
	}
	
	/*public void removeAllSessions() {
		db.delete(TABLE_SESSION, null, null);
	}*/
	
	public void removeAllSpecialCollections(SQLiteDatabase db) {
		db.delete(TABLE_SPECIALCOLLECTION, null, null);
	}
	
	public void removeVoidPaymentByAppid(SQLiteDatabase db, String loanappid) {
		db.delete(TABLE_VOID, "loanappid='"+loanappid+"'", null);
	}
	
	public boolean isTableExist(SQLiteDatabase db, String tablename) {
		//db = this.getReadableDatabase();
		Cursor result = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tablename+"'", null);
		//
		if(result.getCount() > 0) return true;
		return false;
	}
}
