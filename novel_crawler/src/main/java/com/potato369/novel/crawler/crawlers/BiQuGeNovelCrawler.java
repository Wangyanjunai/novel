package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.seimicrawler.xpath.JXDocument;
/**
 * <pre>
 * 类名：BiQuGeNovelCrawler
 * 类的作用：爬取笔趣阁小说网内容 https://www.sbiquge.com
 * 创建原因：使用的爬虫框架github地址：https://github.com/zhegexiaohuozi/SeimiCrawler
 * 创建时间： 2019年4月8日 下午4:24:52
 * 描述：
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Crawler(name = "sbiquge", useUnrepeated = false)
@Slf4j
public class BiQuGeNovelCrawler extends BaseSeimiCrawler{

	private static final long serialVersionUID = -6000214532002090859L;
		
	@Override
	public String[] startUrls() {
		return new String[]{"https://www.sbiquge.com"};
	}

	@Override
	public void start(Response response) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】开始爬取笔趣阁小说信息");
			}
			JXDocument document =  response.document();
			List<Object> objects = document.sel("//div[@class='item']");
			for (Object object : objects) {
				log.info("", object.toString());
			}
		} catch (Exception e) {
			log.error("【爬虫系统后台】爬取笔趣阁小说信息出现错误", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】结束爬取笔趣阁小说信息");
			}
		}
	}

}
