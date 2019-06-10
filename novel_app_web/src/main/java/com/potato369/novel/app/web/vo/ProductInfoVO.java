package com.potato369.novel.app.web.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * 类名: ProductInfoVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年6月10日 下午4:19:21
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
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
	@JsonProperty(value = "id")
    private String productId;

    /**
     * <pre>
     * @JsonProperty productName：商品名称。
     * </pre>
     */
    @JsonProperty(value = "name")
    private String productName;

    /**
     * @JsonProperty productType：计算类型，0-按照天算，1-按照月算；默认：0-按照天算。
     */
    @JsonProperty(value = "typeName")
    private Integer productType;

    /**
     * <pre>
     * @JsonProperty productAmount：商品总价（元）。
     * </pre>
     */
    @JsonProperty(value = "amount")
    private BigDecimal productAmount;

    /**
     * <pre>
     * @JsonProperty productDescription：商品描述。
     * </pre>
     */
    @JsonProperty(value = "description")
    private String productDescription;
    
    /**
     * <pre>
     * @JsonProperty dateValue：加对应的日期值。
     * </pre>
     */
    @JsonProperty(value = "value")
    private Integer dateValue;

    /**
     * <pre>
     * @JsonProperty createTime：创建时间。
     * </pre>
     */
    @JsonProperty(value = "create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * <pre>
     * @JsonProperty updateTime：更新时间。
     * </pre>
     */
    @JsonProperty(value = "update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
