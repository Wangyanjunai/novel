package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName CategoryIsDeleteEnum
 * @Desc CategoryIsDeleteEnum
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/18 15:23
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum CategoryIsDeleteEnum implements CodeEnum<Object>{

    NOT_DELETE(0, Boolean.TRUE, "否"),

    DELETE(1, Boolean.TRUE, "是"),

    ;

    private Integer code;

    private Boolean status;

    private String message;
}
