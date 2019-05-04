package com.potato369.novel.crawler.crawlers;

import java.math.BigDecimal;
import java.util.LinkedList;
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
@Crawler(name = "88dush7", useUnrepeated = true) 
@Slf4j
public class DuShu88NovelCrawler7 extends BaseSeimiCrawler{

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
//            JXDocument document = response.document();
//            List<Object> urlList = document.sel("//body/div[@class='tuijian']/ul/li/h2/a/@href");
            List<Object> urlList = new LinkedList<Object>();
            urlList.add("/sort7/1/");
            if (urlList == null || urlList.isEmpty() || urlList.size() < 1) { 
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
	                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler7::getEachPage));
	                //当前线程等待直到被唤醒
	                try {
	                    BusinessConstants.conditionPoolStart.await();
	                } catch (InterruptedException e) {
	                    log.error("【后台爬虫系统爬取数据】爬取八八读书网小说信息出现InterruptedException异常", e);
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
                    push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler7::getEachBook));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        log.error("【后台爬虫系统爬取数据】爬取八八读书网小说分页数据出现InterruptedException异常", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolPage.await();
                    } catch (InterruptedException e) {
                        log.error("【后台爬虫系统爬取数据】爬取八八读书网小说分页数据出现InterruptedException异常", e);
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
                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler7::getEachPage));
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
                    push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler7::renderChapterBean));
                    BusinessConstants.CURRENT_CHAPTER_INDEX ++;
                    try {
                        //防止被屏蔽间隔1到2秒钟再访问
                        Thread.sleep(new Random().nextInt(1000) + 1000);
                    } catch (InterruptedException e) {
                        log.error("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据出现InterruptedException异常", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolBook.await();
                    } catch (InterruptedException e) {
                    	log.error("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据出现InterruptedException异常", e);
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
            novelInfo.setCategoryCNText(category.toString().split(",")[1]);//1、小说分类中文名称
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取1、小说分类中文名称data={}", novelInfo.getCategoryCNText());
			}
            novelInfo = novelInfo.compasByCategoryCNText(novelInfo, categoryText);
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取2、小说分类类型type data={}", novelInfo.getCategoryType());
        		log.debug("【后台爬虫系统爬取数据】开始爬取3、小说分类英文名称data={}", novelInfo.getCategoryENText());
			}
            List<Object> novelTitleList = document.sel("//div[@class='jieshao']/div[@class='rt']/h1/text()");
            String title = "";//3、小说名称（标题）数据
            for (Object object : novelTitleList) {
            	title = object.toString();
			}
            novelInfo.setTitle(title);
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取4、小说名称（标题）data={}", novelInfo.getTitle());
			}
            List<Object> novelCoverList = document.sel("//div[@class='jieshao']/div[@class='lf']/img/@src");
            String coverURL = "";//4、小说封面图片路径数据
            for (Object object : novelCoverList) {
            	coverURL = object.toString();
			}
            novelInfo.setCoverURL(coverURL);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取5、小说封面图片路径data={}", novelInfo.getCoverURL());
            }
            List<Object> novelAuthorList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[1]/text()");
            String author = "";//5、小说作者信息数据
            for (Object object : novelAuthorList) {
            	author = object.toString().split("：")[1];
			}
            novelInfo.setAuthor(author);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取6、小说作者信息data={}", novelInfo.getAuthor());
            }
            List<Object> novelstatusList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[2]/text()");
            String status = "";//6、小说更新状态数据
            for (Object object : novelstatusList) {
            	status = object.toString().split("：")[1];
			}
            if ("连载中".equals(status)) {
            	novelInfo.setNovelStatus(1);
			} else if ("已完结".equals(status)) {
            	novelInfo.setNovelStatus(0);
			} else {
				novelInfo.setNovelStatus(1);
			}
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取7、小说更新状态data={}", novelInfo.getNovelStatus());
            }
            List<Object> novelIntroList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='intro']/text()");
            String intro = "";//7、小说简介数据
            for (Object object : novelIntroList) {
            	intro = object.toString();
			}
            novelInfo.setIntroduction(intro);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取8、小说简介data={}", novelInfo.getIntroduction());
            }
            String id = UUIDUtil.gen32UUID();
            novelInfo.setId(id);
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取9、小说id信息data={}", novelInfo.getId());
			}
            List<Object> novelLastChapterTitleList = document.sel("//div[@class='jieshao']/div[@class='rt']/div[@class='msg']/em[4]/a/text()");
            String lastChapterTitle = "";//9、小说最新章节标题数据
            for (Object object : novelLastChapterTitleList) {
            	lastChapterTitle = object.toString();
			}
            novelInfo.setNewestChapterTitle(lastChapterTitle);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取10、小说最新章节标题data={}", novelInfo.getNewestChapterTitle());
            }
            novelInfo.setClickNumber(BigDecimal.ZERO);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取11、小说点击数data={}", novelInfo.getClickNumber());
            }
            novelInfo.setPublisher("八八读书网（88dush.com）");
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取12、小说出版社或爬取网站名称data={}", novelInfo.getPublisher());
            }
            novelInfo.setTotalWords(BigDecimal.ZERO);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取13、小说总字数data={}", novelInfo.getTotalWords());
            }
            novelInfo.setTotalChapters(BusinessConstants.CURRENT_TOTAL_CHAPTERS);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取14、小说总章节数data={}", novelInfo.getTotalChapters());
            }
            novelInfo.setReaders(BigDecimal.ZERO);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取15、小说阅读人数data={}", novelInfo.getReaders());
            }
            novelInfo.setRecentReaders(BigDecimal.ZERO);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取16、小说跟随阅读人数data={}", novelInfo.getRecentReaders());
            }
            novelInfo.setRetention(0);
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】开始爬取17、小说留存率data={}", novelInfo.getRetention());
            }
            if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说信息数据={}", novelInfo);
			}
            NovelInfo novelInfo2 = novelInfoService.findByTitleAndCategoryText(title, categoryText);
            if (novelInfo2 == null) {
            	novelInfoService.save(novelInfo);
			}
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
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取小说每个章节的数据");
			}
            BusinessConstants.lock.lock();
            Chapter chapter = null;
            try {
            	chapter = response.render(Chapter.class);
            	chapter.setIndex(BusinessConstants.CURRENT_CHAPTER_INDEX);
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
        			String categoryText = chapter.getCategoryCNText();
        			NovelInfo novelInfo = novelInfoService.findByTitleAndCategoryText(title, categoryText);
        			if (novelInfo != null) {
        				novelChapter.setBookId(novelInfo.getId());
        				novelChapter.setNewestChapterTitle(novelInfo.getNewestChapterTitle());
        				NovelChapter novelChapter2 = chapterService.findChaperByTitleAndBookId(chapter.getTitle(), novelInfo.getId());
        				NovelChapter novelChapterTemp = null;
        				if (novelChapter2 == null) {
        					novelChapterTemp = chapterService.save(novelChapter);
                			String lastChapterName = null;
                			if (novelChapterTemp != null) {
                				lastChapterName = novelChapterTemp.getNewestChapterTitle();
                				List<NovelChapter> chapterList = chapterService.findByChaperTitle(lastChapterName);
                				if (chapterList != null && chapterList.size() > 0) {
                					NovelChapter lastChapter = chapterList.get(0);
                					String lastChapterId = lastChapter.getId();
                					String bookId = lastChapter.getBookId();
                					NovelInfo novelInfo2 = novelInfoService.find(bookId);
                					if (novelInfo2 != null) {
                						novelInfo2.setNewestChapterId(lastChapterId);
                						novelInfoService.update(novelInfo2);
                						if (log.isDebugEnabled()) {
                		            		log.debug("【后台爬虫系统爬取数据】开始爬取每页10、小说最新章节id信息data={}", lastChapterId);
                		    			}
        							}
        						}
        					}
                			if (log.isDebugEnabled()) {
                				log.debug("成功保存章节信息到数据库info={}", novelChapterTemp);
                			}
						}
					}
    			}
        		if (BusinessConstants.CURRENT_CHAPTER_INDEX >= BusinessConstants.CURRENT_TOTAL_CHAPTERS) {
        			//当前类型所有页跑完唤醒上一级
                	BusinessConstants.CURRENT_CHAPTER_INDEX = BusinessConstants.CURRENT_TOTAL_CHAPTERS;//将这个变量重置为0
                	BusinessConstants.CURRENT_CHAPTER_INDEX = 0;
				}
    		} catch (Exception e) {
    			//唤醒上一级线程
                BusinessConstants.conditionPoolBook.signal();
                BusinessConstants.lock.unlock();
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
            log.error("【后台爬虫系统爬取数据】爬取小说每个章节数据出现错误", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolBook.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束小说每个章节数据");
			}
		}
    }
}