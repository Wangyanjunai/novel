/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : novel_tmp

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 10/05/2019 20:46:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说章节信息记录表';

-- ----------------------------
-- Table structure for novel_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `novel_advertisement`;
CREATE TABLE `novel_advertisement` (
  `ad_id` varchar(32) NOT NULL COMMENT '主键，广告id。',
  `tag1` tinyint(4) NOT NULL COMMENT '标识广告是应用类广告还是初始页面跳转广告，0-跳转广告，1-应用内广告。',
  `tag2` tinyint(4) NOT NULL COMMENT '标识广告是否可以跳转，0-可以跳转，1-不可以跳转。',
  `image_url` varchar(1024) DEFAULT NULL COMMENT '广告图片链接绝对路径URL。',
  `link_url` varchar(1024) DEFAULT NULL COMMENT '广告跳转链接绝对路径URL。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tag` int(11) NOT NULL,
  `novel_id` varchar(32) DEFAULT NULL,
  `novel_parent_category_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说加载广告信息数据记录表';

-- ----------------------------
-- Table structure for novel_category
-- ----------------------------
DROP TABLE IF EXISTS `novel_category`;
CREATE TABLE `novel_category` (
  `category_id` varchar(32) NOT NULL COMMENT 'id，主键',
  `parent_category_id` varchar(32) DEFAULT NULL COMMENT '父级id',
  `category_cn_text` varchar(64) DEFAULT NULL COMMENT '类目中文名称',
  `category_en_text` varchar(64) DEFAULT NULL COMMENT '类目英文名称',
  `category_type` smallint(4) unsigned DEFAULT NULL COMMENT '类型编号',
  `reading_number` int(11) unsigned DEFAULT '0' COMMENT '阅读（点击）用户数',
  `click_number` int(11) unsigned DEFAULT '0' COMMENT '点击次数',
  `is_deleted` tinyint(4) unsigned DEFAULT '0' COMMENT '是否删除；0-否，1-是',
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
  `chapter_index` smallint(6) DEFAULT NULL COMMENT '章节索引',
  `chapter_title` varchar(512) DEFAULT NULL COMMENT '章节标题（名称）',
  `newest_chapter_title` varchar(512) DEFAULT NULL COMMENT '最新章节标题（名称）',
  `chapter_url` varchar(1024) DEFAULT NULL COMMENT '章节URL',
  `chapter_content` mediumtext COMMENT '章节内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`chapter_id`,`book_id`) USING BTREE,
  UNIQUE KEY `key_chapter_id` (`chapter_id`) USING HASH COMMENT '主键唯一索引。',
  KEY `idx_chapter_index` (`chapter_index`) USING HASH COMMENT '目录普通索引。',
  KEY `idx_chapter_title` (`chapter_title`) USING HASH COMMENT '章节标题索引。',
  KEY `normal_chapter_bookId` (`book_id`) USING HASH COMMENT '所属小说索引。',
  KEY `normal_create_time` (`create_time`) USING HASH COMMENT '创建时间普通索引。',
  KEY `normal_update_time` (`update_time`) USING HASH COMMENT '更新时间普通索引。'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='小说章节信息记录表';

-- ----------------------------
-- Table structure for novel_comment
-- ----------------------------
DROP TABLE IF EXISTS `novel_comment`;
CREATE TABLE `novel_comment` (
  `comment_id` varchar(32) NOT NULL COMMENT '主键，评论id。',
  `user_id` varchar(32) NOT NULL COMMENT '用户id。',
  `novel_id` varchar(32) NOT NULL COMMENT '小说id',
  `chapter_id` varchar(32) DEFAULT NULL COMMENT '章节id',
  `comment_title` varchar(512) DEFAULT NULL COMMENT '评论标题。',
  `comment_content` text COMMENT '评论内容。',
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
  `cover_url` varchar(1024) DEFAULT NULL COMMENT '封面图片绝对路径URL',
  `data_url` varchar(1024) DEFAULT NULL COMMENT '小说数据绝对路径URL',
  `title` varchar(256) DEFAULT NULL COMMENT '标题，小说的名称。',
  `author` varchar(256) DEFAULT NULL COMMENT '作者',
  `publisher` varchar(128) DEFAULT NULL COMMENT '出版社或爬取的网站名称。',
  `total_words` decimal(16,0) unsigned DEFAULT '0' COMMENT '总字数。',
  `novel_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '状态，0-已完结，1-连载中。',
  `category_type` smallint(4) unsigned NOT NULL COMMENT '类目类型编号。',
  `category_cn_text` varchar(64) NOT NULL COMMENT '类目类型中文名称。',
  `category_en_text` varchar(64) NOT NULL COMMENT '类目类型英文名称。',
  `introduction` text COMMENT '小说简介',
  `readers` decimal(16,0) unsigned DEFAULT '0' COMMENT '阅读（点击）用户数；默认“0-阅读（点击）用户数”',
  `recent_readers` decimal(16,0) unsigned DEFAULT '0' COMMENT '最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”',
  `click_number` decimal(16,0) unsigned DEFAULT '0' COMMENT '点击次数',
  `newest_chapter_id` varchar(32) DEFAULT NULL COMMENT '最新章节id',
  `newest_chapter_title` varchar(512) DEFAULT NULL COMMENT '最新章节标题（名称）',
  `total_chapters` int(6) unsigned DEFAULT '0' COMMENT '章节总数',
  `retention` tinyint(3) unsigned DEFAULT '0' COMMENT '留存率，现在只是保存数字，显示的时候加上百分比',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_title` (`title`,`author`,`category_type`,`category_cn_text`) USING BTREE COMMENT '保证小说的名称，小说的作者，小说的中文分类名称，小说的分类类型type唯一的索引。'
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
  `lang` varchar(25) DEFAULT NULL,
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
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(32) NOT NULL,
  `alt` varchar(256) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `city` varchar(25) DEFAULT NULL,
  `country` varchar(25) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `gender` int(11) NOT NULL,
  `ip` varchar(25) DEFAULT NULL,
  `lang` varchar(25) DEFAULT NULL,
  `login_time` datetime NOT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  `privated_id` varchar(32) DEFAULT NULL,
  `province` varchar(25) DEFAULT NULL,
  `signature` varchar(256) DEFAULT NULL,
  `subscribe` int(11) NOT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `union_id` varchar(64) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `user_name` varchar(128) DEFAULT NULL,
  `watermark_app_id` varchar(64) DEFAULT NULL,
  `watermark_timestamp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
