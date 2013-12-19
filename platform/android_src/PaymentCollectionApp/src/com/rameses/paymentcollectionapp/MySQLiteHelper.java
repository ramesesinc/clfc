package com.rameses.paymentcollectionapp;

import java.math.BigDecimal;
import java.text.ParseException;
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
	private static final String TABLE_NOTES = "notes";
	private static final String TABLE_UPLOADS = "uploads";
	//private static final String TABLE_UPLOADEDPAYMENTS = "uploaded_payments";
	private static final String TABLE_SYSTEM = "system";
	private static final String TABLE_HOST = "host";
	private static final String TABLE_ROUTE = "route";
	private static final String TABLE_VOID = "void";
	
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
			"collectionaddress text" +
			");";
	private static final String CREATE_TABLE_PAYMENT = "" +
			"CREATE TABLE PAYMENT(" +
			"objid text PRIMARY KEY, " +
			"refno text, " +
			"txndate text, " +
			"paymenttype text, " +
			"paymentamount numeric, " +
			"loanappid text, " +
			"detailid text, "+
			"routecode text, " +
			"isfirstbill numeric" +
			");";
	private static final String CREATE_TABLE_REMARKS = "CREATE TABLE REMARKS(loanappid text PRIMARY KEY, remarks text);";
	private static final String CREATE_TABLE_NOTES = "CREATE TABLE NOTES(" +
			"objid text PRIMARY KEY, " +
			"loanappid text, " +
			"fromdate text, " +
			"todate text, " +
			"remarks text" +
			");";
	private static final String CREATE_TABLE_UPLOADS = "CREATE TABLE UPLOADS(" +
			"loanappid text, " +
			"referenceid text, " +
			"PRIMARY KEY(loanappid, referenceid)" +
			");";
	private static final String CREATE_TABLE_SYSTEM = "CREATE TABLE SYSTEM(" +
			"sessionid text, " +
			"serverdate text, " +
			"collectorid text" +
			")";
	private static final String CREATE_TABLE_HOST = "CREATE TABLE HOST(" +
			"ipaddress text, " +
			"port text" +
			");";
	private static final String CREATE_TABLE_ROUTE = "CREATE TABLE ROUTE(" +
			"routecode text PRIMARY KEY, " +
			"routedescription text, " +
			"routearea text" +
			");";
	private static final String CREATE_TABLE_VOID = "CREATE TABLE VOID(" +
			"objid text PRIMARY KEY, " +
			"paymentid text, " +
			"loanappid text, " +
			"state text, " +
			"collectorid text, " +
			"reason text" +
			");";
	
	
	private SQLiteDatabase db;
	public boolean isOpen = false;
	
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
		db.execSQL(CREATE_TABLE_NOTES);
		//db.execSQL(CREATE_TABLE_UPLOADEDPAYMENT);
		db.execSQL(CREATE_TABLE_UPLOADS);
		db.execSQL(CREATE_TABLE_SYSTEM);
		db.execSQL(CREATE_TABLE_HOST);
		db.execSQL(CREATE_TABLE_ROUTE);
		db.execSQL(CREATE_TABLE_VOID);
		db.execSQL("INSERT INTO "+TABLE_HOST+" VALUES('121.97.60.200', '8070')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("Database", "onupgrade");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_COLLECTIONSHEET);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_PAYMENT);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_REMARKS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
		//db.execSQL("DROP TABLE IF EXISTS "+TABLE_UPLOADEDPAYMENT);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_UPLOADS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_HOST);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROUTE);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_VOID);
		onCreate(db);
	}

	public void insertCollectionsheet(Map<String, Object> params) {
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
		db.insert(TABLE_COLLECTIONSHEET, null, values);
	}
	
	public void insertPayment(Map<String, Object> params) {
		ContentValues values=new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("detailid", params.get("detailid").toString());
		values.put("refno", params.get("refno").toString());
		values.put("txndate", params.get("txndate").toString());
		values.put("paymentamount", Double.parseDouble(params.get("amount").toString()));
		values.put("paymenttype", params.get("type").toString());
		values.put("routecode", params.get("routecode").toString());
		values.put("isfirstbill", Integer.parseInt(params.get("isfirstbill").toString()));
		db.insert(TABLE_PAYMENT, null, values);
	}
	
	public void insertRemarks(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("loanappid", params.get("loanappid").toString());
		values.put("remarks", params.get("remarks").toString());
		db.insert(TABLE_REMARKS, null, values);
	}
	
	public void updateRemarks(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		String loanappid = params.get("loanappid").toString();
		values.put("remarks", params.get("remarks").toString());
		db.update(TABLE_REMARKS, values, "loanappid='"+loanappid+"'", null);
	}
	
	public void insertNotes(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("fromdate", params.get("fromdate").toString());
		values.put("todate", params.get("todate").toString());
		values.put("remarks", params.get("remarks").toString());
		db.insert(TABLE_NOTES, null, values);
	}
	
	public void updateNotes(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		String objid = params.get("objid").toString();
		//values.put("objid", objid);
		values.put("loanappid", params.get("loanappid").toString());
		values.put("fromdate", params.get("fromdate").toString());
		values.put("todate", params.get("todate").toString());
		values.put("remarks", params.get("remarks").toString());
		db.update(TABLE_NOTES, values, "objid='"+objid+"'", null);
	}
	
	/*public void insertUploadedPayment(String loanappid) {
		ContentValues values=new ContentValues();
		values.put("loanappid", loanappid);
		db.insert(TABLE_UPLOADEDPAYMENT, null, values);
	}*/
	public void insertUploads(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("loanappid", params.get("loanappid").toString());
		values.put("referenceid", params.get("referenceid").toString());
		db.insert(TABLE_UPLOADS, null, values);
	}
	
	public void insertSystem(Map<String, Object> params) {
		ContentValues values=new ContentValues();
		values.put("sessionid", params.get("sessionid").toString());
		values.put("serverdate", params.get("serverdate").toString());
		values.put("collectorid", params.get("collectorid").toString());
		db.insert(TABLE_SYSTEM, null, values);
	}
	
	public void insertHost(String ipaddress, String port) {
		ContentValues values=new ContentValues();
		values.put("ipaddress", ipaddress);
		values.put("port", port);
		db.insert(TABLE_HOST, null, values);
	}
	
	public void insertRoute(Map<String, String> params) {
		ContentValues values=new ContentValues();
		values.put("routecode", params.get("routecode").toString());
		values.put("routedescription", params.get("routedescription").toString());
		values.put("routearea", params.get("routearea").toString());
		db.insert(TABLE_ROUTE, null, values);
	}
	
	public void insertVoidPayment(Map<String, Object> params) {
		ContentValues values = new ContentValues();
		values.put("objid", params.get("objid").toString());
		values.put("state", params.get("state").toString());
		values.put("paymentid", params.get("paymentid").toString());
		values.put("loanappid", params.get("loanappid").toString());
		values.put("collectorid", params.get("collectorid").toString());
		values.put("reason", params.get("reason").toString());
		db.insert(TABLE_VOID, null, values);
	}
	
	public void approveVoidPayment(String objid) {
		db.execSQL("UPDATE FROM "+TABLE_VOID+" SET state='APPROVED' WHERE objid='"+objid+"'");
	}
	
	public void updateHost(String ipaddress, String port) {
		ContentValues values=new ContentValues();
		values.put("ipaddress", ipaddress);
		values.put("port", port);
		db.update(TABLE_HOST, values, null, null);
	}
	
	public String getSessionid() {
		Cursor result = db.rawQuery("SELECT sessionid FROM "+TABLE_SYSTEM, null);
		
		if(result != null) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("sessionid"));
		}
		return null;
	}
	
	public String getCollectorid() {
		Cursor result = db.rawQuery("SELECT collectorid FROM "+TABLE_SYSTEM, null);
		
		if (result != null) {
			result.moveToFirst();
			return result.getString(result.getColumnIndex("collectorid"));
		}
		return null;
	}
	
	public Date getServerDate() throws ParseException {
		Cursor result = db.rawQuery("SELECT serverdate FROM "+TABLE_SYSTEM, null);
		
		if (result != null && result.getCount() > 0) {
			result.moveToFirst();
			Object date = result.getString(result.getColumnIndex("serverdate"));
			if(!(date instanceof Date))
				return (new java.text.SimpleDateFormat("yyyy-MM-dd")).parse(date.toString());
			return ((Date) date);
		}	
		return null;
	}
	
	public Cursor getHost() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_HOST, null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheets() {
		Cursor result=db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" ORDER BY acctname", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingVoidPayments() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE state='PENDING'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getPendingVoidPaymentsByAppid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE state='PENDING' AND loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getVoidPaymentByPaymentidAndAppid(String paymentid, String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_VOID+" WHERE paymentid='"+paymentid+"' AND loanappid='"+loanappid+"' LIMIT 1", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheetsByRoute(String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE routecode='"+routecode+"' ORDER BY seqno", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getCollectionsheetByLoanappid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE loanappid='"+loanappid+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor searchCollectionSheets(String searchtext, String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_COLLECTIONSHEET+" WHERE acctname LIKE '%"+searchtext+"%' AND routecode='"+routecode+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRemarks() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_REMARKS, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getRemarksByAppid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_REMARKS+" WHERE loanappid='"+loanappid+"' LIMIT 1", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getNotes() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getNotesByAppid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NOTES+" WHERE loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}	
	
	public Cursor getPayments() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	/*public Cursor getPayments(String routecode) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE routecode='"+routecode+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}*/
	
	public Cursor getPaymentsByAppid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_PAYMENT+" WHERE loanappid='"+loanappid+"'", null);
		
		if(result != null) result.moveToFirst();
		return result;
	}
	
	/*public Cursor getUploadedPayments() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_UPLOADEDPAYMENT, null);
		
		if(result != null) result.moveToFirst();
		return result;
	}*/
	public Cursor getUploads() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_UPLOADS, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getUploadsByAppid(String loanappid) {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_UPLOADS+" WHERE loanappid='"+loanappid+"'", null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public Cursor getUpload(String loanappid, String referenceid) {
		Cursor result = db.rawQuery("SELECT * FORM "+TABLE_UPLOADS+" WHERE loanappid='"+loanappid+"' AND referenceid='"+referenceid+"'", null);

		if (result != null) result.moveToFirst();
		return result;
	}
	
	public int getTotalCollectionSheetsForUpload() {
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
	
	public Cursor getRoutes() {
		Cursor result = db.rawQuery("SELECT * FROM "+TABLE_ROUTE, null);
		
		if (result != null) result.moveToFirst();
		return result;
	}
	
	public void emptySystemTable() {
		db.delete(TABLE_SYSTEM, null, null);
	}
	
	public void removeCollectionsheetsByRoute(String code) {
		db.delete(TABLE_COLLECTIONSHEET, "routecode='"+code+"'", null);
	}
	
	public void removeAllCollectionsheets() {
		db.delete(TABLE_COLLECTIONSHEET, null, null);
	}
	
	public void removeCollectionsheetByLoanappid(String loanappid) {
		db.delete(TABLE_COLLECTIONSHEET, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllPayments() {
		db.delete(TABLE_PAYMENT, null, null);
	}
	
	/*public void removePaymentByLoanappid(String loanappid) {
		db.delete(TABLE_PAYMENT, "loanappid='"+loanappid+"'", null);
	}*/
	public void removePaymentByAppid(String loanappid) {
		db.delete(TABLE_PAYMENT, "loanappid='"+loanappid+"'", null);
	}

	public void removePaymentById(String objid) {
		db.delete(TABLE_PAYMENT, "objid='"+objid+"'", null);
	}
	
	public void removeNoteById(String objid) {
		db.delete(TABLE_NOTES, "objid='"+objid+"'", null);
	}
	
	public void removeNotesByAppid(String loanappid) {
		db.delete(TABLE_NOTES, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllNotes() {
		db.delete(TABLE_NOTES, null, null);
	}
	
	public void removeAllVoidPayments() {
		db.delete(TABLE_VOID, null, null);
	}
	
	public void removeRemarksByAppid(String loanappid) {
		db.delete(TABLE_REMARKS, "loanappid='"+loanappid+"'", null);
	}
	
	public void removeAllRemarks() {
		db.delete(TABLE_REMARKS, null, null);
	}
	
	/*public void removeAllUploadedPayments() {
		db.delete(TABLE_UPLOADEDPAYMENT, null, null);
	}*/
	public void removeAllUploads() {
		db.delete(TABLE_UPLOADS, null, null);
	}
	
	public void removeAllRoutes() {
		db.delete(TABLE_ROUTE, null, null);
	}
	
	public void openDb() throws SQLException {
		db = this.getWritableDatabase();
		isOpen = true;
	}
	
	public void closeDb() {
		this.close();
		isOpen = false;
	}
	
	public boolean isTableExist(String tablename) {
		//db = this.getReadableDatabase();
		Cursor result = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tablename+"'", null);
		//
		if(result.getCount() > 0) return true;
		return false;
	}
}
