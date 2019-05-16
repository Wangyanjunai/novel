package com.potato369.novel.app.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.potato369.novel.app.web.converter.NovelInfo2NovelInfoVOConverter;
import com.potato369.novel.app.web.dto.ShelfInfoDTO;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.ShelfDetailInfoVO;
import com.potato369.novel.app.web.vo.ShelfInfoVO;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.dataobject.NovelShelf;
import com.potato369.novel.basic.dataobject.NovelShelfDetail;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.service.ShelfDetailService;
import com.potato369.novel.basic.service.ShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName ShelfController
 * @Desc 书架控制器类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 14:56
 * @CreateBy InTellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/shelf")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private ShelfDetailService detailService;

    @Autowired
    private NovelInfoService novelInfoService;

    /**
     * <pre>
     * 获取用户书架
     * @param userId 用户id
     * </pre>
     */
    @GetMapping(value = "/get")
    public ResultVO<ShelfInfoVO> getShelf(@RequestParam(name = "userId") String userId) {
        ResultVO<ShelfInfoVO> shelfInfoVOResultVO = new ResultVO<ShelfInfoVO>();
        ShelfInfoVO shelfInfoVO = ShelfInfoVO.builder().build();
        try {
            NovelShelf novelShelf = shelfService.selectByUserId(userId);
            String shelfId = null;
            if (novelShelf != null) {
                shelfId = novelShelf.getShelfId();
            }
            BeanUtils.copyProperties(novelShelf, shelfInfoVO);
            List<NovelShelfDetail> shelfDetailList = detailService.selectByUserIdAndShelfId(userId, shelfId);
            List<ShelfDetailInfoVO> shelfDetailInfoVOList = new ArrayList<>();
            if (shelfDetailList != null && !shelfDetailList.isEmpty() && shelfDetailList.size() > 0) {
                for (NovelShelfDetail shelfDetail : shelfDetailList) {
                    ShelfDetailInfoVO shelfDetailInfoVO = ShelfDetailInfoVO.builder().build();
                    BeanUtils.copyProperties(shelfDetail, shelfDetailInfoVO);
                    String novelId = shelfDetail.getNovelId();
                    if (StringUtils.isNotEmpty(novelId)) {
                        NovelInfo novelInfo = novelInfoService.findById(novelId);
                        if (novelInfo != null) {
                            NovelInfoVO novelInfoVO = NovelInfo2NovelInfoVOConverter.convert(novelInfo);
                            shelfDetailInfoVO.setNovelInfoVO(novelInfoVO);
                        }
                    }
                    shelfDetailInfoVOList.add(shelfDetailInfoVO);
                }
            }
            shelfInfoVO.setShelfDetailInfoVOList(shelfDetailInfoVOList);
            shelfInfoVOResultVO.setData(shelfInfoVO);
            shelfInfoVOResultVO.setCode(0);
            shelfInfoVOResultVO.setMsg("返回数据成功");
        } catch (Exception e) {
            log.error("", e);
        } finally {

        }
        return shelfInfoVOResultVO;
    }

    /**
     * <pre>
     * 将小说添加到书架
     * 用户id，小说id，书架id，小说章节id，章节索引
     * </pre>
     */
    @PostMapping(value = "/add")
    public void addToShelf(@RequestParam(name = "shelfInfoJson") String shelfInfoJson) {
        try {
            ShelfInfoDTO shelf =  (ShelfInfoDTO)JSONUtils.parse(shelfInfoJson);
        } catch (Exception e) {

        } finally {

        }
    }
}
