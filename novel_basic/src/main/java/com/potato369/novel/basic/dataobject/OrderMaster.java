package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.OrderTypeEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.PayTypeEnum;
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
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName OrderMaster
 * @Desc 订单信息记录实体。
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:37
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "OrderMaster")
@NoArgsConstructor
@Table(name = "order_master")
public class OrderMaster implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = 3765541505739277089L;

    /**
     * <pre>
     * @serialField orderId：订单id，主键。
     * </pre>
     */
    @Id
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
     * @serialField transactionId：订单支付交易流水号。
     * </pre>
     */
    @Column(name = "transaction_id", length = 64)
    private String transactionId;

    /**
     * <pre>
     * @serialField buyerName：买家名字。
     * </pre>
     */
    @Column(name = "buyer_name", length = 64)
    private String buyerName;

    /**
     * <pre>
     * @serialField buyerAddress：买家地址。
     * </pre>
     */
    @Column(name = "buyer_address", length = 128)
    private String buyerAddress;

    /**
     * <pre>
     * @serialField buyerOpenid：买家openid。
     * </pre>
     */
    @Column(name = "buyer_openid", length = 64)
    private String buyerOpenid;

    /**
     * <pre>
     * @serialField productId：商品id。
     * </pre>
     */
    @Column(name = "product_id", nullable = false, length = 32)
    private String productId;

    /**
     * <pre>
     * @serialField orderAmount：订单总金额，默认：0.00。
     * </pre>
     */
    @Builder.Default
    @Column(name = "order_amount", nullable = false, length = 10)
    private BigDecimal orderAmount = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField orderName：订单名称。
     * <pre>
     */
    @Column(name = "order_name", length = 64)
    private String orderName;

    /**
     * <pre>
     * @serialField orderType：订单产品类型，0-提现；1-兑换；2-充值，“默认：0-提现”。
     * </pre>
     */
    @Builder.Default
    @Column(name = "order_type", nullable = false, length = 1)
    private Integer orderType = OrderTypeEnum.WITHDRAW.getCode();

    /**
     * <pre>
     * @serialField orderStatus：订单状态，0-新订单；1-已完结；2-已取消，“默认：0-新订单”。
     * </pre>
     */
    @Builder.Default
    @Column(name = "order_status", nullable = false, length = 1)
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * <pre>
     * @serialField payStatus：订单支付状态，0-等待支付；1-支付成功；2-已经关闭，“默认：0-等待支付。”。
     * </pre>
     */
    @Builder.Default
    @Column(name = "pay_status", nullable = false, length = 1)
    private Integer payStatus = PayStatusEnum.NEW.getCode();

    /**
     * <pre>
     * @serialField payType：订单支付方式，1-支付宝；2-微信支付；3-余额支付，”默认：1-支付宝“。
     * </pre>
     */
    @Builder.Default
    @Column(name = "pay_type", nullable = false, length = 1)
    private Integer payType = PayTypeEnum.PAY_WITH_ALIPAY.getCode();

    /**
     * <pre>
     * @serialField payTime：支付时间。
     * </pre>
     */
    @Column(name = "pay_time", length = 64)
    private Date payTime;

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

    /**
     * <pre>
     * @serialField orderDetailList：订单详情列表。
     * </pre>
     */
    @Transient
    private List<OrderDetail> orderDetailList;

    @Transient
    public OrderTypeEnum getOrderTypeEnum() {
        return EnumUtil.getByCode(orderType, OrderTypeEnum.class);
    }

    @Transient
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @Transient
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

    @Transient
    public PayTypeEnum getPayTypeEnum() {
        return EnumUtil.getByCode(payType, PayTypeEnum.class);
    }
}
