/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : zero

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 19/01/2021 17:33:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stu
-- ----------------------------
DROP TABLE IF EXISTS `stu`;
CREATE TABLE `stu`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  `version` bigint(11) NULL DEFAULT 1 COMMENT '乐观锁',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu
-- ----------------------------
INSERT INTO `stu` VALUES (1, 'Drew', 12, 1, 'admin', '2021-01-10 10:15:42', NULL, '2021-01-10 10:16:02');
INSERT INTO `stu` VALUES (2, '诸葛亮', 12, 1, 'Drew', '2021-01-10 10:15:49', NULL, '2021-01-10 10:16:02');

SET FOREIGN_KEY_CHECKS = 1;
