/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : localhost:3306
 Source Schema         : zero

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 01/05/2020 19:15:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_salary
-- ----------------------------
DROP TABLE IF EXISTS `t_salary`;
CREATE TABLE `t_salary`  (
  `ID` int(10) NOT NULL DEFAULT 0,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `office` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公地点',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '入职日期\n',
  `salary` int(10) NULL DEFAULT NULL COMMENT '薪水',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '薪资表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_salary
-- ----------------------------
INSERT INTO `t_salary` VALUES (1, 'Airi Satou', 'Accountant', 'Tokyo', 33, '2020-04-25 00:00:00', 20000);
INSERT INTO `t_salary` VALUES (2, 'Angelica Ramos', 'Chief Executive Officer (CEO)', 'London', 47, '2000-06-06 00:00:00', 1200000);
INSERT INTO `t_salary` VALUES (3, 'Ashton Cox', 'Junior Technical Author', 'San Francisco', 66, '2020-04-23 00:00:00', 20000);
INSERT INTO `t_salary` VALUES (4, 'Bradley Greer', 'Software Engineer', 'London', 45, '2016-04-26 00:00:00', 35000);
INSERT INTO `t_salary` VALUES (5, 'Brenden Wagner', 'Software Engineer', 'San Francisco', 28, '2009-06-09 00:00:00', 35000);
INSERT INTO `t_salary` VALUES (6, 'Brielle Williamson', 'Integration Specialist', 'New York', 61, '2011-04-20 00:00:00', 45000);
INSERT INTO `t_salary` VALUES (7, 'Bruno Nash', 'Software Engineer', 'London', 38, '2017-11-16 00:00:00', 35000);
INSERT INTO `t_salary` VALUES (8, 'Caesar Vance', 'Pre-Sales Support', 'New York', 21, '2014-08-13 00:00:00', 10600);
INSERT INTO `t_salary` VALUES (9, 'Cara Stevens', 'Sales Assistant', 'New York', 46, '2016-09-24 00:00:00', 13000);
INSERT INTO `t_salary` VALUES (10, 'Cedric Kelly', 'Senior Javascript Developer', 'Edinburgh', 22, '2016-04-22 00:00:00', 28000);
INSERT INTO `t_salary` VALUES (11, 'Dob Drew', 'Accountant', 'Edinburgh', 24, '2006-02-11 00:00:00', 13000);
INSERT INTO `t_salary` VALUES (12, 'Charde Marshall', 'Regional Director', 'San Francisco', 36, '2018-06-07 00:00:00', 24700);
INSERT INTO `t_salary` VALUES (13, 'Dai Rios', 'Personnel Lead', 'San Francisco', 35, '2009-06-09 00:00:00', 56000);
INSERT INTO `t_salary` VALUES (14, 'Donna Snider', 'Customer Support', 'New York', 27, '2011-04-20 00:00:00', 37200);
INSERT INTO `t_salary` VALUES (15, 'Doris Wilder', 'Sales Assistant', 'London', 23, '2017-11-16 00:00:00', 12900);
INSERT INTO `t_salary` VALUES (16, 'Finn Camacho', 'Support Engineer', 'New York', 47, '2014-08-13 00:00:00', 10600);
INSERT INTO `t_salary` VALUES (17, 'Fiona Green', 'Chief Operating Officer (COO)', 'New York', 48, '2016-09-24 00:00:00', 50600);
INSERT INTO `t_salary` VALUES (18, 'Garrett Winters', 'Accountant', 'Edinburgh', 63, '2016-04-22 00:00:00', 20000);
INSERT INTO `t_salary` VALUES (19, 'Gavin Cortez', 'Team Leader', 'San Francisco', 22, '2018-06-07 00:00:00', 51800);
INSERT INTO `t_salary` VALUES (20, 'Herrod Chandler', 'Sales Assistant', 'San Francisco', 47, '2012-12-31 00:00:00', 12900);
INSERT INTO `t_salary` VALUES (21, 'Hope Fuentes', 'Secretary', 'San Francisco', 59, '2009-02-11 00:00:00', 20000);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL DEFAULT 0 COMMENT '用户主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表（用来测试代码生成插件EasyCode）' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Drew', 'Drew');
INSERT INTO `user` VALUES (2, 'King', 'King');
INSERT INTO `user` VALUES (3, 'Bob', 'Bob');
INSERT INTO `user` VALUES (4, 'Drew', 'Drew');
INSERT INTO `user` VALUES (5, 'King', 'King');
INSERT INTO `user` VALUES (6, 'Bob', 'Bob');
INSERT INTO `user` VALUES (7, 'Drew', 'Drew');
INSERT INTO `user` VALUES (8, 'King', 'King');
INSERT INTO `user` VALUES (9, 'Bob', 'Bob');
INSERT INTO `user` VALUES (10, 'Drew', 'Drew');
INSERT INTO `user` VALUES (11, 'King', 'King');
INSERT INTO `user` VALUES (12, 'Bob', 'Bob');
INSERT INTO `user` VALUES (13, 'Drew', 'Drew');
INSERT INTO `user` VALUES (14, 'King', 'King');
INSERT INTO `user` VALUES (15, 'Bob', 'Bob');
INSERT INTO `user` VALUES (16, 'Drew', 'Drew');
INSERT INTO `user` VALUES (17, 'King', 'King');
INSERT INTO `user` VALUES (18, 'Bob', 'Bob');
INSERT INTO `user` VALUES (19, 'Drew', 'Drew');
INSERT INTO `user` VALUES (20, 'King', 'King');
INSERT INTO `user` VALUES (21, 'Bob', 'Bob');
INSERT INTO `user` VALUES (22, 'Drew', 'Drew');
INSERT INTO `user` VALUES (23, 'King', 'King');

SET FOREIGN_KEY_CHECKS = 1;
