-- MySQL dump 10.10
--
-- Host: localhost    Database: clfc2b
-- ------------------------------------------------------
-- Server version	5.0.27-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `clfc2b`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `clfc2` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `clfc2`;

--
-- Table structure for table `batch_payment`
--

DROP TABLE IF EXISTS `batch_payment`;
CREATE TABLE `batch_payment` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL COMMENT 'DRAFT, APPROVED',
  `dtposted` datetime default NULL,
  `postedby` varchar(40) default NULL,
  `txndate` date default NULL,
  `dtcreated` datetime default NULL,
  `route_code` varchar(40) default NULL,
  `totalcount` smallint(6) default NULL,
  `totalamount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreated` (`dtcreated`),
  KEY `ix_dtposted` (`dtposted`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_routecode` (`route_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `batch_payment`
--

LOCK TABLES `batch_payment` WRITE;
/*!40000 ALTER TABLE `batch_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `batch_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batch_payment_cashbreakdown`
--

DROP TABLE IF EXISTS `batch_payment_cashbreakdown`;
CREATE TABLE `batch_payment_cashbreakdown` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `caption` varchar(25) default NULL,
  `denomination` decimal(7,2) default '0.00',
  `qty` decimal(7,2) default '0.00',
  `amount` decimal(7,2) default '0.00',
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `batch_payment_cashbreakdown`
--

LOCK TABLES `batch_payment_cashbreakdown` WRITE;
/*!40000 ALTER TABLE `batch_payment_cashbreakdown` DISABLE KEYS */;
/*!40000 ALTER TABLE `batch_payment_cashbreakdown` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batch_payment_detail`
--

DROP TABLE IF EXISTS `batch_payment_detail`;
CREATE TABLE `batch_payment_detail` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `refno` varchar(25) default NULL,
  `appid` varchar(40) default NULL,
  `paytype` varchar(15) default NULL COMMENT 'advance, over',
  `payamount` decimal(7,2) default '0.00',
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`),
  KEY `ix_appid` (`appid`),
  CONSTRAINT `FK_batch_payment_detail` FOREIGN KEY (`parentid`) REFERENCES `batch_payment` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `batch_payment_detail`
--

LOCK TABLES `batch_payment_detail` WRITE;
/*!40000 ALTER TABLE `batch_payment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `batch_payment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
CREATE TABLE `borrower` (
  `objid` varchar(40) NOT NULL default '',
  `lastname` varchar(50) default NULL,
  `firstname` varchar(50) default NULL,
  `middlename` varchar(50) default NULL,
  `address` varchar(250) default NULL,
  `gender` varchar(3) default NULL,
  `birthdate` date default NULL,
  `civilstatus` varchar(25) default NULL,
  `citizenship` varchar(50) default NULL,
  `contactno` varchar(25) default NULL,
  `residency_type` varchar(25) default NULL,
  `residency_since` date default NULL,
  `residency_remarks` text,
  `residency_renttype` varchar(25) default NULL,
  `residency_rentamount` decimal(10,2) default '0.00',
  `occupancy_type` varchar(25) default NULL,
  `occupancy_since` date default NULL,
  `occupancy_remarks` text,
  `occupancy_renttype` varchar(25) default NULL,
  `occupancy_rentamount` decimal(10,2) default '0.00',
  `spouse_objid` varchar(40) default NULL,
  `spouse_lastname` varchar(50) default NULL,
  `spouse_firstname` varchar(50) default NULL,
  `spouse_middlename` varchar(50) default NULL,
  `spouse_address` varchar(250) default NULL,
  `spouse_gender` varchar(3) default NULL,
  `spouse_birthdate` date default NULL,
  `spouse_civilstatus` varchar(25) default NULL,
  `spouse_citizenship` varchar(50) default NULL,
  `spouse_contactno` varchar(25) default NULL,
  `spouse_residency_type` varchar(25) default NULL,
  `spouse_residency_since` date default NULL,
  `spouse_residency_remarks` text,
  `spouse_residency_renttype` varchar(25) default NULL,
  `spouse_residency_rentamount` decimal(10,2) default '0.00',
  `spouse_occupancy_type` varchar(25) default NULL,
  `spouse_occupancy_since` date default NULL,
  `spouse_occupancy_remarks` text,
  `spouse_occupancy_renttype` varchar(25) default NULL,
  `spouse_occupancy_rentamount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`),
  KEY `ix_lfm` (`lastname`,`firstname`,`middlename`),
  KEY `ix_spouse_objid` (`spouse_objid`),
  KEY `ix_spouse_lfm` (`spouse_lastname`,`spouse_firstname`,`spouse_middlename`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_bankacct`
--

DROP TABLE IF EXISTS `borrower_bankacct`;
CREATE TABLE `borrower_bankacct` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `type` varchar(25) default NULL,
  `bankname` varchar(50) default NULL,
  `acctno` varchar(50) default NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `fk_borrowerbankacct_parentid_objid` (`parentid`),
  CONSTRAINT `fk_borrowerbankacct_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `borrower` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower_bankacct`
--

LOCK TABLES `borrower_bankacct` WRITE;
/*!40000 ALTER TABLE `borrower_bankacct` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower_bankacct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_children`
--

DROP TABLE IF EXISTS `borrower_children`;
CREATE TABLE `borrower_children` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `name` varchar(150) default NULL,
  `age` smallint(6) default NULL,
  `education` text,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_name` (`name`),
  KEY `fk_borrowerchildren_parentid_objid` (`parentid`),
  CONSTRAINT `fk_borrowerchildren_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `borrower` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower_children`
--

LOCK TABLES `borrower_children` WRITE;
/*!40000 ALTER TABLE `borrower_children` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower_children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_education`
--

DROP TABLE IF EXISTS `borrower_education`;
CREATE TABLE `borrower_education` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `attainment` varchar(250) default NULL,
  `school` varchar(150) default NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `fk_borrowereducation_parentid_objid` (`parentid`),
  CONSTRAINT `fk_borrowereducation_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `borrower` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower_education`
--

LOCK TABLES `borrower_education` WRITE;
/*!40000 ALTER TABLE `borrower_education` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_parent`
--

DROP TABLE IF EXISTS `borrower_parent`;
CREATE TABLE `borrower_parent` (
  `objid` varchar(40) NOT NULL,
  `fathername` varchar(150) default NULL,
  `fatherage` smallint(6) default NULL,
  `mothername` varchar(150) default NULL,
  `motherage` smallint(6) default NULL,
  `address` varchar(250) default NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_fathername` (`fathername`),
  KEY `ix_mothername` (`mothername`),
  CONSTRAINT `fk_borrowerparent_objid_objid` FOREIGN KEY (`objid`) REFERENCES `borrower` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower_parent`
--

LOCK TABLES `borrower_parent` WRITE;
/*!40000 ALTER TABLE `borrower_parent` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower_parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower_sibling`
--

DROP TABLE IF EXISTS `borrower_sibling`;
CREATE TABLE `borrower_sibling` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `name` varchar(150) default NULL,
  `age` smallint(6) default NULL,
  `address` varchar(250) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_name` (`name`),
  KEY `fk_borrowersibling_parentid_objid` (`parentid`),
  CONSTRAINT `fk_borrowersibling_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `borrower` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrower_sibling`
--

LOCK TABLES `borrower_sibling` WRITE;
/*!40000 ALTER TABLE `borrower_sibling` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower_sibling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `mode` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `custno` varchar(15) default NULL,
  `branchid` varchar(32) default NULL,
  `name` varchar(150) default NULL,
  `lastname` varchar(50) default NULL,
  `firstname` varchar(50) default NULL,
  `middlename` varchar(50) default NULL,
  `gender` varchar(2) default NULL,
  `birthdate` date default NULL,
  `citizenship` varchar(50) default NULL,
  `civilstatus` varchar(15) default NULL,
  `address` varchar(255) default NULL,
  `previousaddress` varchar(255) default NULL,
  `dtmodified` datetime default NULL,
  `modifiedby` varchar(50) default NULL,
  `lockid` varchar(32) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_custno` (`custno`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_dtmodifiedby` (`dtmodified`,`modifiedby`),
  KEY `ix_name` (`name`),
  KEY `ix_lfm` (`lastname`,`firstname`,`middlename`),
  KEY `ix_firstname` (`firstname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_connection`
--

DROP TABLE IF EXISTS `customer_connection`;
CREATE TABLE `customer_connection` (
  `principalid` varchar(40) NOT NULL,
  `relaterid` varchar(40) NOT NULL,
  `relationship` varchar(50) default NULL,
  PRIMARY KEY  (`principalid`,`relaterid`),
  KEY `ix_relaterid` (`relaterid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_connection`
--

LOCK TABLES `customer_connection` WRITE;
/*!40000 ALTER TABLE `customer_connection` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employment`
--

DROP TABLE IF EXISTS `employment`;
CREATE TABLE `employment` (
  `objid` varchar(40) NOT NULL,
  `refid` varchar(40) default NULL,
  `employer` varchar(100) default NULL,
  `address` varchar(250) default NULL,
  `contactno` varchar(25) default NULL,
  `position` varchar(50) default NULL,
  `salary` decimal(10,2) default '0.00',
  `lengthofservice` varchar(32) default NULL,
  `status` varchar(25) default NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_refid` (`refid`),
  KEY `ix_employer` (`employer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employment`
--

LOCK TABLES `employment` WRITE;
/*!40000 ALTER TABLE `employment` DISABLE KEYS */;
/*!40000 ALTER TABLE `employment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity` (
  `objid` varchar(50) NOT NULL default '',
  `entityno` varchar(15) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `mailingaddress` varchar(255) default NULL,
  `type` varchar(25) NOT NULL default '',
  `sys_lastupdate` varchar(25) default NULL,
  `sys_lastupdateby` varchar(50) default NULL,
  `remarks` varchar(160) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `ux_entity_entityno` (`entityno`),
  KEY `ix_entity_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entity`
--

LOCK TABLES `entity` WRITE;
/*!40000 ALTER TABLE `entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entitycontact`
--

DROP TABLE IF EXISTS `entitycontact`;
CREATE TABLE `entitycontact` (
  `objid` varchar(50) NOT NULL default '',
  `entityid` varchar(50) NOT NULL default '',
  `contacttype` varchar(25) NOT NULL default '',
  `contact` varchar(50) NOT NULL,
  PRIMARY KEY  (`objid`),
  KEY `FK_entitycontact_entity` (`entityid`),
  CONSTRAINT `FK_entitycontact_entity` FOREIGN KEY (`entityid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entitycontact`
--

LOCK TABLES `entitycontact` WRITE;
/*!40000 ALTER TABLE `entitycontact` DISABLE KEYS */;
/*!40000 ALTER TABLE `entitycontact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entityidcard`
--

DROP TABLE IF EXISTS `entityidcard`;
CREATE TABLE `entityidcard` (
  `objid` varchar(50) NOT NULL default '',
  `entityid` varchar(50) NOT NULL default '',
  `idtype` varchar(50) NOT NULL default '',
  `idno` varchar(25) NOT NULL default '',
  `expiry` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `ux_entityidcard_entityid_idtype_idno` (`entityid`,`idtype`,`idno`),
  KEY `FK_entityidcard_entity` (`entityid`),
  CONSTRAINT `FK_entityidcard_entity` FOREIGN KEY (`entityid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entityidcard`
--

LOCK TABLES `entityidcard` WRITE;
/*!40000 ALTER TABLE `entityidcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `entityidcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entityindividual`
--

DROP TABLE IF EXISTS `entityindividual`;
CREATE TABLE `entityindividual` (
  `objid` varchar(50) NOT NULL default '',
  `lastname` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `middlename` varchar(50) default NULL,
  `birthdate` date default NULL,
  `birthplace` varchar(160) default NULL,
  `citizenship` varchar(50) default NULL,
  `gender` varchar(10) default NULL,
  `civilstatus` varchar(15) default NULL,
  `profession` varchar(50) default NULL,
  `tin` varchar(25) default NULL,
  `sss` varchar(25) default NULL,
  `height` varchar(10) default NULL,
  `weight` varchar(10) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_entityindividual_lastname` (`lastname`),
  KEY `ix_entityindividual_firstname` (`firstname`),
  CONSTRAINT `FK_entityindividual_entity` FOREIGN KEY (`objid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entityindividual`
--

LOCK TABLES `entityindividual` WRITE;
/*!40000 ALTER TABLE `entityindividual` DISABLE KEYS */;
/*!40000 ALTER TABLE `entityindividual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entityjuridical`
--

DROP TABLE IF EXISTS `entityjuridical`;
CREATE TABLE `entityjuridical` (
  `objid` varchar(50) NOT NULL,
  `tin` varchar(25) default NULL,
  `dtregistered` date default NULL,
  `orgtype` varchar(25) default NULL COMMENT 'SINGLEPROPERTORSHIP, CORPORATION, etc',
  `nature` varchar(50) default NULL COMMENT 'RETAILER, WHOLESALER, etc.',
  PRIMARY KEY  (`objid`),
  KEY `ix_tin` (`tin`),
  CONSTRAINT `FK_entityjuridical_entity` FOREIGN KEY (`objid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entityjuridical`
--

LOCK TABLES `entityjuridical` WRITE;
/*!40000 ALTER TABLE `entityjuridical` DISABLE KEYS */;
/*!40000 ALTER TABLE `entityjuridical` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entitymember`
--

DROP TABLE IF EXISTS `entitymember`;
CREATE TABLE `entitymember` (
  `objid` varchar(50) character set utf8 NOT NULL default '',
  `entityid` varchar(50) character set utf8 NOT NULL default '',
  `itemno` int(11) NOT NULL,
  `prefix` varchar(25) character set utf8 default NULL,
  `taxpayer_objid` varchar(50) character set utf8 NOT NULL default '',
  `taxpayer_name` text character set utf8 NOT NULL,
  `taxpayer_address` varchar(160) character set utf8 NOT NULL,
  `suffix` varchar(25) character set utf8 default NULL,
  `remarks` varchar(160) character set utf8 default NULL,
  PRIMARY KEY  (`objid`),
  KEY `FK_entitymember_entity` (`entityid`),
  KEY `ix_taxpayer_name` (`taxpayer_name`(255)),
  KEY `ix_taxpayer_objid` (`taxpayer_objid`),
  CONSTRAINT `FK_entitymember_entity` FOREIGN KEY (`entityid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entitymember`
--

LOCK TABLES `entitymember` WRITE;
/*!40000 ALTER TABLE `entitymember` DISABLE KEYS */;
/*!40000 ALTER TABLE `entitymember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entitymultiple`
--

DROP TABLE IF EXISTS `entitymultiple`;
CREATE TABLE `entitymultiple` (
  `objid` varchar(50) NOT NULL,
  `fullname` text,
  PRIMARY KEY  (`objid`),
  CONSTRAINT `FK_entitymultiple_entity` FOREIGN KEY (`objid`) REFERENCES `entity` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entitymultiple`
--

LOCK TABLES `entitymultiple` WRITE;
/*!40000 ALTER TABLE `entitymultiple` DISABLE KEYS */;
/*!40000 ALTER TABLE `entitymultiple` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday_event_calendar`
--

DROP TABLE IF EXISTS `holiday_event_calendar`;
CREATE TABLE `holiday_event_calendar` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `name` varchar(150) default NULL,
  `date` date default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_name` (`name`),
  KEY `ix_date` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `holiday_event_calendar`
--

LOCK TABLES `holiday_event_calendar` WRITE;
/*!40000 ALTER TABLE `holiday_event_calendar` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_event_calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_exemption`
--

DROP TABLE IF EXISTS `loan_exemption`;
CREATE TABLE `loan_exemption` (
  `objid` varchar(50) NOT NULL,
  `state` varchar(25) default NULL,
  `ledgerid` varchar(50) default NULL,
  `batchpaymentid` varchar(50) default NULL,
  `txndate` date default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(100) default NULL,
  `reason` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_batchpaymentid` (`batchpaymentid`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_ledgerid` (`ledgerid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_exemption`
--

LOCK TABLES `loan_exemption` WRITE;
/*!40000 ALTER TABLE `loan_exemption` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_exemption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger`
--

DROP TABLE IF EXISTS `loan_ledger`;
CREATE TABLE `loan_ledger` (
  `objid` varchar(50) NOT NULL,
  `acctid` varchar(50) default NULL,
  `acctname` varchar(50) default NULL,
  `appid` varchar(50) default NULL,
  `state` varchar(25) default NULL,
  `producttypeid` varchar(50) default NULL,
  `loancount` int(11) default '0',
  `term` int(11) default '0',
  `paymentmethod` varchar(15) default NULL,
  `balance` decimal(16,2) default '0.00',
  `dailydue` decimal(16,2) default '0.00',
  `interestamount` decimal(5,2) default '0.00',
  `overpaymentamount` decimal(10,2) default '0.00',
  `overduepenalty` decimal(10,2) default '0.00',
  `absentpenalty` decimal(10,2) default '0.00',
  `totalprincipal` decimal(16,2) default '0.00',
  `totaldue` decimal(16,2) default '0.00',
  `compromiseid` varchar(40) default NULL,
  `dtstarted` date default NULL,
  `dtmatured` date default NULL,
  `dtlastpaid` date default NULL,
  `dtcurrentschedule` date default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_appid` (`appid`),
  KEY `ix_acctid` (`acctid`),
  KEY `ix_acctname` (`acctname`),
  KEY `ix_producttypeid` (`producttypeid`),
  KEY `ix_compromiseid` (`compromiseid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger`
--

LOCK TABLES `loan_ledger` WRITE;
/*!40000 ALTER TABLE `loan_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_billing`
--

DROP TABLE IF EXISTS `loan_ledger_billing`;
CREATE TABLE `loan_ledger_billing` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `dtmodified` datetime default NULL,
  `collector_objid` varchar(50) default NULL,
  `collector_username` varchar(50) default NULL,
  `billdate` date default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_dtmodified` (`dtmodified`),
  KEY `ix_collectorid` (`collector_objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_billing`
--

LOCK TABLES `loan_ledger_billing` WRITE;
/*!40000 ALTER TABLE `loan_ledger_billing` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_billing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_billing_detail`
--

DROP TABLE IF EXISTS `loan_ledger_billing_detail`;
CREATE TABLE `loan_ledger_billing_detail` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `ledgerid` varchar(40) default NULL,
  `route_code` varchar(40) default NULL,
  `acctid` varchar(40) default NULL,
  `acctname` varchar(50) default NULL,
  `loanamount` decimal(16,2) default '0.00',
  `appno` varchar(25) default NULL,
  `dailydue` decimal(7,2) default '0.00',
  `amountdue` decimal(10,2) default '0.00',
  `penalty` decimal(7,2) default '0.00',
  `overpaymentamount` decimal(7,2) default '0.00',
  `lackinginterest` decimal(7,2) default '0.00',
  `lackingpenalty` decimal(7,2) default '0.00',
  `balance` decimal(16,2) default '0.00',
  `refno` varchar(25) default NULL,
  `txndate` date default NULL,
  `dtmatured` date default NULL,
  `isfirstbill` smallint(10) default NULL,
  `paymentmethod` varchar(15) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`),
  KEY `ix_ledgerid` (`ledgerid`),
  KEY `ix_acctid` (`acctid`),
  KEY `ix_refno` (`refno`),
  KEY `ix_txndate` (`txndate`),
  KEY `ix_appno` (`appno`),
  CONSTRAINT `FK_loan_ledger_billing_detail` FOREIGN KEY (`parentid`) REFERENCES `loan_ledger_billing` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_billing_detail`
--

LOCK TABLES `loan_ledger_billing_detail` WRITE;
/*!40000 ALTER TABLE `loan_ledger_billing_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_billing_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_billing_lock`
--

DROP TABLE IF EXISTS `loan_ledger_billing_lock`;
CREATE TABLE `loan_ledger_billing_lock` (
  `billingid` varchar(40) NOT NULL,
  `routecode` varchar(40) NOT NULL,
  PRIMARY KEY  (`billingid`,`routecode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_billing_lock`
--

LOCK TABLES `loan_ledger_billing_lock` WRITE;
/*!40000 ALTER TABLE `loan_ledger_billing_lock` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_billing_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_billing_route`
--

DROP TABLE IF EXISTS `loan_ledger_billing_route`;
CREATE TABLE `loan_ledger_billing_route` (
  `billingid` varchar(40) NOT NULL,
  `routecode` varchar(40) NOT NULL,
  PRIMARY KEY  (`billingid`,`routecode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_billing_route`
--

LOCK TABLES `loan_ledger_billing_route` WRITE;
/*!40000 ALTER TABLE `loan_ledger_billing_route` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_billing_route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_detail`
--

DROP TABLE IF EXISTS `loan_ledger_detail`;
CREATE TABLE `loan_ledger_detail` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `state` varchar(25) default NULL COMMENT 'RECEIVED, OFFSET',
  `amtdue` decimal(10,2) default '0.00',
  `interestdue` decimal(10,2) default '0.00',
  `penaltydue` decimal(10,2) default '0.00',
  `amtpaid` decimal(10,2) default '0.00',
  `interestpaid` decimal(10,2) default '0.00',
  `penaltypaid` decimal(10,2) default '0.00',
  `amtbal` decimal(10,2) default '0.00',
  `interestbal` decimal(10,2) default '0.00',
  `penaltybal` decimal(10,2) default '0.00',
  `dtpaid` date default NULL,
  `refno` varchar(25) default NULL,
  `day` smallint(6) default '0',
  `balance` decimal(16,2) default '0.00',
  `partialpayment` decimal(10,2) default '0.00',
  `txndate` datetime default NULL,
  `baseamount` decimal(10,2) default '0.00',
  `groupbaseamount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`),
  KEY `fk_loanledgerdetail_parentid_objid` (`parentid`),
  KEY `ix_dtpaid` (`dtpaid`),
  KEY `ix_refno` (`refno`),
  KEY `ix_day` (`day`),
  KEY `ix_txndate` (`txndate`),
  CONSTRAINT `fk_loanledgerdetail_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `loan_ledger` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_detail`
--

LOCK TABLES `loan_ledger_detail` WRITE;
/*!40000 ALTER TABLE `loan_ledger_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_ledger_history`
--

DROP TABLE IF EXISTS `loan_ledger_history`;
CREATE TABLE `loan_ledger_history` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `state` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `amtdue` decimal(10,2) default '0.00',
  `interestdue` decimal(10,2) default '0.00',
  `penaltydue` decimal(10,2) default '0.00',
  `amtpaid` decimal(10,2) default '0.00',
  `interestpaid` decimal(10,2) default '0.00',
  `penaltypaid` decimal(10,2) default '0.00',
  `amtbal` decimal(10,2) default '0.00',
  `interestbal` decimal(10,2) default '0.00',
  `penaltybal` decimal(10,2) default '0.00',
  `dtpaid` date default NULL,
  `refno` varchar(25) default NULL,
  `day` smallint(11) default '0',
  `balance` decimal(10,2) default '0.00',
  `partialpayment` decimal(10,2) default '0.00',
  `paytype` varchar(15) default NULL COMMENT 'advance, over',
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreated` (`dtcreated`),
  KEY `ix_parentid` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_ledger_history`
--

LOCK TABLES `loan_ledger_history` WRITE;
/*!40000 ALTER TABLE `loan_ledger_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_ledger_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_payment`
--

DROP TABLE IF EXISTS `loan_payment`;
CREATE TABLE `loan_payment` (
  `objid` varchar(40) NOT NULL,
  `txndate` date default NULL,
  `dtcreated` datetime default NULL,
  `totalcount` smallint(6) default NULL,
  `totalamount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_payment`
--

LOCK TABLES `loan_payment` WRITE;
/*!40000 ALTER TABLE `loan_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_payment_detail`
--

DROP TABLE IF EXISTS `loan_payment_detail`;
CREATE TABLE `loan_payment_detail` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `refno` varchar(25) default NULL,
  `appid` varchar(40) default NULL,
  `paytype` varchar(15) default NULL COMMENT 'advance, over',
  `payamount` decimal(7,2) default '0.00',
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_payment_detail`
--

LOCK TABLES `loan_payment_detail` WRITE;
/*!40000 ALTER TABLE `loan_payment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_payment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_product_type`
--

DROP TABLE IF EXISTS `loan_product_type`;
CREATE TABLE `loan_product_type` (
  `name` varchar(50) NOT NULL,
  `description` varchar(150) default NULL,
  `term` smallint(6) default '0',
  `interestrate` decimal(5,2) default '0.00',
  `pastduerate` decimal(5,2) default '0.00',
  `underpaymentpenalty` decimal(5,2) default '0.00',
  `surchargerate` decimal(5,2) default '0.00',
  `absentpenalty` decimal(5,2) default '0.00',
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_product_type`
--

LOCK TABLES `loan_product_type` WRITE;
/*!40000 ALTER TABLE `loan_product_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_route`
--

DROP TABLE IF EXISTS `loan_route`;
CREATE TABLE `loan_route` (
  `code` varchar(40) NOT NULL default '',
  `description` varchar(150) default NULL,
  `area` varchar(255) default NULL,
  PRIMARY KEY  (`code`),
  UNIQUE KEY `uix_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_route`
--

LOCK TABLES `loan_route` WRITE;
/*!40000 ALTER TABLE `loan_route` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_state_type`
--

DROP TABLE IF EXISTS `loan_state_type`;
CREATE TABLE `loan_state_type` (
  `name` varchar(40) NOT NULL,
  `level` smallint(6) default '0',
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_state_type`
--

LOCK TABLES `loan_state_type` WRITE;
/*!40000 ALTER TABLE `loan_state_type` DISABLE KEYS */;
INSERT INTO `loan_state_type` VALUES ('APPROVED',5),('BACK_OUT',10),('CANCELLED_OUT',10),('CLOSED',20),('DISAPPROVED',10),('DISQUALIFIED_OUT',10),('FOR_APPROVAL',4),('FOR_CRECOM',3),('FOR_INSPECTION',2),('FOR_RELEASE',6),('INCOMPLETE',0),('PENDING',1),('RELEASED',7);
/*!40000 ALTER TABLE `loan_state_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp`
--

DROP TABLE IF EXISTS `loanapp`;
CREATE TABLE `loanapp` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `appno` varchar(25) default NULL,
  `apptype` varchar(25) default NULL,
  `appmode` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `loanamount` decimal(10,2) default '0.00',
  `branch_code` varchar(10) default NULL,
  `branch_name` varchar(60) default NULL,
  `borrower_objid` varchar(40) default NULL,
  `borrower_name` varchar(150) default NULL,
  `producttype_name` varchar(40) default NULL,
  `producttype_term` smallint(6) default NULL,
  `route_code` varchar(40) default NULL,
  `purpose` text,
  `remarks` text,
  `approval_type` varchar(12) default NULL,
  `approval_amount` decimal(10,2) default '0.00',
  `approval_absencetype` varchar(12) default NULL,
  `approval_absencepolicy` int(11) default '0',
  `approval_absenceprovision` int(11) default '0',
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_appno` (`appno`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_branchcode` (`branch_code`),
  KEY `ix_borrowerobjid` (`borrower_objid`),
  KEY `ix_borrowername` (`borrower_name`),
  KEY `ix_branchname` (`branch_name`),
  KEY `ix_routecode` (`route_code`),
  KEY `ix_producttypename` (`producttype_name`),
  KEY `ix_approvaltype` (`approval_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp`
--

LOCK TABLES `loanapp` WRITE;
/*!40000 ALTER TABLE `loanapp` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_borrower`
--

DROP TABLE IF EXISTS `loanapp_borrower`;
CREATE TABLE `loanapp_borrower` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `borrowerid` varchar(40) default NULL,
  `borrowername` varchar(150) default NULL,
  `principalid` varchar(40) default NULL,
  `relaterid` varchar(40) default NULL,
  `type` varchar(12) default NULL,
  `relation` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`),
  KEY `ix_borrowerid` (`borrowerid`),
  KEY `ix_borrowername` (`borrowername`),
  KEY `ix_principalid` (`principalid`),
  KEY `ix_relaterid` (`relaterid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_borrower`
--

LOCK TABLES `loanapp_borrower` WRITE;
/*!40000 ALTER TABLE `loanapp_borrower` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_business`
--

DROP TABLE IF EXISTS `loanapp_business`;
CREATE TABLE `loanapp_business` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `tradename` varchar(250) default NULL,
  `address` varchar(250) default NULL,
  `kind` varchar(50) default NULL,
  `dtstarted` datetime default NULL,
  `capital` decimal(10,2) default '0.00',
  `ownership` varchar(50) default NULL,
  `stallsize` decimal(7,2) default '0.00',
  `avgsales` varchar(50) default NULL,
  `officehours` varchar(50) default NULL,
  `contactno` varchar(25) default NULL,
  `occupancy_type` varchar(25) default NULL,
  `occupancy_remarks` text,
  `occupancy_renttype` varchar(25) default NULL,
  `occupancy_rentamount` decimal(10,2) default '0.00',
  `ci_evaluation` text,
  `ci_physicaloutlook` text,
  `ci_dtfiled` date default NULL,
  `ci_timefiled` varchar(10) default NULL,
  `ci_filedby` varchar(50) default NULL,
  `ci_amount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_tradename` (`tradename`),
  KEY `fk_lonappbusiness_parentid_loanapp_objid` (`parentid`),
  CONSTRAINT `fk_lonappbusiness_parentid_loanapp_objid` FOREIGN KEY (`parentid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_business`
--

LOCK TABLES `loanapp_business` WRITE;
/*!40000 ALTER TABLE `loanapp_business` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_capture`
--

DROP TABLE IF EXISTS `loanapp_capture`;
CREATE TABLE `loanapp_capture` (
  `objid` varchar(40) NOT NULL,
  `version` smallint(6) default '1',
  `loanno` varchar(16) default NULL,
  `clienttype` varchar(12) default NULL COMMENT 'WALK-IN,MARKETED',
  `marketedby` varchar(100) default NULL,
  `dtreleased` date default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_loanno_version` (`loanno`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_capture`
--

LOCK TABLES `loanapp_capture` WRITE;
/*!40000 ALTER TABLE `loanapp_capture` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_capture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_capture_open`
--

DROP TABLE IF EXISTS `loanapp_capture_open`;
CREATE TABLE `loanapp_capture_open` (
  `objid` varchar(40) NOT NULL,
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_capture_open`
--

LOCK TABLES `loanapp_capture_open` WRITE;
/*!40000 ALTER TABLE `loanapp_capture_open` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_capture_open` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_collateral_appliance`
--

DROP TABLE IF EXISTS `loanapp_collateral_appliance`;
CREATE TABLE `loanapp_collateral_appliance` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `type` varchar(50) default NULL,
  `brand` varchar(50) default NULL,
  `dtacquired` date default NULL,
  `modelno` varchar(50) default NULL,
  `serialno` varchar(50) default NULL,
  `marketvalue` decimal(10,2) default '0.00',
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_parentid` (`parentid`),
  CONSTRAINT `fk_loanappappliance_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_collateral_appliance`
--

LOCK TABLES `loanapp_collateral_appliance` WRITE;
/*!40000 ALTER TABLE `loanapp_collateral_appliance` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_collateral_appliance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_collateral_other`
--

DROP TABLE IF EXISTS `loanapp_collateral_other`;
CREATE TABLE `loanapp_collateral_other` (
  `objid` varchar(40) NOT NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  CONSTRAINT `fk_loanappcollateral_objid_objid` FOREIGN KEY (`objid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_collateral_other`
--

LOCK TABLES `loanapp_collateral_other` WRITE;
/*!40000 ALTER TABLE `loanapp_collateral_other` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_collateral_other` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_collateral_property`
--

DROP TABLE IF EXISTS `loanapp_collateral_property`;
CREATE TABLE `loanapp_collateral_property` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `classification` varchar(32) default NULL,
  `location` varchar(250) default NULL,
  `areauom` varchar(25) default NULL,
  `areavalue` decimal(10,2) default '0.00',
  `zonalvalue` decimal(10,2) default '0.00',
  `dtacquired` date default NULL,
  `acquiredfrom` varchar(150) default NULL,
  `modeofacquisition` varchar(25) default NULL,
  `registeredname` varchar(150) default NULL,
  `marketvalue` decimal(10,2) default '0.00',
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `fk_loanappproperty_parentid_objid` (`parentid`),
  CONSTRAINT `fk_loanappproperty_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_collateral_property`
--

LOCK TABLES `loanapp_collateral_property` WRITE;
/*!40000 ALTER TABLE `loanapp_collateral_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_collateral_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_collateral_vehicle`
--

DROP TABLE IF EXISTS `loanapp_collateral_vehicle`;
CREATE TABLE `loanapp_collateral_vehicle` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `kind` varchar(25) default NULL,
  `make` varchar(50) default NULL,
  `model` varchar(50) default NULL,
  `bodytype` varchar(50) default NULL,
  `usetype` varchar(25) default NULL,
  `dtacquired` date default NULL,
  `acquiredfrom` varchar(150) default NULL,
  `registeredname` varchar(150) default NULL,
  `chassisno` varchar(32) default NULL,
  `plateno` varchar(25) default NULL,
  `engineno` varchar(32) default NULL,
  `marketvalue` decimal(10,2) default '0.00',
  `remarks` text,
  `orcr_crno` varchar(32) default NULL,
  `orcr_fuel` varchar(32) default NULL,
  `orcr_denomination` varchar(50) default NULL,
  `orcr_seriesno` varchar(32) default NULL,
  `orcr_pistondisplacement` varchar(50) default NULL,
  `orcr_noofcylinders` smallint(6) default NULL,
  `orcr_netwt` varchar(25) default NULL,
  `orcr_grosswt` varchar(25) default NULL,
  `orcr_netcapacity` smallint(6) default NULL,
  `orcr_shippingwt` varchar(25) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `fk_loanappvehicle_parentid_objid` (`parentid`),
  KEY `ix_orcr_crno` (`orcr_crno`),
  KEY `ix_plateno` (`plateno`),
  CONSTRAINT `fk_loanappvehicle_parentid_objid` FOREIGN KEY (`parentid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_collateral_vehicle`
--

LOCK TABLES `loanapp_collateral_vehicle` WRITE;
/*!40000 ALTER TABLE `loanapp_collateral_vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_collateral_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_log`
--

DROP TABLE IF EXISTS `loanapp_log`;
CREATE TABLE `loanapp_log` (
  `objid` varchar(40) NOT NULL,
  `appid` varchar(40) default NULL,
  `dtposted` datetime default NULL,
  `postedby` varchar(50) default NULL,
  `remarks` varchar(250) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtpostedby` (`dtposted`,`postedby`),
  KEY `ix_appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_log`
--

LOCK TABLES `loanapp_log` WRITE;
/*!40000 ALTER TABLE `loanapp_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_new`
--

DROP TABLE IF EXISTS `loanapp_new`;
CREATE TABLE `loanapp_new` (
  `objid` varchar(40) NOT NULL,
  `version` smallint(6) default '1',
  `loanno` varchar(16) default NULL,
  `loanamount` decimal(10,2) default '0.00',
  `clienttype` varchar(12) default NULL COMMENT 'WALK-IN,MARKETED',
  `marketedby` varchar(100) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_loanno_version` (`loanno`,`version`),
  CONSTRAINT `fk_loanappnew_objid_objid` FOREIGN KEY (`objid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_new`
--

LOCK TABLES `loanapp_new` WRITE;
/*!40000 ALTER TABLE `loanapp_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_otherlending`
--

DROP TABLE IF EXISTS `loanapp_otherlending`;
CREATE TABLE `loanapp_otherlending` (
  `objid` varchar(40) NOT NULL,
  `parentid` varchar(40) default NULL,
  `name` varchar(100) default NULL,
  `address` varchar(250) default NULL,
  `kind` varchar(50) default NULL,
  `amount` decimal(10,2) default '0.00',
  `dtgranted` date default NULL,
  `dtmatured` date default NULL,
  `term` smallint(6) default NULL,
  `interest` decimal(5,2) default '0.00',
  `paymentmode` varchar(25) default NULL,
  `paymentamount` decimal(10,2) default '0.00',
  `collateral` text,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `fk_loanapp_otherlending_parent_objid` (`parentid`),
  CONSTRAINT `fk_loanapp_otherlending_parent_objid` FOREIGN KEY (`parentid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_otherlending`
--

LOCK TABLES `loanapp_otherlending` WRITE;
/*!40000 ALTER TABLE `loanapp_otherlending` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_otherlending` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_recommendation`
--

DROP TABLE IF EXISTS `loanapp_recommendation`;
CREATE TABLE `loanapp_recommendation` (
  `objid` varchar(40) NOT NULL,
  `ciremarks` text,
  `crecomremarks` text,
  `marketeramount` decimal(10,2) default '0.00',
  `ciamount` decimal(10,2) default '0.00',
  `fcaamount` decimal(10,2) default '0.00',
  `caoamount` decimal(10,2) default '0.00',
  `bcohamount` decimal(10,2) default '0.00',
  PRIMARY KEY  (`objid`),
  CONSTRAINT `fk_loanapprecommendation_objid_objid` FOREIGN KEY (`objid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_recommendation`
--

LOCK TABLES `loanapp_recommendation` WRITE;
/*!40000 ALTER TABLE `loanapp_recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_renew`
--

DROP TABLE IF EXISTS `loanapp_renew`;
CREATE TABLE `loanapp_renew` (
  `objid` varchar(40) NOT NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `prevloanid` varchar(40) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_dtcreatedby` (`dtcreated`,`createdby`),
  KEY `ix_prevloanid` (`prevloanid`),
  CONSTRAINT `fk_loanapprenew_objid_objid` FOREIGN KEY (`objid`) REFERENCES `loanapp_new` (`objid`),
  CONSTRAINT `fk_loanapprenew_prevloanid_objid` FOREIGN KEY (`prevloanid`) REFERENCES `loanapp` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_renew`
--

LOCK TABLES `loanapp_renew` WRITE;
/*!40000 ALTER TABLE `loanapp_renew` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_renew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_search`
--

DROP TABLE IF EXISTS `loanapp_search`;
CREATE TABLE `loanapp_search` (
  `objid` varchar(40) NOT NULL,
  `state` varchar(25) default NULL,
  `appno` varchar(32) default NULL,
  `branchcode` varchar(10) default NULL,
  `branchname` varchar(60) default NULL,
  `routecode` varchar(32) default NULL,
  `fullborrowername` text,
  `loanamount` decimal(10,2) default '0.00',
  `dtposted` datetime default NULL,
  `postedby` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_appno` (`appno`),
  KEY `ix_dtpostedby` (`dtposted`,`postedby`),
  KEY `ix_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_search`
--

LOCK TABLES `loanapp_search` WRITE;
/*!40000 ALTER TABLE `loanapp_search` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanapp_search_index`
--

DROP TABLE IF EXISTS `loanapp_search_index`;
CREATE TABLE `loanapp_search_index` (
  `objid` varchar(40) NOT NULL,
  `appid` varchar(40) default NULL,
  `searchtext` varchar(150) default NULL,
  `borrowerid` varchar(40) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `ix_appid` (`appid`),
  KEY `ix_searchtext` (`searchtext`),
  KEY `ix_borrowerid` (`borrowerid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loanapp_search_index`
--

LOCK TABLES `loanapp_search_index` WRITE;
/*!40000 ALTER TABLE `loanapp_search_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapp_search_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `objid` varchar(50) NOT NULL,
  `docstate` varchar(25) NOT NULL,
  `rulename` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `packagename` varchar(100) NOT NULL,
  `author` varchar(50) NOT NULL,
  `salience` int(11) NOT NULL,
  `agendagroup` varchar(25) NOT NULL,
  `ruleset` varchar(50) default NULL,
  `effectivefrom` date default NULL,
  `effectiveto` date default NULL,
  `conditions` text,
  `actions` text,
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rule`
--

LOCK TABLES `rule` WRITE;
/*!40000 ALTER TABLE `rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule_package`
--

DROP TABLE IF EXISTS `rule_package`;
CREATE TABLE `rule_package` (
  `ruleset` varchar(50) NOT NULL,
  `rulegroup` varchar(50) NOT NULL,
  `packagename` varchar(255) NOT NULL,
  `orderindex` int(11) default NULL,
  `type` varchar(10) default NULL,
  `content` text,
  `lastmodified` datetime default NULL,
  PRIMARY KEY  (`ruleset`,`rulegroup`,`packagename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rule_package`
--

LOCK TABLES `rule_package` WRITE;
/*!40000 ALTER TABLE `rule_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `rule_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule_sets`
--

DROP TABLE IF EXISTS `rule_sets`;
CREATE TABLE `rule_sets` (
  `name` varchar(50) NOT NULL default '',
  `rulegroup` varchar(50) NOT NULL default '',
  `title` varchar(50) default NULL,
  `domain` varchar(50) default NULL,
  `role` varchar(50) default NULL,
  `permission` varchar(50) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rule_sets`
--

LOCK TABLES `rule_sets` WRITE;
/*!40000 ALTER TABLE `rule_sets` DISABLE KEYS */;
/*!40000 ALTER TABLE `rule_sets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rulegroup`
--

DROP TABLE IF EXISTS `rulegroup`;
CREATE TABLE `rulegroup` (
  `objid` varchar(50) NOT NULL,
  `name` varchar(255) default NULL,
  `sortorder` int(11) NOT NULL,
  `ruleset` varchar(50) default NULL,
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rulegroup`
--

LOCK TABLES `rulegroup` WRITE;
/*!40000 ALTER TABLE `rulegroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `rulegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sourceofincome`
--

DROP TABLE IF EXISTS `sourceofincome`;
CREATE TABLE `sourceofincome` (
  `objid` varchar(40) NOT NULL,
  `refid` varchar(40) default NULL,
  `name` varchar(50) default NULL,
  `remarks` text,
  PRIMARY KEY  (`objid`),
  KEY `ix_refid` (`refid`),
  KEY `ix_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sourceofincome`
--

LOCK TABLES `sourceofincome` WRITE;
/*!40000 ALTER TABLE `sourceofincome` DISABLE KEYS */;
/*!40000 ALTER TABLE `sourceofincome` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dataset`
--

DROP TABLE IF EXISTS `sys_dataset`;
CREATE TABLE `sys_dataset` (
  `objid` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `title` varchar(255) default NULL,
  `input` mediumtext,
  `output` mediumtext,
  `statement` varchar(50) default NULL,
  `datasource` varchar(50) default NULL,
  `servicename` varchar(50) default NULL,
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_dataset`
--

LOCK TABLES `sys_dataset` WRITE;
/*!40000 ALTER TABLE `sys_dataset` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dataset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_fact`
--

DROP TABLE IF EXISTS `sys_fact`;
CREATE TABLE `sys_fact` (
  `name` varchar(50) NOT NULL,
  `classname` varchar(50) default NULL,
  `description` varchar(250) default NULL,
  `ruleset` varchar(50) default NULL,
  `rulegroup` varchar(50) default NULL,
  PRIMARY KEY  (`name`),
  KEY `ix_ruleset` (`ruleset`),
  KEY `ix_rulegroup` (`rulegroup`),
  CONSTRAINT `fk_factrulegroup_rulegroupname` FOREIGN KEY (`rulegroup`) REFERENCES `sys_rulegroup` (`name`),
  CONSTRAINT `fk_factruleset_rulesetname` FOREIGN KEY (`ruleset`) REFERENCES `sys_ruleset` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_fact`
--

LOCK TABLES `sys_fact` WRITE;
/*!40000 ALTER TABLE `sys_fact` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_fact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_fact_field`
--

DROP TABLE IF EXISTS `sys_fact_field`;
CREATE TABLE `sys_fact_field` (
  `name` varchar(50) NOT NULL,
  `caption` varchar(50) default NULL,
  `handler` varchar(50) default NULL,
  `valuetype` varchar(15) default NULL,
  `required` smallint(6) default '0',
  `usevar` smallint(6) default '0',
  `info` text,
  `operator` varchar(50) default NULL,
  `fact` varchar(50) default NULL,
  PRIMARY KEY  (`name`),
  KEY `fk_factfieldoperator_operatorname` (`operator`),
  KEY `fk_factfieldfact_factname` (`fact`),
  CONSTRAINT `fk_factfieldfact_factname` FOREIGN KEY (`fact`) REFERENCES `sys_fact` (`name`),
  CONSTRAINT `fk_factfieldoperator_operatorname` FOREIGN KEY (`operator`) REFERENCES `sys_operator` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_fact_field`
--

LOCK TABLES `sys_fact_field` WRITE;
/*!40000 ALTER TABLE `sys_fact_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_fact_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operator`
--

DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator` (
  `name` varchar(50) NOT NULL,
  `description` varchar(160) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_operator`
--

LOCK TABLES `sys_operator` WRITE;
/*!40000 ALTER TABLE `sys_operator` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_operator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operator_item`
--

DROP TABLE IF EXISTS `sys_operator_item`;
CREATE TABLE `sys_operator_item` (
  `name` varchar(50) NOT NULL,
  `caption` varchar(160) default NULL,
  `symbol` varchar(50) default NULL,
  `sortorder` smallint(6) default '0',
  `operator` varchar(50) default NULL,
  PRIMARY KEY  (`name`),
  KEY `fk_operatoritem_operator_operatorname` (`operator`),
  CONSTRAINT `fk_operatoritem_operator_operatorname` FOREIGN KEY (`operator`) REFERENCES `sys_operator` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_operator_item`
--

LOCK TABLES `sys_operator_item` WRITE;
/*!40000 ALTER TABLE `sys_operator_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_operator_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_org`
--

DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `objid` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `orgclass` varchar(50) default NULL,
  `parentid` varchar(50) default NULL,
  `parentclass` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_org_name` (`name`,`parentid`),
  KEY `FK_sys_org_orgclass` (`orgclass`),
  KEY `FK_sys_org` (`parentclass`),
  CONSTRAINT `FK_sys_org` FOREIGN KEY (`parentclass`) REFERENCES `sys_orgclass` (`name`),
  CONSTRAINT `FK_sys_org_orgclass` FOREIGN KEY (`orgclass`) REFERENCES `sys_orgclass` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_org`
--

LOCK TABLES `sys_org` WRITE;
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` VALUES ('006','N.BACALSO BRANCH','BRANCH',NULL,NULL);
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_orgclass`
--

DROP TABLE IF EXISTS `sys_orgclass`;
CREATE TABLE `sys_orgclass` (
  `name` varchar(50) NOT NULL,
  `title` varchar(255) default NULL,
  `childnodes` varchar(255) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_orgclass`
--

LOCK TABLES `sys_orgclass` WRITE;
/*!40000 ALTER TABLE `sys_orgclass` DISABLE KEYS */;
INSERT INTO `sys_orgclass` VALUES ('BRANCH','BRANCH',NULL);
/*!40000 ALTER TABLE `sys_orgclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_report_admin`
--

DROP TABLE IF EXISTS `sys_report_admin`;
CREATE TABLE `sys_report_admin` (
  `objid` varchar(50) NOT NULL,
  `userid` varchar(50) default NULL,
  `reportfolderid` varchar(50) default NULL,
  `exclude` mediumtext,
  PRIMARY KEY  (`objid`),
  KEY `FK_sys_report_admin_folder` (`reportfolderid`),
  KEY `FK_sys_report_admin_user` (`userid`),
  CONSTRAINT `FK_sys_report_admin_folder` FOREIGN KEY (`reportfolderid`) REFERENCES `sys_report_folder` (`objid`),
  CONSTRAINT `FK_sys_report_admin_user` FOREIGN KEY (`userid`) REFERENCES `sys_user` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_report_admin`
--

LOCK TABLES `sys_report_admin` WRITE;
/*!40000 ALTER TABLE `sys_report_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_report_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_report_entry`
--

DROP TABLE IF EXISTS `sys_report_entry`;
CREATE TABLE `sys_report_entry` (
  `objid` varchar(50) default NULL,
  `reportfolderid` varchar(50) default NULL,
  `name` varchar(50) default NULL,
  `title` varchar(255) default NULL,
  `filetype` varchar(25) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  KEY `FK_sys_report_entry_folder` (`reportfolderid`),
  CONSTRAINT `FK_sys_report_entry_folder` FOREIGN KEY (`reportfolderid`) REFERENCES `sys_report_folder` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_report_entry`
--

LOCK TABLES `sys_report_entry` WRITE;
/*!40000 ALTER TABLE `sys_report_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_report_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_report_folder`
--

DROP TABLE IF EXISTS `sys_report_folder`;
CREATE TABLE `sys_report_folder` (
  `objid` varchar(50) NOT NULL,
  `name` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  `parentid` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_foldername` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_report_folder`
--

LOCK TABLES `sys_report_folder` WRITE;
/*!40000 ALTER TABLE `sys_report_folder` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_report_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_report_member`
--

DROP TABLE IF EXISTS `sys_report_member`;
CREATE TABLE `sys_report_member` (
  `objid` varchar(50) default NULL,
  `reportfolderid` varchar(50) default NULL,
  `userid` varchar(50) default NULL,
  `usergroupid` varchar(50) default NULL,
  `exclude` mediumtext,
  KEY `FK_sys_report_member_folder` (`reportfolderid`),
  KEY `FK_sys_report_member_user` (`userid`),
  KEY `FK_sys_report_member_usergroup` (`usergroupid`),
  CONSTRAINT `FK_sys_report_member_folder` FOREIGN KEY (`reportfolderid`) REFERENCES `sys_report_folder` (`objid`),
  CONSTRAINT `FK_sys_report_member_user` FOREIGN KEY (`userid`) REFERENCES `sys_user` (`objid`),
  CONSTRAINT `FK_sys_report_member_usergroup` FOREIGN KEY (`usergroupid`) REFERENCES `sys_usergroup` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_report_member`
--

LOCK TABLES `sys_report_member` WRITE;
/*!40000 ALTER TABLE `sys_report_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_report_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_rulegroup`
--

DROP TABLE IF EXISTS `sys_rulegroup`;
CREATE TABLE `sys_rulegroup` (
  `name` varchar(50) NOT NULL,
  `title` varchar(160) default NULL,
  `ruleset` varchar(50) default NULL,
  `sortorder` int(11) default '0',
  PRIMARY KEY  (`name`),
  KEY `fk_sysrulegroup_ruleset_sysruleset_name` (`ruleset`),
  CONSTRAINT `fk_sysrulegroup_ruleset_sysruleset_name` FOREIGN KEY (`ruleset`) REFERENCES `sys_ruleset` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_rulegroup`
--

LOCK TABLES `sys_rulegroup` WRITE;
/*!40000 ALTER TABLE `sys_rulegroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_rulegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_ruleset`
--

DROP TABLE IF EXISTS `sys_ruleset`;
CREATE TABLE `sys_ruleset` (
  `name` varchar(50) NOT NULL,
  `title` varchar(160) default NULL,
  `packagename` varchar(50) default NULL,
  `domain` varchar(50) default NULL,
  `role` varchar(50) default NULL,
  `permission` varchar(50) default NULL,
  PRIMARY KEY  (`name`),
  KEY `ix_domain` (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_ruleset`
--

LOCK TABLES `sys_ruleset` WRITE;
/*!40000 ALTER TABLE `sys_ruleset` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_ruleset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_script`
--

DROP TABLE IF EXISTS `sys_script`;
CREATE TABLE `sys_script` (
  `name` varchar(50) NOT NULL,
  `title` varbinary(255) default NULL,
  `content` mediumtext,
  `category` varchar(20) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_script`
--

LOCK TABLES `sys_script` WRITE;
/*!40000 ALTER TABLE `sys_script` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_script` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_securitygroup`
--

DROP TABLE IF EXISTS `sys_securitygroup`;
CREATE TABLE `sys_securitygroup` (
  `objid` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `usergroupid` varchar(50) default NULL,
  `exclude` varchar(255) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_securitygroup_name` (`name`),
  KEY `FK_sys_securitygroup_usergroup` (`usergroupid`),
  CONSTRAINT `FK_sys_securitygroup_usergroup` FOREIGN KEY (`usergroupid`) REFERENCES `sys_usergroup` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_securitygroup`
--

LOCK TABLES `sys_securitygroup` WRITE;
/*!40000 ALTER TABLE `sys_securitygroup` DISABLE KEYS */;
INSERT INTO `sys_securitygroup` VALUES ('LOAN_ACCT_OFFICER_DEFAULT','ACCOUNTING OFFICER','LOAN_ACCT_OFFICER',NULL),('LOAN_APPROVER_DEFAULT','LOAN APPROVER','LOAN_APPROVER',NULL),('LOAN_CAO_OFFICER_DEFAULT','CAO OFFICER','LOAN_CAO_OFFICER',NULL),('LOAN_CI_OFFICER_DEFAULT','CREDIT INVESTIGATOR','LOAN_CI_OFFICER',NULL),('LOAN_CRECOM_OFFICER_DEFAULT','CRECOM OFFICER','LOAN_CRECOM_OFFICER',NULL),('LOAN_DATAMGMT_AUTHOR_DEFAULT','DATA MANAGEMENT AUTHOR','LOAN_DATAMGMT_AUTHOR',NULL),('LOAN_FIELD_COLLECTOR_DEFAULT','FIELD COLLECTOR','LOAN_FIELD_COLLECTOR',NULL);
/*!40000 ALTER TABLE `sys_securitygroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_sequence`
--

DROP TABLE IF EXISTS `sys_sequence`;
CREATE TABLE `sys_sequence` (
  `objid` varchar(100) NOT NULL,
  `nextseries` int(11) NOT NULL,
  PRIMARY KEY  (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_sequence`
--

LOCK TABLES `sys_sequence` WRITE;
/*!40000 ALTER TABLE `sys_sequence` DISABLE KEYS */;
INSERT INTO `sys_sequence` VALUES ('customer006',1),('ENTITY',1),('loan',1),('loanbill',1);
/*!40000 ALTER TABLE `sys_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_session`
--

DROP TABLE IF EXISTS `sys_session`;
CREATE TABLE `sys_session` (
  `sessionid` varchar(50) NOT NULL,
  `userid` varchar(50) default NULL,
  `username` varchar(50) default NULL,
  `clienttype` varchar(12) default NULL,
  `accesstime` datetime default NULL,
  `accessexpiry` datetime default NULL,
  `timein` datetime default NULL,
  PRIMARY KEY  (`sessionid`),
  KEY `ix_userid` (`userid`),
  KEY `ix_username` (`username`),
  KEY `ix_timein` (`timein`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_session`
--

LOCK TABLES `sys_session` WRITE;
/*!40000 ALTER TABLE `sys_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_session_log`
--

DROP TABLE IF EXISTS `sys_session_log`;
CREATE TABLE `sys_session_log` (
  `sessionid` varchar(50) NOT NULL,
  `userid` varchar(50) default NULL,
  `username` varchar(50) default NULL,
  `clienttype` varchar(12) default NULL,
  `accesstime` datetime default NULL,
  `accessexpiry` datetime default NULL,
  `timein` datetime default NULL,
  `timeout` datetime default NULL,
  `state` varchar(10) default NULL,
  PRIMARY KEY  (`sessionid`),
  KEY `ix_userid` (`userid`),
  KEY `ix_username` (`username`),
  KEY `ix_timein` (`timein`),
  KEY `ix_timeout` (`timeout`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_session_log`
--

LOCK TABLES `sys_session_log` WRITE;
/*!40000 ALTER TABLE `sys_session_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_session_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_terminal`
--

DROP TABLE IF EXISTS `sys_terminal`;
CREATE TABLE `sys_terminal` (
  `terminalid` varchar(50) NOT NULL,
  `parentid` varchar(50) default NULL,
  `parentcode` varchar(50) default NULL,
  `parenttype` varchar(50) default NULL,
  `macaddress` varchar(50) default NULL,
  `dtregistered` datetime default NULL,
  `registeredby` varchar(50) default NULL,
  `info` text,
  `state` int(11) default NULL,
  PRIMARY KEY  (`terminalid`),
  UNIQUE KEY `macaddress_unique` (`macaddress`),
  KEY `FK_terminal` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_terminal`
--

LOCK TABLES `sys_terminal` WRITE;
/*!40000 ALTER TABLE `sys_terminal` DISABLE KEYS */;
INSERT INTO `sys_terminal` VALUES ('ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_terminal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `objid` varchar(50) NOT NULL,
  `state` varchar(15) default NULL,
  `dtcreated` datetime default NULL,
  `createdby` varchar(50) default NULL,
  `username` varchar(50) default NULL,
  `pwd` varchar(50) default NULL,
  `firstname` varchar(50) default NULL,
  `lastname` varchar(50) default NULL,
  `middlename` varchar(50) default NULL,
  `name` varchar(50) default NULL,
  `jobtitle` varchar(50) default NULL,
  `pwdlogincount` smallint(6) default NULL,
  `pwdexpirydate` datetime default NULL,
  `usedpwds` text,
  `lockid` varchar(32) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_username` (`username`),
  KEY `ix_lastname_firstname` (`lastname`,`firstname`),
  KEY `ix_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_usergroup`
--

DROP TABLE IF EXISTS `sys_usergroup`;
CREATE TABLE `sys_usergroup` (
  `objid` varchar(50) NOT NULL,
  `title` varchar(255) default NULL,
  `domain` varchar(25) default NULL,
  `role` varchar(50) default NULL,
  `userclass` varchar(25) default NULL,
  `orgclass` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `uix_domainrole` (`domain`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_usergroup`
--

LOCK TABLES `sys_usergroup` WRITE;
/*!40000 ALTER TABLE `sys_usergroup` DISABLE KEYS */;
INSERT INTO `sys_usergroup` VALUES ('ENTITY_ENCODER','ENTITY_ENCODER','ENTITY','ENCODER','usergroup',NULL),('LOAN_ACCT_OFFICER','ACCOUNTING OFFICER','LOAN','ACCT_OFFICER','usergroup',NULL),('LOAN_APPROVER','APPROVER','LOAN','APPROVER','usergroup',NULL),('LOAN_CAO_OFFICER','CAO OFFICER','LOAN','CAO_OFFICER','usergroup',NULL),('LOAN_CI_OFFICER','CREDIT INSPECTOR','LOAN','CI_OFFICER','usergroup',NULL),('LOAN_CRECOM_OFFICER','CRECOM OFFICER','LOAN','CRECOM_OFFICER','usergroup',NULL),('LOAN_DATAMGMT_AUTHOR','LOAN DATA MANAGEMENT','DATAMGMT','DATAMGMT_AUTHOR','usergroup',NULL),('LOAN_FIELD_COLLECTOR','FIELD COLLECTOR','LOAN','FIELD_COLLECTOR','usergroup',NULL),('LOAN_INSPECTOR','CREDIT INSPECTOR','LOAN','INSPECTOR','usergroup',NULL);
/*!40000 ALTER TABLE `sys_usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_usergroup_admin`
--

DROP TABLE IF EXISTS `sys_usergroup_admin`;
CREATE TABLE `sys_usergroup_admin` (
  `objid` varchar(50) NOT NULL,
  `usergroupid` varchar(50) default NULL,
  `user_objid` varchar(50) default NULL,
  `user_username` varchar(50) default NULL,
  `user_firstname` varchar(50) NOT NULL,
  `user_lastname` varchar(50) default NULL,
  `exclude` varchar(255) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_usergroup_admin_user` (`usergroupid`,`user_objid`),
  KEY `FK_sys_usergroup_admin` (`user_objid`),
  CONSTRAINT `FK_sys_usergroup_admin` FOREIGN KEY (`user_objid`) REFERENCES `sys_user` (`objid`),
  CONSTRAINT `FK_sys_usergroup_admin_usergroup` FOREIGN KEY (`usergroupid`) REFERENCES `sys_usergroup` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_usergroup_admin`
--

LOCK TABLES `sys_usergroup_admin` WRITE;
/*!40000 ALTER TABLE `sys_usergroup_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_usergroup_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_usergroup_member`
--

DROP TABLE IF EXISTS `sys_usergroup_member`;
CREATE TABLE `sys_usergroup_member` (
  `objid` varchar(50) NOT NULL,
  `state` varchar(10) default NULL,
  `usergroupid` varchar(50) default NULL,
  `user_objid` varchar(50) NOT NULL,
  `user_username` varchar(50) default NULL,
  `user_firstname` varchar(50) NOT NULL,
  `user_lastname` varchar(50) NOT NULL,
  `org_objid` varchar(50) default NULL,
  `org_name` varchar(50) default NULL,
  `org_orgclass` varchar(50) default NULL,
  `securitygroupid` varchar(50) default NULL,
  `exclude` varchar(255) default NULL,
  `displayname` varchar(50) default NULL,
  `jobtitle` varchar(50) default NULL,
  `usertxncode` varchar(50) default NULL,
  PRIMARY KEY  (`objid`),
  UNIQUE KEY `idx_usergroup_user_org` (`usergroupid`,`user_objid`,`org_objid`),
  UNIQUE KEY `uix_txncode` (`usertxncode`),
  KEY `FK_sys_usergroup_member_securitygorup` (`securitygroupid`),
  KEY `FK_sys_usergroup_member` (`user_objid`),
  KEY `FK_sys_usergroup_member_org` (`org_objid`),
  KEY `ix_user_lastname_firstname` (`user_lastname`,`user_firstname`),
  KEY `ix_username` (`user_username`),
  CONSTRAINT `FK_sys_usergroup_member` FOREIGN KEY (`user_objid`) REFERENCES `sys_user` (`objid`),
  CONSTRAINT `FK_sys_usergroup_member_org` FOREIGN KEY (`org_objid`) REFERENCES `sys_org` (`objid`),
  CONSTRAINT `FK_sys_usergroup_member_securitygorup` FOREIGN KEY (`securitygroupid`) REFERENCES `sys_securitygroup` (`objid`),
  CONSTRAINT `FK_sys_usergroup_member_usergroup` FOREIGN KEY (`usergroupid`) REFERENCES `sys_usergroup` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_usergroup_member`
--

LOCK TABLES `sys_usergroup_member` WRITE;
/*!40000 ALTER TABLE `sys_usergroup_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_usergroup_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_usergroup_permission`
--

DROP TABLE IF EXISTS `sys_usergroup_permission`;
CREATE TABLE `sys_usergroup_permission` (
  `objid` varchar(50) NOT NULL,
  `usergroupid` varchar(50) default NULL,
  `object` varchar(25) default NULL,
  `permission` varchar(25) default NULL,
  PRIMARY KEY  (`objid`),
  KEY `FK_sys_usergroup_permission_usergroup` (`usergroupid`),
  CONSTRAINT `FK_sys_usergroup_permission_usergroup` FOREIGN KEY (`usergroupid`) REFERENCES `sys_usergroup` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_usergroup_permission`
--

LOCK TABLES `sys_usergroup_permission` WRITE;
/*!40000 ALTER TABLE `sys_usergroup_permission` DISABLE KEYS */;
INSERT INTO `sys_usergroup_permission` VALUES ('DATAMGMT_HOLIDAY_EVENT_01','LOAN_DATAMGMT_AUTHOR','holidayevent','create'),('DATAMGMT_HOLIDAY_EVENT_02','LOAN_DATAMGMT_AUTHOR','holidayevent','read'),('DATAMGMT_HOLIDAY_EVENT_03','LOAN_DATAMGMT_AUTHOR','holidayevent','edit'),('DATAMGMT_HOLIDAY_EVENT_04','LOAN_DATAMGMT_AUTHOR','holidayevent','delete'),('DATAMGMT_PRODTYPE_01','LOAN_DATAMGMT_AUTHOR','producttype','create'),('DATAMGMT_PRODTYPE_02','LOAN_DATAMGMT_AUTHOR','producttype','read'),('DATAMGMT_PRODTYPE_03','LOAN_DATAMGMT_AUTHOR','producttype','edit'),('DATAMGMT_PRODTYPE_04','LOAN_DATAMGMT_AUTHOR','producttype','delete'),('DATAMGMT_ROUTE_01','LOAN_DATAMGMT_AUTHOR','route','create'),('DATAMGMT_ROUTE_02','LOAN_DATAMGMT_AUTHOR','route','read'),('DATAMGMT_ROUTE_03','LOAN_DATAMGMT_AUTHOR','route','edit'),('DATAMGMT_ROUTE_04','LOAN_DATAMGMT_AUTHOR','route','delete'),('ENTITY-ENCODER-EM','ENTITY_ENCODER','entity','manage'),('ENTITY-ENCODER-ER','ENTITY_ENCODER','entity','reconcile'),('ENTITY-ENCODER-IC','ENTITY_ENCODER','individual','create'),('ENTITY-ENCODER-IE','ENTITY_ENCODER','individual','edit'),('ENTITY-ENCODER-IEO','ENTITY_ENCODER','individual','enrollonline'),('ENTITY-ENCODER-IR','ENTITY_ENCODER','individual','read'),('ENTITY-ENCODER-JC','ENTITY_ENCODER','juridical','create'),('ENTITY-ENCODER-JD','ENTITY_ENCODER','juridical','delete'),('ENTITY-ENCODER-JE','ENTITY_ENCODER','juridical','edit'),('ENTITY-ENCODER-JR','ENTITY_ENCODER','juridical','read'),('ENTITY-ENCODER-MC','ENTITY_ENCODER','multiple','create'),('ENTITY-ENCODER-MD','ENTITY_ENCODER','multiple','delete'),('ENTITY-ENCODER-ME','ENTITY_ENCODER','multiple','edit'),('ENTITY-ENCODER-MR','ENTITY_ENCODER','multiple','read'),('ENTITY_ENCODER-ID','ENTITY_ENCODER','individual','delete'),('LOAN_CAO_APP_C','LOAN_CAO_OFFICER','application','create'),('LOAN_CAO_APP_D','LOAN_CAO_OFFICER','application','delete'),('LOAN_CAO_APP_E','LOAN_CAO_OFFICER','application','edit'),('LOAN_CAO_APP_R','LOAN_CAO_OFFICER','application','open');
/*!40000 ALTER TABLE `sys_usergroup_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_var`
--

DROP TABLE IF EXISTS `sys_var`;
CREATE TABLE `sys_var` (
  `name` varchar(50) NOT NULL,
  `value` text,
  `description` varchar(255) default NULL,
  `datatype` varchar(15) default NULL,
  `category` varchar(50) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sys_var`
--

LOCK TABLES `sys_var` WRITE;
/*!40000 ALTER TABLE `sys_var` DISABLE KEYS */;
INSERT INTO `sys_var` VALUES ('branch_address','N.Bacalso Avenue Cebu City	','Branch Address',NULL,'BRANCH'),('branch_code','006','Branch code',NULL,'BRANCH'),('branch_name','N.Bacalso Branch','Branch name',NULL,'BRANCH'),('invalid_login_access_date_interval','1m','number of hours/days access date is moved when failed_login_max_retries is reached( d=days, h=hours)',NULL,'SYSTEM'),('invalid_login_action','1','0 - suspend 1-move access to a later date',NULL,'SYSTEM'),('invalid_login_max_retries','5','No. of times a user can retry login before disabling account',NULL,'SYSTEM'),('pwd_change_cycle','3','No. of times the user cannot use a repeating password',NULL,'SYSTEM'),('pwd_change_date_interval','1M','No. of days/months before a user is required to change their password (d=days, M=months)',NULL,'SYSTEM'),('pwd_change_login_count','0','No. of times a user has successfully logged in before changing their password. (0=no limit) ',NULL,'SYSTEM'),('receipt_item_printout_count','10',NULL,NULL,'SYSTEM'),('sa_pwd','560145c20d7508ecb59223999c4654dd',NULL,NULL,'SYSTEM'),('server_timezone','Asia/Shanghai','this must be matched with default timezone of server',NULL,'SYSTEM'),('session_timeout_interval','1d ','expiry dates of inactive session (d=days, h=hours)',NULL,'SYSTEM'),('system_pwd','!12345','system password',NULL,'SYSTEM');
/*!40000 ALTER TABLE `sys_var` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-02  2:27:30
