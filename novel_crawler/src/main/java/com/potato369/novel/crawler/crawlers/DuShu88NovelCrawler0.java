package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.constants.BusinessConstants;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.CategoryEnum;
import com.potato369.novel.basic.enums.NovelInfoEnum;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
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
@Crawler(name = "88dush0")
@Slf4j
public class DuShu88NovelCrawler0 extends BaseSeimiCrawler{

	private static final long serialVersionUID = 3993378973651481714L;

	private static final String DOMAIN_URL = "https://www.88dush.com";

	@Autowired
	private NovelInfoService novelInfoService;

	@Override
	public String[] startUrls() {return new String[]{DOMAIN_URL};}

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
					push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler0::getEachPage));
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
			Object categoryCNTextObj = document.selOne("//body/div[@class='booklist']/h1/text()");
			String categoryCNTextStr = pan(categoryCNTextObj);
			try {
				BusinessConstants.threadPoolBook.execute(()->{
					BusinessConstants.lock.lock();
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】start=====================爬取每页推荐dl元素小说信息列表====================start");
					}
					List<Object> dlObjList = document.sel("//body/div[@class='fengtui']/dl");
					if (dlObjList == null || dlObjList.isEmpty() || dlObjList.size() < 1) {
						return;
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】每页dl元素对象列表大小data=={}", dlObjList.size());
						for (Object dl:dlObjList) {
							saveDL(dl, categoryCNTextStr);
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
					List<Object> liObjList = document.sel("//body/div[@class='booklist']/ul/li[@class!='t']");
					if (liObjList == null || liObjList.isEmpty() || liObjList.size() < 1) {
						return;
					}
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】每页li元素对象列表大小data=={}", liObjList.size());
						for (Object li:liObjList) {
							saveLI(li, categoryCNTextStr);
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
				push(Request.build(BusinessConstants.CURRENT_START_URL, DuShu88NovelCrawler0::getEachPage));
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

	private void saveDL(Object dl, String categoryCNText) {
		if (dl != null) {
//			log.debug("【后台爬虫系统爬取数据】每页dl元素转换为字符串data=={}", dl.toString());
			JXDocument doc = JXDocument.create(dl.toString());
			Object coverImageObj = doc.selOne("//dl/dt/a/img/@src");//1、获取封面图片路径
			Object novelGetDataObj = doc.selNOne("//dl/dd/h3/a/@href");//2、获取小说信息数据路径
			Object novelTitleObj = doc.selNOne("//dl/dd/h3/a/text()");//3、获取小说名称
			Object novelAuthorObj = doc.selNOne("//dl/dd/span/text()");//4、获取小说作者
			Object novelIntroductionObj = doc.selNOne("//dl/dd/p/text()");//5、获取小说简介
			NovelInfo novelInfo = pan(categoryCNText, coverImageObj, novelGetDataObj, novelTitleObj, null, novelAuthorObj, null, null, null, novelIntroductionObj);
			save(novelInfo);
		}
	}

	private void saveLI(Object li, String categoryCNText){
		if (li != null) {
//			log.debug("【后台爬虫系统爬取数据】每页li元素转换为字符串data=={}", li.toString());
			JXDocument doc = JXDocument.create(li.toString());
			Object novelTitleObj = doc.selNOne("//li/span[@class='sm']/a/b/text()");//1、获取小说名称
			Object novelNewestChapterTitleObj = doc.selNOne("//li/span[@class='zj']/a/text()");//2、获取小说最新章节标题
			Object novelAuthorObj = doc.selNOne("//li/span[@class='zz']/text()");//3、获取小说作者
			Object novelTotalWordsObj = doc.selNOne("//li/span[@class='zs']/num()");//4、获取小说字数
			Object novelStatusObj = doc.selNOne("//li/span[@class='zt']/text()");//5、获取小说更新状态
			Object novelReadersObj = doc.selNOne("//li/span[@class='fs']/num()");//6、获取小说阅读数
			Object novelGetDataObj = doc.selNOne("//li/span[@class='sm']/a/@href");//7、获取小说信息数据路径
			NovelInfo novelInfo = pan(categoryCNText, null, novelGetDataObj, novelTitleObj, novelNewestChapterTitleObj, novelAuthorObj, novelTotalWordsObj, novelStatusObj, novelReadersObj, null);
			save(novelInfo);
		}
	}

	private void save(NovelInfo novelInfo) {
		if (novelInfo != null) {
			NovelInfo novelInfoTmp = novelInfoService.findByTitleAndCategoryTextAndAuthor(novelInfo.getTitle(), novelInfo.getCategoryCNText(), novelInfo.getAuthor());
			if (novelInfoTmp == null) {
				NovelInfo saveResult = novelInfoService.save(novelInfo);
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】保存到数据库小说data=={}", saveResult);
				}
			} else {
				NovelInfo novelInfoUpdate = copy(novelInfoTmp, novelInfo);
				NovelInfo updateResult = novelInfoService.save(novelInfoUpdate);
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】更新到数据库小说data=={}", updateResult);
				}
			}
		}
	}

	private String pan(Object categoryCNTextObj) {
		String categoryCNText = null;
		if (categoryCNTextObj != null) {
			categoryCNText = categoryCNTextObj.toString();
		}
		return categoryCNText;
	}

	private NovelInfo pan(String categoryCNText,
						  Object coverImageObj,
						  Object getDataObj,
						  Object titleObj,
						  Object newestChapterTitleObj,
						  Object authorObj,
						  Object totalWordsObj,
						  Object statusObj,
						  Object readersObj,
						  Object novelIntroductionObj) {
		NovelInfo novelInfo = NovelInfo.builder().build();
		String id = UUIDUtil.gen32UUID();
		novelInfo.setId(id);//1、设置小说id
		log.debug("1、小说id=={}", id);
		String novelTitleStr = null;
		if (titleObj != null) {
			novelTitleStr = titleObj.toString();
		}
		log.debug("2、小说名称=={}", novelTitleStr);
		novelInfo.setTitle(novelTitleStr);//2、设置小说名称

		String novelNewestChapterTitleStr = null;
		if (newestChapterTitleObj != null) {
			novelNewestChapterTitleStr = newestChapterTitleObj.toString();
		}
		log.debug("3、小说最新章节标题=={}", novelNewestChapterTitleStr);
		novelInfo.setNewestChapterTitle(novelNewestChapterTitleStr);//3、设置小说最新章节标题
		String novelAuthorStr = null;
		if (authorObj != null) {
			novelAuthorStr = authorObj.toString();
		}
		log.debug("4、小说作者=={}", novelAuthorStr);
		novelInfo.setAuthor(novelAuthorStr);//4、设置小说作者
		BigDecimal novelTotalWordsDecimal = BigDecimal.ZERO;
		if (totalWordsObj != null) {
			novelTotalWordsDecimal = new BigDecimal(totalWordsObj.toString());
		}
		log.debug("5、小说总字数=={}", novelTotalWordsDecimal);
		novelInfo.setTotalWords(novelTotalWordsDecimal);//5、设置小说总字数
		String novelStatusStr = NovelInfoEnum.NOVEL_STATUS_UPDATING.getMessage();//默认：连载中
		Integer status = NovelInfoEnum.NOVEL_STATUS_UPDATING.getCode();//默认：1，连载中
		if (statusObj != null) {
			novelStatusStr = statusObj.toString();
		}
		if (NovelInfoEnum.NOVEL_STATUS_FINISHED.getMessage().equals(novelStatusStr)) {
			novelStatusStr = NovelInfoEnum.NOVEL_STATUS_FINISHED.getMessage();
			status = NovelInfoEnum.NOVEL_STATUS_FINISHED.getCode();//已完成
		}
		log.debug("6、小说状态=={}", novelStatusStr);
		novelInfo.setNovelStatus(status);//6、设置小说更新状态
		BigDecimal novelReadersDecimal = BigDecimal.ZERO;
		if (readersObj != null) {
			novelReadersDecimal = new BigDecimal(readersObj.toString());
		}
		BigDecimal readers = novelReadersDecimal.multiply(new BigDecimal(100L));
		log.debug("7、小说阅读人数=={}", readers);
		novelInfo.setReaders(readers);//7、设置小说阅读人数
		BigDecimal clickNumber = novelReadersDecimal.multiply(new BigDecimal(1000L));
		log.debug("8、小说点击（阅读）次数=={}", clickNumber);
		novelInfo.setClickNumber(clickNumber);//8、设置小说点击（阅读）次数
		String publisher = "八八读书网（88dush.com）";
		log.debug("9、小说出版社或者爬取的网站名称=={}", publisher);
		novelInfo.setPublisher(publisher);//9、设置小说出版社或者爬取的网站名称
		Integer totalChapters = 0;
		log.debug("10、小说总章节数=={}", totalChapters);
		novelInfo.setTotalChapters(totalChapters);//10、设置小说总章节数
		log.debug("11、小说跟随（关注）人数=={}", novelReadersDecimal);
		novelInfo.setRecentReaders(novelReadersDecimal);//11、设置小说跟随阅读人数
		Integer retention = 0;
		log.debug("12、小说留存率=={}", retention);
		novelInfo.setRetention(retention);//12、设置小说留存率
		String cn_text = categoryCNText;
		log.debug("13、小说分类中文名称=={}", cn_text);
		novelInfo.setCategoryCNText(cn_text);//13、设置小说分类中文名称
		String coverImage = null;
		if (coverImageObj != null) {
			coverImage = coverImageObj.toString();
		}
		log.debug("14、小说封面图片路径=={}", coverImage);
		novelInfo.setCoverURL(coverImage);//14、设置小说封面图片路径
		String introduction = null;
		if (novelIntroductionObj != null) {
			introduction = novelIntroductionObj.toString();
		}
		log.debug("15、小说简介=={}", introduction);
		novelInfo.setIntroduction(introduction);//15、设置小说简介
		novelInfo = novelInfo.compasByCategoryCNText(novelInfo, categoryCNText);
		Integer categoryType = novelInfo.getCategoryType();
		log.debug("16、小说分类类型type=={}", categoryType);
		String en_text = novelInfo.getCategoryENText();
		log.debug("17、小说分类英文名称=={}", en_text);
		String novelGetDataUrlStr = null;
		if (getDataObj != null) {
			novelGetDataUrlStr = getDataObj.toString();
			if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
				novelGetDataUrlStr = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
			}
		}
		log.debug("18、小说章节数据路径=={}", novelGetDataUrlStr);
		novelInfo.setDataURL(novelGetDataUrlStr);//18、设置小说获取数据路径
		return novelInfo;
	}

	private NovelInfo copy(NovelInfo source, NovelInfo target) {
		if (source != null && target != null) {
			if (StringUtils.isNotEmpty(source.getId())) {
				target.setId(source.getId());//设置小说id
			}
			if (StringUtils.isNotEmpty(source.getCoverURL())) {
				target.setCoverURL(source.getCoverURL());//设置小说封面图片路径url
			}
			if (StringUtils.isNotEmpty(source.getDataURL())) {
				target.setDataURL(source.getDataURL());//获取数据url
			}
			if (source.getCategoryType() != null) {
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
			if (source.getRecentReaders() !=null && !BigDecimal.ZERO.equals(source.getRecentReaders())) {//最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
				target.setRecentReaders(source.getRecentReaders());//设置小说最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
			}
			if (source.getTotalWords()!=null && !BigDecimal.ZERO.equals(source.getTotalWords())) {//总字数
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