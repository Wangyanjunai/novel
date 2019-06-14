package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.dto.PayInfoDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
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
@SuppressWarnings({"unchecked", "rawtypes"})
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
    @PostMapping(value = "/create", produces = "application/json;charset=utf-8")
    public ResultVO create(@RequestBody @Valid PayInfoDTO payInfoDTO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO();
        try {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
			if (bindingResult.hasErrors()) {
                resultVO.setMsg(bindingResult.getFieldError().getDefaultMessage());
                resultVO.setCode(-100);
                resultVO.setData(null);
                return resultVO;
            }
            String userId = null;
            String productId = null;
            Integer payType = null;
			if (payInfoDTO != null) {
                userId      = payInfoDTO.getUserId();
                productId   = payInfoDTO.getProductId();
                payType     = payInfoDTO.getPayType();
            }
            NovelUserInfo novelUserInfo = userInfoService.findByUserMId(userId);
			if (novelUserInfo == null) {
                resultVO.setMsg("用户信息不存在");
                resultVO.setCode(-200);
                resultVO.setData(null);
                return resultVO;
            }
            ProductInfo productInfo = productService.findOne(productId);
			if (productInfo == null) {
                resultVO.setMsg("商品信息不存在");
                resultVO.setCode(-300);
                resultVO.setData(null);
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
			    orderMaster.setPayType(payType);//支付方式
			    orderMaster.setOrderAmount(productInfo.getProductAmount());//支付总金额
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
			    if (PayStatusEnum.PAY_WITH_ALIPAY.getCode().equals(payType)) {//支付宝支付
					
				}
			    if (PayStatusEnum.PAY_WITH_BALANCE.getCode().equals(payType)) {//余额支付
					
				}
			    if (PayStatusEnum.PAY_WITH_WECHAT.getCode().equals(payType)) {//微信支付
			    	payService.createByWeChatPay(orderMaster);
			    }
            }
			return resultVO;
		} catch (Exception e) {
			log.error("", e);
            return resultVO;
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("");
			}
		}
    }
    
    @GetMapping(value = "/notify")
    public void notified() {
    	
    }
}
