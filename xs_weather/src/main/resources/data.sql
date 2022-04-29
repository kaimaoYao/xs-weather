CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `paswd` varchar(512) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`paswd`) values 
(1,'story','12345678');

/*Table structure for table `weather` */

DROP TABLE IF EXISTS `weather`;

CREATE TABLE `weather` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `region_code` varchar(16) DEFAULT '' COMMENT '地区编码',
  `region_name` varchar(32) DEFAULT '' COMMENT '地区名称',
  `weather_data` varchar(1024) DEFAULT '' COMMENT '天气',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
