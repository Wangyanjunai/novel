package com.potato369.novel.crawler.domain;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.crawler
 * @ClassName Category
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-04-17 20:45
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Category{

    @Xpath(value = "//div[@class='booklist']/h1/text()")
    private String categoryText;

    @Xpath(value = "//div[@class='booklist']/div[@class='pagelink']/a[@class='first']/@href")
    private String categoryUrl;
}
