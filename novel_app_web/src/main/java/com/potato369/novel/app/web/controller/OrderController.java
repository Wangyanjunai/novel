package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.conf.prop.WeChatPayProperties;
import com.potato369.novel.app.web.model.AliPayResult;
import com.potato369.novel.app.web.model.WeChatPayResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.app.web.vo.MessageVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.*;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.ProductService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName OrderController
 * @Desc 订单支付Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 15:57
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@RestController
@RequestMapping(value = "/order")
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Autowired
    private WeChatPayProperties properties;

    /**
     * <pre>
     * 创建订单并提交生成并返回预支付订单信息
     * 创建订单微信文档地址：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1
     * @param mid 用户mid
     * @param pid 商品id
     * @param type 支付方式
     * </pre>
     */
    @GetMapping(value = "/create")
    public ResultVO create(@RequestParam(name = "mid", required = false) String mid,
                           @RequestParam(name = "pid", required = false) String pid,
                           @RequestParam(name = "type", required = false) Integer type) {
        ResultVO resultVO = new ResultVO();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================【创建订单】后台生成订单信息并支付====================start");
            }
            if (StringUtils.isEmpty(mid)) {
                resultVO.setMsg("【创建订单】缺少必要参数用户mid，用户mid不能为空。");
                resultVO.setCode(-100);
                return resultVO;
            }
            if (StringUtils.isEmpty(pid)) {
                resultVO.setMsg("【创建订单】缺少必要参数选择的VIP套餐商品id，VIP套餐商品id不能为空。");
                resultVO.setCode(-100);
                return resultVO;
            }
            if (type == null) {
                resultVO.setMsg("【创建订单】缺少必要参数支付方式，支付方式不能为空。");
                resultVO.setCode(-100);
                return resultVO;
            }
            NovelUserInfo novelUserInfo = userInfoService.findByUserMId(mid);
            if (novelUserInfo == null) {
                resultVO.setMsg("【创建订单】查询用户信息不存在。");
                resultVO.setCode(-200);
                return resultVO;
            }
            ProductInfo productInfo = productService.findOne(pid);
            if (productInfo == null) {
                resultVO.setMsg("【创建订单】查询商品信息不存在。");
                resultVO.setCode(-300);
                return resultVO;
            }
            Integer productType = productInfo.getProductType();
            if (productType != null) {
                OrderMaster orderMaster = OrderMaster.builder().build();
                String orderId = UUIDUtil.genTimstampUUID();//创建订单id
                orderMaster.setOrderId(orderId);//设置订单id
                orderMaster.setUserId(novelUserInfo.getMId());//设置用户mid
                orderMaster.setBuyerName(novelUserInfo.getNickName());//设置用户名称
                orderMaster.setBuyerAddress(novelUserInfo.getAddress());//设置用户地址
                orderMaster.setBuyerOpenid(novelUserInfo.getOpenid());//设置用户平台openid
                orderMaster.setOrderName(new StringBuffer().append(this.properties.getOrderNamePrefix()).append(productInfo.getProductName()).toString().trim());//设置订单名称
                orderMaster.setProductId(productInfo.getProductId());//设置商品id
                orderMaster.setPayType(type);//设置支付方式
                orderMaster.setOrderAmount(productInfo.getProductAmount());//设置支付总金额
                List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
                OrderDetail orderDetail = OrderDetail.builder().build();
                orderDetail.setDetailId(UUIDUtil.genTimstampUUID());//设置订单详情id
                orderDetail.setOrderId(orderId);//设置订单id
                orderDetail.setBuyerOpenid(novelUserInfo.getOpenid());//设置用户平台openid
                orderDetail.setProductId(productInfo.getProductId());//设置商品id
                orderDetail.setUserId(novelUserInfo.getMId());//设置用户mid
                orderDetailList.add(orderDetail);
                orderMaster.setOrderDetailList(orderDetailList);
                if (ProductTypeEnum.RECHARGE.getCode().equals(productType)) {//充值
                    orderMaster.setOrderStatus(OrderStatusEnum.RECHARGE_WAITING.getCode());//设置订单状态
                    orderMaster.setPayStatus(PayStatusEnum.PAY_WAITING.getCode());//设置支付状态
                    orderMaster.setOrderType(ProductTypeEnum.RECHARGE.getCode());//设置订单类型
                }
                if (ProductTypeEnum.EXCHANGE.getCode().equals(productType)) {//兑换
                    orderMaster.setOrderStatus(OrderStatusEnum.EXCHANGE_WAITING.getCode());
                    orderMaster.setPayStatus(PayStatusEnum.EXCHANGE_WAITING.getCode());
                    orderMaster.setOrderType(ProductTypeEnum.EXCHANGE.getCode());
                }
                if (ProductTypeEnum.WITHDRAW.getCode().equals(productType)) {//提现
                    orderMaster.setOrderStatus(OrderStatusEnum.WITHDRAW_ING.getCode());
                    orderMaster.setPayStatus(PayStatusEnum.WITHDRAW_WAITING.getCode());
                    orderMaster.setOrderType(ProductTypeEnum.WITHDRAW.getCode());
                }
                orderService.save(orderMaster);
                if (PayTypeEnum.PAY_WITH_WECHAT.getCode().equals(type)) {//微信支付
                    WeChatPayResult payResult = payService.weixinPay(orderMaster.getOrderId());
                    resultVO.setCode(0);
                    resultVO.setMsg("请求微信支付生成预支付信息成功。");
                    resultVO.setData(payResult);
                    return resultVO;
                }
                if (PayTypeEnum.PAY_WITH_ALIPAY.getCode().equals(type)) {//支付宝支付
                    AliPayResult payResult = payService.aliPay(orderId);
                    resultVO.setCode(0);
                    resultVO.setMsg("请求支付宝支付生成预支付信息成功。");
                    resultVO.setData(payResult);
                    return resultVO;
                }
                if (PayTypeEnum.PAY_WITH_BALANCE.getCode().equals(type)) {//余额支付
                    UserInfoVO userInfoVO = payService.balancePay(orderId);
                    resultVO.setCode(0);
                    resultVO.setMsg("请求余额支付信息成功。");
                    resultVO.setData(userInfoVO);
                    return resultVO;
                }
            }
            return resultVO;
        } catch (Exception e) {
            log.error("【创建订单】后台生成订单信息并支付出现错误", e);
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================【创建订单】后台生成订单信息并支付======================end");
            }
        }
    }

    @GetMapping(value = "/message")
    public ResultVO<MessageVO> findOrderMessage(@RequestParam(name = "orderType", required = false) Integer orderType) {
        ResultVO<MessageVO> resultVO = new ResultVO<>();
        try {
            if (orderType == null) {//查询所有的订单前10条记录
                Sort sort = new Sort(Sort.Direction.DESC, "createTime", "updateTime");
                PageRequest pageRequest = new PageRequest(0, 10, sort);
                List<Integer> orderStatusList = new ArrayList<>();
                orderStatusList.add(OrderStatusEnum.RECHARGE_SUCCESS.getCode());
                orderStatusList.add(OrderStatusEnum.EXCHANGE_SUCCESS.getCode());
                orderStatusList.add(OrderStatusEnum.WITHDRAW_SUCCESS.getCode());
                List<Integer> payStatusList = new ArrayList<>();
                payStatusList.add(PayStatusEnum.PAY_SUCCESS.getCode());
                payStatusList.add(PayStatusEnum.EXCHANGE_SUCCESS.getCode());
                payStatusList.add(PayStatusEnum.WITHDRAW_SUCCESS.getCode());
                Page<OrderMaster> orderMasterPage = orderService.findByOrderStatusAndPayStatus(orderStatusList, payStatusList, pageRequest);
                if (log.isDebugEnabled()) {
                    log.debug("totalPage={}", orderMasterPage.getTotalPages());
                    log.debug("getTotalElements={}", orderMasterPage.getTotalElements());
                }
            }
        } catch (Exception e) {

        } finally {

        }
        return resultVO;
    }
}
