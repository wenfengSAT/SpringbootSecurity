# Host: 127.0.0.1  (Version 5.7.17-log)
# Date: 2018-09-05 11:59:56
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "resources"
#

DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(255) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "resources"
#

INSERT INTO `resources` VALUES (1,'资源1',0),(2,'资源2',0),(3,'资源3',0);

#
# Structure for table "role_resources"
#

DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `rr_id` int(11) NOT NULL AUTO_INCREMENT,
  `rr_resource_id` int(11) DEFAULT NULL,
  `rr_role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "role_resources"
#

INSERT INTO `role_resources` VALUES (1,1,'1'),(2,2,'1'),(3,3,'1'),(4,2,'2');

#
# Structure for table "roles"
#

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `r_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `r_flag` varchar(50) DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "roles"
#

INSERT INTO `roles` VALUES (1,'超级管理员','ROLE_ADMIN'),(2,'普通用户','ROLE_USER');

#
# Structure for table "user_roles"
#

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ur_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `ur_role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "user_roles"
#

INSERT INTO `user_roles` VALUES (1,1,1),(2,2,2),(3,1,2);

#
# Structure for table "users"
#

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `u_username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `u_password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "users"
#

INSERT INTO `users` VALUES (1,'admin','123456'),(2,'test','123456');
