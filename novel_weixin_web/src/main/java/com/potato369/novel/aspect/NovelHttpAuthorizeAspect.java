package com.potato369.novel.aspect;

import com.potato369.novel.utils.WwwUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * @PackageName com.potato369.novel.aspect
 * @ClassName HttpAspect
 * @Desc Http请求拦截切面类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:01
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class NovelHttpAuthorizeAspect {

	@Pointcut(value = "execution(public * com.potato369.novel.controller.*.*(..))")
	private void log() {}

	@Before(value = "log()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("【拦截http请求】doBefore：拦截http请求日志");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		// url：请求URL
		log.info("【拦截http请求】url={}", request.getRequestURL());
		// method：请求方式
		log.info("【拦截http请求】method={}", request.getMethod());
		// ip：请求IP地址
		log.info("【拦截http请求】代理ip={}", WwwUtil.getIpAddr1(request));
		// 客户端真实ip
		log.info("【拦截http请求】真实ip={}", WwwUtil.getIpAddr4(request));
		// class method：请求类的方法
		log.info("【拦截http请求】class method name={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");
		// args：请求参数
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			log.info("【拦截http请求】 args[" + i + "]={}", joinPoint.getArgs()[i]);
		}
	}

	@After(value = "log()")
	public void doAfter() {
		log.info("【拦截http请求】doAfter：拦截http请求日志");
	}

	@AfterReturning(returning = "object", pointcut = "log()")
	public void doAfterReturning(Object object) {
		log.info("【拦截http请求】doAfterReturning: 拦截http请求返回response={}", object);
	}
}
