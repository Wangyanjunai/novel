/*
MySQL Data Transfer
Source Host: www.potato369.com
Source Database: novel_tmp
Target Host: www.potato369.com
Target Database: novel_tmp
Date: 2019/4/22 15:21:48
*/
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- 【1】Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` varchar(32) NOT NULL COMMENT '版本id，主键。',
  `version_code` int(11) NOT NULL COMMENT '版本号。',
  `version_name` varchar(16) NOT NULL COMMENT '版本名称。',
  `release_notes` varchar(128) NULL DEFAULT NULL COMMENT '版本更新内容说明。',
  `source_file_url` varchar(256) NOT NULL COMMENT '更新的app版本下载地址',
  `publish_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '版本发布时间。',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'APP版本信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of app_version
-- ----------------------------
INSERT INTO `app_version` VALUES ('aaa', '1', 'v1.0', '1. 添加反馈以后联系方式的填写 2. 添加初步的登录注册功能 3. 优化用户体验。', 'https://www.potato369.com/apk/app.apk', '2019-05-27 13:59:14');
INSERT INTO `app_version` VALUES ('bbb', '1', 'v1.0', '1. 第一个版本 2. 添加初步的登录注册功能 3. 优化用户体验。', 'https://www.potato369.com/apk/jisu.apk', '2019-05-21 11:33:51');
INSERT INTO `app_version` VALUES ('ccc', '1', 'v1.0', '1.优化修复样式。', 'https://www.potato369.com/apk/app-release.apk', '2019-05-27 13:58:46');

