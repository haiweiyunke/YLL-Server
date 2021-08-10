/*
 Navicat Premium Data Transfer

 Source Server         : localhost-3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : _yll

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 12/07/2019 21:24:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ordinal` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id_path` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `deleted` int(11) NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('D001746', 'D005341', '目标责任考核小组', '目标责任考核小组', '2', '/D273322/D005341/D001746/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D003040', 'D396674', '总行营业部负责人', '总行营业部负责人', '00', '/D273322/D396674/D003040/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D003950', 'D048252', '贷后管理中心', '贷后管理中心', '31', '/D273322/D048252/D003950/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D004318', 'D273322', '纪检监察室', '纪检监察室', '240', '/D273322/D004318/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D005341', 'D273322', '战略规划暨目标责任考核办公室', '战略规划暨目标责任考核办公室', '109', '/D273322/D005341/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D014682', 'D396674', '产品管理团队', '产品管理团队', '07', '/D273322/D396674/D014682/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D017440', 'D116516', '互联网金融部', '互联网金融部', '239', '/D273322/D116516/D017440/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D035183', 'D273322', '直销银行部', '直销银行部', '180', '/D273322/D035183/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D035197', 'D273322', '董事会办公室', '董事会办公室', '02', '/D273322/D035197/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D038327', 'D273322', '零售业务部', '零售业务部', '110', '/D273322/D038327/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D038940', 'D273322', '审计稽核部', '审计稽核部', '120', '/D273322/D038940/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D042376', 'D257938', '运营作业中心', '运营作业中心', '33', '/D273322/D257938/D042376/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D048252', 'D273322', '信贷管理部', '信贷管理部', '08', '/D273322/D048252/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D050064', 'D396674', '公司业务部', '公司业务部', '02', '/D273322/D396674/D050064/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D063135', 'D273322', '授信审批部', '授信审批部', '140', '/D273322/D063135/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D070108', 'D273322', '行政服务部', '行政服务部', '250', '/D273322/D070108/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D075804', 'D273322', '机构管理部', '机构管理部', '190', '/D273322/D075804/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D080663', 'D273322', '国际业务部', '国际业务部', '210', '/D273322/D080663/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D083596', 'D396674', '外汇系统管理部', '外汇系统管理部', '05', '/D273322/D396674/D083596/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D090177', 'D273322', '保卫部', '保卫部', '220', '/D273322/D090177/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D098019', 'D273322', '党委办公室', '党委办公室', '01', '/D273322/D098019/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D109313', 'D150369', '资产管理中心', '资产管理中心', '01', '/D273322/D150369/D109313/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D114013', 'D273322', '组织-人力资源部', '组织-人力资源部', '05', '/D273322/D114013/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D116516', 'D273322', '科技部', '科技部', '248', '/D273322/D116516/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D131951', 'D273322', '行领导', '行领导', '0', '/D273322/D131951/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D133560', 'D273322', '工会办公室', '工会办公室', '259', '/D273322/D133560/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D150369', 'D273322', '合规部', '合规部', '160', '/D273322/D150369/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D153894', 'D273322', '风险管理部', '风险管理部', '130', '/D273322/D153894/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D158641', 'D273322', '投贷联动领导小组办公室', '投贷联动领导小组办公室', '249', '/D273322/D158641/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D160383', 'D273322', '行长办公室', '行长办公室', '04', '/D273322/D160383/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D167364', 'D396674', '综合办公室', '综合办公室', '01', '/D273322/D396674/D167364/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D176763', 'D038327', '信用卡中心', '信用卡中心', '01', '/D273322/D038327/D176763/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D201035', 'D273322', '研究发展部', '研究发展部', '245', '/D273322/D201035/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D205619', 'D396674', '市场管理部', '市场管理部', '04', '/D273322/D396674/D205619/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D208730', 'D273322', '金融市场部', '金融市场部', '100', '/D273322/D208730/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D238882', 'D257938', '消费者权益保护及客户服务中心', '消费者权益保护及客户服务中心', '35', '/D273322/D257938/D238882/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D249778', 'D257938', '反洗钱中心', '反洗钱中心', '34', '/D273322/D257938/D249778/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D257938', 'D273322', '运营管理部', '运营管理部', '10', '/D273322/D257938/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D273322', '', '总行', '银行', '00', '/D273322/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D275558', 'D273322', '小企业部', '小企业部', '10', '/D273322/D275558/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D342593', 'D273322', '计划财务部', '计划财务部', '07', '/D273322/D342593/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D361432', 'D257938', '现金管理中心', '现金管理中心', '3', '/D273322/D257938/D361432/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D375840', 'D114013', '培训中心', '培训中心', '36', '/D273322/D114013/D375840/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D376988', 'D273322', '全面风险管理项目建设办公室', '全面风险管理项目建设办公室', '135', '/D273322/D376988/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D396674', 'D273322', '总行营业部', '总行营业部', '260', '/D273322/D396674/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D405628', 'D396674', '零售业务部', '零售业务部', '03', '/D273322/D396674/D405628/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D417658', 'D273322', '机关工会', '机关工会', '259', '/D273322/D417658/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D421354', 'D273322', '投资银行部', '投资银行部', '170', '/D273322/D421354/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D434195', 'D273322', '公司业务部', '公司业务部', '09', '/D273322/D434195/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D449605', 'D273322', '基建办公室', '基建办公室', '150', '/D273322/D449605/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D500388', 'D273322', '监事会办公室', '监事会办公室', '03', '/D273322/D500388/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D516656', 'D005341', '项目管理小组', '项目管理小组', '1', '/D273322/D005341/D516656/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D535878', 'D273322', '团委', '团委', '01', '/D273322/D535878/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D538841', 'D396674', '国际结算单证中心', '国际结算单证中心', '06', '/D273322/D396674/D538841/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D541278', 'D070108', '物业管理中心', '物业管理中心', '1', '/D273322/D070108/D541278/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D847623', 'D396674', '营业室', '营业室', '08', '/D273322/D396674/D847623/', 0, NULL, '2018-10-26 11:18:23', NULL, '2018-12-27 17:25:12');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `ordinal` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_path` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('10000', '0', '系统管理', '', 1, '/system/system-index.html', 'xit', '400', '/10000/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('10100', '10000', '用户管理', '', 2, '/system/user/user-list.html', 'icon-yonghuguanli', '10', '/10000/10100/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('10200', '10000', '角色管理', ' ', 2, '/system/role/role-list.html', 'icon-wulumuqishigongandashujuguanlipingtai-ico-', '40', '/10000/10600/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('10300', '10000', '部门管理', '', 2, '/system/department/department-index.html', 'icon-daibanshixiang1', '50', '/10000/10700/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('10800', '10000', '操作日志', '', 2, '/system/op-log/op-log-index.html', 'icon-rizhi', '60', '/10000/10800/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('20000', '0', '接口管理', '', 1, '/interface/interface-index.html', 'shujuy', '300', '/20000/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('20100', '20000', '数据源站点管理', '', 2, '/interface/fetcher-site/fetcher-site-list.html', '', '10', '/20000/20200/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('20200', '20000', '数据源接口管理', '', 2, '/interface/fetcher-driver/fetcher-driver-index.html', '', '20', '/20000/20200/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('20400', '20000', '服务接口帮助', '', 2, '/interface/exporter/exporter-index.html', '', '40', '/20000/20400/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('20700', '20000', '调度任务', '', 2, '/interface/sched-job/sched-job-index.html', '', '70', '/20000/20700/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('30000', '0', '费用管理', '', 1, '/expense/expense-index.html', 'feiy', '200', '/30000/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('30100', '30000', '费用维护', '', 2, '/expense/fee-rule/fee-rule-index.html', '', '1', '/30000/30100/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('30200', '30000', '费用日志', '', 2, '/expense/expense-log/expense-log-index.html', '', '2', '/30000/30200/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('30300', '30000', '统计报表', '', 2, '/expense/fee-account/fee-account-index.html', '', '3', '/30000/30300/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('40000', '0', '数据调阅', '', 1, '/search/search-index.html', 'shuj', '100', '/40000/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('40100', '40000', '数据调阅', '', 2, '/search/data-search/data-search-index.html', '', '10', '/40000/40100/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('40200', '40000', '调阅日志', '', 2, '/search/data-search-log/data-search-log.html', '', '20', '/40000/40200/', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES ('40300', '40000', '统计报表', '', 2, '/search/data-permit/todo-data-permit-self.html', '', '30', '/40000/40300/', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0000000000000000010001', '系统管理员', 'UAT测试增加,勿删。', '0', '1982-12-11 00:00:00', '0', '2018-12-27 14:01:14');
INSERT INTO `role` VALUES ('0000000000000000010002', '业务管理员', 'UAT测试增加,勿删。', '0', '1982-12-12 00:00:00', '0', '2018-12-27 14:01:11');
INSERT INTO `role` VALUES ('0000000000000000010003', '业务用户', 'UAT测试增加,勿删。能够使用外部数据服务以及查看数据的用户', '0', '2018-12-27 09:50:36', '0', '2018-12-27 14:01:08');
INSERT INTO `role` VALUES ('0000000000000000090000', '测试角色α', '不要删', '0', '1982-12-13 00:00:00', NULL, NULL);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `permission_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('2018122709191648300004', '0000000000000000010002', '40000', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400004', '0000000000000000010002', '40100', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400104', '0000000000000000010002', '40200', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400204', '0000000000000000010002', '40300', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400304', '0000000000000000010002', '40400', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400404', '0000000000000000010002', '40500', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400504', '0000000000000000010002', '30000', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400604', '0000000000000000010002', '30100', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400704', '0000000000000000010002', '30200', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400804', '0000000000000000010002', '30300', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648400904', '0000000000000000010002', '20000', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401004', '0000000000000000010002', '20100', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401104', '0000000000000000010002', '20200', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401204', '0000000000000000010002', '20400', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401304', '0000000000000000010002', '20500', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401404', '0000000000000000010002', '20600', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401604', '0000000000000000010002', '10100', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401704', '0000000000000000010002', '10500', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401804', '0000000000000000010002', '10600', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648401904', '0000000000000000010002', '10700', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648402004', '0000000000000000010002', '10800', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709191648402104', '0000000000000000010002', '10000', '0', '2018-12-27 09:19:16', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200004', '0000000000000000010003', '40100', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200104', '0000000000000000010003', '40500', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200204', '0000000000000000010003', '20400', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200304', '0000000000000000010003', '10600', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200404', '0000000000000000010003', '40000', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200504', '0000000000000000010003', '20000', '0', '2018-12-27 09:51:18', NULL, NULL);
INSERT INTO `role_permission` VALUES ('2018122709511817200604', '0000000000000000010003', '10000', '0', '2018-12-27 09:51:18', NULL, NULL);

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `mac` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MAC地址(多个地址逗号,分隔)',
  `host` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `sequence` int(11) NULL DEFAULT NULL COMMENT '序号',
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `created_at` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `updated_at` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server_info
-- ----------------------------
INSERT INTO `server_info` VALUES ('57d1b12beaf3ff29a6967794158f73ef', '00-00-00-00-00-00-00-E0,28-D2-44-3F-1E-55,E8-B1-FC-27-10-3B,E8-B1-FC-27-10-37,00-50-56-C0-00-08,00-FF-0E-CD-34-1D,5E-55-A5-DB-2A-0E', '10.0.102.190', 1, '#', '2019-07-12 17:37:24', '', '2019-07-12 20:21:38');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-1',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` int(11) NULL DEFAULT 1,
  `deleted` int(11) NULL DEFAULT NULL COMMENT '0-正常 1-删除',
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('$1', '-1', '西安银行', 'test', '{bcrypt}$2a$10$Kx8lfilp2pRMjRyeuK.q7OFLce8pw9VNAiHlKX/deEk1jJd.iCGT.', '测试用户α', '测试账号α', 1, 0, NULL, NULL, '0', '2019-03-18 14:23:34');
INSERT INTO `user` VALUES ('0', '-1', '', 'admin', '{bcrypt}$2a$10$CXn4bMRWSvg1Nh2Pbtoy1uwtYKgIrHO516HDZZpeSxoZCG634CUey', '管理员', '管理员', 1, 0, NULL, NULL, '0', '2018-12-27 09:28:26');
INSERT INTO `user` VALUES ('2019030716020546600002', NULL, NULL, 'testt', '{bcrypt}$2a$10$RhHC2j/8v4vft5EpiZRFhufrSFOZMNczLzg8a6cevjWVzK5pVbDtC', 'testtt', 'ttt', 0, NULL, '0', '2019-03-07 16:02:06', '0', '2019-03-07 16:45:12');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2018121710161233100004', '$1', '0000000000000000000001', '0', '2018-12-17 10:16:12', NULL, NULL);
INSERT INTO `user_role` VALUES ('2018122709221838900004', '000590', '0000000000000000010002', '0', '2018-12-27 09:22:18', NULL, NULL);
INSERT INTO `user_role` VALUES ('2018122709515723000004', '000423', '0000000000000000010003', '0', '2018-12-27 09:51:57', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
