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

 Date: 13/06/2020 12:49:08
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
  `stu_sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '学生性别（0：未知，1：男，2：女）',
  `deleted` int(1) NULL DEFAULT 0 COMMENT '逻辑删除标识（0：有效数据，1：无效数据）',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'Beijing', 24, '1', 0);
INSERT INTO `student` VALUES (2, 'ChangSha', 23, '0', 0);
INSERT INTO `student` VALUES (3, 'HaiNan', 22, '0', 0);
INSERT INTO `student` VALUES (4, 'TianJing', 25, '1', 1);
INSERT INTO `student` VALUES (5, 'ShangHai', 23, '0', 0);
INSERT INTO `student` VALUES (6, 'HongKong', 21, '1', 0);
INSERT INTO `student` VALUES (7, 'WuHan', 28, '0', 0);
INSERT INTO `student` VALUES (8, 'YongZhou', 26, '1', 1);
INSERT INTO `student` VALUES (9, 'ZhuZhou', 24, '0', 0);
INSERT INTO `student` VALUES (10, 'WuHan', 28, '1', 0);
INSERT INTO `student` VALUES (11, 'YongZhou', 26, '0', 0);
INSERT INTO `student` VALUES (12, 'ZhuZhou3', 37, '0', 0);
INSERT INTO `student` VALUES (13, 'Bob', 24, '1', 0);
INSERT INTO `student` VALUES (14, 'NanNing', 22, '0', 0);
INSERT INTO `student` VALUES (15, 'HeNan', 26, '1', 0);
INSERT INTO `student` VALUES (16, 'GuangZhou', 35, '0', 0);
INSERT INTO `student` VALUES (17, 'ShengZhen', 24, '0', 0);
INSERT INTO `student` VALUES (18, 'ShanTou', 24, '1', 0);
INSERT INTO `student` VALUES (19, 'QingYuan', 22, '1', 0);
INSERT INTO `student` VALUES (20, 'XiangXi', 56, '1', 1);
INSERT INTO `student` VALUES (21, 'Changde', 78, '0', 1);

SET FOREIGN_KEY_CHECKS = 1;
