/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for personalfinances
CREATE DATABASE IF NOT EXISTS `personalfinances` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `personalfinances`;

# -------------- pf_user management -----------------

DROP TABLE IF EXISTS `pf_user`;
CREATE TABLE `pf_user` (
  `id`         INT(12)      NOT NULL,
  `login`      VARCHAR(100) NOT NULL,
  `password`   VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
  `last_name`  VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
  `email`      VARCHAR(128) NOT NULL,
  `phone`      VARCHAR(128) NULL     DEFAULT NULL,
  `icon`       VARCHAR(128)          DEFAULT NULL,
  `last_login` TIMESTAMP    NULL     DEFAULT NULL,
  `enabled`    TINYINT(3)   NOT NULL DEFAULT '1',
  `role`        VARCHAR(12)      NOT NULL,
  `created_at` DATETIME     NOT NULL,
  `changed_by` INT(12)      NOT NULL,
  `changed_at` DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `pf_user_login_uidx` (`login`),
  UNIQUE INDEX `pf_user_email_uidx` (`email`),
  INDEX `pf_user_enabled_idx` (`enabled`),
  INDEX `pf_user_role_idx` (`role`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;


DROP TABLE IF EXISTS `verification_token`;
CREATE TABLE `verification_token` (
  `id`          INT(12)      NOT NULL,
  `user_id`     INT(12)      NOT NULL,
  `token`       VARCHAR(100) NULL,
  `token_type`  TINYINT     NOT NULL,
  `expiry_date` DATETIME     NOT NULL,
  `expired`  TINYINT     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `verification_token_uidx` (`token`),
  INDEX `verification_token_user_id_idx` (`user_id`),
  INDEX `verification_token_type_idx` (`token_type`),
  CONSTRAINT `verification_token_user_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)  ENGINE = InnoDB;

# -------------- category -----------------

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id`          INT(12)      NOT NULL,
  `name`        VARCHAR(128) NOT NULL COLLATE 'utf8_unicode_ci',
  `icon`         VARCHAR(30),
  `color`       VARCHAR(10),
  `description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `parent_id`   INT(12) NOT NULL,
  `user_id`     INT(12)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `category_name_idx` (`name`),
  INDEX `category_parent_id_idx` (`parent_id`),
  INDEX `category_user_idx` (`user_id`),
  CONSTRAINT `category_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

# -------------- account -----------------

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id`              INT(12)     NOT NULL,
  `name`            VARCHAR(128) NOT NULL COLLATE 'utf8_unicode_ci',
  `description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `balance` DECIMAL(12, 2),
  `account_type`      TINYINT     NOT NULL,
  `currency`        VARCHAR(3)  NOT NULL,
  `symbol` VARCHAR(8) NULL COLLATE 'utf8_unicode_ci',
  `active`          BOOLEAN     NOT NULL DEFAULT 1,
  `sort_order`      INT(12)     NOT NULL DEFAULT 0,
  `icon`         VARCHAR(30),
  `color`       VARCHAR(10),
  `user_id`         INT(12)     NOT NULL,
  `created_at`      DATETIME    NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `account_name_idx` (`name`),
  INDEX `account_currency_idx` (`currency`),
  INDEX `account_symbol_idx` (`symbol`),
  INDEX `account_active_idx` (`active`),
  INDEX `account_sort_order_idx` (`sort_order`),
  INDEX `account_user_id_idx` (`user_id`),
  CONSTRAINT `account_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

# -------------- transaction -----------------

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id`               INT(12)        NOT NULL,
  `account_id`       INT(12)        NOT NULL,
  `category_id`      INT(12)        NOT NULL,
  `amount`           DECIMAL(12, 2) NOT NULL,
  `status`           TINYINT        NOT NULL,
  `transaction_type` TINYINT        NOT NULL,
  `transaction_source` TINYINT        NOT NULL,
  `transaction_date` DATETIME       NOT NULL,
  `contact_id`      INT(12) ,
  `description`       VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `user_id`          INT(12)        NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `transaction_account_id_idx` (`account_id`),
  INDEX `transaction_category_id_idx` (`category_id`),
  INDEX `transaction_transaction_type_idx` (`transaction_type`),
  INDEX `transaction_user_id_idx` (`user_id`),
  INDEX `transaction_transaction_date_idx` (`transaction_date`),
  CONSTRAINT `transaction_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `transaction_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `transaction_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
  `id`               INT(12)        NOT NULL,
  `transaction_id`      INT(12)        NOT NULL,
  `target_account_id`       INT(12)        NOT NULL,
  `rate`           DECIMAL(12, 4) NOT NULL,
  `converted_amount`           DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `transfer_id_idx` (`id`),
  INDEX `transfer_transaction_id_idx` (`transaction_id`),
  INDEX `transfer_target_account_id_idx` (`target_account_id`),
  CONSTRAINT `transfer_transaction_id_fk` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`) ON DELETE CASCADE,
  CONSTRAINT `transfer_target_account_id_fk` FOREIGN KEY (`target_account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

# -------------- reminder -----------------
DROP TABLE IF EXISTS `reminder`;
CREATE TABLE `reminder` (
  `id`               INT(12)        NOT NULL,
  `status`           TINYINT        NOT NULL,
  `due_date`   DATETIME       NOT NULL,
  `account_id`       INT(12)        NOT NULL,
  `category_id`      INT(12)        NOT NULL,
  `amount`           DECIMAL(12, 2) NOT NULL,
  `transaction_type` TINYINT        NOT NULL,
  `auto_charge`      TINYINT(1)     NOT NULL,
  `reminder_repeat`           TINYINT     NOT NULL,
  `contact_id`      INT(12) ,
  `description`       VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `user_id`          INT(12)        NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `reminder_status_idx` (`status`),
  INDEX `reminder_due_date_idx` (`due_date`),
  INDEX `reminder_account_id_idx` (`account_id`),
  INDEX `reminder_category_id_idx` (`category_id`),
  INDEX `reminder_transaction_type_idx` (`transaction_type`),
  INDEX `reminder_auto_charge_idx` (`auto_charge`),
  INDEX `reminder_repeat_idx` (`reminder_repeat`),
  INDEX `reminder_user_idx` (`user_id`),
  CONSTRAINT `reminder_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `reminder_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `reminder_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS `reminder_transfer`;
CREATE TABLE `reminder_transfer` (
  `id`               INT(12)        NOT NULL,
  `reminder_id`      INT(12)        NOT NULL,
  `target_account_id`       INT(12)        NOT NULL,
  `rate`           DECIMAL(12, 4) NOT NULL,
  `converted_amount`           DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `transfer_id_idx` (`id`),
  INDEX `reminder_transfer_reminder_id_idx` (`reminder_id`),
  INDEX `reminder_transfer_account_id_idx` (`target_account_id`),
  CONSTRAINT `reminder_transfer_reminder_id_fk` FOREIGN KEY (`reminder_id`) REFERENCES `reminder` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reminder_transfer_target_account_id_fk` FOREIGN KEY (`target_account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS `reminder_transaction`;
CREATE TABLE `reminder_transaction` (
  `reminder_id`      INT(12)        NOT NULL,
  `transaction_id`      INT(12)        NOT NULL,
  PRIMARY KEY (`reminder_id`, `transaction_id`),
  INDEX `reminder_transaction_reminder_id_idx` (`reminder_id`),
  INDEX `reminder_transaction_transaction_id_idx` (`transaction_id`),
  CONSTRAINT `reminder_transaction_reminder_id_fk` FOREIGN KEY (`reminder_id`) REFERENCES `reminder` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reminder_transaction_transaction_id_fk` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`) ON DELETE CASCADE
)
  ENGINE = InnoDB;

# -------------- Events -----------------

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id`           INT(12)  NOT NULL,
  `event_type`   TINYINT   NOT NULL,
  `message`      TEXT     NULL COLLATE 'utf8_unicode_ci',
  `performed_by` INT(12),
  `created_at`   DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `event_event_type_idx` (`event_type`),
  INDEX `event_performed_by_idx` (`performed_by`),
  INDEX `event_created_at_idx` (`created_at`)
)
  COLLATE = 'utf8_unicode_ci'
  ENGINE = InnoDB;

# -------------- Currency -----------------

DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency` (
  `iso_code`   VARCHAR(3)   NOT NULL,
  `name`    VARCHAR (40) COLLATE 'utf8_unicode_ci',
  `symbol` VARCHAR(8) NULL COLLATE 'utf8_unicode_ci',
  PRIMARY KEY (`iso_code`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

# -------------- contact -----------------

DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id`         INT(12)      NOT NULL,
  `name` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
  `email`      VARCHAR(128) DEFAULT NULL,
  `phone`      VARCHAR(128) NULL     DEFAULT NULL,
  `description`       VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `user_id`          INT(12)        NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `contact_name_idx` (`name`),
  INDEX `contact_user_id_idx` (`user_id`),
  CONSTRAINT `contact_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `pf_user` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
