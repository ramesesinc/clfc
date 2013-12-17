USE clfc2;

RENAME TABLE `batch_payment` TO `batch_collectionsheet`;
RENAME TABLE `batch_payment_detail` TO `batch_collectionsheet_detail`;
RENAME TABLE `batch_payment_cashbreakdown` TO `batch_collectionsheet_cashbreakdown`;

ALTER TABLE `batch_collectionsheet_detail`
DROP `refno`,
DROP `paytype`,
DROP `payamount`,
ADD `remarks` TEXT;

DROP TABLE IF EXISTS `batch_collectionsheet_detail_payment`;
CREATE TABLE `batch_collectionsheet_detail_payment` (
	`objid` VARCHAR(40) NOT NULL DEFAULT '',
	`parentid` VARCHAR(40) DEFAULT '',
	`refno` VARCHAR(25) DEFAULT '',
	`paytype` VARCHAR(15) DEFAULT '',
	`payamount` DECIMAL(7,2) DEFAULT '0.00',
	KEY `ix_parentind` (`parentid`),
	KEY `ix_refno` (`refno`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `batch_collectionsheet_detail_note`;
CREATE TABLE `batch_collectionsheet_detail_note` (
	`objid` VARCHAR(40) NOT NULL DEFAULT '',
	`parentid` VARCHAR(40) DEFAULT '',
	`fromdate` DATE,
	`todate` DATE,
	`remarks` VARCHAR(150) DEFAULT '',
	KEY `ix_parentid` (`parentid`),
	KEY `ix_fromdate` (`fromdate`),
	KEY `ix_todate` (`todate`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;