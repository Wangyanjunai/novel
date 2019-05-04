package com.potato369.novel.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.constants.BusinessConstants;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.CategoryEnum;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
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
			BusinessConstants.threadPoolBook.execute(()->{
				BusinessConstants.lock.lock();
				NovelInfo novelInfo = NovelInfo.builder().build();
				novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
				Object categoryCNTextObj = document.selOne("//body/div[@class='booklist']/h1/text()");
				String categoryCNTextStr2 = null;
				if (categoryCNTextObj != null) {
					categoryCNTextStr2 = categoryCNTextObj.toString();
				}
				if (log.isDebugEnabled()) {
					log.debug("【后台爬虫系统爬取数据】每页小说分类中文名称信息转换为字符串data=={}", categoryCNTextStr2);
				}
				novelInfo.setCategoryCNText(categoryCNTextStr2);//9、设置小说分类中文名称
				if ("玄幻魔法".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.XUANHUANQIHUAN.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.XUANHUANQIHUAN.getMessage());//3、设置小说分类英文名称
				}
				if ("武侠修真".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.XIANXIAWUXIA.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.XIANXIAWUXIA.getMessage());//3、设置小说分类英文名称
				}
				if ("都市言情".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.DUSHIQINGGAN.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.DUSHIQINGGAN.getMessage());//3、设置小说分类英文名称
				}
				if ("历史穿越".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.CHUANGYUECHONGSHENG.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.CHUANGYUECHONGSHENG.getMessage());//3、设置小说分类英文名称
				}
				if ("恐怖悬疑".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.XUANYILINGYI.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.XUANYILINGYI.getMessage());//3、设置小说分类英文名称
				}
				if ("游戏竞技".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.YOUXIJINGJI.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.YOUXIJINGJI.getMessage());//3、设置小说分类英文名称
				}
				if ("军事科幻".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.LISHIJUNSHI.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.LISHIJUNSHI.getMessage());//3、设置小说分类英文名称
				}
				if ("女生频道".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.DUSHISHENGHUO.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.DUSHISHENGHUO.getMessage());//3、设置小说分类英文名称
				}
				if ("综合类型".equals(categoryCNTextStr2)) {
					novelInfo.setCategoryType(CategoryEnum.QITALEIBIE.getCode());//2、设置小说分类类型type
					novelInfo.setCategoryENText(CategoryEnum.QITALEIBIE.getMessage());//3、设置小说分类英文名称
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
						log.debug("【后台爬虫系统爬取数据】每页小说作者信息数据转换为字符串data=={}", novelAuthorStr);
						novelInfo.setAuthor(novelAuthorStr);//6、设置小说作者
						Object novelIntroductionObj = dlJxDocument.selNOne("//dl/dd/p/text()");//获取小说简介
						String novelIntroductionStr = null;
						if (novelIntroductionObj != null) {
							novelIntroductionStr = novelIntroductionObj.toString();
						}
						log.debug("【后台爬虫系统爬取数据】每页小说简介信息数据转换为字符串data=={}", novelIntroductionStr);
						novelInfo.setIntroduction(novelIntroductionStr);//7、设置小说简介
						novelInfo.setNovelStatus(1);//8、设置小说更新状态
						novelInfo.setClickNumber(BigDecimal.ZERO);//10
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
						novelInfo.setTotalChapters(0);
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
					}
				}
				NovelInfo novelInfoTmp = novelInfoService.findByTitleAndCategoryTextAndAuthor(novelInfo.getTitle(), novelInfo.getCategoryCNText(), novelInfo.getAuthor());
				if (novelInfoTmp == null) {
					novelInfoService.save(novelInfo);
					if (log.isDebugEnabled()) {
						log.debug("【后台爬虫系统爬取数据】每页小说信息数据data=={}", novelInfo);
					}
				}
				NovelInfo novelInfo1 = NovelInfo.builder().build();
				novelInfo1.setId(UUIDUtil.gen32UUID());//1、设置小说id
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
						Object novelGetDataObj = dlJxDocument.selNOne("//li/span[@class='sm']/a/@href");//获取小说信息数据路径
						String novelGetDataUrlStr = null;
						if (novelGetDataObj != null) {
							novelGetDataUrlStr = novelGetDataObj.toString();
							if (!novelGetDataUrlStr.startsWith(DOMAIN_URL) && !novelGetDataUrlStr.contains(DOMAIN_URL)) {
								novelGetDataUrlStr = new StringBuffer().append(DOMAIN_URL).append(novelGetDataUrlStr).toString();
							}
						}
						log.debug("【后台爬虫系统爬取数据】每页小说内容信息数据路径url转换为字符串data=={}", novelGetDataUrlStr);
						Object novelTitleObj = dlJxDocument.selNOne("//li/span[@class='sm']/a/b/text()");//获取小说名称
						String novelTitleStr = null;
						if (novelTitleObj != null) {
							novelTitleStr = novelTitleObj.toString();
						}
						log.debug("【后台爬虫系统爬取数据】每页小说名称信息数据转换为字符串data=={}", novelTitleStr);
						novelInfo1.setTitle(novelTitleStr);//2、设置小说名称
						Object novelNewestChapterTitleObj = dlJxDocument.selNOne("//li/span[@class='zj']/a/text()");//获取小说最新章节标题
						String novelNewestChapterTitleStr = null;
						if (novelNewestChapterTitleObj != null) {
							novelNewestChapterTitleStr = novelNewestChapterTitleObj.toString();
						}
						log.debug("【后台爬虫系统爬取数据】每页小说最新章节标题信息数据转换为字符串data=={}", novelNewestChapterTitleStr);
						novelInfo1.setNewestChapterTitle(novelNewestChapterTitleStr);//3、设置小说最新章节
						Object novelAuthorObj = dlJxDocument.selNOne("//li/span[@class='zz']/text()");//获取小说作者
						String novelAuthorStr = null;
						if (novelAuthorObj != null) {
							novelAuthorStr = novelAuthorObj.toString();
						}
						log.debug("【后台爬虫系统爬取数据】每页小说作者信息数据转换为字符串data=={}", novelAuthorStr);
						novelInfo1.setAuthor(novelAuthorStr);//4、设置小说最新章节
						Object novelTotalWordsObj = dlJxDocument.selNOne("//li/span[@class='zs']/num()");//获取小说字数
						BigDecimal novelTotalWordsDecimal = null;
						if (novelTotalWordsObj != null && ((JXNode) novelAuthorObj).isNumber()) {
							novelTotalWordsDecimal = BigDecimal.valueOf(Long.valueOf(novelAuthorStr));
						}
						log.debug("【后台爬虫系统爬取数据】每页小说作者信息数据转换为字符串data=={}", novelTotalWordsDecimal);
						novelInfo1.setTotalWords(novelTotalWordsDecimal);//5、设置小说总字数
						Object novelStatusObj = dlJxDocument.selNOne("//li/span[@class='zt']/text()");//获取小说状态
						String novelStatusStr = null;
						if (novelStatusObj != null) {
							novelStatusStr = novelStatusObj.toString();
						}
						log.debug("【后台爬虫系统爬取数据】每页小说状态信息数据转换为字符串data=={}", novelStatusStr);
						if ("连载中".equals(novelStatusStr)) {
							novelInfo1.setNovelStatus(1);
						} else if ("已完结".equals(novelStatusStr)) {
							novelInfo1.setNovelStatus(0);//6、设置小说总字数
						} else {
							novelInfo1.setNovelStatus(1);//6、设置小说总字数
						}
						Object novelRedersObj = dlJxDocument.selNOne("//li/span[@class='fs']/num()");//获取小说阅读数
						BigDecimal novelRedersDecimal = null;
						if (novelAuthorObj != null && ((JXNode) novelAuthorObj).isNumber()) {
							novelRedersDecimal = BigDecimal.valueOf(Long.valueOf(novelRedersObj.toString()));
						}
						log.debug("【后台爬虫系统爬取数据】每页小说阅读人数信息数据转换为字符串data=={}", novelRedersDecimal);
						novelInfo1.setReaders(novelRedersDecimal);//7、设置小说阅读人数
						novelInfo1.setClickNumber(BigDecimal.ZERO);//10
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】开始爬取11、小说点击数data={}", novelInfo.getClickNumber());
						}
						novelInfo1.setPublisher("八八读书网（88dush.com）");
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】开始爬取12、小说出版社或爬取网站名称data={}", novelInfo.getPublisher());
						}
						novelInfo1.setTotalChapters(0);
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】开始爬取14、小说总章节数data={}", novelInfo.getTotalChapters());
						}
						novelInfo1.setRecentReaders(BigDecimal.ZERO);
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】开始爬取16、小说跟随阅读人数data={}", novelInfo.getRecentReaders());
						}
						novelInfo1.setRetention(0);
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】开始爬取17、小说留存率data={}", novelInfo.getRetention());
						}
						Object categoryCNTextObj1 = document.selOne("//body/div[@class='booklist']/h1/text()");
						String categoryCNTextStr1 = null;
						if (categoryCNTextObj1 != null) {
							categoryCNTextStr1 = categoryCNTextObj1.toString();
						}
						if (log.isDebugEnabled()) {
							log.debug("【后台爬虫系统爬取数据】每页小说分类中文名称信息转换为字符串data=={}", categoryCNTextStr1);
						}
						novelInfo1.setCategoryCNText(categoryCNTextStr1);//9、设置小说分类中文名称
						if ("玄幻魔法".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.XUANHUANQIHUAN.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.XUANHUANQIHUAN.getMessage());//3、设置小说分类英文名称
						}
						if ("武侠修真".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.XIANXIAWUXIA.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.XIANXIAWUXIA.getMessage());//3、设置小说分类英文名称
						}
						if ("都市言情".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.DUSHIQINGGAN.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.DUSHIQINGGAN.getMessage());//3、设置小说分类英文名称
						}
						if ("历史穿越".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.CHUANGYUECHONGSHENG.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.CHUANGYUECHONGSHENG.getMessage());//3、设置小说分类英文名称
						}
						if ("恐怖悬疑".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.XUANYILINGYI.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.XUANYILINGYI.getMessage());//3、设置小说分类英文名称
						}
						if ("游戏竞技".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.YOUXIJINGJI.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.YOUXIJINGJI.getMessage());//3、设置小说分类英文名称
						}
						if ("军事科幻".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.LISHIJUNSHI.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.LISHIJUNSHI.getMessage());//3、设置小说分类英文名称
						}
						if ("女生频道".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.DUSHISHENGHUO.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.DUSHISHENGHUO.getMessage());//3、设置小说分类英文名称
						}
						if ("综合类型".equals(categoryCNTextStr1)) {
							novelInfo1.setCategoryType(CategoryEnum.QITALEIBIE.getCode());//2、设置小说分类类型type
							novelInfo1.setCategoryENText(CategoryEnum.QITALEIBIE.getMessage());//3、设置小说分类英文名称
						}
						NovelInfo novelInfoTmp1 = novelInfoService.findByTitleAndCategoryTextAndAuthor(novelInfo1.getTitle(), novelInfo1.getCategoryCNText(), novelInfo1.getAuthor());
						if (novelInfoTmp1 == null) {
							novelInfoService.save(novelInfo1);
							if (log.isDebugEnabled()) {
								log.debug("【后台爬虫系统爬取数据】每页小说信息数据data=={}", novelInfo1);
							}
						}
					}
				}
				BusinessConstants.lock.unlock();
			});
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
}