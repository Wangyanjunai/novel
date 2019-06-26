package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName BalanceVO
 * @Desc 收益json数据封装实体。
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/11 15:23
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BalanceVO {

    /**
     * <pre>
     * @JsonProperty userId：用户mid。
     * </pre>
     */
    @JsonProperty(value = "userId")
    private String userId;

    /**
     * <pre>
     * @JsonProperty balanceAmount：总余额（0.00元）。
     * </pre>
     */
    @JsonProperty(value = "balance")
    private BigDecimal balanceAmount;

    /**
     * <pre>
     * @JsonProperty envelopeAmount：红包进度值。
     * </pre>
     */
    @JsonProperty(value = "envelope")
    private BigDecimal envelopeAmount;

    /**
     * <pre>
     * @JsonProperty yieldAmount：最近7天收益（0.00元）。
     * </pre>
     */
    @JsonProperty(value = "yield")
    private BigDecimal yieldAmount;


}
