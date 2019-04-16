package com.potato369.novel.app.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName LoadingController
 * @Desc 急速追书小说app初始化加载数据接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/16 15:48
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@RestController
@RequestMapping(value = "/loading")
public class LoadingController {

    /**
     * @api {GET} /loading/getData 加载广告图片
     * @apiVersion 0.0.1
     * @apiGroup Users
     * @apiDescription 急速追书小说APP初始页面加载广告图片接口
     * @apiParam {OrderForm} orderForm 订单form
     * @apiParam {BindingResult} bindingResult 订单验证信息
     * @apiParamExample {json} 请求样例：
     *                ?account=sodlinken&password=11223344&mobile=13739554137&vip=0&recommend=
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccess (200) {int} code 0 代表无错误 1代表有错误
     * @apiSuccessExample {json} 返回样例:
     *                    {"code":"0","msg":"注册成功"}
     */
    @GetMapping(value = "/getData")
    public void getData() {

    }
}
