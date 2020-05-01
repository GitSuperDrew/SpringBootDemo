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

 Date: 01/05/2020 19:07:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int(100) NOT NULL AUTO_INCREMENT COMMENT '学生ID，主键',
  `stu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `stu_age` int(3) NULL DEFAULT NULL COMMENT '学生年龄',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'Beijing', 24);
INSERT INTO `student` VALUES (2, 'ChangSha', 23);
INSERT INTO `student` VALUES (3, 'HaiNan', 22);
INSERT INTO `student` VALUES (4, 'TianJing', 25);
INSERT INTO `student` VALUES (5, 'ShangHai', 23);
INSERT INTO `student` VALUES (6, 'HongKong', 21);
INSERT INTO `student` VALUES (7, 'WuHan', 28);
INSERT INTO `student` VALUES (8, 'YongZhou', 26);
INSERT INTO `student` VALUES (9, 'ZhuZhou', 24);
INSERT INTO `student` VALUES (10, 'WuHan', 28);
INSERT INTO `student` VALUES (11, 'YongZhou', 26);
INSERT INTO `student` VALUES (12, 'ZhuZhou3', 37);
INSERT INTO `student` VALUES (13, 'Bob', 24);
INSERT INTO `student` VALUES (14, 'NanNing', 22);
INSERT INTO `student` VALUES (15, 'HeNan', 26);
INSERT INTO `student` VALUES (16, 'GuangZhou', 35);
INSERT INTO `student` VALUES (17, 'ShengZhen', 24);
INSERT INTO `student` VALUES (18, 'ShanTou', 24);
INSERT INTO `student` VALUES (19, 'QingYuan', 22);
INSERT INTO `student` VALUES (20, 'XiangXi', 56);
INSERT INTO `student` VALUES (21, 'Changde', 78);

SET FOREIGN_KEY_CHECKS = 1;
