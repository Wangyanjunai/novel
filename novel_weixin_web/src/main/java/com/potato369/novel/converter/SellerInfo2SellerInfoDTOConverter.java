package com.potato369.novel.converter;

import com.potato369.novel.basic.dataobject.SellerInfo;
import com.potato369.novel.dto.SellerInfoDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * @PackageName com.potato369.novel.converter
 * @ClassName SellerInfo2SellerInfoDTOConverter
 * @Desc 将卖家管理员信息转换为卖家管理员信息DTO转换器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/24 10:07
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class SellerInfo2SellerInfoDTOConverter {

    /**
     * <pre>
     * 将SellerInfo对象转换为SellerInfoDTO对象
     * @param sellerInfo
     * @return SellerInfoDTO对象
     * </pre>
     */
    public static SellerInfoDTO convert(SellerInfo sellerInfo) {
        SellerInfoDTO sellerInfoDTO = SellerInfoDTO.builder().build();
        BeanUtils.copyProperties(sellerInfo, sellerInfoDTO);
        return sellerInfoDTO;
    }

    /**
     * <pre>
     * 将List<SellerInfo>对象转换为List<SellerInfoDTO>对象
     * @param sellerInfoList
     * @return List<SellerInfoDTO>对象
     * </pre>
     */
    public static List<SellerInfoDTO> convertToSellerInfoDTOList(List<SellerInfo> sellerInfoList) {
        return sellerInfoList.stream().map(sellerInfo -> convert(sellerInfo)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<SellerInfo>对象转换为Page<SellerInfoDTO>对象
     * @param sellerInfoPage
     * @return Page<SellerInfo>对象
     * </pre>
     */
    public static Page<SellerInfoDTO> convertToSellerInfoDTOPage(Page<SellerInfo> sellerInfoPage, Pageable pageable) {
        return new PageImpl<>(convertToSellerInfoDTOList(sellerInfoPage.getContent()), pageable, sellerInfoPage.getTotalElements());
    }
    /**
     * <pre>
     * 将SellerInfoDTO对象转换为SellerInfo对象
     * @param sellerInfoDTO
     * @return SellerInfo对象
     * </pre>
     */
    public static SellerInfo convert(SellerInfoDTO sellerInfoDTO) {
        SellerInfo sellerInfo = SellerInfo.builder().build();
        BeanUtils.copyProperties(sellerInfoDTO, sellerInfo);
        return sellerInfo;
    }

    /**
     * <pre>
     * 将List<SellerInfoDTO>对象转换为List<SellerInfo>对象
     * @param sellerInfoDTOList
     * @return List<SellerInfo>对象
     * </pre>
     */
    public static List<SellerInfo> convertToSellerInfoList(List<SellerInfoDTO> sellerInfoDTOList) {
        return sellerInfoDTOList.stream().map(sellerInfoDTO -> convert(sellerInfoDTO)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<SellerInfoDTO>对象转换为Page<SellerInfo>对象
     * @param pageable
     * @param sellerInfoDTOPage
     * @return Page<SellerInfoDTO>对象
     * </pre>
     */
    public static Page<SellerInfo> convertToSellerInfoPage(Page<SellerInfoDTO> sellerInfoDTOPage, Pageable pageable) {
        return new PageImpl<>(convertToSellerInfoList(sellerInfoDTOPage.getContent()), pageable, sellerInfoDTOPage.getTotalElements());
    }
}
