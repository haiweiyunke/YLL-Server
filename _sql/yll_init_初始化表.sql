/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : yll04

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2019-07-25 17:51:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '标题',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动表';

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, '1', null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `activities` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `activities` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, '1', null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for activities_answer
-- ----------------------------
DROP TABLE IF EXISTS `activities_answer`;
CREATE TABLE `activities_answer` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `content` longtext COMMENT '内容',
  `answer` varchar(255) DEFAULT NULL COMMENT '正确答案（0-否，1-是；填空题为具体内容）',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动竞赛答案表';

-- ----------------------------
-- Records of activities_answer
-- ----------------------------
INSERT INTO `activities_answer` VALUES ('088be8a2bdb24c9581d74ad78357c8cd', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('094df0964ee94565bfc3da6a26cc1b33', '1ea010155a9f4cc9b097fb537eb8ff4f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('1', '1', null, null, '1', '111', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('1143c2c0d7f44542864d148d8b8597db', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('162219578a2c4314b75898e475a7f876', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('18750470a58c4408beb2ffb2b6764034', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('18cee6e8ee90440d909f42f5796b1144', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('1d2d63696e02425e8fbfb85e48b74008', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('1f1292118dca491ba2ef07010b146206', 'a64bc35e8bd243aa81ea4ab617292340', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('2', '1', null, null, '1', '2222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('20313ce9edec45349ab14165bb56e7b5', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('21a13cc4ef6943dcbe7304d884c1707d', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('2296d9b513dc4e9cb253334ab6722e14', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('27357d292e2240b2ac740417e2351190', 'eb660afb783645448a4e9568a55cdc33', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('294a63ccd553479a9222a64f3adb26d1', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('3', '1', null, null, '1', '333', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('33188b63ddbc49559bb8e039c4c59f9e', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('33f7a5a33342442f8a68061e469f7f66', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('342cbff6dba24ff2a0bc41a2bc434b41', '16b7458069474e179532a2a8b78cea0a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('34d950d7ec0e422c9f1aac6b833179ba', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('354ceb4113694c88960a22ca61e572d2', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('37e3f6f1c53442ee92403b525f78debc', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('3b80b6bcb1c14f7e93ebacf74e7d0411', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('4', '2', null, null, '1', '222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('44a151b1df084f11a76c5cc84cc2862d', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('47ee50ea9ebb490484c8d492e02e0efe', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('4af0a82da8034208baf483e038d6d503', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('4bbedd250ecc4a0e98e34664ca953919', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('4e2e989f931242b0acd3e0a7f099d112', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('4ed5d42d39f64002869378a839d8d845', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('51c446c2c71349ba820ad740f9c154a7', '9f6f05bbd565485394740cbf692fd81f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('54215fdb4ffb43b79f9c82fc6fca4b2b', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('57ef1386a8134022887a333ccb4e9e8a', '082de545da024136bb545974ccd48c85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('5e0dc600e15b4f8fb56c20aee4cdf7d4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('613389e91c1941f78afe1f61b7607324', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('6460ce9c9ec944af9a23c9d8ad478ccf', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('654f0b1d899c4437938fec13be8c04de', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('68067e0b677d479cbd98578264257a76', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('6aea9e35e59642659324f9ebfdcd3bfc', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('6cb7d2e64c7a4ceb84d57596bd1ab1be', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('6e52e8f6c6f348e0976ee4363ab693c3', '05b3d9d320f6498d92b3ceec9bfc506a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7025c724e3ba43d4814b8958d2160f23', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7274f65f167c4f1bb08bdda76f8b4617', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('74b26cf976e24743b806ded581371bab', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7545976fb6584c6b9bf3a56a83c99e5d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7aa4bf8c11634e7c9fa567cd8654c606', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7bf14dfe65a449739df48c44b0f03374', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('7cc22f02846c485983441fda1231579e', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('824d662451a64ad2b8519a8fec157345', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('87aa71eaea3d43f69cd84922aeff8f43', 'c7b5caa70e77470e8a6f6808068f3415', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('89a6322204d742fdb8204ccc2a2a5971', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('8e6f329cf91447abbd9e7205ea3d51c3', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('8f738a95a3c04e5a9b0ea5be84e1e74d', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('904427c010b140d2a92e6457a2a031f8', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('92936bb7688348448cfb32c8f6cc9ef3', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('93b9ab8fd88c477084d7291ee705c26f', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('93f3fac9c2544d9d8bdb1ccb0483f9f1', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('95b893e54c3344eeac8d62b7dba48782', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('9cac62f92fb7486583e7e2b034dca96e', 'b7659390175843679025cd774b71f009', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('9cb87a900ed44a93a76e467a2eedd303', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('9fbcc9ccfca24025938e702337923566', 'f91b76bf1eca47cbbb83a86de63a48cb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('a6107709041349cebc1af207967d8a8d', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('a7cf1b41cd804049ba10288d698de975', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('ae02d1f09aa04e6aba17c0cba554dc7d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('b1d40c4ec7d7401aa5fbc913c6869dd3', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('b3f3d308996e44a6a54345e27b7dde63', 'f45f5dca11864538a60ca840c56ea382', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('bd44f8baa1fa46f287aad2678e75a5a0', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('bf22afb750ba40cd9de145813ef55c15', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('c02afbc272104104938380d57f124140', '3cd76c26168a4d80ae1954f988cf6fd2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('c619a2d7934a4db5a112a98297126cc6', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('cb97045bf49041b08d4c65def74ce68d', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('ce47b00b35a340bd99f4f923ec15f990', '43a21b92e14143bb919797b91ce944dc', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('d0ea40658b554245bf6e55d04d73ad54', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('da2c523b90cf47dbb78df66e3da2d11f', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('dafd835f558e435395caabe9b6e85c59', '3de4003ac20943c8a519698d67774727', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('db8c48cad0f04d9d95c6df1ee5b85051', '0484fd8a47a5427d8d6f7fefcc68437e', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('dc58e48150274cd0bc674b84f8a107eb', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('de9840de913e40ef9269fc62ea7d1b71', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('e628e741cb6545cc858d858ba9bab0bb', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('ea456d96b51346ea8e98142cba913308', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('ebf4515893a74c859909e21077749371', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('ec2e0e51c78e4fd19a75d4c8241bbfad', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('f0b4559713ba4854a55149797c5ea3b0', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('f1e292823607472693b3c639c1b25fdf', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('f20406da18f549ed8d1cfdd8116749e0', '28263c4e02744e078048c96b52321a0c', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('f2556d35a1d04a53ab0d2ffdd2422231', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('f7562f269db040de9f91ee831170e54e', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('fb8edd17de69480f89e7ef8fbcd1e6fc', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_answer` VALUES ('fd80ca09e1bf471ea992c971305f6e97', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for activities_modules
-- ----------------------------
DROP TABLE IF EXISTS `activities_modules`;
CREATE TABLE `activities_modules` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `content` longtext COMMENT '内容',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取。资讯、课件等)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动详情模块表';

-- ----------------------------
-- Records of activities_modules
-- ----------------------------
INSERT INTO `activities_modules` VALUES ('088be8a2bdb24c9581d74ad78357c8cd', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('094df0964ee94565bfc3da6a26cc1b33', '1ea010155a9f4cc9b097fb537eb8ff4f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('1', '1', null, null, '1', '111', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('1143c2c0d7f44542864d148d8b8597db', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('162219578a2c4314b75898e475a7f876', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('18750470a58c4408beb2ffb2b6764034', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('18cee6e8ee90440d909f42f5796b1144', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('1d2d63696e02425e8fbfb85e48b74008', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('1f1292118dca491ba2ef07010b146206', 'a64bc35e8bd243aa81ea4ab617292340', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('2', '1', null, null, '1', '2222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('20313ce9edec45349ab14165bb56e7b5', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('21a13cc4ef6943dcbe7304d884c1707d', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('2296d9b513dc4e9cb253334ab6722e14', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('27357d292e2240b2ac740417e2351190', 'eb660afb783645448a4e9568a55cdc33', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('294a63ccd553479a9222a64f3adb26d1', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('3', '1', null, null, '1', '333', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('33188b63ddbc49559bb8e039c4c59f9e', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('33f7a5a33342442f8a68061e469f7f66', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('342cbff6dba24ff2a0bc41a2bc434b41', '16b7458069474e179532a2a8b78cea0a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('34d950d7ec0e422c9f1aac6b833179ba', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('354ceb4113694c88960a22ca61e572d2', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('37e3f6f1c53442ee92403b525f78debc', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('3b80b6bcb1c14f7e93ebacf74e7d0411', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('4', '2', null, null, '1', '222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('44a151b1df084f11a76c5cc84cc2862d', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('47ee50ea9ebb490484c8d492e02e0efe', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('4af0a82da8034208baf483e038d6d503', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('4bbedd250ecc4a0e98e34664ca953919', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('4e2e989f931242b0acd3e0a7f099d112', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('4ed5d42d39f64002869378a839d8d845', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('51c446c2c71349ba820ad740f9c154a7', '9f6f05bbd565485394740cbf692fd81f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('54215fdb4ffb43b79f9c82fc6fca4b2b', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('57ef1386a8134022887a333ccb4e9e8a', '082de545da024136bb545974ccd48c85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('5e0dc600e15b4f8fb56c20aee4cdf7d4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('613389e91c1941f78afe1f61b7607324', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('6460ce9c9ec944af9a23c9d8ad478ccf', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('654f0b1d899c4437938fec13be8c04de', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('68067e0b677d479cbd98578264257a76', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('6aea9e35e59642659324f9ebfdcd3bfc', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('6cb7d2e64c7a4ceb84d57596bd1ab1be', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('6e52e8f6c6f348e0976ee4363ab693c3', '05b3d9d320f6498d92b3ceec9bfc506a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7025c724e3ba43d4814b8958d2160f23', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7274f65f167c4f1bb08bdda76f8b4617', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('74b26cf976e24743b806ded581371bab', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7545976fb6584c6b9bf3a56a83c99e5d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7aa4bf8c11634e7c9fa567cd8654c606', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7bf14dfe65a449739df48c44b0f03374', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('7cc22f02846c485983441fda1231579e', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('824d662451a64ad2b8519a8fec157345', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('87aa71eaea3d43f69cd84922aeff8f43', 'c7b5caa70e77470e8a6f6808068f3415', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('89a6322204d742fdb8204ccc2a2a5971', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('8e6f329cf91447abbd9e7205ea3d51c3', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('8f738a95a3c04e5a9b0ea5be84e1e74d', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('904427c010b140d2a92e6457a2a031f8', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('92936bb7688348448cfb32c8f6cc9ef3', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('93b9ab8fd88c477084d7291ee705c26f', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('93f3fac9c2544d9d8bdb1ccb0483f9f1', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('95b893e54c3344eeac8d62b7dba48782', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('9cac62f92fb7486583e7e2b034dca96e', 'b7659390175843679025cd774b71f009', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('9cb87a900ed44a93a76e467a2eedd303', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('9fbcc9ccfca24025938e702337923566', 'f91b76bf1eca47cbbb83a86de63a48cb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('a6107709041349cebc1af207967d8a8d', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('a7cf1b41cd804049ba10288d698de975', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('ae02d1f09aa04e6aba17c0cba554dc7d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('b1d40c4ec7d7401aa5fbc913c6869dd3', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('b3f3d308996e44a6a54345e27b7dde63', 'f45f5dca11864538a60ca840c56ea382', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('bd44f8baa1fa46f287aad2678e75a5a0', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('bf22afb750ba40cd9de145813ef55c15', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('c02afbc272104104938380d57f124140', '3cd76c26168a4d80ae1954f988cf6fd2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('c619a2d7934a4db5a112a98297126cc6', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('cb97045bf49041b08d4c65def74ce68d', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('ce47b00b35a340bd99f4f923ec15f990', '43a21b92e14143bb919797b91ce944dc', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('d0ea40658b554245bf6e55d04d73ad54', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('da2c523b90cf47dbb78df66e3da2d11f', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('dafd835f558e435395caabe9b6e85c59', '3de4003ac20943c8a519698d67774727', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('db8c48cad0f04d9d95c6df1ee5b85051', '0484fd8a47a5427d8d6f7fefcc68437e', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('dc58e48150274cd0bc674b84f8a107eb', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('de9840de913e40ef9269fc62ea7d1b71', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('e628e741cb6545cc858d858ba9bab0bb', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('ea456d96b51346ea8e98142cba913308', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('ebf4515893a74c859909e21077749371', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('ec2e0e51c78e4fd19a75d4c8241bbfad', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('f0b4559713ba4854a55149797c5ea3b0', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('f1e292823607472693b3c639c1b25fdf', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('f20406da18f549ed8d1cfdd8116749e0', '28263c4e02744e078048c96b52321a0c', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('f2556d35a1d04a53ab0d2ffdd2422231', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('f7562f269db040de9f91ee831170e54e', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('fb8edd17de69480f89e7ef8fbcd1e6fc', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_modules` VALUES ('fd80ca09e1bf471ea992c971305f6e97', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for activities_questions
-- ----------------------------
DROP TABLE IF EXISTS `activities_questions`;
CREATE TABLE `activities_questions` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `content` longtext COMMENT '题目',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取。选择、判断、填空)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动竞赛题目表';

-- ----------------------------
-- Records of activities_questions
-- ----------------------------
INSERT INTO `activities_questions` VALUES ('088be8a2bdb24c9581d74ad78357c8cd', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('094df0964ee94565bfc3da6a26cc1b33', '1ea010155a9f4cc9b097fb537eb8ff4f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('1', '1', null, null, '1', '111', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('1143c2c0d7f44542864d148d8b8597db', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('162219578a2c4314b75898e475a7f876', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('18750470a58c4408beb2ffb2b6764034', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('18cee6e8ee90440d909f42f5796b1144', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('1d2d63696e02425e8fbfb85e48b74008', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('1f1292118dca491ba2ef07010b146206', 'a64bc35e8bd243aa81ea4ab617292340', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('2', '1', null, null, '1', '2222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('20313ce9edec45349ab14165bb56e7b5', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('21a13cc4ef6943dcbe7304d884c1707d', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('2296d9b513dc4e9cb253334ab6722e14', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('27357d292e2240b2ac740417e2351190', 'eb660afb783645448a4e9568a55cdc33', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('294a63ccd553479a9222a64f3adb26d1', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('3', '1', null, null, '1', '333', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('33188b63ddbc49559bb8e039c4c59f9e', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('33f7a5a33342442f8a68061e469f7f66', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('342cbff6dba24ff2a0bc41a2bc434b41', '16b7458069474e179532a2a8b78cea0a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('34d950d7ec0e422c9f1aac6b833179ba', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('354ceb4113694c88960a22ca61e572d2', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('37e3f6f1c53442ee92403b525f78debc', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('3b80b6bcb1c14f7e93ebacf74e7d0411', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('4', '2', null, null, '1', '222', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('44a151b1df084f11a76c5cc84cc2862d', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('47ee50ea9ebb490484c8d492e02e0efe', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('4af0a82da8034208baf483e038d6d503', '57fc2e3930ca41429048c88915fb42ab', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('4bbedd250ecc4a0e98e34664ca953919', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('4e2e989f931242b0acd3e0a7f099d112', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('4ed5d42d39f64002869378a839d8d845', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('51c446c2c71349ba820ad740f9c154a7', '9f6f05bbd565485394740cbf692fd81f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('54215fdb4ffb43b79f9c82fc6fca4b2b', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('57ef1386a8134022887a333ccb4e9e8a', '082de545da024136bb545974ccd48c85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('5e0dc600e15b4f8fb56c20aee4cdf7d4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('613389e91c1941f78afe1f61b7607324', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('6460ce9c9ec944af9a23c9d8ad478ccf', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('654f0b1d899c4437938fec13be8c04de', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('68067e0b677d479cbd98578264257a76', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('6aea9e35e59642659324f9ebfdcd3bfc', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('6cb7d2e64c7a4ceb84d57596bd1ab1be', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('6e52e8f6c6f348e0976ee4363ab693c3', '05b3d9d320f6498d92b3ceec9bfc506a', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7025c724e3ba43d4814b8958d2160f23', 'f2199be2ecf342bb97314fa8767578aa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7274f65f167c4f1bb08bdda76f8b4617', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('74b26cf976e24743b806ded581371bab', 'dd82f810ba564ad39baecdec4a43070b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7545976fb6584c6b9bf3a56a83c99e5d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7aa4bf8c11634e7c9fa567cd8654c606', '5b9659d50eec4edc8455271e424e1031', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7bf14dfe65a449739df48c44b0f03374', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('7cc22f02846c485983441fda1231579e', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('824d662451a64ad2b8519a8fec157345', '33f9ab3eee894fa589e5bb6edbe43e08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('87aa71eaea3d43f69cd84922aeff8f43', 'c7b5caa70e77470e8a6f6808068f3415', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('89a6322204d742fdb8204ccc2a2a5971', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('8e6f329cf91447abbd9e7205ea3d51c3', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('8f738a95a3c04e5a9b0ea5be84e1e74d', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('904427c010b140d2a92e6457a2a031f8', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('92936bb7688348448cfb32c8f6cc9ef3', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('93b9ab8fd88c477084d7291ee705c26f', '86589aaba8604996a2229bb7d88915fe', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('93f3fac9c2544d9d8bdb1ccb0483f9f1', 'e1368766bf444728830d3505164c0fc6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('95b893e54c3344eeac8d62b7dba48782', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('9cac62f92fb7486583e7e2b034dca96e', 'b7659390175843679025cd774b71f009', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('9cb87a900ed44a93a76e467a2eedd303', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('9fbcc9ccfca24025938e702337923566', 'f91b76bf1eca47cbbb83a86de63a48cb', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('a6107709041349cebc1af207967d8a8d', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('a7cf1b41cd804049ba10288d698de975', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('ae02d1f09aa04e6aba17c0cba554dc7d', '3ef333838db24933a759c986dfbc3a08', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('b1d40c4ec7d7401aa5fbc913c6869dd3', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('b3f3d308996e44a6a54345e27b7dde63', 'f45f5dca11864538a60ca840c56ea382', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('bd44f8baa1fa46f287aad2678e75a5a0', '5e62774f1fff486db5b724a2bf904b85', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('bf22afb750ba40cd9de145813ef55c15', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('c02afbc272104104938380d57f124140', '3cd76c26168a4d80ae1954f988cf6fd2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('c619a2d7934a4db5a112a98297126cc6', '8537adb5bb734720a91665b71c72c270', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('cb97045bf49041b08d4c65def74ce68d', '9f6f13d758de4d3d9c238819483c4e49', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('ce47b00b35a340bd99f4f923ec15f990', '43a21b92e14143bb919797b91ce944dc', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('d0ea40658b554245bf6e55d04d73ad54', '2d224243c83e4fc18f6cc356463dd8b6', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('da2c523b90cf47dbb78df66e3da2d11f', '9b6fd243803748c298074d51238f045d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('dafd835f558e435395caabe9b6e85c59', '3de4003ac20943c8a519698d67774727', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('db8c48cad0f04d9d95c6df1ee5b85051', '0484fd8a47a5427d8d6f7fefcc68437e', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('dc58e48150274cd0bc674b84f8a107eb', '429d11c2d7db4b2c9951e827b99de7f5', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('de9840de913e40ef9269fc62ea7d1b71', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('e628e741cb6545cc858d858ba9bab0bb', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('ea456d96b51346ea8e98142cba913308', '31d3f04897c142878c3ccb7b5bd32332', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('ebf4515893a74c859909e21077749371', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('ec2e0e51c78e4fd19a75d4c8241bbfad', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('f0b4559713ba4854a55149797c5ea3b0', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('f1e292823607472693b3c639c1b25fdf', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('f20406da18f549ed8d1cfdd8116749e0', '28263c4e02744e078048c96b52321a0c', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('f2556d35a1d04a53ab0d2ffdd2422231', 'bcd3aa492be946108043cc0fb1c92408', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('f7562f269db040de9f91ee831170e54e', 'a6d33918eec540c5a99a47a49272957d', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('fb8edd17de69480f89e7ef8fbcd1e6fc', '15e7ab6146734b3581deea1222d700e4', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);
INSERT INTO `activities_questions` VALUES ('fd80ca09e1bf471ea992c971305f6e97', '0d0883d104354f83bd6f0574169ad3ce', null, null, '1', null, null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `version_type` varchar(100) DEFAULT NULL COMMENT '版本类型',
  `version_name` varchar(100) DEFAULT NULL COMMENT '版本号',
  `creator` varchar(100) DEFAULT NULL COMMENT '更新人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version_details` varchar(1000) DEFAULT NULL COMMENT '版本更新详情',
  `file_url` varchar(200) DEFAULT NULL COMMENT '文件地址',
  `state` int(5) DEFAULT NULL COMMENT '有效状态',
  `version_code` int(10) DEFAULT NULL COMMENT '版本更新次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app版本更新记录表';

-- ----------------------------
-- Records of app_version
-- ----------------------------
INSERT INTO `app_version` VALUES ('0aab552e56eb45918cb98e4d5030bee3', 'android', '1.1', '_admin', '2018-02-03 15:42:15', 'test', '/apk/20180203/409373020474310656.apk', '1', '2');
INSERT INTO `app_version` VALUES ('6c77a4a6361b434cbe7b9460dbf652e1', 'android', '1.1', '_admin', '2018-02-03 14:36:24', 'test', '/apk/20180203/409356445549592576.apk', '0', '2');
INSERT INTO `app_version` VALUES ('9095c060888a45a8a68a6640b0563210', 'android', '1.1', '_admin', '2018-02-03 15:19:28', 'test', '/apk/20180203/409368946853019648.apk', '0', '2');
INSERT INTO `app_version` VALUES ('bb3e67ec964f4905a61d96140c8040ca', 'android', '1.0', '_admin', '2018-01-24 12:01:26', 'APP下载', '/apk/20180124/405746017967800320.docx', '1', '1');
INSERT INTO `app_version` VALUES ('df4d7c3986ef41bb8a07206cc98a9f60', 'android', '1.2', '_admin', '2018-04-04 14:34:32', '【全新内容】\n（1）作品分享功能\n（2）用户私信功能\n【更多优化】\n（1）图片上传优化\n（2）图片显示优化\n【情感交流】\n（1）你从未如此走进艺术\n（2）不同的作品，不同的品味', '/apk/20180404/431099246435893248.apk', '1', '3');
INSERT INTO `app_version` VALUES ('f6b43e55a077416394dde3a725a72586', 'android', '1.1', '_admin', '2018-02-03 15:17:42', 'test', '/apk/20180203/409366843480866816.apk', '0', '1');

-- ----------------------------
-- Table structure for corridors
-- ----------------------------
DROP TABLE IF EXISTS `corridors`;
CREATE TABLE `corridors` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容',
  `image` varchar(255) DEFAULT NULL COMMENT '图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='长廊表';

-- ----------------------------
-- Records of corridors
-- ----------------------------
INSERT INTO `corridors` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `corridors` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `corridors` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for coursewares
-- ----------------------------
DROP TABLE IF EXISTS `coursewares`;
CREATE TABLE `coursewares` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '标题',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `files` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学习-课件表';

-- ----------------------------
-- Records of coursewares
-- ----------------------------
INSERT INTO `coursewares` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `coursewares` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `coursewares` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `username` varchar(50) DEFAULT NULL COMMENT '登录名(手机号)',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `ali_id` varchar(50) DEFAULT NULL COMMENT '支付宝登录标识open_id',
  `wechat_id` varchar(50) DEFAULT NULL COMMENT '微信登录标识open_id',
  `nikename` varchar(50) DEFAULT NULL COMMENT '用户名',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` int(5) DEFAULT NULL COMMENT '用户状态（0-删除，1-正常）',
  `pay_password` varchar(50) DEFAULT NULL COMMENT '支付密码',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', 'cc', '', null, null, 'cc', null, '测试', null, null, '1', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_activities
-- ----------------------------
DROP TABLE IF EXISTS `customer_activities`;
CREATE TABLE `customer_activities` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `activities_id` varchar(50) NOT NULL COMMENT '活动id',
  `state` char(2) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的活动表';

-- ----------------------------
-- Records of customer_activities
-- ----------------------------

-- ----------------------------
-- Table structure for customer_activities_answer
-- ----------------------------
DROP TABLE IF EXISTS `customer_activities_answer`;
CREATE TABLE `customer_activities_answer` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `total` int(12) DEFAULT NULL COMMENT '共答题',
  `correct` int(12) DEFAULT NULL COMMENT '正确',
  `rate` varchar(20) DEFAULT NULL COMMENT '正确率',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的活动-知识竞赛结果表';

-- ----------------------------
-- Records of customer_activities_answer
-- ----------------------------
INSERT INTO `customer_activities_answer` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('1', '1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('2', '1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('3', '2', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_activities_answer` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_addresses
-- ----------------------------
DROP TABLE IF EXISTS `customer_addresses`;
CREATE TABLE `customer_addresses` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '许可证照片',
  `province` varchar(50) DEFAULT NULL COMMENT '省',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `district` varchar(50) DEFAULT NULL COMMENT '区域',
  `detailed` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `type` varchar(32) DEFAULT NULL COMMENT '标签(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常，2-默认）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';

-- ----------------------------
-- Records of customer_addresses
-- ----------------------------
INSERT INTO `customer_addresses` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('1', '1', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('2', '1', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('3', '2', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_addresses` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_authentications
-- ----------------------------
DROP TABLE IF EXISTS `customer_authentications`;
CREATE TABLE `customer_authentications` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `licences` varchar(100) DEFAULT NULL COMMENT '许可证号码',
  `licences_img` varchar(255) DEFAULT NULL COMMENT '许可证照片',
  `id_name` varchar(50) DEFAULT NULL COMMENT '身份证姓名',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `id_img` varchar(255) DEFAULT NULL COMMENT '身份证照片',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证表';

-- ----------------------------
-- Records of customer_authentications
-- ----------------------------
INSERT INTO `customer_authentications` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('1', '1', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('2', '1', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('3', '2', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_authentications` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, null, null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_collects
-- ----------------------------
DROP TABLE IF EXISTS `customer_collects`;
CREATE TABLE `customer_collects` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取。资讯、课件等。再由对应表的type关联上级字典属性x信息)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的收藏表';

-- ----------------------------
-- Records of customer_collects
-- ----------------------------
INSERT INTO `customer_collects` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('1', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('2', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('3', '2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_collects` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_feedback
-- ----------------------------
DROP TABLE IF EXISTS `customer_feedback`;
CREATE TABLE `customer_feedback` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `content` longtext COMMENT '内容',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈表';

-- ----------------------------
-- Records of customer_feedback
-- ----------------------------
INSERT INTO `customer_feedback` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('1', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('2', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('3', '2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_feedback` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_likes
-- ----------------------------
DROP TABLE IF EXISTS `customer_likes`;
CREATE TABLE `customer_likes` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取。资讯、课件等)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of customer_likes
-- ----------------------------
INSERT INTO `customer_likes` VALUES ('088be8a2bdb24c9581d74ad78357c8cd', 'f2199be2ecf342bb97314fa8767578aa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('094df0964ee94565bfc3da6a26cc1b33', '1ea010155a9f4cc9b097fb537eb8ff4f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('1', '1', null, '1', '111', '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('1143c2c0d7f44542864d148d8b8597db', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('162219578a2c4314b75898e475a7f876', '57fc2e3930ca41429048c88915fb42ab', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('18750470a58c4408beb2ffb2b6764034', 'fde9ceeb1d9a47448cdd92548e5419a0', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('18cee6e8ee90440d909f42f5796b1144', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('1d2d63696e02425e8fbfb85e48b74008', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('1f1292118dca491ba2ef07010b146206', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('2', '1', null, '1', '2222', '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('20313ce9edec45349ab14165bb56e7b5', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('21a13cc4ef6943dcbe7304d884c1707d', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('2296d9b513dc4e9cb253334ab6722e14', '86589aaba8604996a2229bb7d88915fe', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('27357d292e2240b2ac740417e2351190', 'eb660afb783645448a4e9568a55cdc33', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('294a63ccd553479a9222a64f3adb26d1', '1ac837ba8aae4c85a7611e57c0c83ecb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('3', '1', null, '1', '333', '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('33188b63ddbc49559bb8e039c4c59f9e', '86589aaba8604996a2229bb7d88915fe', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('33f7a5a33342442f8a68061e469f7f66', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('342cbff6dba24ff2a0bc41a2bc434b41', '16b7458069474e179532a2a8b78cea0a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('34d950d7ec0e422c9f1aac6b833179ba', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('354ceb4113694c88960a22ca61e572d2', 'd8d6d47fb3d04903a279b5cf334e8c77', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('37e3f6f1c53442ee92403b525f78debc', '86589aaba8604996a2229bb7d88915fe', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('3b80b6bcb1c14f7e93ebacf74e7d0411', 'c57d7baf17c94bd59583a0beeb0c581a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('4', '2', null, '1', '222', '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('44a151b1df084f11a76c5cc84cc2862d', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('47ee50ea9ebb490484c8d492e02e0efe', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('4af0a82da8034208baf483e038d6d503', '57fc2e3930ca41429048c88915fb42ab', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('4bbedd250ecc4a0e98e34664ca953919', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('4e2e989f931242b0acd3e0a7f099d112', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('4ed5d42d39f64002869378a839d8d845', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('51c446c2c71349ba820ad740f9c154a7', '9f6f05bbd565485394740cbf692fd81f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('54215fdb4ffb43b79f9c82fc6fca4b2b', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('57ef1386a8134022887a333ccb4e9e8a', '082de545da024136bb545974ccd48c85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('5e0dc600e15b4f8fb56c20aee4cdf7d4', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('613389e91c1941f78afe1f61b7607324', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('6460ce9c9ec944af9a23c9d8ad478ccf', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('654f0b1d899c4437938fec13be8c04de', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('68067e0b677d479cbd98578264257a76', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('6aea9e35e59642659324f9ebfdcd3bfc', '33f9ab3eee894fa589e5bb6edbe43e08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('6cb7d2e64c7a4ceb84d57596bd1ab1be', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('6e52e8f6c6f348e0976ee4363ab693c3', '05b3d9d320f6498d92b3ceec9bfc506a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7025c724e3ba43d4814b8958d2160f23', 'f2199be2ecf342bb97314fa8767578aa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7274f65f167c4f1bb08bdda76f8b4617', 'dd82f810ba564ad39baecdec4a43070b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('74b26cf976e24743b806ded581371bab', 'dd82f810ba564ad39baecdec4a43070b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7545976fb6584c6b9bf3a56a83c99e5d', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7aa4bf8c11634e7c9fa567cd8654c606', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7bf14dfe65a449739df48c44b0f03374', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('7cc22f02846c485983441fda1231579e', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('824d662451a64ad2b8519a8fec157345', '33f9ab3eee894fa589e5bb6edbe43e08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('87aa71eaea3d43f69cd84922aeff8f43', 'c7b5caa70e77470e8a6f6808068f3415', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('89a6322204d742fdb8204ccc2a2a5971', '1ac837ba8aae4c85a7611e57c0c83ecb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('8e6f329cf91447abbd9e7205ea3d51c3', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('8f738a95a3c04e5a9b0ea5be84e1e74d', '8537adb5bb734720a91665b71c72c270', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('904427c010b140d2a92e6457a2a031f8', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('92936bb7688348448cfb32c8f6cc9ef3', '429d11c2d7db4b2c9951e827b99de7f5', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('93b9ab8fd88c477084d7291ee705c26f', '86589aaba8604996a2229bb7d88915fe', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('93f3fac9c2544d9d8bdb1ccb0483f9f1', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('95b893e54c3344eeac8d62b7dba48782', '429d11c2d7db4b2c9951e827b99de7f5', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('9cac62f92fb7486583e7e2b034dca96e', 'b7659390175843679025cd774b71f009', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('9cb87a900ed44a93a76e467a2eedd303', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('9fbcc9ccfca24025938e702337923566', 'f91b76bf1eca47cbbb83a86de63a48cb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('a6107709041349cebc1af207967d8a8d', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('a7cf1b41cd804049ba10288d698de975', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('ae02d1f09aa04e6aba17c0cba554dc7d', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('b1d40c4ec7d7401aa5fbc913c6869dd3', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('b3f3d308996e44a6a54345e27b7dde63', 'f45f5dca11864538a60ca840c56ea382', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('bd44f8baa1fa46f287aad2678e75a5a0', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('bf22afb750ba40cd9de145813ef55c15', 'c7cbea3640974a16be90ab8cc7c7a12b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('c02afbc272104104938380d57f124140', '3cd76c26168a4d80ae1954f988cf6fd2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('c619a2d7934a4db5a112a98297126cc6', '8537adb5bb734720a91665b71c72c270', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('cb97045bf49041b08d4c65def74ce68d', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('ce47b00b35a340bd99f4f923ec15f990', '43a21b92e14143bb919797b91ce944dc', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('d0ea40658b554245bf6e55d04d73ad54', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('da2c523b90cf47dbb78df66e3da2d11f', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('dafd835f558e435395caabe9b6e85c59', '3de4003ac20943c8a519698d67774727', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('db8c48cad0f04d9d95c6df1ee5b85051', '0484fd8a47a5427d8d6f7fefcc68437e', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('dc58e48150274cd0bc674b84f8a107eb', '429d11c2d7db4b2c9951e827b99de7f5', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('de9840de913e40ef9269fc62ea7d1b71', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('e628e741cb6545cc858d858ba9bab0bb', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('ea456d96b51346ea8e98142cba913308', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('ebf4515893a74c859909e21077749371', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('ec2e0e51c78e4fd19a75d4c8241bbfad', 'c7cbea3640974a16be90ab8cc7c7a12b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('f0b4559713ba4854a55149797c5ea3b0', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('f1e292823607472693b3c639c1b25fdf', 'df97f80b0d92497b8cd5295f11b97eaa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('f20406da18f549ed8d1cfdd8116749e0', '28263c4e02744e078048c96b52321a0c', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('f2556d35a1d04a53ab0d2ffdd2422231', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('f7562f269db040de9f91ee831170e54e', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('fb8edd17de69480f89e7ef8fbcd1e6fc', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_likes` VALUES ('fd80ca09e1bf471ea992c971305f6e97', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_messages
-- ----------------------------
DROP TABLE IF EXISTS `customer_messages`;
CREATE TABLE `customer_messages` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的消息表';

-- ----------------------------
-- Records of customer_messages
-- ----------------------------
INSERT INTO `customer_messages` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `customer_messages` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `customer_messages` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for customer_points
-- ----------------------------
DROP TABLE IF EXISTS `customer_points`;
CREATE TABLE `customer_points` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `point` int(12) DEFAULT NULL COMMENT '总积分',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分表';

-- ----------------------------
-- Records of customer_points
-- ----------------------------
INSERT INTO `customer_points` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('1', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('2', '1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('3', '2', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for customer_points_details
-- ----------------------------
DROP TABLE IF EXISTS `customer_points_details`;
CREATE TABLE `customer_points_details` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(50) NOT NULL COMMENT '关联id',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取。积分获取来源)',
  `signs` char(2) DEFAULT NULL COMMENT '加减符号(0-减，1-加)',
  `point` int(12) DEFAULT NULL COMMENT '积分',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分明细表';

-- ----------------------------
-- Records of customer_points_details
-- ----------------------------
INSERT INTO `customer_points_details` VALUES ('033145286f8e422bbb940163b11db29e', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('03716b9281a648f3bba7886d6e62b415', 'aa5b9b21fc724dd18d0abccbbec5f5f2', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('04e8f5bc4a5e458ea5d8ea86abc416f7', 'df97f80b0d92497b8cd5295f11b97eaa', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('0a321e4030354a8fa0c7d4cf23727543', 'c7cbea3640974a16be90ab8cc7c7a12b', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('0b41b2b8eff74c9aa4d112c9c969c6f1', 'dd82f810ba564ad39baecdec4a43070b', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('0be2ac8abb98460e8f04b70807a9edc8', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('0d2f6bc040884ac6bd634255df03d1d1', '43ce78a1ae1047e48fb29a4cbf0e3e12', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('0f09e8d32f704728b16825c0b1a943ee', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('1', '1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('13423fdc5c674fd1a1a5ae80c6d60881', 'e1368766bf444728830d3505164c0fc6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('1898ebbeb0e64754ac97dd8407bf94d7', 'e1368766bf444728830d3505164c0fc6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('18a2ef80863c43ecab103703075268ef', '5b9659d50eec4edc8455271e424e1031', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('2', '1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('2e2981aaf4fb48a0b8177cf1e981406a', 'a6d33918eec540c5a99a47a49272957d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('3', '2', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('3726d8d494be4657ae41d76aae60747a', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('398f0b3dcf764e018ee18ec9b0d43fdb', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('3c2314db68fe47b6b14c824ef14787d0', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('402ec17febc54be593f693cc99cedcbc', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('46b4fbc730874515bf94e4343fef1c5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('49c709e71feb4f5286da57658f669328', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('50fd35f885924455888c53cafb3af6d5', '9b6fd243803748c298074d51238f045d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('51bdaebcea2e4c9cb4253ffd7f188897', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('5270ac2199114b99bc525cb6c1af316e', 'f2199be2ecf342bb97314fa8767578aa', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('5ba92ae7bccb4a63b09e81660f38c5a1', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('5c0b403883c1458e9f8805a12cdfbd11', '1ac837ba8aae4c85a7611e57c0c83ecb', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6684b1da50aa414c9d76da5e4a4346d3', '28263c4e02744e078048c96b52321a0c', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6908e2420bd34294a637f2cfab869b5b', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6afdbfe231f64da581b48176a6129f92', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6c46b4fd96524d91bc468b18609ba1d1', 'a6d33918eec540c5a99a47a49272957d', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6cc4e68f75f04e3a836471ed099e15a9', '31d3f04897c142878c3ccb7b5bd32332', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('6f6921ff93514c8a804f58042169f51b', 'fde9ceeb1d9a47448cdd92548e5419a0', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('702550ee6fb8423e818cd36b773f4c2e', '9f6f13d758de4d3d9c238819483c4e49', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('71cbe11bc7514d22ae67fa50ef7f32f2', '443cc918dc924712a1f2b251921a5ef8', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('76da99466a2349a3b97e6f5149e4c516', 'c57d7baf17c94bd59583a0beeb0c581a', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('83243d80e2ab47efb92f0ed945aeedd3', '43a21b92e14143bb919797b91ce944dc', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('854e2be516854df1a919fbf0ff571140', 'f45f5dca11864538a60ca840c56ea382', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('86971d1c788c495785de196ba5bac669', '16b7458069474e179532a2a8b78cea0a', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('8fbfe69cc5e34c5eb62a972c5a31f357', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('906420a65b8f472fb0cdb101c14e98e5', '57fc2e3930ca41429048c88915fb42ab', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('95586c46343a4f68a4932e2595d7c97b', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('9888218cd8a444f18a2da4e4be6b7f44', 'bcd3aa492be946108043cc0fb1c92408', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('9c9915d9db104a83856cfbf160c87377', 'd4083292b61c4adfb01a4741ac0a58ff', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('a47b6660db044764aa7ad1bcacce5d2f', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('ab96289b42fa42f8910c7e6b6418e63d', '5b9659d50eec4edc8455271e424e1031', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('ae424d51b89a4ee399f965fea022ead9', '15e7ab6146734b3581deea1222d700e4', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('af000314414d4eb7aa468af98781933b', '80d7fa7f5d6147b58a1c34b6d697a5cf', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('b1771ed589354cedbccc467e4f6f246c', '4fa614cb129f4b4dbc1902a5a93b097f', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('b99734950d36444eaaaaf3f8ed500fbb', '5e62774f1fff486db5b724a2bf904b85', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('be373eb89ba04faa8fc5332e857f9c69', '3ef333838db24933a759c986dfbc3a08', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('c02186f6248b4fe7b21ea3bf26eba2f4', 'f2f06c98aaf9487fbd44898dd6e9b8a1', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('ca857b01f8304f17806a1073bf7f85f1', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('d82e259129e44bf99f4ea25f95a31ba6', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('dde1b057a25f4c7cb70946cfbf40afb1', 'eb6e3a9fd3174c75a412ac43a6fd9095', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('e4ab438e062947a38142f37f3f6fca1f', '0d0883d104354f83bd6f0574169ad3ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('e9a0721f2ddc4dba89f20e9c28a05960', 'a64bc35e8bd243aa81ea4ab617292340', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('ef2a89b08df546a680dd9c8dcf61fca3', 'd8d6d47fb3d04903a279b5cf334e8c77', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('f3a8b065e5e144efb233925f1cc5e630', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('f50c4678970a41c980373380992c3885', '00634bc814ff4be7bdc93b98f1e726ce', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);
INSERT INTO `customer_points_details` VALUES ('ffaaa75cf2ee46a6926198c92c3c55e4', '2d224243c83e4fc18f6cc356463dd8b6', null, null, null, '1', null, '0', '0', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `ordinal` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `id_path` varchar(2000) CHARACTER SET utf8 DEFAULT '',
  `deleted` int(11) NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('D001746', 'D005341', '目标责任考核小组', '目标责任考核小组', '2', '/D273322/D005341/D001746/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D003040', 'D396674', '总行营业部负责人', '总行营业部负责人', '00', '/D273322/D396674/D003040/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D003950', 'D048252', '贷后管理中心', '贷后管理中心', '31', '/D273322/D048252/D003950/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D004318', 'D273322', '纪检监察室', '纪检监察室', '240', '/D273322/D004318/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D005341', 'D273322', '战略规划暨目标责任考核办公室', '战略规划暨目标责任考核办公室', '109', '/D273322/D005341/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D014682', 'D396674', '产品管理团队', '产品管理团队', '07', '/D273322/D396674/D014682/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D017440', 'D116516', '互联网金融部', '互联网金融部', '239', '/D273322/D116516/D017440/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D035183', 'D273322', '直销银行部', '直销银行部', '180', '/D273322/D035183/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D035197', 'D273322', '董事会办公室', '董事会办公室', '02', '/D273322/D035197/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D038327', 'D273322', '零售业务部', '零售业务部', '110', '/D273322/D038327/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D038940', 'D273322', '审计稽核部', '审计稽核部', '120', '/D273322/D038940/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D042376', 'D257938', '运营作业中心', '运营作业中心', '33', '/D273322/D257938/D042376/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D048252', 'D273322', '信贷管理部', '信贷管理部', '08', '/D273322/D048252/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D050064', 'D396674', '公司业务部', '公司业务部', '02', '/D273322/D396674/D050064/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D063135', 'D273322', '授信审批部', '授信审批部', '140', '/D273322/D063135/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D070108', 'D273322', '行政服务部', '行政服务部', '250', '/D273322/D070108/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D075804', 'D273322', '机构管理部', '机构管理部', '190', '/D273322/D075804/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D080663', 'D273322', '国际业务部', '国际业务部', '210', '/D273322/D080663/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D083596', 'D396674', '外汇系统管理部', '外汇系统管理部', '05', '/D273322/D396674/D083596/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D090177', 'D273322', '保卫部', '保卫部', '220', '/D273322/D090177/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D098019', 'D273322', '党委办公室', '党委办公室', '01', '/D273322/D098019/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D109313', 'D150369', '资产管理中心', '资产管理中心', '01', '/D273322/D150369/D109313/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D114013', 'D273322', '组织-人力资源部', '组织-人力资源部', '05', '/D273322/D114013/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D116516', 'D273322', '科技部', '科技部', '248', '/D273322/D116516/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D131951', 'D273322', '行领导', '行领导', '0', '/D273322/D131951/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D133560', 'D273322', '工会办公室', '工会办公室', '259', '/D273322/D133560/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D150369', 'D273322', '合规部', '合规部', '160', '/D273322/D150369/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D153894', 'D273322', '风险管理部', '风险管理部', '130', '/D273322/D153894/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D158641', 'D273322', '投贷联动领导小组办公室', '投贷联动领导小组办公室', '249', '/D273322/D158641/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D160383', 'D273322', '行长办公室', '行长办公室', '04', '/D273322/D160383/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D167364', 'D396674', '综合办公室', '综合办公室', '01', '/D273322/D396674/D167364/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D176763', 'D038327', '信用卡中心', '信用卡中心', '01', '/D273322/D038327/D176763/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D201035', 'D273322', '研究发展部', '研究发展部', '245', '/D273322/D201035/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D205619', 'D396674', '市场管理部', '市场管理部', '04', '/D273322/D396674/D205619/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D208730', 'D273322', '金融市场部', '金融市场部', '100', '/D273322/D208730/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D238882', 'D257938', '消费者权益保护及客户服务中心', '消费者权益保护及客户服务中心', '35', '/D273322/D257938/D238882/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D249778', 'D257938', '反洗钱中心', '反洗钱中心', '34', '/D273322/D257938/D249778/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D257938', 'D273322', '运营管理部', '运营管理部', '10', '/D273322/D257938/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D273322', '', '总行', '银行', '00', '/D273322/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D275558', 'D273322', '小企业部', '小企业部', '10', '/D273322/D275558/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D342593', 'D273322', '计划财务部', '计划财务部', '07', '/D273322/D342593/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D361432', 'D257938', '现金管理中心', '现金管理中心', '3', '/D273322/D257938/D361432/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D375840', 'D114013', '培训中心', '培训中心', '36', '/D273322/D114013/D375840/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D376988', 'D273322', '全面风险管理项目建设办公室', '全面风险管理项目建设办公室', '135', '/D273322/D376988/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D396674', 'D273322', '总行营业部', '总行营业部', '260', '/D273322/D396674/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D405628', 'D396674', '零售业务部', '零售业务部', '03', '/D273322/D396674/D405628/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D417658', 'D273322', '机关工会', '机关工会', '259', '/D273322/D417658/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D421354', 'D273322', '投资银行部', '投资银行部', '170', '/D273322/D421354/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D434195', 'D273322', '公司业务部', '公司业务部', '09', '/D273322/D434195/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D449605', 'D273322', '基建办公室', '基建办公室', '150', '/D273322/D449605/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D500388', 'D273322', '监事会办公室', '监事会办公室', '03', '/D273322/D500388/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D516656', 'D005341', '项目管理小组', '项目管理小组', '1', '/D273322/D005341/D516656/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D535878', 'D273322', '团委', '团委', '01', '/D273322/D535878/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D538841', 'D396674', '国际结算单证中心', '国际结算单证中心', '06', '/D273322/D396674/D538841/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D541278', 'D070108', '物业管理中心', '物业管理中心', '1', '/D273322/D070108/D541278/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');
INSERT INTO `department` VALUES ('D847623', 'D396674', '营业室', '营业室', '08', '/D273322/D396674/D847623/', '0', null, '2018-10-26 11:18:23', null, '2018-12-27 17:25:12');

-- ----------------------------
-- Table structure for dic
-- ----------------------------
DROP TABLE IF EXISTS `dic`;
CREATE TABLE `dic` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `target_id` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `codename` varchar(50) DEFAULT NULL COMMENT '编码名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `state` int(5) DEFAULT NULL COMMENT '记录状态（1有效，0无效）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典分类表';

-- ----------------------------
-- Records of dic
-- ----------------------------
INSERT INTO `dic` VALUES ('2019072517213617700002', null, 'focusNews', '要闻', '资讯版块', '2019-07-25 17:21:36', '0', '1');

-- ----------------------------
-- Table structure for dic_type
-- ----------------------------
DROP TABLE IF EXISTS `dic_type`;
CREATE TABLE `dic_type` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `code` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `codename` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `state` int(5) DEFAULT NULL COMMENT '记录状态（1有效，0无效）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典类别表';

-- ----------------------------
-- Records of dic_type
-- ----------------------------
INSERT INTO `dic_type` VALUES ('2019072517143077500002', 'information', '资讯', '资讯', '2019-07-25 17:14:31', '0', '1');

-- ----------------------------
-- Table structure for hundred_dedicates
-- ----------------------------
DROP TABLE IF EXISTS `hundred_dedicates`;
CREATE TABLE `hundred_dedicates` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容(诗歌)',
  `image` varchar(255) DEFAULT NULL COMMENT '图片(","分割，书画、摄影)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频(摄影)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='百作献鲁烟表';

-- ----------------------------
-- Records of hundred_dedicates
-- ----------------------------
INSERT INTO `hundred_dedicates` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `hundred_dedicates` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `hundred_dedicates` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for hundred_events
-- ----------------------------
DROP TABLE IF EXISTS `hundred_events`;
CREATE TABLE `hundred_events` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `content` longtext COMMENT '内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='百事记鲁烟表';

-- ----------------------------
-- Records of hundred_events
-- ----------------------------
INSERT INTO `hundred_events` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, '', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `hundred_events` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, '<p>122</p><figure class=\"image\"><img src=\"http://localhost:8085/app/upload/preview?filePath=/village/20181014/501146522516193280.jpg\"></figure>', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `hundred_events` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, '', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for hundred_memories
-- ----------------------------
DROP TABLE IF EXISTS `hundred_memories`;
CREATE TABLE `hundred_memories` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='百人忆鲁烟表';

-- ----------------------------
-- Records of hundred_memories
-- ----------------------------
INSERT INTO `hundred_memories` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `hundred_memories` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `hundred_memories` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for hundred_talks
-- ----------------------------
DROP TABLE IF EXISTS `hundred_talks`;
CREATE TABLE `hundred_talks` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='百客话鲁烟表';

-- ----------------------------
-- Records of hundred_talks
-- ----------------------------
INSERT INTO `hundred_talks` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `hundred_talks` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `hundred_talks` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '标题',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `content` longtext COMMENT '内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常，2-隐藏）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='咨讯表';

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, '', '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `information` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, '<p>122</p><figure class=\"image\"><img src=\"http://localhost:8085/app/upload/preview?filePath=/village/20181014/501146522516193280.jpg\"></figure>', '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '0', '2019-07-22 17:42:41', null, null);
INSERT INTO `information` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, '', '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for knowledges
-- ----------------------------
DROP TABLE IF EXISTS `knowledges`;
CREATE TABLE `knowledges` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '标题',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割)',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '基本信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识表';

-- ----------------------------
-- Records of knowledges
-- ----------------------------
INSERT INTO `knowledges` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `knowledges` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `knowledges` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for live
-- ----------------------------
DROP TABLE IF EXISTS `live`;
CREATE TABLE `live` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容',
  `image` varchar(255) DEFAULT NULL COMMENT '图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频(存储第三方平台直播或视频地址)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常，2-隐藏）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播、回顾表';

-- ----------------------------
-- Records of live
-- ----------------------------
INSERT INTO `live` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `live` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `live` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for microvideo
-- ----------------------------
DROP TABLE IF EXISTS `microvideo`;
CREATE TABLE `microvideo` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '内容',
  `image` varchar(255) DEFAULT NULL COMMENT '图片(","分割)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常，2-隐藏）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `browses` int(12) DEFAULT NULL COMMENT '浏览次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微视表';

-- ----------------------------
-- Records of microvideo
-- ----------------------------
INSERT INTO `microvideo` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `microvideo` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '/village/20181014/501160273252450304.jpg', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `microvideo` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '', null, '1', null, null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for op_log
-- ----------------------------
DROP TABLE IF EXISTS `op_log`;
CREATE TABLE `op_log` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `url` varchar(4000) CHARACTER SET utf8 NOT NULL,
  `comment` varchar(4000) CHARACTER SET utf8 NOT NULL,
  `user_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_ip` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of op_log
-- ----------------------------

-- ----------------------------
-- Table structure for panorama
-- ----------------------------
DROP TABLE IF EXISTS `panorama`;
CREATE TABLE `panorama` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割.1-列表图,2-主页图)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '基本信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全景表';

-- ----------------------------
-- Records of panorama
-- ----------------------------
INSERT INTO `panorama` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, null, '', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `panorama` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, null, '<p>122</p><figure class=\"image\"><img src=\"http://localhost:8085/app/upload/preview?filePath=/village/20181014/501146522516193280.jpg\"></figure>', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `panorama` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, null, '', '1', null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `type` int(11) NOT NULL,
  `value` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `ordinal` varchar(20) CHARACTER SET utf8 NOT NULL,
  `id_path` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('10000', '0', '系统管理', '', '1', '/system/system-index.html', 'xit', '900', '/10000/', null, null, null, null);
INSERT INTO `permission` VALUES ('10100', '10000', '用户管理', '', '2', '/system/user/user-list.html', 'icon-yonghuguanli', '10', '/10000/10100/', null, null, null, null);
INSERT INTO `permission` VALUES ('10200', '10000', '角色管理', ' ', '2', '/system/role/role-list.html', 'icon-wulumuqishigongandashujuguanlipingtai-ico-', '40', '/10000/10600/', null, null, null, null);
INSERT INTO `permission` VALUES ('10300', '60000', '部门管理', '', '2', '/system/department/department-index.html', 'icon-daibanshixiang1', '50', '/10000/10700/', null, null, null, null);
INSERT INTO `permission` VALUES ('10400', '10000', '数据字典类别管理', '', '2', '/system/dic/dic-type-list.html', 'icon-yuanshujupeizhi', '50', '/10000/10400/', '$1', '2019-07-25 16:40:55', '$1', '2019-07-25 16:40:58');
INSERT INTO `permission` VALUES ('10500', '10000', '数据字典管理', '', '2', '/system/dic/dic-list.html', 'icon-yuanshujupeizhi', '60', '/10000/10500/', '$1', '2019-07-25 16:40:55', '$1', '2019-07-25 16:40:58');
INSERT INTO `permission` VALUES ('10800', '10000', '操作日志', '', '2', '/system/op-log/op-log-index.html', 'icon-rizhi', '70', '/10000/10800/', null, null, null, null);
INSERT INTO `permission` VALUES ('20000', '0', '资讯管理', '', '1', '/yll/information/information-index.html', 'zix', '100', '/20000/', null, null, null, null);
INSERT INTO `permission` VALUES ('20100', '20000', '资讯', '', '2', '/yll/information/information-list.html', '', '10', '/20000/20100/', null, null, null, null);
INSERT INTO `permission` VALUES ('30000', '0', '学习管理', '', '1', '/system/system-index.html', 'xuex', '200', '/30000/', null, null, null, null);
INSERT INTO `permission` VALUES ('30100', '30000', '产品资料', '', '2', '/expense/fee-rule/fee-rule-index.html', '', '1', '/30000/30100/', null, null, null, null);
INSERT INTO `permission` VALUES ('30200', '30000', '培训课件', '', '2', '/expense/expense-log/expense-log-index.html', '', '2', '/30000/30200/', null, null, null, null);
INSERT INTO `permission` VALUES ('30300', '30000', '烟草知识', '', '2', '/expense/fee-account/fee-account-index.html', '', '3', '/30000/30300/', null, null, null, null);
INSERT INTO `permission` VALUES ('40000', '0', '活动管理', '', '1', '/system/system-index.html', 'huod', '200', '/40000/', null, null, null, null);
INSERT INTO `permission` VALUES ('40100', '40000', '活动', '', '2', '/search/data-search/data-search-index.html', '', '10', '/40000/40100/', null, null, null, null);
INSERT INTO `permission` VALUES ('50000', '0', '专区管理', '', '1', '/system/system-index.html', 'zhuanq', '300', '/40000/', '$1', '2019-07-16 18:12:57', '$1', '2019-07-16 18:13:00');
INSERT INTO `permission` VALUES ('50100', '50000', '全景管理', '', '2', '/search/data-search-log/data-search-log.html', '', '20', '/40000/40200/', null, null, null, null);
INSERT INTO `permission` VALUES ('50200', '50000', '百事记鲁烟', '', '2', '/search/data-permit/todo-data-permit-self.html', '', '30', '/40000/40300/', null, null, null, null);
INSERT INTO `permission` VALUES ('50300', '50000', '百人忆鲁烟', '', '2', '/interface/fetcher-driver/fetcher-driver-index.html', '', '20', '/20000/20200/', null, null, null, null);
INSERT INTO `permission` VALUES ('50400', '50000', '百客话鲁烟', '', '2', '/interface/exporter/exporter-index.html', '', '40', '/20000/20400/', null, null, null, null);
INSERT INTO `permission` VALUES ('50500', '50000', '百作献鲁烟', '', '2', '/interface/sched-job/sched-job-index.html', '', '70', '/20000/20700/', null, null, null, null);
INSERT INTO `permission` VALUES ('50600', '50000', '百演献鲁烟', '', '2', '/interface/sched-job/sched-job-index.html', '', '70', '/20000/20700/', '$1', '2019-07-16 18:24:30', '$1', '2019-07-16 18:24:32');
INSERT INTO `permission` VALUES ('60000', '0', '党群工作管理', '', '1', '/system/system-index.html', 'dangqgz', '400', '/40000/', '$1', '2019-07-16 18:12:57', '$1', '2019-07-16 18:13:00');
INSERT INTO `permission` VALUES ('70000', '0', '直播管理', '', '1', '/system/system-index.html', 'zhib', '500', '/40000/', '$1', '2019-07-16 18:12:57', '$1', '2019-07-16 18:13:00');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(150) DEFAULT NULL COMMENT '名称',
  `image` varchar(255) DEFAULT NULL COMMENT '列表预览图片(","分割.1-列表图,2-主页图)',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `profile` varchar(255) DEFAULT NULL COMMENT '简介',
  `content` longtext COMMENT '基本信息',
  `price` decimal(2,0) DEFAULT NULL COMMENT '零售价',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(字典表获取)',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常，2-隐藏）',
  `share` int(12) DEFAULT NULL COMMENT '分享次数',
  `collects` int(12) DEFAULT NULL COMMENT '收藏次数',
  `likes` int(12) DEFAULT NULL COMMENT '点赞次数',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', '', null, null, '', null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `products` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', '/village/20181014/501160273252450304.jpg', null, null, '<p>122</p><figure class=\"image\"><img src=\"http://localhost:8085/app/upload/preview?filePath=/village/20181014/501146522516193280.jpg\"></figure>', null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `products` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', '', null, null, '', null, '1', null, null, null, null, null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for products_params
-- ----------------------------
DROP TABLE IF EXISTS `products_params`;
CREATE TABLE `products_params` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `target_id` varchar(32) NOT NULL COMMENT '关联id',
  `type` varchar(32) DEFAULT NULL COMMENT '参数类型(字典表获取)',
  `content` varchar(100) DEFAULT NULL COMMENT '参数值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` int(5) DEFAULT NULL COMMENT '状态（0-删除，1-正常）',
  `ordinal` int(12) DEFAULT NULL COMMENT '排序',
  `enabled` int(12) NOT NULL COMMENT '是否可用',
  `deleted` int(12) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改者',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleter` varchar(36) DEFAULT NULL COMMENT '删除者',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品详细参数表';

-- ----------------------------
-- Records of products_params
-- ----------------------------
INSERT INTO `products_params` VALUES ('21c82023f522442b99b7da2b3dab1bf5', '11', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:42:50', null, null, null, null);
INSERT INTO `products_params` VALUES ('4938819b06f94c0ebe2402458237d3a6', '1', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:43:36', '_admin', '2018-10-15 11:05:04', null, null);
INSERT INTO `products_params` VALUES ('7035c40e88e846d9b2faf3072006397f', 'guoqing', null, null, '1', null, null, '1', '0', '_admin', '2018-10-14 19:39:41', null, null, null, null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0000000000000000010001', '系统管理员', 'UAT测试增加,勿删。', '0', '1982-12-11 00:00:00', '0', '2018-12-27 14:01:14');
INSERT INTO `role` VALUES ('0000000000000000010002', '业务管理员', 'UAT测试增加,勿删。', '0', '1982-12-12 00:00:00', '0', '2018-12-27 14:01:11');
INSERT INTO `role` VALUES ('0000000000000000010003', '业务用户', 'UAT测试增加,勿删。能够使用外部数据服务以及查看数据的用户', '0', '2018-12-27 09:50:36', '0', '2018-12-27 14:01:08');
INSERT INTO `role` VALUES ('0000000000000000090000', '测试角色α', '不要删', '0', '1982-12-13 00:00:00', null, null);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 NOT NULL DEFAULT '1',
  `permission_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('2018122709191648300004', '0000000000000000010002', '40000', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400004', '0000000000000000010002', '40100', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400104', '0000000000000000010002', '40200', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400204', '0000000000000000010002', '40300', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400304', '0000000000000000010002', '40400', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400404', '0000000000000000010002', '40500', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400504', '0000000000000000010002', '30000', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400604', '0000000000000000010002', '30100', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400704', '0000000000000000010002', '30200', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400804', '0000000000000000010002', '30300', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648400904', '0000000000000000010002', '20000', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401004', '0000000000000000010002', '20100', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401104', '0000000000000000010002', '20200', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401204', '0000000000000000010002', '20400', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401304', '0000000000000000010002', '20500', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401404', '0000000000000000010002', '20600', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401604', '0000000000000000010002', '10100', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401704', '0000000000000000010002', '10500', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401804', '0000000000000000010002', '10600', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648401904', '0000000000000000010002', '10700', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648402004', '0000000000000000010002', '10800', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709191648402104', '0000000000000000010002', '10000', '0', '2018-12-27 09:19:16', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200004', '0000000000000000010003', '40100', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200104', '0000000000000000010003', '40500', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200204', '0000000000000000010003', '20400', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200304', '0000000000000000010003', '10600', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200404', '0000000000000000010003', '40000', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200504', '0000000000000000010003', '20000', '0', '2018-12-27 09:51:18', null, null);
INSERT INTO `role_permission` VALUES ('2018122709511817200604', '0000000000000000010003', '10000', '0', '2018-12-27 09:51:18', null, null);

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `mac` varchar(4000) CHARACTER SET utf8 DEFAULT NULL COMMENT 'MAC地址(多个地址逗号,分隔)',
  `host` varchar(4000) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP地址',
  `sequence` int(11) DEFAULT NULL COMMENT '序号',
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建者',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改者',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of server_info
-- ----------------------------
INSERT INTO `server_info` VALUES ('27752e34606aa4a4f9ead1e211f1fb60', '00-50-56-C0-00-01,9C-5C-8E-36-5C-16,00-50-56-C0-00-0A,10-02-B5-AC-7B-B9,10-02-B5-AC-7B-BA,00-50-56-C0-00-09,10-02-B5-AC-7B-BD,12-02-B5-AC-7B-B9', '192.168.159.1', '2', '', '2019-07-24 14:10:57', '', '2019-07-25 17:25:08');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `department_id` varchar(36) CHARACTER SET utf8 DEFAULT '-1',
  `department_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `enabled` int(11) DEFAULT '1',
  `deleted` int(11) DEFAULT NULL COMMENT '0-正常 1-删除',
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('$1', '-1', '西安银行', 'test', '{bcrypt}$2a$10$Kx8lfilp2pRMjRyeuK.q7OFLce8pw9VNAiHlKX/deEk1jJd.iCGT.', '测试用户α', '测试账号α', '1', '0', null, null, '0', '2019-03-18 14:23:34');
INSERT INTO `user` VALUES ('0', '-1', '', 'admin', '{bcrypt}$2a$10$CXn4bMRWSvg1Nh2Pbtoy1uwtYKgIrHO516HDZZpeSxoZCG634CUey', '管理员', '管理员', '1', '0', null, null, '0', '2018-12-27 09:28:26');
INSERT INTO `user` VALUES ('2019030716020546600002', null, null, 'testt', '{bcrypt}$2a$10$RhHC2j/8v4vft5EpiZRFhufrSFOZMNczLzg8a6cevjWVzK5pVbDtC', 'testtt', 'ttt', '0', null, '0', '2019-03-07 16:02:06', '0', '2019-03-07 16:45:12');
INSERT INTO `user` VALUES ('2019071617411708300001', null, null, 'cc', '{bcrypt}$2a$10$a34TdsQS6GnJN7yzntADK.RWdcZ9SbUgbZ5FOBS5qPj7x5iUC6fZK', 'cc', 'cc', '1', '0', '0', '2019-07-16 17:41:17', '0', '2019-07-16 17:48:16');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 NOT NULL DEFAULT '1',
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2018121710161233100004', '$1', '0000000000000000000001', '0', '2018-12-17 10:16:12', null, null);
INSERT INTO `user_role` VALUES ('2018122709221838900004', '000590', '0000000000000000010002', '0', '2018-12-27 09:22:18', null, null);
INSERT INTO `user_role` VALUES ('2018122709515723000004', '000423', '0000000000000000010003', '0', '2018-12-27 09:51:57', null, null);
INSERT INTO `user_role` VALUES ('2019071617474192600001', '2019071617411708300001', '0000000000000000010001', '0', '2019-07-16 17:47:41', null, null);
