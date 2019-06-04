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
import javax.persistence.Transient;
import java.io.Serializable;
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
@Data
@DynamicUpdate
@Entity(name = "OrderDetail")
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
	@Transient
    private static final long serialVersionUID = -2922952467636846483L;

    /**
     * <pre>
     * @serialField detailId：详情id，主键。
     * </pre>
     */
    @Id
    @Column(name = "detail_id", nullable = false, length = 32)
    private String detailId;

    /**
     * <pre>
     * @serialField orderId：订单id，外键。
     * </pre>
     */
    @Column(name = "order_id", nullable = false, length = 32)
    private String orderId;
    
    /**
     * <pre>
     * @serialField userId：用户mid。
     * </pre>
     */
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    /**
     * <pre>
     * @serialField productId：商品id。
     * </pre>
     */
    @Column(name = "product_id", nullable = false, length = 32)
    private String productId;

    /**
     * <pre>
     * @serialField buyerOpenid：买家openid。
     * </pre>
     */
    @Column(name = "buyer_openid", length = 64)
    private String buyerOpenid;

    /**
     * <pre>
     * @serialField payTime：支付时间。
     * </pre>
     */
    @Column(name = "pay_time", length = 64)
    private Date payTime;
    
    /**
     * <pre>
     * @serialField startTime：VIP开始时间。
     * </pre>
     */
    @Column(name = "start_time", length = 64)
    private Date startTime;
    
    /**
     * <pre>
     * @serialField endTime：VIP结束时间。
     * </pre>
     */
    @Column(name = "end_time", length = 64)
    private Date endTime;

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
