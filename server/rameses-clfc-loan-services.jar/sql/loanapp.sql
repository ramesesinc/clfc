[findByObjid]
SELECT l.*, 
	lr.description AS route_description, 
	lr.area AS route_area 
FROM loanapp l  
	LEFT JOIN loan_route lr ON l.route_code=lr.code 
WHERE l.objid=$P{objid} 

[changeState]
UPDATE loanapp SET state=$P{state} WHERE objid=$P{objid} 

[findCurrentLoanForBorrower]
SELECT 
	l.objid, l.state, st.level, l.dtcreated, l.appno, 
	lb.borrowerid, lb.borrowername, lb.type AS borrowertype 
FROM loanapp_borrower lb 
	INNER JOIN loanapp l ON lb.parentid=l.objid 
	INNER JOIN loan_state_type st ON l.state=st.name AND st.level < 10 
WHERE 
	lb.borrowerid=$P{borrowerid} AND 
	lb.type IN ('PRINCIPAL','JOINT') 
ORDER BY l.dtcreated DESC 
LIMIT 1 

[getBorrowers] 
SELECT 
	lb.borrowerid, lb.type as borrowertype,  
	b.lastname, b.firstname, b.middlename 
FROM loanapp_borrower lb 
	INNER JOIN borrower b ON lb.borrowerid=b.objid 
WHERE lb.parentid=$P{parentid} 

[getQualifiedIndexNames]
SELECT lb.borrowername FROM loanapp_borrower lb 
WHERE lb.parentid=$P{parentid} AND lb.type IN ('principal','joint') 

[findBorrower]
SELECT * FROM loanapp_borrower WHERE parentid=$P{parentid} AND type=$P{type}  

[removeBorrowerIndices]
DELETE FROM loanapp_search_index 
WHERE appid=$P{appid} and borrowerid IS NOT NULL 

[removeBorrowerIndex]
DELETE FROM loanapp_search_index
WHERE appid=$P{appid} AND borrowerid=$P{borrowerid} 

[findSearchIndex]
SELECT * FROM loanapp_search WHERE objid=$P{objid} 

[removeSearchIndex]
DELETE FROM loanapp_search WHERE objid=$P{objid} 

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


