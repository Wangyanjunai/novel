package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.repository.OrderDetailRepository;
import com.potato369.novel.basic.repository.OrderMasterRepository;
import com.potato369.novel.basic.repository.ProductInfoRepository;
import com.potato369.novel.basic.repository.UserInfoRepository;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Calendar;
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
    @Transactional
    public OrderMaster save(OrderMaster orderMaster) throws Exception{
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
     * @param orderDTO
     * @return OrderDTO
     * </pre>
     */
    @Override
    @Transactional
    public OrderMaster cancel(OrderMaster order) throws Exception{
        /** 1、判断订单是否为空 */
        if (order == null) {
            log.error("【取消订单】订单信息不存在");
            throw new Exception(ResultEnum.ORDER_NOT_EXIST.getMessage());
        }
        /** 2、判断订单的状态 */
        if (orderDTO.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
            log.error("【取消订单】订单状态不正确， orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new NovelOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 3、修改订单的状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【取消订单】更新失败，orderMaster={}", JsonUtil.toJson(orderMaster));
            throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 4、如果订单已经支付，需要退款 */
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            weChatPayService.refund(orderDTO);
        }
        /** 5、推送订单取消通知模板消息 */
        pushMessageService.pushOrderStatus(orderDTO,"您好，您的订单已取消，订单状态已通过系统确认！！！", "如果您已经支付，我们会尽快原路退回您支付的金额到您的原支付账户，具体到账时间以银行时间为准。感谢您对土豆互联科技的支持，有任何疑问可拨打客服电话：0755-86969315");
        return orderDTO;
    }

    /**
     * <pre>
     * 完结订单
     * @param orderDTO
     * @return orderDTO
     * </pre>
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) throws Exception{
        /** 1、判断订单状态 */
        if (orderDTO.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            log.error("【完结订单】 订单状态不正确， orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new NovelOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 2.修改订单状态为完结状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【完结订单】 更新失败，orderMaster={}", JsonUtil.toJson(orderMaster));
            throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 3、推送订单完结状态模板消息 */
        pushMessageService.pushOrderStatus(orderDTO, "您好，您的订单已经完成，订单状态已通过系统确认！！！", "请您核对收到的商品是否与您购买的一致！感谢您对土豆互联的支持，有任何疑问可拨打客服电话：0755-86969315");
        return orderDTO;
    }
    /**
     * <pre>
     * 支付订单
     * @param orderDTO
     * @return OrderDTO
     * </pre>
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) throws Exception{
        Date now = new Date();
        /** 1、判断订单状态 */
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()){
            log.error("【微信公众号支付订单】订单状态不正确， orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new NovelOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 2、判断支付状态 */
        if (PayStatusEnum.WAITING.getCode() != orderDTO.getPayStatus()){
            log.error("【微信公众号支付订单】订单支付状态不正确， orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new NovelOrderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        /** 3、修改订单支付状态 */
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = OrderMaster.builder().build();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setPayTime(now);
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setPayTime(now);
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                log.error("【查询书币产品信息】书币产品信息不存在， 产品id={}", orderDetail.getProductId());
                throw new NovelOrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (productInfo.getProductId().equals("5ff041a3da12455b83546e2987741a10")) {
                Calendar calendar =Calendar.getInstance();
                calendar.setTime(now);
                calendar.add(Calendar.YEAR, 1);
                orderMaster.setEndTime(calendar.getTime());
                orderDetail.setEndTime(calendar.getTime());
            }
            if (productInfo.getProductId().equals("80edd2c1503249debecd2ce523f1fa12")) {
                Calendar calendar =Calendar.getInstance();
                calendar.setTime(now);
                calendar.add(Calendar.MONTH, 3);
                orderMaster.setEndTime(calendar.getTime());
                orderDetail.setEndTime(calendar.getTime());
            }else {
                orderMaster.setEndTime(now);
                orderDetail.setEndTime(now);
            }
            /**给对应的用户发放书币*/
            UserInfo userInfo = userInfoRepository.findByOpenid(orderDTO.getBuyerOpenid());
            if (userInfo == null) {
                log.error("【微信公众号支付更新订单】给对应的用户发放书币失败，用户微信openid={}", orderDTO.getBuyerOpenid());
                throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
            }
            BigDecimal balance = userInfo.getBalance().add(orderDetail.getProductQuantity()).add(orderDetail.getProductGiveQuantity());
            userInfo.setBalance(balance);
            UserInfo userInfoUpdateResult =  userInfoRepository.save(userInfo);
            if (userInfoUpdateResult == null) {
                log.error("【微信公众号支付更新订单】给对应的用户发放书币失败，用户信息={}", JsonUtil.toJson(userInfo));
                throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
            }
            /**更新订单详情表数据*/
            OrderDetail orderDetailUpdateResult = orderDetailRepository.save(orderDetail);
            if (orderDetailUpdateResult == null) {
                log.error("【微信公众号支付更新订单】更新订单订单详情失败，orderDetail={}", JsonUtil.toJson(orderDetail));
                throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
            }
        }
        /**更新订单表数据*/
        OrderMaster orderMasterUpdateResult = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdateResult == null) {
            log.error("【微信公众号支付更新订单】更新订单失败，orderMaster={}", JsonUtil.toJson(orderMaster));
            throw new NovelOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 4、推送订单支付成功模板消息 */
        pushMessageService.pushPaySuccess(orderDTO);
        return orderDTO;
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
}
