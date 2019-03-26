package com.potato369.novel.aspect;

import com.potato369.novel.constant.CookieConstant;
import com.potato369.novel.constant.RedisConstant;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.exception.SellerAuthorizeException;
import com.potato369.novel.manage.RedisManager;
import com.potato369.novel.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * @PackageName com.potato369.novel.aspect
 * @ClassName NovelAuthorizeAspect
 * @Desc 卖家后端登录校验切面类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:21
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class NovelSellerAuthorizeAspect {

    @Autowired
    private RedisManager redisManager;

    @Pointcut("execution(public * com.potato369.novel.controller.Seller*.*(..)) && !execution(public * com.potato369.novel.controller.SellerAdminController.*(..)) ")
    private void verify() {}

    @Before(value = "verify()")
    public void doVerify() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【后端管理员登录校验】Cookie中查询不到token");
            throw new SellerAuthorizeException(ResultEnum.COOKIE_TOKEN_IS_NULL_ERROR);
        }
        String tokenValue = redisManager.getStr(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【后端管理员登录校验】Redis中查询不到token");
            throw new SellerAuthorizeException(ResultEnum.REDIS_TOKEN_IS_NULL_ERROR);
        }
    }
}
