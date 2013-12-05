[findByRoutecode]
SELECT * FROM batch_payment
WHERE route_code=$P{route_code} AND state='DRAFT'

[getDetailsByParentId]
SELECT bpd.objid, l.objid AS appid, l.borrower_name AS borrowername, 
	bpd.refno, bpd.paytype, bpd.payamount, l.appno 
FROM loanapp l
INNER JOIN batch_payment_detail bpd ON l.objid=bpd.appid
WHERE bpd.parentid=$P{parentid}

[removeByAppId]
DELETE FROM loan_payment_temp WHERE appid=$P{appid}

[findDetailByAppIdAndParentId]
SELECT * FROM batch_payment_detail
WHERE appid=$P{appid} AND parentid=$P{parentid}

[findUnpostedPayment]
SELECT * FROM batch_payment bp
INNER JOIN batch_payment_detail bpt ON bp.objid=bpt.parentid
WHERE bp.state='DRAFT' AND bp.route_code=$P{route_code}
LIMIT 1

[changeStateApproved]
UPDATE batch_payment SET state='APPROVED'
WHERE objid=$P{objid}

[getCashBreakdown]
SELECT * FROM batch_payment_cashbreakdown
WHERE parentid=$P{parentid}
ORDER BY denomination DESC