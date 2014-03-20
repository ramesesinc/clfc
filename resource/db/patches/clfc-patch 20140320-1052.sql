USE clfc2;

INSERT INTO sys_usergroup(objid, title, domain, role, userclass, orgclass)
VALUE('LOAN_ACCT_ASSISTANT', 'ACCOUNTING ASSISTANT', 'LOAN', 'ACCT_ASSISTANT', 'usergroup', NULL);
INSERT INTO sys_usergroup(objid, title, domain, role, userclass, orgclass)
VALUE('LOAN_LEGAL_COLLECTOR', 'LEGAL COLLECTOR', 'LOAN', 'LEGAL_COLLECTOR', 'usergroup', NULL);

ALTER TABLE loan_payment_detail
ADD COLUMN `control_objid` VARCHAR(40),
ADD COLUMN `control_series` VARCHAR(50),
ADD COLUMN `control_seriesno` INT(11);