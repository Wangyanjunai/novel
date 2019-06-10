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
 * @ClassName UserInfoDTO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 13:46
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserInfoDTO {

    @NotNull(message = "手机串号不能为空")
    @JsonProperty(value = "meId")
    private String meId;// 手机串号。
    
    @NotNull(message = "用户登录身份类型不能为空")
    @JsonProperty(value = "type")
    private Integer userType;// userType：用户登录身份类型，0-游客身份；1-微信身份；2-微博身份；3-QQ身份，默认：“0-游客身份“。

    @JsonProperty(value = "openid")
    private String openid;// 微信openid，或者微博openid，或者QQ账号openid。

    @JsonProperty(value = "brand")
    private String brand;// 手机品牌。

    @JsonProperty(value = "model")
    private String model;// 手机型号。

    @JsonProperty(value = "mac")
    private String mac;// 手机mac地址。

    @JsonProperty(value = "systemName")
    private String systemName;// 手机系统类型。

    @JsonProperty(value = "systemCode")
    private String systemCode;// 手机系统版本。

    @JsonProperty(value = "versionName")
    private String versionName;// APP应用版本名称。

    @JsonProperty(value = "nickName")
    private String nickName;// 用户微信，QQ，微博昵称。

    @JsonProperty(value = "gender")
    private Integer gender;// 性别，2-女；1-男；0-未知，默认：“0-未知“。

    @JsonProperty(value = "avatar")
    private String avatarUrl;// 头像地址URL。

    @JsonProperty(value = "ip")
    private String ip;// APP客户端登录的外网IP。
    
    @JsonProperty(value = "address")
    private String address;// 用户登录或者设置的地址（国家省份城市）
}
