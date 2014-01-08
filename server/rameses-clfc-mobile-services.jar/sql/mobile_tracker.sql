[getList]
SELECT * FROM mobile_tracker WHERE sessionid=$P{sessionid} ORDER BY txndate 

[getLogs]
SELECT * FROM mobile_tracker_detail WHERE parentid=$P{parentid} ORDER BY txndate 

[findByPrimary]
SELECT * FROM mobile_tracker WHERE objid=$P{objid} 
