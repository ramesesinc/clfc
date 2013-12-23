[findByRoutecode]
SELECT * FROM batch_collectionsheet
WHERE route_code=$P{route_code} AND state='DRAFT'

[getDetailsByParentId]
SELECT bcd.objid, l.objid AS appid, l.borrower_name AS borrowername, 
	bcdp.refno, bcdp.paytype, bcdp.payamount, l.appno 
FROM loanapp l
INNER JOIN batch_collectionsheet_detail bcd ON l.objid=bcd.appid
INNER JOIN batch_collectionsheet_detail_payment bcdp ON bcd.objid=bcdp.parentid
WHERE bcd.parentid=$P{parentid}

[findDetailByAppIdAndParentId]
SELECT * FROM batch_collectionsheet_detail
WHERE appid=$P{appid} AND parentid=$P{parentid}

[findTotalDetailByParentId]
SELECT COUNT(*) AS total FROM batch_collectionsheet_detail
WHERE parentid=$P{parentid}

[findUnpostedPayment]
SELECT * FROM batch_collectionsheet bc
INNER JOIN batch_collectionsheet_detail bcd ON bc.objid=bcd.parentid
INNER JOIN batch_collectionsheet_detail_payment bcdp ON bcd.objid=bcdp.parentid
WHERE bc.state='DRAFT' AND bc.route_code=$P{route_code}
LIMIT 1

[changeStateApproved]
UPDATE batch_collectionsheet SET state='APPROVED'
WHERE objid=$P{objid}

[getCashBreakdown]
SELECT * FROM batch_collectionsheet_cashbreakdown
WHERE parentid=$P{parentid}
ORDER BY denomination DESC

[getPaymentsByDetailid]
SELECT * FROM batch_collectionsheet_detail_payment
WHERE parentid=$P{parentid}

[getNotesByDetailid]
SELECT * FROM batch_collectionsheet_detail_note
WHERE parentid=$P{parentid}

[findDetailById]
SELECT * FROM batch_collectionsheet_detail
where objid=$P{objid}