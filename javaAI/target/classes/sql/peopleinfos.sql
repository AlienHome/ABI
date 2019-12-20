CREATE DATABASE `peopleinfosforsb` DEFAULT CHARACTER SET utf8;

USE `peopleinfosforsb`;

DROP TABLE IF EXISTS `peopleinfostable`;
CREATE TABLE `peopleinfostable`(
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) COLLATE utf8_unicode_ci DEFAULT NULL,
    `address` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY(`id`)
)ENGINE=InnoDB CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO peopleinfostable (name, address) VALUES ("xiaohong", "北京");
INSERT INTO peopleinfostable (name, address) VALUES ("xiaohua", "上海");
INSERT INTO peopleinfostable (name, address) VALUES ("小小", "广州");
INSERT INTO peopleinfostable (name, address) VALUES ("天籁", "深圳");
