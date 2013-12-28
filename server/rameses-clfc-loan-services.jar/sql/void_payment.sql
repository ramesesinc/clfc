[getList]
SELECT vp.objid, vp.state, la.appno, vp.collectorid AS collector_objid,
	su.name AS collector_name, vp.reason, la.borrower_objid, 
	la.borrower_name, lr.area AS route_area
FROM void_payment vp
INNER JOIN loanapp la ON vp.loanappid=la.objid
INNER JOIN sys_user su ON vp.collectorid=su.objid
INNER JOIN loan_route lr on vp.routecode=lr.code
WHERE vp.state LIKE $P{state}

[findVoidPaymentByPaymentid]
SELECT * FROM void_payment WHERE paymentid=$P{paymentid}

[changeStateVoided]
UPDATE void_payment SET state="VOIDED" WHERE objid=$P{objid}

[changeStateApproved]
UPDATE void_payment SET state="APPROVED" WHERE objid=$P{objid}

[findVoidRequestById]
SELECT vp.objid, vp.state, la.appno, la.borrower_name, c.address AS borrower_address,
	lr.description AS route_description, lr.area AS route_area, 
	su.name AS requestedby, bcdp.payamount, vp.reason
FROM void_payment vp
INNER JOIN loanapp la ON vp.loanappid=la.objid
INNER JOIN batch_collectionsheet_detail_payment bcdp ON vp.paymentid=bcdp.objid
INNER JOIN customer c ON la.borrower_objid=c.objid
INNER JOIN loan_route lr ON vp.routecode=lr.code
INNER JOIN sys_user su ON vp.collectorid=su.objid
WHERE vp.objid=$P{objid}