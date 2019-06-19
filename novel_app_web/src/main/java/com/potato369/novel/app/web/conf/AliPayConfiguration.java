package com.potato369.novel.app.web.conf;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.potato369.novel.app.web.conf.prop.AliPayProperties;
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
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 16:21
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Configuration
@ConditionalOnClass(AlipayClient.class)
public class AliPayConfiguration {

    @Autowired
    private AliPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(
        		StringUtils.trimToNull(this.properties.getServerUrl()),
        		StringUtils.trimToNull(this.properties.getAppId()),
        		StringUtils.trimToNull(this.properties.getAppPrivateKey()),
        		StringUtils.trimToNull(this.properties.getFormat()),
        		StringUtils.trimToNull(this.properties.getCharSet()), 
        		StringUtils.trimToNull(this.properties.getAppPublicKey()),
        		StringUtils.trimToNull(this.properties.getSignType()));
        return alipayClient;
    }
}
