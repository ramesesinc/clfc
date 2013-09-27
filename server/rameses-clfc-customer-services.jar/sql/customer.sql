[getLookupCustomers]
SELECT * FROM customer c 
WHERE ${filter} ORDER BY name 

[findCustomer]
SELECT * FROM customer WHERE objid=$P{objid} 

[getConnections]
SELECT 
	r.objid, r.name, r.lastname, r.firstname, r.middlename, c.relationship
FROM customer_connection c 
	INNER JOIN customer r ON c.relaterid=r.objid 
WHERE c.principalid=$P{principalid} 
ORDER BY r.name 
