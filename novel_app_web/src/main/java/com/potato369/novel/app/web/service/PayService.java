package com.potato369.novel.app.web.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.potato369.novel.app.web.dto.OrderDTO;
import com.potato369.novel.app.web.model.PayResult;
import com.potato369.novel.basic.dataobject.OrderMaster;

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
public interface PayService {

    /**
     * <pre>
     * 微信支付订单
     * @param orderMaster
     * @return
     * </pre>
     */
    PayResult createByWeChatPay(OrderMaster orderMaster);

    /**
     * <pre>
     * 微信公众号支付异步通知结果
     * @param notifyData
     * @return
     * </pre>
     */
    WxPayOrderNotifyResult notify(String notifyData);

    /**
     * <pre>
     * 微信公众号支付退款
     * @param orderMaster
     * @return
     * </pre>
     */
    void refund(OrderMaster orderMaster);

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderMaster
     * </pre>
     */
    void close(OrderMaster orderMaster);
}
