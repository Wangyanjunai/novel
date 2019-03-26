package com.potato369.novel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.potato369.novel.dto.OrderDTO;

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
     * @param orderDTO
     * @return OrderDTO
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    OrderDTO create(OrderDTO orderDTO);

    /**
     * <pre>
     * 查询单个订单
     * @param orderId
     * @return OrderDTO
     * </pre>
     */
    OrderDTO findOne(String orderId);

    /**
     * <pre>
     * 查找未支付且订单状态是等待支付的订单信息列表
     * @param payStatus
     * @param orderStatus
     * @return
     * </pre>
     */
    List<OrderDTO> findTimeOutUnpaid(Integer payStatus, Integer orderStatus);

    /**
     * <pre>
     * 分页查询某个用户对应buyerOpenid的订单列表
     * @param buyerOpenid
     * @param pageable
     * @return Page<OrderDTO>
     * </pre>
     */
    Page<OrderDTO> findAll(String buyerOpenid, Pageable pageable);

    /**
     * <pre>
     * 取消订单
     * @param orderDTO
     * @return OrderDTO
     * </pre>
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * <pre>
     * 完结订单
     * @param orderDTO
     * @return orderDTO
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * <pre>
     * 支付订单
     * @param orderDTO
     * @return OrderDTO
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * <pre>
     * 分页查询订单列表
     * @param pageable
     * @return Page<OrderDTO>
     * </pre>
     */
    Page<OrderDTO> findAll(Pageable pageable);
    
    /**
     * <pre>
     * closeOrCancelOrder方法的作用：关闭未支付的超时订单
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @since JDK 1.6
     * </pre>
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void closeOrCancelOrder(OrderDTO orderDTO);
}
