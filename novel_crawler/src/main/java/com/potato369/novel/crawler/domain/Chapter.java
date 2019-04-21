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
	
    private String id;
    
    @Xpath("//div[@class='novel']/h1/text()")
    private String title;

    @Xpath("//div[@class='novel']/div[@class='yd_text2']")
    private String content;
    
    @Xpath("//div[@class='read_t']/a[2]/text()")
    private String categoryText;
    
    @Xpath("//div[@class='read_t']/a[3]/text()")
    private String novelName;
    
    private String url;

    private String starturl;
}
