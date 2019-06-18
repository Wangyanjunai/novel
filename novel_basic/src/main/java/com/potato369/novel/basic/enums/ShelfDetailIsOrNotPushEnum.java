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
public enum ShelfDetailIsOrNotPushEnum implements CodeEnum<Object>{

    IS_PUSH(1, "开启推送"),

    NOT_PUSH(0, "不开启推送"),

    ;

    private Integer code;

    private String message;
}
