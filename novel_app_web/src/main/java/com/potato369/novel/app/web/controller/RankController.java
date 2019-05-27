package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.app.web.vo.RankVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.service.CategoryService;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName RankController
 * @Desc RankController
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/9 15:36
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/ranking")
@SuppressWarnings("unchecked")
public class RankController {

    @Autowired
    private NovelInfoService novelInfoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{category}/{name}")
    public ResultVO<RankVO> getRanks(@PathVariable(name = "category") String category,
                                     @PathVariable(name = "name") String name,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "size", defaultValue = "100") Integer size) {
        ResultVO<RankVO> rankVOResultVO = new ResultVO<>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================获取排行数据====================start");
            }
            rankVOResultVO.setData(getData(category, name, page, size));
            rankVOResultVO.setCode(0);
            rankVOResultVO.setMsg("返回数据成功");
            return rankVOResultVO;
        } catch (Exception e) {
            log.error("获取排行数据失败", e);
            return ResultVOUtil.error(-1, "获取数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================获取排行数据======================end");
            }
        }
    }

    public RankVO getData(String category, String name, Integer page, Integer size) {
        RankVO rankVO = RankVO.builder().build();
//        String title = null;
//        String shortTitle = null;
        Sort sort = null;
        List<Integer> categoryTypeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(name)) {
            if ("hotTop100".equals(name)) {
//                title = "追书最热榜 Top100";
//                shortTitle = "最热榜";
                sort = new Sort(Sort.Direction.DESC, "readers");
            }
            if ("loveTop100".equals(name)) {
//                title = "好评榜 Top100";
//                shortTitle = "好评榜";
                sort = new Sort(Sort.Direction.DESC, "recentReaders");
            }
            if ("searchTop100".equals(name)) {
//                title = "热搜榜 Top100";
//                shortTitle = "热搜榜";
                sort = new Sort(Sort.Direction.DESC, "clickNumber");
            }
            if ("retentionTop100".equals(name)) {
//                title = "读者留存率 Top100";
//                shortTitle = "最热榜";
                sort = new Sort(Sort.Direction.DESC, "retention");
            }
        }
//        rankVO.setTitle(title);
//        rankVO.setShortTitle(shortTitle);
        PageRequest pageRequest = new PageRequest(page - 1, size, sort);
        String typeId = null;
        if (StringUtils.isNotEmpty(category)) {
            typeId = BeanUtil.getId(category);
        }
        List<NovelCategory> categoryList = categoryService.findByParentCategoryId(typeId);
        for (NovelCategory c : categoryList) {
            categoryTypeList.add(c.getCategoryType());
        }
        Page<NovelInfo> novelInfoPage = novelInfoService.findNovelInfoByCategoryTypeIn(pageRequest, categoryTypeList);
        List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
        if (novelInfoPage != null && novelInfoPage.getTotalElements() > 0) {
            for (NovelInfo novelInfo : novelInfoPage.getContent()) {
                NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
                BeanUtils.copyProperties(novelInfo, novelInfoVO);
                novelInfoVOList.add(novelInfoVO);
            }
        }
        rankVO.setRanksData(novelInfoVOList);
        rankVO.setTotalPage(new BigDecimal(novelInfoPage.getTotalPages()));
        return rankVO;
    }
}
