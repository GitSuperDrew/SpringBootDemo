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

 Date: 01/05/2020 19:07:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
