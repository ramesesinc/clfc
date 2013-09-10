[getList]
SELECT DISTINCT si.*  
FROM loanapp_borrower_index bi 
	INNER JOIN loanapp_search_index si ON bi.loanappid=si.objid 
WHERE ${filter} si.state=$P{state} 
ORDER BY ${orderby} 
