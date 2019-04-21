package com.potato369.novel.crawler.domain;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    // 书名
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='sm']/a/b/text()")
    private String title;
    // 小说URL
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='sm']/a/@href")
    private String novelURL;
    
    @Xpath(value = "//div[@class='booklist']/h1/text()")
    private String categoryText;
    
    @Xpath(value = "//div[@class='booklist']/div[@class='pagelink']/a[@class='first']/@href")
    private String categoryUrl;
    
    // 小说最新章节名称
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='zj']/a/text()")
    private String lastChapterName;
    
    // 小说最新章节URL
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='zj']/a/@href")
    private String lastChapterUrl;
    
    // 作者
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='zz']/text()")
    private String author;
    
    //字数
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='zs']/text()")
    private String words;
    
    // 更新日期
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='sj']/text()")
    private String updateDate;
    
    // 状态
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='zt']/text()")
    private String status;
    
    // 关注人数
    @Xpath(value = "//div[@class='booklist']/ul/li/span[@class='fs']/text()")
    private String users;
}
