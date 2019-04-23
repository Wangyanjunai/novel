/*
MySQL Data Transfer
Source Host: www.potato369.com
Source Database: novel_tmp
Target Host: www.potato369.com
Target Database: novel_tmp
Date: 2019/4/22 15:21:48
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节id',
  `title` varchar(512) DEFAULT NULL COMMENT '节章标题（名称）',
  `content` mediumtext COMMENT '章节内容',
  `url` varchar(1024) DEFAULT NULL COMMENT '章节路径URL',
  `startUrl` varchar(1024) DEFAULT NULL COMMENT '爬取数据开始路径URL',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说章节信息记录表';

-- ----------------------------
-- Table structure for novel_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `novel_advertisement`;
CREATE TABLE `novel_advertisement` (
  `ad_id` varchar(32) NOT NULL COMMENT '主键，广告id。',
  `tag` tinyint(4) NOT NULL COMMENT '标识广告是否可以跳转，0-可以跳转，1-不可以跳转。',
  `image_url` varchar(1024) DEFAULT NULL COMMENT '广告图片链接绝对路径URL。',
  `link_url` varchar(1024) DEFAULT NULL COMMENT '广告跳转链接绝对路径URL。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说加载广告信息数据记录表';

-- ----------------------------
-- Table structure for novel_category
-- ----------------------------
DROP TABLE IF EXISTS `novel_category`;
CREATE TABLE `novel_category` (
  `category_id` varchar(32) NOT NULL COMMENT 'id，主键',
  `parent_category_id` varchar(32) NULL DEFAULT NULL COMMENT '父级id',
  `category_cn_text` varchar(64) NULL DEFAULT NULL COMMENT '类目中文名称',
  `category_en_text` varchar(64) NULL DEFAULT NULL COMMENT '类目英文名称',
  `category_type` smallint(4) unsigned NULL DEFAULT NULL COMMENT '类型编号',
  `reading_number` int(11) unsigned NULL DEFAULT '0' COMMENT '阅读（点击）用户数',
  `click_number` int(11) unsigned NULL DEFAULT '0' COMMENT '点击次数',
  `is_deleted` tinyint(4) unsigned NULL DEFAULT '0' COMMENT '是否删除；0-否，1-是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说类目信息记录表';

-- ----------------------------
-- Table structure for novel_chapter
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
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`chapter_id`,`book_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说章节信息记录表';

-- ----------------------------
-- Table structure for novel_comment
-- ----------------------------
DROP TABLE IF EXISTS `novel_comment`;
CREATE TABLE `novel_comment` (
  `comment_id` varchar(32) NOT NULL COMMENT '主键，评论id。',
  `user_id` varchar(32) NOT NULL COMMENT '用户id。',
  `novel_id` int(11) NOT NULL COMMENT '小说id',
  `chapter_id` varchar(32) DEFAULT NULL COMMENT '章节id',
  `comment_title` varchar(512) DEFAULT NULL COMMENT '评论标题。',
  `comment_content` text DEFAULT NULL COMMENT '评论内容。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说用户评论信息数据记录表';

-- ----------------------------
-- Table structure for novel_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_info`;
CREATE TABLE `novel_info` (
  `id` varchar(32) NOT NULL COMMENT 'id，主键id',
  `cover_url` varchar(1024) NULL DEFAULT NULL COMMENT '封面图片绝对路径URL',
  `title` varchar(256) NULL DEFAULT NULL COMMENT '标题，小说的名称。',
  `author` varchar(256) NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(128) NULL DEFAULT NULL COMMENT '出版社或爬取的网站名称。',
  `total_words` decimal(16,0) unsigned NULL DEFAULT '0' COMMENT '总字数。',
  `novel_status` tinyint(4) unsigned NULL NOT NULL DEFAULT '0' COMMENT '状态，0-已完结，1-连载中。',
  `category_type` smallint(4) unsigned NOT NULL COMMENT '类目类型编号。',
  `category_cn_text` varchar(64) NOT NULL COMMENT '类目类型中文名称。',
  `category_en_text` varchar(64) NOT NULL COMMENT '类目类型英文名称。',
  `introduction` text NULL DEFAULT NULL COMMENT '小说简介',
  `readers` decimal(16,0) unsigned NULL DEFAULT '0' COMMENT '阅读（点击）用户数；默认“0-阅读（点击）用户数”',
  `recent_readers` decimal(16,0) unsigned NULL DEFAULT '0' COMMENT '最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”',
  `click_number` decimal(16,0) unsigned NULL DEFAULT '0' COMMENT '点击次数',
  `newest_chapter_id` varchar(32) NULL DEFAULT NULL COMMENT '最新章节id',
  `newest_chapter_title` varchar(512) NULL DEFAULT NULL COMMENT '最新章节标题（名称）',
  `total_chapters` int(6) unsigned NULL DEFAULT '0' COMMENT '章节总数',
  `retention` tinyint(3) unsigned NULL DEFAULT '0' COMMENT '留存率，现在只是保存数字，显示的时候加上百分比',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_title` (`title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说信息记录表';

-- ----------------------------
-- Table structure for novel_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_menu_info`;
CREATE TABLE `novel_menu_info` (
  `menu_id` varchar(32) NOT NULL COMMENT '主键，公众号自定义菜单id。',
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '公众号自定义菜单父级id',
  `type` varchar(64) NOT NULL COMMENT '菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型。',
  `name` varchar(64) NOT NULL COMMENT '菜单标题，不超过16个字节，子菜单不超过60个字节。',
  `key` varchar(64) DEFAULT NULL COMMENT '菜单KEY值，用于消息接口推送，不超过128字节，click等点击类型必须。',
  `url` varchar(1024) DEFAULT NULL COMMENT '网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。view、miniprogram类型必须。',
  `reading_number` int(11) unsigned DEFAULT '0' COMMENT '阅读（点击）用户数',
  `click_number` int(11) unsigned DEFAULT '0' COMMENT '点击次数',
  `media_id` varchar(128) DEFAULT NULL COMMENT '调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型必须。',
  `app_id` varchar(64) DEFAULT NULL COMMENT '小程序的appid，miniprogram类型必须。',
  `page_path` varchar(1024) DEFAULT NULL COMMENT '小程序的页面路径，miniprogram类型必须。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单按钮数据记录表';

-- ----------------------------
-- Table structure for novel_shelf
-- ----------------------------
DROP TABLE IF EXISTS `novel_shelf`;
CREATE TABLE `novel_shelf` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id。',
  `novel_id` varchar(32) NOT NULL COMMENT '小说id。',
  `chapter_id` varchar(32) NOT NULL COMMENT '已经阅读到的章节id。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`,`novel_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说用户书架信息数据记录表';

-- ----------------------------
-- Table structure for novel_user_info
-- ----------------------------
DROP TABLE IF EXISTS `novel_user_info`;
CREATE TABLE `novel_user_info` (
  `id` varchar(32) NOT NULL COMMENT '用户id',
  `openid` varchar(64) DEFAULT '' COMMENT 'openid',
  `nick_name` varchar(128) DEFAULT '' COMMENT '用户昵称',
  `gender` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '性别，2-女；1-男；0-未知，默认：“0-未知“',
  `user_name` varchar(128) DEFAULT '' COMMENT '用户名',
  `signature` varchar(256) DEFAULT '' COMMENT '签名内容',
  `alt` varchar(1024) DEFAULT '' COMMENT '个人主页URL',
  `city` varchar(25) DEFAULT '' COMMENT '城市',
  `province` varchar(25) DEFAULT '' COMMENT '省份',
  `country` varchar(25) DEFAULT '' COMMENT '国家',
  `avatar_url` varchar(1024) DEFAULT '' COMMENT '头像URL',
  `balance` decimal(10,0) unsigned DEFAULT '0' COMMENT '土豆币余额',
  `charge_amount` decimal(8,2) DEFAULT NULL COMMENT '充值总金额（元）',
  `shelf_amount` int(11) DEFAULT '0' COMMENT '书架小说总数量',
  `ip` varchar(25) DEFAULT '' COMMENT '客户端IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户信息记录表';

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id，主键',
  `order_id` varchar(32) NOT NULL COMMENT '订单id',
  `product_id` varchar(32) NOT NULL COMMENT '土豆币商品id',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `product_name` varchar(64) NOT NULL COMMENT '土豆币商品名称',
  `product_price` decimal(8,2) unsigned NOT NULL COMMENT '书币商品单价',
  `product_quantity` decimal(8,0) unsigned NOT NULL COMMENT '购买土豆币数量',
  `product_give_quantity` decimal(8,0) DEFAULT NULL COMMENT '赠送土豆币数量',
  `product_description` varchar(1024) DEFAULT NULL COMMENT '土豆币商品描述',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '会员支付时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '会员结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `key_order_id` (`order_id`) USING BTREE,
  KEY `key_product_id` (`product_id`) USING BTREE,
  KEY `key_buyer_openid` (`buyer_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单详情记录表';

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL COMMENT '订单id，主键',
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) unsigned NOT NULL COMMENT '订单总金额',
  `order_name` varchar(64) DEFAULT NULL COMMENT '订单名称',
  `order_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态，0-新订单；1-已完结；2-已取消，“默认：0-新订单”',
  `pay_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '订单支付状态，0-等待支付；1-支付成功，“默认：0-等待支付”',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '会员支付时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '会员结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `key_buyer_openid` (`buyer_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单信息记录表';

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL COMMENT '商品id，主键',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '商品类型，0-土豆币，1-会员；默认0-书币',
  `product_price` decimal(8,2) unsigned NOT NULL COMMENT '商品单价（元）',
  `product_quantity` decimal(8,0) DEFAULT NULL COMMENT '商品数量',
  `product_amount` decimal(8,2) DEFAULT NULL COMMENT '商品总价（元）',
  `product_give_quantity` decimal(8,0) unsigned NOT NULL COMMENT '赠送数量',
  `product_rank` varchar(64) DEFAULT NULL COMMENT '商品排行描述',
  `product_description` varchar(1024) DEFAULT NULL COMMENT '商品描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品信息记录表';

-- ----------------------------
-- Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `seller_id` varchar(32) NOT NULL COMMENT '卖家id，主键',
  `seller_name` varchar(64) NOT NULL COMMENT '卖家名字',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `openid` varchar(64) NOT NULL COMMENT '卖家微信openid',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(32) DEFAULT NULL COMMENT '登录终端ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`seller_id`) USING BTREE,
  KEY `key_openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='卖家信息记录表';

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bda', null, '男版', 'male', '100', '0', '0', '0', '2018-11-15 17:26:38', '2019-01-18 17:23:44');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdb', null, '女版', 'female', '200', '0', '0', '0', '2018-11-15 17:27:03', '2019-01-18 17:23:49');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdc', null, '漫画', 'picture', '300', '0', '0', '0', '2018-11-15 17:27:03', '2019-01-18 17:23:49');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdd', '61ae83410ed64850bb3d334845d83bda', '玄幻奇幻', 'xuanhuanqihuan', '101', '0', '0', '0', '2018-11-15 17:40:52', '2019-01-18 17:25:51');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bde', '61ae83410ed64850bb3d334845d83bda', '修仙修真', 'xiuxianxiuzhen', '102', '0', '0', '0', '2018-11-15 17:46:14', '2019-01-18 17:25:27');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdf', '61ae83410ed64850bb3d334845d83bda', '仙侠武侠', 'xianxiawuxia', '103', '0', '0', '0', '2018-11-15 17:45:38', '2019-01-18 17:25:16');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdg', '61ae83410ed64850bb3d334845d83bda', '都市情感', 'dushiqinggan', '104', '0', '0', '0', '2018-11-15 17:46:48', '2019-01-18 17:24:58');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdh', '61ae83410ed64850bb3d334845d83bda', '都市异能', 'dushiyineng', '105', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdj', '61ae83410ed64850bb3d334845d83bda', '历史军事', 'lishijunshi', '106', '0', '0', '0', '2018-11-15 17:44:53', '2019-01-18 17:25:48');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdk', '61ae83410ed64850bb3d334845d83bda', '科幻末世', 'kehuanmoshi', '107', '0', '0', '0', '2018-11-15 17:44:53', '2019-01-18 17:25:48');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdl', '61ae83410ed64850bb3d334845d83bda', '游戏竞技', 'youxijingji', '108', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdm', '61ae83410ed64850bb3d334845d83bdb', '幻想言情', 'huanxiangyanqing', '201', '0', '0', '0', '2018-11-15 17:46:14', '2019-01-18 17:25:27');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdn', '61ae83410ed64850bb3d334845d83bdb', '古代言情', 'gudaiyanqing', '202', '0', '0', '0', '2018-11-15 17:45:38', '2019-01-18 17:25:16');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdo', '61ae83410ed64850bb3d334845d83bdb', '都市生活', 'dushishenghuo', '203', '0', '0', '0', '2018-11-15 17:46:48', '2019-01-18 17:24:58');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdp', '61ae83410ed64850bb3d334845d83bdb', '青春校园', 'qingchunxiaoyuan', '204', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdq', '61ae83410ed64850bb3d334845d83bdb', '总裁豪门', 'zongcaihaomen', '205', '0', '0', '0', '2018-11-15 17:44:53', '2019-01-18 17:25:48');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdr', '61ae83410ed64850bb3d334845d83bdb', '悬疑灵异', 'xuanyilingyi', '206', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bds', '61ae83410ed64850bb3d334845d83bdb', '穿越重生', 'chuangyuechongsheng', '207', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
INSERT INTO `novel_category` VALUES ('61ae83410ed64850bb3d334845d83bdt', '61ae83410ed64850bb3d334845d83bdb', '其他类别', 'qitaleibie', '208', '0', '0', '0', '2018-11-15 17:47:53', '2019-01-18 17:25:34');
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
INSERT INTO `order_detail` VALUES ('07c6f70133154dba91906aa38b366d37', '201901162253259963da1d3c08809444', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-16 22:53:26', '2019-01-16 22:53:26');
INSERT INTO `order_detail` VALUES ('0a2a3363387146cb93da214d0f02dcf1', '2019011209070300854347899ea194c5', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-12 09:07:03', '2019-01-12 09:07:03');
INSERT INTO `order_detail` VALUES ('19b5f2e9ead94992847142fbaea77080', '20190111155859888f6f3b12341e9489', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '50', '3000', '充值5,000书币', '2019-01-11 15:59:12', '2019-01-11 15:59:12', '2019-01-11 15:59:01', '2019-01-11 15:59:12');
INSERT INTO `order_detail` VALUES ('21a658490bab4e6aa1478c1eb9a5ab37', '20190118095637209e942dcfbb74544c', '6329c34dd3fd45de86e02fe7f4973b93', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '1,000书币', '0.01', '1000', '0', '充值1,000书币', null, null, '2019-01-18 09:57:57', '2019-01-18 09:57:57');
INSERT INTO `order_detail` VALUES ('21ddda1ad5694f1cadbb4ae0bde2d08e', '201901162252566100e02eacdab3b43a', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 22:52:57', '2019-01-16 22:52:57');
INSERT INTO `order_detail` VALUES ('253453d201854f44bf0223f04c384b1e', '201901121224461496881c64ab710482', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv-mHD9NY6Ybt0PUt-T_NX9w', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-12 12:24:46', '2019-01-12 12:24:46');
INSERT INTO `order_detail` VALUES ('263f263d542b485fb8181b30d02ed814', '2019011622533606319933365daf14f3', '6329c34dd3fd45de86e02fe7f4973b93', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '1,000书币', '0.01', '1000', '0', '充值1,000书币', null, null, '2019-01-16 22:53:36', '2019-01-16 22:53:36');
INSERT INTO `order_detail` VALUES ('2fbbbff18732473eab54c2a529f48b18', '2019011708392167835d8f91673be43b', 'e23b37ef6f874d1ca7ea107c1d0c338a', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '3,000书币', '0.01', '3000', '0', '充值3,000书币', null, null, '2019-01-17 08:39:22', '2019-01-17 08:39:22');
INSERT INTO `order_detail` VALUES ('32747a950b9a4cafb34e25220098a2aa', '2019011623173346698eef7e8db9649a', '6329c34dd3fd45de86e02fe7f4973b93', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '1,000书币', '0.01', '1000', '0', '充值1,000书币', null, null, '2019-01-16 23:17:33', '2019-01-16 23:17:33');
INSERT INTO `order_detail` VALUES ('363875a899a84e168518b4ccab8787a4', '2019011621304336481fb6267e05249a', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 21:30:44', '2019-01-16 21:30:44');
INSERT INTO `order_detail` VALUES ('36e2afcf5e55492fbca4994bcf479e27', '20190115041039604c6868f84a07047f', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-15 04:10:40', '2019-01-15 04:10:40');
INSERT INTO `order_detail` VALUES ('3f81a3c4eceb46e9ad625ec0ce80ae3e', '20190121152040313b9cac66999fd48c', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-21 15:20:41', '2019-01-21 15:20:41');
INSERT INTO `order_detail` VALUES ('4247b779214b4522951c471eba59c6ee', '20190116231720670969cc8d6d821426', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 23:17:21', '2019-01-16 23:17:21');
INSERT INTO `order_detail` VALUES ('42778d231a444bf3996f26502a799102', '20190117091107419fb1050114e6d419', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-17 09:12:28', '2019-01-17 09:12:28');
INSERT INTO `order_detail` VALUES ('4296a0053b364e3eaab00388f0718766', '20190112055928764e547a31d7b27455', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-12 05:59:29', '2019-01-12 05:59:29');
INSERT INTO `order_detail` VALUES ('4bbe651502e64d8baa317925d380e092', '201901111600109291d00806655bb44c', '6329c34dd3fd45de86e02fe7f4973b93', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '1,000书币', '0.01', '10', '0', '充值1,000书币', '2019-01-11 16:00:18', '2019-01-11 16:00:18', '2019-01-11 16:00:11', '2019-01-11 16:00:18');
INSERT INTO `order_detail` VALUES ('5ec365d5d4d94537936427ce14cca138', '2019011618403154010839a447cdc474', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 18:40:32', '2019-01-16 18:40:32');
INSERT INTO `order_detail` VALUES ('6b89b53b615440868ff1d251c263769d', '201901120734262174fb07a93fc7346a', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-12 07:34:26', '2019-01-12 07:34:26');
INSERT INTO `order_detail` VALUES ('6c90801bdb20424ca53345ccc47c26e2', '201901141859125026f0b392ea2be42f', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-14 18:59:13', '2019-01-14 18:59:13');
INSERT INTO `order_detail` VALUES ('6ee65bbd0196474093a41a2187d52124', '201901161017424659c8c0913c20d4d8', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 10:17:42', '2019-01-16 10:17:42');
INSERT INTO `order_detail` VALUES ('70ee617a80d14240bfa78a1d428f133f', '20190111173516608f18b5258e50f411', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 17:35:16', '2019-01-11 17:35:16');
INSERT INTO `order_detail` VALUES ('79f9a833024e42acb1bb0f659371906c', '20190111172500587454e6c6fc3624a2', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 17:25:00', '2019-01-11 17:25:00');
INSERT INTO `order_detail` VALUES ('7e642183741a4b0cbca293d42f083e4f', '20190118094514169005675927afa434', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-18 09:46:34', '2019-01-18 09:46:34');
INSERT INTO `order_detail` VALUES ('83d0b5976ee34d23ae5040c443da49d4', '20190111220558675660057cfb353438', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 22:05:59', '2019-01-11 22:05:59');
INSERT INTO `order_detail` VALUES ('97f408e77311434aa8334437a706e5b5', '2019011121222150194206a2470c44d9', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-11 21:22:21', '2019-01-11 21:22:21');
INSERT INTO `order_detail` VALUES ('a2b045d176594897be264a0b42d5ea64', '201901180943281851dd0b45728cb471', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-18 09:44:48', '2019-01-18 09:44:48');
INSERT INTO `order_detail` VALUES ('a3afda01773b438f80daeadcfeeac29c', '20190120073143312379a93e298f249f', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-20 07:31:44', '2019-01-20 07:31:44');
INSERT INTO `order_detail` VALUES ('aab039e7bfb6438a8d9c907915cdf46f', '20190121152111569d42d35cedfbd4b0', 'e23b37ef6f874d1ca7ea107c1d0c338a', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '3,000书币', '0.01', '3000', '0', '充值3,000书币', null, null, '2019-01-21 15:21:12', '2019-01-21 15:21:12');
INSERT INTO `order_detail` VALUES ('bda1569e924649598f790de39c7d4f49', '201901111846375586cd8ca7ddd99430', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 18:46:37', '2019-01-11 18:46:37');
INSERT INTO `order_detail` VALUES ('bee1c17452de4c998ce513900ff087e3', '201901111610438715a506474b62842e', '5ff041a3da12455b83546e2987741a10', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-11 16:10:44', '2019-01-11 16:10:44');
INSERT INTO `order_detail` VALUES ('c3d9982888c640d9b9416a4142b716d3', '20190115200614748f86fe054326041b', '5ff041a3da12455b83546e2987741a10', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-15 20:06:15', '2019-01-15 20:06:15');
INSERT INTO `order_detail` VALUES ('c676e1bb24234561a25b2e4123ab2015', '20190111160649036e8305fd6db9247b', '6329c34dd3fd45de86e02fe7f4973b93', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '1,000书币', '0.01', '10', '0', '充值1,000书币', null, null, '2019-01-11 16:06:49', '2019-01-11 16:06:49');
INSERT INTO `order_detail` VALUES ('c7d841f9d4b74ab996997a1370a231c8', '201901161842015365f8f3b6004c0451', '80edd2c1503249debecd2ce523f1fa12', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '季度会员', '0.00', '0', '0', '充值季度会员，所有书免费看三个月，季度会员', null, null, '2019-01-16 18:42:02', '2019-01-16 18:42:02');
INSERT INTO `order_detail` VALUES ('cc204bd72dea4659b1154fe98267b199', '201901162322443987ce5980ed73d4e7', 'e23b37ef6f874d1ca7ea107c1d0c338a', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '3,000书币', '0.01', '3000', '0', '充值3,000书币', null, null, '2019-01-16 23:22:44', '2019-01-16 23:22:44');
INSERT INTO `order_detail` VALUES ('ce9644564a3f440882cd1137212affd5', '20190111161013267d4c6982b7b15455', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 16:10:13', '2019-01-11 16:10:13');
INSERT INTO `order_detail` VALUES ('d160ed33b7d94d3d92b0752a3eef7df0', '20190111160701809713e4e28a958454', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '5,000书币', '0.01', '50', '3000', '充值5,000书币', null, null, '2019-01-11 16:07:02', '2019-01-11 16:07:02');
INSERT INTO `order_detail` VALUES ('d7ff95423e9843efab6f4a19e8dc37cd', '201901112122093960c5a5a564d0c4fc', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 21:22:09', '2019-01-11 21:22:09');
INSERT INTO `order_detail` VALUES ('d85eeba5d8db4db29d4d387a5ff5225e', '2019011623014537583e192a74d16453', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-16 23:01:45', '2019-01-16 23:01:45');
INSERT INTO `order_detail` VALUES ('dc8351f31e7b4f4fb7931ddf943275d3', '201901161843461898db5593d089a4ae', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-16 18:43:46', '2019-01-16 18:43:46');
INSERT INTO `order_detail` VALUES ('e50a7cf4d0e74b85a551ed333bdb3f94', '2019011118463853919e328c0d543460', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 18:46:38', '2019-01-11 18:46:38');
INSERT INTO `order_detail` VALUES ('e9eac8fd464942a380e23e7d0dbf0738', '201901211538010345821ca01a2c5420', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-21 15:38:01', '2019-01-21 15:38:01');
INSERT INTO `order_detail` VALUES ('ea11388a06c743dea21e48424c049db7', '20190116184114231a2ba83f649a8471', '5ff041a3da12455b83546e2987741a10', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '年度会员', '0.00', '0', '0', '充值年度会员，所有书免费看一年，年度会员', null, null, '2019-01-16 18:41:14', '2019-01-16 18:41:14');
INSERT INTO `order_detail` VALUES ('f06d88bcab1848b696071dec8499c3f1', '20190111161013928a115569d288a45f', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 16:10:14', '2019-01-11 16:10:14');
INSERT INTO `order_detail` VALUES ('fa1488fa7d5d4507b2049ed9258685e0', '20190111173757161e8761c46d06d451', 'b6a51d414ca742cb81f94b69b0423a7c', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '5,000书币', '0.01', '5000', '3000', '充值5,000书币', null, null, '2019-01-11 17:37:57', '2019-01-11 17:37:57');
INSERT INTO `order_master` VALUES ('20190111155859888f6f3b12341e9489', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '0.50', '5,000书币', '1', '1', null, null, '2019-01-11 15:59:01', '2019-01-16 16:25:22');
INSERT INTO `order_master` VALUES ('201901111600109291d00806655bb44c', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '0.10', '1,000书币', '0', '1', '2019-01-11 16:00:18', '2019-01-11 16:00:18', '2019-01-11 16:00:11', '2019-01-11 16:00:18');
INSERT INTO `order_master` VALUES ('20190111160649036e8305fd6db9247b', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '0.10', '1,000书币', '0', '0', null, null, '2019-01-11 16:06:49', '2019-01-11 16:06:49');
INSERT INTO `order_master` VALUES ('20190111160701809713e4e28a958454', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '0.50', '5,000书币', '0', '0', null, null, '2019-01-11 16:07:02', '2019-01-11 16:07:02');
INSERT INTO `order_master` VALUES ('20190111161013267d4c6982b7b15455', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 16:10:13', '2019-01-11 16:10:13');
INSERT INTO `order_master` VALUES ('20190111161013928a115569d288a45f', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 16:10:14', '2019-01-11 16:10:14');
INSERT INTO `order_master` VALUES ('201901111610438715a506474b62842e', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '365.00', '年度会员', '0', '0', null, null, '2019-01-11 16:10:44', '2019-01-11 16:10:44');
INSERT INTO `order_master` VALUES ('20190111172500587454e6c6fc3624a2', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 17:25:00', '2019-01-11 17:25:00');
INSERT INTO `order_master` VALUES ('20190111173516608f18b5258e50f411', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 17:35:16', '2019-01-11 17:35:16');
INSERT INTO `order_master` VALUES ('20190111173757161e8761c46d06d451', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 17:37:57', '2019-01-11 17:37:57');
INSERT INTO `order_master` VALUES ('201901111846375586cd8ca7ddd99430', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 18:46:38', '2019-01-11 18:46:38');
INSERT INTO `order_master` VALUES ('2019011118463853919e328c0d543460', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 18:46:38', '2019-01-11 18:46:38');
INSERT INTO `order_master` VALUES ('201901112122093960c5a5a564d0c4fc', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 21:22:09', '2019-01-11 21:22:09');
INSERT INTO `order_master` VALUES ('2019011121222150194206a2470c44d9', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-11 21:22:21', '2019-01-11 21:22:21');
INSERT INTO `order_master` VALUES ('20190111220558675660057cfb353438', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '50.00', '5,000书币', '0', '0', null, null, '2019-01-11 22:05:59', '2019-01-11 22:05:59');
INSERT INTO `order_master` VALUES ('20190112055928764e547a31d7b27455', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-12 05:59:29', '2019-01-12 05:59:29');
INSERT INTO `order_master` VALUES ('201901120734262174fb07a93fc7346a', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-12 07:34:26', '2019-01-12 07:34:26');
INSERT INTO `order_master` VALUES ('2019011209070300854347899ea194c5', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-12 09:07:03', '2019-01-12 09:07:03');
INSERT INTO `order_master` VALUES ('201901121224461496881c64ab710482', '腾龙', '中国广东佛山', 'oSkiNv-mHD9NY6Ybt0PUt-T_NX9w', '50.00', '5,000书币', '0', '0', null, null, '2019-01-12 12:24:46', '2019-01-12 12:24:46');
INSERT INTO `order_master` VALUES ('201901141859125026f0b392ea2be42f', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-14 18:59:13', '2019-01-14 18:59:13');
INSERT INTO `order_master` VALUES ('20190115041039604c6868f84a07047f', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '2', '0', null, null, '2019-01-15 04:10:40', '2019-01-16 16:24:17');
INSERT INTO `order_master` VALUES ('20190115200614748f86fe054326041b', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '365.00', '年度会员', '1', '0', null, null, '2019-01-15 20:06:15', '2019-01-16 16:24:10');
INSERT INTO `order_master` VALUES ('201901161017424659c8c0913c20d4d8', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '1', '0', null, null, '2019-01-16 10:17:42', '2019-01-16 16:21:25');
INSERT INTO `order_master` VALUES ('2019011618403154010839a447cdc474', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-16 18:40:32', '2019-01-16 18:40:32');
INSERT INTO `order_master` VALUES ('20190116184114231a2ba83f649a8471', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-16 18:41:14', '2019-01-16 18:41:14');
INSERT INTO `order_master` VALUES ('201901161842015365f8f3b6004c0451', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '180.00', '季度会员', '0', '0', null, null, '2019-01-16 18:42:02', '2019-01-16 18:42:02');
INSERT INTO `order_master` VALUES ('201901161843461898db5593d089a4ae', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '1', '0', null, null, '2019-01-16 18:43:46', '2019-01-16 18:44:36');
INSERT INTO `order_master` VALUES ('2019011621304336481fb6267e05249a', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-16 21:30:44', '2019-01-16 21:30:44');
INSERT INTO `order_master` VALUES ('201901162252566100e02eacdab3b43a', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-16 22:52:57', '2019-01-16 22:52:57');
INSERT INTO `order_master` VALUES ('201901162253259963da1d3c08809444', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-16 22:53:26', '2019-01-16 22:53:26');
INSERT INTO `order_master` VALUES ('2019011622533606319933365daf14f3', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '10.00', '1,000书币', '0', '0', null, null, '2019-01-16 22:53:36', '2019-01-16 22:53:36');
INSERT INTO `order_master` VALUES ('2019011623014537583e192a74d16453', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-16 23:01:45', '2019-01-16 23:01:45');
INSERT INTO `order_master` VALUES ('20190116231720670969cc8d6d821426', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-16 23:17:21', '2019-01-16 23:17:21');
INSERT INTO `order_master` VALUES ('2019011623173346698eef7e8db9649a', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '10.00', '1,000书币', '0', '0', null, null, '2019-01-16 23:17:33', '2019-01-16 23:17:33');
INSERT INTO `order_master` VALUES ('201901162322443987ce5980ed73d4e7', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '30.00', '3,000书币', '0', '0', null, null, '2019-01-16 23:22:44', '2019-01-16 23:22:44');
INSERT INTO `order_master` VALUES ('2019011708392167835d8f91673be43b', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '30.00', '3,000书币', '0', '0', null, null, '2019-01-17 08:39:22', '2019-01-17 08:39:22');
INSERT INTO `order_master` VALUES ('20190117091107419fb1050114e6d419', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-17 09:12:28', '2019-01-17 09:12:28');
INSERT INTO `order_master` VALUES ('201901180943281851dd0b45728cb471', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-18 09:44:48', '2019-01-18 09:44:48');
INSERT INTO `order_master` VALUES ('20190118094514169005675927afa434', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '2', '0', null, null, '2019-01-18 09:46:34', '2019-01-18 11:58:16');
INSERT INTO `order_master` VALUES ('20190118095637209e942dcfbb74544c', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '10.00', '1,000书币', '1', '0', null, null, '2019-01-18 09:57:57', '2019-01-18 11:58:09');
INSERT INTO `order_master` VALUES ('20190120073143312379a93e298f249f', '腾龙', '中国广东深圳', 'oSkiNv_DW2MfAAskiD7BDKw2w8TQ', '50.00', '5,000书币', '0', '0', null, null, '2019-01-20 07:31:44', '2019-01-20 07:31:44');
INSERT INTO `order_master` VALUES ('20190121152040313b9cac66999fd48c', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '50.00', '5,000书币', '0', '0', null, null, '2019-01-21 15:20:41', '2019-01-21 15:20:41');
INSERT INTO `order_master` VALUES ('20190121152111569d42d35cedfbd4b0', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '30.00', '3,000书币', '0', '0', null, null, '2019-01-21 15:21:12', '2019-01-21 15:21:12');
INSERT INTO `order_master` VALUES ('201901211538010345821ca01a2c5420', 'Jack', '中国广东深圳', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '365.00', '年度会员', '0', '0', null, null, '2019-01-21 15:38:01', '2019-01-21 15:38:01');
INSERT INTO `product_info` VALUES ('0bfb552d4b0048a3afe4a49bf963b6ff', '10,000书币', '0', '0.01', '10000', '100.00', '5000', '兴趣专享', '充值10,000书币', '2019-01-08 17:11:50', '2019-01-11 16:09:05');
INSERT INTO `product_info` VALUES ('5ff041a3da12455b83546e2987741a10', '年度会员', '1', '0.00', '0', '365.00', '0', '超值专享', '充值年度会员，所有书免费看一年，年度会员', '2019-01-08 17:16:07', '2019-01-11 16:08:58');
INSERT INTO `product_info` VALUES ('6329c34dd3fd45de86e02fe7f4973b93', '1,000书币', '0', '0.01', '1000', '10.00', '0', '经济实惠', '充值1,000书币', '2019-01-08 17:06:08', '2019-01-11 16:08:53');
INSERT INTO `product_info` VALUES ('80edd2c1503249debecd2ce523f1fa12', '季度会员', '1', '0.00', '0', '180.00', '0', '热销专享', '充值季度会员，所有书免费看三个月，季度会员', '2019-01-08 17:15:21', '2019-01-11 16:08:49');
INSERT INTO `product_info` VALUES ('b6a51d414ca742cb81f94b69b0423a7c', '5,000书币', '0', '0.01', '5000', '50.00', '3000', '新人专享', '充值5,000书币', '2019-01-08 17:10:30', '2019-01-11 16:08:44');
INSERT INTO `product_info` VALUES ('e23b37ef6f874d1ca7ea107c1d0c338a', '3,000书币', '0', '0.01', '3000', '30.00', '0', '多而不贵', '充值3,000书币', '2019-01-08 17:07:25', '2019-01-11 16:08:40');
INSERT INTO `seller_info` VALUES ('0ed2ba762e364ce790661d86e59b162b', 'Jack', 'b814b812ec4b322e19fae7bb78d4d330', 'oSkiNv4fBXYxidv0wU_U0UDHNP4M', '2019-01-21 17:24:17', '183.14.30.126', '2017-12-17 21:18:22', '2019-01-21 17:24:17');
