package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * @ClassName: ProductInfoVO
 * @Function:
 * @Reason: 买家商品数据封装类
 * @Date: 2019年6月10日 下午4:19:21
 * @Desc:前端充值或者兑换的商品信息
 * @author 王艳军
 * @version 1.0
 * @since JDK 1.6
 * <pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductInfoVO {
    /**
     * <pre>
     * @JsonProperty productId：商品id，主键。
     * </pre>
     */
    @JsonProperty(value = "no")
    private String productId;

    /**
     * <pre>
     * @JsonProperty productName：商品名称。
     * </pre>
     */
    @JsonProperty(value = "title")
    private String productName;

    /**
     * <pre>
     * @JsonProperty dateValue：加对应的日期值。
     * </pre>
     */
    @JsonProperty(value = "value")
    private Integer dateValue;

    /**
     * <pre>
     * @JsonProperty productAmount：商品总价（元）。
     * </pre>
     */
    @JsonProperty(value = "amount")
    private BigDecimal productAmount;
}
