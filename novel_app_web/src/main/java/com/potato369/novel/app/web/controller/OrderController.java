package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.dto.PayInfoDTO;
import com.potato369.novel.app.web.service.WeChatPayService;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WeChatPayService weChatPayService;

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
			    if (ProductInfoEnum.CHARGE.getCode().equals(productType)) {//充值

                }
			    if (ProductInfoEnum.EXCHANGE.getCode().equals(productType)) {//兑换

                }
			    if (ProductInfoEnum.WITHDRAW.getCode().equals(productType)) {//提现

                }
            }
            OrderMaster orderMaster = OrderMaster.builder().build();
            String orderId = UUIDUtil.genTimstampUUID();
            orderMaster.setOrderId(orderId);
            orderMaster.setBuyerAddress(novelUserInfo.getAddress());
            orderMaster.setBuyerName(novelUserInfo.getNickName());
            orderMaster.setBuyerOpenid(novelUserInfo.getOpenid());
            orderMaster.setOrderName(productInfo.getProductName());
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAITING.getCode());
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
}
