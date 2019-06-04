package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.ProductInfoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
     * @serialField productType：计算类型，0-按照天算，1-按照月算；默认：0-按照天算。
     */
    @Builder.Default
    @Column(name = "product_type", nullable = false, length = 1)
    private Integer productType = ProductInfoEnum.DAY.getCode();

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
}
