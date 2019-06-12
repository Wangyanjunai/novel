package com.potato369.novel.app.web.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.potato369.novel.app.web.dto.OrderDTO;
import com.potato369.novel.app.web.model.PayResult;
import com.potato369.novel.app.web.service.WeChatPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.service.impl
 * @ClassName WeChatPayServiceImpl
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:51
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Slf4j
@Transactional
public class WeChatPayServiceImpl implements WeChatPayService {

    @Autowired
    private WxPayService wxPayService;

    /**
     * <pre>
     * 微信公众号支付订单
     * @param orderDTO
     * @return
     * </pre>
     */
    @Override
    public PayResult create(OrderDTO orderDTO) {
        return null;
    }

    /**
     * <pre>
     * 微信公众号支付异步通知结果
     * @param notifyData
     * @return
     * </pre>
     */
    @Override
    public WxPayOrderNotifyResult notify(String notifyData) {
        return null;
    }

    /**
     * <pre>
     * 微信公众号支付退款
     * @param orderDTO
     * @return
     * </pre>
     */
    @Override
    public void refund(OrderDTO orderDTO) {

    }

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderDTO
     * </pre>
     */
    @Override
    public void close(OrderDTO orderDTO) {

    }
}
