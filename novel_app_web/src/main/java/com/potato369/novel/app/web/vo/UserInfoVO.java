package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName UserInfoVO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 15:49
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserInfoVO {

    @JsonProperty(value = "mid")
    private String mId;// 用户mid。

    @JsonProperty(value = "nickName")
    private String nickName;// 用户微信，QQ，微博昵称。

    @JsonProperty(value = "gender")
    private Integer gender;// 性别。

    @JsonProperty(value = "avatar")
    private String avatarUrl;// 头像地址URL。

    @JsonProperty(value = "envelope")
    private BigDecimal envelopeAmount;// 红包进度值

    @JsonProperty(value = "shelfCount")
    private Integer shelfAmount;// 书架小说总数量

    @JsonProperty(value = "gradeName")
    private String vipGradeName;// VIP等级名称

    @JsonProperty(value = "startTime")
    private Date vipStartTime;// VIP开始时间

    @JsonProperty(value = "endTime")
    private Date vipEndTime;// VIP结束时间

    @JsonProperty(value = "balance")
    private BigDecimal balanceAmount;//总余额

    @JsonProperty(value = "type")
    private Integer userType;//用户登录身份类型

    @JsonProperty(value = "isOrNotBandWechat")
    private Integer isOrNotBandWechat;// 是否完成绑定微信任务
}
