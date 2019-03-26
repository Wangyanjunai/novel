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
 * @ClassName WeChatOpenProperties
 * @Desc WeChatOpenProperties
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/13 14:28
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Component
@Data
@NoArgsConstructor
public class WeChatOpenProperties {
    /**
     * <pre>
     * @Field 1、mpAppId：微信开放平台appid
     * </pre>
     */
    @JsonProperty(value = "appId")
    @Value(value = "${weChat.open.appId}")
    private String appId;

    /**
     * <pre>
     * @Field 2、mpAppSecret：微信开放平台密钥
     * </pre>
     */
    @JsonProperty(value = "appSecret")
    @Value(value = "${weChat.open.appSecret}")
    private String appSecret;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
