[getList]
SELECT * FROM mobile_tracker WHERE sessionid=$P{sessionid} ORDER BY txndate 

[findBySession]
SELECT * FROM mobile_tracker WHERE sessionid=$P{sessionid} 

[findBranch]
SELECT * FROM branch WHERE objid=$P{objid} 

[getLogs]
SELECT * FROM mobile_tracker_detail WHERE parentid=$P{parentid} ORDER BY txndate 
