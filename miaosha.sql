/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.40 : Database - miaosha
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`miaosha` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `miaosha`;

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) NOT NULL DEFAULT '',
  `price` double(10,2) NOT NULL DEFAULT '0.00',
  `decription` varchar(255) NOT NULL DEFAULT '',
  `sales` int(255) NOT NULL DEFAULT '0',
  `img_url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `item` */

insert  into `item`(`id`,`title`,`price`,`decription`,`sales`,`img_url`) values (1,'iPhone XR',5099.00,'好用的苹果手机',9,'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3112123992,3786781667&fm=26&gp=0.jpg'),(8,'iPhone XS',10099.00,'最好用的手机',4,'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2928984556,3586971847&fm=26&gp=0.jpg'),(9,'iPhone 11',5499.00,'最新款iPhone',4,'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1364216034,1838640983&fm=26&gp=0.jpg'),(11,'iPad',3099.00,'最好用的iPad',4,'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2164524140,1554288532&fm=26&gp=0.jpg');

/*Table structure for table `item_stock` */

DROP TABLE IF EXISTS `item_stock`;

CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_item_id` (`item_id`),
  CONSTRAINT `fk_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `item_stock` */

insert  into `item_stock`(`id`,`item_id`,`stock`) values (1,1,91),(8,8,96),(9,9,96),(11,11,96);

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` varchar(32) NOT NULL COMMENT '订单号',
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_price` double NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '0',
  `order_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `order_info` */

insert  into `order_info`(`id`,`user_id`,`item_id`,`item_price`,`amount`,`order_price`) values ('201909292800002800',4,1,5099,1,5099),('201909292900002900',4,1,5099,1,5099);

/*Table structure for table `promo` */

DROP TABLE IF EXISTS `promo`;

CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `promo_name` varchar(255) NOT NULL,
  `promo_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_id_promo` (`item_id`),
  CONSTRAINT `fk_item_id_in_promo` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_item_id_promo` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `promo` */

insert  into `promo`(`id`,`item_id`,`start_time`,`end_time`,`promo_name`,`promo_price`) values (1,1,'2019-09-29 00:35:19','2020-12-01 16:03:29','女生节限时秒杀','4899.00'),(2,8,'2019-09-29 00:55:19','2019-11-03 16:04:18','女生节限时秒杀','4899.00'),(3,9,'2019-11-02 16:05:50','2019-11-05 16:05:54','女生节限时秒杀','4899.00');

/*Table structure for table `sequence_info` */

DROP TABLE IF EXISTS `sequence_info`;

CREATE TABLE `sequence_info` (
  `name` varchar(64) NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sequence_info` */

insert  into `sequence_info`(`name`,`current_value`,`step`) values ('order_info',30,1);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `gender` int(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '用户性别0(默认) 1(男性) 2(女性)',
  `age` int(3) NOT NULL DEFAULT '0' COMMENT '年龄',
  `telphone` varchar(15) NOT NULL COMMENT '手机号',
  `register_mode` varchar(255) NOT NULL DEFAULT '' COMMENT '注册方式',
  `third_party_id` varchar(64) NOT NULL DEFAULT '' COMMENT '第三方账号id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_telphone` (`telphone`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`name`,`gender`,`age`,`telphone`,`register_mode`,`third_party_id`) values (1,'安琪拉',1,16,'123456789123','byWeChat','anqila'),(4,'孙尚香',2,18,'12345678910','byTelPhone',''),(6,'妲己',2,16,'15556227203','byTelPhone',''),(9,'大小姐',2,11,'11111111111','byTelPhone',''),(10,'22222',2,22,'22222222222','byTelPhone',''),(11,'1111111',2,11,'12345678901','byTelPhone',''),(12,'1111111',2,33,'33333333333','byTelPhone',''),(13,'444444',2,44,'44444444444','byTelPhone','');

/*Table structure for table `user_password` */

DROP TABLE IF EXISTS `user_password`;

CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pwd_user` (`user_id`),
  CONSTRAINT `fk_pwd_user` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_password` */

insert  into `user_password`(`id`,`encrpt_password`,`user_id`) values (1,'123456',1),(2,'4QrcOUm6Wau+VuBX8g+IPg==',4),(4,'4QrcOUm6Wau+VuBX8g+IPg==',6),(6,'lueSGJZetyySpUndWjMBEg==',9),(7,'4861iBoKH9qtASltdVSGjQ==',10),(8,'lueSGJZetyySpUndWjMBEg==',11),(9,'GhANLA2rGcRDDn1zdis0Iw==',12),(10,'c4gqsfpSnXJz2g22tJzE8w==',13);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
