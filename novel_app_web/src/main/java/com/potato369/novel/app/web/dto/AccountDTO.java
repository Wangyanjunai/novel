package com.potato369.novel.app.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.dto
 * @ClassName AccountDTO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 14:38
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AccountDTO {
    /**
     * <pre>
     * @JsonProperty accountName：账户名称。
     * </pre>
     */
    @JsonProperty(value = "name", defaultValue = "支付宝")
    private String accountName;

    /**
     * <pre>
     * @JsonProperty accountInfo：账号信息。
     * </pre>
     */
    @NotNull(message = "账号不能为空。")
    @JsonProperty(value = "info")
    private String accountInfo;

    /**
     * <pre>
     * @JsonProperty userId：用户mid。
     * </pre>
     */
    @NotNull(message = "用户mid不能为空。")
    @JsonProperty(value = "userId")
    private String userId;

    /**
     * <pre>
     * @JsonProperty accountUserName：姓名。
     * </pre>
     */
    @NotNull(message = "姓名不能为空。")
    @JsonProperty(value = "userName")
    private String accountUserName;

    /**
     * <pre>
     * @JsonProperty accountIdNumber：身份证号码。
     * </pre>
     */
    @NotNull(message = "身份证号码不能为空。")
    @JsonProperty(value = "idNumber")
    private String accountIdNumber;

    /**
     * <pre>
     * @JsonProperty accountPhoneNumber：手机号码。
     * </pre>
     */
    @NotNull(message = "手机号码不能为空")
    @JsonProperty(value = "phoneNumber")
    private String accountPhoneNumber;
}
