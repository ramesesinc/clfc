ALTER TABLE `loan_ledger`
ADD `state` VARCHAR(25) DEFAULT NULL,
ADD `paymentmethod` VARCHAR(15) DEFAULT NULL,
ADD dtcurrentschedule DATE DEFAULT NULL,
ADD updatetype SMALLINT(10);

ALTER TABLE `loan_ledger`
ADD INDEX `ix_state` (`state`),
ADD INDEX `ix_paymentmethod` (`paymentmethod`),
ADD INDEX `ix_dtcurrentschedule` (`dtcurrentschedule`);

ALTER TABLE `loan_ledger_detail`
DROP `paytype`,
ADD  `baseamount` DECIMAL(10,2) DEFAULT '0.00',
ADD `groupbaseamount` DECIMAL(10,2) DEFAULT '0.00';

DROP TABLE IF EXISTS `loan_ledger_billing`;
CREATE TABLE `loan_ledger_billing` (
  `objid` VARCHAR(40) NOT NULL,
  `state` VARCHAR(25) DEFAULT NULL,
  `dtcreated` DATETIME DEFAULT NULL,
  `createdby` VARCHAR(50) DEFAULT NULL,
  `dtmodified` DATETIME DEFAULT NULL,
  `collector_objid` VARCHAR(50) DEFAULT NULL,
  `collector_username` VARCHAR(50) DEFAULT NULL,
  `billdate` DATE DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_dtmodified` (`dtmodified`),
  KEY `ix_collectorid` (`collector_objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `loan_ledger_billing_detail`;
CREATE TABLE `loan_ledger_billing_detail` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) DEFAULT NULL,
  `ledgerid` VARCHAR(40) DEFAULT NULL,
  `route_code` VARCHAR(40) DEFAULT NULL,
  `acctid` VARCHAR(40) DEFAULT NULL,
  `acctname` VARCHAR(50) DEFAULT NULL,
  `loanamount` DECIMAL(16,2) DEFAULT '0.00',
  `appno` VARCHAR(25) DEFAULT NULL,
  `dailydue` DECIMAL(7,2) DEFAULT '0.00',
  `amountdue` DECIMAL(10,2) DEFAULT '0.00',
  `penalty` DECIMAL(7,2) DEFAULT '0.00',
  `overpaymentamount` DECIMAL(7,2) DEFAULT '0.00',
  `lackinginterest` DECIMAL(7,2) DEFAULT '0.00',
  `lackingpenalty` DECIMAL(7,2) DEFAULT '0.00',
  `balance` DECIMAL(16,2) DEFAULT '0.00',
  `refno` VARCHAR(25) DEFAULT NULL,
  `txndate` DATE DEFAULT NULL,
  `dtmatured` DATE DEFAULT NULL,
  `isfirstbill` SMALLINT(10) DEFAULT NULL,
  `paymentmethod` VARCHAR(15) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`),
  KEY `ix_ledgerid` (`ledgerid`),
  KEY `ix_acctid` (`acctid`),
  KEY `ix_refno` (`refno`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_appno` (`appno`),
  CONSTRAINT `FK_loan_ledger_billing_detail` FOREIGN KEY (`parentid`) REFERENCES `loan_ledger_billing` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `loan_ledger_billing_lock`;
CREATE TABLE `loan_ledger_billing_lock` (
  `billingid` VARCHAR(40) NOT NULL,
  `routecode` VARCHAR(40) NOT NULL,
  PRIMARY KEY  (`billingid`,`routecode`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `loan_ledger_billing_route`;
CREATE TABLE `loan_ledger_billing_route` (
  `billingid` VARCHAR(40) NOT NULL,
  `routecode` VARCHAR(40) NOT NULL,
  PRIMARY KEY  (`billingid`,`routecode`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `loan_exemption`;
CREATE TABLE `loan_exemption` (
  `objid` VARCHAR(50) NOT NULL,
  `state` VARCHAR(25) DEFAULT NULL,
  `ledgerid` VARCHAR(50) DEFAULT NULL,
  `batchpaymentid` VARCHAR(50) DEFAULT NULL,
  `txndate` DATE DEFAULT NULL,
  `dtcreated` DATETIME DEFAULT NULL,
  `createdby` VARCHAR(100) DEFAULT NULL,
  `reason` TEXT,
  PRIMARY KEY  (`objid`),
  KEY `ix_batchpaymentid` (`batchpaymentid`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_ledgerid` (`ledgerid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

ALTER TABLE loan_product_type
DROP `surchargerate`;