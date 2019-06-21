package com.potato369.novel.app.web.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.potato369.novel.app.web.conf.prop.AliPayProperties;
import com.potato369.novel.app.web.conf.prop.ProjectUrlProperties;
import com.potato369.novel.app.web.converter.UserInfo2UserInfoVOConverter;
import com.potato369.novel.app.web.model.*;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.app.web.utils.MathUtil;
import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.PayTypeEnum;
import com.potato369.novel.basic.enums.ProductCalculateTypeEnum;
import com.potato369.novel.basic.enums.ProductTypeEnum;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.enums.UserInfoVIPGradeIdEnum;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.ProductService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import com.potato369.novel.basic.utils.WwwUtil;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private ProjectUrlProperties urlProperties;

    @Autowired
    private AliPayProperties properties;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserInfoService userInfoService;

    /**
     * <pre>
     * 微信APP支付订单
     * @param orderId
     * @return
     * </pre>
     */
    @Override
    public WeChatPayResult weixinPay(String orderId) {
        WeChatPayResult payResult = new WeChatPayResult();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================生成微信APP预支付订单信息====================start");
            }
            OrderMaster orderInfo = checkOrder(orderId);
            ProductInfo productInfo = productService.findOne(orderInfo.getProductId());
            if (productInfo == null) {
                log.error("【检查支付的订单信息】 商品信息不存在，商品id={}", orderInfo.getProductId());
                throw new Exception(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody(orderInfo.getOrderName());
            orderRequest.setOutTradeNo(orderInfo.getOrderId());
            orderRequest.setTotalFee(orderInfo.getOrderAmount().multiply(new BigDecimal(100)).intValue());
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            orderRequest.setSpbillCreateIp(WwwUtil.getIpAddr4(request));
            orderRequest.setTradeType(WxPayConstants.TradeType.APP);
            orderRequest.setProductId(productInfo.getProductId());
            WxPayAppOrderResult result = wxPayService.createOrder(orderRequest);
            if (result != null) {
                payResult.setReturnCode("SUCCESS");
                payResult.setReturnMsg("生成微信APP预支付订单信息成功。");
                payResult.setResultCode("SUCCESS");
                payResult.setErrCode("SUCCESS");
                payResult.setErrCodeDes("请求微信支付统一下单接口生成预付单信息成功。");
                payResult.setAppId(result.getAppId());
                payResult.setPartnerId(result.getPartnerId());
                payResult.setPackageValue(result.getPackageValue());
                payResult.setPrepayId(result.getPrepayId());
                payResult.setNonceStr(result.getNonceStr());
                payResult.setSign(result.getSign());
                payResult.setTimeStamp(result.getTimeStamp());
                payResult.setOrderId(orderId);
                return payResult;
            }
            payResult.setReturnCode("FAIL");
            payResult.setReturnMsg("生成微信APP预支付订单信息失败。");
            payResult.setResultCode("FAIL");
            payResult.setErrCode("FAIL");
            payResult.setErrCodeDes("请求微信支付统一下单接口生成预付单信息为空。");
            payResult.setOrderId(orderId);
            return payResult;
        } catch (Exception e) {
            log.error("生成微信APP预支付订单信息出现错误。", e);
            payResult.setReturnCode("FAIL");
            payResult.setReturnMsg("请求微信支付统一下单接口生成预付单信息失败。");
            payResult.setResultCode("FAIL");
            payResult.setErrCode("FAIL");
            payResult.setErrCodeDes("请求微信支付统一下单接口生成预付单信息出现错误。");
            payResult.setOrderId(orderId);
            return payResult;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================生成微信APP预支付订单信息======================end");
            }
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
    public AliPayResult aliPay(String orderId) {
        AliPayResult alipayResult = new AliPayResult();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================生成支付宝APP预支付订单信息====================start");
            }
            OrderMaster orderInfo = checkOrder(orderId);
            ProductInfo productInfo = productService.findOne(orderInfo.getProductId());
            if (productInfo == null) {
                log.error("【检查支付的订单信息】 商品信息不存在，商品id={}", orderInfo.getProductId());
                throw new Exception(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(orderInfo.getOrderName());
            model.setSubject("急速追书" + orderInfo.getOrderName());
            model.setOutTradeNo(orderInfo.getOrderId());
            model.setTimeoutExpress(StringUtils.trimToNull(this.properties.getTimeoutExpress()));
            model.setTotalAmount(orderInfo.getOrderAmount().toString());
            model.setGoodsType("0");
            model.setProductCode(productInfo.getProductCode());
            request.setBizModel(model);
            request.setNotifyUrl(StringUtils.trimToNull(this.urlProperties.getDomainUrl() + this.urlProperties.getProjectName() + this.properties.getNotifyUrl()));
            AlipayClient alipayClient = new DefaultAlipayClient(
                    StringUtils.trimToNull(this.properties.getServerPayUrl()),
                    StringUtils.trimToNull(this.properties.getAppId()),
                    StringUtils.trimToNull(this.properties.getAppPrivateKey()),
                    StringUtils.trimToNull(this.properties.getFormat()),
                    StringUtils.trimToNull(this.properties.getCharSet()),
                    StringUtils.trimToNull(this.properties.getAliPayPublicKey()),
                    StringUtils.trimToNull(this.properties.getSignType()));
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response != null) {
                String body = response.getBody();
                if (body != null) {
                    alipayResult.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
                    alipayResult.setBody(body);
                    alipayResult.setReturnCode("SUCCESS");
                    alipayResult.setResultCode("SUCCESS");
                    alipayResult.setReturnMsg("生成支付宝APP预支付订单信息返回数据成功。");
                    alipayResult.setErrCode("SUCCESS");
                    alipayResult.setErrCodeDes("生成支付宝APP预支付订单信息返回数据成功。");
                    alipayResult.setOrderId(orderId);
                    return alipayResult;
                }
            }
            alipayResult.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
            alipayResult.setBody(null);
            alipayResult.setReturnCode("FAIL");
            alipayResult.setResultCode("FAIL");
            alipayResult.setReturnMsg("生成支付宝APP预支付订单信息返回数据失败。");
            alipayResult.setErrCode("FAIL");
            alipayResult.setErrCodeDes("生成支付宝APP预支付订单信息返回数据失败。");
            alipayResult.setOrderId(orderId);
            return alipayResult;
        } catch (Exception e) {
            log.error("生成支付宝APP预支付订单信息出现错误", e);
            alipayResult.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
            alipayResult.setBody(null);
            alipayResult.setReturnCode("FAIL");
            alipayResult.setResultCode("FAIL");
            alipayResult.setReturnMsg("生成支付宝APP预支付订单信息返回数据失败。");
            alipayResult.setErrCode("FAIL");
            alipayResult.setErrCodeDes("生成支付宝APP预支付订单信息返回数据失败。");
            alipayResult.setOrderId(orderId);
            return alipayResult;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================生成支付宝APP预支付订单信息======================end");
            }
        }
    }

    /**
     * <pre>
     * 余额APP支付订单
     * @param orderId
     * @return UserInfoVO
     * </pre>
     */
    @Override
    public UserInfoVO balancePay(String orderId) {
    	UserInfoVO userInfoVO = new UserInfoVO();
    	try {
			OrderMaster orderMaster = checkOrder(orderId);
			if (orderMaster != null) {
				String userId = orderMaster.getUserId();
				NovelUserInfo userInfo = userInfoService.findById(userId);
				if (userInfo == null) {
					log.error("余额支付，用户信息不存在");
					throw new Exception("余额支付，用户信息不存在");
				}
				BigDecimal balanceAmount = userInfo.getBalanceAmount();
				if (!MathUtil.compareTo(balanceAmount.doubleValue(), orderMaster.getOrderAmount().doubleValue())) {
					log.error("余额支付，用户余额不足");
					throw new Exception("余额支付，用户余额不足");
				}
				String productId = orderMaster.getProductId();
				ProductInfo productInfo = productService.findOne(productId);
				if (productInfo == null) {
					log.error("余额支付，商品信息不存在");
					throw new Exception("余额支付，商品信息不存在");
				}
				if (!ProductTypeEnum.EXCHANGE.getCode().equals(productInfo.getProductType()) && 
					!ProductTypeEnum.WITHDRAW.getCode().equals(productInfo.getProductType())) {
					log.error("余额支付，商品类型不支持余额支付");
					throw new Exception("余额支付，商品类型不支持余额支付");
				}
				userInfo.setBalanceAmount(balanceAmount.subtract(orderMaster.getOrderAmount()));
				userInfo.setVipGradeId(UserInfoVIPGradeIdEnum.VIP2.getMessage());
	            Date vipEndTime = userInfo.getVipEndTime();
	            Integer calculateType = productInfo.getProductCalculateType();
	            Integer dateValue = productInfo.getDateValue();
	            Date updateVIPEndTime = null;
	            if (ProductCalculateTypeEnum.DAY.getCode().equals(calculateType)) {
	                updateVIPEndTime = DateUtil.getAfterDayDate(vipEndTime, dateValue);
	            }
	            if (ProductCalculateTypeEnum.MONTH.getCode().equals(calculateType)) {
	                updateVIPEndTime = DateUtil.getAfterMonthDate(vipEndTime, dateValue);
	            }
	            userInfo.setVipEndTime(updateVIPEndTime);
	            NovelUserInfo userInfoUpdateResult = userInfoService.save(userInfo);
	            if (userInfoUpdateResult == null) {
	                log.error("【余额支付回调更新订单】给对应的用户增加VIP时长失败，用户信息={}", userInfo);
	                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
	            }
	            
				orderMaster.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
				orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
				orderMaster.setOrderType(productInfo.getProductType());
				orderMaster.setPayTime(new Date());
				orderMaster.setTransactionId(UUIDUtil.genTimstampUUID());
				OrderMaster orderMasterUpdateResult = orderService.save(orderMaster);
				if (orderMasterUpdateResult == null) {
		            log.error("【余额支付回调更新订单】更新订单失败，orderMaster={}", orderMaster);
		            throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
		        }
				userInfoVO = UserInfo2UserInfoVOConverter.convert(userInfoUpdateResult);
				return userInfoVO;
			}
			return userInfoVO;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return userInfoVO;
		} finally {
			
		} 
    }

    /**
     * <pre>
     * 微信APP支付异步通知结果
     * @param notifyData
     * @return
     * </pre>
     */
    @Override
    public WxPayOrderNotifyResult weChatPayNotify(String notifyData) {
        WxPayOrderNotifyResult payOrderNotifyResult = null;
        try {
            payOrderNotifyResult = wxPayService.parseOrderNotifyResult(notifyData);
            if (payOrderNotifyResult != null) {
                String returnCode = payOrderNotifyResult.getReturnCode();
                if ("SUCCESS".equals(returnCode)) {
                    String resultCode = payOrderNotifyResult.getResultCode();
                    if ("SUCCESS".equals(resultCode)) {
                        OrderMaster orderMaster = orderService.findOne(payOrderNotifyResult.getOutTradeNo());//商户订单号
                        if (orderMaster == null) {
                            log.error("【微信APP支付】异步通知，订单不存在 orderId={}", payOrderNotifyResult.getOutTradeNo());
                            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
                        }
                        if (!MathUtil.equals(payOrderNotifyResult.getTotalFee().doubleValue(), BaseWxPayRequest.yuanToFen(String.valueOf(orderMaster.getOrderAmount())).doubleValue())) {
                            log.error("【微信APP支付】异步通知，订单金额不一致 orderId={}，微信通知金额={}，商户订单系统记录金额={}", payOrderNotifyResult.getOutTradeNo(), payOrderNotifyResult.getTotalFee().doubleValue(), BaseWxPayRequest.yuanToFen(String.valueOf(orderMaster.getOrderAmount())).doubleValue());
                            throw new Exception(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR.getMessage());
                        }
                        orderMaster.setTransactionId(payOrderNotifyResult.getTransactionId());
                        orderMaster.setPayTime(DateUtil.dateFormat(DateUtil.sdfTimeMinuFmt, payOrderNotifyResult.getTimeEnd()));
                        orderService.paidByWeChatPay(orderMaster);
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

    /**
     * <pre>
     * 支付宝异步请求逻辑处理
     * @param conversionParams
     * @return
     * </pre>
     */
    @Override
    public String aliPayNotify(Map<String, String> conversionParams) {
        //签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================支付宝异步请求逻辑处理数据验签==================start");
            }
            //调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(conversionParams, StringUtils.trimToNull(this.properties.getAliPayPublicKey()), StringUtils.trimToNull(this.properties.getCharSet()), StringUtils.trimToNull(this.properties.getSignType()));
        } catch (AlipayApiException e) {
            log.error("支付宝异步请求逻辑处理数据验签失败！", e);
            return "fail";
        } catch (Exception e) {
            log.error("支付宝异步请求逻辑处理数据验签失败！", e);
            return "fail";
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end====================支付宝异步请求逻辑处理数据验签====================end");
            }
        }
        //对验签进行处理
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================对验签进行数据处理====================start");
            }
            if (signVerified) {
                AliPayNotifyResult result = checkNotifyParams(conversionParams);
                if (result != null) {
                    OrderMaster orderMaster = orderService.findOne(result.getOutTradeNo());
                    if (orderMaster == null) {
                        log.error("【支付宝APP支付】异步通知，订单不存在 orderId={}", result.getOutTradeNo());
                        throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
                    }
                    if (!MathUtil.equals(Double.valueOf(result.getTotalAmount()), orderMaster.getOrderAmount().doubleValue())) {
                        log.error("【支付宝APP支付】异步通知，订单金额不一致orderId={}，支付宝通知金额={}，商户订单系统记录金额={}", result.getOutTradeNo(), result.getTotalAmount(), orderMaster.getOrderAmount());
                        throw new Exception(ResultEnum.ALIPAY_NOTIFY_MONEY_VERIFY_ERROR.getMessage());
                    }
                    if (!StringUtils.trimToNull(this.properties.getSellerId()).equals(result.getSellerId())) {
                        log.error("【支付宝APP支付】异步通知，卖家支付宝用户号不一致。异步通知卖家支付宝用户号={}，系统卖家支付宝用户号={}", result.getSellerId(), StringUtils.trimToNull(this.properties.getSellerId()));
                        throw new Exception(ResultEnum.ALIPAY_NOTIFY_PID_VERIFY_ERROR.getMessage());
                    }
                    if (!StringUtils.trimToNull(this.properties.getAppId()).equals(result.getAppId())) {
                        log.error("【支付宝APP支付】异步通知，支付宝分配给开发者的应用Id不一致。异步通知支付宝分配给开发者的应用Id={}，系统支付宝分配给开发者的应用Id={}", result.getAppId(), StringUtils.trimToNull(this.properties.getAppId()));
                        throw new Exception(ResultEnum.ALIPAY_NOTIFY_PID_VERIFY_ERROR.getMessage());
                    }
                    orderMaster.setTransactionId(result.getTradeNo());
                    orderMaster.setPayTime(DateUtil.dateFormat(DateUtil.sdfTimeFmt, result.getGmtPayment()));
                    orderMaster.setPayType(PayTypeEnum.PAY_WITH_ALIPAY.getCode());//支付方式，支付宝
                    switch (result.getTradeStatus()) {
                        case "TRADE_FINISHED":// 交易结束并不可退款
                            orderMaster.setOrderStatus(OrderStatusEnum.FAIL.getCode());//3
                            orderMaster.setPayStatus(PayStatusEnum.FAIL.getCode());//3
                            break;
                        case "TRADE_CLOSED":// 未付款交易超时关闭或支付完成后全额退款
                            orderMaster.setOrderStatus(OrderStatusEnum.CLOSE.getCode());//2
                            orderMaster.setPayStatus(PayStatusEnum.CLOSE.getCode());//2
                            break;
                        case "TRADE_SUCCESS":// 交易支付成功
                            orderMaster.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());//1
                            orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());//1
                            break;
                        case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());//0
                            orderMaster.setPayStatus(PayStatusEnum.NEW.getCode());//0
                            break;
                        default:
                            break;
                    }
                   OrderMaster orderInfoUpdateResult = orderService.paidByAliPay(orderMaster);
                    if ("TRADE_SUCCESS".equals(result.getTradeStatus())) {
                    	if (orderInfoUpdateResult != null) {
							return "success";
						} else {
							return "fail";
						}
					}
                }
            } else {
                log.error("调用SDK验证签名不通过");
                return "fail";
            }
            return "fail";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "fail";
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================对验签进行数据处理======================end");
            }
        }
    }

    @Override
    public WeChatPayQueryResult queryWeChatPayResult(String orderId) {
        WeChatPayQueryResult queryResult = new WeChatPayQueryResult();
        WxPayOrderQueryResult result = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================查询微信支付订单结果====================start");
            }
            result = wxPayService.queryOrder(null, orderId);
            if (result != null) {
                BeanUtils.copyProperties(result, queryResult);
                queryResult.setResultCode("SUCCESS");
                queryResult.setReturnCode("SUCCESS");
                queryResult.setReturnMsg("查询微信支付订单结果成功。");
                queryResult.setErrCode("SUCCESS");
                queryResult.setErrCodeDes("查询微信支付订单结果成功。");
                return queryResult;
            }
        } catch (WxPayException e) {
            BeanUtils.copyProperties(result, queryResult);
        } catch (Exception e) {
            log.error("查询微信支付订单结果出现错误", e);
            queryResult.setResultCode("FAIL");
            queryResult.setReturnCode("FAIL");
            queryResult.setReturnMsg("查询微信支付订单结果失败。");
            queryResult.setErrCode("FAIL");
            queryResult.setErrCodeDes("查询微信支付订单结果失败。");
            return queryResult;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================查询微信支付订单结果======================end");
            }
        }
        return queryResult;
    }

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderId
     * </pre>
     */
    @Override
    public AliPayQueryResult queryAliPayResult(String orderId) {
        AliPayQueryResult queryResult = new AliPayQueryResult();
        try {

        } catch (Exception e) {

        } finally {

        }
        return queryResult;
    }

    public OrderMaster checkOrder(String orderId) throws Exception {
        OrderMaster orderInfo = orderService.findOne(orderId);
        if (orderInfo == null) {
            log.error("【检查支付的订单信息】 订单信息不存在，订单id={}", orderId);
            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
        }
        if (orderInfo.getOrderStatus() != OrderStatusEnum.NEW.getCode() && 
        		orderInfo.getOrderStatus() != OrderStatusEnum.EXCHANG_ING.getCode() && 
        		orderInfo.getOrderStatus() != OrderStatusEnum.WITHDRAW_ING.getCode()) {
            log.error("【检查支付的订单信息】 订单状态不正确，订单id={}，订单状态={}", orderId, orderInfo.getOrderStatusEnum().getMessage());
            throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
        }
        if (orderInfo.getPayStatus() != PayStatusEnum.NEW.getCode() &&
            orderInfo.getPayStatus() != PayStatusEnum.EXCHANG_ING.getCode() && 
            orderInfo.getPayStatus() != PayStatusEnum.WITHDRAW_ING.getCode()) {
            log.error("【检查支付的订单信息】 订单支付状态不正确，订单id={}，订单支付状态={}", orderId, orderInfo.getPayStatusEnum().getMessage());
            throw new Exception(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
        }
        if (orderInfo.getPayType() != PayTypeEnum.PAY_WITH_ALIPAY.getCode() &&
            orderInfo.getPayType() != PayTypeEnum.PAY_WITH_WECHAT.getCode() &&
            orderInfo.getPayType() != PayTypeEnum.PAY_WITH_BALANCE.getCode()) {
            log.error("【检查支付的订单信息】 订单支付方式不正确，订单id={}，订单支付方式={}", orderId, orderInfo.getPayTypeEnum().getMessage());
            throw new Exception(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
        }
        return orderInfo;
    }

    private AliPayNotifyResult checkNotifyParams(Map<String, String> conversionParams) {
        AliPayNotifyResult result = new AliPayNotifyResult();
        if (conversionParams != null) {
            String appId_key = "app_id", appId_value;//支付宝分配给开发者的应用Id
            String notifyTime_key = "notify_time", notifyTime_value;//通知时间:yyyy-MM-dd HH:mm:ss
            String gmtCreate_key = "gmt_create", gmtCreate_value;//交易创建时间:yyyy-MM-dd HH:mm:ss
            String gmtPayment_key = "gmt_payment", gmtPayment_value;//交易付款时间
            String gmtRefund_key = "gmt_refund", gmtRefund_value;//交易退款时间
            String gmtClose_key = "gmt_close", gmtClose_value;//交易结束时间
            String tradeNo_key = "trade_no", tradeNo_value;//支付宝的交易号
            String outTradeNo_key = "out_trade_no", outTradeNo_value;//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
            String outBizNo_key = "out_biz_no", outBizNo_value;//商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
            String buyerLogonId_key = "buyer_logon_id", buyerLogonId_value;//买家支付宝账号
            String sellerId_key = "seller_id", sellerId_value;//卖家支付宝用户号
            String sellerEmail_key = "seller_email", sellerEmail_value;//卖家支付宝账号
            String totalAmount_key = "total_amount", totalAmount_value;//订单金额:本次交易支付的订单金额，单位为人民币（元）
            String receiptAmount_key = "receipt_amount", receiptAmount_value;//实收金额:商家在交易中实际收到的款项，单位为人民币（元）
            String invoiceAmount_key = "invoice_amount", invoiceAmount_value;//开票金额:用户在交易中支付的可开发票的金额
            String buyerPayAmount_key = "buyer_pay_amount", buyerPayAmount_value;//付款金额:用户在交易中支付的金额
            String tradeStatus_key = "trade_status", tradeStatus_value;//交易状态
            if (conversionParams.containsKey(appId_key)) {
                appId_value = conversionParams.get(appId_key);
                result.setAppId(appId_value);
            }
            if (conversionParams.containsKey(notifyTime_key)) {
                notifyTime_value = conversionParams.get(notifyTime_key);
                result.setNotifyTime(notifyTime_value);
            }
            if (conversionParams.containsKey(gmtCreate_key)) {
                gmtCreate_value = conversionParams.get(gmtCreate_key);
                result.setGmtCreate(gmtCreate_value);
            }
            if (conversionParams.containsKey(gmtPayment_key)) {
                gmtPayment_value = conversionParams.get(gmtPayment_key);
                result.setGmtPayment(gmtPayment_value);
            }
            if (conversionParams.containsKey(gmtRefund_key)) {
                gmtRefund_value = conversionParams.get(gmtRefund_key);
                result.setGmtRefund(gmtRefund_value);
            }
            if (conversionParams.containsKey(gmtClose_key)) {
                gmtClose_value = conversionParams.get(gmtClose_key);
                result.setGmtClose(gmtClose_value);
            }
            if (conversionParams.containsKey(tradeNo_key)) {
                tradeNo_value = conversionParams.get(tradeNo_key);
                result.setTradeNo(tradeNo_value);
            }
            if (conversionParams.containsKey(outTradeNo_key)) {
                outTradeNo_value = conversionParams.get(outTradeNo_key);
                result.setOutTradeNo(outTradeNo_value);
            }
            if (conversionParams.containsKey(outBizNo_key)) {
                outBizNo_value = conversionParams.get(outBizNo_key);
                result.setOutBizNo(outBizNo_value);
            }
            if (conversionParams.containsKey(buyerLogonId_key)) {
                buyerLogonId_value = conversionParams.get(buyerLogonId_key);
                result.setBuyerLogonId(buyerLogonId_value);
            }
            if (conversionParams.containsKey(sellerId_key)) {
                sellerId_value = conversionParams.get(sellerId_key);
                result.setSellerId(sellerId_value);
            }
            if (conversionParams.containsKey(sellerEmail_key)) {
                sellerEmail_value = conversionParams.get(sellerEmail_key);
                result.setSellerEmail(sellerEmail_value);
            }
            if (conversionParams.containsKey(totalAmount_key)) {
                totalAmount_value = conversionParams.get(totalAmount_key);
                result.setTotalAmount(totalAmount_value);
            }
            if (conversionParams.containsKey(receiptAmount_key)) {
                receiptAmount_value = conversionParams.get(receiptAmount_key);
                result.setReceiptAmount(receiptAmount_value);
            }
            if (conversionParams.containsKey(invoiceAmount_key)) {
                invoiceAmount_value = conversionParams.get(invoiceAmount_key);
                result.setInvoiceAmount(invoiceAmount_value);
            }
            if (conversionParams.containsKey(buyerPayAmount_key)) {
                buyerPayAmount_value = conversionParams.get(buyerPayAmount_key);
                result.setBuyerPayAmount(buyerPayAmount_value);
            }
            if (conversionParams.containsKey(tradeStatus_key)) {
                tradeStatus_value = conversionParams.get(tradeStatus_key);
                result.setTradeStatus(tradeStatus_value);
            }
        }
        return result;
    }
}
