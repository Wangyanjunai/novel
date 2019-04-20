package com.potato369.novel.crawler.crawlers;

import java.util.List;
import java.util.Random;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.constants.BusinessConstants;
import org.seimicrawler.xpath.JXDocument;
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
	private NovelChapterService chapterService;
	
	@Override
	public String[] startUrls() {
		return new String[]{DOMAIN_URL};
	}
	
	@Override
	public void start(Response response){
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始首页爬取");
			}
            JXDocument document = response.document();
            List<Object> urlList = document.sel("//body/div[@class='tuijian']/ul/li/h2/a/@href");
            if (urlList.size() < 1) {
                return;
            }
            log.info("current thread={}", Thread.currentThread());
            BusinessConstants.threadPoolStart.execute(()->{
	            BusinessConstants.lock.lock();
	            for (Object url:urlList) {
	                String urlStr = url.toString();
	                if (!urlStr.startsWith(DOMAIN_URL)) {
	                    BusinessConstants.CURRENT_START_URL = new StringBuffer(DOMAIN_URL).append(urlStr).toString();
	                } else {
	                    BusinessConstants.CURRENT_START_URL = urlStr;
	                }
	                log.info("start current start url={}", BusinessConstants.CURRENT_START_URL);
	                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
	                //当前线程等待直到被唤醒
	                try {
	                    BusinessConstants.conditionPoolStart.await();
	                } catch (Exception e) {
	                    log.error("", e);
	                }
	            }
	            BusinessConstants.lock.unlock();
            }
          );
        } catch (Exception e) {
            log.error("", e);
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束首页爬取");
			}
        }
    }
	
	public void getEachPage(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页分页数据");
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
                    log.info("start current get data url={}", BusinessConstants.CURRENT_GET_DATA_URL);
                    push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler::getEachBook));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolPage.await();
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
                //总页数是多少需要提前获取一下
                if(BusinessConstants.CURRENT_PAGE_NUMBER>=BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER){
                    // 当前类型所有页跑完唤醒上一级
                    BusinessConstants.conditionPoolStart.signal();
                    BusinessConstants.lock.unlock();//调用signal()方法后需要手动释放
                    return;
                }
                String nextPageEndPrefix = BusinessConstants.CURRENT_START_URL.substring(0, BusinessConstants.CURRENT_START_URL.indexOf("sort") + 6);
                log.info(nextPageEndPrefix);
                StringBuffer bf = new StringBuffer(nextPageEndPrefix).append(BusinessConstants.CURRENT_PAGE_NUMBER).append("/");
                if (log.isDebugEnabled()) {
                    log.debug("url={}", bf.toString());
                }
                BusinessConstants.CURRENT_START_URL = bf.toString();
                log.info("start current start url={}", BusinessConstants.CURRENT_START_URL);
                push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler::getEachPage));
                BusinessConstants.lock.unlock();
            });
        } catch (Exception e) {
            log.error("", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolStart.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取每页分页数据");
			}
        }
    }
	
    public void getEachBook(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说数据");
			}
            JXDocument document = response.document();
            List<Object> urlList = document.sel("//div[@class='mulu']/ul/li/a/@href");
            BusinessConstants.threadPoolBook.execute(()-> {
                BusinessConstants.lock.lock();
                for (Object url:urlList) {
                    String urlStr = url.toString();
                    if (!urlStr.startsWith(BusinessConstants.CURRENT_GET_DATA_URL)) {
                        BusinessConstants.CURRENT_GET_BOOK_DATA_URL = new StringBuffer(BusinessConstants.CURRENT_GET_DATA_URL).append(urlStr).toString();
                    } else {
                        BusinessConstants.CURRENT_GET_BOOK_DATA_URL = urlStr;
                    }
                    log.info("start current get book data url={}", BusinessConstants.CURRENT_GET_BOOK_DATA_URL);
                    push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler::renderChapterBean));
                    try {
                        //防止被屏蔽间隔1到2秒钟访问
                        Thread.sleep(new Random().nextInt(1000) + 1000);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    //当前线程等待直到被唤醒
                    try {
                        BusinessConstants.conditionPoolBook.await();
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
                //唤醒上一级线程
                BusinessConstants.conditionPoolPage.signal();
                BusinessConstants.lock.unlock();
            });
        } catch (Exception e) {
            log.error("", e);
            //当前有异常唤醒上一级
            BusinessConstants.conditionPoolPage.signal();
            BusinessConstants.lock.unlock();
        } finally {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】结束爬取每页小说数据");
			}
        }
    }
    
    public void renderNovelInfoBean(Response response) {}
    
    public void renderChapterBean(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说章节数据");
			}
            BusinessConstants.lock.lock();
            //crawlerService.saveForDuShu88(response);
            chapterService.save(response, 1000);
            //唤醒上一级线程
            BusinessConstants.conditionPoolBook.signal();
            BusinessConstants.lock.unlock();
        } catch (Exception e) {
            log.error("", e);
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