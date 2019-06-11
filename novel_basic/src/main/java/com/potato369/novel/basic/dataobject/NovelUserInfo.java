package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.UserInfoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName UserInfo
 * @Desc 用户信息记录实体
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
public class NovelUserInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -8639503100982373589L;

    /**
     * <pre>
     * @serialField mid：用户mid，主键。
     * </pre>
     */
    @Id
    @Column(name = "m_id", nullable = false, length = 20)
    private String mId;
    
    /**
     * <pre>
     * @serialField meId：手机串号。
     * </pre>
     */
    @Column(name = "me_id", length = 20)
    private String meId;

    /**
     * <pre>
     * @serialField brand：手机品牌。
     * </pre>
     */
    @Column(name = "brand", length = 64)
    private String brand;

    /**
     * <pre>
     * @serialField model：手机型号。
     * </pre>
     */
    @Column(name = "model", length = 64)
    private String model;

    /**
     * <pre>
     * @serialField mac：手机mac地址。
     * </pre>
     */
    @Column(name = "mac", length = 64)
    private String mac;

    /**
     * <pre>
     * @serialField systemName：手机系统类型。
     * </pre>
     */
    @Column(name = "system_name", length = 64)
    private String systemName;

    /**
     * <pre>
     * @serialField systemCode：手机系统版本。
     * </pre>
     */
    @Column(name = "system_code", length = 64)
    private String systemCode;

    /**
     * <pre>
     * @serialField versionName：APP应用版本名称。
     * </pre>
     */
    @Column(name = "version_name", length = 64)
    private String versionName;

    /**
     * <pre>
     * @serialField openid：微信openid，或者微博openid，或者QQ账号openid。
     * </pre>
     */
    @Column(name = "openid", length = 64)
    private String openid;

    /**
     * <pre>
     * @serialField nickName：用户微信，QQ，微博昵称。
     * </pre>
     */
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * <pre>
     * @serialField gender：性别，2-女；1-男；0-未知，默认：“0-未知“。
     * </pre>
     */
    @Builder.Default
    @Column(name = "gender", length = 1)
    private Integer gender = UserInfoEnum.GENDER_UNKNOWN.getCode();

    /**
     * <pre>
     * @serialField lang：语言。
     * </pre>
     */
    @Column(name = "lang", length = 25)
    private String lang;

    /**
     * <pre>
     * @serialField address：定位地址（国家省份城市）。
     * </pre>
     */
    @Column(name = "address", length = 256)
    private String address;

    /**
     * <pre>
     * @serialField avatarUrl：头像地址URL。
     * </pre>
     */
    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;

    /**
     * <pre>
     * @serialField chargeAmount：总充值金额（元）。
     * </pre>
     */
    @Builder.Default
    @Column(name = "charge_amount", length = 10)
    private BigDecimal chargeAmount = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField envelopeAmount：红包进度条。
     * </pre>
     */
    @Builder.Default
    @Column(name = "envelope_amount", length = 10)
    private BigDecimal envelopeAmount = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField shelfAmount：书架小说总数量。
     * </pre>
     */
    @Builder.Default
    @Column(name = "shelf_amount", length = 11)
    private Integer shelfAmount = Integer.valueOf(0);

    /**
     * <pre>
     * @serialField vipGradeId：VIP等级id。关联VIP权限等级信息表的权限等级id主键。
     * </pre>
     */
    @Builder.Default
    @Column(name = "vip_grade_id", length = 32)
    private String vipGradeId = UserInfoEnum.VIP1.getMessage();

    /**
     * <pre>
     * @serialField vipStartTime：VIP开始时间。
     * </pre>
     */
    @Column(name = "vip_start_time", length = 64)
    private Date vipStartTime;

    /**
     * <pre>
     * @serialField vipEndTime：VIP结束时间。
     * </pre>
     */
    @Column(name = "vip_end_time", length = 64)
    private Date vipEndTime;

    /**
     * <pre>
     * @serialField balanceAmount：总余额。
     * </pre>
     */
    @Builder.Default
    @Column(name = "balance_amount", length = 10)
    private BigDecimal balanceAmount = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField userType：用户登录身份类型，0-游客身份；1-微信身份；2-微博身份；3-QQ身份，默认：“0-游客身份“。
     * </pre>
     */
    @Builder.Default
    @Column(name = "user_type", length = 1)
    private Integer userType = UserInfoEnum.VISITOR.getCode();

    /**
     * <pre>
     * @serialField isOrNotBandWeChat：是否完成绑定微信号任务，0-未完成；1-已完成，默认：“1-未完成“。
     * </pre>
     */
    @Builder.Default
    @Column(name = "is_or_not_band_wechat", length = 1)
    private Integer isOrNotBandWechat = UserInfoEnum.UNFINISHED.getCode();

    /**
     * <pre>
     * @serialField ip：APP客户端登录的外网IP。
     * </pre>
     */
    @Column(name = "ip", length = 25)
    private String ip;

    /**
     * <pre>
     * @serialField createTime：注册时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField loginTime：登录时间。
     * </pre>
     */
    @Column(name = "login_time", nullable = false, length = 64)
    private Date loginTime;
}
