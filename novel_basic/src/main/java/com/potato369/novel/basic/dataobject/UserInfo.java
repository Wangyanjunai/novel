package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName UserInfo
 * @Desc 用户信息记录
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 16:59
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "UserInfo")
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfo implements Serializable {

    /**
     * @serialField serialVersionUID : 序列号
     */
    private static final long serialVersionUID = -8639503100982373589L;

    /**
     * @serialField id：用户id
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * @serialField uid：标识
     */
    @Column(name = "uid", nullable = true, length = 32)
    private Integer uid;

    /**
     * @serialField privatedId：私密阅读id
     */
    @Column(name = "privated_id", nullable = true, length = 32)
    private String privatedId;

    /**
     * @serialField openid：微信openid
     */
    @Column(name = "openid", nullable = true, length = 64)
    private String openid;

    /**
     * @serialField nickName：微信昵称
     */
    @Column(name = "nick_name", nullable = true, length = 64)
    private String nickName;

    /**
     * @serialField gender：性别
     */
    @Column(name = "gender", nullable = false, length = 4)
    private Integer gender;

    /**
     * @serialField subscribe：是否关注公众号，0-未关注，1-已关注
     */
    @Column(name = "subscribe", nullable = false, length = 4)
    private Integer subscribe;

    /**
     * @serialField userName：用户名
     */
    @Column(name = "user_name", nullable = true, length = 128)
    private String userName;

    /**
     * @serialField signature：签名
     */
    @Column(name = "signature", nullable = true, length = 256)
    private String signature;

    /**
     * @serialField alt：个人主页URL
     */
    @Column(name = "alt", nullable = true, length = 256)
    private String alt;

    /**
     * @serialField lang：语言
     */
    @Column(name = "lang", nullable = true, length = 25)
    private String lang;

    /**
     * @serialField lang：语言
     */
    @Column(name = "city", nullable = true, length = 25)
    private String city;

    /**
     * @serialField province：省份
     */
    @Column(name = "province", nullable = true, length = 25)
    private String province;

    /**
     * @serialField country：国家
     */
    @Column(name = "country", nullable = true, length = 25)
    private String country;

    /**
     * @serialField avatarUrl：微信头像URL
     */
    @Column(name = "avatar_url", nullable = true, length = 255)
    private String avatarUrl;

    /**
     * @serialField ip：客户端IP
     */
    @Column(name = "ip", nullable = true, length = 25)
    private String ip;

    /**
     * @serialField balance：书币余额，1元人民币=100书币
     */
    private BigDecimal balance;

    /**
     * @serialField unionId：联合id
     */
    @Column(name = "union_id", nullable = true, length = 64)
    private String unionId;

    /**
     * @serialField watermarkTimestamp：水印时间戳
     */
    @Column(name = "watermark_timestamp", nullable = true, length = 64)
    private String watermarkTimestamp;

    /**
     * @serialField watermarkTimestamp：水印时间戳
     */
    @Column(name = "watermark_app_id", nullable = true, length = 64)
    private String watermarkAppId;

    /**
     * @serialField subscribeTime：关注时间
     */
    @Column(name = "subscribe_time", nullable = true, length = 64)
    private Date subscribeTime;

    /**
     * @serialField createTime：创建时间
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * @serialField updateTime：更新时间
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;

    /**
     * @serialField loginTime：登录时间
     */
    @Column(name = "login_time", nullable = false, length = 64)
    private Date loginTime;
}
