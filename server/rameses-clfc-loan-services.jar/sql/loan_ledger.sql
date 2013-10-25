[findLoanCountByAcctid]
SELECT IFNULL(MAX(loancount),0) AS loancount 
FROM loan_ledger WHERE acctid=$P{acctid} 

[getCollectionsheets]
SELECT ll.objid AS objid, la.objid AS loanappid, la.appno AS appno, 
		ll.acctname AS acctname, ll.dailydue AS dailydue,
		ll.dtlastpaid AS dtlastpaid, ll.dtstarted AS dtstarted,
		ll.overduepenalty AS overduepenalty, ll.dtmatured AS dtmatured,
		ll.producttypeid AS producttypeid, ll.balance AS balance,
		ll.overpaymentamount AS overpaymentamount, la.loanamount AS loanamount,
		ll.absentpenalty AS absentpenalty, ll.dtmatured AS dtmatured
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid = la.objid
WHERE la.route_code=$P{route_code}

[findByAppId]
SELECT * FROM loan_ledger
WHERE appid=$P{appid}

[findLastLedgerItemByParentId]
SELECT * FROM loan_ledger_detail
WHERE parentid=$P{parentid}
ORDER BY day DESC, dtpaid DESC
LIMIT 1

[updateDtlastpaid]
UPDATE loan_ledger SET dtlastpaid=$P{dtlastpaid} WHERE objid=$P{objid}

[findLastLedgerItemNotSameDatePaid]
SELECT * FROM loan_ledger_detail
WHERE dtpaid < $P{dtpaid}
	AND parentid=$P{parentid}
ORDER BY dtpaid DESC
LIMIT 1

[getList]
SELECT ll.objid AS ledgerid, la.borrower_objid AS borrower_objid,
	la.objid AS appid, la.borrower_name AS borrower_name, 
	ll.producttypeid AS producctypeid, la.appno AS appno, 
	la.route_code AS route_code, ll.dailydue AS dailydue,
	ll.dtstarted AS dtstarted, ll.dtmatured AS dtmatured,
	ll.interestamount AS interest
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid = la.objid
WHERE la.state IN('RELEASED','CLOSED')
	AND la.borrower_name LIKE $P{searchtext}

[getLedgerDetailsByLedgerid]
SELECT * FROM loan_ledger_detail
WHERE parentid=$P{parentid}
ORDER BY day, refno, txndate, state

[getPaymentsFromLedgerDetail]
SELECT ll.appid AS appid, lld.refno, lld.dtpaid AS txndate, 
	lld.paytype, lld.amtpaid AS payamount
FROM loan_ledger_detail lld
INNER JOIN loan_ledger ll ON lld.parentid=ll.objid
WHERE lld.paytype IS NOT NULL
	AND lld.parentid=$P{parentid}
ORDER BY lld.dtpaid, lld.refno

[removeLedgerDetail]
DELETE FROM loan_ledger_detail
WHERE parentid=$P{parentid}
