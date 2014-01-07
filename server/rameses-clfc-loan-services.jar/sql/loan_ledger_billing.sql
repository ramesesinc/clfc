[getList]
SELECT llb.*, lls.parentid AS parentid, lls.subcollector_objid, lls.subcollector_username
FROM loan_ledger_billing llb
LEFT JOIN loan_ledger_subbilling lls ON llb.objid=lls.objid
LEFT JOIN loan_ledger_specialcollection llsc ON llb.objid=llsc.billingid
WHERE (llb.createdby LIKE $P{searchtext} OR llb.collector_username LIKE $P{searchtext})
	AND llb.state=$P{state}
	AND llsc.objid IS NULL
ORDER BY llb.billdate

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

[getBillingDetailByParentid]
SELECT * FROM loan_ledger_billing_detail WHERE parentid=$P{parentid}

[getBillingByCollectorid]
SELECT llb.* 
FROM loan_ledger_billing llb
LEFT JOIN loan_ledger_subbilling lls ON llb.objid=lls.objid
LEFT JOIN loan_ledger_specialcollection llsc ON llb.objid=llsc.billingid
WHERE llb.collector_objid=$P{collectorid}
	AND lls.subcollector_objid IS NULL
	AND llb.billdate=$P{billdate}
	AND llsc.objid IS NULL
	AND llb.state='DRAFT'

[getBillingBySubCollectorid]
SELECT llb.* 
FROM loan_ledger_billing llb
LEFT JOIN loan_ledger_subbilling lls ON llb.objid=lls.objid
LEFT JOIN loan_ledger_specialcollection llsc ON llb.objid=llsc.billingid
WHERE lls.subcollector_objid=$P{subcollectorid}
	AND llb.billdate=$P{billdate}
	AND llsc.objid IS NULL
	AND llb.state='DRAFT'

[getRoutesByCollectorid]
SELECT lr.* 
FROM loan_ledger_billing lb
 INNER JOIN loan_ledger_billing_route lbr ON lb.objid=lbr.billingid
 INNER JOIN loan_route lr ON lbr.routecode=lr.code
WHERE lb.collector_objid=$P{collectorid}

[findBillingByBilldate]
SELECT * FROM loan_ledger_billing 
WHERE billdate=$P{billdate}
	AND collector_objid=$P{collectorid}

[getBillingDetailByRoutecode]
SELECT llbd.*, lbn.nexttoid AS nextto
FROM loan_ledger_billing_detail llbd
LEFT JOIN loanapp_borrower_nextto lbn ON llbd.acctid=lbn.borrowerid
WHERE parentid=$P{billingid} 
	AND route_code=$P{route_code}

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
SELECT DISTINCT statey
FROM loan_ledger_billing

[changeStateCompleted]
UPDATE loan_ledger_billing SET state="COMPLETED"
WHERE objid=$P{objid}

[changeStateUploaded]
UPDATE loan_ledger_billing SET state="UPLOADED"
WHERE objid=$P{objid}

[changeStateVoided]
UPDATE loan_ledger_billing SET state="VOIDED"
WHERE objid=$P{objid}

[changeStateDraft]
UPDATE loan_ledger_billing SET state="DRAFT"
WHERE objid=$P{objid}

[changeBatchCollectionSheetStateVoided]
UPDATE batch_collectionsheet SET state="VOIDED"
WHERE objid=$P{objid}

[changeBatchCollectionSheetStateDraft]
UPDATE batch_collectionsheet SET state="DRAFT"
WHERE objid=$P{objid}

[getPastDraftBillings]
SELECT * FROM loan_ledger_billing
WHERE state='DRAFT'
	AND billdate < $P{date}

[getBillingForSubCollection]
SELECT llb.* 
FROM loan_ledger_billing llb
LEFT JOIN loan_ledger_specialcollection llsc ON llb.objid=llsc.billingid
WHERE llb.collector_username LIKE $P{searchtext}
	AND llb.state='DRAFT'
	AND llsc.objid IS NULL
ORDER BY llb.billdate