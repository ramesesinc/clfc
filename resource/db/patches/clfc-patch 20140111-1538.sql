USE clfc2;

ALTER TABLE batch_collectionsheet_detail_payment
ADD COLUMN `paidby` VARCHAR(50) DEFAULT NULL AFTER `payamount`;