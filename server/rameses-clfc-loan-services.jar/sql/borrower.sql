[findByObjid]
SELECT objid FROM borrower WHERE objid=$P{objid} 

[getChildren]
SELECT * FROM borrower_children WHERE borrowerid=$P{borrowerid} 

[removeChildren]
DELETE FROM borrower_children WHERE borrowerid=$P{borrowerid} 