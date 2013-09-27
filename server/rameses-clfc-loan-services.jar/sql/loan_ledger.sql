[findLoanCountByAcctid]
SELECT IFNULL(MAX(loancount),0) AS loancount 
FROM loan_ledger WHERE acctid=$P{acctid} 

[getCollectionsheets]
SELECT la.objid AS loanappid, la.appno AS appno, 
		ll.acctname AS acctname, ll.dailydue AS dailydue,
		ll.dtlastpaid AS dtlastpaid
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
WHERE dtpaid IS NOT NULL
	AND dtpaid < $P{dtpaid}
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