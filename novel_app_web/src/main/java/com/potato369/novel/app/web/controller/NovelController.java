package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.NovelInfo2NovelInfoVOConverter;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.*;
import com.potato369.novel.basic.dataobject.HotWordsInfo;
import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.NovelInfoEnum;
import com.potato369.novel.basic.enums.TypeEnum;
import com.potato369.novel.basic.service.CategoryService;
import com.potato369.novel.basic.service.HotWordsInfoService;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.BeanUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
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
@SuppressWarnings("unchecked")
public class NovelController {

    @Autowired
    private NovelInfoService novelInfoService;

    @Autowired
    private NovelChapterService novelChapterService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HotWordsInfoService hotWordsInfoService;

    @GetMapping(value = "/home/hot/{categoryType}")//近期热门
    public ResultVO<HomeDataVO> homeHot(
            @PathVariable(name = "categoryType") String categoryType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取推荐页面数据=====================start");
                log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
            }
            return ResultVOUtil.success(getData(BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "retention"))));
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
            @PathVariable(name = "categoryType") String categoryType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "3") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取主页主编推荐数据=====================start");
                log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
            }
            return ResultVOUtil.success(getData(BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "clickNumber"))));
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
            @PathVariable(name = "categoryType", required = true) String categoryType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "2") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取主页最近更新推荐页面数据=====================start");
                log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
            }
            return ResultVOUtil.success(getData(BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "updateTime"))));
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
            @PathVariable(name = "categoryType") String categoryType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取主页爽文推荐页面数据=====================start");
                log.debug("【后台小说接口】获取推荐页面categoryType={}", categoryType);
            }
            return ResultVOUtil.success(getData(BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "recentReaders"))));
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
            NovelInfo novelInfo = novelInfoService.findById(novelId);
            updateClickNumber(novelInfo);
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

    @GetMapping(value = "/info/chapter/{novelId}/{index}")//分页显示小说章节目录列表，默认按照索引正序，第一页，每页100条
    public ResultVO<NovelChapterVO> chapterDetail(@PathVariable(name = "novelId") String novelId,//小说id
                                                  @PathVariable(name = "index") Integer index,//0-正序，1-倒序
                                                  @RequestParam(name = "page", defaultValue = "1", required = true) Integer page,//页数
                                                  @RequestParam(name = "size", defaultValue = "100", required = true) Integer size) {//每页条数
        ResultVO<NovelChapterVO> infoVOResultVO = new ResultVO<>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start====================获取小说章节目录列表数据====================start");
            }
            NovelChapterVO chapterVO = NovelChapterVO.builder().build();
            Sort sort = null;
            if (index != null) {
                if (Integer.valueOf(0).equals(index)) {
                    sort = new Sort(Sort.Direction.ASC, "index");
                } else if (Integer.valueOf(1).equals(index)) {
                    sort = new Sort(Sort.Direction.DESC, "index");
                }
            }
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            Page<NovelChapter> novelChapterPage = novelChapterService.findAllByNovelId(novelId, pageRequest);
            List<NovelChapter> chapterList = novelChapterPage.getContent();
            List<NovelChapterInfoVO> novelChapterInfoVOList = new ArrayList<>();
            for (NovelChapter novelChapter : chapterList) {
                NovelChapterInfoVO novelChapterInfoVO = NovelChapterInfoVO.builder().build();
                BeanUtils.copyProperties(novelChapter, novelChapterInfoVO);
                novelChapterInfoVOList.add(novelChapterInfoVO);
            }
            chapterVO.setNovelChapterInfoVOList(novelChapterInfoVOList);
            chapterVO.setTotalPage(novelChapterPage.getTotalPages());
            infoVOResultVO.setMsg("返回数据成功");
            infoVOResultVO.setCode(0);
            infoVOResultVO.setData(chapterVO);
            return infoVOResultVO;
        } catch (Exception e) {
            log.error("【后台小说接口】获取小说章节目录列表数据失败", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】end====================获取小说章节目录列表数据====================end");
            }
        }
    }

    @GetMapping(value = "/chapter/content/{novelId}/{index}")//小说章节内容
    public ResultVO<NovelChapterTitleAndContentVO> content(@PathVariable(name = "novelId") String novelId,
                                                           @PathVariable(name = "index") Integer index) {
        ResultVO<NovelChapterTitleAndContentVO> resultVO = new ResultVO<NovelChapterTitleAndContentVO>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start====================获取小说内容数据====================start");
            }
            NovelChapter chapter = novelChapterService.selectByNovelIdAndIndex(novelId, index);
            NovelChapterTitleAndContentVO contentVO = NovelChapterTitleAndContentVO.builder().build();
            if (chapter != null) {
                BeanUtils.copyProperties(chapter, contentVO);
            }
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

    @GetMapping(value = "/book/hotWords-search")//热词搜索，大家都在搜，获取搜索热词
    public ResultVO<WordsVO> hotWordsSearch(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        ResultVO<WordsVO> wordsVOResultVO = new ResultVO<>();
        WordsVO wordsVO = WordsVO.builder().build();
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】start==================获取搜索热词数据==================start");
            }
            Sort sort = new Sort(Sort.Direction.DESC, "times");
            PageRequest request = new PageRequest(page - 1, size, sort);
            Page<HotWordsInfo> hotWordsInfoPage = hotWordsInfoService.findAll(request);
            wordsVO.setTotalPage(new BigDecimal(hotWordsInfoPage.getTotalPages()));
            List<HotWordsInfoVO> hotWordsInfoVOList = new ArrayList<>();
            for (HotWordsInfo hotWordsInfo : hotWordsInfoPage.getContent()) {
                HotWordsInfoVO hotWordsInfoVO = HotWordsInfoVO.builder().build();
                BeanUtils.copyProperties(hotWordsInfo, hotWordsInfoVO);
                hotWordsInfoVOList.add(hotWordsInfoVO);
            }
            wordsVO.setWords(hotWordsInfoVOList);
            wordsVOResultVO.setData(wordsVO);
            wordsVOResultVO.setCode(0);
            wordsVOResultVO.setMsg("返回数据成功");
            return wordsVOResultVO;
        } catch (Exception e) {
            log.error("获取搜索热词数据出现错误", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】end====================获取搜索热词数据====================end");
            }
        }
    }

    /**
     * <pre>
     * 搜索接口
     * </pre>
     */
    @GetMapping(value = "/book/fuzzy-search")//模糊搜索
    public ResultVO<FuzzySearchVO> fuzzySearch(@RequestParam(name = "keyWords") String keyWords,
                                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {//搜索接口
        try {
            hotWordsAdd(keyWords);
            List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
            FuzzySearchVO fuzzySearchVO = FuzzySearchVO.builder().build();
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】end====================获取搜索的小说内容数据====================end");
                log.debug("搜索关键字keyWords={}", keyWords);
            }
            Sort sort = new Sort(Sort.Direction.DESC, "title", "author", "retention", "createTime");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            Page<NovelInfo> novelInfoPage = novelInfoService.findByAuthorContainsOrTitleContains(keyWords, pageRequest);
            novelInfoVOList = NovelInfo2NovelInfoVOConverter.convertNovelInfoVOPage(novelInfoPage, pageRequest).getContent();
            fuzzySearchVO.setNovelInfoVOList(novelInfoVOList);
            fuzzySearchVO.setTotalPage(new BigDecimal(novelInfoPage.getTotalPages()));
            return ResultVOUtil.success(fuzzySearchVO);
        } catch (Exception e) {
            log.error("获取搜索的小说内容数据出现错误", e);
            return ResultVOUtil.error(-1, "返回数据错误");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说接口】end====================获取搜索的小说内容数据====================end");
            }
        }
    }

    @GetMapping(value = "/book/finshed/{categoryType}")
    public ResultVO<HomeDataVO> finsh(@PathVariable(name = "categoryType") String categoryType,
                                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "size", defaultValue = "40") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【小说后台接口】start====================查询小说状态为已完成的数据====================start");
            }
            return ResultVOUtil.success(getData1(NovelInfoEnum.NOVEL_STATUS_FINISHED.getCode(), BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "createTime", "retention"))));
        } catch (Exception e) {
            log.error("查询小说状态为已完成的数据失败", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【小说后台接口】end======================查询小说状态为已完成的数据======================end");
            }
        }
    }

    @GetMapping(value = "/book/update/{categoryType}")
    public ResultVO<HomeDataVO> update(@PathVariable(name = "categoryType") String categoryType,
                                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【小说后台接口】start====================查询小说状态为已完成的数据====================start");
            }
            return ResultVOUtil.success(getData1(NovelInfoEnum.NOVEL_STATUS_UPDATING.getCode(), BeanUtil.getId(categoryType), new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, "updateTime", "clickNumber"))));
        } catch (Exception e) {
            log.error("查询小说状态为已完成的数据失败", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【小说后台接口】end======================查询小说状态为已完成的数据======================end");
            }
        }
    }

    private HomeDataVO getData(String typeId, PageRequest pageRequest) {
        HomeDataVO homeDataVO = HomeDataVO.builder().build();
        List<NovelInfoVO> data = new ArrayList<>();
        List<Integer> categoryTypeList = new ArrayList<>();
        BigDecimal totalPage = BigDecimal.ZERO;
        if (StringUtils.isNotEmpty(typeId)) {
            List<NovelCategory> categoryList = categoryService.findByParentCategoryId(typeId);
            for (NovelCategory c : categoryList) {
                categoryTypeList.add(c.getCategoryType());
            }
            Page<NovelInfo> novelInfoPage = novelInfoService.findNovelInfoByCategoryTypeIn(pageRequest, categoryTypeList);
            totalPage = new BigDecimal(novelInfoPage.getTotalPages());
            for (NovelInfo novelInfo : novelInfoPage.getContent()) {
                NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
                BeanUtils.copyProperties(novelInfo, novelInfoVO);
                data.add(novelInfoVO);
            }
        }
        homeDataVO.setList(data);
        homeDataVO.setTotalPage(totalPage);
        return homeDataVO;
    }

    private HomeDataVO getData1(Integer status, String typeId, PageRequest pageRequest) {
        HomeDataVO homeDataVO = HomeDataVO.builder().build();
        List<NovelInfoVO> data = new ArrayList<>();
        List<Integer> categoryTypeList = new ArrayList<>();
        BigDecimal totalPage = BigDecimal.ZERO;
        if (StringUtils.isNotEmpty(typeId)) {
            List<NovelCategory> categoryList = categoryService.findByParentCategoryId(typeId);
            for (NovelCategory c : categoryList) {
                categoryTypeList.add(c.getCategoryType());
            }
            Page<NovelInfo> novelInfoPage = novelInfoService.findByNovelStatusAndCategoryTypeIn(status, pageRequest, categoryTypeList);
            totalPage = new BigDecimal(novelInfoPage.getTotalPages());
            for (NovelInfo novelInfo : novelInfoPage.getContent()) {
                NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
                BeanUtils.copyProperties(novelInfo, novelInfoVO);
                data.add(novelInfoVO);
            }
        }
        homeDataVO.setList(data);
        homeDataVO.setTotalPage(totalPage);
        return homeDataVO;
    }

    /**
     * 添加搜索热词
     *
     * @param hotWords
     */
    private void hotWordsAdd(String hotWords) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说】start==================搜索热词添加更新==================start");
            }
            HotWordsInfo hotWordsInfo = HotWordsInfo.builder().build();
            if (StringUtils.isNotEmpty(hotWords)) {
                String wordId = hotWordsInfoService.findByWord(hotWords);
                if (StringUtils.isEmpty(wordId)) {
                    hotWordsInfo.setWordId(UUIDUtil.gen32UUID());
                    hotWordsInfo.setWord(hotWords);
                    hotWordsInfo.setSoaring(new BigDecimal(1));
                    hotWordsInfo.setIsNew(TypeEnum.IS_NEW.getCode());
                    hotWordsInfo.setTimes(new BigDecimal(1));
                    if (log.isDebugEnabled()) {
                        log.debug("搜索热词添加");
                    }
                    hotWordsInfoService.save(hotWordsInfo);
                } else {
                    hotWordsInfo = hotWordsInfoService.findByWordId(wordId);
                    if (hotWordsInfo != null) {
                        hotWordsInfo.setSoaring(hotWordsInfo.getSoaring().add(new BigDecimal(1)));
                        hotWordsInfo.setTimes(hotWordsInfo.getTimes().add(new BigDecimal(1)));
                        hotWordsInfo.setIsNew(TypeEnum.NOT_IS_NEW.getCode());
                        if (log.isDebugEnabled()) {
                            log.debug("搜索热词更新");
                        }
                    }
                    hotWordsInfoService.update(hotWordsInfo);
                }
            }
        } catch (Exception e) {
            log.error("搜索热词添加更新接口出现错误", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【后台小说】end====================搜索热词添加更新====================end");
            }
        }
    }
    private synchronized void updateClickNumber(NovelInfo novelInfo) {
        if (novelInfo != null) {
            String novelId = novelInfo.getId();
            BigDecimal clickNumber = novelInfo.getClickNumber().add(new BigDecimal(1));
            novelInfoService.updateClickNumber(clickNumber, novelId);
        }
    }
}
