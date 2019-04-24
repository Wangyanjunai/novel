package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.SellerInfo;
import com.potato369.novel.basic.repository.SellerInfoRepository;
import com.potato369.novel.basic.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SellerInfoRepository repository;

    /**
     * <pre>
     * 根据卖家微信openid查找卖家信息
     * @param openid
     * @return
     * </pre>
     */
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) throws Exception{
        return repository.findByOpenid(openid);
    }

    /**
     * <pre>
     * 更新卖家信息
     * @param sellerInfo
     * @return
     * </pre>
     */
    @Override
    @Modifying
    @Transactional
    public SellerInfo updateSellerInfo(SellerInfo sellerInfo) throws Exception{
        return repository.saveAndFlush(sellerInfo);
    }
}
