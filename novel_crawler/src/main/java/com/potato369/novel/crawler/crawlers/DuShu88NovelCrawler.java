package com.potato369.novel.crawler.crawlers;

import java.util.ArrayList;
import java.util.List;

import com.potato369.novel.crawler.constants.BusinessConstants;
import org.seimicrawler.xpath.JXDocument;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
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
			List<Object> navUrlListLeft = document.sel("//div[@class='nav']/div[@class='main']/ul[@class='nav_l']/li/a/@href");
			List<Object> navUrlListRight = document.sel("//div[@class='nav']/div[@class='main']/ul[@class='nav_r']/li/a/@href");
			List<String> urList = new ArrayList<String>();
			if (navUrlListLeft.size() <=0 || navUrlListRight.size() <=0) {
				return;
			} else {
				for (Object url:navUrlListLeft) {
					String urlStr = url.toString();
					if (url.toString().contains("sort")) {
						urList.add(new StringBuffer(DOMAIN_URL).append(urlStr).toString());
					}
				}
				for (Object url:navUrlListRight) {
					String urlStr = url.toString();
					if (urlStr.contains("sort")) {
						urList.add(new StringBuffer(DOMAIN_URL).append(urlStr).toString());
					}
				}
			}
			for (int i = 0; i < urList.size(); i++) {
				log.info("aaaaaa url={}", urList.get(i));
				push(Request.build(urList.get(i), DuShu88NovelCrawler::getEachCategory));
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
	
	public void getEachCategory(Response response) {
		try {
			log.info("getEachCategory url={}", response.getUrl());
			JXDocument doc = response.document();
			List<Object> novelUrList = doc.sel("//div[@class='booklist']/ul/li/span[@class='sm']/a/@href");
			if (novelUrList.size() < 1) {
				BusinessConstants.CURRENT_PAGE_NUMBER = 1;
				return;
			}
			for (Object object : novelUrList) {
				String url = object.toString();
				if (!url.startsWith(DOMAIN_URL)) {
					BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(url).toString();
				} else {
					BusinessConstants.CURRENT_GET_DATA_URL = url;
				}
				push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::getEachPage));
				log.info(BusinessConstants.CURRENT_START_URL);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
	
	public void getEachPage(Response response) {
		try {
			JXDocument doc = response.document();
			List<Object> novelUrList = doc.sel("//div[@class='booklist']/ul/li/span[@class='sm']/a/@href");
			if (novelUrList.size() < 1) {
				BusinessConstants.CURRENT_PAGE_NUMBER = 1;
				return;
			}
			for (Object object : novelUrList) {
				String url = object.toString();
				if (!url.startsWith(DOMAIN_URL)) {
					BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(url).toString();
				} else {
					BusinessConstants.CURRENT_GET_DATA_URL = url;
				}
				push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::renderBean));
				log.info(BusinessConstants.CURRENT_START_URL);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
	
	public void renderBean(Response response) {
		try {
			log.info("render bean url={}", response.getUrl());
			JXDocument doc = response.document();
			List<Object> novelUrList = doc.sel("//div[@class='booklist']/ul/li/span[@class='sm']/a/@href");
			if (novelUrList.size() < 1) {
				BusinessConstants.CURRENT_PAGE_NUMBER = 1;
				return;
			}
			for (Object object : novelUrList) {
				String url = object.toString();
				if (!url.startsWith(DOMAIN_URL)) {
					BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(url).toString();
				} else {
					BusinessConstants.CURRENT_GET_DATA_URL = url;
				}
				push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::getEachPage));
				log.info(BusinessConstants.CURRENT_START_URL);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
}