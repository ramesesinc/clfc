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
SELECT lb.type, b.* 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.type='COMAKER' 

[removeItems]
DELETE FROM loanapp_borrower WHERE parentid=$P{parentid} 
