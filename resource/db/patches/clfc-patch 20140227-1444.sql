USE clfc2;

ALTER TABLE void_payment
ADD COLUMN `dtdisapproved` DATETIME,
ADD COLUMN `disapprovedby` VARCHAR(40) DEFAULT '',
ADD COLUMN `disapprovedremarks` TEXT;

RENAME TABLE `void_payment` TO `void_request`;

CREATE TABLE IF NOT EXISTS `void_payment` (
  `objid` VARCHAR(40) NOT NULL,
  `dtfiled` DATETIME DEFAULT NULL,
  `filedby` VARCHAR(40) DEFAULT NULL,
  `collector_objid` VARCHAR(40) DEFAULT NULL,
  `collector_name` VARCHAR(40) DEFAULT NULL,
  `borrower_objid` VARCHAR(40) DEFAULT NULL,
  `borrower_name` VARCHAR(40) DEFAULT NULL,
  `routecode` VARCHAR(40) DEFAULT NULL,
  `refno` VARCHAR(25) DEFAULT NULL,
  `type` VARCHAR(15) DEFAULT NULL COMMENT 'schedule, over',
  `amount` DECIMAL(10,2) DEFAULT NULL,
  `dtpaid` DATE DEFAULT NULL,
  `paidby` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtfiled` (`dtfiled`),
  KEY `ix_collectorid` (`collector_objid`),
  KEY `ix_borrowerid` (`borrower_objid`),
  KEY `ix_routecode` (`routecode`),
  KEY `ix_type` (`type`),
  KEY `ix_dtpaid` (`dtpaid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;