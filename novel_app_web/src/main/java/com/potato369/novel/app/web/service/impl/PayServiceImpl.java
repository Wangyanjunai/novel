package com.potato369.novel.app.web.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.potato369.novel.app.web.model.WeixinPayResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.utils.DateUtil;
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
    private OrderService orderService;

    /**
     * <pre>
     * 微信APP支付订单
     * @param orderId
     * @return
     * </pre>
     */
    @Override
    public WeixinPayResult weixinPay(String orderId) {
    	WeixinPayResult payResult = new WeixinPayResult();
    	try {
    		OrderMaster order = orderService.findOne(orderId);
    		if (order == null) {
    			log.error("【微信APP支付订单】 订单信息不存在，订单id={}", orderId);
    			throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
			}
    		if (order.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
    			log.error("【微信APP支付订单】 订单状态不正确，订单id={}，订单状态={}", orderId, order.getOrderStatus());
    			throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
			}
    		if (order.getPayStatus() != PayStatusEnum.WAITING.getCode()) {
    			log.error("【微信APP支付订单】 订单支付状态不正确，订单id={}，订单支付状态={}", orderId, order.getPayStatus());
    			throw new Exception(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
			}
    		WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
    		orderRequest.setBody(order.getOrderName());
    		orderRequest.setOutTradeNo(order.getOrderId());
    		orderRequest.setTotalFee(order.getOrderAmount().intValueExact()*100);
    		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    		HttpServletRequest request = requestAttributes.getRequest();
    		orderRequest.setSpbillCreateIp(WwwUtil.getIpAddr4(request));
    		orderRequest.setTradeType(WxPayConstants.TradeType.APP);
    		WxPayAppOrderResult result = wxPayService.createOrder(orderRequest);
    		if (result != null) {
    			payResult.setReturnCode("SUCCESS");
				payResult.setReturnMsg("生成微信APP预支付订单信息成功。");
				payResult.setResultCode("SUCCESS");
				payResult.setErrCode("SUCCESS");
				payResult.setErrCodeDes("请求微信支付统一下单接口生成预付单数据成功。");
				payResult.setAppId(result.getAppId());
				payResult.setPartnerId(result.getPartnerId());
				payResult.setPackageValue(result.getPackageValue());
				payResult.setPrepayId(result.getPrepayId());
				payResult.setNonceStr(result.getNonceStr());
				payResult.setSign(result.getSign());
				payResult.setTimeStamp(result.getTimeStamp());
				return payResult;
			} else {
				payResult.setReturnCode("FAIL");
				payResult.setReturnMsg("生成微信APP预支付订单信息失败");
				payResult.setResultCode("FAIL");
				payResult.setErrCode("FAIL");
				payResult.setErrCodeDes("请求微信支付统一下单接口生成预付单数据为空。");
				return payResult;
			}
		} catch (Exception e) {
			log.error("生成微信APP预支付订单信息出现错误", e);
			payResult.setReturnCode("FAIL");
			payResult.setReturnMsg("生成微信APP预支付订单信息失败");
			payResult.setResultCode("FAIL");
			payResult.setErrCode("FAIL");
			payResult.setErrCodeDes("生成微信APP预支付订单信息出现错误。");
			return payResult;
		} finally {
			
		}
    }
    
    /**
     * <pre>
     * 支付宝APP支付订单
     * @param orderId
     * @return
     * </pre>
     */
    @Override
    public void aliPay(String orderId) {
		
	}
    
    /**
     * <pre>
     * 余额APP支付订单
     * @param orderId
     * @return
     * </pre>
     */
    @Override
    public void balancePay(String orderId) {
		
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
    	WxPayOrderNotifyResult payOrderNotifyResult = null;
    	try {
    		payOrderNotifyResult = wxPayService.parseOrderNotifyResult(notifyData);
    		if (log.isDebugEnabled()) {
    			log.debug("【微信APP支付】异步通知，payResponse={}", JSONUtils.toJSONString(payOrderNotifyResult));
			}
    		if (payOrderNotifyResult != null) {
				String returnCode = payOrderNotifyResult.getReturnCode();
				if ("SUCCESS".equals(returnCode)) {
					String resultCode = payOrderNotifyResult.getResultCode();
					if ("SUCCESS".equals(resultCode)) {
						OrderMaster orderMaster = orderService.findOne(payOrderNotifyResult.getOutTradeNo());//商户订单号
						if (orderMaster == null) {
							log.error("【微信APP支付】异步通知，订单不存在 orderId={}", payOrderNotifyResult.getOutTradeNo());
						    throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
						} else {
							if (payOrderNotifyResult.getTotalFee().compareTo(orderMaster.getOrderAmount().intValue()) != 0) {
								log.error("【微信APP支付】异步通知，订单金额不一致 orderId={}，微信通知金额={}，系统金额={}", payOrderNotifyResult.getOutTradeNo(), payOrderNotifyResult.getTotalFee(), orderMaster.getOrderAmount());
							    throw new Exception(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR.getMessage());
							}
							orderMaster.setTransactionalId(payOrderNotifyResult.getTransactionId());
							orderMaster.setPayTime(DateUtil.dateFormat("yyyyMMddHHmmss", payOrderNotifyResult.getTimeEnd()));
							orderService.paid(orderMaster);
						}
					} else {
						
					}
				} else {
					
				}
			} else {
				
			}
    		return payOrderNotifyResult;
		} catch (Exception e) {
			log.error("", e);
			return payOrderNotifyResult;
		} finally {
			
		}
    }

    /**
     * <pre>
     * 微信公众号支付退款
     * @param orderId
     * @return
     * </pre>
     */
    @Override
    public void refund(String orderId) {

    }

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderId
     * </pre>
     */
    @Override
    public void close(String orderId) {

    }
}
