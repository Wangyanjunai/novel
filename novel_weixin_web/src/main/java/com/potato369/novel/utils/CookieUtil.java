package com.potato369.novel.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.potato369.novel.constant.CookieConstant;
import com.potato369.novel.dto.CartDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName CookieUtil
 * @Desc Cookie工具类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:41
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class CookieUtil {

    /**
     * 设置保存Cookie到客户端浏览器
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response, String name, String value, Integer maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    /**
     * 设置CartDTO购物车对象信息保存Cookie到客户端浏览器
     * @param response
     * @param cartDTO
     * @param maxAge
     */
    public static void set(HttpServletResponse response, CartDTO cartDTO, Integer maxAge){
        Cookie cookie1 = new Cookie(CookieConstant.CART_COOKIE + "productId", cartDTO.getProductId());
        Cookie cookie2 = new Cookie(CookieConstant.CART_COOKIE + "quantity", String.valueOf(cartDTO.getProductQuantity()));
        cookie1.setPath("/");
        cookie1.setMaxAge(maxAge);
        response.addCookie(cookie1);
        cookie2.setPath("/");
        cookie2.setMaxAge(maxAge);
        response.addCookie(cookie2);
    }

    /**
     * 从客户端浏览器获取Cookie
     * @param request
     * @param name
     */
    public static Cookie get(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)){
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * 从客户端浏览器遍历cookie，将cookie封装为map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length >0){
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
