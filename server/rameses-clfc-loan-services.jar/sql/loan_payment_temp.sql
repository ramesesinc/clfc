[getList]
SELECT * FROM loan_payment_temp

[getListByRouteId]
SELECT l.appno AS appno, l.objid AS appid, l.borrower_name AS borrowername,
		lpt.refno AS refno, lpt.txndate AS txndate, 
		lpt.paytype AS paytype, lpt.payamount AS payamount
FROM loan_payment_temp lpt
INNER JOIN loanapp l ON lpt.appid = l.objid
WHERE l.route_code=$P{route_code}