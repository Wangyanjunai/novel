package com.potato369.novel.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.conf.prop.WeChatPayProperties;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.exception.NovelOrderException;
import com.potato369.novel.exception.SellerAuthorizeException;
import com.potato369.novel.model.PayResult;
import com.potato369.novel.service.OrderDetailService;
import com.potato369.novel.service.OrderService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.service.WeChatPayService;
import com.potato369.novel.utils.JsonUtil;
import com.potato369.novel.utils.StringUtil;
import com.potato369.novel.utils.UUIDUtil;
import com.potato369.novel.utils.WxPaySignature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName WeChatPayServiceImpl
 * @Desc WeChatPayServiceImpl
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/13 15:28
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Slf4j
public class WeChatPayServiceImpl implements WeChatPayService {

    @Autowired
    private WxPayService wxPayService;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private WeChatPayProperties properties;

    /**
     * <pre>
     * 微信公众号支付订单
     * @param orderDTO
     * @return
     * </pre>
     */
    @Override
    public PayResult create(OrderDTO orderDTO) {
        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder().build();
        request.setOutTradeNo(orderDTO.getOrderId());
        request.setTotalFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));
        request.setBody(orderDTO.getOrderName());
        request.setOpenid(orderDTO.getBuyerOpenid());
        request.setSpbillCreateIp(orderDTO.getCip());
        request.setNonceStr(StringUtil.getRandomStr());
        request.setTradeType(WxPayConstants.TradeType.JSAPI);
        WxPayMpOrderResult result = null;
        try {
            result = wxPayService.createOrder(request);
        } catch (Exception e) {
            log.error("【微信公众号支付】出现错误e={}", e);
        }
        return buildPayPayResult(result);
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
    	//需要注意的点：
    	//1、验证签名
        //2、支付的状态
        //3、支付的金额
        //4、下单人（下单人=支付人）
    	try {
			if (log.isDebugEnabled()) {
				log.debug("【微信公众号支付】开始执行异步通知结果");
			}
			//解析微信发送过来的xml数据
			WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(notifyData);
			//查询订单信息
			OrderDTO orderDTO = orderService.findOne(result.getOutTradeNo());
			if (orderDTO == null) {
				log.error("【微信公众号支付】异步通知，订单不存在 orderId={}", result.getOutTradeNo());
			    throw new SellerAuthorizeException(ResultEnum.ORDER_NOT_EXIST);
			}
			//判断订单金额与支付金额是否一致（0.10 == 0.1）
			if (!BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()).equals(result.getTotalFee())) {
				log.error("【微信公众号支付】异步通知，订单金额不一致 orderId={}，微信通知金额={}，系统金额={}", result.getOutTradeNo(), result.getTotalFee(), orderDTO.getOrderAmount());
			    throw new SellerAuthorizeException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
			}
			// 修改订单的支付状态
		    orderService.paid(orderDTO);
		    return result;
		} catch (Exception e) {
			log.error("【微信公众号支付】执行异步通知结果出现错误e={}", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【微信公众号支付】结束执行异步通知结果");
			}
		}
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
    	WxPayRefundRequest request = new WxPayRefundRequest();
    	request.setOutTradeNo(orderDTO.getOrderId());
    	request.setOutRefundNo(UUIDUtil.genTimstampUUID());
    	request.setTotalFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));
    	request.setRefundFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));
    	try {
    		if (log.isDebugEnabled()) {
				log.debug("【微信公众号支付退款】开始退款");
			}
    		//退款
			wxPayService.refund(request);
    		//减去已经加上的书币
            UserInfo userInfo = userInfoService.findByOpenid(orderDTO.getBuyerOpenid());
            if (userInfo == null) {
                log.error("【微信公众号支付退款】给对应的用户减去书币失败，用户微信openid={}，用户信息不存在", orderDTO.getBuyerOpenid());
                throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
            }
            List<ProductInfo> productInfoList = orderDetailService.findProductInfoByOrderId(orderDTO.getOrderId());
            BigDecimal quantity = null;
            for (ProductInfo productInfo : productInfoList) {
                quantity = productInfo.getProductQuantity().add(productInfo.getProductGiveQuantity());
            }
            BigDecimal balance = userInfo.getBalance().subtract(quantity);
            userInfo.setBalance(balance);
            UserInfo result =  userInfoService.save(userInfo);
            if (result == null) {
                log.error("【微信公众号支付退款】给对应的用户去书币失败，用户信息={}", JsonUtil.toJson(userInfo));
                throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
            }
		} catch (Exception e) {
			log.error("【微信公众号支付退款】出现错误e={}", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【微信公众号支付退款】结束退款");
			}
		}
    }

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderDTO
     * </pre>
     */
    @Override
    public void close(OrderDTO orderDTO) {
        try {
        	orderService.cancel(orderDTO);
        	WxPayOrderCloseResult request = wxPayService.closeOrder(orderDTO.getOrderId());
        	log.info("【关闭超时未支付的订单】返回数据data request={}", JsonUtil.toJson(request));
        } catch (Exception e) {
           log.error("【关闭超时未支付的订单】出现错误", e);
        }
    }

    /**
     * 返回给h5的参数
     * @param response
     * @return
     */
    private PayResult buildPayPayResult(WxPayMpOrderResult response) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = StringUtil.getRandomStr();
        String packAge = response.getPackageValue();
        String signType = "MD5";

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appId", response.getAppId());
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", packAge);
        map.put("signType", signType);

        PayResult payResponse = new PayResult();
        payResponse.setAppId(response.getAppId());
        payResponse.setTimeStamp(timeStamp);
        payResponse.setNonceStr(nonceStr);
        payResponse.setPackAge(packAge);
        payResponse.setSignType(signType);
        payResponse.setPaySign(WxPaySignature.sign(map, properties.getMchKey()));

        return payResponse;
    }
}
