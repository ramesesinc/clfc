[getList]
SELECT * FROM loan_ledger_billing
WHERE (createdby LIKE $P{searchtext} OR collector_username LIKE $P{searchtext})
	AND state=$P{state}
ORDER BY billdate

[getDefaultList]
SELECT * FROM loan_ledger_billing
WHERE createdby LIKE $P{searchtext} OR collector_username LIKE $P{searchtext}
ORDER BY billdate

[getRoutesByBillingid]
SELECT lr.* FROM loan_ledger_billing_route llbr
INNER JOIN loan_route lr ON llbr.routecode=lr.code
WHERE llbr.billingid=$P{billingid}

[removeRouteByBillingid]
DELETE FROM loan_ledger_billing_route WHERE billingid=$P{billingid}

[removeBillingDetailByParentid]
DELETE FROM loan_ledger_billing_detail WHERE parentid=$P{parentid}

[findBillingByCollectorid]
SELECT * FROM loan_ledger_billing WHERE collector_objid=$P{collectorid} AND billdate=$P{billdate}

[getRoutesByCollectorid]
SELECT lr.* 
FROM loan_ledger_billing lb
 INNER JOIN loan_ledger_billing_route lbr ON lb.objid=lbr.billingid
 INNER JOIN loan_route lr ON lbr.routecode=lr.code
WHERE lb.collector_objid=$P{collectorid}

[findBillingByBilldate]
SELECT * FROM loan_ledger_billing WHERE billdate=$P{billdate}

[getBillingDetailByRoutecode]
SELECT * FROM loan_ledger_billing_detail WHERE parentid=$P{billingid} AND route_code=$P{route_code}

[findBillingLock]
SELECT * FROM loan_ledger_billing_lock WHERE billingid=$P{billingid} AND routecode=$P{routecode}

[removeBillingLockByBillingid]
DELETE FROM loan_ledger_billing_lock WHERE billingid=$P{billingid}

[findAvgOverpaymentAmount]
SELECT MAX(sq.count) AS max, sq.groupbaseamount FROM (
	SELECT COUNT(ll.groupbaseamount) AS count, ll.groupbaseamount 
	FROM loan_ledger_detail ll
	WHERE ll.parentid=$P{parentid}
	GROUP BY ll.groupbaseamount
) sq
GROUP BY sq.groupbaseamount
LIMIT 1

[getStates]
SELECT DISTINCT state 
FROM loan_ledger_billing

[changeStateCompleted]
UPDATE loan_ledger_billing SET state="COMPLETED"
WHERE objid=$P{objid}