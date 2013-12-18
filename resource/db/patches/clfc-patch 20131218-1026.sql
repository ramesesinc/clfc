USE clfc2;

ALTER TABLE `batch_collectionsheet`
ADD `branchid` VARCHAR(10) DEFAULT '';

ALTER TABLE `loan_ledger_compromise`
DROP `iswavepenalty`,
ADD `iswaivepenalty` SMALLINT(6),
ADD INDEX `ix_iswaivepenalty` (`iswaivepenalty`),
DROP `iswaveinterest`,
ADD `iswaiveinterest` SMALLINT(6),
ADD INDEX `ix_iswaiveinterest` (`iswaiveinterest`);