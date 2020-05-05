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

 Date: 05/05/2020 22:36:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Jone', 18, 'test1@baomidou.com');
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com');
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com');
INSERT INTO `user` VALUES (5, 'King', 24, 'test5@baomidou.com');
INSERT INTO `user` VALUES (6, 'Drew', 24, 'test6@baomidou.com');
INSERT INTO `user` VALUES (7, 'Bob', 24, 'test7@baomidou.com');
INSERT INTO `user` VALUES (8, 'Mary', 24, 'test8@baomidou.com');
INSERT INTO `user` VALUES (9, 'Mark', 24, 'test9@baomidou.com');
INSERT INTO `user` VALUES (10, 'Aallen', 24, 'test10@baomidou.com');
INSERT INTO `user` VALUES (11, 'Yelily', 24, 'test11@baomidou.com');
INSERT INTO `user` VALUES (12, 'Ping', 24, 'test12@baomidou.com');
INSERT INTO `user` VALUES (13, 'Pong', 24, 'test13@baomidou.com');
INSERT INTO `user` VALUES (14, 'Jacky', 24, 'test14@baomidou.com');
INSERT INTO `user` VALUES (15, 'BruceLee', 24, 'test15@baomidou.com');
INSERT INTO `user` VALUES (16, 'Billie', 24, 'test16@baomidou.com');
INSERT INTO `user` VALUES (17, 'Billie', 24, 'test17@baomidou.com');
INSERT INTO `user` VALUES (18, 'Tine', 24, 'test18@baomidou.com');
INSERT INTO `user` VALUES (19, 'Line', 24, 'test19@baomidou.com');
INSERT INTO `user` VALUES (20, 'Tigger', 24, 'test20@baomidou.com');
INSERT INTO `user` VALUES (21, 'Shark', 24, 'test21@baomidou.com');
INSERT INTO `user` VALUES (22, 'Eagle', 24, 'test22@baomidou.com');

SET FOREIGN_KEY_CHECKS = 1;
