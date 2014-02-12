USE clfc2;

CREATE TABLE IF NOT EXISTS `field_collection` (
  `objid` VARCHAR(40) NOT NULL,
  `state` VARCHAR(25) DEFAULT NULL COMMENT 'DRAFT, FOR_POSTING, POSTED',
  `dtfiled` DATETIME DEFAULT NULL,
  `filedby` VARCHAR(25) DEFAULT NULL,
  `branchid` VARCHAR(40) DEFAULT NULL,
  `trackerid` VARCHAR(40) DEFAULT NULL,
  `billdate` DATE DEFAULT NULL,
  `totalcount` SMALLINT(6) DEFAULT NULL,
  `totalamount` DECIMAL(10,2) DEFAULT NULL,
  `collector_objid` VARCHAR(40) DEFAULT NULL,
  `collector_name` VARCHAR(40) DEFAULT NULL,
  `dtposted` DATETIME DEFAULT NULL,
  `postedby` VARCHAR(25) DEFAULT NULL,
  `postedremarks` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_state` (`state`),
  KEY `ix_branchid` (`branchid`),
  KEY `ix_billdate` (`billdate`),
  KEY `ix_collectorid` (`collector_objid`),
  KEY `ix_dtfiled` (`dtfiled`),
  KEY `ix_dtposted` (`dtposted`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `field_collection_cashbreakdown` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `denomination` DECIMAL(7,2) DEFAULT NULL,
  `qty` SMALLINT(6) DEFAULT NULL,
  `amount` DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `FK_fielcollection_parentid_objid` (`parentid`),
  CONSTRAINT `FK_fielcollection_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `field_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `field_collection_loan` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `loanapp_objid` VARCHAR(40) DEFAULT NULL,
  `loanapp_appno` VARCHAR(25) DEFAULT NULL,
  `borrower_objid` VARCHAR(40) DEFAULT NULL,
  `borrower_name` VARCHAR(40) DEFAULT NULL,
  `routecode` VARCHAR(40) DEFAULT NULL,
  `noofpayments` SMALLINT(6) DEFAULT NULL,
  `remarks` TEXT,
  PRIMARY KEY  (`objid`),
  KEY `ix_loanappid` (`loanapp_objid`),
  KEY `ix_borrowerid` (`borrower_objid`),
  KEY `ix_routecode` (`routecode`),
  KEY `FK_fieldcollection_parentid_objid` (`parentid`),
  CONSTRAINT `FK_fieldcollection_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `field_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `field_collection_payment` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `fieldcollectionid` VARCHAR(40) DEFAULT NULL,
  `dtfiled` DATETIME DEFAULT NULL,
  `filedby` VARCHAR(25) DEFAULT NULL,
  `txnmode` VARCHAR(25) DEFAULT NULL COMMENT 'ONLINE, OFFLINE',
  `refno` VARCHAR(25) DEFAULT NULL,
  `paytype` VARCHAR(15) DEFAULT NULL,
  `payamount` DECIMAL(10,2) DEFAULT NULL,
  `paidby` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtfiled` (`dtfiled`),
  KEY `ix_paytype` (`paytype`),
  KEY `ix_refno` (`refno`),
  KEY `FK_fcloan_parentid_objid` (`parentid`),
  KEY `FK_fc_fieldcollectionid_objid` (`fieldcollectionid`),
  CONSTRAINT `FK_fcloan_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `field_collection_loan` (`objid`),
  CONSTRAINT `FK_fc_fieldcollectionid_objid` FOREIGN KEY (`fieldcollectionid`) REFERENCES `field_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `field_collection_route` (
  `fieldcollectionid` VARCHAR(40) NOT NULL,
  `routecode` VARCHAR(40) NOT NULL,
  `totalcount` SMALLINT(6) DEFAULT NULL,
  PRIMARY KEY  (`fieldcollectionid`,`routecode`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `online_collection` (
  `objid` VARCHAR(40) NOT NULL,
  `state` VARCHAR(15) DEFAULT NULL COMMENT 'FOR_POSTING, POSTED',
  `txndate` DATE DEFAULT NULL,
  `collector_objid` VARCHAR(40) DEFAULT NULL,
  `collector_name` VARCHAR(40) DEFAULT NULL,
  `dtcreated` DATETIME DEFAULT NULL,
  `createdby` VARCHAR(25) DEFAULT NULL,
  `dtposted` DATETIME DEFAULT NULL,
  `postedby` VARCHAR(25) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_state` (`state`),
  KEY `ix_dtcreated` (`dtcreated`),
  KEY `ix_dtposted` (`dtposted`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `online_collection_cashbreakdown` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `denomination` DECIMAL(7,2) DEFAULT '0.00',
  `qty` SMALLINT(6) DEFAULT NULL,
  `amount` DECIMAL(10,2) DEFAULT '0.00',
  PRIMARY KEY  (`objid`),
  KEY `FK_online_collection_cashbreakdown` (`parentid`),
  CONSTRAINT `FK_online_collection_cashbreakdown` FOREIGN KEY (`parentid`) REFERENCES `online_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `online_collection_collector` (
  `objid` VARCHAR(40) NOT NULL,
  `name` VARCHAR(40) DEFAULT NULL,
  PRIMARY KEY  (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `online_collection_detail` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `loanapp_objid` VARCHAR(40) DEFAULT NULL,
  `loanapp_appno` VARCHAR(25) DEFAULT NULL,
  `borrower_objid` VARCHAR(40) DEFAULT NULL,
  `borrower_name` VARCHAR(150) DEFAULT NULL,
  `dtpaid` DATETIME DEFAULT NULL,
  `paidby` VARCHAR(25) DEFAULT NULL,
  `refno` VARCHAR(25) DEFAULT NULL,
  `type` VARCHAR(15) DEFAULT NULL COMMENT 'schedule, over',
  `amount` DECIMAL(10,2) DEFAULT '0.00',
  `route_code` VARCHAR(40) DEFAULT NULL,
  `route_description` VARCHAR(150) DEFAULT NULL,
  `route_area` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `FK_online_collection_detail` (`parentid`),
  KEY `ix_loanappid` (`loanapp_objid`),
  KEY `ix_borrowerid` (`borrower_objid`),
  KEY `ix_dtpaid` (`dtpaid`),
  KEY `ix_type` (`type`),
  KEY `ix_routecode` (`route_code`),
  CONSTRAINT `FK_online_collection_detail` FOREIGN KEY (`parentid`) REFERENCES `online_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `special_collection` (
  `objid` VARCHAR(40) NOT NULL,
  `state` VARCHAR(25) DEFAULT NULL COMMENT 'PENDING, FOR_DOWNLOAD, DOWNLOADED',
  `billingid` VARCHAR(40) DEFAULT NULL,
  `dtrequested` DATETIME DEFAULT NULL,
  `requestedby` VARCHAR(40) DEFAULT NULL,
  `collector_objid` VARCHAR(40) DEFAULT NULL,
  `collector_name` VARCHAR(40) DEFAULT NULL,
  `remarks` TEXT,
  `downloaded` SMALLINT(6) DEFAULT NULL COMMENT '0=false, 1=true',
  PRIMARY KEY  (`objid`),
  KEY `ix_dtrequested` (`dtrequested`),
  KEY `ix_collectorid` (`collector_objid`),
  KEY `ix_downloaded` (`downloaded`),
  KEY `FK_specialcollection_billing` (`billingid`),
  CONSTRAINT `FK_specialcollection_billing` FOREIGN KEY (`billingid`) REFERENCES `loan_ledger_billing` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `special_collection_loan` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `billingdetailid` VARCHAR(40) DEFAULT NULL,
  `routecode` VARCHAR(40) DEFAULT NULL,
  `borrower_objid` VARCHAR(40) DEFAULT NULL,
  `borrower_name` VARCHAR(40) DEFAULT NULL,
  `loanapp_objid` VARCHAR(40) DEFAULT NULL,
  `loanapp_appno` VARCHAR(25) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_routecode` (`routecode`),
  KEY `ix_borrowerid` (`borrower_objid`),
  KEY `ix_loanappid` (`loanapp_objid`),
  KEY `ix_loanappno` (`loanapp_appno`),
  KEY `FK_special_collection_parentid_objid` (`parentid`),
  KEY `FK_billingdetailid_objid` (`billingdetailid`),
  CONSTRAINT `FK_billingdetailid_objid` FOREIGN KEY (`billingdetailid`) REFERENCES `loan_ledger_billing_detail` (`objid`),
  CONSTRAINT `FK_special_collection_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `special_collection` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `special_collection_route` (
  `specialcollectionid` VARCHAR(40) NOT NULL,
  `routecode` VARCHAR(40) NOT NULL,
  `billingid` VARCHAR(40) DEFAULT NULL,
  `uploaded` SMALLINT(6) DEFAULT NULL COMMENT '0=false, 1=true',
  PRIMARY KEY  (`specialcollectionid`,`routecode`),
  KEY `ix_billingid` (`billingid`),
  KEY `ix_uploaded` (`uploaded`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;