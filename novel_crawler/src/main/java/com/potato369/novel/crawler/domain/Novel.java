package com.potato369.novel.crawler.domain;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.crawler.domain
 * @ClassName Novel
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-04-17 20:47
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Novel{
    // 分类名称
    @Xpath(value = "div[@class='nav']/div[@class='main']/ul[@class='nav_l']/li/a/text()|div[@class='nav']/div[@class='main']/ul[@class='nav_l']/li/a/allText()|//div[@class='booklist']/h1/allText()|//div[@class='booklist']/h1/text()")
    private String categoryText;
    // 分类URL
    @Xpath(value = "div[@class='nav']/div[@class='main']/ul[@class='nav_l']/li/a/@href|//div[@class='tuijian']/ul/li/h2/a/@href")
    private String categoryURL;
    // 书名
    @Xpath(value = "//div[@class='booklist']/li/span[@class='sm']/a/b/allText()|//div[@class='booklist']/li/span[@class='sm']/a/b/text()")
    private String title;
    // 小说URL
    @Xpath(value = "//div[@class='booklist']/li/span[@class='sm']/a/@href")
    private String novelURL;
    // 作者
    @Xpath(value = "//div[@class='booklist']/li/span[@class='zz']/a/allText()|//div[@class='booklist']/li/span[@class='zz']/a/text()")
    private String author;
    //字数
    @Xpath(value = "//div[@class='booklist']/li/span[@class='zs']/a/allText()|//div[@class='booklist']/li/span[@class='zs']/a/text()")
    private String words;
    // 更新日期
    @Xpath(value = "//div[@class='booklist']/li/span[@class='sj']/a/allText()|//div[@class='booklist']/li/span[@class='sj']/a/text()")
    private String updateDate;
    // 状态
    @Xpath(value = "//div[@class='booklist']/li/span[@class='zt']/a/allText()|//div[@class='booklist']/li/span[@class='zt']/a/text()")
    private String status;
    // 简介
    @Xpath(value = "/div[@class='jieshao']/div[@class='rt']/div[@class='intro']/allText()|/div[@class='jieshao']/div[@class='rt']/div[@class='intro']/text()")
    private String description;
    // 关注人数
    @Xpath(value = "//div[@class='booklist']/li/span[@class='gz']/a/allText()|//div[@class='booklist']/li/span[@class='gz']/a/text()")
    private String users;
}
