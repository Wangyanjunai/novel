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
 * @ClassName OrderDetail
 * @Desc 订单详情信息实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:01
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "OrderDetail")
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    /**
     * @serialField serialVersionUID：序列号
     */
    private static final long serialVersionUID = -2922952467636846483L;

    /**
     * @serialField detailId：详情id，主键
     */
    @Id
    @Column(name = "detail_id", nullable = false, length = 32)
    private String detailId;

    /**
     * @serialField orderId：订单id，外键
     */
    @Column(name = "order_id", nullable = false, length = 32)
    private String orderId;

    /**
     * @serialField productId：产品id，外键
     */
    @Column(name = "product_id", nullable = false, length = 32)
    private String productId;

    /**
     * @serialField buyerOpenid：买家微信openid，外键
     */
    @Column(name = "buyer_openid", nullable = false, length = 64)
    private String buyerOpenid;

    /**
     * @serialField productName：产品名称
     */
    @Column(name = "product_name", nullable = false, length = 64)
    private String productName;

    /**
     * @serialField productPrice：产品单价
     */
    @Column(name = "product_price", nullable = false, length = 8)
    private BigDecimal productPrice;

    /**
     * @serialField productQuantity：购买数量
     */
    @Column(name = "product_quantity", nullable = false, length = 11)
    private BigDecimal productQuantity;

    /**
     * @serialField productGiveQuantity：赠送数量
     */
    @Column(name = "product_give_quantity", nullable = false, length = 8)
    private BigDecimal productGiveQuantity;

    /**
     * @serialField productDescription：书币产品描述
     */
    @Column(name = "product_description", nullable = true, length = 1024)
    private String productDescription;

    /**
     * @serialField payTime：会员支付时间
     */
    @Column(name = "pay_time", nullable = true, length = 64)
    private Date payTime;

    /**
     * @serialField endTime：会员结束时间
     */
    @Column(name = "end_time", nullable = true, length = 64)
    private Date endTime;

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
