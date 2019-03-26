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
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName SellerInfo
 * @Desc 卖家信息实体类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 10:19
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "SellerInfo")
@NoArgsConstructor
@Table(name = "seller_info")
public class SellerInfo implements Serializable {

    /**
     * @serialField serialVersionUID: 序列号
     */
    private static final long serialVersionUID = -8639503600988373589L;

    /**
     * @serialField sellerId：卖家id
     */
    @Id
    @Column(name = "seller_id", nullable = false, length = 32)
    private String sellerId;

    /**
     * @serialField sellerName：卖家名字
     */
    @Column(name = "seller_name", nullable = false, length = 64)
    private String sellerName;

    /**
     * @serialField password：登录密码
     */
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    /**
     * @serialField openid：微信openid
     */
    @Column(name = "openid", nullable = false, length = 64)
    private String openid;

    /**
     * @serialField loginTime：登录时间
     */
    @Column(name = "login_time", nullable = true, length = 64)
    private Date loginTime;

    /**
     * @serialField loginIp：登录终端ip
     */
    @Column(name = "login_ip", nullable = true, length = 32)
    private String loginIp;

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
}
