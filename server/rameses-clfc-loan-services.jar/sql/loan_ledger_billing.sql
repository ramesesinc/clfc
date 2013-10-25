[getList]
SELECT * FROM loan_ledger_billing

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