[getCollectors]
SELECT * FROM online_collection_collector

[findCollectionForPostingByTxndateAndCollector]
SELECT * 
FROM online_collection
WHERE state='FOR_POSTING'
	AND txndate=$P{txndate}
	AND collector_objid=$P{collectorid}

[findPaymentById]
SELECT ocd.*, oc.txndate
FROM online_collection_detail ocd
INNER JOIN online_collection oc ON ocd.parentid=oc.objid
WHERE ocd.objid=$P{objid}

[getCollectionDateByCollector]
SELECT txndate 
FROM online_collection
WHERE collector_objid=$P{collectorid}
	AND state='FOR_POSTING'
ORDER BY txndate

[getPayments]
SELECT * FROM online_collection_detail
WHERE parentid=$P{parentid}
ORDER BY route_description, borrower_name 

[getCashBreakdown]
SELECT * FROM online_collection_cashbreakdown
WHERE parentid=$P{parentid}
ORDER BY denomination DESC

[changeState]
UPDATE online_collection SET state=$P{state}
WHERE objid=$P{objid}
