[getList]
SELECT * FROM loan_ledger_specialcollection
ORDER BY dtrequested

[getForSpecialCollectionList]
SELECT ll.*, sqry.code AS route_code, sqry.area AS route_area,
	sqry.description AS route_description, la.objid AS loanappid,
	la.appno, c.address AS homeaddress, la.dtcreated AS loandate,
	la.loanamount
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid=la.objid
INNER JOIN customer c ON ll.acctid=c.objid
INNER JOIN (SELECT lr.*
	FROM loan_route lr
	LEFT OUTER JOIN (SELECT llbr.routecode
		FROM loan_ledger_billing llb
		LEFT JOIN loan_ledger_subbilling llsb ON llb.objid=llsb.objid
		INNER JOIN loan_ledger_billing_route llbr ON llb.objid=llbr.billingid
		WHERE llb.collector_objid=$P{collectorid}
			AND llb.billdate=$P{billdate}) sqry ON lr.code=sqry.routecode
	WHERE sqry.routecode IS NULL) sqry ON la.route_code=sqry.code
WHERE ll.state = 'OPEN'

[getLedgersByBillingid]
SELECT ll.*, lr.code AS route_code, lr.area AS route_area,
	lr.description AS route_description
FROM loan_ledger_billing_detail llbd
INNER JOIN loan_ledger ll ON llbd.ledgerid=ll.objid
INNER JOIN loan_route lr ON llbd.route_code=lr.code
WHERE parentid=$P{billingid}