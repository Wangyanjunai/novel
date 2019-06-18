package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName UserInfoGenderEnum
 * @Desc 用户默认值设置枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 16:40
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum UserInfoIsOrNotBandWechatEnum implements CodeEnum<Object>{

    FINISHED(1, "完成绑定微信任务"),

    UNFINISHED(0, "未完成绑定微信任务"),

    ;

    private Integer code;

    private String message;
}
