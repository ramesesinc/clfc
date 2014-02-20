package com.rameses.clfc.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rameses.db.android.AbstractDB;

public class VoidRequestDB extends AbstractDB 
{
	public VoidRequestDB(Context ctx, String dbname, int dbversion) {
		super(ctx, dbname, dbversion); 
	}

	protected void onCreateProcess(SQLiteDatabase sqldb) { 
		try { 
			loadDBResource(sqldb, "clfcrequestdb_create");
		} catch(RuntimeException re) {
			throw re; 
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e); 
		}
	}

	protected void onUpgradeProcess(SQLiteDatabase sqldb, int arg1, int arg2) {
		try { 
			loadDBResource(sqldb, "clfcrequestdb_upgrade");
			onCreate(sqldb); 
		} catch(RuntimeException re) {
			throw re; 
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e); 
		}		
	}
}
