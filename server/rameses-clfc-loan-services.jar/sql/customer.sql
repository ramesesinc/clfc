[getLookupCustomers]
SELECT c.objid, c.custno, c.branchid, c.name, c.address 
FROM customer c WHERE ${filter} ORDER BY name 
