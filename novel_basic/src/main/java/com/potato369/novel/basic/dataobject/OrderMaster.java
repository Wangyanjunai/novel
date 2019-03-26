package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
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
 * @ClassName OrderMaster
 * @Desc 订单信息记录实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:37
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "OrderMaster")
@NoArgsConstructor
@Table(name = "order_master")
public class OrderMaster implements Serializable {

    /**
     * @serialField serialVersionUID：序列号
     */
    private static final long serialVersionUID = 3765541505739277089L;

    /**
     * @serialField orderId：订单id，主键
     */
    @Id
    @Column(name = "order_id", nullable = false, length = 32)
    private String orderId;

    /**
     * @serialField buyerName：买家名字
     */
    @Column(name = "buyer_name", nullable = false, length = 32)
    private String buyerName;

    /**
     * @serialField buyerAddress：买家地址
     */
    @Column(name = "buyer_address", nullable = false, length = 128)
    private String buyerAddress;

    /**
     * @serialField buyerOpenid：买家微信openId
     */
    @Column(name = "buyer_openid", nullable = false, length = 64)
    private String buyerOpenid;

    /**
     * @serialField orderAmount：订单总金额
     */
    @Column(name = "order_amount", nullable = false, length = 8)
    private BigDecimal orderAmount;

    /**
     * orderName：订单名称
     */
    @Column(name = "order_name", nullable = true, length = 64)
    private String orderName;

    /**
     * @serialField orderStatus：订单状态，0-新订单；1-已完结；2-已取消，“默认：0-新订单”
     */
    @Builder.Default
    @Column(name = "order_status", nullable = false, length = 3)
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * @serialField payStatus：订单支付状态，0-等待支付；1-支付成功，“默认：0-等待支付”
     */
    @Builder.Default
    @Column(name = "pay_status", nullable = false, length = 3)
    private Integer payStatus = PayStatusEnum.WAITING.getCode();

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
