package com.potato369.novel.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName WxPaySignature
 * @Desc WxPaySignature
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/14 11:34
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class WxPaySignature {
    /**
     * 签名
     * @param params
     * @param signKey
     * @return
     */
    public static String sign(Map<String, String> params, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(signKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 校验签名
     * @param params
     * @param signKey
     * @return
     */
    public static Boolean verify(Map<String, String> params, String signKey) {
        String sign = sign(params, signKey);
        return sign.equals(params.get("sign"));
    }
}
