package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName WeChatPayResult
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:49
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WeChatPayResult extends Result{
    
	/**
	 * <pre>
	 * @JsonProperty appId：应用APPID，微信开放平台审核通过的应用APPID。
	 * </pre>
	 */
	@JsonProperty(value = "appid")
    private String appId;
	
    /**
	 * <pre>
	 * @JsonProperty partnerId：商户号，微信支付分配的商户号。
	 * </pre>
	 */
    @JsonProperty(value = "partnerid")
    private String partnerId;
    
    /**
	 * <pre>
	 * @JsonProperty prepayId：预支付交易会话ID，微信返回的支付交易会话ID。
	 * </pre>
	 */
    @JsonProperty(value = "prepayid")
    private String prepayId;
    
    /**
     * <pre>
     * 由于package为java保留关键字，因此改为packageValue. 前端使用时记得要更改为package
     * @JsonProperty packageValue：扩展字段，暂填写固定值Sign=WXPay
     * </pre>
     */
    @JsonProperty(value = "package")
    private String packageValue;

	/**
	 * <pre>
	 * @JsonProperty nonceStr：随机字符串，微信返回的随机字符串。
	 * </pre>
	 */
	@JsonProperty(value = "noncestr")
    private String nonceStr;
	
    /**
     * <pre>
     * @JsonProperty timeStamp：时间戳，时间戳，请见接口规则-参数规定。
     * </pre>
     */
    @JsonProperty(value = "timestamp")
    private String timeStamp;

	/**
	 * <pre>
	 * @JsonProperty sign：签名，微信返回的签名，详见签名算法。
	 * </pre>
	 */
    @JsonProperty(value = "sign")
    private String sign;

    /**
	 * <pre>
	 * @JsonProperty orderId：商户订单id。
	 * </pre>
	 */
    @JsonProperty(value = "out_trade_no")
    private String orderId;
}
