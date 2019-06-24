package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName WeChatPayQueryResult
 * @Desc 微信支付结果查询
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/20 10:14
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WeChatPayQueryResult extends Result{

	/**
     * <pre>
     * @JsonProperty openid：微信支付平台账号id。
     * </pre>
     */
    @JsonProperty(value = "openid")
    private String openid;

    /**
     * <pre>
     * @JsonProperty tradeType：交易类型。
     * </pre>
     */
    @JsonProperty(value = "trade_type")
    private String tradeType;

    /**
     * <pre>
     * @JsonProperty tradeState：交易状态。
     * </pre>
     */
    @JsonProperty(value = "trade_state")
    private String tradeState;

    /**
     * <pre>
     * @JsonProperty totalFee：交易总金额（元）。
     * </pre>
     */
    @JsonProperty(value = "total_fee")
    private Integer totalFee;

    /**
     * <pre>
     * @JsonProperty tradeStateDesc：交易状态描述。
     * </pre>
     */
    @JsonProperty(value = "trade_state_desc")
    private String tradeStateDesc;
}
