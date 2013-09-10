[findByObjid]
SELECT objid FROM borrower WHERE objid=$P{objid} 

[getChildren]
SELECT * FROM borrower_children WHERE borrowerid=$P{borrowerid} 

[removeChildren]
DELETE FROM borrower_children WHERE borrowerid=$P{borrowerid} 

[removeEmployments]
DELETE FROM employment WHERE refid=$P{refid} 

[removeOtherIncomes]
DELETE FROM sourceofincome WHERE refid=$P{refid} 
