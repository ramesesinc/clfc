USE clfc2;

CREATE TABLE IF NOT EXISTS `mobile_tracker` 
(
  `objid` VARCHAR(50) NOT NULL,
  `state` VARCHAR(25) DEFAULT NULL COMMENT 'OPEN,CLOSED',
  `type` VARCHAR(25) DEFAULT NULL COMMENT 'NORMAL,SPECIAL',
  `sessionid` VARCHAR(50) DEFAULT NULL,
  `branchid` VARCHAR(50) DEFAULT NULL,
  `dtstart` DATETIME DEFAULT NULL,
  `startlng` DECIMAL(18,14) DEFAULT '0.00000000000000',
  `startlat` DECIMAL(18,14) DEFAULT '0.00000000000000',
  `dtclosed` DATETIME DEFAULT NULL,
  `closedby` VARCHAR(50) DEFAULT NULL,
  `closedremarks` VARCHAR(150) DEFAULT NULL,
  `closedlng` DECIMAL(18,14) DEFAULT '0.00000000000000',
  `closedlat` DECIMAL(18,14) DEFAULT '0.00000000000000',
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_sessionid` (`sessionid`),
  KEY `ix_branchid` (`branchid`),
  KEY `ix_dtclosedby` (`dtclosed`,`closedby`),
  KEY `ix_dtstart` (`dtstart`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `mobile_tracker_detail` 
(
  `objid` varchar(50) NOT NULL,
  `parentid` varchar(50) default NULL,
  `txndate` datetime default NULL,
  `refid` varchar(50) default NULL,
  `reftype` varchar(50) default NULL,
  `lng` decimal(18,14) default '0.00000000000000',
  `lat` decimal(18,14) default '0.00000000000000',
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_parentid_refid` (`parentid`,`refid`),
  KEY `ix_txndate` (`txndate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

