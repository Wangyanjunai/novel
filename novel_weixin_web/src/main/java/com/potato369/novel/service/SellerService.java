package com.potato369.novel.service;

import com.potato369.novel.basic.dataobject.SellerInfo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName SellerService
 * @Desc 卖家业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 11:27
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface SellerService {

    /**
     * <pre>
     * 根据卖家微信openid查找卖家信息
     * @param openid
     * @return
     * </pre>
     */
    SellerInfo findSellerInfoByOpenid(String openid);

    /**
     * <pre>
     * 更新卖家信息
     * @param sellerInfo
     * @return
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    SellerInfo updateSellerInfo(SellerInfo sellerInfo);
}
