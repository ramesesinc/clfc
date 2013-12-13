USE clfc2;

INSERT INTO sys_usergroup (`objid`,`title`,`domain`,`role`,`userclass`,`orgclass`) 
VALUES ( 'LOAN_CASHIER','CASHIER','LOAN','CASHIER','usergroup',NULL); 

ALTER TABLE sys_securitygroup 
	DROP INDEX `idx_securitygroup_name`, 
	ADD UNIQUE INDEX `uix_securitygroup_name` (`name`, `usergroupid`); 

UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_ACCT_OFFICER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_APPROVER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_CAO_OFFICER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_CASHIER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_CI_OFFICER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_CRECOM_OFFICER_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_DATAMGMT_AUTHOR_DEFAULT'; 
UPDATE sys_securitygroup SET `name`='(Default)' WHERE `objid`='LOAN_FIELD_COLLECTOR_DEFAULT'; 
