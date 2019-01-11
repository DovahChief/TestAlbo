create database avengersdatabase;
use avengersdatabase;
 SET NAMES utf8 ;

DROP TABLE IF EXISTS `creator`;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `creator` (
  `idcreators` int(11) NOT NULL AUTO_INCREMENT,
  `creator_name` varchar(45) NOT NULL,
  `creator_role` varchar(45) NOT NULL,
  `hero` int(11) NOT NULL,
  PRIMARY KEY (`idcreators`),
  KEY `herofk_idx` (`hero`),
  CONSTRAINT `herofk` FOREIGN KEY (`hero`) REFERENCES `hero_catalog` (`id_hero_catalog`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `hero_catalog`;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hero_catalog` (
  `id_hero_catalog` int(11) NOT NULL AUTO_INCREMENT,
  `hero_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_hero_catalog`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `hero_catalog` WRITE;
INSERT INTO `hero_catalog` VALUES (1,'captain america'),(2,'iron man');
UNLOCK TABLES;

