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
 * 类名: WeChatPayProperties
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年3月13日 下午1:51:11
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
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
	 * @Field 1、appId：微信公众号或者小程序等的appId
	 * </pre>
	 */
	@JsonProperty(value = "appId")
	@Value(value = "${weChat.pay.appId}")
	private String appId;

	/**
	 * <pre>
	 * @Field 2、mchId：微信支付商户号
	 * </pre>
	 */
	@JsonProperty(value = "mchId")
	@Value(value = "${weChat.pay.mchId}")
	private String mchId;
	
	/**
	 * <pre>
	 * @Field 3、mchKey：微信支付商户密钥
	 * </pre>
	 */
	@JsonProperty(value = "mchKey")
	@Value(value = "${weChat.pay.mchKey}")
	private String mchKey;
	
	/**
	 * <pre>
	 * @Field 4、subAppId：服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
	 * </pre>
	 */
	@JsonProperty(value = "subAppId")
	@Value(value = "${weChat.pay.subAppId}")
	private String subAppId;
		
	/**
	 * <pre>
	 * @Field 5、subMchId：服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
	 * <pre>
	 */
	@JsonProperty(value = "subMchId")
	@Value(value = "${weChat.pay.subMchId}")
	private String subMchId;
		
	/**
	 * <pre>
	 * @Field 6、keyPath：apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
	 * </pre>
	 */
	@JsonProperty(value = "keyPath")
	@Value(value = "${weChat.pay.keyPath}")
	private String keyPath;
	  
	/**
	 * <pre>
	 * @Field 7、notifyUrl：微信支付异步通知地址URL
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
