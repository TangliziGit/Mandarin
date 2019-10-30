-- MySQL dump 10.13  Distrib 8.0.15, for Linux (x86_64)
--
-- Host: localhost    Database: mandarin
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `FKgm8oqrkr94uj3rv5w9rbq97s2` (`isbn`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'9780393351378'),(2,'9780393351378'),(3,'9780132856201'),(4,'9780134177298'),(5,'9780134177298'),(6,'9780262035613'),(7,'9780262035613');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_description`
--

DROP TABLE IF EXISTS `book_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `book_description` (
  `isbn` varchar(30) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `cover_url` text,
  `location` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish_year` int(11) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `FKh12j9vhhqw3s205s4ahfu27h7` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_description`
--

LOCK TABLES `book_description` WRITE;
/*!40000 ALTER TABLE `book_description` DISABLE KEYS */;
INSERT INTO `book_description` VALUES ('9780393351378','Kip Thorne','http://106.13.1.40:8081/img/s27824536.jpg','Floor 2 - Shelf 1',24.95,2014,'W. W. Norton & Company','A journey through the otherworldly science behind Christopher Nolan’s highly anticipated film, Interstellar, from executive producer and theoretical physicist Kip Thorne.','The Science of Interstellar',1),('9780132856201','James F. Kurose / Keith W. Ross','http://106.13.1.40:8081/img/s24966915.jpg','Floor 1 - Shelf 2',171.6,2012,'Pearson','Computer Networking continues with an early emphasis on application-layer paradigms and application programming interfaces (the top layer), encouraging a hands-on experience with protocols and networking concepts, before working down the protocol stack to more abstract layers.','Computer Networking',2),('9780134177298','Cay S. Horstmann','http://106.13.1.40:8081/img/s28069282.jpg','Floor 2 - Shelf 3',59.99,2016,'Prentice Hall','Core Java® has long been recognized as the leading, no-nonsense tutorial and reference for experienced programmers who want to write robust Java code for real-world applications. Now, Core Java®, Volume II—Advanced Topics, Tenth Edition, has been extensively updated to reflect the most eagerly awaited and innovative version of Java in years: Java SE 8. Rewritten and reorganized...Core Java® has long been recognized as the leading, no-nonsense tutorial and reference for experienced programmers who want to write robust Java code for real-world applications. Now, Core Java®, Volume II—Advanced Topics, Tenth Edition, has been extensively updated to reflect the most eagerly awaited and innovative version of Java in years: Java SE 8. Rewritten and reorganized to illuminate powerful new Java features, idioms, and best practices for enterprise and desktop development, it contains hundreds of up-to-date example programs—all carefully crafted for easy understanding and practical applicability.Writing for serious programmers solving real-world problems, Cay Horstmann deepens your understanding of today’s Java language and library. In this second of two updated volumes, he offers in-depth coverage of expert-level topics including the new Streams API and date/time/calendar library, advanced Swing, security, code processing, and more. This guide will help youUse the new Streams library to process collections more flexibly and efficientlyEfficiently access files and directories, read/write binary or text data, and serialize objectsWork with Java SE 8’s regular expression packageMake the most of XML in Java: parsing, validation, XPath, document generation, XSL, and moreEfficiently connect Java programs to network servicesProgram databases with JDBC 4.2Elegantly overcome date/time programming complexities with the new java.time APIWrite internationalized programs with localized dates/times, numbers, text, and GUIsProcess code with the scripting API, compiler API, and annotation processorsEnforce security via class loaders, bytecode verification, security managers, permissions, user authentication, digital signatures, code signing, and encryptionMaster advanced Swing components for lists, tables, trees, text, and progress indicatorsProduce high-quality drawings with the Java 2D APIUse JNI native methods to leverage code in other languagesIf you’re an experienced programmer moving to Java SE 8, Core Java®, Tenth Edition, will be your reliable, practical companion—now and for many years to come.Look for the companion volume, Core Java®, Volume I—Fundamentals, Tenth Edition (ISBN-13: 978-0-13-417730-4), for foundational coverage of Java 8 language concepts, UI programming, objects, generics, collections, lambda expressions, concurrency, functional programming, and more.Cay S. Horstmann is author of Core Java ® for the Impatient (2015), Java SE 8 for the Really Impatient (2014), and Scala for the Impatient (2012), all from Addison-Wesley. He has written more than a dozen other books for professional programmers and computer science students. He is a professor of computer science at San Jose State University and is a Java Champion.','Core Java, Volume II (10th Edition) : Advanced Features',2),('9780262035613','Ian Goodfellow / Yoshua Bengio / Aaron Courville','http://106.13.1.40:8081/img/s29133163.jpg','Floor 2 - Shelf 4',72,2016,'The MIT Press','Written by three experts in the field, Deep Learning is the only comprehensive book on the subject.\\\" -- Elon Musk, co-chair of OpenAI; co-founder and CEO of Tesla and SpaceXDeep learning is a form of machine learning that enables computers to learn from experience and understand the world in terms of a hierarchy of concepts. Because the computer gathers knowledge from experie...\\\"Written by three experts in the field, Deep Learning is the only comprehensive book on the subject.\\\" -- Elon Musk, co-chair of OpenAI; co-founder and CEO of Tesla and SpaceXDeep learning is a form of machine learning that enables computers to learn from experience and understand the world in terms of a hierarchy of concepts. Because the computer gathers knowledge from experience, there is no need for a human computer operator to formally specify all the knowledge that the computer needs. The hierarchy of concepts allows the computer to learn complicated concepts by building them out of simpler ones; a graph of these hierarchies would be many layers deep. This book introduces a broad range of topics in deep learning.The text offers mathematical and conceptual background, covering relevant concepts in linear algebra, probability theory and information theory, numerical computation, and machine learning. It describes deep learning techniques used by practitioners in industry, including deep feedforward networks, regularization, optimization algorithms, convolutional networks, sequence modeling, and practical methodology; and it surveys such applications as natural language processing, speech recognition, computer vision, online recommendation systems, bioinformatics, and videogames. Finally, the book offers research perspectives, covering such theoretical topics as linear factor models, autoencoders, representation learning, structured probabilistic models, Monte Carlo methods, the partition function, approximate inference, and deep generative models.Deep Learning can be used by undergraduate or graduate students planning careers in either industry or research, and by software engineers who want to begin using deep learning in their products or platforms. A website offers supplementary material for both readers and instructors.Ian Goodfellow is Research Scientist at OpenAI. Yoshua Bengio is Professor of Computer Science at the Université de Montréal. Aaron Courville is Assistant Professor of Computer Science at the Université de Montréal.','Deep Learning : Adaptive Computation and Machine Learning series',2);
/*!40000 ALTER TABLE `book_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowing_fine_history`
--

DROP TABLE IF EXISTS `borrowing_fine_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrowing_fine_history` (
  `fine_id` int(11) NOT NULL AUTO_INCREMENT,
  `find_end_time` datetime DEFAULT NULL,
  `find_start_time` datetime NOT NULL,
  `paid` bit(1) NOT NULL,
  `borrowing_history_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`fine_id`),
  KEY `FKd8btcmpcvm4u63401njjf6cof` (`borrowing_history_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowing_fine_history`
--

LOCK TABLES `borrowing_fine_history` WRITE;
/*!40000 ALTER TABLE `borrowing_fine_history` DISABLE KEYS */;
INSERT INTO `borrowing_fine_history` VALUES (1,NULL,'2019-10-20 15:18:53',_binary '\0',1),(2,NULL,'2019-10-20 15:18:53',_binary '\0',2);
/*!40000 ALTER TABLE `borrowing_fine_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowing_history`
--

DROP TABLE IF EXISTS `borrowing_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrowing_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrowing_end_time` datetime DEFAULT NULL,
  `borrowing_start_time` datetime DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `borrowing_fine_history_fine_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdvuybvabjhkpvdxq6ho62c7a8` (`book_id`),
  KEY `FKf8tfg78drlnfiy6cy8c09jb4j` (`borrowing_fine_history_fine_id`),
  KEY `FK8og47hd6iod43w33n4oiy44nc` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowing_history`
--

LOCK TABLES `borrowing_history` WRITE;
/*!40000 ALTER TABLE `borrowing_history` DISABLE KEYS */;
INSERT INTO `borrowing_history` VALUES (1,NULL,'2019-10-20 15:18:53',1,1,3),(2,NULL,'2019-10-20 15:18:53',2,2,3);
/*!40000 ALTER TABLE `borrowing_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(40) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `UK_lroeo5fvfdeg4hpicn4lw7x9b` (`category_name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'science'),(2,'technology');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deleting_history`
--

DROP TABLE IF EXISTS `deleting_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `deleting_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deleting_time` datetime DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpky6rar6x52fiubxq76e8gdrk` (`book_id`),
  KEY `FKkcj9gxgexpna2t03jyk5pxkae` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deleting_history`
--

LOCK TABLES `deleting_history` WRITE;
/*!40000 ALTER TABLE `deleting_history` DISABLE KEYS */;
INSERT INTO `deleting_history` VALUES (1,'2019-10-20 15:18:53',1,1);
/*!40000 ALTER TABLE `deleting_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8qxqvv9oy66uuotjtfvqu1fxy` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
INSERT INTO `income` VALUES (1,300,'2019-10-30 15:18:52','DEPOSIT',3),(2,300,'2019-10-30 15:18:53','DEPOSIT',4);
/*!40000 ALTER TABLE `income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `date` datetime DEFAULT NULL,
  `title` text NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`news_id`),
  KEY `FK4538gbwfa03nwr9edl3fdloo9` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'<p><br />\r\nOn October 25, 2019, all party members of the party branch of the library&#39;s reader service department held a party class themed &quot;never forget the original intention and keep the mission in mind&quot; in the party member activity room of the library. Li caixiang, leader of the second tour group of the school&#39;s theme education &quot;never forget the original intention and keep the mission in mind&quot;, attended the party class.</p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><img alt=\"\" src=\"https://portalfiles.nwpu.edu.cn/resources/image/2/1572436247802_3fb16c62-3497-39fd-20b0-a242f8e62d2d.JPG\" style=\"height:600px; width:800px\" /></p>\r\n\r\n<p><br />\r\nHe huiru, deputy secretary and deputy director of the general party branch of the library, made a profound party lesson education of &quot;never forget the original intention to undertake the mission and fulfill the purpose to make contributions&quot; with the title of &quot;never forget the original intention and keep the mission in mind&quot;. He Huiru from the party&#39;s nature and the party&#39;s purpose, its characteristics, its development course, beginner&#39;s mind and the mission of our party to do the thorough elaboration, combined with the theme &quot;don&#39;t forget the beginner&#39;s mind, keep in mind that the mission&quot; education, the thought of socialism with Chinese characteristics in jinping new era as a guide, tells the story of how to bear the mission, bear, at the same time with practice to find gaps and improvement measures are put forward.</p>\r\n\r\n<p><br />\r\n<img alt=\"\" src=\"https://portalfiles.nwpu.edu.cn/resources/image/2/1572436264181_c42fafe2-e5a5-baa9-29b7-c9910c706fe4.JPG\" style=\"height:600px; width:800px\" /></p>\r\n\r\n<p><br />\r\nWu lixin, secretary of the party branch of the reader service department, took the party lesson with the title of &quot;studying together the work regulations of the party branch of the communist party of China&quot;. All the party members of the branch studied the work regulations of the communist party branch of the People&#39;s Republic of China again seriously. According to the actual work of the branch, the main points of the branch&#39;s work were explained one by one.</p>\r\n\r\n<p><br />\r\nTo carry out education on the theme of &quot;never forgetting our original intention and keeping our mission in mind&quot; throughout the party is to equip our minds with the party&#39;s innovative theories and encourage the whole party to make unremitting efforts to realize the party&#39;s historical mission in the new era more consciously. We should carry out the whole process of theme education by &quot;keeping the original intention, undertaking the mission, finding the gap and carrying it out&quot;. We will firmly grasp and thoroughly study and implement xi jinping thought on socialism with Chinese characteristics for a new era, forge the political character of loyalty, cleanness and responsibility, and unite and lead the Chinese people of all ethnic groups in working together to realize the great dream. This special party course is to carry out the spirit of &quot;never forget the original intention, keep in mind the mission&quot; theme education, and strive to achieve the specific goals of theoretical learning, ideological and political baptism, undertaking, serving the people to solve problems, honesty and honesty as a model, to ensure that the theme education achieved solid results.</p>\r\n','2019-10-30 15:18:53','The party branch of the library reader service department held party classes on the theme of \"never forget the original intention and bear the mission in mind\"',1),(2,'<p>For the further implementation of the national education conference spirit, explore the deepening the reform of digital instructional innovation model, promote new media and the integration of traditional culture, with the development of teaching resources sharing needs, on the morning of November 1, and higher education press office jointly sponsored digital textbook construction and publishing communication meeting. The specific matters are hereby notified as follows:</p>\r\n\r\n<p><strong>I. meeting time</strong></p>\r\n\r\n<p>9:00 am, Thursday, November 1, 2018</p>\r\n\r\n<p><br />\r\n<strong>Ii. Venue of the meeting</strong><br />\r\nFriendship campus old library teacher development center C219</p>\r\n\r\n<p><br />\r\n<strong>Iii. Contents of the meeting</strong><br />\r\nDigital course customization and publishing services - create your own online courses</p>\r\n\r\n<p><br />\r\n<strong>Iv. Participants</strong></p>\r\n\r\n<ol>\r\n	<li>The responsible person of digital textbook construction and publishing and team teacher</li>\r\n	<li>Responsible for online course construction and team teachers</li>\r\n	<li>Teachers who are interested in the publication of new textbooks and digital courses</li>\r\n</ol>\r\n\r\n<p>Teachers are welcome to attend the meeting and participate in the discussion!</p>\r\n','2019-10-30 15:18:53',' Notice on holding digital textbook construction and publishing exchange meeting',1);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserving_history`
--

DROP TABLE IF EXISTS `reserving_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reserving_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fetched` bit(1) NOT NULL,
  `reserving_end_time` datetime DEFAULT NULL,
  `reserving_start_time` datetime NOT NULL,
  `book_id` int(11) DEFAULT NULL,
  `reader_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86j2f06c9ckl2le8gdw2igewa` (`book_id`),
  KEY `FK9magv2r11h9sv5fpnc7me0kpn` (`reader_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserving_history`
--

LOCK TABLES `reserving_history` WRITE;
/*!40000 ALTER TABLE `reserving_history` DISABLE KEYS */;
INSERT INTO `reserving_history` VALUES (1,_binary '\0',NULL,'2019-10-20 15:18:53',1,3);
/*!40000 ALTER TABLE `reserving_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'READER'),(2,'LIBRARIAN'),(3,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `setting` (
  `setting_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`setting_id`),
  UNIQUE KEY `UK_bk4oycm648x0ox633r4m22b7d` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES (1,'FINE',1),(2,'PERIOD',30),(3,'DEPOSIT',300);
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password_hash` varchar(40) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `sign_up_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`),
  UNIQUE KEY `UK_4bgmpi98dylab6qdvf9xyaxu4` (`phone_number`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'librarian@mandarin.com','librarian','passwd','18681941718','2019-10-30 15:18:52'),(2,'admin@mandarin.com','admin','passwd','18681941717','2019-10-30 15:18:52'),(3,'reader1@mandarin.com','reader1','passwd','18681941716','2019-10-30 15:18:52'),(4,'reader2@mandarin.com','reader2','passwd','18681941715','2019-10-30 15:18:52');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,2),(2,3),(3,1),(4,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-30 15:24:59
