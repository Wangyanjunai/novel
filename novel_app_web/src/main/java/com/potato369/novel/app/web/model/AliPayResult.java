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
public class AliPayResult extends Result{
    
	/**
	 * <pre>
	 * @JsonProperty appId：应用APPID，支付宝开放平台审核通过的应用APPID。
	 * </pre>
	 */
	@JsonProperty(value = "appid")
    private String appId;
	
	/**
	 * <pre>
	 * @JsonProperty body：应用APPID，微信开放平台审核通过的应用APPID。
	 * </pre>
	 */
	@JsonProperty(value = "orderString")
    private String body;
	
	/**
	 * <pre>
	 * @JsonProperty orderId：商户订单id。
	 * </pre>
	 */
    @JsonProperty(value = "out_trade_no")
    private String orderId;
}
