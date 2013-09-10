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
