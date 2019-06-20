package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelUserAccount
 * @Desc 用户账户信息记录实体。
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 11:02
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelUserAccount")
@NoArgsConstructor
@Table(name = "novel_user_account")
public class NovelUserAccount implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -8639503100981273589L;

    /**
     * <pre>
     * @serialField accountId：账户id，主键。
     * </pre>
     */
    @Id
    @Column(name = "account_id", nullable = false, length = 32)
    private String accountId;

    /**
     * <pre>
     * @serialField accountName：账户名称。
     * </pre>
     */
    @Column(name = "account_name", length = 10)
    private String accountName;

    /**
     * <pre>
     * @serialField accountInfo：账户信息。
     * </pre>
     */
    @Column(name = "account_info", length = 64)
    private String accountInfo;

    /**
     * <pre>
     * @serialField userId：用户mid。
     * </pre>
     */
    @Column(name = "userId", length = 20)
    private String userId;

    /**
     * <pre>
     * @serialField accountUserName：姓名。
     * </pre>
     */
    @Column(name = "account_user_name", length = 64)
    private String accountUserName;

    /**
     * <pre>
     * @serialField accountIdNumber：身份证号码。
     * </pre>
     */
    @Column(name = "account_id_number", length = 64)
    private String accountIdNumber;

    /**
     * <pre>
     * @serialField accountPhoneNumber：手机号码。
     * </pre>
     */
    @Column(name = "account_phone_number", length = 64)
    private String accountPhoneNumber;

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
