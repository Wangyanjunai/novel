package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import org.seimicrawler.xpath.JXDocument;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.CategoryEnum;
import com.potato369.novel.basic.enums.NovelInfoEnum;

import org.springframework.beans.factory.annotation.Autowired;
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
@Crawler(name = "sbiquge", useUnrepeated = true)
@Slf4j
public class BiQuGeNovelCrawler extends BaseSeimiCrawler{

	private static final long serialVersionUID = -6000214532002090859L;
	
	private static final String DOMAIN_URL = "https://www.sbiquge.com";

	@Autowired
	private NovelInfoService novelInfoService;

	@Autowired
	private NovelChapterService chapterService;
		
	@Override
	public String[] startUrls() {
		String[] urlList = new String[] {
				DOMAIN_URL+"/",  				 
				DOMAIN_URL+"/xuanhuanxiaoshuo/",
				DOMAIN_URL+"/xiuzhenxiaoshuo/",  
				DOMAIN_URL+"/dushixiaoshuo/",
				DOMAIN_URL+"/chuanyuexiaoshuo/", 
				DOMAIN_URL+"/wangyouxiaoshuo/",
				DOMAIN_URL+"/kehuanxiaoshuo/",    
				DOMAIN_URL+"/qitaxiaoshuo/"};
		return urlList;
	}

	@Override
	public void start(Response response) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】开始爬取笔趣阁小说网站内容");
			}
			JXDocument document = response.document();
			List<Object> urls = new LinkedList<>();
			urls.addAll(document.sel("//div[@class='image']/a/@href"));
			urls.addAll(document.sel("//div[@class='block']/ul/li/a/@href"));
			urls.addAll(document.sel("//span[@class='s2']/a/@href"));
			Integer total = urls.size();
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】爬取笔趣阁小说网站内容总数量total={}", total);
			}
			for (Object url : urls) {
				String urlString = DOMAIN_URL.concat(url.toString());
				push(Request.build(urlString, "renderNovelInfo"));
			}
		} catch (Exception e) {
			log.error("【爬虫系统后台】爬取笔趣阁小说网站内容", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】结束爬取笔趣阁小说网站内容");
			}
		}
	}

	public void renderNovelInfo(Response response) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】开始渲染爬取到的笔趣阁小说网站小说内容");
			}
			NovelInfo novelInfo = response.render(NovelInfo.class);
			novelInfo.setCover(DOMAIN_URL.concat(novelInfo.getCover()));
			novelInfo.setAuthor(novelInfo.getAuthor().split("：")[1]);
			String realUrl = response.getRealUrl();
			CategoryEnum category = getCategoryType(realUrl);
			novelInfo.setCategoryType(category.getCode());
			novelInfo.setCategoryText(category.getMessage());
			novelInfo.setBookId(UUIDUtil.gen32UUID());
			novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_FINISHED.getCode());
			novelInfo.setHaveRead(category.getCode());
			novelInfo.setType(category.getCode());
			log.info("novelInfo={}", novelInfo);
			NovelInfo novelInfoTmp = novelInfoService.save(novelInfo);
			Integer id = novelInfoTmp.getId();
			List<String> urlList = novelInfo.getChapterUrl();
			for (String string : urlList) {
				String url = DOMAIN_URL.concat(string);
				push(Request.build(url, "renderChapter"));
			}
		} catch (Exception e) {
			log.error("【爬虫系统后台】渲染爬取到的笔趣阁小说网站小说内容出现错误", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】结束渲染爬取到的笔趣阁小说网站小说内容");
			}
		}
	}
	
	public void renderChapter(Response response) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】开始渲染爬到的取笔趣阁小说网站小说章节内容");
			}
			NovelChapter chapter = response.render(NovelChapter.class);
			chapter.setChapterId(UUIDUtil.gen32UUID());
//			chapterService.save(chapter);
			log.info("chapter={}", chapter);
		} catch (Exception e) {
			log.error("【爬虫系统后台】渲染爬取到的笔趣阁小说网站小说章节内容出现错误", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【爬虫系统后台】结束渲染爬取到的笔趣阁小说网站小说章节内容");
			}
		}
	}
	
	public CategoryEnum getCategoryType(String realUrl) {
		if (realUrl.contains("xuanhuan")) {
			return CategoryEnum.FANTASY;
		} if (realUrl.contains("xiuzhen")) {
			return CategoryEnum.COATARD;
		} if (realUrl.contains("dushi")) {
			return CategoryEnum.CITY;
		} if (realUrl.contains("chuanyue")) {
			return CategoryEnum.THROUGH;
		} if (realUrl.contains("wangyou")) {
			return CategoryEnum.WEBGAME;
		} if (realUrl.contains("kehuan")) {
			return CategoryEnum.SCIENCE;
		} if (realUrl.contains("qita")) {
			return CategoryEnum.SCIENCE;
		} else {
			return CategoryEnum.FANTASY;
		}
	}
	
	public synchronized String getNumber(int width, int number) {
    	DecimalFormat df = null;
        String result = null;
        try {
            char[] chs = new char[width];
            for (int i = 0; i < width; i++) {
                chs[i] = '0';
            }
            df = new DecimalFormat(new String(chs));
            result = df.format(number).concat(String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("生成id有误", e);
        }
        return result;
    }
}
