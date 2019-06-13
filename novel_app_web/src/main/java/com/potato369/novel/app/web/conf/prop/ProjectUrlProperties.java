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
 * @PackageName com.potato369.novel.conf.prop
 * @ClassName ProjectUrlProperties.java
 * @Desc 项目路径相关配置
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 10:00
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Component
@Data
@NoArgsConstructor
public class ProjectUrlProperties {

    /**
     * <pre>
     * @Field projectName：项目名字
     * </pre>
     */
    @JsonProperty(value = "projectName")
    @Value(value = "${projectUrl.projectName}")
    private String projectName;

    /**
     * <pre>
     * @Field domainUrl：项目部署解析的域名地址URL
     * </pre>
     */
    @JsonProperty(value = "domainUrl")
    @Value(value = "${projectUrl.domainUrl}")
    private String domainUrl;

    /**
     * <pre>
     * @Field weChatMpAuthorizeUrl：微信公众平台授权地址URL
     * </pre>
     */
    @JsonProperty(value = "weChatMpAuthorizeUrl")
    @Value(value = "${projectUrl.weChatMpAuthorizeUrl}")
    private String weChatMpAuthorizeUrl;

    /**
     * <pre>
     * @Field weChatMpAuthorizeUrlUserInfo：微信公众平台授权获取用户信息地址URL
     * </pre>
     */
    @JsonProperty(value = "weChatMpAuthorizeUrlUserInfo")
    @Value(value = "${projectUrl.weChatMpAuthorizeUrlUserInfo}")
    private String weChatMpAuthorizeUrlUserInfo;

    /**
     * <pre>
     * @Field weChatOpenAuthorizeUrl：微信开放平台授权登录地址URL
     * </pre>
     */
    @JsonProperty(value = "weChatOpenAuthorizeUrl")
    @Value(value = "${projectUrl.weChatOpenAuthorizeUrl}")
    private String weChatOpenAuthorizeUrl;

    /**
     * <pre>
     * @Field weChatOpenAuthorizeUrlUserInfo：微信开放平台授权登录获取用户信息地址URL
     * </pre>
     */
    @JsonProperty(value = "weChatOpenAuthorizeUrlUserInfo")
    @Value(value = "${projectUrl.weChatOpenAuthorizeUrlUserInfo}")
    private String weChatOpenAuthorizeUrlUserInfo;

    /**
     * <pre>
     * @Field loginUrl：跳转登录地址URL
     * </pre>
     */
    @JsonProperty(value = "loginUrl")
    @Value(value = "${projectUrl.loginUrl}")
    private String loginUrl;

    /**
     * <pre>
     * @Field logoutUrl：跳转登出地址URL
     * </pre>
     */
    @JsonProperty(value = "logoutUrl")
    @Value(value = "${projectUrl.logoutUrl}")
    private String logoutUrl;

    /**
     * <pre>
     * @Field errorUrl：错误页面地址URL
     * </pre>
     */
    @JsonProperty(value = "errorUrl")
    @Value(value = "${projectUrl.errorUrl}")
    private String errorUrl;

    /**
     * <pre>
     * @Field successUrl：成功页面地址URL
     * </pre>
     */
    @JsonProperty(value = "successUrl")
    @Value(value = "${projectUrl.successUrl}")
    private String successUrl;
    /**
     * <pre>
     * @Field mp3FilePath：科大讯飞语音合成文件上传Nginx服务器路径
     * </pre>
     */
    @JsonProperty(value = "mp3FilePath")
    @Value(value = "${projectUrl.mp3FilePath}")
    private  String mp3FilePath;

    /**
     * <pre>
     * @Field resUrl：科大讯飞语音合成文件Nginx服务器下载URL
     * </pre>
     */
    @JsonProperty(value = "resUrl")
    @Value(value = "${projectUrl.resUrl}")
    private  String resUrl;

    @JsonProperty(value = "uploadFilePath")
    @Value(value = "${projectUrl.uploadFilePath}")
    private String uploadFilePath;
    
    @JsonProperty(value = "coverImgFilePath")
    @Value(value = "${projectUrl.coverImgFilePath}")
    private String coverImgFilePath;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
