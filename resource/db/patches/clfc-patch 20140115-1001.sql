USE clfc2;

ALTER TABLE loan_ledger_billing_detail
MODIFY COLUMN `penalty` DECIMAL(16, 2) DEFAULT '0.00';

RENAME TABLE `holiday_event_calendar` TO `calendar_event`;