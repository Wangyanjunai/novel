package com.potato369.novel.crawler.crawlers;

import java.util.List;
import com.potato369.novel.crawler.constants.BusinessConstants;
import org.seimicrawler.xpath.JXDocument;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.annotation.Xpath;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * 类名: DuShu88NovelCrawler
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月15日 上午9:07:36
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Crawler(name = "88dush", useUnrepeated = false)
@Slf4j
public class DuShu88NovelCrawler extends BaseSeimiCrawler{

	private static final long serialVersionUID = 3993378973651481714L;

	private static final String DOMAIN_URL = "https://www.88dush.com";
	
	@Override
	public String[] startUrls() {
		return new String[]{DOMAIN_URL};
	}

	@Override
	public void start(Response response){
		try {
			if (log.isDebugEnabled()) {
			}
			JXDocument document = response.document();
			List<Object> urlList = document.sel("//div[@class='tuijian']/ul/li/h2/a/@href");
			if (urlList.size() < 1) {
				return;
			}
			if (log.isDebugEnabled()) {
				log.debug("the url list size={}", urlList.size());
			}
			for (Object url:urlList) {
				String urlStr = url.toString();
				if (!urlStr.startsWith(DOMAIN_URL) && !urlStr.contains(DOMAIN_URL)) {
					BusinessConstants.CURRENT_START_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
				} else {
					BusinessConstants.CURRENT_START_URL = urlStr;
				}
				if (log.isDebugEnabled()) {
					log.debug("CURRENT_START_URL={}", BusinessConstants.CURRENT_START_URL);
				}
				push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
		}
	}
	public void getEachPage(Response response) {
		try {
			JXDocument document = response.document();
			List<Object> urlList = document.sel("//div[@class='booklist']/li/span[@class='sm']/a/@href");
			if(urlList.size() < 1 ){
				BusinessConstants.CURRENT_PAGE_NUMBER = 1;
				return;
			}
			BusinessConstants.CURRENT_PAGE_NUMBER += 1;
			for (Object url:urlList) {
				String urlStr = url.toString();
				if(!urlStr.startsWith(DOMAIN_URL) && !urlStr.contains(DOMAIN_URL)) {
					BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
				} else {
					BusinessConstants.CURRENT_GET_DATA_URL = urlStr;
				}
				push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::renderBean));
			}
			StringBuffer bf =new StringBuffer(BusinessConstants.CURRENT_GET_DATA_URL).append(BusinessConstants.CURRENT_PAGE_NUMBER);
			if (log.isDebugEnabled()) {
				log.debug("url={}", bf.toString());
			}
			push(Request.build(bf.toString(), DuShu88NovelCrawler::getEachPage));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
		}
	}
	public void renderBean(Response response) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
			Novel novel = response.render(Novel.class);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
		}
	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Category{
		private String categoryText;
		private String url;
	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Novel{
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
		//章节
		@Xpath(value = "")
		private List<Chapter> chapters;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Chapter {
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
	
	public void callback(Response response) {
	}
	
}
