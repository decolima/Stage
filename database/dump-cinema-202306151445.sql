-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `biglietto`
--

DROP TABLE IF EXISTS `biglietto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `biglietto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `importo` decimal(19,2) DEFAULT NULL,
  `nome_cliente` varchar(255) DEFAULT NULL,
  `pos_x` int(11) NOT NULL CHECK (`pos_x` >= 1),
  `pos_y` int(11) NOT NULL CHECK (`pos_y` >= 1),
  `tipo` varchar(255) NOT NULL,
  `programmazione_id` bigint(20) NOT NULL,
  `utente_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7dg22egdvgi08ib7fu5pub0g7` (`programmazione_id`),
  KEY `FK33t56je40vpfflkuawxeemmkg` (`utente_id`),
  CONSTRAINT `FK33t56je40vpfflkuawxeemmkg` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`),
  CONSTRAINT `FK7dg22egdvgi08ib7fu5pub0g7` FOREIGN KEY (`programmazione_id`) REFERENCES `programmazione` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biglietto`
--

LOCK TABLES `biglietto` WRITE;
/*!40000 ALTER TABLE `biglietto` DISABLE KEYS */;
INSERT INTO `biglietto` VALUES (1,0,10.00,'teste',1,1,'INTERO',1,1);
/*!40000 ALTER TABLE `biglietto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `film`
--

DROP TABLE IF EXISTS `film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `film` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `eta_minima` int(11) NOT NULL CHECK (`eta_minima` >= 0),
  `regista` varchar(255) DEFAULT NULL,
  `titolo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
INSERT INTO `film` VALUES (1,NULL,'Descrizione 1',0,'Regista 1','Film 1');
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programmazione`
--

DROP TABLE IF EXISTS `programmazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programmazione` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `data_programmazione` date NOT NULL,
  `data_pubblicazione` date NOT NULL,
  `prezzo` decimal(4,2) NOT NULL,
  `film_id` bigint(20) NOT NULL,
  `sala_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpdfh6w8o33t3egmcfx4qv1njj` (`film_id`,`data_programmazione`,`sala_id`),
  KEY `FK566yw8omvvuyh5jmuh1stdx01` (`sala_id`),
  CONSTRAINT `FK566yw8omvvuyh5jmuh1stdx01` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`),
  CONSTRAINT `FKqusqe4h0ylsxia45wxx5rriua` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programmazione`
--

LOCK TABLES `programmazione` WRITE;
/*!40000 ALTER TABLE `programmazione` DISABLE KEYS */;
INSERT INTO `programmazione` VALUES (1,NULL,'2023-06-25','2023-06-14',10.00,1,1);
/*!40000 ALTER TABLE `programmazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sala` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `posti` int(11) NOT NULL CHECK (`posti` >= 0),
  `posti_x` int(11) NOT NULL CHECK (`posti_x` >= 0),
  `posti_y` int(11) NOT NULL CHECK (`posti_y` >= 0),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1xhvp29dwpod0att46kiwh5um` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES (1,NULL,'SALA 1',100,10,10);
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `contatore_acesso` bigint(20) DEFAULT NULL,
  `data_nascita` date NOT NULL,
  `data_acesso` date DEFAULT NULL,
  `pwd` varchar(255) NOT NULL,
  `ruolo` varchar(255) NOT NULL,
  `usr` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t5sf08b5wohp7622qaiq0a461` (`usr`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,NULL,NULL,'2000-01-01',NULL,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','ADMIN','admin');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-15 14:45:06
