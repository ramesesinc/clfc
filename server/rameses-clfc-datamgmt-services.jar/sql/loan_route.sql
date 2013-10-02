[getList]
SELECT * FROM loan_route

[getLookupRoutes]
SELECT * FROM loan_route r
WHERE ${filter}

[findByCode]
SELECT * FROM loan_route WHERE code=$P{code} 