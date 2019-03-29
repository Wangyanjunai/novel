package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import lombok.extern.slf4j.Slf4j;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.crawler.crawlers
 * @ClassName Basic
 * @Desc crawlers
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/29 16:55
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Crawler(name = "basic", useUnrepeated = false)
@Slf4j
public class Basic extends BaseSeimiCrawler {
    /**
     * @return 设置起始url
     */
    @Override
    public String[] startUrls() {
        return new String[]{"https://www.biquyun.com/"};
    }

    /**
     * 针对startUrl生成首批的response回调这个初始接口
     *
     * @param response --
     */
    @Override
    public void start(Response response) {
        if (log.isDebugEnabled()) {
            //log.debug("【后端爬虫框架】开始爬取笔趣阁小说网站数据……");
        }
        JXDocument document = response.document();
        //log.info("【后端爬虫框架】爬取到的数据document={}", document);
        List<Object> objectList = document.sel("//body//div[@id='wrapper']//div[@id='main']//div[@id='hotcontent']//div[@class='l']//div[@class='item']");
        for (Object o:objectList) {
           // log.info("o={}",o);
            push(Request.build(o.toString(), Basic::getTitle));
        }
        if (log.isDebugEnabled()) {
            //log.debug("【后端爬虫框架】结束爬取笔趣阁小说网站数据……");
        }
    }

    public void getTitle(Response response) {
        JXDocument document = response.document();
        try {
            log.info("url:{}{}", response.getUrl(), document.sel("//div[@class='item']//div[@class='image']//a//href()"));
        } catch (Exception e) {

        }
    }
}
