[getLookupCustomers]
SELECT * FROM customer c 
WHERE ${filter} ORDER BY name 
