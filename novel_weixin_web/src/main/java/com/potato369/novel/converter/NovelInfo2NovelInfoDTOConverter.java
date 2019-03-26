package com.potato369.novel.converter;

import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.dto.NovelInfoDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
/**
 * <pre>
 * @PackageName com.potato369.novel.converter
 * @ClassName NovelInfo2NovelInfoDTOConverter
 * @Desc 将小说信息转换为小说信息DTO转化器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-01-22 23:25
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class NovelInfo2NovelInfoDTOConverter {

    /**
     * <pre>
     * 将NovelInfo对象转换为NovelInfoDTO对象
     * @param novelInfo
     * @return NovelInfoDTO对象
     * </pre>
     */
    public static NovelInfoDTO convert(NovelInfo novelInfo) {
        NovelInfoDTO novelInfoDTO = NovelInfoDTO.builder().build();
        BeanUtils.copyProperties(novelInfo, novelInfoDTO);
        return novelInfoDTO;
    }

    /**
     * <pre>
     * 将List<NovelInfo>对象转换为List<NovelInfoDTO>对象
     * @param novelInfoList
     * @return List<NovelInfoDTO>对象
     * </pre>
     */
    public static List<NovelInfoDTO> convertToNovelInfoDTOList(List<NovelInfo> novelInfoList) {
        return novelInfoList.stream().map(novelInfo -> convert(novelInfo)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<NovelInfo>对象转换为Page<NovelInfoDTO>
     * @param novelInfoPage
     * @return Page<NovelInfoDTO>
     * </pre>
     */
    public static Page<NovelInfoDTO> convertToNovelInfoDTOPage(Page<NovelInfo> novelInfoPage, Pageable pageable) {
        return new PageImpl<>(convertToNovelInfoDTOList(novelInfoPage.getContent()), pageable, novelInfoPage.getTotalElements());
    }
    /**
     * <pre>
     * 将NovelInfoDTO对象转换为NovelInfo对象
     * @param novelInfoDTO
     * @return NovelInfo对象
     * </pre>
     */
    public static NovelInfo convert(NovelInfoDTO novelInfoDTO) {
        NovelInfo novelInfo = NovelInfo.builder().build();
        BeanUtils.copyProperties(novelInfoDTO, novelInfo);
        return novelInfo;
    }

    /**
     * <pre>
     * 将List<NovelInfoDTO>对象转换为List<NovelInfo>对象
     * @param novelInfoDTOList
     * @return List<NovelInfo>对象
     * </pre>
     */
    public static List<NovelInfo> convertToNovelInfoList(List<NovelInfoDTO> novelInfoDTOList) {
        return novelInfoDTOList.stream().map(novelInfoDTO -> convert(novelInfoDTO)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<NovelInfoDTO>对象转换为Page<NovelInfo>对象
     * @param novelInfoDTOPage
     * @return Page<NovelInfo>对象
     * </pre>
     */
    public static Page<NovelInfo> convertToNovelInfoPage(Page<NovelInfoDTO> novelInfoDTOPage, Pageable pageable) {
        return new PageImpl<>(convertToNovelInfoList(novelInfoDTOPage.getContent()), pageable, novelInfoDTOPage.getTotalElements());
    }
}
