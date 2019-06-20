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
import com.potato369.novel.app.web.model.AliPayQueryResult;
import com.potato369.novel.app.web.model.AliPayResult;
import com.potato369.novel.app.web.model.WeChatPayQueryResult;
import com.potato369.novel.app.web.model.WeChatPayResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.app.web.utils.MathUtil;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.PayTypeEnum;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.ProductService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.WwwUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
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
     * @return
     * </pre>
     */
    @Override
    public void balancePay(String orderId) {

    }

    /**
     * <pre>
     * 微信APP支付异步通知结果
     * @param notifyData
     * @return
     * </pre>
     */
    @Override
    public WxPayOrderNotifyResult notify(String notifyData) {
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
                        orderService.paid(orderMaster);
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
    public void refund(String orderId) {}

    /**
     * <pre>
     * 关闭超时未支付的订单
     * @param orderId
     * </pre>
     */
    @Override
    public void close(String orderId) {}

    /**
     * 支付宝异步请求逻辑处理
     *
     * @param conversionParams
     * @return
     */
    @Override
    public String notify1(Map<String, String> conversionParams) {
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
        } catch (Exception e) {
            log.error("支付宝异步请求逻辑处理数据验签失败！", e);
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
                if (conversionParams != null) {
                    String trade_status_key = "trade_status";
                    String trade_status_value = null;
                    String out_trade_no_key = "out_trade_no";
                    String out_trade_no_value = null;
                    String trade_no_key = "trade_no";
                    String trade_no_value = null;
                    String gmt_payment_key = "gmt_payment";
                    String gmt_payment_value = null;
                    String buyer_pay_amount_key = "buyer_pay_amount";
                    String buyer_pay_amount_value = null;
                    if (conversionParams.containsKey(trade_status_key)) {
                        trade_status_value = conversionParams.get(trade_status_key);
                    }
                    if (conversionParams.containsKey(out_trade_no_key)) {
                        out_trade_no_value = conversionParams.get(out_trade_no_key);
                    }
                    if (conversionParams.containsKey(trade_no_key)) {
                        trade_no_value = conversionParams.get(trade_no_key);
                    }
                    if (conversionParams.containsKey(gmt_payment_key)) {
                        gmt_payment_value = conversionParams.get(gmt_payment_key);
                    }
                    if (conversionParams.containsKey(buyer_pay_amount_key)) {
                        buyer_pay_amount_value = conversionParams.get(buyer_pay_amount_key);
                    }
                    if ("TRADE_SUCCESS".equals(trade_status_value)) {
                        OrderMaster orderMaster = orderService.findOne(out_trade_no_value);
                        if (orderMaster == null) {
                            log.error("【支付宝APP支付】异步通知，订单不存在 orderId={}", out_trade_no_value);
                            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
                        }
                        if (!MathUtil.equals(Double.valueOf(buyer_pay_amount_value), orderMaster.getOrderAmount().doubleValue())) {
                            log.error("【支付宝APP支付】异步通知，订单金额不一致 orderId={}，支付宝通知金额={}，商户订单系统记录金额={}", out_trade_no_value, buyer_pay_amount_value, orderMaster.getOrderAmount());
                            throw new Exception(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR.getMessage());
                        }
                        orderMaster.setTransactionId(trade_no_value);
                        orderMaster.setPayTime(DateUtil.dateFormat(DateUtil.sdfTimeFmt, gmt_payment_value));
                        orderService.paid(orderMaster);
                    }
                }
                //验签通过，获取需要保存的数据 https://blog.csdn.net/ouyzc/article/details/79551714
                String appId = conversionParams.get("app_id");//支付宝分配给开发者的应用Id
                String notifyTime = conversionParams.get("notify_time");//通知时间:yyyy-MM-dd HH:mm:ss
                String gmtCreate = conversionParams.get("gmt_create");//交易创建时间:yyyy-MM-dd HH:mm:ss
                String gmtPayment = conversionParams.get("gmt_payment");//交易付款时间
                String gmtRefund = conversionParams.get("gmt_refund");//交易退款时间
                String gmtClose = conversionParams.get("gmt_close");//交易结束时间
                String tradeNo = conversionParams.get("trade_no");//支付宝的交易号
                String outTradeNo = conversionParams.get("out_trade_no");//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
                String outBizNo = conversionParams.get("out_biz_no");//商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
                String buyerLogonId = conversionParams.get("buyer_logon_id");//买家支付宝账号
                String sellerId = conversionParams.get("seller_id");//卖家支付宝用户号
                String sellerEmail = conversionParams.get("seller_email");//卖家支付宝账号
                String totalAmount = conversionParams.get("total_amount");//订单金额:本次交易支付的订单金额，单位为人民币（元）
                String receiptAmount = conversionParams.get("receipt_amount");//实收金额:商家在交易中实际收到的款项，单位为元
                String invoiceAmount = conversionParams.get("invoice_amount");//开票金额:用户在交易中支付的可开发票的金额
                String buyerPayAmount = conversionParams.get("buyer_pay_amount");//付款金额:用户在交易中支付的金额
                String tradeStatus = conversionParams.get("trade_status");// 获取交易状态
                //支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）
                AlipaymentOrder alipaymentOrder = this.selectByOutTradeNo(outTradeNo);
                if (alipaymentOrder != null && totalAmount.equals(alipaymentOrder.getTotalAmount().toString()) && AlipayConfig.APPID.equals(appId)) {
                    //修改数据库支付宝订单表(因为要保存每次支付宝返回的信息到数据库里，以便以后查证)
                    alipaymentOrder.setNotifyTime(dateFormat(notifyTime));
                    alipaymentOrder.setGmtCreate(dateFormat(gmtCreate));
                    alipaymentOrder.setGmtPayment(dateFormat(gmtPayment));
                    alipaymentOrder.setGmtRefund(dateFormat(gmtRefund));
                    alipaymentOrder.setGmtClose(dateFormat(gmtClose));
                    alipaymentOrder.setTradeNo(tradeNo);
                    alipaymentOrder.setOutBizNo(outBizNo);
                    alipaymentOrder.setBuyerLogonId(buyerLogonId);
                    alipaymentOrder.setSellerId(sellerId);
                    alipaymentOrder.setSellerEmail(sellerEmail);
                    alipaymentOrder.setTotalAmount(Double.parseDouble(totalAmount));
                    alipaymentOrder.setReceiptAmount(Double.parseDouble(receiptAmount));
                    alipaymentOrder.setInvoiceAmount(Double.parseDouble(invoiceAmount));
                    alipaymentOrder.setBuyerPayAmount(Double.parseDouble(buyerPayAmount));
                    switch (tradeStatus) {// 判断交易结果
                        case "TRADE_FINISHED": // 交易结束并不可退款
                            alipaymentOrder.setTradeStatus((byte) 3);
                            break;
                        case "TRADE_SUCCESS": // 交易支付成功
                            alipaymentOrder.setTradeStatus((byte) 2);
                            break;
                        case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                            alipaymentOrder.setTradeStatus((byte) 1);
                            break;
                        case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                            alipaymentOrder.setTradeStatus((byte) 0);
                            break;
                        default:
                            break;
                    }
                    int returnResult = this.updateByPrimaryKey(alipaymentOrder);    //更新交易表中状态

                    if (tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功
                        if (returnResult > 0) {
                            return "success";
                        } else {
                            return "fail";
                        }
                    } else {
                        return "fail";
                    }

                } else {
                    logger.info("==================支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）,不一致！返回fail");
                    return "fail";
                }

            } else {  //验签不通过
                logger.info("==================验签不通过 ！");
                return "fail";
            }
            if (signVerified) {
                if (conversionParams != null) {
                    String trade_status_key = "trade_status";
                    String trade_status_value = null;
                    String out_trade_no_key = "out_trade_no";
                    String out_trade_no_value = null;
                    String trade_no_key = "trade_no";
                    String trade_no_value = null;
                    String gmt_payment_key = "gmt_payment";
                    String gmt_payment_value = null;
                    String buyer_pay_amount_key = "buyer_pay_amount";
                    String buyer_pay_amount_value = null;
                    if (conversionParams.containsKey(trade_status_key)) {
                        trade_status_value = conversionParams.get(trade_status_key);
                    }
                    if (conversionParams.containsKey(out_trade_no_key)) {
                        out_trade_no_value = conversionParams.get(out_trade_no_key);
                    }
                    if (conversionParams.containsKey(trade_no_key)) {
                        trade_no_value = conversionParams.get(trade_no_key);
                    }
                    if (conversionParams.containsKey(gmt_payment_key)) {
                        gmt_payment_value = conversionParams.get(gmt_payment_key);
                    }
                    if (conversionParams.containsKey(buyer_pay_amount_key)) {
                        buyer_pay_amount_value = conversionParams.get(buyer_pay_amount_key);
                    }
                    if ("TRADE_SUCCESS".equals(trade_status_value)) {
                        OrderMaster orderMaster = orderService.findOne(out_trade_no_value);
                        if (orderMaster == null) {
                            log.error("【支付宝APP支付】异步通知，订单不存在 orderId={}", out_trade_no_value);
                            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
                        }
                        if (!MathUtil.equals(Double.valueOf(buyer_pay_amount_value), orderMaster.getOrderAmount().doubleValue())) {
                            log.error("【支付宝APP支付】异步通知，订单金额不一致 orderId={}，支付宝通知金额={}，商户订单系统记录金额={}", out_trade_no_value, buyer_pay_amount_value, orderMaster.getOrderAmount());
                            throw new Exception(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR.getMessage());
                        }
                        orderMaster.setTransactionId(trade_no_value);
                        orderMaster.setPayTime(DateUtil.dateFormat(DateUtil.sdfTimeFmt, gmt_payment_value));
                        orderService.paid(orderMaster);
                    }
                }
            } else {
                log.error("调用SDK验证签名不通过");
                return "fail";
            }
            return "fail";
        } catch (Exception e) {
            log.error("对验签进行数据处理出现错误", e);
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
        if (orderInfo.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
            log.error("【检查支付的订单信息】 订单状态不正确，订单id={}，订单状态={}", orderId, orderInfo.getOrderStatusEnum().getMessage());
            throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
        }
        if (orderInfo.getPayStatus() != PayStatusEnum.WAITING.getCode()) {
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
}
