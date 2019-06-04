package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName TaskInfoEnum
 * @Desc 任务进度信息枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 17:07
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum TaskInfoEnum implements CodeEnum<Object> {

    BINDING(1, "绑定任务"),

    SHARE(2, "分享任务"),

    DOWNLOAD(3, "下载任务"),

    READING(4, "阅读任务"),

    ;
    private Integer code;

    private String message;
}
