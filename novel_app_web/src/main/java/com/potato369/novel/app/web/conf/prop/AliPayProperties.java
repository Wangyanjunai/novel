package com.potato369.novel.app.web.conf.prop;

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
 * @ClassName AliPayProperties
 * @Desc 支付宝公共参数配置
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
public class AliPayProperties {

	/**
     * <pre>
     * @Field 1、serverUrl：请求接口地址。
     * </pre>
     */
    @Value(value = "${alipay.pay.serverPayUrl}")
    private String serverPayUrl;
	
    /**
     * <pre>
     * @Field 2、appId：支付宝分配给开发者的应用ID。
     * </pre>
     */
    @Value(value = "${alipay.pay.appId}")
    private String appId;
    
    /**
     * <pre>
     * @Field 3、appPrivateKey：应用私钥。
     * </pre>
     */
    @Value(value = "${alipay.pay.appPrivateKey}")
    private String appPrivateKey;
    
    /**
     * <pre>
     * @Field 4、format：请求数据格式，仅支持JSON。
     * </pre>
     */
    @Value(value = "${alipay.pay.format}")
    private String format;
    
    /**
     * <pre>
     * @Field 5、charSet：请求使用的编码格式，如utf-8,gbk,gb2312等。
     * </pre>
     */
    @Value(value = "${alipay.pay.charSet}")
    private String charSet;
    
    /**
     * <pre>
     * @Field 6、appPublicKey：应用公钥。
     * </pre>
     */
    @Value(value = "${alipay.pay.appPublicKey}")
    private String appPublicKey;
    
    /**
     * <pre>
     * @Field 7、signType：商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2。
     * </pre>
     */
    @Value(value = "${alipay.pay.signType}")
    private String signType;
    
    /**
     * <pre>
     * @Field 8、timeoutExpress：支付宝支付超时时间。
     * </pre>
     */
    @Value(value = "${alipay.pay.timeoutExpress}")
    private String timeoutExpress;
    
    /**
     * <pre>
     * @Field 9、notifyUrl：支付宝支付异步通知地址URL
     * </pre>
     */
    @Value(value = "${alipay.pay.notifyUrl}")
    private String notifyUrl;
    
    
    /**
     * <pre>
     * @Field 10、alipayPublicKey：支付宝公钥。
     * </pre>
     */
    @Value(value = "${alipay.pay.alipayPublicKey}")
    private String alipayPublicKey;
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
