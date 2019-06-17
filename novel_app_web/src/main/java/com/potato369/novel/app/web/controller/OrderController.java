package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.model.WeixinPayResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.basic.enums.ProductInfoEnum;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.ProductService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    /**
     * <pre>
     * 创建订单
     * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1
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
	            String orderId = UUIDUtil.genTimstampUUID();
	            orderMaster.setOrderId(orderId);//订单id
	            orderMaster.setBuyerAddress(novelUserInfo.getAddress());//用户地址
	            orderMaster.setBuyerName(novelUserInfo.getNickName());//用户名称
	            orderMaster.setBuyerOpenid(novelUserInfo.getOpenid());//用户平台openid
	            orderMaster.setOrderName(productInfo.getProductName());//商品名称
	            orderMaster.setProductId(productInfo.getProductId());//商品id
			    orderMaster.setPayType(type);//支付方式
//			    orderMaster.setOrderAmount(productInfo.getProductAmount());//支付总金额
			    orderMaster.setOrderAmount(new BigDecimal(1));//支付总金额
			    List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			    OrderDetail orderDetail = OrderDetail.builder().build();
			    orderDetail.setDetailId(UUIDUtil.genTimstampUUID());//订单详情id
			    orderDetail.setOrderId(orderId);//订单id
			    orderDetail.setBuyerOpenid(novelUserInfo.getOpenid());//用户平台openid
			    orderDetail.setProductId(productInfo.getProductId());
			    orderDetail.setUserId(novelUserInfo.getMId());
			    orderDetailList.add(orderDetail);
			    orderMaster.setOrderDetailList(orderDetailList);
			    if (ProductInfoEnum.CHARGE.getCode().equals(productType)) {//充值
			    	orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());//订单状态
		            orderMaster.setPayStatus(PayStatusEnum.WAITING.getCode());//支付状态
		            orderMaster.setOrderType(ProductInfoEnum.CHARGE.getCode());//订单类型
                }
			    if (ProductInfoEnum.EXCHANGE.getCode().equals(productType)) {//兑换
			    	orderMaster.setOrderStatus(OrderStatusEnum.EXCHANG_ING.getCode());
			    	orderMaster.setPayStatus(PayStatusEnum.DEDUCT_ING.getCode());
			    	orderMaster.setOrderType(ProductInfoEnum.EXCHANGE.getCode());
                }
			    if (ProductInfoEnum.WITHDRAW.getCode().equals(productType)) {//提现
			    	orderMaster.setOrderStatus(OrderStatusEnum.WITHDRAW_ING.getCode());
			    	orderMaster.setPayStatus(PayStatusEnum.DEDUCT_ING.getCode());
			    	orderMaster.setOrderType(ProductInfoEnum.WITHDRAW.getCode());
                }
			    orderService.save(orderMaster);
			    if (PayStatusEnum.PAY_WITH_WECHAT.getCode().equals(type)) {//微信支付
			    	WeixinPayResult payResult = payService.weixinPay(orderMaster.getOrderId());
			    	resultVO.setCode(0);
			    	resultVO.setMsg("请求微信支付生成预支付信息成功。");
			    	resultVO.setData(payResult);
			    	return resultVO;
			    }
			    if (PayStatusEnum.PAY_WITH_ALIPAY.getCode().equals(type)) {//支付宝支付
			    	payService.aliPay(orderId);
				}
			    if (PayStatusEnum.PAY_WITH_BALANCE.getCode().equals(type)) {//余额支付
			    	payService.balancePay(orderId);
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
}
