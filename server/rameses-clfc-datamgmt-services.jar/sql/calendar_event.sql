[getList]
SELECT t.* 
FROM calendar_event t 
WHERE t.name LIKE $P{searchtext} 
ORDER BY t.date 

[getListBetweenStartdateAndEnddate]
SELECT t.* FROM calendar_event t
WHERE t.date BETWEEN $P{startdate} AND $P{enddate}