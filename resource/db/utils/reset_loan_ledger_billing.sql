DELETE FROM loan_ledger_billing_route WHERE billingid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM loan_ledger_billing_lock WHERE billingid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM loan_ledger_billing_detail WHERE parentid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM loan_ledger_billing WHERE objid='LB-f0e8694:142c7ba94f5:-7ff4';

DELETE FROM batch_payment_detail WHERE parentid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM batch_payment_cashbreakdown WHERE parentid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM batch_payment WHERE objid='LB-f0e8694:142c7ba94f5:-7ff4';

DELETE FROM loan_payment_detail WHERE parentid='LB-f0e8694:142c7ba94f5:-7ff4';
DELETE FROM loan_payment WHERE objid='LB-f0e8694:142c7ba94f5:-7ff4';
