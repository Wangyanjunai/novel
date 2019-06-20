package com.potato369.novel.app.web.service;

import javax.servlet.http.HttpServletRequest;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.potato369.novel.app.web.model.AliPayQueryResult;
import com.potato369.novel.app.web.model.AliPayResult;
import com.potato369.novel.app.web.model.WeChatPayQueryResult;
import com.potato369.novel.app.web.model.WeChatPayResult;

import java.util.Map;

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
    WeChatPayResult weixinPay(String orderId);
    
    /**
     * <pre>
     * 支付宝APP支付订单
     * @param orderId
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
     * @param conversionParams
     * @return String
     * </pre>
     */
    String notify1(Map<String, String> conversionParams);

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
    
    /**
     * <pre>
     * 查询微信支付的结果
     * @param orderId
     * </pre>
     */
    WeChatPayQueryResult queryWeChatPayResult(String orderId);
    
    /**
     * <pre>
     * 查询支付宝支付的结果
     * @param orderId
     * </pre>
     */
    AliPayQueryResult queryAliPayResult(String orderId);
}
