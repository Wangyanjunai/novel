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
public class AliPayResult {
	
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
	
    /**
	 * <pre>
	 * @JsonProperty resultCode：业务结果，微信返回的业务结果，SUCCESS/FAIL。
	 * </pre>
	 */
    @JsonProperty(value = "result_code")
    private String resultCode;

    /**
	 * <pre>
	 * @JsonProperty errCode：错误代码，详细参见第6节错误列表。
	 * </pre>
	 */
    @JsonProperty(value = "err_code")
    private String errCode;
    
    /**
	 * <pre>
	 * @JsonProperty errCodeDes：错误代码描述，错误返回的信息描述。
	 * </pre>
	 */
    @JsonProperty(value = "err_code_des")
    private String errCodeDes;
    
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
}
