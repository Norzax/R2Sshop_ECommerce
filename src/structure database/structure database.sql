CREATE DATABASE  IF NOT EXISTS `r2sshop_ecommerce` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `r2sshop_ecommerce`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: r2sshop_ecommerce
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'address thu 1',3),(2,'address thu 2',3);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,'2023-12-23 21:10:52.963000',2),(2,'2023-12-23 21:19:44.817000',3);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartline_item`
--

DROP TABLE IF EXISTS `cartline_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartline_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `added_date` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `cart_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  `variant_product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswl36g3nsubgp3qh5wgglrwsb` (`cart_id`),
  KEY `FKhkdsqhjbatn7id8p4b2i52us` (`order_id`),
  KEY `FK8ja1ji7f99rfvu5ksmjqav1wr` (`variant_product_id`),
  CONSTRAINT `FK8ja1ji7f99rfvu5ksmjqav1wr` FOREIGN KEY (`variant_product_id`) REFERENCES `variant_product` (`id`),
  CONSTRAINT `FKhkdsqhjbatn7id8p4b2i52us` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKswl36g3nsubgp3qh5wgglrwsb` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartline_item`
--

LOCK TABLES `cartline_item` WRITE;
/*!40000 ALTER TABLE `cartline_item` DISABLE KEYS */;
INSERT INTO `cartline_item` VALUES (5,'2023-12-23 22:59:13.568000',_binary '',5,130,1,5,1),(6,'2023-12-23 23:14:19.459000',_binary '',6,300,1,5,2),(7,'2023-12-23 23:16:14.706000',_binary '',4,320,1,5,3),(8,'2023-12-24 00:33:12.384000',_binary '',2,52,2,6,1),(9,'2023-12-24 00:33:21.800000',_binary '',6,6000,2,6,4),(10,'2023-12-24 09:47:42.747000',_binary '',4,120,1,5,10),(11,'2023-12-24 10:38:34.899000',_binary '',5,100,1,5,11),(12,'2023-12-24 10:51:04.731000',_binary '',4,280,1,5,12),(13,'2023-12-24 10:53:51.738000',_binary '',1,15,1,5,13),(14,'2023-12-24 10:55:45.398000',_binary '',2,80,1,5,14),(15,'2023-12-24 11:03:52.958000',_binary '\0',1,60,1,7,29),(16,'2023-12-24 20:53:20.484000',_binary '',4,200,2,8,2),(17,'2023-12-24 20:53:31.436000',_binary '',8,640,2,8,3),(20,'2023-12-25 00:37:02.762000',_binary '',8,128,2,11,40),(21,'2023-12-25 02:24:26.815000',_binary '\0',6,240,2,12,40);
/*!40000 ALTER TABLE `cartline_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,' Includes clothing, shoes, bags, jewelry, watches, and other fashion accessories.','Fashion and Accessories'),(2,'Encompasses mobile phones, tablets, laptops, cameras, smart home devices, and other consumer electronics.','Electronics'),(3,'Covers furniture, kitchenware, smart home appliances, cooking utensils, household items, and home decor.','Home and Kitchen'),(4,'Involves cosmetics, pharmaceuticals, health care products, supplements, and beauty devices.','Health and Beauty'),(5,'Involves books, children\'s toys, educational toys, electronic games, and various play items.','Books and Toys'),(6,'Encompasses sports gear, sportswear, outdoor equipment, fishing gear, and outdoor toys.','Sports and Outdoors'),(7,'Includes car accessories, motorcycles, bicycles, vehicle care and decoration products.','Automotive and Vehicles'),(8,'Covers food items, beverages, snacks, fresh produce, and culinary-related products.','Food and Beverages'),(9,'Involves pet food, toys, pet care products, and services related to pets.','Pets'),(10,'Encompasses children\'s toys, educational toys, electronic games, and entertainment products.','Toys and Electronic Gadgets');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `receiver_id` bigint DEFAULT NULL,
  `sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjnjxr6fd6nmvp28gakno4np94` (`receiver_id`),
  KEY `FKip9clvpi646rirksmm433wykx` (`sender_id`),
  CONSTRAINT `FKip9clvpi646rirksmm433wykx` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKjnjxr6fd6nmvp28gakno4np94` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `delivery_time` datetime(6) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,'abc','2023-12-25 11:00:57.153000',1345,'Ten Nguoi Khac',2),(6,'thu lan thu 8','2023-12-25 01:25:49.400000',6052,'thu them lan 8',3),(7,NULL,NULL,NULL,NULL,NULL),(8,'dia chi nhan','2023-12-25 20:54:00.492000',756,'tui la Luan',3),(11,'day la address','2023-12-26 02:07:12.842000',57.6,'tui',3),(12,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'T-shirt',1),(2,'High Heels',1),(3,'Leather Handbag',1),(4,'Smartphone',2),(5,'Tablet',2),(6,'Laptop',2),(7,'Sofa',3),(8,'Cookware Set',3),(9,'Smart Speaker',3),(10,'Lipstick Set',4),(11,'Vitamin C Serum',4),(12,'Hair Dryer',4),(13,'Fantasy Novel',5),(14,'Building Blocks Set',5),(15,'VR Gaming Console',5),(16,'Running Shoes',6),(17,'Tent',6),(18,'Fishing Rod',6),(19,'Car Seat Covers',7),(20,'Motorcycle Helmet',7),(21,'Bicycle Lock',7),(22,'Organic Coffee Beans',8),(23,'Green Tea',8),(24,'Assorted Snack Pack',8),(25,'Dog Food',9),(26,'Cat Toy Set',9),(27,'Pet Grooming Kit',9),(28,'Action Figure Set',10),(29,'Educational Robot',10),(30,'VR Headset',10);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_percentage` int DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `is_order_discount` bit(1) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `usage_limit` int DEFAULT NULL,
  `is_enable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (58,'BAOLUAN24','string',10,'2023-12-24 22:37:42.653000',_binary '\0','2023-12-24 21:37:42.653000',0,_binary '\0'),(59,'TEST PROMO2','string',0,'2023-12-24 20:27:11.951000',_binary '','2023-12-24 20:25:53.740000',10,_binary '\0'),(60,'BAOLUAN','string',10,'2023-12-24 21:27:11.951000',_binary '','2023-12-24 20:25:53.740000',10,_binary '\0'),(64,'BAOLUAN2','string',20,'2023-12-24 21:37:42.653000',_binary '\0','2023-12-24 21:37:42.653000',0,_binary '\0'),(65,'code moi','string',20,'2023-12-25 01:21:44.372000',_binary '\0','2023-12-24 23:21:44.372000',-1,_binary '\0'),(66,'code moi2','string',30,'2023-12-25 01:21:44.372000',_binary '\0','2023-12-24 23:21:44.372000',-1,_binary '\0'),(67,'code moi3','string',30,'2023-12-25 01:21:44.372000',_binary '\0','2023-12-24 23:21:44.372000',-1,_binary '\0'),(68,'code moi4','string',30,'2023-12-25 02:21:44.372000',_binary '\0','2023-12-24 23:21:44.372000',-1,_binary '\0'),(69,'code moi5','string',60,'2023-12-25 02:30:57.171000',_binary '\0','2023-12-25 02:26:57.171000',15,_binary '\0'),(70,'BAOLUAN3.1','giam gia ne',13,'2023-12-25 02:30:32.851000',_binary '','2023-12-25 02:09:32.851000',11,_binary '\0');
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,NULL,'USER'),(2,NULL,'ADMIN'),(3,NULL,'TEST');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_user`
--

