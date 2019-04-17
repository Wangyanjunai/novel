package com.potato369.novel.crawler.crawlers;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.support.json.JSONUtils;
import com.potato369.novel.crawler.constants.BusinessConstants;
import com.potato369.novel.crawler.domain.Category;
import com.potato369.novel.crawler.domain.Chapter;
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
@Crawler(name = "88dush", useUnrepeated = true)
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
			JXDocument document = response.document();
			List<Object> urlList = document.sel("//div[@class='tuijian']/ul/li/h2/a/@href");
			if (urlList.size() <= 0) {
				return;
			} else {
				for (Object url:urlList) {
					String urlStr = url.toString();
					if (!urlStr.startsWith(DOMAIN_URL) && !urlStr.contains(DOMAIN_URL)) {
						BusinessConstants.CURRENT_START_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
					} else {
						BusinessConstants.CURRENT_START_URL = urlStr;
					}
					log.info("start current start url={}", BusinessConstants.CURRENT_START_URL);
					push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
				}
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
	public void getEachPage(Response response) {
		try {
			JXDocument document = response.document();
			List<Object> urlList = document.sel("//div[@class='booklist']/ul/li/span[@class='sm']/a/@href");
			BusinessConstants.CURRENT_PAGE_NUMBER ++;
			for (Object url:urlList) {
				String urlStr = url.toString();
				if (!urlStr.startsWith(DOMAIN_URL) && !urlStr.contains(DOMAIN_URL)) {
					BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
				} else {
					BusinessConstants.CURRENT_GET_DATA_URL = urlStr;
				}
				push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::renderBean));
			}
			String nextPageEndPrefix=BusinessConstants.CURRENT_START_URL.substring(BusinessConstants.CURRENT_START_URL.indexOf("sort"));
			log.info(nextPageEndPrefix);
			StringBuffer bf =new StringBuffer(startUrls()[0]).append("/").append(nextPageEndPrefix).append(BusinessConstants.CURRENT_PAGE_NUMBER);
			if (log.isDebugEnabled()) {
				log.debug("url={}", bf.toString());
			}
			BusinessConstants.CURRENT_START_URL = bf.toString();
			push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
	public void renderBean(Response response) {
		try {
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
}
