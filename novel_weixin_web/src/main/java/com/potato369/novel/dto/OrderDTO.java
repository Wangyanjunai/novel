package com.potato369.novel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.utils.EnumUtil;
import com.potato369.novel.utils.serializer.Date2LongSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.dto
 * @ClassName OrderDTO
 * @Desc 订单信息DTO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:59
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderDTO {

    /**
     * <pre>
     * @Field orderId：订单编号
     * </pre>
     */
    private String orderId;

    /**
     * <pre>
     * @Field orderName：订单名称
     * </pre>
     */
    private String orderName;

    /**
     * <pre>
     * @Field buyerName：买家名字
     * </pre>
     */
    private String buyerName;

    /**
     * <pre>
     * @Field buyerAddress：买家地址
     * </pre>
     */
    private String buyerAddress;

    /**
     * <pre>
     * @Field buyerOpenid：买家微信openid
     * </pre>
     */
    private String buyerOpenid;

    /**
     * <pre>
     * @Field orderAmount：订单总金额
     * </pre>
     */
    private BigDecimal orderAmount;

    /**
     * <pre>
     * @Field orderStatus：订单状态，默认是新下单
     * </pre>
     */
    private Integer orderStatus;

    /**
     * <pre>
     * @Field payStatus：支付状态，默认0为未支付
     * </pre>
     */
    private Integer payStatus;

    /**
     * <pre>
     * @Field createTime：创建时间
     * </pre>
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * <pre>
     * @Field updateTime：更新时间
     * </pre>
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @Transient
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

    /**
     * <pre>
     * @Field cip：买家微信客户端ip
     * </pre>
     */
    private String cip;

    /**
     * <pre>
     * @Field fingerPrint：浏览器指纹
     * </pre>
     */
    private String fingerPrint;

    /**
     * <pre>
     * @Field jsonSceneInfo：json格式场景信息字符串
     * </pre>
     */
    private String jsonSceneInfo;
}
