package com.potato369.novel.app.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName AliPayQueryResult
 * @Desc 支付宝支付结果查询
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/20 10:13
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AliPayQueryResult extends Result{
	
	/**
     * <pre>
     * @JsonProperty openid：支付宝用户的平台账号id。
     * </pre>
     */
    @JsonProperty(value = "openid")
	private String openid;
}
