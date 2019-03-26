package com.potato369.novel.aspect;

import com.potato369.novel.conf.prop.ProjectUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * @PackageName com.potato369.novel.aspect
 * @ClassName NovelBuyerAuthorizeAspect
 * @Desc 买家前端登录校验切面类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:30
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class NovelBuyerAuthorizeAspect {

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    @Pointcut("execution(public * com.potato369.novel.controller.Buyer*.*(..)) && !execution(public * com.potato369.novel.controller.BuyerWeChatController.*(..)) ")
    private void verify() {}

    @Before(value = "verify()")
    public void doVerify() {
//        if (log.isDebugEnabled()) {
//            log.debug("【前端用户登录校验】开始校验");
//        }
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        HttpServletResponse response = requestAttributes.getResponse();
//        Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
//        if (cookie == null) {
//            log.warn("【前端用户登录校验】Cookie中查询不到openid");
//            redirect(response);
//            if (log.isDebugEnabled()) {
//                log.debug("【前端用户登录校验】结束校验");
//           }
//            throw new BuyerAuthorizeException(ResultEnum.COOKIE_OPENID_IS_NULL_ERROR);
//        }
    }
    private void redirect(HttpServletResponse response) {
        String redirectUrl = projectUrlProperties.getDomainUrl() + projectUrlProperties.getProjectName() + projectUrlProperties.getWeChatMpAuthorizeUrl();
        try {
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            log.error("【前端用户登录校验】出现错误", e);
        }
    }
}
