USE clfc2;

INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('CALEVT_DATAMGMT_AUTHOR01','LOAN_DATAMGMT_AUTHOR','calendar_event','create'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('CALEVT_DATAMGMT_AUTHOR02','LOAN_DATAMGMT_AUTHOR','calendar_event','read'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('CALEVT_DATAMGMT_AUTHOR03','LOAN_DATAMGMT_AUTHOR','calendar_event','edit'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('CALEVT_DATAMGMT_AUTHOR04','LOAN_DATAMGMT_AUTHOR','calendar_event','delete'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('CALEVT_DATAMGMT_AUTHOR05','LOAN_DATAMGMT_AUTHOR','calendar_event','approve'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('PRODTYPE_DATAMGMT_AUTHOR1','LOAN_DATAMGMT_AUTHOR','product_type','create'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('PRODTYPE_DATAMGMT_AUTHOR2','LOAN_DATAMGMT_AUTHOR','product_type','read'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('PRODTYPE_DATAMGMT_AUTHOR3','LOAN_DATAMGMT_AUTHOR','product_type','edit'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('PRODTYPE_DATAMGMT_AUTHOR4','LOAN_DATAMGMT_AUTHOR','product_type','delete'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('PRODTYPE_DATAMGMT_AUTHOR5','LOAN_DATAMGMT_AUTHOR','product_type','approve'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('ROUTE_DATAMGMT_AUTHOR1','LOAN_DATAMGMT_AUTHOR','route','create'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('ROUTE_DATAMGMT_AUTHOR2','LOAN_DATAMGMT_AUTHOR','route','read'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('ROUTE_DATAMGMT_AUTHOR3','LOAN_DATAMGMT_AUTHOR','route','edit'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('ROUTE_DATAMGMT_AUTHOR4','LOAN_DATAMGMT_AUTHOR','route','delete'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('ROUTE_DATAMGMT_AUTHOR5','LOAN_DATAMGMT_AUTHOR','route','approve'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('BRANCH_DATAMGMT_AUTHOR1','LOAN_DATAMGMT_AUTHOR','branch','create'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('BRANCH_DATAMGMT_AUTHOR2','LOAN_DATAMGMT_AUTHOR','branch','read'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('BRANCH_DATAMGMT_AUTHOR3','LOAN_DATAMGMT_AUTHOR','branch','edit'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('BRANCH_DATAMGMT_AUTHOR4','LOAN_DATAMGMT_AUTHOR','branch','delete'); 
INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ('BRANCH_DATAMGMT_AUTHOR5','LOAN_DATAMGMT_AUTHOR','branch','approve'); 
 

CREATE TABLE IF NOT EXISTS `branch` (
  `objid` VARCHAR(40) NOT NULL,
  `dtcreated` DATETIME DEFAULT NULL,
  `createdby` VARCHAR(50) DEFAULT NULL,
  `name` VARCHAR(50) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `manager` VARCHAR(150) DEFAULT NULL,
  `contactno` VARCHAR(50) DEFAULT NULL,
  `email` VARCHAR(50) DEFAULT NULL,
  `businesshours` VARCHAR(50) DEFAULT NULL,
  `lng` DECIMAL(16,8) DEFAULT '0.00000000',
  `lat` DECIMAL(16,8) DEFAULT '0.00000000',
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_name` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;
