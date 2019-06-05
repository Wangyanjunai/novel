package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.repository.OrderDetailRepository;
import com.potato369.novel.basic.repository.ProductInfoRepository;
import com.potato369.novel.basic.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName OrderDetailServiceImpl
 * @Desc 订单详情业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:15
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private ProductInfoRepository productInfoRepository;

    /**
     * 根据订单id查询订单详情列表信息
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetail> findOrderDetailByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    /**
     * 根据订单id查询订购的商品信息
     *
     * @param orderId
     * @return
     */
    @Override
    public List<ProductInfo> findProductInfoByOrderId(String orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        if (orderDetailList != null) {
            for (OrderDetail orderDetail : orderDetailList) {
                ProductInfo productInfo = productInfoRepository.findOne(orderDetail.getProductId());
                productInfoList.add(productInfo);
            }
        }
        return productInfoList;
    }
}
