USE clfc2;

DROP TABLE IF EXISTS `loan_ledger_compromise`;
CREATE TABLE `loan_ledger_compromise` (
  `objid` VARCHAR(40) NOT NULL,
  `compromisetype` VARCHAR(40) DEFAULT NULL,
  `iswaveinterest` SMALLINT(6) DEFAULT NULL,
  `iswavepenalty` SMALLINT(6) DEFAULT NULL,
  `isfixedamount` SMALLINT(6) DEFAULT NULL,
  `fixedamount` DECIMAL(10,2) DEFAULT NULL,
  `dtcreated` DATETIME DEFAULT NULL,
  `createdby` VARCHAR(100) DEFAULT NULL,
  `dteffectivefrom` DATE DEFAULT NULL,
  `dteffectiveto` DATE DEFAULT NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_compromisetype` (`compromisetype`),
  KEY `ix_iswaveinterest` (`iswaveinterest`),
  KEY `ix_iswavepenalty` (`iswavepenalty`),
  KEY `ix_fixedamount` (`isfixedamount`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;