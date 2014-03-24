SET FOREIGN_KEY_CHECKS=0;

USE clfc2;

ALTER TABLE sys_org DROP FOREIGN KEY `FK_sys_org`; 
ALTER TABLE sys_org ADD COLUMN `code` VARCHAR(50) NULL AFTER `objid`;
ALTER TABLE sys_org CHANGE `parentid` `parent_objid` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL;
ALTER TABLE sys_org CHANGE `parentclass` `parent_orgclass` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL;
ALTER TABLE sys_org ADD COLUMN `root` SMALLINT DEFAULT 0 NULL AFTER `parent_orgclass`; 
ALTER TABLE sys_org ADD CONSTRAINT `FK_sys_org_parent_orgclass` FOREIGN KEY (`parent_orgclass`) REFERENCES `sys_orgclass`(`name`); 
ALTER TABLE sys_org ADD UNIQUE INDEX `uix_code` (`code`); 

UPDATE sys_org SET `code`=objid WHERE `code` IS NULL;
UPDATE sys_org SET `root`=1 WHERE objid='CLFC';

ALTER TABLE sys_orgclass CHANGE `childnodes` `parentclass` VARCHAR(255) CHARSET latin1 COLLATE latin1_swedish_ci NULL;
ALTER TABLE sys_orgclass ADD COLUMN `handler` VARCHAR(50) NULL AFTER `parentclass`; 

ALTER TABLE sys_securitygroup DROP FOREIGN KEY `FK_sys_securitygroup_usergroup`; 
ALTER TABLE sys_securitygroup CHANGE `usergroupid` `usergroup_objid` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL; 
ALTER TABLE sys_securitygroup ADD CONSTRAINT `FK_sys_securitygroup_usergroup` FOREIGN KEY (`usergroup_objid`) REFERENCES `sys_usergroup` (`objid`); 

ALTER TABLE sys_user ADD COLUMN `txncode` VARCHAR(10) NULL AFTER `lockid`; 

ALTER TABLE sys_usergroup_permission DROP FOREIGN KEY `FK_sys_usergroup_permission_usergroup`; 
ALTER TABLE sys_usergroup_permission CHANGE `usergroupid` `usergroup_objid` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL; 
ALTER TABLE sys_usergroup_permission ADD COLUMN `title` VARCHAR(50) NULL AFTER `permission`; 
ALTER TABLE sys_usergroup_permission ADD CONSTRAINT `FK_sys_usergroup_permission_usergroup` FOREIGN KEY (`usergroup_objid`) REFERENCES `sys_usergroup` (`objid`);

UPDATE sys_usergroup_permission SET title=permission WHERE title IS NULL;

ALTER TABLE sys_usergroup_member DROP FOREIGN KEY `FK_sys_usergroup_member_securitygorup`;
ALTER TABLE sys_usergroup_member DROP FOREIGN KEY `FK_sys_usergroup_member_usergroup`;
ALTER TABLE sys_usergroup_member DROP COLUMN `jobtitle`;
ALTER TABLE sys_usergroup_member CHANGE `usergroupid` `usergroup_objid` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL; 
ALTER TABLE sys_usergroup_member CHANGE `securitygroupid` `securitygroup_objid` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL;
ALTER TABLE sys_usergroup_member ADD CONSTRAINT `FK_sys_usergroup_member_usergroup` FOREIGN KEY (`usergroup_objid`) REFERENCES `sys_usergroup` (`objid`);
ALTER TABLE sys_usergroup_member ADD CONSTRAINT `FK_sys_usergroup_member_securitygorup` FOREIGN KEY (`securitygroup_objid`) REFERENCES `sys_securitygroup` (`objid`); 

SET FOREIGN_KEY_CHECKS=1;
