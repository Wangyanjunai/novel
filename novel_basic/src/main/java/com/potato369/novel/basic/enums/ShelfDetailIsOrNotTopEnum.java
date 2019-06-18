package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName ShelfDetailIsOrNotTopEnum
 * @Desc 书架详情默认值枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 16:18
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ShelfDetailIsOrNotTopEnum implements CodeEnum<Object>{

    IS_TOP(1, "开启置顶"),

    NOT_TOP(0, "不开启置顶"),

    ;

    private Integer code;

    private String message;
}
