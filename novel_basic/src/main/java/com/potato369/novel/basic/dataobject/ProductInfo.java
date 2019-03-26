package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName ProductInfo
 * @Desc 金币产品信息实体对象
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:16
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "ProductInfo")
@NoArgsConstructor
@Table(name = "product_info")
public class ProductInfo implements Serializable {

    /**
     * @serialField serialVersionUID: 序列号
     */
    private static final long serialVersionUID = -8639503600980373589L;

    /**
     * @serialField productId：书币产品id，主键
     */
    @Id
    @Column(name = "product_id", nullable = false, length = 32)
    private String productId;

    /**
     * @serialField productName：书币产品名称
     */
    @Column(name = "product_name", nullable = false, length = 64)
    private String productName;

    /**
     * @serialField productType：产品类型，0-书币，1-会员；默认0-书币
     */
    @Column(name = "product_type", nullable = false, length = 4)
    private Integer productType;

    /**
     * @serialField productPrice：书币产品单价
     */
    @Column(name = "product_price", nullable = false, length = 10)
    private BigDecimal productPrice;

    /**
     * @serialField productQuantity：产品数量
     */
    @Column(name = "product_quantity", nullable = true, length = 8)
    private BigDecimal productQuantity;

    /**
     * @serialField productAmount：产品总价
     */
    @Column(name = "product_amount", nullable = true, length = 10)
    private BigDecimal productAmount;

    /**
     * @serialField productGiveQuantity：赠送数量
     */
    @Column(name = "product_give_quantity", nullable = true, length = 8)
    private BigDecimal productGiveQuantity;

    /**
     * @serialField productRank：排行
     */
    @Column(name = "product_rank", nullable = true, length = 64)
    private String productRank;

    /**
     * @serialField productDescription：书币产品描述
     */
    @Column(name = "product_description", nullable = true, length = 1024)
    private String productDescription;

    /**
     * @serialField createTime：创建时间
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * @serialField updateTime：更新时间
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;
}
