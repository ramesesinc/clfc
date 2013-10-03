[getList]
SELECT * FROM loan_route

[getLookupRoutes]
SELECT * FROM loan_route r
WHERE ${filter}

[findByCode]
SELECT * FROM loan_route WHERE code=$P{code} 

[getCollectors]
SELECT * FROM loan_route_collector WHERE parentid=$P{parentid} ORDER BY user_name 

[removeCollector]
DELETE FROM loan_route_collector WHERE objid=$P{objid}  