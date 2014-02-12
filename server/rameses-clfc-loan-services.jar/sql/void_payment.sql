[getList]
SELECT vp.objid, vp.state, vp.loanapp_appno, vp.collector_objid,
	vp.collector_name, vp.reason, la.borrower_objid, 
	la.borrower_name, lr.area AS route_area, vp.txncode
FROM void_payment vp
INNER JOIN loanapp la ON vp.loanapp_objid=la.objid
INNER JOIN loan_route lr on vp.routecode=lr.code
WHERE vp.state LIKE $P{state}

[findVoidPaymentByPaymentid]
SELECT * FROM void_payment WHERE paymentid=$P{paymentid}

[changeStateVoided]
UPDATE void_payment SET state="VOIDED" WHERE objid=$P{objid}

[changeStateApproved]
UPDATE void_payment SET state="APPROVED" WHERE objid=$P{objid}

[findFieldVoidRequestById]
SELECT vp.objid, vp.state, vp.loanapp_appno, la.borrower_name, c.address AS borrower_address,
	lr.description AS route_description, lr.area AS route_area, 
	vp.collector_name, fcp.payamount, vp.reason, vp.approvedremarks
FROM void_payment vp
INNER JOIN field_collection_payment fcp ON vp.paymentid=fcp.objid
INNER JOIN loanapp la ON vp.loanapp_objid=la.objid
INNER JOIN customer c ON la.borrower_objid=c.objid
INNER JOIN loan_route lr ON vp.routecode=lr.code
WHERE vp.objid=$P{objid}

[findOnlineVoidRequestById]
SELECT vp.objid, vp.state, vp.loanapp_appno, la.borrower_name, c.address AS borrower_address,
	lr.description AS route_description, lr.area AS route_area, 
	vp.collector_name, ocd.amount AS payamount, vp.reason, vp.approvedremarks
FROM void_payment vp
INNER JOIN online_collection_detail ocd ON vp.paymentid=ocd.objid
INNER JOIN loanapp la ON vp.loanapp_objid=la.objid
INNER JOIN customer c ON la.borrower_objid=c.objid
INNER JOIN loan_route lr ON vp.routecode=lr.code
WHERE vp.objid=$P{objid}