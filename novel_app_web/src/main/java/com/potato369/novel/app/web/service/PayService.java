package com.potato369.novel.app.web.service;

import javax.servlet.http.HttpServletRequest;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.potato369.novel.app.web.model.AliPayResult;
import com.potato369.novel.app.web.model.WeixinPayResult;
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
     * @param orderId
     * @return
     * </pre>
     */
    WeixinPayResult weixinPay(String orderId);
    
    /**
     * <pre>
     * 支付宝APP支付订单
     * @param orderMaster
     * @return
     * </pre>
     */
    AliPayResult aliPay(String orderId);
    
    /**
     * <pre>
     * 余额APP支付订单
     * @param orderId
     * @return
     * </pre>
     */
    void balancePay(String orderId);

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
     * 支付宝支付异步通知结果
     * @param request
     * @return
     * </pre>
     */
    void notify1(HttpServletRequest request);

    /**
     * <pre>
     * 微信公众号支付退款
     * @param orderId
     * @return
     * </pre>
     */
    void refund(String orderId);

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderId
     * </pre>
     */
    void close(String orderId);
}
