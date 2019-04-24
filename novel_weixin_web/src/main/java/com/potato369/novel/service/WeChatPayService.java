package com.potato369.novel.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.model.PayResult;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @InterfaceName WeChatPayService
 * @Desc WeChatPayService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/13 15:11
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface WeChatPayService {

    /**
     * <pre>
     * 微信公众号支付订单
     * @param orderDTO
     * @return
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    PayResult create(OrderDTO orderDTO);

    /**
     * <pre>
     * 微信公众号支付异步通知结果
     * @param notifyData
     * @return
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    WxPayOrderNotifyResult notify(String notifyData);

    /**
     * <pre>
     * 微信公众号支付退款
     * @param orderDTO
     * @return
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void refund(OrderDTO orderDTO);

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderDTO
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void close(OrderDTO orderDTO);
}
