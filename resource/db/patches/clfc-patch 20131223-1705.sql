USE clfc2;

ALTER TABLE loan_ledger_billing
DROP `subcollector_objid`,
DROP `subcollector_username`;

CREATE TABLE `loan_ledger_subbilling` (
  `objid` VARCHAR(40) NOT NULL,
  `parentid` VARCHAR(40) NOT NULL,
  `subcollector_objid` VARCHAR(40) DEFAULT NULL,
  `subcollector_username` VARCHAR(40) DEFAULT NULL,
  PRIMARY KEY  (`objid`,`parentid`),
  KEY `FK_billingid_parentid` (`parentid`),
  KEY `ix_subcollectorid` (`subcollector_objid`),
  CONSTRAINT `FK_billingid_objid` FOREIGN KEY (`objid`) REFERENCES `loan_ledger_billing` (`objid`),
  CONSTRAINT `FK_billingid_parentid` FOREIGN KEY (`parentid`) REFERENCES `loan_ledger_billing` (`objid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

ALTER TABLE batch_collectionsheet_cashbreakdown
ADD CONSTRAINT `FK_collectionsheet_cashbreakdown` FOREIGN KEY(`parentid`) REFERENCES `batch_collectionsheet` (`objid`);

ALTER TABLE batch_collectionsheet_detail_note
ADD CONSTRAINT `FK_detail_note` FOREIGN KEY(`parentid`) REFERENCES `batch_collectionsheet_detail`(`objid`);

ALTER TABLE batch_collectionsheet_detail_payment
ADD CONSTRAINT `FK_detail_payment` FOREIGN KEY(`parentid`) REFERENCES `batch_collectionsheet_detail`(`objid`);