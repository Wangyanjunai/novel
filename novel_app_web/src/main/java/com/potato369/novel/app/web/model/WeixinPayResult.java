package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName WeixinPayResult
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:49
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class WeixinPayResult {
	
	/**
	 * <pre>
	 * @JsonProperty returnCode：返回状态码，SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断。
	 * </pre>
	 */
	@JsonProperty(value = "return_code")
	private String returnCode;
	
	/**
	 * <pre>
	 * @JsonProperty returnMsg：返回信息，如非空，为错误原因，签名失败，参数格式校验错误。
	 * </pre>
	 */
	@JsonProperty(value = "return_msg")
	private String returnMsg;

    /** 以下字段仅在微信APP支付返回。*/
	/**
	 * <pre>
	 * @JsonProperty appid：应用APPID，调用接口提交的应用ID。
	 * </pre>
	 */
	@JsonProperty(value = "appid")
    private String appid;
	
	/**
	 * <pre>
	 * @JsonProperty mchId：商户号，调用接口提交的商户号。
	 * </pre>
	 */
	@JsonProperty(value = "mch_id")
	private String mchId;
	
	/**
	 * <pre>
	 * @JsonProperty deviceInfo：设备号，调用接口提交的终端设备号。
	 * </pre>
	 */
	@JsonProperty(value = "device_info")
	private String deviceInfo;

	/**
	 * <pre>
	 * @JsonProperty nonceStr：随机字符串，微信返回的随机字符串。
	 * </pre>
	 */
	@JsonProperty(value = "nonce_str")
    private String nonceStr;

	/**
	 * <pre>
	 * @JsonProperty sign：签名，微信返回的签名，详见签名算法。
	 * </pre>
	 */
    @JsonProperty("sign")
    private String sign;
    
    /**
	 * <pre>
	 * @JsonProperty resultCode：业务结果，微信返回的业务结果，SUCCESS/FAIL。
	 * </pre>
	 */
    @JsonProperty("result_code")
    private String resultCode;

    /**
	 * <pre>
	 * @JsonProperty errCode：错误代码，详细参见第6节错误列表。
	 * </pre>
	 */
    @JsonProperty("err_code")
    private String errCode;
    
    /**
	 * <pre>
	 * @JsonProperty errCodeDes：错误代码描述，错误返回的信息描述。
	 * </pre>
	 */
    @JsonProperty("err_code_des")
    private String errCodeDes;
    
    /**
	 * <pre>
	 * @JsonProperty tradeType：交易类型，调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，详细说明见参数规定。
	 * </pre>
	 */
    @JsonProperty("trade_type")
    private String tradeType;

    /**
	 * <pre>
	 * @JsonProperty prepayId：预支付交易会话标识，微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时。
	 * </pre>
	 */
    @JsonProperty("prepay_id")
    private String prepayId;
}