-- ----------------------------
-- 【2】Table structure for hot_words_info
-- ----------------------------
DROP TABLE IF EXISTS `hot_words_info`;
CREATE TABLE `hot_words_info` (
  `word_id` varchar(32) NOT NULL COMMENT '搜索热词id，主键。',
  `word` varchar(256) NOT NULL COMMENT '搜索热词。',
  `times` decimal(16, 0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '搜索次数。',
  `is_new` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否是新创建的，0-新创建，1-已经有的。',
  `soaring` decimal(16, 0) NULL DEFAULT 0 COMMENT '增长值。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间。',
  PRIMARY KEY (`word_id`) USING BTREE,
  UNIQUE KEY `uq_word` (`word`) USING BTREE COMMENT '唯一搜索热词索引'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '搜索热词信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hot_words_info
-- ----------------------------
INSERT INTO `hot_words_info` VALUES ('211903af46594362be197702c3ef9e00', '重生', '862', '1', '1', '2019-05-14 11:51:57', '2019-05-17 16:03:42');
INSERT INTO `hot_words_info` VALUES ('4a46647768e0480fa2584ad03534f9f0', '创设', '10', '1', '10', '2019-05-16 11:02:29', '2019-05-16 11:18:38');
INSERT INTO `hot_words_info` VALUES ('4b50f3bb57bb45c593b0704e959beb9d', '系统', '1023', '1', '12', '2019-05-14 11:51:04', '2019-05-28 13:59:04');
INSERT INTO `hot_words_info` VALUES ('4f7ebdca9db34608a5c468dd0e53d394', '龙', '36', '1', '36', '2019-05-16 10:20:28', '2019-05-30 10:53:25');
INSERT INTO `hot_words_info` VALUES ('70c08d0f8c3748b9b73a5f2a56df5848', '斗罗大陆', '488', '1', '8', '2019-05-14 11:55:30', '2019-05-30 10:53:16');
INSERT INTO `hot_words_info` VALUES ('74f3ae7df34546da965687565e096a75', '公共', '3', '1', '3', '2019-05-17 16:04:12', '2019-05-17 16:05:37');
INSERT INTO `hot_words_info` VALUES ('74f59434f9114a8c8ba61e4c8e14e09a', '虎', '18', '1', '18', '2019-05-16 10:34:50', '2019-05-17 16:13:05');
INSERT INTO `hot_words_info` VALUES ('82cb630afb41443bb9f6b0bfad47fe52', '三国', '478', '1', '8', '2019-05-14 11:53:57', '2019-05-17 16:09:09');
INSERT INTO `hot_words_info` VALUES ('86d4c00c01a14965bf57c80b64b79dc6', '4G和', '1', '0', '1', '2019-05-17 16:02:01', '2019-05-17 16:02:01');
INSERT INTO `hot_words_info` VALUES ('9556590eb9fc47e88870889787e43e23', '末世', '467', '1', '3', '2019-05-14 11:54:25', '2019-05-17 09:54:07');
INSERT INTO `hot_words_info` VALUES ('a41835e6e3714966ab97b46eab370ffc', '火影', '509', '1', '5', '2019-05-14 11:52:27', '2019-05-30 10:53:21');

-- ----------------------------
-- 【3】Table structure for novel_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `novel_advertisement`;
CREATE TABLE `novel_advertisement` (
   `ad_id` varchar(32) NOT NULL COMMENT '主键，广告id。',
   `tag1` tinyint(1) NOT NULL DEFAULT 0 COMMENT '标识广告是应用类广告还是初始页面跳转广告，0-跳转广告，1-应用内广告。',
   `tag2` tinyint(1) NOT NULL DEFAULT 0 COMMENT '标识广告是否可以跳转，0-可以跳转，1-不可以跳转。',
   `image_url` varchar(1024) NULL DEFAULT NULL COMMENT '广告图片链接绝对路径URL。',
   `link_url` varchar(1024) NULL DEFAULT NULL COMMENT '广告跳转链接绝对路径URL。',
   `novel_id` varchar(32) NULL DEFAULT NULL COMMENT '小说id。',
   `novel_parent_category_id` varchar(32) NULL DEFAULT NULL COMMENT '小说分类所属父级id。',
   `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
   `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
   PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小说加载广告信息数据记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_advertisement
-- ----------------------------
INSERT INTO `novel_advertisement` VALUES ('1', '0', '1', 'https://p2.ssl.qhimgs1.com/bdr/346__/t01d522d40c724c13ae.jpg', null, null, '', '2019-05-08 15:58:35', '2019-05-09 10:31:02');
INSERT INTO `novel_advertisement` VALUES ('2', '1', '0', 'http://www.myexception.cn/img/2014/08/01/104637145.png', null, '0057cd19e7ee45c4872c686b5425d697', '61ae83410ed64850bb3d334845d83bda', '2019-05-08 16:02:34', '2019-05-08 16:20:05');
INSERT INTO `novel_advertisement` VALUES ('3', '1', '0', 'http://img.pc841.com/2018/0730/20180730081824547.jpg', null, '16c757e5bd25442c81963084f3b70087', '61ae83410ed64850bb3d334845d83bda', '2019-05-08 16:04:17', '2019-05-08 16:20:15');
INSERT INTO `novel_advertisement` VALUES ('4', '1', '0', 'http://attach.bbs.miui.com/forum/201406/12/230058zmwnbno4b3pvfhdf.jpg', null, '1b29e7fe12d045389d24a56daeac4219', '61ae83410ed64850bb3d334845d83bda', '2019-05-08 16:06:39', '2019-05-08 16:20:17');
INSERT INTO `novel_advertisement` VALUES ('5', '1', '0', 'http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1212/11/c1/16517685_1355209921404.jpg', null, '1d227ae4c58b4675addcdebfb15ea891', '61ae83410ed64850bb3d334845d83bda', '2019-05-08 16:07:38', '2019-05-08 16:20:29');
INSERT INTO `novel_advertisement` VALUES ('6', '1', '0', 'http://img5.duitang.com/uploads/item/201410/05/20141005192521_nNeK8.jpeg', null, '2015e1d263fe417096a72f4e42c33c48', '61ae83410ed64850bb3d334845d83bdb', '2019-05-08 16:09:56', '2019-05-08 16:20:36');
INSERT INTO `novel_advertisement` VALUES ('7', '1', '0', 'http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1108/23/c3/8745157_1314096206402_1024x1024it.jpg', null, '207c9579851f4128bf5fda4230806295', '61ae83410ed64850bb3d334845d83bdb', '2019-05-08 16:10:08', '2019-05-08 16:11:59');
INSERT INTO `novel_advertisement` VALUES ('8', '1', '0', 'http://uploads.5068.com/allimg/171116/1-1G116110R2.jpg', null, '1a1a6989c9314cb7ad8be208f5d92663', '61ae83410ed64850bb3d334845d83bdb', '2019-05-08 16:12:11', '2019-05-08 16:13:03');

-- ----------------------------
-- 【4】Table structure for novel_category
-- ----------------------------
DROP TABLE IF EXISTS `novel_category`;
CREATE TABLE `novel_category` (
  `category_id` varchar(32) NOT NULL COMMENT 'id，主键。',
  `parent_category_id` varchar(32) NULL DEFAULT NULL COMMENT '父级id。',
  `category_cn_text` varchar(64) NULL DEFAULT NULL COMMENT '类目中文名称。',
  `category_en_text` varchar(64) NULL DEFAULT NULL COMMENT '类目英文名称。',
  `category_type` smallint(4) UNSIGNED NULL DEFAULT NULL COMMENT '类型编号。',
  `reading_number` decimal(16,0) UNSIGNED NULL DEFAULT 0 COMMENT '阅读（点击）用户数。',
  `click_number` decimal(16,0) UNSIGNED NULL DEFAULT 0 COMMENT '点击次数。',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除；0-否，1-是。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4  COLLATE = utf8mb4_general_ci COMMENT = '小说类目信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_category
-- ----------------------------
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bda', null, '男版', 'male', '100', '52', '52', '0', '2018-11-15 17:26:38', '2019-06-03 10:12:08');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdb', null, '女版', 'female', '200', '12', '12', '0', '2018-11-15 17:27:03', '2019-05-28 10:43:10');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdc', null, '漫画', 'picture', '300', '2', '2', '0', '2018-11-15 17:27:03', '2019-05-21 15:29:37');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdd', '61ae83410ed64850bb3d334845d83bda', '玄幻奇幻', 'xuanhuanqihuan', '101', '103', '103', '0', '2018-11-15 17:40:52', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bde', '61ae83410ed64850bb3d334845d83bda', '修仙修真', 'xiuxianxiuzhen', '102', '39', '39', '0', '2018-11-15 17:46:14', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdf', '61ae83410ed64850bb3d334845d83bda', '仙侠武侠', 'xianxiawuxia', '103', '32', '32', '0', '2018-11-15 17:45:38', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdg', '61ae83410ed64850bb3d334845d83bda', '都市情感', 'dushiqinggan', '104', '25', '25', '0', '2018-11-15 17:46:48', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdh', '61ae83410ed64850bb3d334845d83bda', '都市异能', 'dushiyineng', '105', '26', '26', '0', '2018-11-15 17:47:53', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdj', '61ae83410ed64850bb3d334845d83bda', '历史军事', 'lishijunshi', '106', '30', '30', '0', '2018-11-15 17:44:53', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdk', '61ae83410ed64850bb3d334845d83bda', '科幻末世', 'kehuanmoshi', '107', '35', '35', '0', '2018-11-15 17:44:53', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdl', '61ae83410ed64850bb3d334845d83bda', '游戏竞技', 'youxijingji', '108', '22', '22', '0', '2018-11-15 17:47:53', '2019-06-03 10:12:11');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdm', '61ae83410ed64850bb3d334845d83bdb', '幻想言情', 'huanxiangyanqing', '201', '30', '30', '0', '2018-11-15 17:46:14', '2019-05-28 11:06:51');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdn', '61ae83410ed64850bb3d334845d83bdb', '古代言情', 'gudaiyanqing', '202', '15', '15', '0', '2018-11-15 17:45:38', '2019-05-22 04:56:02');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdo', '61ae83410ed64850bb3d334845d83bdb', '都市生活', 'dushishenghuo', '203', '17', '17', '0', '2018-11-15 17:46:48', '2019-05-22 04:56:02');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdp', '61ae83410ed64850bb3d334845d83bdb', '青春校园', 'qingchunxiaoyuan', '204', '15', '15', '0', '2018-11-15 17:47:53', '2019-05-28 10:16:56');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdq', '61ae83410ed64850bb3d334845d83bdb', '总裁豪门', 'zongcaihaomen', '205', '15', '15', '0', '2018-11-15 17:44:53', '2019-05-29 10:42:30');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdr', '61ae83410ed64850bb3d334845d83bdb', '悬疑灵异', 'xuanyilingyi', '206', '15', '15', '0', '2018-11-15 17:47:53', '2019-05-29 10:41:46');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bds', '61ae83410ed64850bb3d334845d83bdb', '穿越重生', 'chuangyuechongsheng', '207', '13', '13', '0', '2018-11-15 17:47:53', '2019-05-28 10:42:24');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdt', '61ae83410ed64850bb3d334845d83bdb', '其他类别', 'qitaleibie', '208', '14', '14', '0', '2018-11-15 17:47:53', '2019-05-28 10:42:44');

