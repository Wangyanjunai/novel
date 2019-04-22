package com.potato369.novel.crawler.crawlers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import com.potato369.novel.basic.utils.UUIDUtil;
import com.potato369.novel.crawler.domain.Chapter;
import com.vladsch.flexmark.convert.html.FlexmarkHtmlParser;
import com.potato369.novel.basic.constants.BusinessConstants;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.CategoryEnum;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * 类名: DuShu88NovelCrawler
 * 类的作用:爬取88读书网的小说信息
 * 创建原因:
 * 创建时间: 2019年4月15日 上午9:07:36
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.8
 * </pre>
 */
@Crawler(name = "88dush", useUnrepeated = true) 
@Slf4j
public class DuShu88NovelCrawler extends BaseSeimiCrawler{

	private static final long serialVersionUID = 3993378973651481714L;

	private static final String DOMAIN_URL = "https://www.88dush.com";
	
	@Autowired
	private NovelInfoService novelInfoService;
	
	@Autowired
	private NovelChapterService chapterService;
	
	@Override
	public String[] startUrls() {
		return new String[]{DOMAIN_URL};
	}
	
	@Override
	public void start(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取八八读书网小说推荐分类信息");
			}
            JXDocument document = response.document();
            List<Object> urlList = document.sel("//body/div[@class='tuijian']/ul/li/h2/a/@href");
            urlList.add("/sort8/1/");
            if (urlList.size() < 1) { 
                return;
            }
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】爬取八八读书网小说推荐分类信息的当前运行线程信息info={}", Thread.currentThread());
            	log.debug("【后台爬虫系统爬取数据】爬取八八读书网小说推荐分类信息的the url list size={}", urlList.size());
			}
            BusinessConstants.threadPoolStart.execute(()-> {
	            BusinessConstants.lock.lock();
	            for (Object url:urlList) {
	                String urlStr = url.toString();
	                if (!urlStr.startsWith(DOMAIN_URL)) {
	                    BusinessConstants.CURRENT_START_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
	                } else {
	                    BusinessConstants.CURRENT_START_URL = urlStr;
	                }
	                if (log.isDebugEnabled()) {
	                	log.debug("【后台爬虫系统爬取数据】爬取八八读书网小说信息每次爬取的分类信息URL={}", BusinessConstants.CURRENT_START_URL);
					}
	                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
	                //当前线程等待直到被唤醒
	                try {
	                    BusinessConstants.conditionPoolStart.await();
	                } catch (Exception e) {
	                    log.error("【后台爬虫系统爬取数据】爬取八八读书网小说信息出现错误", e);
	                }
	            }
	            BusinessConstants.lock.unlock();
            }
          );
        } catch (Exception e) {
            log.error("【后台爬虫系统爬取数据】爬取八八读书网小说信息出现错误", e);
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取八八读书网小说推荐分类信息");
			}
        }
    }
	
	public void getEachPage(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取八八读书网小说分页数据");
			}
            JXDocument document = response.document();
            List<Object> urlList = document.sel("//div[@class='booklist']/ul/li/span[@class='sm']/a/@href");
            //获取每一种类型的总页数
            List<Object> pageTotal = document.sel("//div[@class='booklist']/div[@class='pagelink']/a[@class='last']/text()");
            BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER = Integer.valueOf(pageTotal.get(0).toString());
            BusinessConstants.CURRENT_PAGE_NUMBER ++;
            BusinessConstants.threadPoolPage.execute(()-> {
                BusinessConstants.lock.lock();
                for (Object url:urlList) {
                    String urlStr = url.toString();
                    if (!urlStr.startsWith(DOMAIN_URL)) {
                        BusinessConstants.CURRENT_GET_DATA_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
                    } else {
                        BusinessConstants.CURRENT_GET_DATA_URL = urlStr;
                    }
                    if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】当前获取数据的URL={}", BusinessConstants.CURRENT_GET_DATA_URL);
					}
                    push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::getEachBook));
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        log.error("【后台爬虫系统爬取数据】爬取八八读书网小说分页数据出现错误", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolPage.await();
                    } catch (Exception e) {
                        log.error("【后台爬虫系统爬取数据】爬取八八读书网小说分页数据出现错误", e);
                    }
                }
                //总页数是多少需要提前获取一下
                if(BusinessConstants.CURRENT_PAGE_NUMBER >= BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER){
                    //当前类型所有页跑完唤醒上一级
                	BusinessConstants.CURRENT_PAGE_NUMBER = 1;//将这个变量重置为1
                    BusinessConstants.conditionPoolStart.signal();
                    BusinessConstants.lock.unlock();//调用signal()方法后需要手动释放
                    return;
                }
                String nextPageEndPrefix = BusinessConstants.CURRENT_START_URL.substring(0, BusinessConstants.CURRENT_START_URL.indexOf("sort") + 6);
                if (log.isDebugEnabled()) {
                	log.debug("【后台爬虫系统爬取数据】下一页分页信息的后缀 the next page end prefix ={}", nextPageEndPrefix);
				}
                StringBuffer bf = new StringBuffer(nextPageEndPrefix).append(BusinessConstants.CURRENT_PAGE_NUMBER).append("/");
                if (log.isDebugEnabled()) {
                	log.debug("【后台爬虫系统爬取数据】下一页分页信息的URL={}", bf.toString());
                }
                BusinessConstants.CURRENT_START_URL = bf.toString();
                if (log.isDebugEnabled()) {
                	log.debug("【后台爬虫系统爬取数据】分页信息的开始的URL={}", BusinessConstants.CURRENT_START_URL);
                }
                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
                BusinessConstants.lock.unlock();
            });
        } catch (Exception e) {
            log.error("【后台爬虫系统爬取数据】爬取八八读书网小说分页数据出现错误", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolStart.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取八八读书网小说分页数据");
			}
        }
    }
	
    public void getEachBook(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说的目录和内容数据");
			}
            JXDocument document = response.document();
            List<Object> urlList = document.sel("//div[@class='mulu']/ul/li/a/@href");
            BusinessConstants.CURRENT_TOTAL_CHAPTERS = urlList.size();
            BusinessConstants.threadPoolBook.execute(()-> {
                BusinessConstants.lock.lock();
                for (Object url:urlList) {
                    String urlStr = url.toString();
                    if (!urlStr.startsWith(BusinessConstants.CURRENT_GET_DATA_URL)) {
                        BusinessConstants.CURRENT_GET_BOOK_DATA_URL = new StringBuffer(BusinessConstants.CURRENT_GET_DATA_URL).append(urlStr).toString();
                    } else {
                        BusinessConstants.CURRENT_GET_BOOK_DATA_URL = urlStr;
                    }
                    if (log.isDebugEnabled()) {
                    	log.debug("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据URL={}", BusinessConstants.CURRENT_GET_BOOK_DATA_URL);
					}
                    push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler::renderChapterBean));
                    try {
                        //防止被屏蔽间隔0.1到0.2秒钟访问
                        Thread.sleep(new Random().nextInt(100) + 100);
                    } catch (Exception e) {
                        log.error("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据出现错误", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolBook.await();
                    } catch (Exception e) {
                    	log.error("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据出现错误", e);
                    }
                }
                //唤醒上一级线程
                BusinessConstants.conditionPoolPage.signal();
                BusinessConstants.lock.unlock();
            });
            NovelInfo novelInfo = NovelInfo.builder().build();
            List<Object> categoryList = document.sel("//div[@class='place']/a/text()");
            StringBuffer category = new StringBuffer();
            for (Object object : categoryList) {
            	category.append(object.toString()).append(",");
			}
            String categoryText = category.toString().split(",")[1];
            novelInfo.setCategoryText(category.toString().split(",")[1]);//1、小说分类名称
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页1、小说分类名称data={}", categoryText);
			}
            if ("玄幻魔法".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.XUANHUANQIHUAN.getCode());//2、小说分类类型type
			}
            if ("武侠修真".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.XIANXIAWUXIA.getCode());
			}
            if ("都市言情".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.DUSHIQINGGAN.getCode());
			}
            if ("历史穿越".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.CHUANGYUECHONGSHENG.getCode());
			}
            if ("恐怖悬疑".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.XUANYILINGYI.getCode());
			}
            if ("游戏竞技".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.YOUXIJINGJI.getCode());
			}
            if ("军事科幻".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.LISHIJUNSHI.getCode());
			}
            if ("女生频道".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.DUSHISHENGHUO.getCode());
			}
            if ("综合类型".equals(categoryText)) {
            	novelInfo.setCategoryType(CategoryEnum.QITALEIBIE.getCode());
			}
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页2、小说分类type data={}", novelInfo.getCategoryType());
			}
            List<Object> novelTitleList = document.sel("//div[@class='jieshao']/div[@class='rt']/h1/text()");
            String title = "";//3、小说名称（标题）数据
            for (Object object : novelTitleList) {
            	title = object.toString();
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页3、小说名称（标题）data={}", title);
    			}
			}
            novelInfo.setTitle(title);
            List<Object> novelCoverList = document.sel("//div[@class='jieshao']/div[@class='lf']/img/@src");
            String coverURL = "";//4、小说封面图片路径数据
            for (Object object : novelCoverList) {
            	coverURL = object.toString();
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页4、小说封面图片路径data={}", coverURL);
    			}
			}
            novelInfo.setCoverURL(coverURL);
            List<Object> novelAuthorList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[1]/text()");
            String author = "";//5、小说作者信息数据
            for (Object object : novelAuthorList) {
            	author = object.toString().split("：")[1];
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页5、小说作者信息data={}", author);
    			}
			}
            novelInfo.setAuthor(author);
            List<Object> novelstatusList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[2]/text()");
            String status = "";//6、小说更新状态数据
            for (Object object : novelstatusList) {
            	status = object.toString().split("：")[1];
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页6、小说更新状态data={}", status);
    			}
			}
            if ("连载中".equals(status)) {
            	novelInfo.setNovelStatus(1);
			}else if ("已完结".equals(status)) {
            	novelInfo.setNovelStatus(0);
			} else {
				novelInfo.setNovelStatus(1);
			}
            List<Object> novelIntroList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='intro']/text()");
            String intro = "";//7、小说简介数据
            for (Object object : novelIntroList) {
            	intro = object.toString();
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页7、小说简介data={}", intro);
    			}
			}
            novelInfo.setIntroduction(intro);
            String id = UUIDUtil.gen32UUID();
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页8、小说id信息data={}", id);
			}
            List<Object> novelLastChapterTitleList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[4]/a/text()");
            String lastChapterTitle = "";//9、小说最新章节标题数据
            for (Object object : novelLastChapterTitleList) {
            	lastChapterTitle = object.toString();
            	if (log.isDebugEnabled()) {
            		log.debug("【后台爬虫系统爬取数据】开始爬取每页9、小说最新章节标题data={}", lastChapterTitle);
    			}
			}
            novelInfo.setLastChapterTitle(lastChapterTitle);
            novelInfo.setId(id);
            novelInfo.setClickNumber(BigDecimal.ZERO);
            novelInfo.setPublisher("八八读书网（88dush.com）");
            novelInfo.setTotalWords(BigDecimal.ZERO);
            novelInfo.setTotalChapters(BusinessConstants.CURRENT_TOTAL_CHAPTERS);
            novelInfo.setReaders(BigDecimal.ZERO);
            novelInfo.setRecentReaders(BigDecimal.ZERO);
            novelInfo.setRetention(0);
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说信息数据={}", novelInfo);
			}
            novelInfoService.save(novelInfo);
        } catch (Exception e) {
            log.error("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据出现错误", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolPage.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取每页小说的目录和内容数据");
			}
        }
    }
    
    public void renderChapterBean(Response response) {
    	Chapter chapter = null;
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说章节数据");
			}
            BusinessConstants.lock.lock();
            try {
            	chapter = response.render(Chapter.class);
        		if (log.isDebugEnabled()) {
        			log.debug("bean resolve res={}, url={}", chapter, response.getUrl());
				}
        		NovelChapter novelChapter = NovelChapter.builder().build();
        		if (chapter != null) {
        			chapter.setId(UUIDUtil.gen32UUID());
        			String htmlContent = null;
        			if(StringUtils.isNotBlank(chapter.getContent())){
        				chapter.setStarturl(BusinessConstants.CURRENT_GET_DATA_URL);
        				chapter.setUrl(BusinessConstants.CURRENT_GET_BOOK_DATA_URL);
                        htmlContent = chapter.getContent();
                        //内容不为空的时候转化
                        chapter.setContent(FlexmarkHtmlParser.parse(htmlContent));
                    }
        			BeanUtils.copyProperties(chapter, novelChapter);
        			String title = chapter.getNovelName();
        			String categoryText = chapter.getCategoryText();
        			NovelInfo novelInfo = novelInfoService.findByTitleAndCategoryText(title, categoryText);
        			if (novelInfo != null) {
        				novelChapter.setBookId(novelInfo.getId());
        				novelChapter.setLastChapterName(novelInfo.getLastChapterTitle());
					} else {
						novelChapter.setBookId(UUIDUtil.gen32UUID());
						novelChapter.setLastChapterName("最后一章出错了");
					}
        			NovelChapter novelChapterTemp = chapterService.save(novelChapter);
        			String lastChapterName = null;
        			if (novelChapterTemp != null) {
        				lastChapterName = novelChapterTemp.getLastChapterName();
        				List<NovelChapter> chapterList = chapterService.findByChaperTitle(lastChapterName);
        				if (chapterList != null && chapterList.size() > 0) {
        					NovelChapter lastChapter = chapterList.get(0);
        					String lastChapterId = lastChapter.getId();
        					String bookId = lastChapter.getBookId();
        					NovelInfo novelInfo2 = novelInfoService.find(bookId);
        					if (novelInfo2 != null) {
        						novelInfo2.setLastChapterId(lastChapterId);
        						novelInfoService.update(novelInfo2);
        						if (log.isDebugEnabled()) {
        		            		log.debug("【后台爬虫系统爬取数据】开始爬取每页10、小说最新章节id信息data={}", lastChapterId);
        		    			}
							}
						}
					}
        			if (log.isDebugEnabled()) {
        				log.debug("covert copy store success in db, novel chapter info={}", novelChapterTemp);
        			}
    			}
    		} catch (Exception e) {
    			log.error("【后台管理】爬取小说章节信息失败", e);
    		} finally {
    			if (log.isDebugEnabled()) {
    				log.debug("render success chapter info={}", chapter);
				}
    		}
            //唤醒上一级线程
            BusinessConstants.conditionPoolBook.signal();
            BusinessConstants.lock.unlock();
        } catch (Exception e) {
            log.error("【后台爬虫系统爬取数据】爬取每页小说章节数据出现错误", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolBook.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取每页小说章节数据");
			}
		}
    }
}