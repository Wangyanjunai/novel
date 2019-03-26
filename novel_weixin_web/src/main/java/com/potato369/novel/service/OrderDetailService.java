package com.potato369.novel.service;

import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.ProductInfo;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName OrderDetailService
 * @Desc 订单详情业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:14
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface OrderDetailService {

    /**
     * 根据订单id查询订单详情列表信息
     * @param orderId
     * @return
     */
    List<OrderDetail> findOrderDetailByOrderId(String orderId);

    /**
     * 根据订单id查询订购的商品信息
     * @param orderId
     * @return
     */
    List<ProductInfo> findProductInfoByOrderId(String orderId);
}
