package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName NovelAdvertisementEnum
 * @Desc 存储加载的广告信息数据实体默认值枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 15:48
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum NovelAdvertisementEnum implements CodeEnum<Object>{

    SKIP_AD(0, "跳转广告"),

    APP_AD(1, "应用内广告"),

    JUMP(0, "可以跳转"),

    NO_JUMP(1, "不可以跳转"),

    ;
    private Integer code;

    private String message;
}
