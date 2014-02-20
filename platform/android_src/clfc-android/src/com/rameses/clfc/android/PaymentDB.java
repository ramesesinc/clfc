package com.rameses.clfc.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rameses.db.android.AbstractDB;

public class PaymentDB extends AbstractDB 
{
	public PaymentDB(Context ctx, String dbname, int dbversion) {
		super(ctx, dbname, dbversion); 
	}

	protected void onCreateProcess(SQLiteDatabase sqldb) { 
		try { 
			loadDBResource(sqldb, "clfcpaymentdb_create");
		} catch(RuntimeException re) {
			throw re; 
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e); 
		}
	}

	protected void onUpgradeProcess(SQLiteDatabase sqldb, int arg1, int arg2) {
		try { 
			loadDBResource(sqldb, "clfcpaymentdb_upgrade");
			onCreate(sqldb); 
		} catch(RuntimeException re) {
			throw re; 
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e); 
		}		
	}
}
