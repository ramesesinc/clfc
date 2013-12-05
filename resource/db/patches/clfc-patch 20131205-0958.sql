USE clfc2;

ALTER TABLE loan_ledger_billing
ADD INDEX `ix_state` (`state`);

ALTER TABLE loan_ledger_billing_detail
ADD `loanappid` VARCHAR(40) DEFAULT NULL,
ADD `homeaddress` VARCHAR(255) DEFAULT NULL,
ADD `collectionaddress` VARCHAR(255) DEFAULT NULL,
ADD `penalty` DECIMAL(7,2) DEFAULT '0.00',
ADD `interest` DECIMAL(7,2) DEFAULT '0.00',
ADD `others` DECIMAL(7,2) DEFAULT '0.00';

DROP TABLE IF EXISTS `loanapp_borrower_nextto`;
CREATE TABLE `loanapp_borrower_nextto` (
	`objid` VARCHAR(40) NOT NULL DEFAULT '',
	borrowerid VARCHAR(40) DEFAULT NULL,
	nexttoid VARCHAR(40) DEFAULT NULL,
	PRIMARY KEY (`objid`),
	KEY `ix_borrowerid` (`borrowerid`),
	KEY `ix_nexttoid` (`nexttoid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;