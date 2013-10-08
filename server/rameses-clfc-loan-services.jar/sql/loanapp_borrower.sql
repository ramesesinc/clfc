[findPrincipalBorrower]
SELECT lb.type, lb.relation, b.* 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.type='PRINCIPAL'  

[removePrincipalBorrower]
DELETE FROM loanapp_borrower WHERE parentid=$P{parentid} AND type='PRINCIPAL' 

[getJointBorrowers]
SELECT lb.type, lb.relation, b.* 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.type='JOINT' 

[removeJointBorrowers]
DELETE FROM loanapp_borrower WHERE parentid=$P{parentid} AND type='JOINT' 

[getComakers]
SELECT lb.type, lb.relation, b.* 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.type='COMAKER' 

[removeComakers]
DELETE FROM loanapp_borrower WHERE parentid=$P{parentid} AND type='COMAKER' 

[getBorrowerNames]
SELECT 
	b.objid, b.lastname, b.firstname, b.middlename, lb.type, 
	CASE WHEN lb.type='PRINCIPAL' THEN 0 ELSE 1 END AS orderindex 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.type IN ('PRINCIPAL','JOINT') 
ORDER BY orderindex 

[removeItems]
DELETE FROM loanapp_borrower WHERE parentid=$P{parentid} 
