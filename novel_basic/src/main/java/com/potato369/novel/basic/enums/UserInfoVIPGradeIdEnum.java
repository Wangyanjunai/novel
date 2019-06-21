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
public enum UserInfoVIPGradeIdEnum implements CodeEnum<Object>{
    
    VIP0(0, "450b9b4f90b14fb784f45643ded0dff5"),
    
    VIP1(1, "450b9b4f90b14fb784f45643ded0dff6"),
    
    VIP2(2, "450b9b4f90b14fb784f45643ded0dff7"),
    
    VIP0_NAME(0, "VIP0"),
    
    VIP1_NAME(1, "VIP1"),
    
    VIP2_NAME(2, "VIP2"),

    ;

    private Integer code;

    private String message;
}
