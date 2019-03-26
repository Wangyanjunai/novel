package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName CategoryEnum
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
public enum CategoryEnum implements CodeEnum<Object> {

    DELETE(1, Boolean.TRUE, "是"),

    NOT_DELETE(0, Boolean.FALSE, "否"),

    ;

    private Integer code;

    private Boolean status;

    private String message;
}
