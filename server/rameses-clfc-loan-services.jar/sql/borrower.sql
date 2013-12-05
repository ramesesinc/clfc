[findByObjid]
SELECT * FROM borrower WHERE objid=$P{objid} 

[getChildren]
SELECT * FROM borrower_children WHERE parentid=$P{parentid} 

[removeChildren]
DELETE FROM borrower_children WHERE parentid=$P{parentid} 

[getEmployments]
SELECT * FROM employment WHERE refid=$P{refid} 

[removeEmployments]
DELETE FROM employment WHERE refid=$P{refid} 

[getOtherIncomes]
SELECT * FROM sourceofincome WHERE refid=$P{refid} 

[removeOtherIncomes]
DELETE FROM sourceofincome WHERE refid=$P{refid} 

[getEducations]
SELECT * FROM borrower_education WHERE parentid=$P{parentid} 

[removeEducations]
DELETE FROM borrower_education WHERE parentid=$P{parentid} 

[findParent]
SELECT * FROM borrower_parent WHERE objid=$P{objid} 

[removeParent]
DELETE FROM borrower_parent WHERE objid=$P{objid} 

[getSiblings]
SELECT * FROM borrower_sibling WHERE parentid=$P{parentid} 

[removeSiblings]
DELETE FROM borrower_sibling WHERE parentid=$P{parentid} 

[findBankAcct]
SELECT * FROM borrower_bankacct 
WHERE parentid=$P{parentid} AND type=$P{type} 

[getBankAccts]
SELECT * FROM borrower_bankacct 
WHERE parentid=$P{parentid} AND type=$P{type} 

[removeBankAccts]
DELETE FROM borrower_bankacct 
WHERE parentid=$P{parentid} AND type=$P{type} 

[removeAllBankAccts]
DELETE FROM borrower_bankacct WHERE parentid=$P{parentid} 