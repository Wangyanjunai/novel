package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.NovelChapterInfoVO;
import com.potato369.novel.app.web.vo.NovelChapterTitleAndContentVO;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.service.NovelChapterService;
import com.potato369.novel.basic.service.NovelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "/novel/lv2")
public class NovelController {

    @Autowired
    private NovelInfoService novelInfoService;
    
    @Autowired
    private NovelChapterService novelChapterService;

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
}
