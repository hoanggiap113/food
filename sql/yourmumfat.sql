-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: foodorder
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,'2025-04-15 03:00:00','2025-04-15 03:00:00'),(2,2,'2025-04-15 03:05:00','2025-04-15 03:05:00'),(3,3,'2025-04-15 03:10:00','2025-04-15 03:10:00'),(4,4,'2025-04-15 03:15:00','2025-04-15 03:15:00'),(5,5,'2025-04-15 03:20:00','2025-04-15 03:20:00');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cart_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_id` (`cart_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,1,1,2,'2025-04-15 03:00:00','2025-04-15 16:48:52',1000000.00),(2,1,3,1,'2025-04-15 03:00:00','2025-04-15 16:48:52',75000.00),(3,1,6,3,'2025-04-15 03:01:00','2025-04-15 16:48:52',60000.00),(4,2,2,1,'2025-04-15 03:05:00','2025-04-15 16:48:52',25000.00),(5,2,5,2,'2025-04-15 03:06:00','2025-04-15 16:48:52',90000.00),(6,3,7,1,'2025-04-15 03:10:00','2025-04-15 16:48:52',25000.00),(7,3,8,2,'2025-04-15 03:11:00','2025-04-15 16:48:52',60000.00),(8,4,10,1,'2025-04-15 03:15:00','2025-04-15 16:48:52',40000.00),(9,4,12,1,'2025-04-15 03:16:00','2025-04-15 16:48:52',55000.00),(10,5,9,2,'2025-04-15 03:20:00','2025-04-15 16:48:52',60000.00),(11,5,14,1,'2025-04-15 03:21:00','2025-04-15 16:48:52',25000.00);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Món Việt','Các món ăn truyền thống Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35'),(2,'Đồ nướng','Các món nướng thơm ngon','2025-04-12 12:06:35','2025-04-12 12:06:35'),(3,'Món chay','Dành cho người ăn chay','2025-04-12 12:06:35','2025-04-12 12:06:35'),(4,'Tráng miệng','Các món ngọt tráng miệng','2025-04-12 12:06:35','2025-04-12 12:06:35'),(5,'Đồ uống đặc biệt','Các loại đồ uống đặc sản','2025-04-12 12:06:35','2025-04-12 12:06:35'),(6,'Đồ ăn nhanh','Các loại đồ ăn nhanh','2025-04-12 12:06:35','2025-04-14 08:56:18'),(7,'Nước ngọt','Các loại nước ngọt','2025-04-12 12:06:35','2025-04-14 08:56:18'),(8,'Nước có ga','Các loại nước có ga phổ biến','2025-04-12 12:06:35','2025-04-14 08:56:18');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,2,50000.00,100000.00),(2,1,3,1,75000.00,75000.00),(3,2,2,3,25000.00,75000.00),(4,2,5,1,45000.00,45000.00),(5,3,4,4,35000.00,140000.00),(6,3,6,2,20000.00,40000.00),(7,4,7,1,25000.00,25000.00),(8,4,8,2,30000.00,60000.00),(9,5,9,3,30000.00,90000.00),(10,6,10,2,40000.00,80000.00),(11,6,11,1,35000.00,35000.00),(12,7,12,1,55000.00,55000.00),(13,7,13,2,20000.00,40000.00),(14,8,14,3,25000.00,75000.00),(15,9,15,4,20000.00,80000.00),(16,10,1,1,50000.00,50000.00),(17,10,2,2,25000.00,50000.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `status` enum('pending','confirmed','delivered','canceled') DEFAULT 'pending',
  `total_price` decimal(10,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `voucher_id` int DEFAULT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(120) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `fk_voucher` (`voucher_id`),
  CONSTRAINT `fk_voucher` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'pending',120000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',NULL,'Van A','Ha NOi',NULL,'0123456789',NULL),(2,2,'confirmed',95000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',1,'Van B','Ha noi',NULL,'0987654321',NULL),(3,3,'delivered',150000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',NULL,'Van C','Ha noi',NULL,'0912345678',NULL),(4,4,'pending',80000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',2,'Van D','Ha noi',NULL,'0978123456',NULL),(5,5,'canceled',65000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',NULL,'Van E','Ha noi',NULL,'0965432187',NULL),(6,1,'confirmed',110000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',3,'Van A','Ha noi',NULL,'0123456789',NULL),(7,2,'delivered',75000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',NULL,'Van B','Ha noi',NULL,'0987654321',NULL),(8,3,'pending',90000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',4,'Your mum','Ha noi',NULL,'0912345678',NULL),(9,4,'confirmed',130000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',NULL,'Skibidi','Ha noi',NULL,'0978123456',NULL),(10,5,'delivered',70000.00,'2025-04-12 12:06:35','2025-04-15 08:04:19',5,'Toilet','Ha noi',NULL,'0965432187',NULL),(13,1,'pending',3000000.00,'2025-04-15 09:26:06','2025-04-15 09:26:06',NULL,'Hoang Giap','Nha so 12 tieu khu Nam Tien','Ca lo nha may','0981609334',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `payment_method` enum('cash','card','online') NOT NULL,
  `payment_status` enum('pending','completed','failed') DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,'card','completed','2025-04-12 12:06:35'),(2,2,'cash','completed','2025-04-12 12:06:35'),(3,3,'online','completed','2025-04-12 12:06:35'),(4,4,'card','pending','2025-04-12 12:06:35'),(5,5,'cash','completed','2025-04-12 12:06:35'),(6,6,'online','completed','2025-04-12 12:06:35'),(7,7,'card','completed','2025-04-12 12:06:35'),(8,8,'cash','pending','2025-04-12 12:06:35'),(9,9,'online','completed','2025-04-12 12:06:35'),(10,10,'card','completed','2025-04-12 12:06:35');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_detail`
--

DROP TABLE IF EXISTS `product_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `status` enum('available','out_of_stock','discontinued') DEFAULT 'available',
  `is_apply` tinyint(1) DEFAULT '0' COMMENT 'TRUE: đang áp dụng, FALSE: không áp dụng',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_applied_product` (`product_id`,`is_apply`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `chk_is_apply` CHECK ((`is_apply` in (true,false)))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_detail`
