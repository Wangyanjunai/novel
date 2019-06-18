package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName CategoryIsDeleteEnum
 * @Desc 类目信息枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/22 09:44
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum CategoryTypeEnum implements CodeEnum<Object> {
    
    MALE(100, Boolean.TRUE, "male"),
    
    FEMALE(200, Boolean.TRUE, "female"),
    
    PICTURE(300, Boolean.TRUE, "picture"),
    
    XUANHUANQIHUAN(101, Boolean.TRUE, "xuanhuanqihuan"),
    
    XIUXIANXIUZHEN(102, Boolean.TRUE, "xiuxianxiuzhen"),
    
    XIANXIAWUXIA(103, Boolean.TRUE, "xianxiawuxia"),
    
    DUSHIQINGGAN(104, Boolean.TRUE, "dushiqinggan"),
    
    DUSHIYINENG(105, Boolean.TRUE, "dushiyineng"),
    
    LISHIJUNSHI(106, Boolean.TRUE, "lishijunshi"),
    
    KEHUANMOSHI(107, Boolean.TRUE, "kehuanmoshi"),
    
    YOUXIJINGJI(108, Boolean.TRUE, "youxijingji"),
    
    HUANXIANGYANQING(201, Boolean.TRUE, "huanxiangyanqing"),
    
    GUDAIYANQING(202, Boolean.TRUE, "gudaiyanqing"),
    
    DUSHISHENGHUO(203, Boolean.TRUE, "dushishenghuo"),
    
    QINGCHUNXIAOYUAN(204, Boolean.TRUE, "qingchunxiaoyuan"),
    
    ZONGCAIHAOMEN(205, Boolean.TRUE, "zongcaihaomen"),
    
    XUANYILINGYI(206, Boolean.TRUE, "xuanyilingyi"),
    
    CHUANGYUECHONGSHENG(207, Boolean.TRUE, "chuangyuechongsheng"),
    
    QITALEIBIE(208, Boolean.TRUE, "qitaleibie"),
    ;

    private Integer code;

    private Boolean status;

    private String message;
}
