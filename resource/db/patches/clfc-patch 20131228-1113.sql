USE clfc2;

ALTER TABLE `batch_collectionsheet_detail_payment`
ADD CONSTRAINT PRIMARY KEY(`objid`);

ALTER TABLE `batch_collectionsheet_detail_note`
ADD CONSTRAINT PRIMARY KEY(`objid`);

ALTER TABLE void_payment
ADD CONSTRAINT `FK_paymentid_collectionsheetpayment` FOREIGN KEY(`paymentid`) REFERENCES `batch_collectionsheet_detail_payment`(`objid`);