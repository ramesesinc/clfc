USE clfc2;

ALTER TABLE `loan_ledger`
ADD updatetype SMALLINT(10),
ADD INDEX `ix_state` (`state`),
ADD INDEX `ix_paymentmethod` (`paymentmethod`),
ADD INDEX `ix_dtcurrentschedule` (`dtcurrentschedule`);

ALTER TABLE loan_product_type
DROP `surchargerate`;

ALTER TABLE loan_ledger_billing_detail
DROP `penalty`,
DROP `lackinginterest`,
DROP `lackingpenalty`,
ADD `loandate` DATE,
ADD `term` SMALLINT(10);