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
 * @ClassName OrderMessageVO
 * @Desc 显示订单信息VO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/21 17:55
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderMessageVO {

    @JsonProperty(value = "user_name")
    private String userName;//用户名

    @JsonProperty(value = "order_type")
    private String orderType;//订单类型

    @JsonProperty(value = "product_name")
    private String productName;//商品名称

    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;//累计总额
}
