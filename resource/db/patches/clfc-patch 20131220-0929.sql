USE clfc2;

CREATE TABLE `void_payment` (
  `objid` VARCHAR(40) NOT NULL,
  `state` VARCHAR(15) DEFAULT NULL,
  `paymentid` VARCHAR(40) DEFAULT NULL,
  `routecode` VARCHAR(40) DEFAULT NULL,
  `loanappid` VARCHAR(40) DEFAULT NULL,
  `collectorid` VARCHAR(40) DEFAULT NULL,
  `reason` TEXT,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_paymentid` (`paymentid`),
  KEY `ix_state` (`state`),
  KEY `ix_loanappid` (`loanappid`),
  KEY `ix_collectorid` (`collectorid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

ALTER TABLE loan_ledger_billing
ADD `subcollector_objid` VARCHAR(50),
ADD `subcollector_username` VARCHAR(50);
