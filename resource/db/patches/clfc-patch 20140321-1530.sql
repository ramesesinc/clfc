use clfc2;

ALTER TABLE loan_route
ADD COLUMN `dayperiod` VARCHAR(10) COMMENT 'AM, PM';

CREATE TABLE IF NOT EXISTS `billing_process` (
`objid` VARCHAR(40) NOT NULL,
PRIMARY KEY (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `billing_process_detail` (
`objid` VARCHAR(40) NOT NULL,
`parentid` VARCHAR(40) NOT NULL,
PRIMARY KEY (`objid`),
CONSTRAINT FK_parentid_objid FOREIGN KEY (`parentid`) REFERENCES `billing_process`(`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

ALTER TABLE `loan_payment_detail`
ADD COLUMN `routecode` VARCHAR(40) DEFAULT NULL AFTER `parentid`;