package com.potato369.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dto
 * @ClassName SellerInfoDTO
 * @Desc 卖家后台管理员信息DTO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/24 10:03
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SellerInfoDTO {

    /**
     * @Field sellerId：卖家id
     */
    private String sellerId;

    /**
     * @Field sellerName：卖家名字
     */
    private String sellerName;

    /**
     * @Field password：登录密码
     */
    private String password;

    /**
     * @Field openid：微信openid
     */
    private String openid;

    /**
     * @Field loginTime：登录时间
     */
    private Date loginTime;

    /**
     * @Field loginIp：登录终端ip
     */
    private String loginIp;

    /**
     * @Field createTime：创建时间
     */
    private Date createTime;

    /**
     * @Field updateTime：更新时间
     */
    private Date updateTime;
}
