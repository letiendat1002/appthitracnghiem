-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: dbquiz
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `enrollment_answers`
--

DROP TABLE IF EXISTS `enrollment_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollment_answers` (
  `ENROLLMENT_ANSWER_ID` bigint NOT NULL AUTO_INCREMENT,
  `ENROLLMENT_ID` bigint NOT NULL,
  `QUESTION_ID` bigint NOT NULL,
  `QUESTION_ANSWER_ID` bigint NOT NULL,
  PRIMARY KEY (`ENROLLMENT_ANSWER_ID`),
  KEY `ENROLLMENTID_FK_idx` (`ENROLLMENT_ID`),
  KEY `QUESTIONID_FK_idx` (`QUESTION_ID`),
  KEY `QUESTIONANSWERID_FK_idx` (`QUESTION_ANSWER_ID`),
  CONSTRAINT `ENROLLMENTID_FK` FOREIGN KEY (`ENROLLMENT_ID`) REFERENCES `enrollments` (`ENROLLMENT_ID`),
  CONSTRAINT `QUESTIONANSWERID_FK` FOREIGN KEY (`QUESTION_ANSWER_ID`) REFERENCES `questiopn_answers` (`QUESTIOPN_ANSWER_ID`),
  CONSTRAINT `QUESTIONID_FK2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `questions` (`QUESTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_answers`
--

LOCK TABLES `enrollment_answers` WRITE;
/*!40000 ALTER TABLE `enrollment_answers` DISABLE KEYS */;
INSERT INTO `enrollment_answers` VALUES (1,1,1,1);
/*!40000 ALTER TABLE `enrollment_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollments` (
  `ENROLLMENT_ID` bigint NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint NOT NULL,
  `ROOM_ID` bigint NOT NULL,
  `SCORE` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ENROLLMENT_ID`),
  KEY `USERID_FK_idx` (`USER_ID`),
  KEY `ROOMID_FK_idx` (`ROOM_ID`),
  CONSTRAINT `ROOMID_FK` FOREIGN KEY (`ROOM_ID`) REFERENCES `rooms` (`ROOM_ID`),
  CONSTRAINT `USERID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
INSERT INTO `enrollments` VALUES (1,1,1,50);
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_questions`
--

