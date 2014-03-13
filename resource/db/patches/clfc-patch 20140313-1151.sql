USE clfc2;

ALTER TABLE `special_collection`
ADD COLUMN `type` VARCHAR(25) DEFAULT '' COMMENT 'FIELD, ONLINE' AFTER `billingid`,
ADD INDEX `ix_type` (`type`);