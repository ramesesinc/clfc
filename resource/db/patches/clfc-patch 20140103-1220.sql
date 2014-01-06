USE clfc2;

ALTER TABLE loan_ledger_billing
ADD `dtcancelled` DATETIME DEFAULT NULL,
ADD `cancelledby` VARCHAR(50) DEFAULT NULL,
ADD `remarks` TEXT DEFAULT NULL;

DROP TABLE IF EXISTS `loan_ledger_specialcollection`;
CREATE TABLE `loan_ledger_specialcollection` (
	`objid` VARCHAR(40) PRIMARY KEY NOT NULL DEFAULT '',
	`state` VARCHAR(15) DEFAULT NULL,
	`billingid` VARCHAR(40) DEFAULT '',
	`dtrequested` DATETIME,
	`requestedby` VARCHAR(50) DEFAULT '',
	`collector_objid` VARCHAR(40) DEFAULT '',
	`collector_name` VARCHAR(50) DEFAULT '',
	`remarks` TEXT DEFAULT NULL,
	KEY `ix_billingid`(`billingid`),
	KEY `ix_state`(`state`),
	KEY `ix_dtrequested`(`dtrequested`),
	KEY `ix_collectorid`(`collector_objid`),
	CONSTRAINT `FK_billing_billingid` FOREIGN KEY(`billingid`) REFERENCES `loan_ledger_billing`(`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;