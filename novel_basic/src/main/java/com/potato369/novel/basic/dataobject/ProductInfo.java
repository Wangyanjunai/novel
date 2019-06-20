package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.ProductCalculateTypeEnum;
import com.potato369.novel.basic.enums.ProductTypeEnum;
import com.potato369.novel.basic.utils.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName ProductInfo
 * @Desc 商品信息记录实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:16
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "ProductInfo")
@NoArgsConstructor
@Table(name = "product_info")
public class ProductInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID: 序列号。
     * </pre>
     */
	@Transient
    private static final long serialVersionUID = -8639503600980373589L;

    /**
     * <pre>
     * @serialField productId：商品id，主键。
     * </pre>
     */
    @Id
    @Column(name = "product_id", nullable = false, length = 32)
    private String productId;

    /**
     * <pre>
     * @serialField productName：商品名称。
     * </pre>
     */
    @Column(name = "product_name", nullable = false, length = 64)
    private String productName;

    /**
     * @serialField productType：产品类型，0-充值，1-兑换；2-提现，默认：0-充值。
     */
    @Builder.Default
    @Column(name = "product_type", nullable = false, length = 1)
    private Integer productType = ProductTypeEnum.CHARGE.getCode();
    
    /**
     * @serialField productCalculateType：计算类型，0-按照天算，1-按照月算；默认：0-按照天算。
     */
    @Builder.Default
    @Column(name = "product_calculate_type", nullable = false, length = 1)
    private Integer productCalculateType = ProductCalculateTypeEnum.DAY.getCode();

    /**
     * @serialField productCode：商品代码。
     */
    @Column(name = "product_code", length = 64)
    private String productCode;

    /**
     * <pre>
     * @serialField productAmount：商品总价（元）。
     * </pre>
     */
    @Builder.Default
    @Column(name = "product_amount", length = 10)
    private BigDecimal productAmount = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField productDescription：商品描述。
     * </pre>
     */
    @Column(name = "product_description", length = 1024)
    private String productDescription;
    
    /**
     * <pre>
     * @serialField dateValue：加对应的日期值。
     * </pre>
     */
    @Column(name = "date_value", length = 2)
    private Integer dateValue;

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;

    @Transient
    public ProductCalculateTypeEnum getProductCalculateTypeEnum() {
        return EnumUtil.getByCode(productCalculateType, ProductCalculateTypeEnum.class);
    }

    @Transient
    public ProductTypeEnum getProductTypeEnum() {
        return EnumUtil.getByCode(productType, ProductTypeEnum.class);
    }
}