--

LOCK TABLES `product_detail` WRITE;
/*!40000 ALTER TABLE `product_detail` DISABLE KEYS */;
INSERT INTO `product_detail` VALUES (1,1,50000.00,100,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(2,2,25000.00,50,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(3,3,75000.00,30,'available',0,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(4,4,35000.00,40,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(5,5,45000.00,60,'available',0,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(6,6,20000.00,80,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(7,7,25000.00,120,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(8,8,30000.00,90,'available',0,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(9,9,30000.00,25,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(10,10,40000.00,35,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(11,11,35000.00,45,'available',0,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(12,12,55000.00,20,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(13,13,20000.00,70,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(14,14,25000.00,55,'available',0,'2025-04-12 05:06:35','2025-04-12 05:06:35'),(15,15,20000.00,65,'available',1,'2025-04-12 05:06:35','2025-04-12 05:06:35');
/*!40000 ALTER TABLE `product_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Phở bò','Phở truyền thống với nước dùng đậm đà','https://cachnau.vn/wp-content/uploads/2021/11/cach-nau-pho-bo-tai-ngon.jpg',1,'TRUYEN_THONG'),(2,'Bánh mì pate','Bánh mì Việt Nam với pate đặc biệt','https://blog.dktcdn.net/files/cach-lam-banh-mi-pate-truyen-thong-ngon-de-ban.jpg',1,'FAST_FOOD'),(3,'Gà nướng','Gà nướng thơm ngon với gia vị đặc biệt','https://beptruong.edu.vn/wp-content/uploads/2021/03/ga-nuong-muoi-ot.jpg',2,'NUONG'),(4,'Nem rán','Nem truyền thống giòn rụm','https://cdn.tgdd.vn/2022/10/CookDish/cach-lam-mon-nem-ran-thom-ngon-chuan-vi-don-gian-tai-nha-avt-1200x676.jpg',1,'CHIEN'),(5,'Bún chả','Bún chả Hà Nội đặc sản','https://i-giadinh.vnecdn.net/2023/04/16/Buoc-11-Thanh-pham-11-7068-1681636164.jpg',1,'TRUYEN_THONG'),(6,'Chè đậu đen','Chè ngọt thanh giải nhiệt','https://www.btaskee.com/wp-content/uploads/2021/08/cach-nau-che-dau-den.jpg',4,'CHE'),(7,'Cà phê sữa đá','Cà phê đặc sản Việt Nam','https://ongbi.vn/wp-content/uploads/2023/01/ca-phe-sua-da.jpg',5,'CA_PHE'),(8,'Bia Hà Nội','Bia truyền thống Hà Nội','https://media.vietnamplus.vn/images/7255a701687d11cb8c6bbc58a6c8078563eb3105c40308dc1c31d385f77d53f01406119d326dd6268076d0e853b6566b/beer.jpg',5,'BIA'),(9,'Đậu hũ chiên','Đậu hũ chiên giòn dành cho người chay','https://file.hstatic.net/200000700229/article/cach-chien-dau-hu-bang-noi-chien-khong-dau-1_0dd2155028b946dfb4b8a3247af411e4.jpg',3,'CHAY'),(10,'Bánh xèo','Bánh xèo miền Tây đặc sản','https://www.huongnghiepaau.com/wp-content/uploads/2017/02/cach-lam-banh-xeo-mien-trung.jpg',1,'TRUYEN_THONG'),(11,'Sinh tố bơ','Sinh tố bơ thơm ngon bổ dưỡng','https://caygiongbo.com/datafiles/3/2019-02-24/99271623-sinh-to-bo-de-quoc-bao-lau-trong-tu-lanh-1.jpg',5,'SINH_TO'),(12,'Cơm tấm sườn','Cơm tấm sườn nướng Sài Gòn','https://i-giadinh.vnecdn.net/2024/03/07/7Honthinthnhphm1-1709800144-8583-1709800424.jpg',1,'COM'),(13,'Bánh flan','Bánh flan tráng miệng thơm ngon','https://pandafood.com.vn/wp-content/uploads/2024/09/banh-flan-phan-thiet-1.jpg',4,'TRANG_MIENG'),(14,'Trà đào','Trà đào mát lạnh','https://enjoycoffee.vn/wp-content/uploads/2024/07/z5645937496573_05f3fc8a89c90f8b0ed9149cf0b5d628.jpg',5,'TRA'),(15,'Bánh bao','Bánh bao nhân thịt truyền thống','https://iguide.ai/cdn-cgi/image/width=3840,format=webp,quality=75/https%3A%2F%2Fimg.iguide.ai%2Fmedia%2Fbanh-ngo-delimanjoo-viettuantea_maxresdefault%20(4).webp',1,'HAP');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,1,5,'Phở rất ngon, nước dùng đậm đà','2025-04-12 12:06:35'),(2,2,2,4,'Bánh mì ngon nhưng hơi ít nhân','2025-04-12 12:06:35'),(3,3,3,5,'Gà nướng thơm ngon, gia vị đặc biệt','2025-04-12 12:06:35'),(4,4,4,3,'Nem hơi khô, cần cải thiện','2025-04-12 12:06:35'),(5,5,5,5,'Bún chả ngon đúng điệu Hà Nội','2025-04-12 12:06:35'),(6,1,6,4,'Chè đậu đen ngọt thanh, vừa miệng','2025-04-12 12:06:35'),(7,2,7,5,'Cà phê đậm đà hương vị Việt','2025-04-12 12:06:35'),(8,3,8,3,'Bia ngon nhưng hơi đắng','2025-04-12 12:06:35'),(9,4,9,4,'Đậu hũ chiên giòn, ăn chay rất tốt','2025-04-12 12:06:35'),(10,5,10,5,'Bánh xèo giòn rụm, nhân đầy đủ','2025-04-12 12:06:35');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'admin'),(3,'shipper'),(1,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role_id` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nguyễn Văn A','a@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0123456789','Hà Nội, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(2,'Trần Thị B','b@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0987654321','TP.HCM, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(3,'Lê Văn C','c@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0912345678','Đà Nẵng, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(4,'Phạm Thị D','d@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0978123456','Hải Phòng, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',2),(5,'Hoàng Văn E','e@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0965432187','Nha Trang, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `discount_percent` decimal(5,2) DEFAULT NULL,
  `valid_from` date NOT NULL,
  `valid_to` date NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `vouchers_chk_1` CHECK (((`discount_percent` >= 0) and (`discount_percent` <= 100)))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'VIETFOOD20',20.00,'2025-04-01','2025-06-30','2025-04-12','2025-04-16'),(2,'HOTMEAL15',15.00,'2025-05-01','2025-07-31','2025-04-12','2025-04-16'),(3,'NEWCUS10',10.00,'2025-01-01','2025-12-31','2025-04-12','2025-04-16'),(4,'SPECIAL25',25.00,'2025-04-15','2025-05-15','2025-04-12','2025-04-16'),(5,'WEEKEND30',30.00,'2025-04-01','2025-12-31','2025-04-12','2025-04-16');
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-13 10:37:56
