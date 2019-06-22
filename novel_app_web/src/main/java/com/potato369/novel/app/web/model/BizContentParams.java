package com.potato369.novel.app.web.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName BizContentParams
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-06-22 22:49
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BizContentParams {

    /**
     * <pre>
     * @JsonProperty outTradeNo：商户订单号；说明：订单支付时传入的商户订单号，和支付宝交易号不能同时为空。 trade_no，out_trade_no如果同时存在优先取trade_no。
     * </pre>
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * <pre>
     * @JsonProperty tradeNo：支付宝交易号；说明：支付宝交易号，和商户订单号不能同时为空。
     * </pre>
     */
    @JSONField(name = "trade_no")
    private String tradeNo;

    /**
     * <pre>
     * @JsonProperty orgPid：银行间联模式下有用，其它场景请不要使用； 双联通过该参数指定需要查询的交易所属收单机构的pid。
     * </pre>
     */
    @JSONField(name = "org_pid")
    private String orgPid;

    /**
     * <pre>
     * @JsonProperty queryOptions：查询选项参数。
     * </pre>
     */
    @JSONField(name = "query_options")
    private String queryOptions;
}
