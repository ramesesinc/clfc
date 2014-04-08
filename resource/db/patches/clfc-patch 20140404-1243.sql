USE clfc2;

ALTER TABLE field_collection
DROP COLUMN `trackerid`;

ALTER TABLE field_collection_route
ADD COLUMN `trackerid` VARCHAR(40);

ALTER TABLE mobile_tracker
ADD COLUMN `routecode` VARCHAR(40);