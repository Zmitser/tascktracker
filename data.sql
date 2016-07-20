-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: tasktracker
-- ------------------------------------------------------
-- Server version	5.7.12-log
SET AUTOCOMMIT=0;
SET FOREIGN_KEY_CHECKS=0;
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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_2d9ern8bvaarimfayk9083iog` (`task_id`),
  CONSTRAINT `FK_2d9ern8bvaarimfayk9083iog` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (9,'gggggggggggggggggg',1),(10,'dsdsad',2),(11,'czcxz',2),(13,'ccscssssssss',1),(14,'cczxcxz',1),(15,'sdsad',8);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Cleave.js has a simple purpose: to help you format input text content automatically.','Cleave.js'),(2,'Uppy is (going to be) a cool JavaScript file uploader that fetches files for you from local disk, Google Drive, Dropbox, Instagram, remote URLs, cameras and other exciting locations, and then uploads them to wherever you want. ','Uppy'),(3,'React is a JavaScript library for building user interfaces.','React'),(4,'Simple yet powerful library for building modern web interfaces.','Vue'),(5,'Redux is a predictable state container for JavaScript apps.','Redux'),(6,'Angular is a development platform for building mobile and desktop web applications. ','Angular');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `is_completed` tinyint(4) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FK_b7i81l1tk1ph95xnhtoftyv53` (`project_id`),
  KEY `FK_4fmjedju7b35tb5cr71n3ntb0` (`user_id`),
  CONSTRAINT `FK_4fmjedju7b35tb5cr71n3ntb0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_b7i81l1tk1ph95xnhtoftyv53` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'2016-06-18 23:37:53','Add credit card type detect event callback',0,'Issue-8888',1,3),(2,'2016-07-18 23:37:53','Fix incorrect remove target in destroy method',0,'Issue-21',1,2),(3,'2016-07-18 23:39:18','Fix prefix can\'t apply issue',0,'Issue-18',1,2),(4,'2016-07-18 23:41:37','Restructure npm scripts, get rid of parallelshell',0,'Issue-102',2,3),(5,'2016-07-18 23:43:54','Allow to cancel Tus uploads in progress',0,'Issue-128',2,3),(6,'2016-07-18 23:45:08','Google Drive client side web sockets',0,'Issue-110',2,3),(7,'2016-07-18 23:48:57','Add referrerPolicy to HTMLDOMPropertyConfig',0,'Issue-7274',3,3),(8,'2016-07-18 23:50:09','Inject default batching after pending transactions',0,'Issue-7033',3,4),(9,'2016-07-18 23:51:08','Make ReactPerf.start() work during reconciliation',0,'Issue-7208',3,4),(10,'2016-07-18 23:54:35','fix multi-line inside HTML interpolations',0,'Issue-3062',4,2),(11,'2016-07-18 23:56:36','Allow extended Vue constructors as the mixins option',0,'Issue-2957',4,2),(12,'2016-07-18 23:57:55','use innerHTML from template nodes for consistent behavior across brow…',0,'Issue-2805',4,3),(13,'2016-07-18 23:59:52','Remove unnecessary optional type from StoreCreator.',0,'Issue-1806',5,4),(14,'2016-07-19 00:00:58','Fix duplicate statement in todomvc test spec',0,'Issue-1782',5,4),(15,'2016-07-19 00:03:05','Use a different convention for colocating reducers with selectors',0,'Issue-1708',5,4),(16,'2016-07-19 00:09:46','add support for ArrayBuffer',0,'Issue-4231',6,3),(17,'2016-07-19 00:09:46','share \'body\' logic between Request and Response',0,'Issue-4131',6,4),(18,'2016-07-19 00:09:46','guards and data resolvers can now return promises',0,'Issue-4324',6,4),(20,'2016-07-19 22:06:07','cxzcxz',1,'cxzc',2,2);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `is_enabled` tinyint(4) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager@manager.by',1,'$2a$06$.SpVm1IVT1nCEkDztV1sp.Ii.2IeexNUOhAABIzWlvyBwdYc/w0i.','ROLE_MANAGER','manager'),(2,'developer@developer.by',1,'$2a$06$8E2gS2pIz4dFVRiyGDWn7.7pDplRahK4Xl0VuYv2MmQqvraUUvy8W','ROLE_DEVELOPER','David'),(3,'developer1@developer.by',1,'$2a$06$oB0Z6p00r1Go6G9jSUYAteL7h1vaeL84q4OIA/xB311dEk1Y7Sn/q','ROLE_DEVELOPER','Nick'),(4,'developer2@developer.by',1,'$2a$06$fhuEUvIXM2DreiJUHHF4zeoiO4vDWz4xIvObbwSnJ3p35xoC7Csom','ROLE_DEVELOPER','John');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-20  3:42:37
