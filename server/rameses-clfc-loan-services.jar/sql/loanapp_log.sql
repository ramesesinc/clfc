[getList]
SELECT * FROM loanapp_log WHERE loanappid = $P{loanappid}
ORDER BY dtposted DESC