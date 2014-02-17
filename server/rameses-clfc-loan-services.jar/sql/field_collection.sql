[findFieldCollection]
SELECT * FROM field_collection
WHERE collector_objid=$P{collectorid}
	AND state='FOR_POSTING'

[getForPostingRoutesByFieldcollectionid]
SELECT lr.code AS route_code, lr.description AS route_description, lr.area AS route_area,
	SUM(fcp.payamount) AS total
FROM field_collection_route fcr 
INNER JOIN loan_route lr ON fcr.routecode=lr.code
INNER JOIN field_collection_loan fcl ON fcr.routecode=fcl.routecode
INNER JOIN field_collection_payment fcp ON fcl.objid=fcp.parentid
WHERE fcr.fieldcollectionid=$P{fielcollectionid}
	AND fcl.parentid=$P{fielcollectionid}
GROUP BY lr.code

[getCashBreakdownByFieldcollectionid]
SELECT * FROM field_collection_cashbreakdown
WHERE parentid=$P{parentid}
ORDER BY denomination DESC

[getLoansByParentidAndRoutecode]
SELECT * FROM field_collection_loan
WHERE parentid=$P{parentid}
	AND routecode=$P{routecode}

[getPaymentsByParentid]
SELECT * FROM field_collection_payment
WHERE parentid=$P{parentid}

[getPaymentsByFieldcollectionid]
SELECT * FROM field_collection_payment
WHERE fieldcollectionid=$P{fieldcollectionid}

[getRoutesByFieldcollectionid]
SELECT * FROM field_collection_route
WHERE fieldcollectionid=$P{fieldcollectionid}

[getCurrentLoansByRoutecode]
SELECT fcl.* 
FROM field_collection fc
INNER JOIN field_collection_loan fcl ON fc.objid=fcl.parentid
WHERE fc.billdate=$P{billdate}
	AND fcl.routecode=$P{routecode}
ORDER BY fcl.borrower_name

[changeState]
UPDATE field_collection SET state=$P{state}
WHERE objid=$P{objid}

[findRouteByFieldcollectionidAndRoutecode]
SELECT * FROM field_collection_route
WHERE fieldcollectionid=$P{fieldcollectionid}
	AND routecode=$P{routecode}

[findPendingVoidRequestByFieldcollectionid]
SELECT fcp.objid FROM field_collection_payment fcp
INNER JOIN void_payment vp ON fcp.objid=vp.paymentid
WHERE fcp.fieldcollectionid=$P{fieldcollectionid}
	AND vp.state='PENDING'

[findFieldCollectionByRouteAndId]
SELECT fc.* FROM field_collection fc
INNER JOIN field_collection_route fcr ON fc.objid=fcr.fieldcollectionid
WHERE fcr.routecode=$P{routecode}
	AND fcr.fieldcollectionid=$P{fieldcollectionid}

[findFieldCollectionLoanByParentidAndRoutecode]
SELECT * FROM field_collection_loan
WHERE parentid=$P{fieldcollectionid}
	AND routecode=$P{routecode}

[findFieldCollectionLoanByLoanappid]
SELECT * FROM field_collection_loan
WHERE loanapp_objid=$P{loanappid}
	AND parentid=$P{parentid}
	AND routecode=$P{routecode}

[findFieldCollectionRouteByFieldcollectionidAndRoutecode]
SELECT * FROM field_collection_route
WHERE fieldcollectionid=$P{fieldcollectionid}
	AND routecode=$P{routecode}

[updateTotalcountByFieldcollectionidAndRoutecode]
UPDATE field_collection_route SET totalcount=$P{totalcount}
WHERE fieldcollectionid=$P{fieldcollectionid}
	AND routecode=$P{routecode}

[findUnremittedRouteByFieldcollectionid]
SELECT fieldcollectionid FROM field_collection_route
WHERE fieldcollectionid=$P{fieldcollectionid}
	AND totalcount=0

[findUnpostedCollection]
SELECT fc.objid FROM field_collection fc
INNER JOIN field_collection_route fcr ON fc.objid=fcr.fieldcollectionid
WHERE fc.state IN ('DRAFT', 'FOR_POSTING')
	AND fc.objid=$P{fieldcollectionid}
	AND fcr.routecode=$P{routecode}

[removeRouteByFieldcollectionid]
DELETE FROM field_collection_route
WHERE fieldcollectionid=$P{fieldcollectionid}

[removeVoidRequestsByFieldcolletionid]
DELETE FROM void_payment
WHERE collectionid=$P{fieldcollectionid}

[removePaymentsByFieldcollectionid]
DELETE FROM field_collection_payment
WHERE fieldcollectionid=$P{fieldcollectionid}

[removeLoanByParentid]
DELETE FROM field_collection_loan
WHERE parentid=$P{parentid}