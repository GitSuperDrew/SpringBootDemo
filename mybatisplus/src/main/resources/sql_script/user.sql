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

 Date: 11/06/2020 20:40:43
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
  `remove_tag` int(1) NULL DEFAULT 1 COMMENT '逻辑删除字段（1：无效数据，0：有效数据，表示可被查询到）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Jone', 18, 'test1@baomidou.com', 0);
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', 0);
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', 0);
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', 0);
INSERT INTO `user` VALUES (5, 'King', 24, 'test5@baomidou.com', 0);
INSERT INTO `user` VALUES (6, 'Drew', 24, 'test6@baomidou.com', 0);
INSERT INTO `user` VALUES (7, 'Bob', 24, 'test7@baomidou.com', 0);
INSERT INTO `user` VALUES (8, 'Mary', 24, 'test8@baomidou.com', 0);
INSERT INTO `user` VALUES (9, 'Mark', 24, 'test9@baomidou.com', 0);
INSERT INTO `user` VALUES (10, 'Aallen', 24, 'test10@baomidou.com', 0);
INSERT INTO `user` VALUES (11, 'Yelily', 24, 'test11@baomidou.com', 0);
INSERT INTO `user` VALUES (12, 'Ping', 24, 'test12@baomidou.com', 0);
INSERT INTO `user` VALUES (13, 'Pong', 24, 'test13@baomidou.com', 1);
INSERT INTO `user` VALUES (14, 'Jacky', 24, 'test14@baomidou.com', 1);
INSERT INTO `user` VALUES (15, 'BruceLee', 24, 'test15@baomidou.com', 1);
INSERT INTO `user` VALUES (16, 'Billie', 24, 'test16@baomidou.com', 0);
INSERT INTO `user` VALUES (17, 'Billie', 24, 'test17@baomidou.com', 0);
INSERT INTO `user` VALUES (18, 'Tine', 24, 'test18@baomidou.com', 0);
INSERT INTO `user` VALUES (19, 'Line', 24, 'test19@baomidou.com', 0);
INSERT INTO `user` VALUES (20, 'Tigger', 24, 'test20@baomidou.com', 0);
INSERT INTO `user` VALUES (21, 'Shark', 24, 'test21@baomidou.com', 1);
INSERT INTO `user` VALUES (22, 'Eagle', 24, 'test22@baomidou.com', 1);

SET FOREIGN_KEY_CHECKS = 1;
