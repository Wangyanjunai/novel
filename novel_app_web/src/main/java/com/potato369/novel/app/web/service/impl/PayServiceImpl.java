package com.potato369.novel.app.web.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.potato369.novel.app.web.conf.prop.WeChatPayProperties;
import com.potato369.novel.app.web.model.PayResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.utils.WwwUtil;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
@Transactional
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private WxPayService wxPayService;
    
    @Autowired
    private WeChatPayProperties properties;

    /**
     * <pre>
     * 微信公众号支付订单
     * @param orderMaster
     * @return
     * </pre>
     */
    @Override
    public PayResult createByWeChatPay(OrderMaster orderMaster) {
    	WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
    	try {
    		orderRequest.setBody(new StringBuffer().append(this.properties.getOrderNamePrefix()).append(orderMaster.getOrderName()).toString().trim());
    		orderRequest.setOutTradeNo(orderMaster.getOrderId());
    		orderRequest.setTotalFee(orderMaster.getOrderAmount().intValue()*100);
    		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    		HttpServletRequest request = requestAttributes.getRequest();
    		orderRequest.setSpbillCreateIp(WwwUtil.getIpAddr4(request));
    		orderRequest.setTradeType(WxPayConstants.TradeType.APP);
    		WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(orderRequest);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			
		}
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
     * @param orderMaster
     * @return
     * </pre>
     */
    @Override
    public void refund(OrderMaster orderMaster) {

    }

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderMaster
     * </pre>
     */
    @Override
    public void close(OrderMaster orderMaster) {

    }
}
