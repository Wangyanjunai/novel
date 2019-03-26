package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName UserInfoVO
 * @Desc 用户数据信息VO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/03 15:48
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = 3368474830259765157L;

	@JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "userAvatar")
    private String userAvatar;

    @JsonProperty(value = "userNickName")
    private String userNickName;

    @JsonProperty(value = "balance")
    private BigDecimal balance;
}
