[getList]
SELECT vp.objid, vp.state, la.appno, vp.collectorid AS collector_objid,
	su.name AS collector_name, vp.reason, la.borrower_objid, 
	la.borrower_name
FROM void_payment vp
INNER JOIN loanapp la ON vp.loanappid=la.objid
INNER JOIN sys_user su ON vp.collectorid=su.objid
WHERE vp.state LIKE $P{state}

[findVoidPaymentByPaymentid]
SELECT * FROM void_payment WHERE paymentid=$P{paymentid}

[changeStateVoided]
UPDATE void_payment SET state="VOIDED" WHERE objid=$P{objid}