package com.potato369.novel.app.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.potato369.novel.basic.dataobject.OrderDetail;
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
    @JsonProperty(value = "orderId")
    private String orderId;

    /**
     * <pre>
     * @Field orderName：订单名称
     * </pre>
     */
    @JsonProperty(value = "orderName")
    private String orderName;

    /**
     * <pre>
     * @Field buyerName：买家名字
     * </pre>
     */
    @JsonProperty(value = "buyerName")
    private String buyerName;

    /**
     * <pre>
     * @Field buyerAddress：买家地址
     * </pre>
     */
    @JsonProperty(value = "buyerAddress")
    private String buyerAddress;

    /**
     * <pre>
     * @Field buyerOpenid：买家微信openid
     * </pre>
     */
    @JsonProperty(value = "buyerOpenid")
    private String buyerOpenid;

    /**
     * <pre>
     * @Field orderAmount：订单总金额
     * </pre>
     */
    @JsonProperty(value = "orderAmount")
    private BigDecimal orderAmount;

    /**
     * <pre>
     * @Field orderStatus：订单状态，默认是新下单
     * </pre>
     */
    @JsonProperty(value = "orderStatus")
    private Integer orderStatus;

    /**
     * <pre>
     * @Field payStatus：支付状态，默认0为未支付
     * </pre>
     */
    @JsonProperty(value = "payStatus")
    private Integer payStatus;

    /**
     * <pre>
     * @Field createTime：创建时间
     * </pre>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * <pre>
     * @Field updateTime：更新时间
     * </pre>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Transient
    private List<OrderDetail> orderDetailList;
}
