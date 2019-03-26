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
 * @ClassName XFYunProperties
 * @Desc 科大讯飞语音合成参数配置
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/24 19:15
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Component
@Data
@NoArgsConstructor
public class XFYunProperties {

    /**
     * <pre>
     * @Field appId
     * </pre>
     */
    @JsonProperty(value = "appId")
    @Value(value = "${xfYun.appId}")
    private String appId;

    /**
     * <pre>
     * @Field apiKey
     * </pre>
     */
    @JsonProperty(value = "apiKey")
    @Value(value = "${xfYun.apiKey}")
    private String apiKey;

    /**
     * <pre>
     * @Field host
     * </pre>
     */
    @JsonProperty(value = "host")
    @Value(value = "${xfYun.host}")
    private String host;

    /**
     * <pre>
     * @Field path
     * </pre>
     */
    @JsonProperty(value = "path")
    @Value(value = "${xfYun.path}")
    private String path;

    /**
     * <pre>
     * @Field method
     * </pre>
     */
    @JsonProperty(value = "method")
    @Value(value = "${xfYun.method}")
    private String method;

    /**
     * <pre>
     * @Field auf
     * </pre>
     */
    @JsonProperty(value = "auf")
    @Value(value = "${xfYun.auf}")
    private String auf;

    /**
     * <pre>
     * @Field aue
     * </pre>
     */
    @JsonProperty(value = "aue")
    @Value(value = "${xfYun.aue}")
    private String aue;

    /**
     * <pre>
     * @Field voiceName
     * </pre>
     */
    @JsonProperty(value = "voiceName")
    @Value(value = "${xfYun.voiceName}")
    private String voiceName;

    /**
     * <pre>
     * @Field speed
     * </pre>
     */
    @JsonProperty(value = "speed")
    @Value(value = "${xfYun.speed}")
    private String speed;

    /**
     * <pre>
     * @Field volume
     * </pre>
     */
    @JsonProperty(value = "volume")
    @Value(value = "${xfYun.volume}")
    private String volume;

    /**
     * <pre>
     * @Field pitch
     * </pre>
     */
    @JsonProperty(value = "pitch")
    @Value(value = "${xfYun.pitch}")
    private String pitch;

    /**
     * <pre>
     * @Field engineType
     * </pre>
     */
    @JsonProperty(value = "engineType")
    @Value(value = "${xfYun.engineType}")
    private String engineType;

    /**
     * <pre>
     * @Field textType
     * </pre>
     */
    @JsonProperty(value = "textType")
    @Value(value = "${xfYun.textType}")
    private String textType;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
