package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.*;
import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.TypeEnum;
import com.potato369.novel.basic.service.CategoryService;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
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
    private CategoryService categoryService;

    @GetMapping(value = "/home/hot/{categoryType}")//近期热门
    public ResultVO<HomeDataVO> homeHot(
    		@PathVariable(name="categoryType") String categoryType,
			@RequestParam(name="page", defaultValue="1") Integer page,
			@RequestParam(name="size", defaultValue="6") Integer size) {
    	try {
    		if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取推荐页面数据=====================start");
				log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
            }
    		return ResultVOUtil.success(getData(getId(categoryType), new PageRequest(page-1, size, new Sort(Sort.Direction.DESC, "retention")), page));
		} catch (Exception e) {
			log.error("获取推荐首页页面数据失败", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
	             log.debug("【后台小说接口】end===================获取推荐首页页面数据======================end");
	        }
		}
	}

	@GetMapping(value = "/home/editor/{categoryType}")//主编推荐
	public ResultVO<HomeDataVO> homeEditor(
			@PathVariable(name="categoryType") String categoryType,
			@RequestParam(name="page", defaultValue="1") Integer page,
			@RequestParam(name="size", defaultValue="3") Integer size) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start==================获取主页主编推荐数据=====================start");
				log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
			}
			return ResultVOUtil.success(getData(getId(categoryType), new PageRequest(page-1, size, new Sort(Sort.Direction.DESC, "clickNumber")), page));
		} catch (Exception e) {
			log.error("获取主页主编推荐数据失败", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】end===================获取主页主编推荐数据======================end");
			}
		}
	}

	@GetMapping(value = "/home/newest/{categoryType}")//新书推荐
	public ResultVO<HomeDataVO> homeNewest(
			@PathVariable(name="categoryType", required=true) String categoryType,
			@RequestParam(name="page", defaultValue="1") Integer page,
			@RequestParam(name="size", defaultValue="2") Integer size) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start==================获取主页最近更新推荐页面数据=====================start");
				log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
			}
			return ResultVOUtil.success(getData(getId(categoryType), new PageRequest(page-1, size, new Sort(Sort.Direction.DESC, "updateTime")), page));
		} catch (Exception e) {
			log.error("获取主页最近更新推荐页面数据失败", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】end===================获取主页最近更新推荐页面数据======================end");
			}
		}
	}

	@GetMapping(value = "/home/featured/{categoryType}")//爽文推荐
	public ResultVO<HomeDataVO> homeFeatured(
			@PathVariable(name="categoryType") String categoryType,
			@RequestParam(name="page", defaultValue="1") Integer page,
			@RequestParam(name="size", defaultValue="4") Integer size) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start==================获取主页爽文推荐页面数据=====================start");
				log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
			}
			return ResultVOUtil.success(getData(getId(categoryType), new PageRequest(page-1, size, new Sort(Sort.Direction.DESC, "recentReaders")), page));
		} catch (Exception e) {
			log.error("获取主页爽文推荐页面数据失败", e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】end===================获取主页爽文推荐页面数据======================end");
			}
		}
	}

    @GetMapping(value = "/info/detail/{novelId}")//小说详情
    public ResultVO<NovelInfoVO> novelDetail(@PathVariable(name = "novelId") String novelId) {
        ResultVO<NovelInfoVO> infoVOResultVO = new ResultVO<NovelInfoVO>();
        try {
           if (log.isDebugEnabled()) {
               log.debug("【后台小说接口】start====================获取小说详情数据====================start");
           }
           NovelInfo novelInfo = novelInfoService.find(novelId);
           NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
           BeanUtils.copyProperties(novelInfo, novelInfoVO);
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

    @GetMapping(value = "/info/chapter/{novelId}")//小说章节列表
	public ResultVO<NovelChapterVO> chapterDetail(@PathVariable(name = "novelId") String novelId) {
		ResultVO<NovelChapterVO> infoVOResultVO = new ResultVO<NovelChapterVO>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start====================获取小说详情数据====================start");
			}
			NovelChapterVO chapterVO = NovelChapterVO.builder().build();
			List<NovelChapter> chapterList = novelChapterService.selectByNovelId(novelId);
			List<NovelChapterInfoVO> novelChapterInfoVOList = new ArrayList<>();
			for (NovelChapter novelChapter:chapterList) {
				NovelChapterInfoVO novelChapterInfoVO =  NovelChapterInfoVO.builder().build();
				BeanUtils.copyProperties(novelChapter, novelChapterInfoVO);
				novelChapterInfoVOList.add(novelChapterInfoVO);
			}
			chapterVO.setNovelChapterInfoVOList(novelChapterInfoVOList);
			infoVOResultVO.setMsg("返回数据成功");
			infoVOResultVO.setCode(0);
			infoVOResultVO.setData(chapterVO);
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
    
    @GetMapping(value ="/chapter/content/{chapterId}")//小说章节内容
    public ResultVO<NovelChapterTitleAndContentVO> content(@PathVariable(name = "chapterId") String chapterId) {
    	ResultVO<NovelChapterTitleAndContentVO> resultVO = new ResultVO<NovelChapterTitleAndContentVO>();
    	try {
			if (log.isDebugEnabled()) {
				log.debug("【后台小说接口】start====================获取小说内容数据====================start");
			}
			NovelChapter NovelChapter = novelChapterService.selectByChapterId(chapterId);
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
    
    private HomeDataVO getData(String typeId, PageRequest pageRequest, Integer page) {
		HomeDataVO homeDataVO = HomeDataVO.builder().build();
    	List<NovelInfoVO> data = new ArrayList<>();
    	List<Integer> categoryTypeList = new ArrayList<>();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal totalPage = BigDecimal.ZERO;
		BigDecimal currentPage = BigDecimal.ZERO;
    	if (StringUtils.isNotEmpty(typeId)) {
			List<NovelCategory> categoryList = categoryService.findByParentCategoryId(typeId);
			for (NovelCategory c:categoryList) {
				categoryTypeList.add(c.getCategoryType());
			}
			Page<NovelInfo> novelInfoPage = novelInfoService.findNovelInfoByCategoryTypeIn(pageRequest, categoryTypeList);
			total = new BigDecimal(novelInfoPage.getTotalElements());
			totalPage = new BigDecimal(novelInfoPage.getTotalPages());
			currentPage = new BigDecimal(page);
			for (NovelInfo novelInfo: novelInfoPage.getContent()) {
				NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
				BeanUtils.copyProperties(novelInfo, novelInfoVO);
				data.add(novelInfoVO);
			}
		}
		homeDataVO.setList(data);
//    	homeDataVO.setTotal(total);
		homeDataVO.setTotalPage(totalPage);
//		homeDataVO.setCurrentPage(currentPage);
    	return homeDataVO;
    }

    private String getId(String categoryType) {
    	String id = null;
		//如果是男生首页
		if (TypeEnum.MALE.getCn().equals(categoryType) || TypeEnum.MALE.getEn().equals(categoryType)) {
			id = TypeEnum.MALE.getId();
		}
		//如果是女生首页
		if (TypeEnum.FEMALE.getCn().equals(categoryType) || TypeEnum.FEMALE.getEn().equals(categoryType)) {
			id = TypeEnum.FEMALE.getId();
		}
		//如果是漫画首页
		if (TypeEnum.PICTURE.getCn().equals(categoryType) || TypeEnum.PICTURE.getEn().equals(categoryType)) {
			id = TypeEnum.PICTURE.getId();
		}
    	return id;
	}
}
