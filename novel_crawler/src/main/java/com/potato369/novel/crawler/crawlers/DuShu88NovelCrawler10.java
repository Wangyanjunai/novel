package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.constants.BusinessConstants;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.NovelInfoEnum;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import com.potato369.novel.crawler.domain.Chapter;
import com.vladsch.flexmark.convert.html.FlexmarkHtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 * 类名: DuShu88NovelCrawler0
 * 类的作用:爬取88读书网的小说信息
 * 创建原因:
 * 创建时间: 2019年5月03日 上午9:07:36
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.8
 * </pre>
 */
@Crawler(name = "88dush10")
@Slf4j
public class DuShu88NovelCrawler10 extends BaseSeimiCrawler{

	private static final long serialVersionUID = 3993378973651481754L;

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
                log.debug("【后台爬虫系统爬取数据】start==================爬取八八读书（88dush.com）小说网小说推荐分类data==================start");
            }
            JXDocument document = response.document();
            List<Object> categorySortList = document.sel("//body/div[@class='tuijian']/ul/li/h2/a/@href");
            if (categorySortList == null || categorySortList.isEmpty() || categorySortList.size() < 1) {
            	return;
			} else {
            	categorySortList.add(7, "/sort8/1/");
			}
            if (log.isDebugEnabled()) {
            	log.debug("【后台爬虫系统爬取数据】爬取八八读书（88dush.com）小说网小说推荐分类data sort=={}", categorySortList);
			}
			if (log.isDebugEnabled()) {
				log.debug("【后台爬虫系统爬取数据】爬取八八读书（88dush.com）小说网小说推荐分类data的当前线程信息data info=={}", Thread.currentThread());
			}
            BusinessConstants.threadPoolStart.execute(()->{
				BusinessConstants.lock.lock();
				for (Object categorySort:categorySortList) {
					String categorySortUrl = categorySort.toString();
					if (!categorySortUrl.startsWith(DOMAIN_URL) || !categorySortUrl.contains(DOMAIN_URL)) {
						BusinessConstants.CURRENT_START_URL = new StringBuffer().append(DOMAIN_URL).append(categorySortUrl).toString();
					} else {
						BusinessConstants.CURRENT_START_URL = categorySortUrl;
					}
					push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler10::getEachPage));
					try {
						BusinessConstants.conditionPoolStart.await();
					} catch (InterruptedException e) {
						log.error("【后台爬虫系统爬取数据】爬取八八读书（88dush.com）小说网小说推荐分类data出现InterruptedException", e);
					}
				}
				BusinessConstants.lock.unlock();
			});
        } catch (Exception e) {
            log.error("【后台爬虫系统爬取数据】爬取八八读书网小说信息出现Exception", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台爬虫系统爬取数据】end====================爬取八八读书（88dush.com）小说网小说推荐分类data====================end");
            }
        }
    }

    public void getEachPage(Response response) {
		try {
            if (log.isDebugEnabled()) {
                log.debug("【后台爬虫系统爬取数据】start===================分页爬取八八读书（88dush.com）小说网每页data===================start");
            }
            JXDocument document = response.document();
			try {
				BusinessConstants.threadPoolBook.execute(()->{
					BusinessConstants.lock.lock();
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】start=====================爬取每页推荐dl元素小说信息列表====================start");
					}
					Object categoryCNTextObj = document.selOne("//body/div[@class='booklist']/h1/text()");
					String categoryCNTextStr = null;
					if (categoryCNTextObj != null) {
						categoryCNTextStr = categoryCNTextObj.toString();
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】小说分类中文名称data=={}", categoryCNTextStr);
					}
					List<Object> dlObjList = document.sel("//body/div[@class='fengtui']/dl");
					if (dlObjList == null || dlObjList.isEmpty() || dlObjList.size() < 1) {
						return;
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】每页dl元素对象列表大小data=={}", dlObjList.size());
						for (Object dl:dlObjList) {
							log.debug("【后台爬虫系统爬取数据】每页dl元素转换为字符串data=={}", dl.toString());
							JXDocument dlJxDocument = JXDocument.create(dl.toString());
							NovelInfo novelInfo = NovelInfo.builder().build();//初始化对象
							novelInfo = novelInfo.compasByCategoryCNText(novelInfo, categoryCNTextStr);
							novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
							Object coverImageObj = dlJxDocument.selOne("//dl/dt/a/img/@src");//获取封面图片路径
							String coverImageUrlStr = null;
							if (coverImageObj != null) {
								coverImageUrlStr = coverImageObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页封面图片路径url转换为字符串data=={}", coverImageUrlStr);
							novelInfo.setCoverURL(coverImageUrlStr);//4、设置小说封面图片路径
							Object novelGetDataObj = dlJxDocument.selNOne("//dl/dd/h3/a/@href");//获取小说信息数据路径
							String novelGetDataUrlStr = null;
							if (novelGetDataObj != null) {
								novelGetDataUrlStr = novelGetDataObj.toString();
								if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
									novelGetDataUrlStr = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
								}
								BusinessConstants.CURRENT_GET_DATA_URL = novelGetDataUrlStr;
							}
							log.debug("【后台爬虫系统爬取数据】每页小说内容信息数据路径url转换为字符串data=={}", novelGetDataUrlStr);
							Object novelTitleObj = dlJxDocument.selNOne("//dl/dd/h3/a/text()");//获取小说名称
							String novelTitleStr = null;
							if (novelTitleObj != null) {
								novelTitleStr = novelTitleObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页小说名称信息数据转换为字符串data=={}", novelTitleStr);
							novelInfo.setTitle(novelTitleStr);//5、设置小说名称
							Object novelAuthorObj = dlJxDocument.selNOne("//dl/dd/span/text()");//获取小说作者
							String novelAuthorStr = null;
							if (novelAuthorObj != null) {
								novelAuthorStr = novelAuthorObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】小说作者data=={}", novelAuthorStr);
							novelInfo.setAuthor(novelAuthorStr);//6、设置小说作者
							Object novelIntroductionObj = dlJxDocument.selNOne("//dl/dd/p/text()");//获取小说简介
							String novelIntroductionStr = null;
							if (novelIntroductionObj != null) {
								novelIntroductionStr = novelIntroductionObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】小说简介data=={}", novelIntroductionStr);
							novelInfo.setIntroduction(novelIntroductionStr);//7、设置小说简介
							novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_UPDATING.getCode());//8、设置小说更新状态为连载中
							novelInfo.setCategoryCNText(categoryCNTextStr);//9、设置小说分类中文名称
							novelInfo.setClickNumber(BigDecimal.ZERO);//10、设置小说阅读（点击）次数
							novelInfo.setPublisher("八八读书网（88dush.com）");//11、设置小说出版社名称或者爬取的网站名称
							novelInfo.setTotalWords(BigDecimal.ZERO);//12、设置小说总字数
							novelInfo.setTotalChapters(0);//13、设置小说总章节数
							novelInfo.setReaders(BigDecimal.ZERO);//14、设置小说阅读人数
							novelInfo.setRecentReaders(BigDecimal.ZERO);//15、设置小说最近跟随阅读（或关注）人数
							novelInfo.setRetention(0);//16、设置小说留存率
							NovelInfo novelInfoTmp = novelInfoService.findByTitleAndCategoryTextAndAuthor(novelInfo.getTitle(), novelInfo.getCategoryCNText(), novelInfo.getAuthor());
							if (novelInfoTmp == null) {
								NovelInfo novelInfoSaveResult = novelInfoService.save(novelInfo);
								log.debug("【后台爬虫系统爬取数据】保存到数据库小说data=={}", novelInfoSaveResult);
							} else {
								NovelInfo novelInfoUpdate = copy(novelInfoTmp, novelInfo);
								NovelInfo novelInfoUpdateResult = novelInfoService.save(novelInfoUpdate);
								log.debug("【后台爬虫系统爬取数据】更新到数据库小说信息数据data=={}", novelInfoUpdateResult);
							}
							push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler10::getEachBook));
						}
					}
					BusinessConstants.lock.unlock();
				});
			} catch (Exception e) {
				log.error("【后台爬虫系统爬取数据】error=====================爬取每页推荐dl元素小说信息列表====================error", e);
				BusinessConstants.threadPoolBook.wait();
				BusinessConstants.lock.unlock();
			} finally {
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】end=======================爬取每页推荐dl元素小说信息列表======================end");
				}
			}
			try {
				BusinessConstants.threadPoolBook.execute(()->{
					BusinessConstants.lock.lock();
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】start=====================爬取每页li元素小说信息列表====================start");
					}
					Object categoryCNTextObj = document.selOne("//body/div[@class='booklist']/h1/text()");
					String categoryCNTextStr = null;
					if (categoryCNTextObj != null) {
						categoryCNTextStr = categoryCNTextObj.toString();
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】小说分类中文名称data=={}", categoryCNTextStr);
					}
					List<Object> liObjList = document.sel("//body/div[@class='booklist']/ul/li[@class!='t']");
					if (liObjList == null || liObjList.isEmpty() || liObjList.size() < 1) {
						return;
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】每页li元素对象列表大小data=={}", liObjList.size());
						for (Object li:liObjList) {
							log.debug("【后台爬虫系统爬取数据】每页li元素转换为字符串data=={}", li.toString());
							JXDocument dlJxDocument = JXDocument.create(li.toString());
							/**
							 * <li>
							 *     <span class="sm"><a href="/xiaoshuo/104/104864/"><b>墨爷有令：乖乖受宠</b></a></span>
							 *     <span class="zj"><a href="/xiaoshuo/104/104864/47998442.html">631、爱的结婚，爱的延续（大结局）</a></span>
							 *     <span class="zz">铭希</span>
							 *     <span class="zs">7823774字</span>
							 *     <span class="sj">19-04-03</span>
							 *     <span class="zt">连载中</span>
							 *     <span class="fs">8人</span>
							 * </li>
							 */
							NovelInfo novelInfo = NovelInfo.builder().build();
							novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
							Object novelGetDataObj = dlJxDocument.selNOne("//li/span[@class='sm']/a/@href");//获取小说信息数据路径
							String novelGetDataUrlStr = null;
							if (novelGetDataObj != null) {
								novelGetDataUrlStr = novelGetDataObj.toString();
								if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
									novelGetDataUrlStr = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
								}
								BusinessConstants.CURRENT_GET_DATA_URL = novelGetDataUrlStr;
							}
							log.debug("【后台爬虫系统爬取数据】每页小说内容信息数据路径url转换为字符串data=={}", novelGetDataUrlStr);
							Object novelTitleObj = dlJxDocument.selNOne("//li/span[@class='sm']/a/b/text()");//获取小说名称
							String novelTitleStr = null;
							if (novelTitleObj != null) {
								novelTitleStr = novelTitleObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页小说名称data=={}", novelTitleStr);
							novelInfo.setTitle(novelTitleStr);//2、设置小说名称
							Object novelNewestChapterTitleObj = dlJxDocument.selNOne("//li/span[@class='zj']/a/text()");//获取小说最新章节标题
							String novelNewestChapterTitleStr = null;
							if (novelNewestChapterTitleObj != null) {
								novelNewestChapterTitleStr = novelNewestChapterTitleObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页小说最新章节标题data=={}", novelNewestChapterTitleStr);
							novelInfo.setNewestChapterTitle(novelNewestChapterTitleStr);//3、设置小说最新章节
							Object novelAuthorObj = dlJxDocument.selNOne("//li/span[@class='zz']/text()");//获取小说作者
							String novelAuthorStr = null;
							if (novelAuthorObj != null) {
								novelAuthorStr = novelAuthorObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页小说作者data=={}", novelAuthorStr);
							novelInfo.setAuthor(novelAuthorStr);//4、设置小说作者
							Object novelTotalWordsObj = dlJxDocument.selNOne("//li/span[@class='zs']/num()");//获取小说字数
							String novelTotalWordsStr = null;
							BigDecimal novelTotalWordsDecimal = BigDecimal.ZERO;
							if (novelTotalWordsObj != null) {
								novelTotalWordsStr = novelTotalWordsObj.toString();
								novelTotalWordsDecimal = new BigDecimal(novelTotalWordsStr);
							}
							log.debug("【后台爬虫系统爬取数据】每页小说总字数 BigDecimal data=={}", novelTotalWordsDecimal);
							novelInfo.setTotalWords(novelTotalWordsDecimal);//5、设置小说总字数
							Object novelStatusObj = dlJxDocument.selNOne("//li/span[@class='zt']/text()");//获取小说状态
							String novelStatusStr = null;
							if (novelStatusObj != null) {
								novelStatusStr = novelStatusObj.toString();
							}
							log.debug("【后台爬虫系统爬取数据】每页小说状态信息数据转换为字符串data=={}", novelStatusStr);
							if (NovelInfoEnum.NOVEL_STATUS_UPDATING.getMessage().equals(novelStatusStr)) {
								novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_UPDATING.getCode());//6、设置小说更新状态为连载中
							} else if (NovelInfoEnum.NOVEL_STATUS_FINISHED.getMessage().equals(novelStatusStr)) {
								novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_FINISHED.getCode());//6、设置小说更新状态为已完成
							}
							Object novelRedersObj = dlJxDocument.selNOne("//li/span[@class='fs']/num()");//获取小说阅读数
							String novelRedersStr = null;
							BigDecimal novelRedersDecimal = BigDecimal.ZERO;
							if (novelRedersObj != null) {
								novelRedersStr = novelRedersObj.toString();
								novelRedersDecimal = new BigDecimal(novelRedersStr);
							}
							log.debug("【后台爬虫系统爬取数据】每页小说阅读人数信息数据转换为BigDecimaldata=={}", novelRedersDecimal);
							novelInfo.setReaders(novelRedersDecimal);//7、设置小说阅读人数
							novelInfo.setClickNumber(BigDecimal.ZERO);//8、设置小说点击（阅读）次数
							novelInfo.setPublisher("八八读书网（88dush.com）");//9、设置小说出版社或者爬取的网站名称
							novelInfo.setTotalChapters(0);//10、设置小说总章节数
							novelInfo.setRecentReaders(BigDecimal.ZERO);//11、设置小说跟随阅读人数
							novelInfo.setRetention(0);//12、设置小说留存率
							novelInfo.setCategoryCNText(categoryCNTextStr);//13、设置小说分类中文名称
							novelInfo = novelInfo.compasByCategoryCNText(novelInfo, categoryCNTextStr);
							NovelInfo novelInfoTmp = novelInfoService.findByTitleAndCategoryTextAndAuthor(novelTitleStr, categoryCNTextStr, novelAuthorStr);
							if (novelInfoTmp == null) {
								NovelInfo novelInfoSaveResult = novelInfoService.save(novelInfo);
								log.debug("【后台爬虫系统爬取数据】保存到数据库小说data=={}", novelInfoSaveResult);
							} else {
								NovelInfo novelInfoUpdate = copy(novelInfoTmp, novelInfo);
								NovelInfo novelInfoUpdateResult = novelInfoService.save(novelInfoUpdate);
								log.debug("【后台爬虫系统爬取数据】更新到数据库小说信息数据data=={}", novelInfoUpdateResult);
							}
							push(Request.build(BusinessConstants.CURRENT_GET_DATA_URL, DuShu88NovelCrawler10::getEachBook));
						}
					}
					BusinessConstants.lock.unlock();
				});
			} catch (Exception e) {
				log.error("【后台爬虫系统爬取数据】error=====================爬取每页li元素小说信息列表==================error", e);
				BusinessConstants.threadPoolBook.wait();
				BusinessConstants.lock.unlock();
			} finally {
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】end=====================爬取每页li元素小说信息列表====================end");
				}
			}
			List<Object> pageTotal = document.sel("//div[@class='booklist']/div[@class='pagelink']/a[@class='last']/text()");
			BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER = Integer.valueOf(pageTotal.get(0).toString());
			BusinessConstants.CURRENT_PAGE_NUMBER ++;
			BusinessConstants.threadPoolPage.execute(()->{
				BusinessConstants.lock.lock();
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】当前页获取数据开始的url data=={}", BusinessConstants.CURRENT_START_URL);
				}
				//总页数是多少需要提前获取一下
				if(BusinessConstants.CURRENT_PAGE_NUMBER > BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER) {
					//当前类型所有页跑完唤醒上一级
					BusinessConstants.CURRENT_PAGE_NUMBER = 1;      //将这个变量重置为1
					BusinessConstants.conditionPoolStart.signal();  //
					BusinessConstants.lock.unlock();                //调用signal()方法后需要手动释放
					return;
				}
				String nextPagePrefix = BusinessConstants.CURRENT_START_URL.substring(0, BusinessConstants.CURRENT_START_URL.indexOf("sort") + 6);
				StringBuffer bf = new StringBuffer().append(nextPagePrefix).append(BusinessConstants.CURRENT_PAGE_NUMBER).append("/");
				BusinessConstants.CURRENT_START_URL = bf.toString();
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】下一页获取数据开始的url data=={}", BusinessConstants.CURRENT_START_URL);
				}
				push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler10::getEachPage));
				BusinessConstants.lock.unlock();
			});
		} catch (Exception e) {
			log.error("【后台爬虫系统爬取数据】分页爬取八八读书（88dush.com）小说网每页data出现Exception", e);
			//当前有异常唤醒上一级
			BusinessConstants.conditionPoolStart.signal();
			BusinessConstants.lock.unlock();
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台爬虫系统爬取数据】end=====================分页爬取八八读书（88dush.com）小说网每页data=====================end");
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
                    push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler10::renderChapterBean));
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
            novelInfo = novelInfo.compasByCategoryCNText(novelInfo,categoryText);
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
            String status = null;//6、小说更新状态数据
            for (Object object : novelstatusList) {
            	status = object.toString().split("：")[1];
			}
            if (NovelInfoEnum.NOVEL_STATUS_UPDATING.getMessage().equals(status)) {
                novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_UPDATING.getCode());//6、设置小说更新状态为连载中
            } else if (NovelInfoEnum.NOVEL_STATUS_FINISHED.getMessage().equals(status)) {
                novelInfo.setNovelStatus(NovelInfoEnum.NOVEL_STATUS_FINISHED.getCode());//6、设置小说更新状态为已完成
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
            NovelInfo novelInfoTmp = novelInfoService.findByTitleAndCategoryTextAndAuthor(title, categoryText, author);
			if (novelInfoTmp == null) {
				NovelInfo novelInfoSaveResult = novelInfoService.save(novelInfo);
				log.debug("【后台爬虫系统爬取数据】保存到数据库小说data=={}", novelInfoSaveResult);
			} else {
				NovelInfo novelInfoUpdate = copy(novelInfoTmp, novelInfo);
				NovelInfo novelInfoUpdateResult = novelInfoService.save(novelInfoUpdate);
				log.debug("【后台爬虫系统爬取数据】更新到数据库小说信息数据data=={}", novelInfoUpdateResult);
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
    
    public static void main(String[] args) {
    	NovelInfo source = NovelInfo.builder().build();
    	source.setId("0057cd19e7ee45c4872c686b5425d697");
    	source.setCoverURL("https://fm.88dush.com/97/97182/97182s.jpg");
    	source.setTitle("九转帝尊");
    	source.setAuthor("忘情至尊");
    	source.setPublisher("八八读书网（88dush.com）");
    	source.setTotalWords(BigDecimal.ZERO);
    	source.setNovelStatus(1);
    	source.setCategoryType(101);
    	source.setCategoryCNText("玄幻魔法");
    	source.setCategoryENText("xuanhuanqihuan");
    	source.setIntroduction("修炼号称十死无生的《九世轮回诀》，楚尘终于迎来了第九世。这一世，他要铸就无敌的传说。他是神道中的帝王，亦是武道中的至尊。九世轮回诀，九纹轮回眼，九转大神通，九天十地，唯我帝尊！");
    	source.setReaders(BigDecimal.ZERO);
    	source.setRecentReaders(BigDecimal.ZERO);
    	source.setClickNumber(BigDecimal.ZERO);
    	source.setNewestChapterId("3ba0587c40b142a686f6779edcddc376");
    	source.setNewestChapterTitle("第1329章 帝尊归来");
    	source.setTotalChapters(1326);
    	source.setRetention(0);
    	source.setCreateTime(DateUtil.dateFormat("yyyy-MM-DD HH:mm:ss", "2019-05-04 12:42:50"));
    	source.setUpdateTime(DateUtil.dateFormat("yyyy-MM-DD HH:mm:ss", "2019-05-04 13:13:15"));
    	
    	NovelInfo target = NovelInfo.builder().build();
    	target.setId(UUIDUtil.gen32UUID());
//    	target.setCoverURL("https://fm.88dush.com/97/97182/97182s.jpg");
    	target.setTitle("九转帝尊");
    	target.setAuthor("忘情至尊");
    	target.setPublisher("八八读书网（88dush.com）");
    	target.setTotalWords(new BigDecimal(852492.0));
    	target.setNovelStatus(1);
    	target.setCategoryType(101);
    	target.setCategoryCNText("玄幻魔法");
    	target.setCategoryENText("xuanhuanqihuan");
//    	target.setIntroduction("修炼号称十死无生的《九世轮回诀》，楚尘终于迎来了第九世。这一世，他要铸就无敌的传说。他是神道中的帝王，亦是武道中的至尊。九世轮回诀，九纹轮回眼，九转大神通，九天十地，唯我帝尊！");
    	target.setReaders(new BigDecimal(85.0));
    	target.setRecentReaders(new BigDecimal(17.0));
    	target.setClickNumber(new BigDecimal(1722.0));
//    	target.setNewestChapterId("3ba0587c40b142a686f6779edcddc376");
    	target.setNewestChapterTitle("第1329章 帝尊归来");
//    	target.setTotalChapters(1326);
    	target.setRetention(0);
    	target.setCreateTime(DateUtil.dateFormat("yyyy-MM-DD HH:mm:ss", "2019-05-04 15:42:50"));
    	target.setUpdateTime(DateUtil.dateFormat("yyyy-MM-DD HH:mm:ss", "2019-05-04 18:13:15"));
    	NovelInfo novelInfo = copy(source, target);
    	log.info("novel info copy={}", novelInfo);
	}

	public static NovelInfo copy(NovelInfo source, NovelInfo target) {
		if (source != null && target != null) {
			if (StringUtils.isNotEmpty(source.getId())) {
				target.setId(source.getId());//设置小说id
			}
			if (StringUtils.isNotEmpty(source.getCoverURL())) {
				target.setCoverURL(source.getCoverURL());//设置小说封面图片路径url
			}
			if (source.getCategoryType()!= null) {
				target.setCategoryType(source.getCategoryType());//设置小说分类类型type
			}
			if (StringUtils.isNotEmpty(source.getCategoryENText())) {
				target.setCategoryENText(source.getCategoryENText());//设置小说分类英文名称
			}
			if (StringUtils.isNotEmpty(source.getCategoryCNText())) {
				target.setCategoryCNText(source.getCategoryCNText());//设置小说分类中文名称
			}
			if (StringUtils.isNotEmpty(source.getAuthor())) {
				target.setAuthor(source.getAuthor());//设置小说作者笔名
			}
			if (StringUtils.isNotEmpty(source.getTitle())) {
				target.setTitle(source.getTitle());//设置小说标题（名称）
			}
			if (StringUtils.isNotEmpty(source.getIntroduction())) {
				target.setIntroduction(source.getIntroduction());//设置小说简介
			}
			if (StringUtils.isNotEmpty(source.getNewestChapterId())) {
				target.setNewestChapterId(source.getNewestChapterId());//设置小说最新章节id
			}
			if (StringUtils.isNotEmpty(source.getNewestChapterTitle())) {
				target.setNewestChapterTitle(source.getNewestChapterTitle());//设置小说最新章节标题
			}
			if (source.getRetention() != null && !Integer.valueOf(0).equals(source.getRetention())) {//留存率，现在只是保存数字，显示的时候加上百分比
				target.setRetention(source.getRetention());//设置小说留存率，现在只是保存数字，显示的时候加上百分比
			}
			if (source.getTotalChapters() != null && !Integer.valueOf(0).equals(source.getTotalChapters())) {//章节总数
				target.setTotalChapters(source.getTotalChapters());//设置小说总章节数
			}
			if (source.getClickNumber() != null && !BigDecimal.ZERO.equals(source.getClickNumber())) {//点击次数
				target.setClickNumber(source.getClickNumber());//设置小说点击次数
			}
			if (source.getReaders() != null && !BigDecimal.ZERO.equals(source.getReaders())) {//阅读（点击）用户数；默认“0-阅读（点击）用户数”
				target.setReaders(source.getReaders());//设置小说阅读（点击）用户数；默认“0-阅读（点击）用户数”
			}
			if (source.getRecentReaders()!=null &&  !BigDecimal.ZERO.equals(source.getRecentReaders())) {//最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
				target.setRecentReaders(source.getRecentReaders());//设置小说最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
			}
			if (source.getTotalWords() != null && !BigDecimal.ZERO.equals(source.getTotalWords())) {//总字数
				target.setTotalWords(source.getTotalWords());//设置小说总字数
			}
			if (source.getCreateTime() != null) {
				target.setCreateTime(source.getCreateTime());//设置小说创建时间
			}
			if (source.getUpdateTime() != null) {
				target.setUpdateTime(source.getUpdateTime());//设置小说更新时间
			}
		}
		return target;
	}
}