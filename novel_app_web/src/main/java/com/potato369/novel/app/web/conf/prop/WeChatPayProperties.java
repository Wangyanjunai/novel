package com.potato369.novel.app.web.conf.prop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.conf.prop
 * @ClassName WeChatPayProperties
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:16
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Component
@Data
@NoArgsConstructor
public class WeChatPayProperties {

    /**
     * <pre>
     * @Field 1、appId：微信开放平台审核通过的应用APPID（请登录open.weixin.qq.com查看，注意与公众号的APPID不同）
     * </pre>
     */
    @JsonProperty(value = "appId")
    @Value(value = "${weChat.pay.appId}")
    private String appId;

    /**
     * <pre>
     * @Field 2、appSecret：微信开放平台审核通过的应用appSecret（请登录open.weixin.qq.com查看，注意与公众号的appSecret不同）
     * </pre>
     */
    @JsonProperty(value = "appId")
    @Value(value = "${weChat.pay.appSecret}")
    private String appSecret;

    /**
     * <pre>
     * @Field 3、mchId：微信支付分配的商户号
     * </pre>
     */
    @JsonProperty(value = "mchId")
    @Value(value = "${weChat.pay.mchId}")
    private String mchId;

    /**
     * <pre>
     * @Field 4、mchKey：微信支付商户密钥
     * </pre>
     */
    @JsonProperty(value = "mchKey")
    @Value(value = "${weChat.pay.mchKey}")
    private String mchKey;

    /**
     * <pre>
     * @Field 5、keyPath：apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     * </pre>
     */
    @JsonProperty(value = "keyPath")
    @Value(value = "${weChat.pay.keyPath}")
    private String keyPath;

    /**
     * <pre>
     * @Field 6、notifyUrl：微信支付异步通知地址URL
     * </pre>
     */
    @JsonProperty(value = "notifyUrl")
    @Value(value = "${weChat.pay.notifyUrl}")
    private String notifyUrl;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
