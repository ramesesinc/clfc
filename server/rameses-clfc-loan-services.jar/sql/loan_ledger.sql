[findLoanCountByAcctid]
SELECT IFNULL(MAX(loancount),0) AS loancount 
FROM loan_ledger WHERE acctid=$P{acctid} 

[getCollectionsheets]
SELECT ll.objid, la.objid AS loanappid, la.appno, ll.acctname, ll.dailydue,
		ll.dtlastpaid, ll.dtstarted, ll.overduepenalty, ll.dtmatured,
		ll.producttypeid, ll.balance, ll.overpaymentamount, la.loanamount,
		ll.absentpenalty, ll.dtmatured, ll.paymentmethod, ll.interestamount,
		ll.dtcurrentschedule, la.dtcreated AS loandate, ll.term, ll.acctid,
		c.address AS homeaddress
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid=la.objid
INNER JOIN customer c ON c.objid=ll.acctid
WHERE la.route_code=$P{route_code}
	AND ll.state='OPEN'

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
	ll.interestamount AS interest, ll.state AS state
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid = la.objid
WHERE la.state IN('RELEASED','CLOSED')
	AND la.borrower_name LIKE $P{searchtext}

[getLedgerDetailsByLedgerid]
SELECT * FROM loan_ledger_detail
WHERE parentid=$P{parentid}
ORDER BY day, refno, txndate, state DESC

[getPaymentsFromLedgerDetail]
SELECT ll.appid AS appid, lld.refno, lld.dtpaid AS txndate, 
		lld.amtpaid AS payamount
FROM loan_ledger_detail lld
INNER JOIN loan_ledger ll ON lld.parentid=ll.objid
WHERE lld.parentid=$P{parentid} 
	AND lld.amtpaid > 0 
	AND lld.state='RECEIVED'
ORDER BY lld.dtpaid, lld.refno

[removeLedgerDetail]
DELETE FROM loan_ledger_detail
WHERE parentid=$P{parentid}

[findLedgerById]
SELECT ll.*, la.appno, la.loanamount
FROM loan_ledger ll
INNER JOIN loanapp la
ON ll.appid=la.objid
WHERE ll.objid=$P{ledgerid}

[getOpenLedgersByCurrentDateGreaterThanMaturityDate]
SELECT * FROM loan_ledger
WHERE state='OPEN'
	AND $P{date} > dtmatured