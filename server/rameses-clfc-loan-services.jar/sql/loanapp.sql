[findByObjid]
SELECT * FROM loanapp WHERE objid=$P{objid} 

[changeState]
UPDATE loanapp SET state=$P{state} WHERE objid=$P{objid} 

[getBorrowers] 
SELECT 
	lb.borrowerid, lb.type as borrowertype,  
	b.lastname, b.firstname, b.middlename 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} 

[getQualifiedIndexNames]
SELECT borrowername FROM loanapp_borrower_index 
WHERE loanappid=$P{loanappid} AND borrowertype IN ('principal','joint') 

[findBorrower]
SELECT * FROM loanapp_borrower WHERE parentid=$P{parentid} AND type=$P{type}  

[removeBorrowerIndices]
DELETE FROM loanapp_borrower_index WHERE loanappid=$P{loanappid} 

[removeBorrowerIndex]
DELETE FROM loanapp_borrower_index 
WHERE loanappid=$P{loanappid} AND borrowerid=$P{borrowerid} 

[findSearchIndex]
SELECT * FROM loanapp_search_index WHERE objid=$P{objid} 

[removeSearchIndex]
DELETE FROM loanapp_search_index WHERE objid=$P{objid} 

[findPrincipalBorrower]
SELECT b.* 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.principalid=b.objid 
WHERE lb.parentid=$P{parentid} AND lb.principalid=$P{principalid} 

[updatePrincipalBorrower] 
UPDATE loanapp_borrower SET 
	principalid=$P{principalid}, relaterid=null, relation=null 
WHERE parentid=$P{parentid} AND type='PRINCIPAL' 

[updatePrincipalBorrowerIndex]
UPDATE loanapp_borrower_index SET 
	borrowerid=$P{borrowerid}, borrowername=$P{borrowername} 
WHERE loanappid=$P{loanappid} AND borrowertype='PRINCIPAL' 

[getBorrowerNames]
SELECT b.objid, b.lastname, b.firstname, b.middlename 
FROM loanapp_borrower lb  
	INNER JOIN borrower b ON lb.principalid=b.objid  
WHERE lb.parentid=$P{parentid} AND lb.type=$P{type} 

[updateFullBorrowerName]
UPDATE loanapp_search_index SET 
	fullborrowername=$P{fullborrowername} 
WHERE objid=$P{objid} 

[getBusinesses]
SELECT * FROM loanapp_business WHERE parentid=$P{parentid} 

[findRecommendation] 
SELECT * FROM loanapp_recommendation WHERE objid=$P{objid}  

[removeRecommendation]
DELETE FROM loanapp_recommendation WHERE objid=$P{objid}  
