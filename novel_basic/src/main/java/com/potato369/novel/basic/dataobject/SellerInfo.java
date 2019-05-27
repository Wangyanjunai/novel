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
import javax.persistence.Transient;

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
@Data
@DynamicUpdate
@Entity(name = "SellerInfo")
@NoArgsConstructor
@Table(name = "seller_info")
public class SellerInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID： 序列号。
     * </pre>
     */
	@Transient
    private static final long serialVersionUID = -8639503600988373589L;

    /**
     * <pre>
     * @serialField sellerId：卖家id。
     * </pre>
     */
    @Id
    @Column(name = "seller_id", nullable = false, length = 32)
    private String sellerId;

    /**
     * <pre>
     * @serialField sellerName：卖家名字。
     * </pre>
     */
    @Column(name = "seller_name", nullable = false, length = 64)
    private String sellerName;

    /**
     * <pre>
     * @serialField password：登录密码。
     * </pre>
     */
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    /**
     * <pre>
     * @serialField openid：微信openid。
     * </pre>
     */
    @Column(name = "openid", nullable = false, length = 64)
    private String openid;

    /**
     * <pre>
     * @serialField loginTime：登录时间。
     * </pre>
     */
    @Column(name = "login_time", nullable = true, length = 64)
    private Date loginTime;

    /**
     * <pre>
     * @serialField loginIp：登录终端ip。
     * </pre>
     */
    @Column(name = "login_ip", nullable = true, length = 32)
    private String loginIp;

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
}
