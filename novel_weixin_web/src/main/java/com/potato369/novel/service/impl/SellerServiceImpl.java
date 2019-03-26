package com.potato369.novel.service.impl;

import com.potato369.novel.basic.dataobject.SellerInfo;
import com.potato369.novel.basic.repository.SellerInfoRepository;
import com.potato369.novel.converter.SellerInfo2SellerInfoDTOConverter;
import com.potato369.novel.dto.SellerInfoDTO;
import com.potato369.novel.service.PushMessageService;
import com.potato369.novel.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName SellerServiceImpl
 * @Desc 卖家业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 11:29
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Autowired
    private PushMessageService pushMessageService;

    /**
     * <pre>
     * 根据卖家微信openid查找卖家信息
     * @param openid
     * @return
     * </pre>
     */
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    /**
     * <pre>
     * 更新卖家信息
     * @param sellerInfo
     * @return
     * </pre>
     */
    @Override
    public SellerInfo updateSellerInfo(SellerInfo sellerInfo) {
        SellerInfoDTO sellerInfoDTO = SellerInfo2SellerInfoDTOConverter.convert(sellerInfo);
        pushMessageService.pushSellerLoginSuccess(sellerInfoDTO);
        return sellerInfoRepository.saveAndFlush(sellerInfo);
    }
}
