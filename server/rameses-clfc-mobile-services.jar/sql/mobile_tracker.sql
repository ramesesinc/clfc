[getList]
SELECT t.*, 
	u.username AS user_name, u.lastname AS user_lastname, 
	u.firstname AS user_firstname, u.middlename AS user_middlename 
FROM mobile_tracker t 
	LEFT JOIN sys_user u ON t.userid=u.objid 
ORDER BY dtstart 

[getLogs]
SELECT * FROM mobile_tracker_detail 
WHERE parentid=$P{parentid} 
	AND state=1
ORDER BY txndate 

[findByPrimary]
SELECT t.*, t.userid AS user_objid, 
	u.username AS user_name, u.lastname AS user_lastname, 
	u.firstname AS user_firstname, u.middlename AS user_middlename  
FROM mobile_tracker t 
	LEFT JOIN sys_user u ON t.userid=u.objid 
WHERE t.objid=$P{objid} 

[findLog]
SELECT * FROM mobile_tracker_detail WHERE objid=$P{objid} 
