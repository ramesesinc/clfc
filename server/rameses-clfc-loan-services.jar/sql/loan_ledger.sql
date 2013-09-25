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