package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.SellerInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.constant.CookieConstant;
import com.potato369.novel.constant.RedisConstant;
import com.potato369.novel.manage.RedisManager;
import com.potato369.novel.service.SellerService;
import com.potato369.novel.utils.CookieUtil;
import com.potato369.novel.utils.JsonUtil;
import com.potato369.novel.utils.UUIDUtil;
import com.potato369.novel.utils.WwwUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerController
 * @Desc 卖家端卖家Controller控制器类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/11 16:30
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@RequestMapping(value = "/seller")
@Slf4j
public class SellerAdminController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private ProjectUrlProperties projectUrlConfig;

    /**
     * <pre>
     * 卖家端后台管理员登录
     * @param openid
     * @param request
     * @param response
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam(name = "openid", required = true) String openid,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理员登录】开始登录校验信息和更新卖家信息");
        }
        try {
            //第一步：用openid和数据库里的数据匹配
            SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
            if (sellerInfo == null) {
                log.error("【卖家端后台管理员登录】卖家信息不存在，卖家微信openid={}", openid);
                map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
                map.put("url", "#");
                return new ModelAndView("common/error");
            }
            // 第二步：设置Token保存到Redis缓存
            String token = UUIDUtil.gen32UUID();
            // 设置Redis缓存过期时间为2小时
            Integer expire = RedisConstant.EXPIRE;
            redisManager.setStrAndExpire(String.format(RedisConstant.TOKEN_PREFIX , token),  openid, expire);
            // 第三步：设置Token保存到客户端浏览器Cookie
            CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
            // 第四步：更新登录时间和卖家登录的IP
            sellerInfo.setLoginTime(new Date());
            String loginIP = WwwUtil.getIpAddr4(request);
            sellerInfo.setLoginIp(loginIP);
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理员登录】开始更新更新登录时间和卖家登录的IP");
            }
            SellerInfo sellerInfoUpdate = sellerService.updateSellerInfo(sellerInfo);
            if (sellerInfoUpdate == null) {
                log.error("【卖家端后台管理员登录】更新更新登录时间和卖家登录的IP出现错误，卖家信息sellerInfo={}", JsonUtil.toJson(sellerInfo));
            }
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理员登录】结束更新更新登录时间和卖家登录的IP");
            }
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理员登录】结束登录校验信息和更新卖家信息");
            }
            // 第五步：返回登录成功后显示订单列表信息
            return new ModelAndView("redirect:" + projectUrlConfig.getDomainUrl() + projectUrlConfig.getProjectName() + "/order/list", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理员登录】登录校验信息或者更新卖家信息出现错误，卖家微信openid={}， e={}", openid, e);
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "#");
            return new ModelAndView("common/error");
        }
    }

    /**
     * <pre>
     * 卖家端后台管理员登出
     * @param request
     * @param response
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.error("【卖家端后台管理员登出】开始清除保存在卖家端的信息");
        }
        try {
            //第一步、从cookie里面查询
            Cookie cookie =  CookieUtil.get(request, CookieConstant.TOKEN);
            if (cookie != null) {
                //第二步、清除Redis
                redisManager.delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
                //第三步、清除cookie
                CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
            }
            map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
            map.put("url","#");
            if (log.isDebugEnabled()) {
                log.error("【卖家端后台管理员登出】结束清除保存在卖家端的信息");
            }
            return new ModelAndView("common/success", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理员登出】清除保存在卖家端的信息，e={}", e);
            map.put("msg", ResultEnum.LOGOUT_FAIL.getMessage());
            map.put("url", "#");
            return new ModelAndView("common/error");
        }
    }
}
