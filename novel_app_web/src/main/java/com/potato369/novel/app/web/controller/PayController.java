package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.service.WeChatPayService;
import com.potato369.novel.basic.service.OrderService;
import com.potato369.novel.basic.service.ProductService;
import com.potato369.novel.basic.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName PayController
 * @Desc 订单支付Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 15:57
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@RestController
@RequestMapping(value = "/pay")
@Slf4j
public class PayController {

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
    public void create() {

    }
}
