package com.potato369.novel.conf.prop;

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
 * @PackageName com.potato369.novel.conf.prop
 * @ClassName WeChatMpProperties
 * @Desc 微信公众号和微信公众号支付开发属性
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 10:54
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Component
@Data
@NoArgsConstructor
public class WeChatMpProperties {

    /**
     * <pre>
     * @Field 1、appId：微信公众平台appid
     * </pre>
     */
    @JsonProperty(value = "appId")
    @Value(value = "${weChat.mp.appId}")
    private String appId;

    /**
     * <pre>
     * @Field 2、mpAppSecret：微信公众平台密钥
     * </pre>
     */
    @JsonProperty(value = "appSecret")
    @Value(value = "${weChat.mp.appSecret}")
    private String appSecret;

    /**
     * <pre>
     * @Field 3、公众平台服务器验证token
     * </pre>
     */
    @JsonProperty(value = "token")
    @Value(value = "${weChat.mp.token}")
    private String token;

    /**
     * <pre>
     * @Field 4、公众平台消息加密密钥
     * </pre>
     */
    @JsonProperty(value = "aesKey")
    @Value(value = "${weChat.mp.aesKey}")
    private String aesKey;

    /**
     * <pre>
     * @Field orderStatus：5、订单状态改变微信模板id
     * </pre>
     */
    @JsonProperty(value = "templateId")
    @Value(value = "${weChat.mp.templateId.orderStatus}")
    private String orderStatus;

    /**
     * <pre>
     * @Field orderSuccess：6、订单状态改变微信模板id
     * </pre>
     */
    @JsonProperty(value = "templateId")
    @Value(value = "${weChat.mp.templateId.orderSuccess}")
    private String orderSuccess;

    /**
     * <pre>
     * @Field paySuccess：7、订单状态改变微信模板id
     * </pre>
     */
    @JsonProperty(value = "paySuccess")
    @Value(value = "${weChat.mp.templateId.paySuccess}")
    private String paySuccess;

    /**
     * <pre>
     * @Field 8、微信后台管理员登录成功微信模板消息id
     * </pre>
     */
    @JsonProperty(value = "sellerLoginSuccess")
    @Value(value = "${weChat.mp.templateId.sellerLoginSuccess}")
    private String sellerLoginSuccess;

    /**
     * <pre>
     * @Field 9、买家端订单取消通知微信模板消息id
     * </pre>
     */
    @JsonProperty(value = "orderCancel")
    @Value(value = "${weChat.mp.templateId.orderCancel}")
    private String orderCancel;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
