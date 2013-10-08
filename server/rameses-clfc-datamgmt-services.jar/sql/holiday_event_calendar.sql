[getList]
SELECT t.* 
FROM holiday_event_calendar t 
WHERE t.name LIKE $P{searchtext} 
ORDER BY t.date 

[getListBetweenStartdateAndEnddate]
SELECT t.* FROM holiday_event_calendar t
WHERE t.date BETWEEN $P{startdate} AND $P{enddate}