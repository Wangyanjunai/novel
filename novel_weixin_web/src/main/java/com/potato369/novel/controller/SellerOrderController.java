package com.potato369.novel.controller;

import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.exception.SellerAuthorizeException;
import com.potato369.novel.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerOrderController
 * @Desc 卖家订单信息Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/16 10:21
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@RequestMapping(value = "/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * <pre>
     * 分页查询买家的订单信息列表
     * @param page
     * @param size
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/list")
    public ModelAndView list(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                             @RequestParam(name = "size", required = true, defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始分页查询买家的订单信息列表，page={}，size={}", page, size);
        }
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            PageRequest pageRequest = new PageRequest(page -1, size, sort);
            Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
            map.put("orderDTOPage", orderDTOPage);
            map.put("currentPage", page);
            map.put("size", size);
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束分页查询买家的订单信息列表，page={}，size={}", page, size);
            }
            return new ModelAndView("order/list", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理】分页查询买家的订单信息列表出现错误，page={}，size={}", page, size);
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error",map);
        }
    }

    /**
     * <pre>
     * 取消买家的订单信息
     * @param orderId
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/cancel")
    public ModelAndView cancel(@RequestParam(name = "orderId", required = true) String orderId,
                               Map<String, Object> map) {
        if (log.isDebugEnabled()) {
           log.debug("【卖家端后台管理】开始取消买家的订单信息，orderId={}", orderId);
        }
        try {
            if (StringUtils.isEmpty(orderId)) {
                log.error("【卖家端后台管理】取消买家的订单信息出现错误，orderId为空");
                throw new SellerAuthorizeException(ResultEnum.PARAM_ERROR);
            }
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束取消买家的订单信息，orderId={}", orderId);
            }
        } catch (Exception e) {
            log.error("【卖家端后台管理】取消买家的订单信息出现错误，orderId={}， e={}", orderId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/novel/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/novel/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * <pre>
     * 获取买家的订单详情信息
     * @param orderId
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/detail")
    public ModelAndView detail(@RequestParam(name = "orderId", required = true) String orderId,
                               Map<String,Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始获取买家的订单详情信息，orderId={}", orderId);
        }
        OrderDTO orderDTO = OrderDTO.builder().build();
        try {
            if (StringUtils.isEmpty(orderId)) {
                log.error("【卖家端后台管理】获取买家的订单详情信息出现错误，orderId为空");
                throw new SellerAuthorizeException(ResultEnum.PARAM_ERROR);
            }
            orderDTO = orderService.findOne(orderId);
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束获取买家的订单详情信息，orderId={}", orderId);
            }
        } catch (Exception e) {
            log.error("【卖家端后台管理】获取买家的订单详情信息出现错误，orderId={}， e={}", orderId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/novel/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * <pre>
     * 完结买家订单信息
     * @param orderId
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/finish")
    public ModelAndView finish(@RequestParam(name = "orderId", required = true) String orderId,
                               Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始完结买家订单信息，orderId={}", orderId);
        }
        try {
            if (StringUtils.isEmpty(orderId)) {
                log.error("【卖家端后台管理】获取完结买家订单信息出现错误，orderId为空");
                throw new SellerAuthorizeException(ResultEnum.PARAM_ERROR);
            }
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
            map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
            map.put("url", "/novel/order/list");
            return new ModelAndView("common/success", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理】获取完结买家订单信息出现错误，orderId={}， e={}", orderId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/novel/order/list");
            return new ModelAndView("common/error", map);
        }
    }
}