DROP TABLE IF EXISTS `exam_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_questions` (
  `EXAM_QUESTION_ID` bigint NOT NULL,
  `EXAM_ID` bigint NOT NULL,
  `QUESTION_ID` bigint NOT NULL,
  PRIMARY KEY (`EXAM_QUESTION_ID`),
  KEY `QUESTIONID_FK_idx` (`QUESTION_ID`),
  KEY `EXAMID_FK_idx` (`EXAM_ID`),
  CONSTRAINT `EXAMID_FK1` FOREIGN KEY (`EXAM_ID`) REFERENCES `exams` (`EXAM_ID`),
  CONSTRAINT `QUESTIONID_FK1` FOREIGN KEY (`QUESTION_ID`) REFERENCES `questions` (`QUESTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_questions`
--

LOCK TABLES `exam_questions` WRITE;
/*!40000 ALTER TABLE `exam_questions` DISABLE KEYS */;
INSERT INTO `exam_questions` VALUES (1,1,1);
/*!40000 ALTER TABLE `exam_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `EXAM_ID` bigint NOT NULL AUTO_INCREMENT,
  `SUBJECT_ID` bigint NOT NULL,
  `TITLE` varchar(50) NOT NULL,
  `TOTAL_QUESTION` int NOT NULL DEFAULT '0',
  `TOTAL_SCORE` int NOT NULL DEFAULT '0',
  `SCORE_PER_QUESTION` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`EXAM_ID`),
  KEY `SUBJECTID_FK_idx` (`SUBJECT_ID`),
  CONSTRAINT `SUBJECTID_FK` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `subjects` (`SUBJECT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,1,'desciption',10,100,10);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_answers`
--

DROP TABLE IF EXISTS `question_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_answers` (
  `QUESTION_ANSWER_ID` bigint NOT NULL AUTO_INCREMENT,
  `QUESTION_ID` bigint NOT NULL,
  `SUBJECT_ID` bigint NOT NULL,
  `IS_CORRECT` bit(1) NOT NULL DEFAULT b'0',
  `CONTENT` text,
  PRIMARY KEY (`QUESTION_ANSWER_ID`),
  KEY `QUESTIONID_FK_idx` (`QUESTION_ID`),
  KEY `SUBJECTID_FK_idx` (`SUBJECT_ID`),
  CONSTRAINT `QUESTIONID_FK3` FOREIGN KEY (`QUESTION_ID`) REFERENCES `questions` (`QUESTION_ID`),
  CONSTRAINT `SUBJECTID_FK3` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `subjects` (`SUBJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_answers`
--

LOCK TABLES `question_answers` WRITE;
/*!40000 ALTER TABLE `question_answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `QUESTION_ID` bigint NOT NULL AUTO_INCREMENT,
  `SUBJECT_ID` bigint NOT NULL,
  `LEVEL` int NOT NULL DEFAULT '1',
  `CONTENT` text,
  PRIMARY KEY (`QUESTION_ID`),
  KEY `SUBJECTID_FK_idx` (`SUBJECT_ID`),
  CONSTRAINT `SUBJECTID_FK1` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `subjects` (`SUBJECT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,1,1,'Vong 1');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questiopn_answers`
--

DROP TABLE IF EXISTS `questiopn_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questiopn_answers` (
  `QUESTIOPN_ANSWER_ID` bigint NOT NULL AUTO_INCREMENT,
  `QUESTION_ID` bigint NOT NULL,
  `SUBJECT_ID` bigint NOT NULL,
  `IS_CORRECT` bit(1) NOT NULL DEFAULT b'0',
  `CONTENT` text,
  PRIMARY KEY (`QUESTIOPN_ANSWER_ID`),
  KEY `QUESTIONID_FK_idx` (`QUESTION_ID`),
  KEY `SUBJECTID_FK_idx` (`SUBJECT_ID`),
  CONSTRAINT `QUESTIONID_FK` FOREIGN KEY (`QUESTION_ID`) REFERENCES `questions` (`QUESTION_ID`),
  CONSTRAINT `SUBJECTID_FK2` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `subjects` (`SUBJECT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questiopn_answers`
--

LOCK TABLES `questiopn_answers` WRITE;
/*!40000 ALTER TABLE `questiopn_answers` DISABLE KEYS */;
INSERT INTO `questiopn_answers` VALUES (1,1,1,_binary '','Do vui');
/*!40000 ALTER TABLE `questiopn_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `ROOM_ID` bigint NOT NULL AUTO_INCREMENT,
  `EXAM_ID` bigint NOT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  `TITLE` varchar(50) NOT NULL,
  `TIME_LIMIT` int NOT NULL DEFAULT '0',
  `IS_AVAILABLE` bit(1) NOT NULL DEFAULT b'0',
  `CONTENT` text,
  PRIMARY KEY (`ROOM_ID`),
  KEY `EXAMID_FK_idx` (`EXAM_ID`),
  CONSTRAINT `EXAMID_FK2` FOREIGN KEY (`EXAM_ID`) REFERENCES `exams` (`EXAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `ROOM_ID` bigint NOT NULL AUTO_INCREMENT,
  `EXAM_ID` bigint NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `TITLE` text,
  `TIME_LIMIT` int NOT NULL DEFAULT '0',
  `IS_AVAILABLE` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ROOM_ID`),
  KEY `EXAMID_FK_idx` (`EXAM_ID`),
  CONSTRAINT `EXAMID_FK` FOREIGN KEY (`EXAM_ID`) REFERENCES `exams` (`EXAM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,1,'e807f1fcf82d132f9bb018ca6738a19f','decription1',10,_binary '');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `subject_id` bigint NOT NULL,
  `subject_name` varchar(50) NOT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `SUBJECT_ID` bigint NOT NULL AUTO_INCREMENT,
  `SUBJECT_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`SUBJECT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'Toan');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `USER_ID` bigint NOT NULL,
  `FULL_NAME` varchar(50) NOT NULL,
  `DATE_OF_BIRTH` datetime NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `PASSWORD_HASH` varchar(50) NOT NULL,
  `IS_HOST` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `FULL_NAME_UNIQUE` (`FULL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Gia Huy','0000-00-00 00:00:00','123@gmail.com','1edf9b9d4dd03c04d6557c3fae4bacdc',_binary '');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-18  0:08:26
