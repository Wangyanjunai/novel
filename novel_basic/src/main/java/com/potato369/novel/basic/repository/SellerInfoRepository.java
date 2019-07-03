package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @InterfaceName SellerInfoRepository
 * @Desc 卖家信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 11:18
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    /**
     * 根据卖家微信openid查询卖家信息
     *
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}
