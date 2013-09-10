[findByObjid]
SELECT objid FROM borrower WHERE objid=$P{objid} 

[getChildren]
SELECT * FROM borrower_children WHERE borrowerid=$P{borrowerid} 

[removeChildren]
DELETE FROM borrower_children WHERE borrowerid=$P{borrowerid} 

[getEmployments]
SELECT * FROM employment WHERE refid=$P{refid} 

[removeEmployments]
DELETE FROM employment WHERE refid=$P{refid} 

[getOtherIncomes]
SELECT * FROM sourceofincome WHERE refid=$P{refid} 

[removeOtherIncomes]
DELETE FROM sourceofincome WHERE refid=$P{refid} 

[getEducations]
SELECT * FROM borrower_education WHERE borrowerid=$P{borrowerid} 

[removeEducations]
DELETE FROM borrower_education WHERE borrowerid=$P{borrowerid} 

[findParent]
SELECT * FROM borrower_parent WHERE objid=$P{objid} 

[removeParent]
DELETE FROM borrower_parent WHERE objid=$P{objid} 

[getSiblings]
SELECT * FROM borrower_sibling WHERE borrowerid=$P{borrowerid} 

[removeSiblings]
DELETE FROM borrower_sibling WHERE borrowerid=$P{borrowerid} 

[findBankAcct]
SELECT * FROM borrower_bankacct 
WHERE borrowerid=$P{borrowerid} AND type=$P{type} 

[getBankAccts]
SELECT * FROM borrower_bankacct 
WHERE borrowerid=$P{borrowerid} AND type=$P{type} 

[removeBankAccts]
DELETE FROM borrower_bankacct 
WHERE borrowerid=$P{borrowerid} AND type=$P{type} 

[removeAllBankAccts]
DELETE FROM borrower_bankacct WHERE borrowerid=$P{borrowerid} 
