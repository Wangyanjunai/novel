package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName Result
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/20 10:48
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Result {

    /**
     * <pre>
     * @JsonProperty returnCode：返回状态码，PAY_SUCCESS/PAY_FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断。
     * </pre>
     */
    @JsonProperty(value = "return_code")
    public String returnCode;

    /**
     * <pre>
     * @JsonProperty returnMsg：返回信息，如非空，为错误原因，签名失败，参数格式校验错误。
     * </pre>
     */
    @JsonProperty(value = "return_msg")
    public String returnMsg;

    /**
     * <pre>
     * @JsonProperty resultCode：业务结果，微信返回的业务结果，PAY_SUCCESS/PAY_FAIL。
     * </pre>
     */
    @JsonProperty(value = "result_code")
    public String resultCode;

    /**
     * <pre>
     * @JsonProperty errCode：错误代码，详细参见第6节错误列表。
     * </pre>
     */
    @JsonProperty(value = "err_code")
    public String errCode;

    /**
     * <pre>
     * @JsonProperty errCodeDes：错误代码描述，错误返回的信息描述。
     * </pre>
     */
    @JsonProperty(value = "err_code_des")
    public String errCodeDes;
}
