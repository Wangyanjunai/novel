package com.potato369.novel.crawler.domain;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.crawler.domain
 * @ClassName Chapter
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-04-17 20:49
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Chapter {
    // 章节名称
    @Xpath(value = "//div[@class='mulu']/ul/li/a/allText()|//div[@class='mulu']/ul/li/a/text()|div[@class='novel']/h1/text()|div[@class='novel']/h1/allText()")
    private String chapterTitle;
    // 章节URL
    @Xpath(value = "//div[@class='mulu']/ul/li/a/@href")
    private String chapterURL;
    // 章节内容
    @Xpath(value = "//div[@class='yd_text2']/allText()|//div[@class='yd_text2']/text()|//div[@class='yd_text2']/allText()|//div[@class='yd_text2']/allText()")
    private String chapterContent;
    // 最新章节标题
    @Xpath(value = "//div[@class='booklist']/li/span[@class='zj']/a/allText()|//div[@class='booklist']/li/span[@class='zj']/a/text()|//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em/a/text()|//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em/a/allText()")
    private String lastChapterTitle;
    // 最新章节URL
    @Xpath(value = "//div[@class='booklist']/li/span[@class='zj']/a/@href|//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em/a/@href")
    private String lastChapterURL;
}
