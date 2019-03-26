package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName ProductInfoEnum
 * @Desc 产品信息枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/11 10:40
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ProductInfoEnum implements CodeEnum<Object> {

    COIN(0, "书币"),

    VIP(1, "会员"),

    ;

    private Integer code;

    private String message;
}
