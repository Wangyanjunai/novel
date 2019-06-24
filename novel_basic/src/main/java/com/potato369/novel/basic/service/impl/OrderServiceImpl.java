package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.*;
import com.potato369.novel.basic.repository.OrderDetailRepository;
import com.potato369.novel.basic.repository.OrderMasterRepository;
import com.potato369.novel.basic.repository.ProductInfoRepository;
import com.potato369.novel.basic.repository.UserInfoRepository;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName OrderServiceImpl
 * @Desc 订单信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:08
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoRepository productRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    
    /**
     * <pre>
     * 创建保存订单信息
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster save(OrderMaster orderMaster) throws Exception{
        if (orderMaster == null) {
            log.error("【创建保存订单信息】，订单信息为空");
            throw new Exception("【创建保存订单信息】出现错误");
        }
        List<OrderDetail> orderDetailList = orderMaster.getOrderDetailList();
        for (OrderDetail orderDetail:orderDetailList) {
            orderDetailRepository.save(orderDetail);
        }
    	return orderMasterRepository.save(orderMaster);
    }

    /**
     * <pre>
     * 查询单个订单
     * @param orderId
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster findOne(String orderId) throws Exception{
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            log.error("【查询订单】订单信息不存在，订单id={}", orderId);
            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【查询订单详情】订单详情信息不存在，订单id={}", orderId);
            throw new Exception(ResultEnum.ORDERDETAIL_NOT_EXIST.getMessage());
        }
        orderMaster.setOrderDetailList(orderDetailList);
        return orderMaster;
    }

    /**
     * <pre>
     * 查询所有的订单列表
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public List<OrderMaster> findAll() throws Exception {
        return orderMasterRepository.findAll();
    }

    /**
     * <pre>
     * 查找未支付且订单状态是等待支付的订单信息列表
     * @param payStatus
     * @param orderStatus
     * @return
     * </pre>
     */
    @Override
    public List<OrderMaster> findTimeOutUnpaid(Integer payStatus, Integer orderStatus) throws Exception{
        return orderMasterRepository.findOrderMastersByPayStatusAndOrderStatus(payStatus, orderStatus);
    }

    /**
     * <pre>
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return Page<OrderDTO>
     * </pre>
     */
    @Override
    public Page<OrderMaster> findAll(String buyerOpenid, Pageable pageable) throws Exception{
        return orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
    }

    /**
     * <pre>
     * 取消订单
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster cancel(OrderMaster orderMaster) throws Exception{
        /** 1、判断订单是否为空 */
        if (orderMaster == null) {
            log.error("【取消订单】订单信息不存在");
            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
        }
        /** 2、判断订单的状态 */
        if (orderMaster.getOrderStatus() != OrderStatusEnum.RECHARGE_WAITING.getCode()) {
            log.error("【取消订单】订单状态不正确， orderId={}，orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
        }
        /** 3、修改订单的状态 */
        orderMaster.setOrderStatus(OrderStatusEnum.RECHARGE_CLOSE.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
        }
        return orderMaster;
    }

    /**
     * <pre>
     * 完结订单
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster finish(OrderMaster orderMaster) throws Exception{
        /** 1、判断订单状态 */
        if (orderMaster.getOrderStatus() != OrderStatusEnum.RECHARGE_WAITING.getCode()){
            log.error("【完结订单】 订单状态不正确， orderId={}，orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
        }
        /** 2.修改订单状态为完结状态 */
        orderMaster.setOrderStatus(OrderStatusEnum.RECHARGE_SUCCESS.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【完结订单】 更新失败，orderMaster={}", orderMaster);
            throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
        }
        return orderMaster;
    }
    /**
     * <pre>
     * 回调修改支付订单信息
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster paidByWeChatPay(OrderMaster orderMaster) throws Exception{
        checkWeChatPayNotifyOrder(orderMaster);
        /** 4、修改订单状态 */
        orderMaster.setPayStatus(PayStatusEnum.RECHARGE_SUCCESS.getCode());//订单支付状态：支付成功
        orderMaster.setOrderStatus(OrderStatusEnum.RECHARGE_SUCCESS.getCode());//订单状态，交易支付成功
        orderMaster.setPayType(PayTypeEnum.PAY_WITH_WECHAT.getCode());//订单支付方式，微信
        List<OrderDetail> orderDetailList = orderMaster.getOrderDetailList();
        for (OrderDetail orderDetail:orderDetailList) {
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                log.error("【微信APP支付回调更新订单】查询商品信息不存在， 商品id={}", orderDetail.getProductId());
                throw new Exception(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            NovelUserInfo userInfo = userInfoRepository.selectByUserMId(orderDetail.getUserId());
            if (userInfo == null) {
                log.error("【微信APP支付回调更新订单】查询用户信息不存在，给对应的用户增加VIP时长失败，用户mid={}", orderDetail.getUserId());
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
            BigDecimal chargeAmount = userInfo.getChargeAmount();
            userInfo.setChargeAmount(chargeAmount.add(orderMaster.getOrderAmount()));
            userInfo.setVipGradeId(UserInfoVIPGradeIdEnum.VIP2.getMessage());
            Date vipEndTime = userInfo.getVipEndTime();
            Integer calculateType = productInfo.getProductCalculateType();
            Integer dateValue = productInfo.getDateValue();
            Date updateVIPEndTime = null;
            if (ProductCalculateTypeEnum.DAY.getCode().equals(calculateType)) {
                updateVIPEndTime = DateUtil.getAfterDayDate(vipEndTime, dateValue);
            }
            if (ProductCalculateTypeEnum.MONTH.getCode().equals(calculateType)) {
                updateVIPEndTime = DateUtil.getAfterMonthDate(vipEndTime, dateValue);
            }
            userInfo.setVipEndTime(updateVIPEndTime);
            NovelUserInfo userInfoUpdateResult = userInfoRepository.save(userInfo);
            if (userInfoUpdateResult == null) {
                log.error("【微信APP支付回调更新订单】给对应的用户增加VIP时长失败，用户信息={}", userInfo);
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
            /**更新订单详情表数据*/
            OrderDetail orderDetailUpdateResult = orderDetailRepository.save(orderDetail);
            if (orderDetailUpdateResult == null) {
                log.error("【微信APP支付回调更新订单】更新订单详情失败，orderDetail={}", orderDetail);
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
        }
        /**更新订单表数据*/
        OrderMaster orderMasterUpdateResult = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdateResult == null) {
            log.error("【微信APP支付回调更新订单】更新订单失败，orderMaster={}", orderMaster);
            throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
        }
        return orderMaster;
    }
    /**
     * <pre>
     * 支付宝支付回调修改支付订单信息
     * @param orderMaster
     * @return OrderMaster.class
     * </pre>
     */
    @Override
    public OrderMaster paidByAliPay(OrderMaster orderMaster) throws Exception{
        List<OrderDetail> orderDetailList = orderMaster.getOrderDetailList();
        for (OrderDetail orderDetail:orderDetailList) {
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                log.error("【微信APP支付回调更新订单】查询商品信息不存在， 商品id={}", orderDetail.getProductId());
                throw new Exception(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            NovelUserInfo userInfo = userInfoRepository.selectByUserMId(orderDetail.getUserId());
            if (userInfo == null) {
                log.error("【微信APP支付回调更新订单】查询用户信息不存在，给对应的用户增加VIP时长失败，用户mid={}", orderDetail.getUserId());
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
            BigDecimal chargeAmount = userInfo.getChargeAmount();
            userInfo.setChargeAmount(chargeAmount.add(orderMaster.getOrderAmount()));
            userInfo.setVipGradeId(UserInfoVIPGradeIdEnum.VIP2.getMessage());
            Date vipEndTime = userInfo.getVipEndTime();
            Integer calculateType = productInfo.getProductCalculateType();
            Integer dateValue = productInfo.getDateValue();
            Date updateVIPEndTime = null;
            if (ProductCalculateTypeEnum.DAY.getCode().equals(calculateType)) {
                updateVIPEndTime = DateUtil.getAfterDayDate(vipEndTime, dateValue);
            }
            if (ProductCalculateTypeEnum.MONTH.getCode().equals(calculateType)) {
                updateVIPEndTime = DateUtil.getAfterMonthDate(vipEndTime, dateValue);
            }
            userInfo.setVipEndTime(updateVIPEndTime);
            NovelUserInfo userInfoUpdateResult = userInfoRepository.save(userInfo);
            if (userInfoUpdateResult == null) {
                log.error("【微信APP支付回调更新订单】给对应的用户增加VIP时长失败，用户信息={}", userInfo);
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
            /**更新订单详情表数据*/
            OrderDetail orderDetailUpdateResult = orderDetailRepository.save(orderDetail);
            if (orderDetailUpdateResult == null) {
                log.error("【微信APP支付回调更新订单】更新订单详情失败，orderDetail={}", orderDetail);
                throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
            }
        }
        /**更新订单表数据*/
        OrderMaster orderMasterUpdateResult = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdateResult == null) {
            log.error("【微信APP支付回调更新订单】更新订单失败，orderMaster={}", orderMaster);
            throw new Exception(ResultEnum.ORDER_UPDATE_FAIL.getMessage());
        }
        return orderMaster;
    }
    /**
     * <pre>
     * findAll:(分页查询订单列表)
     * @param pageable
     * @return Page<OrderMaster>
     * </pre>
     */
    @Override
    public Page<OrderMaster> findAll(Pageable pageable) throws Exception{
        return orderMasterRepository.findAll(pageable);
    }

    /**
     * <pre>
     * 根据订单支付状态列表，订单状态列表和分页排序信息查询订单信息列表。
     * @param orderStatusList
     * @param payStatusList
     * @param pageable
     * @return List
     * </pre>
     */
    @Override
    public List<OrderMaster> findByOrderStatusAndPayStatus(List<Integer> orderStatusList, List<Integer> payStatusList, Pageable pageable) {
        return orderMasterRepository.findOrderMastersByOrderStatusInAndPayStatusIn(orderStatusList, payStatusList, pageable);
    }

    /**
     * <pre>
     * 根据订单支付状态列表，订单状态列表，订单类型和分页排序信息查询订单信息列表。
     * @param orderStatusList
     * @param payStatusList
     * @param orderTypeList
     * @param pageable
     * @return List
     * </pre>
     */
    @Override
    public List<OrderMaster> findByOrderStatusAndPayStatusAndOrderType(List<Integer> orderStatusList, List<Integer> payStatusList, List<Integer> orderTypeList, Pageable pageable) {
        return orderMasterRepository.findOrderMastersByOrderStatusInAndPayStatusInAndOrderTypeIn(orderStatusList, payStatusList, orderTypeList, pageable);
    }

    public OrderMaster checkWeChatPayNotifyOrder(OrderMaster order) throws Exception {
		if (order == null) {
			log.error("【微信APP预支付订单】 订单信息不存在");
			throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
		}
		String orderId = order.getOrderId();
		if (order.getOrderStatus() != OrderStatusEnum.RECHARGE_WAITING.getCode()) {
			log.error("【微信APP预支付订单】 订单状态不正确，订单id={}，订单状态={}", orderId, order.getOrderStatusEnum().getMessage());
			throw new Exception(ResultEnum.ORDER_STATUS_ERROR.getMessage());
		}
		if (order.getPayStatus() != PayStatusEnum.RECHARGE_WAITING.getCode()) {
			log.error("【微信APP预支付订单】 订单支付状态不正确，订单id={}，订单支付状态={}", orderId, order.getPayStatusEnum().getMessage());
			throw new Exception(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
		}
		if (order.getPayType() != PayTypeEnum.PAY_WITH_ALIPAY.getCode() && 
			order.getPayType() != PayTypeEnum.PAY_WITH_WECHAT.getCode() && 
			order.getPayType() != PayTypeEnum.PAY_WITH_BALANCE.getCode()) {
			log.error("【微信APP预支付订单】 订单支付方式不正确，订单id={}，订单支付方式={}", orderId, order.getPayTypeEnum().getMessage());
			throw new Exception(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
		}
		return order;
	}
}
