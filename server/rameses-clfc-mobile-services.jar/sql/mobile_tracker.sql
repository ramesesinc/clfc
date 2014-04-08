[getList]
SELECT t.*, 
	u.username AS user_name, u.lastname AS user_lastname, 
	u.firstname AS user_firstname, u.middlename AS user_middlename 
FROM mobile_tracker t 
	LEFT JOIN sys_user u ON t.userid=u.objid 
ORDER BY dtstart ${_ordermode}

[getLogs]
SELECT d.*, m.state AS trackerstate
FROM mobile_tracker_detail d 
	INNER JOIN mobile_tracker m ON d.parentid=m.objid 
WHERE d.parentid=$P{parentid} AND d.state=1 
ORDER BY d.txndate 

[findByPrimary]
SELECT t.*, t.userid AS user_objid, 
	u.username AS user_name, u.lastname AS user_lastname, 
	u.firstname AS user_firstname, u.middlename AS user_middlename  
FROM mobile_tracker t 
	LEFT JOIN sys_user u ON t.userid=u.objid 
WHERE t.objid=$P{objid} 

[findLog]
SELECT * FROM mobile_tracker_detail WHERE objid=$P{objid} 

[findIsStartedByPrimary]
SELECT objid FROM mobile_tracker
WHERE objid=$P{objid}
	AND dtstart IS NOT NULL

[findDetailByParentidAndRefid]
SELECT objid FROM mobile_tracker_detail
WHERE parentid=$P{parentid}
	AND refid=$P{refid}

[findLastTrackerItemByParentid]
SELECT * FROM mobile_tracker_detail
WHERE parentid =  $P{parentid}
ORDER BY txndate DESC
LIMIT 1