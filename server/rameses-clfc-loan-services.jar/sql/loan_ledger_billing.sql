[getList]
SELECT llb.*, lr.description AS route_description, lr.area AS route_area
FROM loan_ledger_billing llb
INNER JOIN loan_route lr ON llb.route_code=lr.code
ORDER BY llb.route_code