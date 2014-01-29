USE clfc2;

ALTER TABLE `batch_collectionsheet`
ADD COLUMN `trackerid` VARCHAR(50) DEFAULT NULL AFTER `postedby`;