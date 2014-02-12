USE clfc2;

ALTER TABLE `loan_ledger_billing`
CHANGE `state` `state` VARCHAR(25) DEFAULT '' COMMENT 'DRAFT, UPLOADED, CANCELLED, UNPOSTED, VOIDED, COMPLETED, UNREMITTED',
ADD COLUMN `modifiedby` VARCHAR(50) DEFAULT '' AFTER `dtmodified`,
CHANGE `collector_username` `collector_name` VARCHAR(50) DEFAULT '',
ADD COLUMN `totalfordownload` SMALLINT(6) DEFAULT 0,
ADD COLUMN `totaldownloaded` SMALLINT(6) DEFAULT 0,
ADD COLUMN `totalunposted` SMALLINT(6) DEFAULT 0,
ADD COLUMN `totalposted` SMALLINT(6) DEFAULT 0;

ALTER TABLE `loan_ledger_billing_route`
ADD COLUMN `downloaded` SMALLINT(6) DEFAULT 0,
ADD COLUMN `uploaded` SMALLINT(6) DEFAULT 0;

ALTER TABLE `void_payment`
ADD COLUMN `dtfiled` DATETIME AFTER `state`,
ADD COLUMN `filedby` VARCHAR(40) DEFAULT '' AFTER `dtfiled`,
ADD COLUMN `txncode` VARCHAR(25) DEFAULT '' AFTER `filedby`,
CHANGE `loanappid` `loanapp_objid` VARCHAR(40),
ADD COLUMN `loanapp_appno` VARCHAR(25) DEFAULT '' AFTER `loanapp_objid`,
CHANGE `collectorid` `collector_objid` VARCHAR(40),
ADD COLUMN `collector_name` VARCHAR(40) DEFAULT '' AFTER `collector_objid`,
ADD COLUMN `dtapproved` DATETIME AFTER `reason`,
ADD COLUMN `approvedby` VARCHAR(40) DEFAULT '' AFTER `dtapproved`,
CHANGE `remarks` `approvedremarks` TEXT,
CHANGE `state` `state` VARCHAR(15) DEFAULT '' COMMENT 'PENDING, APPROVED',
CHANGE `txncode` `txncode` VARCHAR(25) DEFAULT '' COMMENT 'ONLINE, FIELD`',
DROP FOREIGN KEY `FK_paymentid_collectionsheetpayment`;

ALTER TABLE `loan_ledger_subbilling`
CHANGE `subcollector_username` `subcollector_name` VARCHAR(40);
