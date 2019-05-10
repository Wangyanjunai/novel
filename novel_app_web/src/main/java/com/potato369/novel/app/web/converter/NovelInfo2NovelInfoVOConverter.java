package com.potato369.novel.app.web.converter;

import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.basic.dataobject.NovelInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.converter
 * @ClassName NovelInfo2NovelInfoVOConverter
 * @Desc NovelInfo2NovelInfoVOConverter
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/10 15:06
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class NovelInfo2NovelInfoVOConverter {

    public static NovelInfoVO convert(NovelInfo novelInfo) {
        NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
        BeanUtils.copyProperties(novelInfo, novelInfoVO);
        return novelInfoVO;
    }

    public static List<NovelInfoVO> convertTONovelInfoVOList(List<NovelInfo> novelInfoList) {
        return novelInfoList.stream().map(novelInfo -> convert(novelInfo)).collect(Collectors.toList());
    }

    public static Page<NovelInfoVO> convertNovelInfoVOPage(Page<NovelInfo> novelInfoPage, Pageable pageable) {
        return new PageImpl<>(convertTONovelInfoVOList(novelInfoPage.getContent()), pageable, novelInfoPage.getTotalElements());
    }

    public static NovelInfo convert(NovelInfoVO novelInfoVO) {
        NovelInfo novelInfo = NovelInfo.builder().build();
        BeanUtils.copyProperties(novelInfoVO, novelInfo);
        return novelInfo;
    }

    public static List<NovelInfo> convertTONovelInfoList(List<NovelInfoVO> novelInfoVOList) {
        return novelInfoVOList.stream().map(novelInfo -> convert(novelInfo)).collect(Collectors.toList());
    }

    public static Page<NovelInfo> convertTONovelInfoPage(Page<NovelInfoVO> novelInfoVOPage, Pageable pageable) {
        return new PageImpl<>(convertTONovelInfoList(novelInfoVOPage.getContent()), pageable, novelInfoVOPage.getTotalElements());
    }
}
