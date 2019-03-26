package com.potato369.novel.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.exception.BuyerAuthorizeException;

/**
 * <pre>
 * @PackageName com.potato369.novel.handler
 * @ClassName BuyerExceptionHandler
 * @Desc 买家端出现错误异常处理类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/02/12 10:55
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class BuyerExceptionHandler {

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    @ExceptionHandler(value = BuyerAuthorizeException.class)
    @ResponseBody
    public ModelAndView handlerBuyerAuthorizeException() {
        return new ModelAndView();
    }
}
