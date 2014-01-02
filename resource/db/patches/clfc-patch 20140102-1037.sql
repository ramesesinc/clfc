USE clfc2;

ALTER TABLE `batch_collectionsheet_detail_note`
ADD `dtposted` DATETIME DEFAULT NULL,
ADD `postedby` VARCHAR(50) DEFAULT NULL;

ALTER TABLE `batch_collectionsheet_detail_payment`
ADD `dtposted` DATETIME DEFAULT NULL,
ADD `postedby` VARCHAR(50) DEFAULT NULL;

ALTER TABLE `loan_ledger_billing`
ADD `branchid` VARCHAR(40) DEFAULT NULL;