DROP TABLE IF EXISTS `role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_user` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKiqpmjd2qb4rdkej916ymonic6` (`role_id`),
  CONSTRAINT `FK4320p8bgvumlxjkohtbj214qi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKiqpmjd2qb4rdkej916ymonic6` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_user`
--

LOCK TABLES `role_user` WRITE;
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
INSERT INTO `role_user` VALUES (2,1),(3,1),(1,2);
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'string','string','$2a$10$XftnD6XMPYScKQb4H7nHQen2dDJDPKpiCawj/CEy/5h/MSSUCi0M.',_binary '','string'),(2,'string','string','$2a$10$QHwQ72PY1KNlDtyZ8C2zHOKPP9sc2AuCNDSVANE.QFpVQZkSWGdQG',_binary '','string1'),(3,'thisIsEmail','User co id la 3','$2a$10$cR/llfdSOkooHRo7vnkYROsNVCGeicC5xj4NOkFrS674RtxkSbNlq',_binary '','string2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant_product`
--

DROP TABLE IF EXISTS `variant_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(50) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK19o7w496ujmendl91f56cu00f` (`product_id`),
  CONSTRAINT `FK19o7w496ujmendl91f56cu00f` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant_product`
--

LOCK TABLES `variant_product` WRITE;
/*!40000 ALTER TABLE `variant_product` DISABLE KEYS */;
INSERT INTO `variant_product` VALUES (1,'Black','V-Neck',26,'M',1),(2,'Red','Stiletto',50,'8',2),(3,'Brown','Crossbody',80,'N/A',3),(4,'Black','iPhone 13',1000,'N/A',4),(5,'Silver','Samsung Galaxy Tab S7',650,'10.4\"',5),(6,'Space Gray','MacBook Pro 2022',1500,'13\"',6),(7,'Gray','Sectional Sofa',900,'N/A',7),(8,'Black','Non-Stick Pots and Pans',130,'N/A',8),(9,'White','Amazon Echo Dot',50,'N/A',9),(10,'Red','Matte Lipstick Bundle',30,'N/A',10),(11,'N/A','30ml Bottle',20,'N/A',11),(12,'Black','Ionic Hair Dryer',70,'N/A',12),(13,'N/A','The Name of the Wind',15,'N/A',13),(14,'Multicolor','1000-Piece Blocks',40,'N/A',14),(22,'Brown','Arabica Coffee',13,'1 lb',22),(23,'Green','Japanese Matcha',10,'50g',23),(24,'Multicolor','Variety Snack Pack',20,'N/A',24),(25,'N/A','Premium Dog Food',30,'5 lb',25),(26,'Multicolor','Interactive Cat Toys',15,'N/A',26),(27,'Blue','Pet Clippers and Brushes',40,'N/A',27),(28,'Multicolor','Superhero Action Figures',25,'N/A',28),(29,'Silver','STEM Learning Robot',60,'N/A',29),(30,'Black','Oculus Rift S',400,'N/A',30),(31,'Blue','V-Neck',28,'L',1),(32,'Yellow','Pumps',60,'7',1),(33,'Green','Clutch',120,'N/A',1),(34,'Red','iPhone 13 Pro',1200,'N/A',2),(35,'Space Gray','iPad Pro',900,'12.9\"',2),(36,'Silver','MacBook Pro',2200,'14\"',2),(37,'Brown','Leather Sofa',1400,'N/A',3),(38,'Gray','Dining Table Set',900,'N/A',3),(39,'Black','Smart Refrigerator',2500,'N/A',3),(40,'Red','Lipstick Set',40,'N/A',4),(41,'Clear','Face Serum',50,'N/A',4),(42,'Black','Hair Dryer',80,'N/A',4),(43,'N/A','The Hobbit',15,'N/A',5),(44,'Multicolor','LEGO Classic',50,'N/A',5),(45,'Black','VR Headset',300,'N/A',5),(46,'Blue','Running Shoes',100,'10',6),(47,'Green','Camping Tent',250,'N/A',6),(48,'Black','Fishing Rod',70,'6ft',6),(49,'Gray','Car Seat Covers',80,'N/A',7),(50,'Red','Motorcycle Helmet',120,'L',7),(51,'Black','Bike Lock',30,'N/A',7),(52,'Brown','Arabica Coffee',13,'1 lb',8),(53,'Green','Green Tea',10,'50g',8),(54,'Multicolor','Snack Pack',20,'N/A',8),(55,'Blue','Dog Food',25,'10 lb',9),(56,'Gray','Cat Toy Set',20,'N/A',9),(57,'Black','Grooming Kit',35,'N/A',9),(58,'Red','Action Figure Set',30,'N/A',10),(59,'Silver','Educational Robot',70,'N/A',10),(60,'Black','VR Headset',300,'N/A',10);
/*!40000 ALTER TABLE `variant_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant_product_promo`
--

DROP TABLE IF EXISTS `variant_product_promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant_product_promo` (
  `variant_product_id` bigint NOT NULL,
  `promo_id` bigint NOT NULL,
  KEY `FKim8b9awby34djl16fjyfd7470` (`promo_id`),
  KEY `FKmcntwaaadog3v8li6isw8h9mr` (`variant_product_id`),
  CONSTRAINT `FKim8b9awby34djl16fjyfd7470` FOREIGN KEY (`promo_id`) REFERENCES `promotion` (`id`),
  CONSTRAINT `FKmcntwaaadog3v8li6isw8h9mr` FOREIGN KEY (`variant_product_id`) REFERENCES `variant_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant_product_promo`
--

LOCK TABLES `variant_product_promo` WRITE;
/*!40000 ALTER TABLE `variant_product_promo` DISABLE KEYS */;
INSERT INTO `variant_product_promo` VALUES (3,58),(3,65),(4,66),(4,67),(4,68),(40,69);
/*!40000 ALTER TABLE `variant_product_promo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-26 11:27:00
