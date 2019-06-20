package com.potato369.novel.app.web.conf;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.potato369.novel.app.web.conf.prop.ProjectUrlProperties;
import com.potato369.novel.app.web.conf.prop.WeChatPayProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.conf.prop
 * @ClassName WeChatPayConfiguration
 * @Desc 微信支付Bean配置
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:21
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
public class WeChatPayConfiguration {

    @Autowired
    private WeChatPayProperties properties;

    @Autowired
    private ProjectUrlProperties urlProperties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
        payConfig.setNotifyUrl(StringUtils.trimToNull(this.urlProperties.getDomainUrl() + this.urlProperties.getProjectName() + this.properties.getNotifyUrl()));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
