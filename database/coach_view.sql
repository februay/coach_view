/*
Navicat MySQL Data Transfer

Source Server         : local-coach_view
Source Server Version : 50511
Source Host           : 127.0.0.1:3306
Source Database       : coach_view

Target Server Type    : MYSQL
Target Server Version : 50511
File Encoding         : 65001

Date: 2018-07-10 14:57:23
*/

SET FOREIGN_KEY_CHECKS=0;


CREATE DATABASE IF NOT EXISTS coach_view
default charset utf8mb4 
collate utf8mb4_general_ci;

-- ----------------------------
-- Table structure for `club`
-- ----------------------------
DROP TABLE IF EXISTS `club`;
CREATE TABLE `club` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `club_id` varchar(255) NOT NULL,
  `club_name` varchar(255) DEFAULT NULL,
  `admin_id` varchar(255) DEFAULT NULL COMMENT '管理员uid',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '管理员姓名',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `region` varchar(255) DEFAULT NULL COMMENT '区',
  `county` varchar(255) DEFAULT NULL COMMENT '县',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0：无效用户 1：有效, -1 预分配uid用户',
  `create_time` datetime DEFAULT NULL,
  `activite_time` datetime DEFAULT NULL COMMENT '激活时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`club_id`) USING BTREE,
  UNIQUE KEY `phone` (`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of club
-- ----------------------------

-- ----------------------------
-- Table structure for `match`
-- ----------------------------
DROP TABLE IF EXISTS `match`;
CREATE TABLE `match` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `match_id` varchar(255) NOT NULL,
  `match_name` varchar(255) DEFAULT NULL,
  `match_time` datetime DEFAULT NULL COMMENT '管理员姓名',
  `create_time` datetime DEFAULT NULL,
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match
-- ----------------------------

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` varchar(255) NOT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `club_id` varchar(255) NOT NULL,
  `club_name` varchar(255) DEFAULT NULL,
  `admin_id` varchar(255) DEFAULT NULL COMMENT '管理员uid',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '管理员姓名',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `region` varchar(255) DEFAULT NULL COMMENT '区',
  `county` varchar(255) DEFAULT NULL COMMENT '县',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0：无效用户 1：有效, -1 预分配uid用户',
  `create_time` datetime DEFAULT NULL,
  `activite_time` datetime DEFAULT NULL COMMENT '激活时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`school_id`) USING BTREE,
  UNIQUE KEY `phone` (`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `description` text,
  `order_number` int(11) DEFAULT NULL,
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '10001', 'Admin', 'sys_role_admin', '1', null, '10', '0');
INSERT INTO `sys_role` VALUES ('2', '10002', '俱乐部', 'sys_role_club', '1', null, '20', '0');
INSERT INTO `sys_role` VALUES ('3', '10003', '学校', 'sys_role_school', '1', null, '30', '0');
INSERT INTO `sys_role` VALUES ('4', '10004', '队', 'sys_role_team', '1', null, '40', '0');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`uid`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `team`
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_id` varchar(255) NOT NULL,
  `team_name` varchar(255) DEFAULT NULL,
  `school_id` varchar(255) NOT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `admin_id` varchar(255) DEFAULT NULL COMMENT '管理员uid',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '管理员姓名',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0：无效用户 1：有效, -1 预分配uid用户',
  `create_time` datetime DEFAULT NULL,
  `activite_time` datetime DEFAULT NULL COMMENT '激活时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`school_id`) USING BTREE,
  UNIQUE KEY `phone` (`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team
-- ----------------------------

-- ----------------------------
-- Table structure for `team_member`
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(255) DEFAULT NULL COMMENT '队员id',
  `number` varchar(255) DEFAULT NULL COMMENT '球衣号',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `height` decimal(5,2) DEFAULT NULL COMMENT '身高',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重',
  `photo` varchar(255) DEFAULT NULL COMMENT '照片',
  `first_position` varchar(255) DEFAULT NULL COMMENT '第一位置',
  `second_position` varchar(255) DEFAULT NULL COMMENT '第二位置',
  `team_id` varchar(255) NOT NULL,
  `team_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team_member
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0：无效用户 1：有效, -1 预分配uid用户',
  `create_time` datetime DEFAULT NULL,
  `activite_time` datetime DEFAULT NULL COMMENT '激活时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '10001', 'a', 'a@xp.com', '123123', 'a', '123456789', null, null, null, '1', null, null, '0');