-- ----------------------------
-- 【5】Table structure for novel_chapter
-- ----------------------------
DROP TABLE IF EXISTS `novel_chapter`;
CREATE TABLE `novel_chapter` (
  `chapter_id` varchar(32) NOT NULL COMMENT '章节id，主键id',
  `book_id` varchar(32) NOT NULL COMMENT '章节所属小说id',
  `chapter_index` smallint(6) NULL DEFAULT NULL COMMENT '章节索引',
  `chapter_title` varchar(512) NULL DEFAULT NULL COMMENT '章节标题（名称）',
  `newest_chapter_title` varchar(512) NULL DEFAULT NULL COMMENT '最新章节标题（名称）',
  `chapter_url` varchar(1024) NULL DEFAULT NULL COMMENT '章节URL',
  `chapter_content` mediumtext NULL DEFAULT NULL COMMENT '章节内容',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`chapter_id`,`book_id`) USING BTREE,
  UNIQUE KEY `key_chapter_id` (`chapter_id`) USING BTREE COMMENT '主键唯一索引。',
  KEY `idx_chapter_index` (`chapter_index`) USING BTREE COMMENT '目录普通索引。',
  KEY `idx_chapter_title` (`chapter_title`) USING BTREE COMMENT '章节标题索引。',
  KEY `normal_chapter_bookId` (`book_id`) USING BTREE COMMENT '所属小说索引。',
  KEY `normal_create_time` (`create_time`) USING BTREE COMMENT '创建时间普通索引。',
  KEY `normal_update_time` (`update_time`) USING BTREE COMMENT '更新时间普通索引。'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小说章节信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 【6】Table structure for novel_comment
