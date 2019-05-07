package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.HomeDataVO;
import com.potato369.novel.app.web.vo.LoadingDataVO;
import com.potato369.novel.app.web.vo.NovelChapterInfoVO;
import com.potato369.novel.app.web.vo.NovelChapterTitleAndContentVO;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelAdvertisement;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.AdvertisementEnum;
import com.potato369.novel.basic.enums.TypeEnum;
import com.potato369.novel.basic.service.AdvertisementService;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName NovelController
 * @Desc NovelController
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/29 16:00
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/lv2")
public class NovelController {

    @Autowired
    private NovelInfoService novelInfoService;
    
    @Autowired
    private NovelChapterService novelChapterService;
    
    @Autowired
    private AdvertisementService advertisementService;
    
    //推荐页面首页接口
    /**
     * 
     * <pre>
     * recommend方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param tag1
     * @param tag2
     * @param parentCategoryId
     * @param size
     * @return
     * @since JDK 1.6
     * </pre>
     */
    @GetMapping(value = "/recommend/{tag1}/{tag2}/{parentCategoryId}/{size}")
    public ResultVO<HomeDataVO> recommend(
    		@PathVariable(name = "tag1", required=true) Integer tag1,
    		@PathVariable(name = "tag2", required=true) Integer tag2,
    		@PathVariable(name = "parentCategoryId", required=false) String parentCategoryId,
    		@PathVariable(name = "size", required=true) Integer size) {
    	ResultVO<HomeDataVO> recommendResultVO = new ResultVO<HomeDataVO>();
    	try {
    		if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取推荐页面数据=====================start");
            }
    		HomeDataVO homeDataVO = HomeDataVO.builder().build();
    		List<NovelAdvertisement> advertisements = advertisementService.findByTagAndParentTypeIdLimitSize(tag1, tag2, size, parentCategoryId);
    		List<LoadingDataVO> bannerAdDataVOs = new ArrayList<LoadingDataVO>();
    		for (NovelAdvertisement novelAdvertisement : advertisements) {
    			LoadingDataVO loadingDataVO = LoadingDataVO.builder().build();
    			String adIdString = novelAdvertisement.getAdId();
    			String imageUrlString = novelAdvertisement.getImageUrl();
    			String linkUrlString = novelAdvertisement.getLinkUrl();
    			String novelIdString = novelAdvertisement.getNovelId();
    			loadingDataVO.setId(adIdString);
    			loadingDataVO.setImageUrl(imageUrlString);
    			loadingDataVO.setLinkUrl(new StringBuffer().append(linkUrlString).append("/detail/").append(novelIdString).toString());
    			loadingDataVO.setNovelId(novelIdString);
    			loadingDataVO.setNovelParentCategoryId(parentCategoryId);
    			loadingDataVO.setTag1(tag1);
    			loadingDataVO.setTag2(tag2);
    			bannerAdDataVOs.add(loadingDataVO);
			}
    		homeDataVO.setBannerAdDataVOs(bannerAdDataVOs);
    		recommendResultVO.setData(homeDataVO);
    		recommendResultVO.setCode(0);
    		recommendResultVO.setMsg("返回数据成功");
    		return recommendResultVO;
		} catch (Exception e) {
			log.error("", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
	              log.debug("【后台小说接口】end===================获取推荐页面数据======================end");
	        }
		}
	}
    @GetMapping(value = "/home/{categoryType}")
    public ResultVO<HomeDataVO> home(
    		@PathVariable(name="categoryType", required=true) String categoryType, 
    		@RequestParam(name = "type", required=true, defaultValue="all") String type) {
    	ResultVO<HomeDataVO> recommendResultVO = new ResultVO<HomeDataVO>();
    	try {
    		if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取推荐页面数据=====================start");
            }
    		if (log.isDebugEnabled()) {
    			log.debug("【后台小说接口】获取推荐页面categoryType={}, 数据类型type={}", categoryType, type);
			}
    		HomeDataVO homeDataVO = HomeDataVO.builder().build();
    		if (StringUtils.isNotEmpty(categoryType)) {
				if (TypeEnum.MALE.getCn().equals(categoryType) || TypeEnum.MALE.getEn().equals(categoryType)) {
					//如果是男生首页
					String id = TypeEnum.MALE.getId();
					if (StringUtils.isNotEmpty(type)) {
						if (TypeEnum.DEFAULT.getCn().equals(type) || TypeEnum.DEFAULT.getEn().equals(type)) {//显示所有默认加载首页推荐数据
							List<LoadingDataVO> bannerAdDataVOs = getBannerAdDataVOs(id);//应用内广告轮播图数据
							List<NovelInfoVO> hotYouLikeData = getData(id, TypeEnum.HOT.getEn(), null);//近期热门数据
							List<NovelInfoVO> editorRecommendData = getData(id, TypeEnum.EDIT.getEn(), null);//主编推荐数据
							List<NovelInfoVO> newestData = getData(id, TypeEnum.NEWEST.getEn(), null);//最近更新数据
							NovelInfoVO featuredData = new NovelInfoVO();//爽文推荐数据
							homeDataVO.setBannerAdDataVOs(bannerAdDataVOs);
							homeDataVO.setHotYouLikeData(hotYouLikeData);
							homeDataVO.setEditorRecommendData(editorRecommendData);
							homeDataVO.setNewestData(newestData);
							homeDataVO.setFeaturedData(featuredData);
						}
						if (TypeEnum.HOT.getCn().equals(type) || TypeEnum.HOT.getEn().equals(type)) {//显示加载近期热门数据
							List<NovelInfoVO> hotYouLikeData = new ArrayList<>();//近期热门数据
							homeDataVO.setHotYouLikeData(hotYouLikeData);
						}
						if (TypeEnum.EDIT.getCn().equals(type) || TypeEnum.EDIT.getEn().equals(type)) {//显示加载主编推荐数据
							List<NovelInfoVO> editorRecommendData = new ArrayList<>();//主编推荐数据
							homeDataVO.setEditorRecommendData(editorRecommendData);
						}
						if (TypeEnum.NEWEST.getCn().equals(type) || TypeEnum.NEWEST.getEn().equals(type)) {//显示加载最近更新数据
							List<NovelInfoVO> newestData = new ArrayList<>();//最近更新数据
							homeDataVO.setNewestData(newestData);
						}
						if (TypeEnum.HANDSOME.getCn().equals(type) || TypeEnum.HANDSOME.getEn().equals(type)) {//显示加载爽文推荐数据
							NovelInfoVO featuredData = new NovelInfoVO();//爽文推荐数据
							homeDataVO.setFeaturedData(featuredData);
						}
					}
				}
				if (TypeEnum.FEMALE.getCn().equals(categoryType) || TypeEnum.FEMALE.getEn().equals(categoryType)) {
					//如果是女生首页
					String id = TypeEnum.FEMALE.getId();
					if (StringUtils.isNotEmpty(type)) {
						if (TypeEnum.DEFAULT.getCn().equals(type) || TypeEnum.DEFAULT.getEn().equals(type)) {//显示所有默认加载首页推荐数据
							List<LoadingDataVO> bannerAdDataVOs = new ArrayList<>();//应用内广告轮播图数据
							List<NovelInfoVO> hotYouLikeData = new ArrayList<>();//近期热门数据
							List<NovelInfoVO> editorRecommendData = new ArrayList<>();//主编推荐数据
							List<NovelInfoVO> newestData = new ArrayList<>();//最近更新数据
							NovelInfoVO featuredData = new NovelInfoVO();//爽文推荐数据
							homeDataVO.setBannerAdDataVOs(bannerAdDataVOs);
							homeDataVO.setHotYouLikeData(hotYouLikeData);
							homeDataVO.setEditorRecommendData(editorRecommendData);
							homeDataVO.setNewestData(newestData);
							homeDataVO.setFeaturedData(featuredData);
						}
						if (TypeEnum.HOT.getCn().equals(type) || TypeEnum.HOT.getEn().equals(type)) {//显示加载近期热门推荐数据
							List<NovelInfoVO> hotYouLikeData = new ArrayList<>();//近期热门数据
							homeDataVO.setHotYouLikeData(hotYouLikeData);
						}
						if (TypeEnum.EDIT.getCn().equals(type) || TypeEnum.EDIT.getEn().equals(type)) {//显示加载近期热门推荐数据
							List<NovelInfoVO> editorRecommendData = new ArrayList<>();//主编推荐数据
							homeDataVO.setEditorRecommendData(editorRecommendData);
						}
						if (TypeEnum.NEWEST.getCn().equals(type) || TypeEnum.NEWEST.getEn().equals(type)) {//显示加载近期热门推荐数据
							List<NovelInfoVO> newestData = new ArrayList<>();//最近更新数据
							homeDataVO.setNewestData(newestData);
						}
						if (TypeEnum.HANDSOME.getCn().equals(type) || TypeEnum.HANDSOME.getEn().equals(type)) {//显示加载近期热门推荐数据
							NovelInfoVO featuredData = new NovelInfoVO();//爽文推荐数据
							homeDataVO.setFeaturedData(featuredData);
						}
					}
				}
				if (TypeEnum.PICTURE.getCn().equals(categoryType) || TypeEnum.PICTURE.getEn().equals(categoryType)) {
					//TODO 一期不做漫画首页
					String id = TypeEnum.PICTURE.getId();
					if (StringUtils.isNotEmpty(type)) {
						if (TypeEnum.DEFAULT.getCn().equals(type) || TypeEnum.DEFAULT.getEn().equals(type)) {//显示所有默认加载首页推荐数据
							
						}
						if (TypeEnum.HOT.getCn().equals(type) || TypeEnum.HOT.getEn().equals(type)) {//显示加载近期热门推荐数据
							
						}
						if (TypeEnum.EDIT.getCn().equals(type) || TypeEnum.EDIT.getEn().equals(type)) {//显示加载近期热门推荐数据
							
						}
						if (TypeEnum.NEWEST.getCn().equals(type) || TypeEnum.NEWEST.getEn().equals(type)) {//显示加载近期热门推荐数据
							
						}
						if (TypeEnum.HANDSOME.getCn().equals(type) || TypeEnum.HANDSOME.getEn().equals(type)) {//显示加载近期热门推荐数据
							
						}
					}
				}
			}
    		recommendResultVO.setData(homeDataVO);
    		recommendResultVO.setCode(0);
    		recommendResultVO.setMsg("返回数据成功");
    		return recommendResultVO;
		} catch (Exception e) {
			log.error("", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
	             log.debug("【后台小说接口】end===================获取推荐页面数据======================end");
	        }
		}
	}

    @GetMapping(value = "/detail/{novelId}")
    public ResultVO<NovelInfoVO> detail(@PathVariable(name = "novelId") String novelId) {
        ResultVO<NovelInfoVO> infoVOResultVO = new ResultVO<NovelInfoVO>();
        try {
           if (log.isDebugEnabled()) {
               log.debug("【后台小说接口】start====================获取小说详情数据====================start");
           }
           NovelInfo novelInfo = novelInfoService.find(novelId);
           NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
           BeanUtils.copyProperties(novelInfo, novelInfoVO);
           List<NovelChapter> chapterList = novelChapterService.selectByNovelId(novelId);
           List<NovelChapterInfoVO> novelChapterInfoVOList = new ArrayList<>();
           for (NovelChapter novelChapter:chapterList) {
               NovelChapterInfoVO novelChapterInfoVO =  NovelChapterInfoVO.builder().build();
               BeanUtils.copyProperties(novelChapter, novelChapterInfoVO);
               novelChapterInfoVOList.add(novelChapterInfoVO);
           }
           novelInfoVO.setChapters(novelChapterInfoVOList);
           infoVOResultVO.setMsg("返回数据成功");
           infoVOResultVO.setCode(0);
           infoVOResultVO.setData(novelInfoVO);
           return infoVOResultVO;
        } catch (Exception e) {
            log.error("【后台小说接口】获取小说详情数据失败", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】end====================获取小说详情数据====================end");
            }
        }
    }
    
    @GetMapping(value ="/content/{novelId}/{chapterId}")
    public ResultVO<NovelChapterTitleAndContentVO> content(
    		@PathVariable(name = "novelId") String novelId,
    		@PathVariable(name = "chapterId") String chapterId) {
    	ResultVO<NovelChapterTitleAndContentVO> resultVO = new ResultVO<NovelChapterTitleAndContentVO>();
    	try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start====================获取小说内容数据====================start");
			}
			NovelChapter NovelChapter = novelChapterService.selectByNovelIdAndChapterId(novelId, chapterId);
			NovelChapterTitleAndContentVO contentVO = NovelChapterTitleAndContentVO.builder().build();
			contentVO.setTitle(NovelChapter.getTitle());
			contentVO.setContent(NovelChapter.getContent());
			resultVO.setCode(0);
			resultVO.setData(contentVO);
			resultVO.setMsg("返回数据成功");
    		return resultVO;
		} catch (Exception e) {
			log.error("【后台小说接口】获取小说内容数据失败", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】end====================获取小说内容数据====================end");
			}
		}
    }
    
    private List<LoadingDataVO> getBannerAdDataVOs(String typeId) {
    	List<LoadingDataVO> bannerAdDataVOs = new ArrayList<>();
    	List<NovelAdvertisement> advertisements = advertisementService.findByTagAndParentTypeIdLimitSize(AdvertisementEnum.TAG1_APPLICATION.getCode(), AdvertisementEnum.TAG2_TIAOZHUANG.getCode(), 5, typeId);
    	for (NovelAdvertisement novelAdvertisement : advertisements) {
    		LoadingDataVO loadingDataVO = LoadingDataVO.builder().build();
    		loadingDataVO.setId(novelAdvertisement.getAdId());
    		loadingDataVO.setImageUrl(novelAdvertisement.getImageUrl());
    		loadingDataVO.setLinkUrl(new StringBuffer().append(novelAdvertisement.getLinkUrl()).append("/detail/").append(novelAdvertisement.getNovelId()).toString());
    		loadingDataVO.setNovelId(novelAdvertisement.getNovelId());
    		loadingDataVO.setNovelParentCategoryId(novelAdvertisement.getNovelParentCategoryId());
    		loadingDataVO.setTag1(novelAdvertisement.getTag1());
    		loadingDataVO.setTag2(novelAdvertisement.getTag2());
    		bannerAdDataVOs.add(loadingDataVO);
		}
    	return bannerAdDataVOs;
	}
    
    private List<NovelInfoVO> getData(String typeId, String type, Pageable pageable) {
    	List<NovelInfoVO> data = new ArrayList<>();
    	if (StringUtils.isNotEmpty(typeId) && StringUtils.isNotEmpty(type)) {
			
		}
    	return data;
    }
}
