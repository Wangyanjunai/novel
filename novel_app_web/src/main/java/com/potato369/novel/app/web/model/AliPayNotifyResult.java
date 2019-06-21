package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName AliPayNotifyResult
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/21 9:10
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AliPayNotifyResult extends Result {

    /**
     * <pre>
     * @JsonProperty appId：支付宝分配给开发者的应用Id。
     * </pre>
     */
    @JsonProperty(value = "app_id")
    private String appId;

    /**
     * <pre>
     * @JsonProperty notifyTime：通知时间:yyyy-MM-dd HH:mm:ss。
     * </pre>
     */
    @JsonProperty(value = "notify_time")
    private String notifyTime;

    /**
     * <pre>
     * @JsonProperty gmtCreate：交易创建时间:yyyy-MM-dd HH:mm:ss。
     * </pre>
     */
    @JsonProperty(value = "gmt_create")
    private String gmtCreate;

    /**
     * <pre>
     * @JsonProperty gmtPayment：交易付款时间。
     * </pre>
     */
    @JsonProperty(value = "gmt_payment")
    private String gmtPayment;

    /**
     * <pre>
     * @JsonProperty gmtRefund：交易退款时间。
     * </pre>
     */
    @JsonProperty(value = "gmt_refund")
    private String gmtRefund;

    /**
     * <pre>
     * @JsonProperty gmtClose：交易结束时间。
     * </pre>
     */
    @JsonProperty(value = "")
    private String gmtClose;

    /**
     * <pre>
     * @JsonProperty tradeNo：支付宝的交易号。
     * </pre>
     */
    @JsonProperty(value = "trade_no")
    private String tradeNo;

    /**
     * <pre>
     * @JsonProperty
     * </pre>
     */
    @JsonProperty(value = "out_trade_no")
    private String outTradeNo;//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）

    /**
     * <pre>
     * @JsonProperty outBizNo：商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
     * </pre>
     */
    @JsonProperty(value = "out_biz_no")
    private String outBizNo;

    /**
     * <pre>
     * @JsonProperty buyerLogonId：买家支付宝账号。
     * </pre>
     */
    @JsonProperty(value = "buyer_logon_id")
    private String buyerLogonId;

    /**
     * <pre>
     * @JsonProperty sellerId：卖家支付宝用户号。
     * </pre>
     */
    @JsonProperty(value = "seller_id")
    private String sellerId;

    /**
     * <pre>
     * @JsonProperty sellerEmail：卖家支付宝账号。
     * </pre>
     */
    @JsonProperty(value = "seller_email")
    private String sellerEmail;

    /**
     * <pre>
     * @JsonProperty totalAmount：订单金额:本次交易支付的订单金额，单位为人民币（元）。
     * </pre>
     */
    @JsonProperty(value = "total_amount")
    private String totalAmount;

    /**
     * <pre>
     * @JsonProperty receiptAmount：实收金额:商家在交易中实际收到的款项，单位为人民币（元）。
     * </pre>
     */
    @JsonProperty(value = "receipt_amount")
    private String receiptAmount;

    /**
     * <pre>
     * @JsonProperty invoiceAmount：开票金额:用户在交易中支付的可开发票的金额。
     * </pre>
     */
    @JsonProperty(value = "invoice_amount")
    private String invoiceAmount;

    /**
     * <pre>
     * @JsonProperty buyerPayAmount：付款金额:用户在交易中支付的金额。
     * </pre>
     */
    @JsonProperty(value = "buyer_pay_amount")
    private String buyerPayAmount;

    /**
     * <pre>
     * @JsonProperty tradeStatus：获取交易状态。
     * </pre>
     */
    @JsonProperty(value = "trade_status")
    private String tradeStatus;
}
