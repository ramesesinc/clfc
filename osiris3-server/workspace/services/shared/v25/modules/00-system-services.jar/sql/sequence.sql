[findSeries]
SELECT * FROM sys_sequence WHERE objid=$P{objid} 

[getCurrentSeries]
SELECT nextseries AS currentseries FROM sys_sequence WHERE objid = $P{objid} 

[incrementNextSeries]
UPDATE sys_sequence SET nextseries = nextseries + 1 WHERE objid = $P{objid} 

[updateNextSeries]
UPDATE sys_sequence SET nextseries = $P{nextseries} WHERE objid = $P{objid} 
