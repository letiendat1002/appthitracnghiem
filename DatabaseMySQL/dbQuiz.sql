-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema dbQuiz
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbQuiz
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbQuiz` DEFAULT CHARACTER SET utf8mb4 ;
USE `dbQuiz` ;

-- -----------------------------------------------------
-- Table `dbQuiz`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`users` (
  `user_id` VARCHAR(50) NOT NULL,
  `full_name` VARCHAR(50) NOT NULL,
  `password_hash` VARCHAR(50) NOT NULL,
  `is_host` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`exams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`exams` (
  `exam_id` BIGINT NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(50) NULL DEFAULT NULL,
  `total_question` INT NOT NULL DEFAULT 0,
  `total_score` INT NOT NULL DEFAULT 0,
  `score_per_question` DOUBLE NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`exam_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`rooms` (
  `room_id` BIGINT NOT NULL AUTO_INCREMENT,
  `exam_id` BIGINT NULL DEFAULT NULL,
  `title` VARCHAR(50) NOT NULL,
  `time_limit` INT NOT NULL DEFAULT 0,
  `password` VARCHAR(50) NULL DEFAULT NULL,
  `is_available` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`room_id`),
  INDEX `examid_fk_idx` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `examid_fk`
    FOREIGN KEY (`exam_id`)
    REFERENCES `dbQuiz`.`exams` (`exam_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`questions` (
  `question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `exam_id` BIGINT NOT NULL,
  `level` INT NOT NULL DEFAULT 1,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `fk_questions_exams1_idx` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `fk_questions_exams1`
    FOREIGN KEY (`exam_id`)
    REFERENCES `dbQuiz`.`exams` (`exam_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`question_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`question_answers` (
  `question_answer_id` BIGINT NOT NULL AUTO_INCREMENT,
  `question_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `is_correct` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`question_answer_id`),
  INDEX `questionid_fk_idx` (`question_id` ASC) VISIBLE,
  CONSTRAINT `questionid_fk`
    FOREIGN KEY (`question_id`)
    REFERENCES `dbQuiz`.`questions` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`enrollments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`enrollments` (
  `enrollment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(50) NOT NULL,
  `room_id` BIGINT NOT NULL,
  `score` DOUBLE NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`enrollment_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbQuiz`.`enrollment_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbQuiz`.`enrollment_answers` (
  `enrollment_answer_id` BIGINT NOT NULL AUTO_INCREMENT,
  `enrollment_id` BIGINT NOT NULL,
  `question_id` BIGINT NULL DEFAULT NULL,
  `question_answer_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`enrollment_answer_id`),
  INDEX `enrollmentid_fk_idx` (`enrollment_id` ASC) VISIBLE,
  INDEX `questionid_fk_idx` (`question_id` ASC) VISIBLE,
  INDEX `questionanswerid_fk_idx` (`question_answer_id` ASC) INVISIBLE,
  CONSTRAINT `enrollmentid_fk`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `dbQuiz`.`enrollments` (`enrollment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `enrollment_answers_questionid_fk`
    FOREIGN KEY (`question_id`)
    REFERENCES `dbQuiz`.`questions` (`question_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `enrollment_answers_questionanswerid_fk`
    FOREIGN KEY (`question_answer_id`)
    REFERENCES `dbQuiz`.`question_answers` (`question_answer_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
