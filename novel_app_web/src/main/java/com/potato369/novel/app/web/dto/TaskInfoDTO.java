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
public class TaskInfoDTO {

    @NotNull(message = "用户mid不能为空")
    @JsonProperty(value = "mId")
    private String mId;// 用户mid。

    @JsonProperty(value = "openid")
    private String openid;// 微信openid，或者微博openid，或者QQ账号openid。

//    @JsonProperty(value = "nickName")
//    private String nickName;// 用户微信，QQ，微博昵称。
//
//    @JsonProperty(value = "gender")
//    private Integer gender;// 性别，2-女；1-男；0-未知，默认：“0-未知“。
//
//    @JsonProperty(value = "avatar")
//    private String avatarUrl;// 头像地址URL。
//
//    @JsonProperty(value = "ip")
//    private String ip;// APP客户端登录的外网IP，或者代理路由器外网IP。
//    
//    @JsonProperty(value = "address")
//    private String address;// 用户登录或者设置的地址（国家省份城市）

    @NotNull(message = "完成的任务id不能为空")
    @JsonProperty(value = "taskId")
    private String taskId;//任务id

//    @NotNull(message = "完成的任务任务时间不能为空")
//    @JsonProperty(value = "finishedDate")
//    private String finishedDateString;//任务完成时间

//    @NotNull(message = "第几次完成任务不能为空")
//    @JsonProperty(value = "times")
//    private Integer times;//第几次完成任务
}
