package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.constant.CookieConstant;
import com.potato369.novel.constant.RedisConstant;
import com.potato369.novel.converter.WxMpUser2UserInfoConverter;
import com.potato369.novel.exception.BuyerAuthorizeException;
import com.potato369.novel.manage.RedisManager;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.utils.CookieUtil;
import com.potato369.novel.utils.JsonUtil;
import com.potato369.novel.utils.UUIDUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName WechatController
 * @Desc 微信公众开发入口Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 11:25
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping(value = "/wechat")
public class BuyerWeChatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProjectUrlProperties projectUrlConfig;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private WxMpMessageRouter router;

    /**
     * <pre>
     * @Title: authGet
     * @Description 接收到来自微信服务器的认证消息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echoStr
     * @return string 返回值类型
     * @throws java.lang.IllegalArgumentException 抛出非法参数异常类型
     * </pre>
     */
    @ResponseBody
    @GetMapping(value = "/portal", produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echoStr) {
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台】 接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echoStr);
        }
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echoStr)) {
            log.error("【微信公众平台】请求参数非法，请核实!");
            throw new IllegalArgumentException("【微信公众平台】请求参数非法，请核实!");
        }
        if (this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echoStr;
        }
        return "非法请求";
    }

    /**
     * <pre>
     * @Title: authPost
     * @Description: 接收微信推送过来的消息
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encType
     * @param msgSignature
     * @return string 返回值类型
     * @throws java.lang.IllegalArgumentException 抛出非法参数异常类型
     * </pre>
     */
    @ResponseBody
    @PostMapping(value = "/portal", produces = "application/xml; charset=UTF-8")
    public String authPost (@RequestBody String requestBody,
                            @RequestParam(name = "signature", required = true) String signature,
                            @RequestParam(name = "timestamp", required = true) String timestamp,
                            @RequestParam(name = "nonce", required = true) String nonce,
                            @RequestParam(name = "encrypt_type", required = false) String encType,
                            @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台】接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ", signature, encType, msgSignature, timestamp, nonce, requestBody);
        }
        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("【微信公众平台】非法请求，可能属于伪造的请求！");
            throw new IllegalArgumentException("【微信公众平台】非法请求，可能属于伪造的请求！");
        }
        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toXml();
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, this.wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            if (log.isDebugEnabled()) {
                log.debug("【微信公众平台】消息解密后内容为：{} ", inMessage.toString());
            }
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toEncryptedXml(this.wxMpService.getWxMpConfigStorage());
        }
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台】组装回复信息：{}", out);
        }
        return out;
    }

    /**
     * <pre>
     * @Title: route
     * @Description: 根据不同的消息类型路由处理消息
     * @param message
     * @param message
     * @return WxMpXmlOutMessage 返回值类型
     * @throws  Exception
     * </pre>
     */
    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            log.error("【微信公众平台】处理消息出现错误", e);
        }
        return null;
    }

    /**
     * <pre>
     * 微信公众平台网页授权登录，获取code
     *
     * @param returnUrl
     * @return
     * </pre>
     */
    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam(name = "returnUrl", required = true) String returnUrl) {

        String url = projectUrlConfig.getDomainUrl() + projectUrlConfig.getProjectName() + projectUrlConfig.getWeChatMpAuthorizeUrlUserInfo();
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台网页授权】获取code ==> url={}", url);
        }
        String redirectUrl = "";
        try {
            redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("【微信公众平台网页授权】获取code出现UnsupportedEncodingException错误，e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.ENCODING_UNSUPPORT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("【微信公众平台网页授权】获取code出现Exception错误，e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.MP_OTHER_ERROR.getCode(), e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台网页授权】获取code ==> redirectUrl={}", redirectUrl);
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * <pre>
     * 微信公众平台网页授权，获取用户信息
     * @param code
     * @param returnUrl
     * @return
     * </pre>
     */
    @GetMapping(value = "/userInfo")
    public String userInfo(@RequestParam(name = "code", required = true) String code,
                           @RequestParam(name = "state", required = true) String returnUrl,
                           HttpServletResponse response) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信公众平台网页授权】获取用户信息出现错误， e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台网页授权】获取用户信息  ==>  wxMpOAuth2AccessToken_Json={}", JsonUtil.toJson(wxMpOAuth2AccessToken));
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        if (log.isDebugEnabled()) {
            log.debug("【微信公众平台网页授权】设置token保存到Cookie或者Redis");
        }
        //第一步、设置Token保存到Redis缓存
        String token = UUIDUtil.gen32UUID();
        //设置Redis缓存过期时间为2小时
        Integer expire = RedisConstant.EXPIRE;
        redisManager.setStrAndExpire(String.format(RedisConstant.FORCE_TOKEN_PREFIX , token),  openid, expire);
        //第二步、设置Token保存到客户端浏览器Cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        //第三步、根据用户openid获取用户信息
        try {
			WxMpUser mpUser = wxMpService.getUserService().userInfo(openid);
			UserInfo userInfo = userInfoService.findByOpenid(mpUser.getOpenId());
			if (userInfo == null) {
				userInfo = WxMpUser2UserInfoConverter.WxMpUser2UserInfoConvert(mpUser);
			} else {
				String id = userInfo.getId();
				BeanUtils.copyProperties(mpUser, userInfo);
				userInfo.setId(id);
			}
			userInfoService.save(userInfo);
		} catch (WxErrorException e) {
			log.error("【微信公众平台网页授权】获取用户信息出现WxErrorException错误", e);
		}catch (Exception e) {
			log.error("【微信公众平台网页授权】获取用户信息出现Exception错误", e);
		}
        return "redirect:" + returnUrl + "?openid=" + openid;
    }

    /**
     * <pre>
     * 微信开放平台扫码登录Authorize
     * @param returnUrl
     * @return
     * </pre>
     */
    @GetMapping(value = "/qrAuthorize")
    public String qrAuthorize(@RequestParam(name = "returnUrl", required = true) String returnUrl) {
        String url = projectUrlConfig.getWeChatMpAuthorizeUrl() + projectUrlConfig.getProjectName() + projectUrlConfig.getWeChatOpenAuthorizeUrlUserInfo();
        if (log.isDebugEnabled()) {
            log.debug("【微信开放平台网页授权】扫描登录获取code, url={}", url);
        }
        String redirectUrl = null;
        try {
            redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("【微信开放平台网页授权】出现UnsupportedEncodingException错误，e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.ENCODING_UNSUPPORT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("【微信公众平台网页授权】获取code出现Exception错误，e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.MP_OTHER_ERROR.getCode(), e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("【微信开放平台网页授权】扫描登录获取code ==> redirectUrl={}", redirectUrl);
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * <pre>
     * 微信开放平台扫码登录Authorize，获取用户信息
     * @param code
     * @param returnUrl
     * @return
     * </pre>
     */
    @GetMapping(value = "/qrUserInfo")
    public String qrUserInfo(@RequestParam(name = "code", required = true) String code,
                             @RequestParam(name = "state", required = true) String returnUrl) {
        WxMpOAuth2AccessToken wxOpenOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxOpenOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信开放平台网页授权登录获取code】出现错误， e={}", e);
            throw new BuyerAuthorizeException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("wxMpOAuth2AccessToken={}", JsonUtil.toJson(wxOpenOAuth2AccessToken));
        }
        String openid = wxOpenOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}