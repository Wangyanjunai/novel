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
public enum UserInfoGenderEnum implements CodeEnum<Object>{

    GENDER_UNKNOWN(0, "未知"),

    GENDER_MALE(1, "男"),

    GENDER_FEMALE(2, "女"),

    VISITOR(0, "游客身份"),

    WECHAT(1, "微信身份"),

    WEIBO(2, "微博身份"),

    QQ(3, "QQ身份"),

    FINISHED(1, "完成绑定微信任务"),

    UNFINISHED(0, "未完成绑定微信任务"),
    
    VIP0(0, "450b9b4f90b14fb784f45643ded0dff5"),
    
    VIP1(1, "450b9b4f90b14fb784f45643ded0dff6"),
    
    VIP2(2, "450b9b4f90b14fb784f45643ded0dff7"),

    ;

    private Integer code;

    private String message;
}
