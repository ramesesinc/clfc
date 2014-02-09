package com.rameses.clfc.android.db;

import com.rameses.db.android.DBContext;

public class DBPaymentService extends AbstractDBMapper 
{

	public String getTableName() { return "payment"; }
	
	public boolean hasUnpostedTransactions() throws Exception {		
		DBContext ctx = createDBContext();
		try {
			String sql = "SELECT objid FROM "+ getTableName() +" WHERE state='PENDING' LIMIT 1";
			if (ctx.getCount(sql, new Object[]{}) > 0) return true;
			
			sql = "SELECT loanappid FROM remarks WHERE state='PENDING' LIMIT 1";
			if (ctx.getCount(sql, new Object[]{}) > 0) return true;
			
			sql = " SELECT r.routecode FROM collectionsheet cs " +
				  " 	INNER JOIN route r ON cs.routecode=r.routecode " +
				  " 	LEFT JOIN payment p ON cs.loanappid=p.loanappid " +
				  " 	LEFT JOIN remarks m ON cs.loanappid=m.loanappid " +
				  " WHERE r.state <> 'REMITTED' AND " +
				  " 	(p.state IS NOT NULL OR m.state IS NOT NULL) " +
				  " LIMIT 1 ";			
			return (ctx.getCount(sql, new Object[]{}) > 0);
		} catch(Exception e) {
			throw e; 
		} finally {
			ctx.close(); 
		}		
	}
	
}
