USE clfc2;

ALTER TABLE `mobile_tracker` 
    DROP COLUMN `type`, 
    DROP COLUMN `sessionid`, 
    DROP COLUMN `branchid`, 
    DROP INDEX `uix_sessionid`, 
    DROP INDEX `ix_branchid`; 

ALTER TABLE `mobile_tracker_detail` 
    ADD COLUMN `txntype` VARCHAR(25) NULL AFTER `txndate`, 
    ADD COLUMN `remarks` VARCHAR(255) NULL AFTER `lat`; 

ALTER TABLE `mobile_tracker` 
    ADD COLUMN `terminalid` VARCHAR(15) NULL AFTER `state`, 
    ADD COLUMN `userid` VARCHAR(50) NULL AFTER `terminalid`,
    ADD INDEX `ix_userid` (`userid`); 

ALTER TABLE `mobile_tracker_detail` 
    CHANGE `txntype` `txntype` VARCHAR(25) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'NORMAL,SPECIAL', 
    CHANGE `reftype` `reftype` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'TRACK,PAYMENT,etc...'; 
