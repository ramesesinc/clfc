[getList]
SELECT l.*, 
	b.address AS borrower_address, 
	lr.description AS route_description, lr.area AS route_area, 
	lpt.interestrate AS producttype_interestrate, 
	lpt.pastduerate AS producttype_overduerate, 
	lpt.underpaymentpenalty AS producttype_underpaymentpenalty,
	lpt.absentpenalty AS producttype_absentpenalty,
	lc.dtreleased AS dtreleased
FROM loanapp_capture_open lco 
	INNER JOIN loanapp_capture lc ON lco.objid=lc.objid 
	INNER JOIN loanapp l ON lc.objid=l.objid 
	INNER JOIN loan_product_type lpt ON l.producttype_name=lpt.name 	
	INNER JOIN borrower b ON l.borrower_objid=b.objid 
	LEFT JOIN loan_route lr ON l.route_code=lr.code 
WHERE 
	l.appno LIKE $P{searchtext} AND l.appmode='CAPTURE' AND 
	l.state='RELEASED' 
ORDER BY appno 

[removeOpenApplication]
DELETE FROM loanapp_capture_open WHERE objid=$P{objid} 
