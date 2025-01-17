-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: kappa
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `sex` enum('MALE','FEMALE','OTHER') NOT NULL,
  `customer_credits` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `customer_chk_1` CHECK ((`customer_credits` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'John','Doe','MALE',1200),(2,'Jane','Smith','FEMALE',1500),(3,'Alex','Taylor','OTHER',500),(4,'Emily','Johnson','FEMALE',2300),(5,'Michael','Brown','MALE',1800),(6,'Chris','Davis','MALE',750),(7,'Sarah','Miller','FEMALE',420),(8,'Jordan','Garcia','OTHER',1300),(9,'Sophia','Martinez','FEMALE',2100),(10,'David','Hernandez','MALE',50),(11,'Ella','Lopez','FEMALE',1700),(12,'Ethan','Wilson','MALE',650),(13,'Mia','Anderson','FEMALE',900),(14,'Ryan','Thomas','MALE',400),(15,'Avery','Moore','OTHER',120),(16,'Isabella','Jackson','FEMALE',2500),(17,'Benjamin','Lee','MALE',300),(18,'Oliver','White','MALE',1150),(19,'Liam','Harris','MALE',680),(20,'Charlotte','Clark','FEMALE',240),(21,'James','Walker','MALE',890),(22,'Amelia','Young','FEMALE',2000),(23,'Lucas','King','MALE',350),(24,'Harper','Hall','FEMALE',180),(25,'Mason','Allen','MALE',970);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `customer_products_in_order`
--

DROP TABLE IF EXISTS `customer_products_in_order`;
/*!50001 DROP VIEW IF EXISTS `customer_products_in_order`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `customer_products_in_order` AS SELECT 
 1 AS `customer_first_name`,
 1 AS `customer_last_name`,
 1 AS `product_name`,
 1 AS `number_of_items`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `most_ordered_products`
--

DROP TABLE IF EXISTS `most_ordered_products`;
/*!50001 DROP VIEW IF EXISTS `most_ordered_products`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `most_ordered_products` AS SELECT 
 1 AS `product_name`,
 1 AS `total_ordered`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `place_date` date NOT NULL,
  `price` float NOT NULL,
  `delivery_address` varchar(400) NOT NULL,
  `delivery_zip` char(5) NOT NULL,
  `expected_delivery` datetime NOT NULL,
  `order_note` text,
  PRIMARY KEY (`id`),
  KEY `order_customer_FK` (`customer_id`),
  CONSTRAINT `order_customer_FK` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,'2025-01-10',349.99,'123 Main Street, Springfield','12345','2025-01-14 14:00:00','Leave at the front door'),(2,2,'2025-01-09',699.99,'456 Oak Avenue, Maple Town','23456','2025-01-13 12:00:00','Handle with care'),(3,3,'2025-01-08',59.99,'789 Pine Road, Elm City','34567','2025-01-12 16:00:00','Ring the doorbell upon arrival'),(4,4,'2025-01-07',199.99,'101 Birch Blvd, Cedarville','45678','2025-01-11 10:00:00',NULL),(5,5,'2025-01-06',49.99,'202 Willow Lane, Poplar Creek','56789','2025-01-10 18:00:00','Call before delivery'),(6,6,'2025-01-05',129.99,'303 Aspen Drive, Redwood City','67890','2025-01-09 11:00:00','Deliver to the side door'),(7,7,'2025-01-04',89.99,'404 Chestnut Court, Spruce Valley','78901','2025-01-08 15:00:00',NULL),(8,8,'2025-01-03',249.99,'505 Walnut Street, Fir Town','89012','2025-01-07 13:00:00','Include a gift receipt'),(9,9,'2025-01-02',39.99,'606 Elm Avenue, Pinewood','90123','2025-01-06 17:00:00',NULL),(10,10,'2025-01-01',599.99,'707 Maple Boulevard, Cypress City','01234','2025-01-05 09:00:00','Rush delivery, please!');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_category_id` int NOT NULL,
  `name` varchar(80) NOT NULL,
  `details` text NOT NULL,
  `is_available` tinyint(1) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_product_category_FK` (`product_category_id`),
  CONSTRAINT `product_product_category_FK` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `product_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'Smartphone','High-performance smartphone with 128GB storage',1,699.99),(2,1,'Laptop','15-inch laptop with 16GB RAM and 512GB SSD',1,1199.99),(3,1,'Wireless Earbuds','Bluetooth 5.0 earbuds with noise cancellation',1,149.99),(4,1,'4K Monitor','27-inch 4K UHD monitor with HDR support',1,349.99),(5,1,'Gaming Console','Next-gen gaming console with 1TB storage',0,499.99),(6,2,'Men T-Shirt','Cotton t-shirt available in various colors',1,19.99),(7,2,'Women Jeans','Skinny fit jeans with stretch fabric',1,49.99),(8,2,'Winter Jacket','Waterproof winter jacket with a hood',1,89.99),(9,2,'Sneakers','Comfortable sneakers for everyday wear',1,59.99),(10,2,'Baseball Cap','Adjustable cap with embroidered logo',1,14.99),(11,3,'Refrigerator','Energy-efficient refrigerator with 300L capacity',1,599.99),(12,3,'Washing Machine','Front-loading washing machine with 8kg capacity',1,499.99),(13,3,'Microwave Oven','800W microwave oven with multiple presets',1,99.99),(14,3,'Air Conditioner','Split AC with inverter technology',1,799.99),(15,3,'Vacuum Cleaner','Cordless vacuum cleaner with HEPA filter',1,149.99),(16,4,'Fiction Novel','Bestselling novel with captivating story',1,12.99),(17,4,'Cookbook','Recipes from around the world',1,24.99),(18,4,'Self-Help Book','Motivational guide to personal growth',1,14.99),(19,4,'Children Books','Illustrated book for kids aged 5-8',1,9.99),(20,4,'Graphic Novel','Popular graphic novel series volume 1',1,19.99),(21,5,'Action Figure','Articulated action figure with accessories',1,29.99),(22,5,'Board Game','Strategy board game for 2-4 players',1,39.99),(23,5,'Dollhouse','Wooden dollhouse with furniture set',1,89.99),(24,5,'Puzzle Set','1000-piece puzzle featuring a scenic view',1,14.99),(25,5,'Remote Control Car','Rechargeable RC car with high speed',1,49.99);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (1,'Electronics'),(2,'Clothing'),(3,'Home Appliances'),(4,'Books'),(5,'Toys'),(6,'Sports Equipment'),(7,'Groceries');
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_to_order`
--

DROP TABLE IF EXISTS `product_to_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_to_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `order_id` int NOT NULL,
  `number_of_items` int NOT NULL,
  `total_price` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_to_order_order_FK` (`order_id`),
  KEY `product_to_order_product_FK` (`product_id`),
  CONSTRAINT `product_to_order_order_FK` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE,
  CONSTRAINT `product_to_order_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE,
  CONSTRAINT `product_to_order_chk_1` CHECK ((`number_of_items` >= 0)),
  CONSTRAINT `product_to_order_chk_2` CHECK ((`total_price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_to_order`
--

LOCK TABLES `product_to_order` WRITE;
/*!40000 ALTER TABLE `product_to_order` DISABLE KEYS */;
INSERT INTO `product_to_order` VALUES (1,1,1,1,699.99),(2,2,1,1,1199.99),(3,3,2,2,299.98),(4,7,3,3,59.97),(5,14,4,1,199.99),(6,11,5,1,49.99),(7,17,6,5,74.95),(8,21,7,1,29.99);
/*!40000 ALTER TABLE `product_to_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `customer_products_in_order`
--

/*!50001 DROP VIEW IF EXISTS `customer_products_in_order`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customer_products_in_order` AS select `c`.`first_name` AS `customer_first_name`,`c`.`last_name` AS `customer_last_name`,`p`.`name` AS `product_name`,`pto`.`number_of_items` AS `number_of_items` from (((`customer` `c` join (select `o`.`id` AS `id`,`o`.`customer_id` AS `customer_id` from `order` `o`) `o` on((`c`.`id` = `o`.`customer_id`))) join (select `pto`.`order_id` AS `order_id`,`pto`.`product_id` AS `product_id`,`pto`.`number_of_items` AS `number_of_items` from `product_to_order` `pto`) `pto` on((`o`.`id` = `pto`.`order_id`))) join `product` `p` on((`pto`.`product_id` = `p`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `most_ordered_products`
--

/*!50001 DROP VIEW IF EXISTS `most_ordered_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `most_ordered_products` AS select `p`.`name` AS `product_name`,sum(`pto`.`number_of_items`) AS `total_ordered` from (`product_to_order` `pto` join `product` `p` on((`pto`.`product_id` = `p`.`id`))) group by `p`.`name` order by `total_ordered` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-17 16:40:48
