package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.exception.NovelOrderException;
import com.potato369.novel.model.PayResult;
import com.potato369.novel.service.OrderService;
import com.potato369.novel.service.ProductService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.service.WeChatPayService;
import com.potato369.novel.utils.JsonUtil;
import com.potato369.novel.utils.WwwUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName PayController
 * @Desc 支付订单Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 16:02
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/pay")
@Slf4j
public class BuyerPayController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WeChatPayService weChatPayService;

    /**
     * 发起微信公众号支付
     * @param productId
     * @param openid
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping(value = "/create")
    public ModelAndView create(@RequestParam(name = "productId", required = true) String productId,
                               @RequestParam(name = "openid", required = true) String openid,
                               @RequestParam(name = "returnUrl", required = true) String returnUrl,
                               Map<String, Object> map) {
        /** 第一步骤、前端请求参数校验 */
        checkParams(productId, openid, returnUrl);

        /**第二步骤、根据产品id获取产品信息 */
        ProductInfo productInfo = productService.findOne(productId);
        if (productInfo == null) {
            log.error("【微信公众号支付订单】产品信息不存在", ResultEnum.PRODUCT_NOT_EXIST);
            throw new NovelOrderException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        /** 第三步骤、组装订单详情信息 */
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder().build();
        BeanUtils.copyProperties(productInfo, orderDetail);
//        orderDetail.setProductGiveQuantity(productInfo.getProductGiveQuantity());
        orderDetail.setCreateTime(null);
        orderDetail.setUpdateTime(null);
        orderDetail.setBuyerOpenid(openid);
        if (log.isDebugEnabled()) {
//            log.debug("productInfo.getProductGiveQuantity()=={}", productInfo.getProductGiveQuantity());
            log.debug("orderDetail--1={}", orderDetail);
        }
        orderDetailList.add(orderDetail);

        /** 第四步骤、组装OrderDTO订单信息 */
        OrderDTO orderDTO = OrderDTO.builder().build();
        BeanUtils.copyProperties(productInfo, orderDTO);
        orderDTO.setCreateTime(null);
        orderDTO.setUpdateTime(null);
        NovelUserInfo userInfo = null;
		try {
			userInfo = userInfoService.findByOpenid(openid);
		} catch (Exception e) {
			log.error("【微信公众号支付订单】根据用户openid查询用户信息错误", e);
			throw new NovelOrderException(ResultEnum.MP_USER_INFO_EMPTY);
		}
        if (userInfo == null) {
            log.error("【微信公众号支付订单】用户信息不存在", ResultEnum.MP_USER_INFO_EMPTY);
            throw new NovelOrderException(ResultEnum.MP_USER_INFO_EMPTY);
        }
//        orderDTO.setBuyerAddress(userInfo.getCountry().concat(userInfo.getProvince()).concat(userInfo.getCity()));
        orderDTO.setBuyerOpenid(openid);
        BigDecimal orderAmount = BigDecimal.ZERO;
//        if (productInfo.getProductType() == ProductCalculateTypeEnum.COIN.getCode()) {
////            orderAmount = productInfo.getProductPrice().multiply(productInfo.getProductQuantity());
//        }
//        if (productInfo.getProductType() == ProductCalculateTypeEnum.VIP.getCode()) {
//            orderAmount = productInfo.getProductAmount();
//        }
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderName(productInfo.getProductName());
        orderDTO.setBuyerName(userInfo.getNickName());
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO = orderService.create(orderDTO);
        /** 第五步骤、提交微信公众号支付订单信息 */
        // PayResponse payResponse = payService.create(orderDTO);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        orderDTO.setCip(WwwUtil.getIpAddr4(request));
        PayResult payResponse = weChatPayService.create(orderDTO);
        if (log.isDebugEnabled()) {
            log.debug("payResponse={}", JsonUtil.toJson(payResponse));
        }
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }
    /**
     * 微信公众号支付异步通知
     * @param notifyData
     * @return
     */
    @PostMapping(value = "/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        weChatPayService.notify(notifyData);
        return new ModelAndView("pay/success");
    }

    /**
     * 微信公众号支付请求参数校验
     * @param productId
     * @param openid
     * @param returnUrl
     */
    private void checkParams(String productId, String openid, String returnUrl) {
        if (StringUtils.isEmpty(productId)) {
            log.error("【微信公众号支付订单】缺少产品id参数", ResultEnum.PARAM_ERROR);
            throw new NovelOrderException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(openid)) {
            log.error("【微信公众号支付订单】缺少微信openid参数", ResultEnum.MP_OPENID_ERROR);
            throw new NovelOrderException(ResultEnum.MP_OPENID_ERROR);
        }
        if (StringUtils.isEmpty(returnUrl)) {
            log.error("【微信公众号支付订单】缺少returnUrl参数", ResultEnum.PARAM_ERROR);
            throw new NovelOrderException(ResultEnum.PARAM_ERROR);
        }
    }
}