-- ----------------------------
DROP TABLE IF EXISTS `novel_comment`;
CREATE TABLE `novel_comment` (
  `comment_id` varchar(32) NOT NULL COMMENT '主键，评论id。',
  `user_id` varchar(32) NOT NULL COMMENT '用户id。',
  `novel_id` varchar(32) NOT NULL COMMENT '小说id。',
  `chapter_id` varchar(32) DEFAULT NULL COMMENT '章节id。',
  `comment_title` varchar(512) DEFAULT NULL COMMENT '评论标题。',
  `comment_content` text DEFAULT NULL COMMENT '评论内容。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小说用户评论信息数据记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 【7】Table structure for novel_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_info`;
CREATE TABLE `novel_info` (
  `id` varchar(32) NOT NULL COMMENT 'id，主键id。',
  `cover_url` varchar(1024) DEFAULT NULL COMMENT '封面图片绝对路径URL。',
  `title` varchar(256) DEFAULT NULL COMMENT '标题，小说的名称。',
  `author` varchar(256) DEFAULT NULL COMMENT '作者。',
  `publisher` varchar(128) DEFAULT NULL COMMENT '出版社或爬取的网站名称。',
  `total_words` decimal(16,0) UNSIGNED DEFAULT 0 COMMENT '总字数。',
  `novel_status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态，0-已完结，1-连载中。',
  `category_type` smallint(4) UNSIGNED NOT NULL COMMENT '类目类型编号。',
  `category_cn_text` varchar(64) NOT NULL COMMENT '类目类型中文名称。',
  `category_en_text` varchar(64) NOT NULL COMMENT '类目类型英文名称。',
  `introduction` text COMMENT '小说简介',
  `readers` decimal(16,0) UNSIGNED DEFAULT 0 COMMENT '阅读（点击）用户数；默认“0-阅读（点击）用户数”。',
  `recent_readers` decimal(16,0) UNSIGNED DEFAULT 0 COMMENT '最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”。',
  `click_number` decimal(16,0) UNSIGNED DEFAULT 0 COMMENT '点击次数。',
  `newest_chapter_id` varchar(32) DEFAULT NULL COMMENT '最新章节id。',
  `newest_chapter_title` varchar(512) DEFAULT NULL COMMENT '最新章节标题（名称）。',
  `total_chapters` int(6) UNSIGNED DEFAULT 0 COMMENT '章节总数。',
  `retention` tinyint(3) UNSIGNED DEFAULT 0 COMMENT '留存率，现在只是保存数字，显示的时候加上百分比。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  `data_url` varchar(1024) DEFAULT NULL COMMENT '小说信息数据的地址url。',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_title` (`title`,`author`,`category_type`,`category_cn_text`) USING BTREE COMMENT '保证小说的名称，小说的作者，小说的中文分类名称，小说的分类类型type唯一的索引。'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小说信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_info
-- ----------------------------

-- ----------------------------
-- 【8】Table structure for novel_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_menu_info`;
CREATE TABLE `novel_menu_info` (
  `menu_id` varchar(32) NOT NULL COMMENT '主键，公众号自定义菜单id。',
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '公众号自定义菜单父级id',
  `type` varchar(64) NOT NULL COMMENT '菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型。',
  `name` varchar(64) NOT NULL COMMENT '菜单标题，不超过16个字节，子菜单不超过60个字节。',
  `key` varchar(64) DEFAULT NULL COMMENT '菜单KEY值，用于消息接口推送，不超过128字节，click等点击类型必须。',
  `url` varchar(1024) DEFAULT NULL COMMENT '网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。view、miniprogram类型必须。',
  `reading_number` int(11) UNSIGNED DEFAULT '0' COMMENT '阅读（点击）用户数',
  `click_number` int(11) UNSIGNED DEFAULT '0' COMMENT '点击次数',
  `media_id` varchar(128) DEFAULT NULL COMMENT '调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型必须。',
  `app_id` varchar(64) DEFAULT NULL COMMENT '小程序的appid，miniprogram类型必须。',
  `page_path` varchar(1024) DEFAULT NULL COMMENT '小程序的页面路径，miniprogram类型必须。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='菜单按钮数据记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_menu_info
-- ----------------------------
INSERT INTO `novel_menu_info` VALUES ('f011afad234a4bd5b054375fa6daf57a', null, 'view', '游戏中心', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('f011afad234a4bd5b054375fa6daf57b', 'f011afad234a4bd5b054375fa6daf57a', 'view', '四川安庆麻将', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('f011afad234a4bd5b054375fa6daf57c', 'f011afad234a4bd5b054375fa6daf57a', 'view', '天天抓娃娃', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('f011afad234a4bd5b054375fa6daf57d', 'f011afad234a4bd5b054375fa6daf57a', 'view', '捕鱼黄金联赛', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('f011afad234a4bd5b054375fa6daf57e', 'f011afad234a4bd5b054375fa6daf57a', 'miniprogram', '与佛有关', null, null, '0', '0', null, 'gh_04ac2b502737', 'page/index/index', '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972b1c', null, 'click', '签到送书币', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1a', null, 'view', '访问书屋', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1b', 'ff1346c04ab243a2a24921da34972s1a', 'view', '书屋首页', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1c', 'ff1346c04ab243a2a24921da34972s1a', 'view', '精品推荐', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1d', 'ff1346c04ab243a2a24921da34972s1a', 'view', '免费专区', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1e', 'ff1346c04ab243a2a24921da34972s1a', 'view', '个人中心', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1f', 'ff1346c04ab243a2a24921da34972s1a', 'view', '我要充值', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1g', 'ff1346c04ab243a2a24921da34972s1a', 'view', '阅读记录', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');
INSERT INTO `novel_menu_info` VALUES ('ff1346c04ab243a2a24921da34972s1h', 'ff1346c04ab243a2a24921da34972s1a', 'view', '联系客服', null, null, '0', '0', null, null, null, '2019-04-09 12:36:07', '2019-04-10 09:44:24');

-- ----------------------------
-- 【9】Table structure for novel_shelf
-- ----------------------------
DROP TABLE IF EXISTS `novel_shelf`;
CREATE TABLE `novel_shelf` (
  `shelf_id` varchar(32) NOT NULL COMMENT '小说id，联合主键。',
  `user_id` varchar(32) NOT NULL COMMENT '用户id，联合主键。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  `novel_id` varchar(32) NOT NULL COMMENT '小说id。',
  `chapter_id` varchar(32) NULL DEFAULT NULL COMMENT '已经阅读到的小说章节id。',
  `chapter_index` int(11) NULL DEFAULT NULL COMMENT '已经阅读到的小说章节索引。',
  PRIMARY KEY (`shelf_id`,`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书架信息数据记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_shelf
-- ----------------------------
INSERT INTO `novel_shelf` VALUES ('qda1adbab1214abda103fdc8fe97ed8a', '036a16dd9a8645f48dff1fd96af07877', '2019-05-16 17:46:25', '2019-05-16 17:46:25', '', null, null);
INSERT INTO `novel_shelf` VALUES ('qda1adbab1214abda103fdc8fe97ed8d', '06cf9355f3e1470d8491db6d0dd64416', '2019-05-16 17:47:24', '2019-05-16 17:47:31', '', null, null);
INSERT INTO `novel_shelf` VALUES ('qda1adbab1214abda103fdc8fe97ed8e', '060d7ffef8c945a09f6bfee10ca374bf', '2019-05-16 17:46:59', '2019-05-16 17:46:59', '', null, null);

-- ----------------------------
-- 【10】Table structure for novel_shelf_detail
-- ----------------------------
DROP TABLE IF EXISTS `novel_shelf_detail`;
CREATE TABLE `novel_shelf_detail` (
  `shelf_detail_id` varchar(32) NOT NULL COMMENT '书架详情id，联合主键。',
  `shelf_id` varchar(32) NOT NULL COMMENT '书架id，联合主键。',
  `novel_id` varchar(32) NOT NULL COMMENT '小说id，联合主键。',
  `user_id` varchar(20) NOT NULL COMMENT '用户mid，联合主键。',
  `last_read_chapter_id` varchar(32) NULL DEFAULT NULL COMMENT '最后一次阅读的章节的id(可能为空)。',
  `last_read_chapter_index` smallint(6) UNSIGNED NULL DEFAULT NULL COMMENT '最后一次阅读的章节索引index。',
  `last_read_page` smallint(6) UNSIGNED NULL DEFAULT NULL COMMENT '最后一次阅读章节的页码。',
  `has_update` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '书架的这本小说是否有新的章节更新，0-无更新，1-有更新，默认0。',
  `sort` smallint(6) UNSIGNED NULL DEFAULT NULL COMMENT '保存自定义排序的顺序。',
  `is_or_not_top` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '书架的这本小说是否开启置顶，0-不开启，1-开启，默认0。',
  `is_or_not_push` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '书架的这本小说是否开启小说章节更新消息推送，0-不开启，1-开启，默认0。',
  `last_chapter_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最新章节更新时间。',
  `last_read_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次阅读时间。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  PRIMARY KEY (`shelf_detail_id`,`shelf_id`,`novel_id`,`user_id`) USING BTREE,
  KEY `key_shelf_id` (`shelf_id`) USING BTREE,
  KEY `key_novel_id` (`novel_id`) USING BTREE,
  KEY `key_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书架详情记录表。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_shelf_detail
-- ----------------------------

-- ----------------------------
-- 【11】Table structure for novel_user_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_user_info`;
CREATE TABLE `novel_user_info`  (
  `m_id` varchar(20) NOT NULL COMMENT '用户mid，联合主键。',
  `me_id` varchar(20) NOT NULL COMMENT '手机串号，联合主键。',
  `brand` varchar(64) NULL DEFAULT NULL COMMENT '手机品牌。',
  `model` varchar(64) NULL DEFAULT NULL COMMENT '手机型号。',
  `mac` varchar(64) NULL DEFAULT NULL COMMENT '手机mac地址。',
  `system_name` varchar(64) NULL DEFAULT NULL COMMENT '手机系统类型。',
  `system_code` varchar(64) NULL DEFAULT NULL COMMENT '手机系统版本。',
  `version_name` varchar(64) NULL DEFAULT NULL COMMENT 'APP应用版本名称。',
  `openid` varchar(64) NULL DEFAULT NULL COMMENT '微信openid，或者微博openid，或者QQ账号openid。',
  `nick_name` varchar(128) NULL DEFAULT NULL COMMENT '用户微信，QQ，微博昵称。',
  `gender` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '性别，0-未知；1-男；2-女，默认：“0-未知“。',
  `lang` varchar(25) NULL DEFAULT NULL COMMENT '语言。',
  `address` varchar(256) NULL DEFAULT NULL COMMENT '定位地址（国家省份城市）。',
  `avatar_url` varchar(1024) NULL DEFAULT NULL COMMENT '头像地址URL。',
  `charge_amount` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '充值总金额（元）。',
  `envelope_amount` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '红包进度条。',
  `shelf_amount` int(11) NULL DEFAULT 0 COMMENT '书架小说总数量。',
  `vip_grade_id` varchar(32) NULL DEFAULT NULL COMMENT 'VIP等级id。关联VIP权限等级信息表的权限等级id主键。',
  `vip_start_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP开始时间。',
  `vip_end_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP结束时间。',
  `balance_amount` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '余额。',
  `user_type` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '用户身份类型，0-游客身份；1-微信身份；2-微博身份；3-QQ身份，默认：“0-游客身份“。',
  `is_or_not_band_weChat` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否完成绑定微信号任务，0-未完成；1-已完成，默认：“1-未完成“。',
  `ip` varchar(25)  NULL DEFAULT NULL COMMENT 'APP客户端外网IP。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间。',
  `login_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '登录时间。',
  PRIMARY KEY (`m_id`, `me_id`) USING BTREE,
  INDEX `key_openid`(`openid`) USING BTREE,
  INDEX `key_me_id`(`me_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息记录表。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 【12】Records of novel_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for novel_vip_grade
-- ----------------------------
DROP TABLE IF EXISTS `novel_vip_grade`;
CREATE TABLE `novel_vip_grade` (
	`grade_id` varchar(32) NOT NULL COMMENT 'VIP等级id，主键。',
	`grade_name` varchar(10) NULL DEFAULT NULL COMMENT 'VIP等级名称。',
	`grade_intro` varchar(1024) NULL DEFAULT NULL COMMENT 'VIP等级简介。',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
    PRIMARY KEY (`grade_id`) USING BTREE,
    INDEX `key_grade_name`(`grade_name`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'VIP等级信息记录表。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_vip_grade
-- ----------------------------

-- ----------------------------
-- 【13】Table structure for novel_user_account
-- ----------------------------
DROP TABLE IF EXISTS `novel_user_account`;
CREATE TABLE `novel_user_account` (
    `account_id` varchar(32) NOT NULL COMMENT '账户id，主键。',
    `account_name` varchar(10) NULL DEFAULT NULL COMMENT '账户名称。',
    `account_info` varchar(64) NULL DEFAULT NULL COMMENT '账号信息。',
    `user_id` varchar(20) NOT NULL COMMENT '用户mid。',
    `account_user_name` varchar(64) NULL DEFAULT NULL COMMENT '姓名。',
    `account_id_number` varchar(64) NULL DEFAULT NULL COMMENT '身份证号码。',
    `account_phone_number` varchar(64) NULL DEFAULT NULL COMMENT '手机号码。',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
    PRIMARY KEY (`account_id`) USING BTREE,
    INDEX `key_account_name`(`account_name`) USING BTREE,
    INDEX `key_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户账户信息记录表。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of novel_user_account
-- ----------------------------


-- ----------------------------
-- 【14】Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id，主键。',
  `order_id` varchar(32) NOT NULL COMMENT '订单id。',
  `product_id` varchar(32) NOT NULL COMMENT '土豆币商品id。',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid。',
  `user_id` varchar(20) NOT NULL COMMENT '用户mid。',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称。',
  `product_description` varchar(1024) NULL DEFAULT NULL COMMENT '商品描述。',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间。',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP开始时间。',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP结束时间。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `key_order_id` (`order_id`) USING BTREE,
  KEY `key_product_id` (`product_id`) USING BTREE,
  KEY `key_buyer_openid` (`buyer_openid`) USING BTREE,
  KEY `key_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单详情记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- 【15】Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL COMMENT '订单id，主键。',
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字。',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址。',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家openid。',
  `user_id` varchar(20) NOT NULL COMMENT '用户mid。',
  `order_amount` decimal(8, 2) UNSIGNED NOT NULL COMMENT '订单总金额。',
  `order_name` varchar(64) NULL DEFAULT NULL COMMENT '订单名称。',
  `order_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单类型，0-提现；1-兑换，“默认：0-提现”。',
  `order_status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态，0-新订单；1-已完结；2-已取消，“默认：0-新订单”。',
  `pay_status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单支付状态，0-等待支付；1-支付成功，“默认：0-等待支付”。',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间。',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP开始时间。',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT 'VIP结束时间。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0)  COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0)  ON UPDATE CURRENT_TIMESTAMP(0)  COMMENT '更新时间。',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `key_buyer_openid` (`buyer_openid`) USING BTREE,
  KEY `key_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_master
-- ----------------------------

-- ----------------------------
-- 【16】Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL COMMENT '商品id，主键。',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称。',
  `product_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '计算类型，0-按照天算，1-按照月算；默认0-按照天算。',
  `product_amount` decimal(8, 2) NULL DEFAULT NULL COMMENT '商品总价（元）。',
  `product_description` varchar(1024) NULL DEFAULT NULL COMMENT '商品描述。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `key_product_name`(`product_name`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_info
-- ----------------------------

-- ----------------------------
-- 【17】Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
    `task_id` varchar(32) NOT NULL COMMENT '任务id，主键。',
    `user_id` varchar(20) NOT NULL COMMENT '用户mid。',
    `task_name` varchar(64) NOT NULL COMMENT '任务名称。',
    `task_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '任务类型，1-绑定任务；2-分享任务；3-下载任务；4-阅读任务',
    `task_description` varchar(1024) NULL DEFAULT NULL COMMENT '任务描述。',
    `task_progress_value` smallint(2) NULL DEFAULT NULL COMMENT '任务红包进度条。',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间。',
    PRIMARY KEY (`task_id`) USING BTREE,
    INDEX `key_task_name`(`task_name`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of task_info
-- ----------------------------

-- ----------------------------
-- 【18】Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `seller_id` varchar(32) NOT NULL COMMENT '卖家id，主键。',
  `seller_name` varchar(64) NOT NULL COMMENT '卖家名字。',
  `password` varchar(64) NOT NULL COMMENT '登录密码。',
  `openid` varchar(64) NOT NULL COMMENT '卖家微信openid。',
  `login_time` timestamp(0) NULL DEFAULT NULL COMMENT '登录时间。',
  `login_ip` varchar(32) NULL DEFAULT NULL COMMENT '登录终端ip。',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间。',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间。',
  PRIMARY KEY (`seller_id`) USING BTREE,
  KEY `key_openid` (`openid`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卖家信息记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seller_info
-- ----------------------------
INSERT INTO `seller_info` VALUES ('0ed2ba762e364ce790661d86e59b162b', 'Jack', 'b814b812ec4b322e19fae7bb78d4d330', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '2019-01-21 17:24:17', '183.14.30.126', '2017-12-17 21:18:22', '2019-01-21 17:24:17');
