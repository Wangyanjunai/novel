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

    @JsonProperty(value = "openid")
    private String openid;

    @JsonProperty(value = "trade_type")
    private String tradeType;

    @JsonProperty(value = "trade_state")
    private String tradeState;

    @JsonProperty(value = "total_fee")
    private Integer totalFee;

    @JsonProperty(value = "trade_state_desc")
    private String tradeStateDesc;
}
