ALTER TABLE holiday_event_calendar
DROP INDEX `ix_date`,
ADD UNIQUE INDEX `uix_date` (`date`);