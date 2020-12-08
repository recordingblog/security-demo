/*
SQLyog Ultimate
MySQL - 5.5.27 : Database - imooc-demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `persistent_logins` */

CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `series` varchar(64) COLLATE utf8_bin NOT NULL,
  `token` varchar(64) COLLATE utf8_bin NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `persistent_logins` */

insert  into `persistent_logins`(`username`,`series`,`token`,`last_used`) values ('ltp','iwjV9nmo8MsFmBsHBfdeow==','tVj9XC2vI//4Lsqrvs3igg==','2018-09-26 15:08:55');

/*Table structure for table `zhushan_userconnection` */

CREATE TABLE `zhushan_userconnection` (
  `userId` varchar(255) COLLATE utf8_bin NOT NULL,
  `providerId` varchar(255) COLLATE utf8_bin NOT NULL,
  `providerUserId` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profileUrl` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `imageUrl` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `accessToken` varchar(512) COLLATE utf8_bin NOT NULL,
  `secret` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `refreshToken` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `zhushan_userconnection` */

insert  into `zhushan_userconnection`(`userId`,`providerId`,`providerUserId`,`rank`,`displayName`,`profileUrl`,`imageUrl`,`accessToken`,`secret`,`refreshToken`,`expireTime`) values ('admin','callback.do','0EFB2B7DA07CF47C6FCD8224138E15E6',1,'简单',NULL,'http://thirdqq.qlogo.cn/qqapp/101509209/0EFB2B7DA07CF47C6FCD8224138E15E6/40','871F13AE47E3A135C8A9C98280F4DA52',NULL,'CD0ABD91FC56244963A22EEFCEE846AF',1549436646119);
insert  into `zhushan_userconnection`(`userId`,`providerId`,`providerUserId`,`rank`,`displayName`,`profileUrl`,`imageUrl`,`accessToken`,`secret`,`refreshToken`,`expireTime`) values ('myusers','callback.do','0EFB2B7DA07CF47C6FCD8224138E15E6',1,'简单',NULL,'http://thirdqq.qlogo.cn/qqapp/101509209/0EFB2B7DA07CF47C6FCD8224138E15E6/40','871F13AE47E3A135C8A9C98280F4DA52',NULL,'CD0ABD91FC56244963A22EEFCEE846AF',1549436584641);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
