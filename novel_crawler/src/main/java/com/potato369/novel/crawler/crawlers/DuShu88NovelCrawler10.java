package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.constants.BusinessConstants;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.NovelInfoStatusEnum;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.BeanUtil;
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
                log.debug("【后台爬虫系统爬取数据】start===================爬取八八读书（88dush.com）小说网每个分类每页小说数据===================start");
            }
			JXDocument document = response.document();
			List<Object> pageTotal = document.sel("//div[@class='booklist']/div[@class='pagelink']/a[@class='last']/text()");
			BusinessConstants.CURRENT_TOTAL_PAGE_NUMBER = Integer.valueOf(pageTotal.get(0).toString());
			BusinessConstants.CURRENT_PAGE_NUMBER ++;
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
						log.debug("【后台爬虫系统爬取数据】每页小说内容信息数据路径url转换为字符串data=={}", BusinessConstants.CURRENT_GET_DATA_URL);
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
						novelInfo.setNovelStatus(NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getCode());//8、设置小说更新状态为连载中
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
							NovelInfo novelInfoUpdate = BeanUtil.copy(novelInfoTmp, novelInfo);
							NovelInfo novelInfoUpdateResult = novelInfoService.save(novelInfoUpdate);
							log.debug("【后台爬虫系统爬取数据】更新到数据库小说信息数据data=={}", novelInfoUpdateResult);
						}
						Object novelGetDataObj = dlJxDocument.selNOne("//dl/dd/h3/a/@href");//获取小说信息数据路径
						String novelGetDataUrlStr = null;
						if (novelGetDataObj != null) {
							novelGetDataUrlStr = novelGetDataObj.toString();
							if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
								BusinessConstants.CURRENT_GET_BOOK_DATA_URL = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
							} else {
								BusinessConstants.CURRENT_GET_BOOK_DATA_URL = novelGetDataUrlStr;
							}
						}
					}
					push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler10::getEachBook));
				}
				BusinessConstants.lock.unlock();
			});
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
						NovelInfo novelInfo = NovelInfo.builder().build();
						novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
						log.debug("【后台爬虫系统爬取数据】每页小说内容信息数据路径url转换为字符串data=={}", BusinessConstants.CURRENT_GET_DATA_URL);
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
						if (NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getMessage().equals(novelStatusStr)) {
							novelInfo.setNovelStatus(NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getCode());//6、设置小说更新状态为连载中
						} else if (NovelInfoStatusEnum.NOVEL_STATUS_FINISHED.getMessage().equals(novelStatusStr)) {
							novelInfo.setNovelStatus(NovelInfoStatusEnum.NOVEL_STATUS_FINISHED.getCode());//6、设置小说更新状态为已完成
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
							NovelInfo novelInfoUpdate = BeanUtil.copy(novelInfoTmp, novelInfo);
							NovelInfo novelInfoUpdateResult = novelInfoService.save(novelInfoUpdate);
							log.debug("【后台爬虫系统爬取数据】更新到数据库小说信息数据data=={}", novelInfoUpdateResult);
						}
						Object novelGetDataObj = dlJxDocument.selNOne("//li/span[@class='sm']/a/@href");//获取小说信息数据路径
						String novelGetDataUrlStr = null;
						if (novelGetDataObj != null) {
							novelGetDataUrlStr = novelGetDataObj.toString();
							if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
								BusinessConstants.CURRENT_GET_BOOK_DATA_URL = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
							} else {
								BusinessConstants.CURRENT_GET_BOOK_DATA_URL = novelGetDataUrlStr;
							}
						}
					}
					push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler10::getEachBook));
				}
				BusinessConstants.lock.unlock();
			});
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
			log.error("【后台爬虫系统爬取数据】爬取八八读书（88dush.com）小说网每个分类每页小说数据出现Exception", e);
			BusinessConstants.conditionPoolStart.signal();//当前有异常唤醒上一级
			BusinessConstants.lock.unlock();
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台爬虫系统爬取数据】end=====================爬取八八读书（88dush.com）小说网每个分类每页小说数据=====================end");
			}
		}
	}
    
    public void getEachBook(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取每页小说的目录和内容数据");
			}
        	BusinessConstants.lock.lock();
            JXDocument document = response.document();
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
            if (NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getMessage().equals(status)) {
                novelInfo.setNovelStatus(NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getCode());//6、设置小说更新状态为连载中
            } else if (NovelInfoStatusEnum.NOVEL_STATUS_FINISHED.getMessage().equals(status)) {
                novelInfo.setNovelStatus(NovelInfoStatusEnum.NOVEL_STATUS_FINISHED.getCode());//6、设置小说更新状态为已完成
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
            novelInfo.setId(UUIDUtil.gen32UUID());
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
			novelInfoService.save(novelInfo);
			List<Object> urlList = document.sel("//div[@class='mulu']/ul/li/a/@href");
			BusinessConstants.CURRENT_TOTAL_CHAPTERS = urlList.size();
			BusinessConstants.threadPoolBook.execute(()-> {
				BusinessConstants.lock.lock();
				for (Object url:urlList) {
					String urlStr = url.toString();
					if (!urlStr.startsWith(BusinessConstants.CURRENT_GET_BOOK_DATA_URL)) {
						BusinessConstants.CURRENT_GET_BOOK_DATA_URL = new StringBuffer(BusinessConstants.CURRENT_GET_BOOK_DATA_URL).append(urlStr).toString();
					} else {
						BusinessConstants.CURRENT_GET_BOOK_DATA_URL = urlStr;
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】爬取每页小说的目录和内容数据URL={}", BusinessConstants.CURRENT_GET_BOOK_DATA_URL);
					}
					push(Request.build(BusinessConstants.CURRENT_GET_BOOK_DATA_URL, DuShu88NovelCrawler10::getEachChapter));
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
				BusinessConstants.lock.unlock();
			});
			BusinessConstants.lock.unlock();
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
    
    public void getEachChapter(Response response) {
        try {
        	if (log.isDebugEnabled()) {
        		log.debug("【后台爬虫系统爬取数据】开始爬取小说每个章节的数据");
			}
			Chapter chapter = null;
			BusinessConstants.lock.lock();
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
								NovelInfo novelInfo2 = novelInfoService.findById(bookId);
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