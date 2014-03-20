[getList]
SELECT lp.*, llb.collector_objid, llb.collector_name
FROM loan_payment lp
INNER JOIN loan_ledger_billing llb ON lp.objid=llb.objid
ORDER BY lp.txndate

[getDetailsByParentid]
SELECT * FROM loan_payment_detail
WHERE parentid=$P{parentid}
	AND control_objid IS NULL