[getList]
SELECT * FROM special_collection
WHERE type='FIELD'
ORDER BY dtrequested

[getFollowupCollectionList]
SELECT * FROM special_collection
WHERE type='ONLINE'
ORDER BY dtrequested

[getLedgersByBillingid]
SELECT ll.*, lr.code AS route_code, lr.area AS route_area,
	lr.description AS route_description
FROM loan_ledger_billing_detail llbd
INNER JOIN loan_ledger ll ON llbd.ledgerid=ll.objid
INNER JOIN loan_route lr ON llbd.route_code=lr.code
INNER JOIN special_collection_loan scl ON llbd.objid=scl.billingdetailid
WHERE llbd.parentid=$P{billingid}
	AND scl.parentid=$P{parentid}

[getForSpecialCollectionList]
SELECT ll.*, lr.code AS route_code, lr.area AS route_area,
	lr.description AS route_description, la.objid AS loanappid,
	la.appno, c.address AS homeaddress, la.dtcreated AS loandate,
	la.loanamount
FROM loan_ledger ll
INNER JOIN loanapp la ON ll.appid=la.objid
INNER JOIN customer c ON ll.acctid=c.objid
INNER JOIN loan_route lr ON la.route_code=lr.code
WHERE ll.state = 'OPEN'
	AND ll.acctname LIKE $P{searchtext}
	AND ll.objid NOT IN (SELECT llbd.ledgerid 
				FROM loan_ledger_billing llb
				INNER JOIN loan_ledger_billing_detail llbd ON llb.objid=llbd.parentid
				WHERE llb.collector_objid=$P{collectorid}
					AND llb.billdate=$P{billdate})

[getBillingDetailsBySpecialcollectionid]
SELECT llbd.*
FROM special_collection_loan scl
INNER JOIN loan_ledger_billing_detail llbd ON scl.billingdetailid=llbd.objid
WHERE scl.parentid=$P{specialcollectionid}

[getRoutesByBillingid]
SELECT * FROM special_collection_route
WHERE billingid=$P{billingid}

[getRoutesBySpecialcollectionid]
SELECT scr.*, lr.code AS route_code, lr.area AS route_area,
		lr.description AS route_description
FROM special_collection_route scr
INNER JOIN loan_route lr ON scr.routecode=lr.code
WHERE scr.specialcollectionid=$P{specialcollectionid}

[changeState]
UPDATE special_collection SET state=$P{state}
WHERE objid=$P{objid}

[removeRouteBySpecialcollectionid]
DELETE FROM special_collection_route
WHERE specialcollectionid=$P{specialcollectionid}

[removeLoansByParentid]
DELETE FROM special_collection_loan
WHERE parentid=$P{parentid}

[getLoansByParentid]
SELECT * FROM special_collection_loan
WHERE parentid=$P{parentid}

[getSpecialCollectionsForCancellation]
SELECT sc.*
FROM special_collection sc
INNER JOIN loan_ledger_billing llb ON sc.billingid=llb.objid
WHERE llb.billdate < $P{date}
	AND sc.state IN ('PENDING', 'FOR_DOWNLOAD')

[routeUploaded]
UPDATE special_collection_route SET uploaded=1
WHERE billingid=$P{billingid}
	AND routecode=$P{routecode}