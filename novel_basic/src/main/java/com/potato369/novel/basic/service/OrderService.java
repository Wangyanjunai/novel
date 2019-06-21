package com.potato369.novel.basic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.potato369.novel.basic.dataobject.OrderMaster;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName OrderService
 * @Desc 订单信息业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:58
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface OrderService {

    /**
     * <pre>
     * 创建订单
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster save(OrderMaster orderMaster) throws Exception;

    /**
     * <pre>
     * 根据订单id主键查询单个订单信息
     * @param orderId
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster findOne(String orderId) throws Exception;
    
    /**
     * <pre>
     * 查询所有的订单列表
     * @param orderId
     * @return OrderMaster.class
     * </pre>
     */
    List<OrderMaster> findAll() throws Exception;

    /**
     * <pre>
     * 查找未支付且订单状态是等待支付的订单信息列表
     * @param payStatus
     * @param orderStatus
     * @return List<OrderMaster>.class
     * </pre>
     */
    List<OrderMaster> findTimeOutUnpaid(Integer payStatus, Integer orderStatus) throws Exception;

    /**
     * <pre>
     * 分页查询某个用户对应buyerOpenid的订单列表
     * @param buyerOpenid
     * @param pageable
     * @return Page<OrderMaster>.class
     * </pre>
     */
    Page<OrderMaster> findAll(String buyerOpenid, Pageable pageable) throws Exception;

    /**
     * <pre>
     * 取消订单
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster cancel(OrderMaster orderMaster) throws Exception;

    /**
     * <pre>
     * 完结订单
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster finish(OrderMaster orderMaster) throws Exception;

    /**
     * <pre>
     * 回调修改微信支付订单信息
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster paidByWeChatPay(OrderMaster orderMaster) throws Exception;
    
    /**
     * <pre>
     * 回调修改支付宝支付订单信息
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    OrderMaster paidByAliPay(OrderMaster orderMaster) throws Exception;

    /**
     * <pre>
     * 分页查询订单列表
     * @param pageable
     * @return Page<OrderMaster>.class
     * </pre>
     */
    Page<OrderMaster> findAll(Pageable pageable) throws Exception;

    /**
     * <pre>
     * 根据订单状态，支付状态分页查询订单列表
     * @param pageable
     * @return Page<OrderMaster>.class
     * </pre>
     */
    Page<OrderMaster> findByOrderStatusAndPayStatus(List<Integer> orderStatusList, List<Integer> payStatusList, Pageable pageable) throws Exception;

    /**
     * <pre>
     * 根据订单状态，支付状态，订单类型分页查询订单列表
     * @param pageable
     * @return Page<OrderMaster>.class
     * </pre>
     */
    Page<OrderMaster> findByOrderStatusAndPayStatusAndOrderType(List<Integer> orderStatusList, List<Integer> payStatusList, Integer orderType, Pageable pageable) throws Exception;
}
