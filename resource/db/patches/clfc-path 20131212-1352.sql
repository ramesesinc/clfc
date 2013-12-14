USE clfc2;

INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ( 'LOAN_CAO_APP_CC','LOAN_CAO_OFFICER','capture_application','create'); 

INSERT IGNORE INTO `sys_usergroup_permission` (`objid`,`usergroupid`,`object`,`permission`) 
VALUES ( 'LOAN_CAO_APP_CR','LOAN_CAO_OFFICER','capture_application','read');

CREATE TABLE IF NOT EXISTS `calendar_event` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `name` varchar(150) default NULL,
  `date` date default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_date` (`date`), 
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

