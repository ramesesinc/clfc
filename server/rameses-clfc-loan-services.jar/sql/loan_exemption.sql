[getList]
SELECT * FROM loan_exemption
WHERE batchpaymentid=$P{batchpaymentid}

[getForExemptions]
SELECT * FROM loan_ledger_billing_detail
WHERE parentid=$P{parentid}
	AND txndate=$P{txndate}
	AND objid NOT IN(SELECT bpd.objid
						FROM batch_payment bp
						INNER JOIN batch_payment_detail bpd
						ON bp.objid=bpd.parentid
						WHERE bp.objid=$P{parentid}
							AND bp.txndate=$P{txndate})

[getExemptionsByStartdateAndEnddateAndLedgerid]
SELECT * FROM loan_exemption
WHERE txndate BETWEEN $P{startdate} AND $P{enddate}
	AND ledgerid=$P{ledgerid}
	AND state='APPROVED'

[findExemptionByDateAndLedgerid]
SELECT * FROM loan_exemption
WHERE txndate=$P{txndate}
	AND ledgerid=$P{ledgerid}
	AND state='APPROVED'