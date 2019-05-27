package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.dataobject.idClass.NovelUserInfoIdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
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
@Data
@DynamicUpdate
@Entity(name = "NovelUserInfo")
@NoArgsConstructor
@Table(name = "novel_user_info")
@IdClass(value = NovelUserInfoIdClass.class)
public class NovelUserInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    private static final long serialVersionUID = -8639503100982373589L;

    /**
     * <pre>
     * @serialField id：用户id，主键id。
     * </pre>
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * @serialField openid：微信openid，或者登录微博账号id，QQ账号id。
     * </pre>
     */
    @Id
    @Column(name = "openid", nullable = true, length = 64)
    private String openid;

    /**
     * <pre>
     * @serialField nickName：用户微信，QQ，微博昵称。
     * </pre>
     */
    @Column(name = "nick_name", nullable = true, length = 64)
    private String nickName;

    /**
     * <pre>
     * @serialField gender：性别，2-女；1-男；0-未知，默认：“0-未知“。
     * </pre>
     */
    @Column(name = "gender", nullable = false, length = 4)
    private Integer gender;

    /**
     * <pre>
     * @serialField userName：用户名。
     * </pre>
     */
    @Column(name = "user_name", nullable = false, length = 128)
    private String userName;

    /**
     * <pre>
     * @serialField signature：签名内容。
     * </pre>
     */
    @Column(name = "signature", nullable = true, length = 256)
    private String signature;

    /**
     * <pre>
     * @serialField alt：个人主页URL。
     * </pre>
     */
    @Column(name = "alt", nullable = true, length = 256)
    private String alt;

    /**
     * <pre>
     * @serialField lang：语言。
     * </pre>
     */
    @Column(name = "lang", nullable = true, length = 25)
    private String lang;

    /**
     * <pre>
     * @serialField city：城市。
     * </pre>
     */
    @Column(name = "city", nullable = true, length = 25)
    private String city;

    /**
     * <pre>
     * @serialField province：省份。
     * </pre>
     */
    @Column(name = "province", nullable = true, length = 25)
    private String province;

    /**
     * <pre>
     * @serialField country：国家。
     * </pre>
     */
    @Column(name = "country", nullable = true, length = 25)
    private String country;

    /**
     * <pre>
     * @serialField avatarUrl：头像URL。
     * </pre>
     */
    @Column(name = "avatar_url", nullable = true, length = 1024)
    private String avatarUrl;

    /**
     * <pre>
     * @serialField balance：土豆币余额，1元人民币=100书币。
     * </pre>
     */
    @Column(name = "balance", nullable = true, length = 10)
    private BigDecimal balance;

    /**
     * <pre>
     * @serialField chargeAmount：总充值金额（元）。
     * </pre>
     */
    @Column(name = "charge_amount", nullable = true, length = 10)
    private BigDecimal chargeAmount;

    /**
     * <pre>
     * @serialField shelfAmount：书架小说总数量。
     * </pre>
     */
    @Column(name = "shelf_amount", nullable = true, length = 11)
    private Integer shelfAmount;

    /**
     * <pre>
     * @serialField ip：客户端ip。
     * </pre>
     */
    @Column(name = "ip", nullable = true, length = 25)
    private String ip;

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;

    /**
     * <pre>
     * @serialField loginTime：登录时间。
     * </pre>
     */
    @Column(name = "login_time", nullable = false, length = 64)
    private Date loginTime;
}
