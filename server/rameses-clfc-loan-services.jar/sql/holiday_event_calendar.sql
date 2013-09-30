[getList]
SELECT t.* 
FROM holiday_event_calendar t 
WHERE t.name LIKE $P{searchtext} 
ORDER BY t.date 

