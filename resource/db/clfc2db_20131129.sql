-- MySQL dump 10.10
--
-- Host: localhost    Database: clfc2
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
INSERT INTO `batch_payment` VALUES ('LB-21391adb:14245325a71:-7da4','APPROVED',NULL,NULL,'2013-11-11','2013-11-11 12:46:37','R1',1,'11105.50'),('LB-4dbda590:142a1fc536b:-7ff6','APPROVED',NULL,NULL,'2013-11-29','2013-11-29 12:09:02','R2046788163',1,'200.00');
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
INSERT INTO `batch_payment_cashbreakdown` VALUES ('BPCB-3f233e29:1424578a7c2:-7ff6','LB-21391adb:14245325a71:-7da4','Coins','1.00','5.50','5.50'),('BPCB-3f233e29:1424578a7c2:-7ff7','LB-21391adb:14245325a71:-7da4','10.00','10.00','0.00','0.00'),('BPCB-3f233e29:1424578a7c2:-7ff8','LB-21391adb:14245325a71:-7da4','20.00','20.00','5.00','100.00'),('BPCB-3f233e29:1424578a7c2:-7ff9','LB-21391adb:14245325a71:-7da4','50.00','50.00','0.00','0.00'),('BPCB-3f233e29:1424578a7c2:-7ffa','LB-21391adb:14245325a71:-7da4','100.00','100.00','0.00','0.00'),('BPCB-3f233e29:1424578a7c2:-7ffb','LB-21391adb:14245325a71:-7da4','200.00','200.00','5.00','1000.00'),('BPCB-3f233e29:1424578a7c2:-7ffc','LB-21391adb:14245325a71:-7da4','500.00','500.00','8.00','4000.00'),('BPCB-3f233e29:1424578a7c2:-7ffd','LB-21391adb:14245325a71:-7da4','1000.00','1000.00','6.00','6000.00'),('BPCB-472766aa:142a34fd471:-7ff9','LB-4dbda590:142a1fc536b:-7ff6','Coins','1.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-7ffa','LB-4dbda590:142a1fc536b:-7ff6','10.00','10.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-7ffb','LB-4dbda590:142a1fc536b:-7ff6','20.00','20.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-7ffc','LB-4dbda590:142a1fc536b:-7ff6','50.00','50.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-7ffd','LB-4dbda590:142a1fc536b:-7ff6','100.00','100.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-7ffe','LB-4dbda590:142a1fc536b:-7ff6','200.00','200.00','1.00','200.00'),('BPCB-472766aa:142a34fd471:-7fff','LB-4dbda590:142a1fc536b:-7ff6','500.00','500.00','0.00','0.00'),('BPCB-472766aa:142a34fd471:-8000','LB-4dbda590:142a1fc536b:-7ff6','1000.00','1000.00','0.00','0.00');
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
INSERT INTO `batch_payment_detail` VALUES ('LLBD-3f233e29:1424578a7c2:-7fff','LB-21391adb:14245325a71:-7da4','B00000031','LOAN3c8f5e6:14245464dd3:-8000','over','11105.50'),('LLBD7d328cd1:142a1c8b233:-7ffa','LB-4dbda590:142a1fc536b:-7ff6','B00000064','LOAN6cd09d4c:1429cf5ff70:-8000','schedule','200.00');
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
INSERT INTO `borrower` VALUES ('CUST-2f03f62e:141ed56ed62:-7f93','PERNITO','CARL',NULL,'CEBU CITY','M',NULL,'SINGLE','FIL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('CUST-7ffc746c:1422665be13:-7f72','DEVANCE','JOE',NULL,'CEBU CITY','M',NULL,'SINGLE','FIL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('CUST5ff69d5a:141e9710f6b:-7f84','PERNITO','CARL LOUIE',NULL,'CEBU CITY','M',NULL,'SINGLE','FIL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `customer` VALUES ('CUST-2f03f62e:141ed56ed62:-7f93','ACTIVE','ONLINE','2013-10-25 16:20:45','sa','0060000002','006','PERNITO, CARL','PERNITO','CARL',NULL,'M',NULL,'FIL','SINGLE','CEBU CITY',NULL,'2013-10-25 16:20:45','sa',NULL),('CUST-7ffc746c:1422665be13:-7f72','ACTIVE','ONLINE','2013-11-05 14:30:35','sa','0060000003','006','DEVANCE, JOE','DEVANCE','JOE',NULL,'M',NULL,'FIL','SINGLE','CEBU CITY',NULL,'2013-11-05 14:30:35','sa',NULL),('CUST5ff69d5a:141e9710f6b:-7f84','ACTIVE','ONLINE','2013-10-24 16:54:21','sa','0060000001','006','PERNITO, CARL LOUIE','PERNITO','CARL LOUIE',NULL,'M',NULL,'FIL','SINGLE','CEBU CITY',NULL,'2013-10-24 16:54:21','sa',NULL);
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
INSERT INTO `entity` VALUES ('CUST-2f03f62e:141ed56ed62:-7f93','00002','PERNITO, CARL','CEBU CITY',NULL,'INDIVIDUAL',NULL,NULL,NULL),('CUST-7ffc746c:1422665be13:-7f72','00003','DEVANCE, JOE','CEBU CITY',NULL,'INDIVIDUAL',NULL,NULL,NULL),('CUST5ff69d5a:141e9710f6b:-7f84','00001','PERNITO, CARL LOUIE','CEBU CITY',NULL,'INDIVIDUAL',NULL,NULL,NULL);
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
INSERT INTO `entityindividual` VALUES ('CUST-2f03f62e:141ed56ed62:-7f93','PERNITO','CARL',NULL,NULL,NULL,'FIL','M','SINGLE',NULL,NULL,NULL,NULL,NULL),('CUST-7ffc746c:1422665be13:-7f72','DEVANCE','JOE',NULL,NULL,NULL,'FIL','M','SINGLE',NULL,NULL,NULL,NULL,NULL),('CUST5ff69d5a:141e9710f6b:-7f84','PERNITO','CARL LOUIE',NULL,NULL,NULL,'FIL','M','SINGLE',NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `loan_exemption` VALUES ('0002','APPROVED','LEDGER20d86b3:1429d388aa9:-7ff6','00001','2013-11-27','2013-11-27 00:00:00','LOUIE','FLOOD'),('LLBD7d328cd1:142a1c8b233:-7ffb','APPROVED','LEDGER20d86b3:1429d388aa9:-7ff6','LB-4dbda590:142a1fc536b:-7ff6','2013-11-29','2013-11-29 18:02:40','sa','Flood');
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
INSERT INTO `loan_ledger` VALUES ('LEDGER-21391adb:14245325a71:-7dce','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','LOAN3c8f5e6:14245464dd3:-8000','CLOSED','DAILY',1,120,'over','0.00','100.00','16.70','150.00','0.00','3.00','10000.00','0.00',NULL,'2013-11-08','2014-03-07','2013-11-11','2014-01-22'),('LEDGER20d86b3:1429d388aa9:-7ff6','CUST-7ffc746c:1422665be13:-7f72','DEVANCE, JOE','LOAN-3f233e29:1424578a7c2:-7ff5','OPEN','DAILY',1,120,'schedule','9752.70','100.00','16.70','0.00','0.00','3.00','10000.00','0.00',NULL,'2013-11-08','2014-03-07','2013-11-28','2013-11-29'),('LEDGER66fcd605:1429ce5c646:-7f9e','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','LOAN6cd09d4c:1429cf5ff70:-8000','OPEN','DAILY',2,120,'schedule','9753.10','100.00','16.70','0.00','0.00','3.00','10000.00','0.00',NULL,'2013-11-27','2014-03-26','2013-11-29','2013-11-30');
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
INSERT INTO `loan_ledger_billing` VALUES ('LB-21391adb:14245325a71:-7da4','COMPLETED','2013-11-11 12:44:36','sa','2013-11-11 12:44:36','USR-7eced478:141e3ef3147:-7fe7','USER1 USER1','2013-11-11'),('LB-4dbda590:142a1fc536b:-7ff6','COMPLETED','2013-11-29 11:52:46','sa','2013-11-29 11:57:17','USR-7eced478:141e3ef3147:-7fe7','USER1 USER1','2013-11-29'),('LB-56511e90:1429cc52042:-7ffb','DRAFT','2013-11-28 11:36:16','sa','2013-11-28 17:32:43','USR-7eced478:141e3ef3147:-7fe7','USER1 USER1','2013-11-28'),('LB6e0bfc74:142a35029f0:-7ef0','DRAFT','2013-11-29 18:05:01','sa','2013-11-29 18:08:21','USR-7eced478:141e3ef3147:-7fe7','USER1 USER1','2013-11-30');
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
INSERT INTO `loan_ledger_billing_detail` VALUES ('LLBD-3f233e29:1424578a7c2:-7fff','LB-21391adb:14245325a71:-7da4','LEDGER-21391adb:14245325a71:-7dce','R1','LOAN3c8f5e6:14245464dd3:-8000','PERNITO, CARL LOUIE','10000.00','LC00011','100.00','300.00','3.00','150.00','0.00','0.00','11105.50','B00000031','2013-11-11','2014-03-07',0,'over'),('LLBD-4ec1a963:142a3549ecb:-7fff','LB6e0bfc74:142a35029f0:-7ef0','LEDGER66fcd605:1429ce5c646:-7f9e','R2046788163','LOAN6cd09d4c:1429cf5ff70:-8000','PERNITO, CARL LOUIE','10000.00','LC-00014','100.00','100.00','0.00','0.00','0.00','0.00','11707.00','B00000068','2013-11-30','2014-03-26',0,'schedule'),('LLBD-4ec1a963:142a3549ecb:-8000','LB6e0bfc74:142a35029f0:-7ef0','LEDGER20d86b3:1429d388aa9:-7ff6','R2046788163','LOAN-3f233e29:1424578a7c2:-7ff5','DEVANCE, JOE','10000.00','LC00001','100.00','200.00','0.00','0.00','0.00','0.00','11706.60','B00000067','2013-11-30','2014-03-07',0,'schedule'),('LLBD-6b2c1ed8:1429e0da456:-7fff','LB-56511e90:1429cc52042:-7ffb','LEDGER66fcd605:1429ce5c646:-7f9e','R2046788163','LOAN6cd09d4c:1429cf5ff70:-8000','PERNITO, CARL LOUIE','10000.00','LC-00014','100.00','100.00','0.00','0.00','0.00','0.00','11904.00','B00000060','2013-11-28','2014-03-26',0,'schedule'),('LLBD-6b2c1ed8:1429e0da456:-8000','LB-56511e90:1429cc52042:-7ffb','LEDGER20d86b3:1429d388aa9:-7ff6','R2046788163','LOAN-3f233e29:1424578a7c2:-7ff5','DEVANCE, JOE','10000.00','LC00001','100.00','300.00','3.00','0.00','0.00','0.00','12003.60','B00000059','2013-11-28','2014-03-07',0,'schedule'),('LLBD7d328cd1:142a1c8b233:-7ffa','LB-4dbda590:142a1fc536b:-7ff6','LEDGER66fcd605:1429ce5c646:-7f9e','R2046788163','LOAN6cd09d4c:1429cf5ff70:-8000','PERNITO, CARL LOUIE','10000.00','LC-00014','100.00','200.00','3.00','0.00','0.00','0.00','11907.00','B00000064','2013-11-29','2014-03-26',0,'schedule'),('LLBD7d328cd1:142a1c8b233:-7ffb','LB-4dbda590:142a1fc536b:-7ff6','LEDGER20d86b3:1429d388aa9:-7ff6','R2046788163','LOAN-3f233e29:1424578a7c2:-7ff5','DEVANCE, JOE','10000.00','LC00001','100.00','100.00','0.00','0.00','0.00','0.00','11706.60','B00000063','2013-11-29','2014-03-07',0,'schedule');
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
INSERT INTO `loan_ledger_billing_lock` VALUES ('LB-4dbda590:142a1fc536b:-7ff6','R2046788163');
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
INSERT INTO `loan_ledger_billing_route` VALUES ('LB-21391adb:14245325a71:-7da4','R1'),('LB-21391adb:14245325a71:-7da4','R2046788163'),('LB-4dbda590:142a1fc536b:-7ff6','R1'),('LB-4dbda590:142a1fc536b:-7ff6','R2046788163'),('LB-56511e90:1429cc52042:-7ffb','R1'),('LB-56511e90:1429cc52042:-7ffb','R2046788163'),('LB6e0bfc74:142a35029f0:-7ef0','R1'),('LB6e0bfc74:142a35029f0:-7ef0','R2046788163');
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
INSERT INTO `loan_ledger_detail` VALUES ('LDGRITM-3f233e29:1424578a7c2:-8000','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','150.00','16.70','0.00','150.00','16.70','0.00','0.00','0.00','0.00','2013-11-08','324324',1,'9866.70','133.30','2013-11-11 12:43:24','150.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fb6','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','11105.50','16.70','3.00','11105.50','16.70','3.00','0.00','0.00','0.00','2013-11-11','B00000031',75,'0.00','9866.70','2013-11-11 15:25:01','150.00','155.50'),('LDGRITM-46dae022:142460c9af2:-7fb7','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',74,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fb8','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',73,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fb9','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',72,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fba','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',71,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fbb','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',70,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fbc','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',69,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fbd','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',68,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fbe','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',67,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fbf','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',66,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc0','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',65,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc1','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',64,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc2','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',63,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc3','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',62,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc4','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',61,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc5','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',60,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc6','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',59,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc7','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',58,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc8','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',57,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fc9','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',56,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fca','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',55,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fcb','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',54,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fcc','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',53,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fcd','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',52,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fce','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',51,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fcf','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',50,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd0','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',49,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd1','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',48,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd2','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',47,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd3','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',46,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd4','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',45,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd5','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',44,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd6','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',43,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd7','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',42,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd8','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',41,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fd9','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',40,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fda','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',39,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fdb','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',38,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fdc','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',37,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fdd','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',36,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fde','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',35,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fdf','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',34,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe0','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',33,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe1','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',32,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe2','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',31,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe3','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',30,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe4','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',29,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe5','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',28,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe6','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',27,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe7','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',26,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe8','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',25,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fe9','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',24,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fea','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',23,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7feb','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',22,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fec','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',21,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fed','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',20,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fee','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',19,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fef','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',18,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff0','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',17,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff1','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',16,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff2','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',15,'9866.70','0.00','2013-11-11 15:25:01','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff3','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',14,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff4','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',13,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff5','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',12,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff6','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',11,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff7','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',10,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff8','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',9,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ff9','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',8,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ffa','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',7,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ffb','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',6,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ffc','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',5,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ffd','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',4,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7ffe','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',3,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-46dae022:142460c9af2:-7fff','LEDGER-21391adb:14245325a71:-7dce','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-11','B00000031',2,'9866.70','0.00','2013-11-11 15:25:00','0.00','150.00'),('LDGRITM-472766aa:142a34fd471:-7ff6','LEDGER66fcd605:1429ce5c646:-7f9e','RECEIVED','200.00','16.70','3.00','200.00','16.70','3.00','0.00','0.00','0.00','2013-11-29','B00000064',3,'9753.10','163.60','2013-11-29 18:04:45','100.00','100.00'),('LDGRITM-472766aa:142a34fd471:-7ff7','LEDGER66fcd605:1429ce5c646:-7f9e','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-29','B00000064',2,'9916.70','0.00','2013-11-29 18:04:45','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7fef','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','200.00','16.70','48.00','200.00','16.70','48.00','0.00','0.00','0.00','2013-11-26','345345',18,'9996.60','1.70','2013-11-28 13:42:54','100.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff0','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',17,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff1','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',16,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff2','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',15,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff3','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',14,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff4','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',13,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff5','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',12,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff6','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',11,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff7','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-26','345345',10,'9998.30','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff8','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','200.00','16.70','48.00','200.00','16.70','48.00','0.00','0.00','0.00','2013-11-18','123345',9,'9998.30','1.70','2013-11-28 13:42:54','100.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ff9','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',8,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ffa','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',7,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ffb','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',6,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ffc','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',5,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ffd','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',4,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7ffe','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',3,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-7fff','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',2,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-4b2a148a:1429d3b3cb7:-8000','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-18','123345',1,'10000.00','0.00','2013-11-28 13:42:54','0.00','100.00'),('LDGRITM-6bcc03c8:1429d05332a:-7ffc','LEDGER66fcd605:1429ce5c646:-7f9e','RECEIVED','100.00','16.70','0.00','100.00','16.70','0.00','0.00','0.00','0.00','2013-11-27','1234',1,'9916.70','83.30','2013-11-28 12:44:42','100.00','100.00'),('LDGRITM7d328cd1:142a1c8b233:-7ffe','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','300.00','16.70','6.00','300.00','16.70','6.00','0.00','0.00','0.00','2013-11-28','B00000096',21,'9752.70','243.90','2013-11-29 10:55:53','100.00','100.00'),('LDGRITM7d328cd1:142a1c8b233:-7fff','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-28','B00000096',20,'9996.60','0.00','2013-11-29 10:55:53','0.00','100.00'),('LDGRITM7d328cd1:142a1c8b233:-8000','LEDGER20d86b3:1429d388aa9:-7ff6','RECEIVED','0.00','16.70','0.00','0.00','16.70','0.00','0.00','0.00','0.00','2013-11-28','B00000096',19,'9996.60','0.00','2013-11-29 10:55:53','0.00','100.00');
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
INSERT INTO `loan_payment` VALUES ('LB-21391adb:14245325a71:-7da4','2013-11-11','2013-11-11 15:25:00',1,'11105.50'),('LB-4dbda590:142a1fc536b:-7ff6','2013-11-29','2013-11-29 18:04:45',1,'200.00');
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
INSERT INTO `loan_payment_detail` VALUES ('LPDTL-46dae022:142460c9af2:-8000','LB-21391adb:14245325a71:-7da4','B00000031','LOAN3c8f5e6:14245464dd3:-8000','over','11105.50'),('LPDTL-472766aa:142a34fd471:-7ff8','LB-4dbda590:142a1fc536b:-7ff6','B00000064','LOAN6cd09d4c:1429cf5ff70:-8000','schedule','200.00');
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
INSERT INTO `loan_product_type` VALUES ('DAILY','DAILY SCHEDULE PAYMENT OPTION',120,'0.05','0.06','0.03','3.00','3.00');
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
INSERT INTO `loan_route` VALUES ('R1','1AM','LABANGON'),('R2046788163','GUADALUPE @1PM','GUADALUPE AREA');
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
INSERT INTO `loanapp` VALUES ('LOAN-3f233e29:1424578a7c2:-7ff5','RELEASED','LC00001','NEW','CAPTURE','2013-11-11 13:25:50','sa','10000.00','006','N.BACALSO BRANCH','CUST-7ffc746c:1422665be13:-7f72','DEVANCE, JOE','DAILY',120,'R2046788163','CAPTURE LOAN LC00001',NULL,NULL,'10000.00',NULL,NULL,NULL),('LOAN3c8f5e6:14245464dd3:-8000','CLOSED','LC00011','NEW','CAPTURE','2013-11-11 11:49:35','sa','10000.00','006','N.BACALSO BRANCH','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','DAILY',120,'R1','CAPTURE LOAN LC00011',NULL,NULL,'10000.00',NULL,NULL,NULL),('LOAN6cd09d4c:1429cf5ff70:-8000','RELEASED','LC-00014','NEW','CAPTURE','2013-11-28 12:43:52','sa','10000.00','006','N.BACALSO BRANCH','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','DAILY',120,'R2046788163','CAPTURE LOAN LC-00014',NULL,NULL,'10000.00',NULL,NULL,NULL);
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
INSERT INTO `loanapp_borrower` VALUES ('LB-3f233e29:1424578a7c2:-7ff0','LOAN-3f233e29:1424578a7c2:-7ff5','CUST-7ffc746c:1422665be13:-7f72','DEVANCE, JOE','CUST-7ffc746c:1422665be13:-7f72',NULL,'PRINCIPAL',NULL),('LB-6bcc03c8:1429d05332a:-7ffd','LOAN6cd09d4c:1429cf5ff70:-8000','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','CUST5ff69d5a:141e9710f6b:-7f84',NULL,'PRINCIPAL',NULL),('LB3c8f5e6:14245464dd3:-7ffc','LOAN3c8f5e6:14245464dd3:-8000','CUST5ff69d5a:141e9710f6b:-7f84','PERNITO, CARL LOUIE','CUST5ff69d5a:141e9710f6b:-7f84',NULL,'PRINCIPAL',NULL);
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
INSERT INTO `loanapp_capture` VALUES ('LOAN-3f233e29:1424578a7c2:-7ff5',1,'LC00001','WALK-IN',NULL,'2013-11-07'),('LOAN3c8f5e6:14245464dd3:-8000',1,'LC00011','WALK-IN',NULL,'2013-11-07'),('LOAN6cd09d4c:1429cf5ff70:-8000',1,'LC-00014','WALK-IN',NULL,'2013-11-26');
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
INSERT INTO `loanapp_log` VALUES ('LOG-3f233e29:1424578a7c2:-7ff3','LOAN-3f233e29:1424578a7c2:-7ff5','2013-11-11 13:25:50','sa','CAPTURED LOAN TRANSACTION #LC00001'),('LOG-6bcc03c8:1429d05332a:-8000','LOAN6cd09d4c:1429cf5ff70:-8000','2013-11-28 12:43:52','sa','CAPTURED LOAN TRANSACTION #LC-00014'),('LOG3c8f5e6:14245464dd3:-7fff','LOAN3c8f5e6:14245464dd3:-8000','2013-11-11 11:49:35','sa','CAPTURED LOAN TRANSACTION #LC00011');
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
INSERT INTO `loanapp_search` VALUES ('LOAN-3f233e29:1424578a7c2:-7ff5','RELEASED','LC00001','006','N.BACALSO BRANCH','R2046788163','DEVANCE, JOE','10000.00','2013-11-11 13:25:50','sa'),('LOAN3c8f5e6:14245464dd3:-8000','CLOSED','LC00011','006','N.BACALSO BRANCH','R1','PERNITO, CARL LOUIE','10000.00','2013-11-11 11:49:35','sa'),('LOAN6cd09d4c:1429cf5ff70:-8000','RELEASED','LC-00014','006','N.BACALSO BRANCH','R2046788163','PERNITO, CARL LOUIE','10000.00','2013-11-28 12:43:52','sa');
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
INSERT INTO `loanapp_search_index` VALUES ('IDX-19d2e2d8:141e9ab9169:-7ffd','LOAN-19d2e2d8:141e9ab9169:-8000','PERNITO, CARL LOUIE',NULL),('IDX-19d2e2d8:141e9ab9169:-7ffe','LOAN-19d2e2d8:141e9ab9169:-8000','LC00001',NULL),('IDX-295f598f:142079e541d:-7ffd','LOAN-295f598f:142079e541d:-8000','PERNITO, CARL LOUIE',NULL),('IDX-295f598f:142079e541d:-7ffe','LOAN-295f598f:142079e541d:-8000','LC00004',NULL),('IDX-3f233e29:1424578a7c2:-7ff1','LOAN-3f233e29:1424578a7c2:-7ff5','DEVANCE, JOE',NULL),('IDX-3f233e29:1424578a7c2:-7ff2','LOAN-3f233e29:1424578a7c2:-7ff5','LC00001',NULL),('IDX-442704a1:142453abea8:-7fab','LOAN-442704a1:142453abea8:-7fae','PERNITO, CARL LOUIE',NULL),('IDX-442704a1:142453abea8:-7fac','LOAN-442704a1:142453abea8:-7fae','LC000010',NULL),('IDX-442704a1:142453abea8:-7fb1','LOAN-442704a1:142453abea8:-7fb5','PERNITO, CARL LOUIE',NULL),('IDX-442704a1:142453abea8:-7fb2','LOAN-442704a1:142453abea8:-7fb5','LC00009',NULL),('IDX-6bcc03c8:1429d05332a:-7ffe','LOAN6cd09d4c:1429cf5ff70:-8000','PERNITO, CARL LOUIE',NULL),('IDX-6bcc03c8:1429d05332a:-7fff','LOAN6cd09d4c:1429cf5ff70:-8000','LC-00014',NULL),('IDX-7dfb780f:142079a0836:-7ffd','LOAN-7dfb780f:142079a0836:-8000','PERNITO, CARL LOUIE',NULL),('IDX-7dfb780f:142079a0836:-7ffe','LOAN-7dfb780f:142079a0836:-8000','LC000004',NULL),('IDX168604f:14207e91e39:-7ffd','LOAN168604f:14207e91e39:-8000','PERNITO, CARL',NULL),('IDX168604f:14207e91e39:-7ffe','LOAN168604f:14207e91e39:-8000','LC0001',NULL),('IDX1d59af7c:14226f402ac:-7ffc','LOAN1d59af7c:14226f402ac:-8000','DEVANCE, JOE',NULL),('IDX1d59af7c:14226f402ac:-7ffd','LOAN1d59af7c:14226f402ac:-8000','LC00008',NULL),('IDX2a32fcd8:1420747148c:-7ffb','LOAN2a32fcd8:1420747148c:-7fff','PERNITO, CARL LOUIE',NULL),('IDX2a32fcd8:1420747148c:-7ffc','LOAN2a32fcd8:1420747148c:-7fff','LC00003',NULL),('IDX3c8f5e6:14245464dd3:-7ffd','LOAN3c8f5e6:14245464dd3:-8000','PERNITO, CARL LOUIE',NULL),('IDX3c8f5e6:14245464dd3:-7ffe','LOAN3c8f5e6:14245464dd3:-8000','LC00011',NULL),('IDX62fabe5e:141eeb32c2f:-7ffd','LOAN62fabe5e:141eeb32c2f:-8000','PERNITO, CARL',NULL),('IDX62fabe5e:141eeb32c2f:-7ffe','LOAN62fabe5e:141eeb32c2f:-8000','LC00002',NULL);
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
INSERT INTO `sys_report_entry` VALUES ('R1','rpt1','first','First Report','report',NULL,NULL),('R2','rpt1','second','Second Report','report',NULL,NULL);
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
INSERT INTO `sys_ruleset` VALUES ('bplsassessment','BP Assessment Rules',NULL,'BPLS','BP_DATAMGMT',NULL),('bplsbilling','BPLS Billing Rules',NULL,'BPLS','BP_DATAMGMT',NULL),('rptbilling','RPT Billing Rules',NULL,'RPT','RPT_DATAMGMT',NULL);
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
INSERT INTO `sys_sequence` VALUES ('customer006',4),('ENTITY',4),('loan',1),('loanbill',69);
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
INSERT INTO `sys_session` VALUES ('SESS-59a1efa9:141edf289e2:-7fef','sa','sa','desktop','2013-10-25 14:08:36',NULL,'2013-10-25 14:08:36'),('SESS-59a1efa9:141edf289e2:-7fee','sa','sa','desktop','2013-10-25 14:12:10',NULL,'2013-10-25 14:12:10'),('SESS-59a1efa9:141edf289e2:-7fe7','sa','sa','desktop','2013-10-25 15:47:44',NULL,'2013-10-25 15:47:44'),('SESS-59a1efa9:141edf289e2:-7fe6','sa','sa','desktop','2013-10-25 15:48:14',NULL,'2013-10-25 15:48:14');
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
INSERT INTO `sys_session_log` VALUES ('SESS-59a1efa9:141edf289e2:-7fed','sa','sa','desktop','2013-10-25 14:29:15',NULL,'2013-10-25 14:29:15','2013-10-25 14:29:22','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fec','sa','sa','desktop','2013-10-25 14:32:31',NULL,'2013-10-25 14:32:31','2013-10-25 14:32:41','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7feb','sa','sa','desktop','2013-10-25 14:41:21',NULL,'2013-10-25 14:41:21','2013-10-25 14:42:20','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fea','sa','sa','desktop','2013-10-25 14:47:23',NULL,'2013-10-25 14:47:23','2013-10-25 14:47:43','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe9','sa','sa','desktop','2013-10-25 14:52:31',NULL,'2013-10-25 14:52:31','2013-10-25 14:52:59','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe8','sa','sa','desktop','2013-10-25 14:56:51',NULL,'2013-10-25 14:56:51','2013-10-25 14:59:36','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe5','sa','sa','desktop','2013-10-25 16:03:48',NULL,'2013-10-25 16:03:48','2013-10-25 16:04:07','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe4','sa','sa','desktop','2013-10-25 16:42:25',NULL,'2013-10-25 16:42:25','2013-10-25 16:42:59','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe3','sa','sa','desktop','2013-10-25 16:50:27',NULL,'2013-10-25 16:50:27','2013-10-25 16:50:51','LOGOUT'),('SESS-59a1efa9:141edf289e2:-7fe2','sa','sa','desktop','2013-10-25 16:51:02',NULL,'2013-10-25 16:51:02','2013-10-25 16:51:11','LOGOUT');
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
INSERT INTO `sys_terminal` VALUES ('ADMIN',NULL,NULL,NULL,'00-26-22-AB-A3-6E','2013-10-18 09:12:43','DEVELOPER',NULL,NULL),('T1',NULL,NULL,NULL,'90-4C-E5-36-9A-C9','2013-10-23 14:10:47','DEVLEOPER',NULL,NULL);
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
INSERT INTO `sys_user` VALUES ('USR-7eced478:141e3ef3147:-7fe7','DRAFT',NULL,'SA','USER1','50fd0429181d46e4dd11df7e3839663f','USER1','USER1',NULL,'USER1, USER1','USER',0,NULL,'50fd0429181d46e4dd11df7e3839663f,c4b72ce5a60fe7ab05297a1543146796','LOCK-64c31da5:141e9715f4e:-8000'),('USR3e083699:141d9327859:-7feb','DRAFT',NULL,'sa','TEST','a1623e2c09fd8581935cf9ad45bf56ee','TEST','TEST',NULL,'TEST, TEST','TEST',NULL,NULL,NULL,NULL);
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
INSERT INTO `sys_usergroup_member` VALUES ('USGRP129a1e22:141e3a41bc2:-8000',NULL,'LOAN_FIELD_COLLECTOR','USR3e083699:141d9327859:-7feb','TEST','TEST','TEST',NULL,NULL,NULL,'LOAN_FIELD_COLLECTOR_DEFAULT',NULL,NULL,'TEST',NULL),('USGRP650dfe83:141e3f02c9b:-7ffb',NULL,'LOAN_FIELD_COLLECTOR','USR-7eced478:141e3ef3147:-7fe7','USER1','USER1','USER1',NULL,NULL,NULL,'LOAN_FIELD_COLLECTOR_DEFAULT',NULL,NULL,'USER',NULL),('USGRP650dfe83:141e3f02c9b:-7ffc',NULL,'LOAN_DATAMGMT_AUTHOR','USR-7eced478:141e3ef3147:-7fe7','USER1','USER1','USER1',NULL,NULL,NULL,'LOAN_DATAMGMT_AUTHOR_DEFAULT',NULL,NULL,'USER',NULL),('USGRP650dfe83:141e3f02c9b:-7ffd',NULL,'LOAN_CAO_OFFICER','USR-7eced478:141e3ef3147:-7fe7','USER1','USER1','USER1',NULL,NULL,NULL,'LOAN_CAO_OFFICER_DEFAULT',NULL,NULL,'USER',NULL),('USGRP650dfe83:141e3f02c9b:-7ffe',NULL,'LOAN_APPROVER','USR-7eced478:141e3ef3147:-7fe7','USER1','USER1','USER1',NULL,NULL,NULL,'LOAN_APPROVER_DEFAULT',NULL,NULL,'USER',NULL),('USGRP650dfe83:141e3f02c9b:-7fff',NULL,'LOAN_ACCT_OFFICER','USR-7eced478:141e3ef3147:-7fe7','USER1','USER1','USER1',NULL,NULL,NULL,'LOAN_ACCT_OFFICER_DEFAULT',NULL,NULL,'USER',NULL);
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

-- Dump completed on 2013-11-29 10:18:32
