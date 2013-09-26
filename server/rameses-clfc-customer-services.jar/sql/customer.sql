[getLookupCustomers]
SELECT * FROM customer c 
WHERE ${filter} ORDER BY name 

[findCustomer]
SELECT * FROM customer WHERE objid=$P{objid} 
