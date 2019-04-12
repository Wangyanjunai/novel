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
    
    MALE(200, Boolean.TRUE, "Male"),
    
    FEMALE(300, Boolean.TRUE, "Female"),
    
    FANTASY(201, Boolean.TRUE, "Fantasy"),
    
    COATARD(202, Boolean.TRUE, "Coatard"),
    
    WEBGAME(203, Boolean.TRUE, "Webgame"),
    
    MILITARY(204, Boolean.TRUE, "Military"),
    
    SCIENCE(205, Boolean.TRUE, "Science"),
    
    HISTORY(206, Boolean.TRUE, "History"),
    
    BIZARRE(207, Boolean.TRUE, "Bizarre"),
    
    WUXIA(208, Boolean.TRUE, "Wuxia"),
    
    XIANXIA(209, Boolean.TRUE, "Xianxia"),
    
    CAMPUS(210, Boolean.TRUE, "Campus"),
    
    MYSTERY(212, Boolean.TRUE, "Mystery"),
    
    CITY(301, Boolean.TRUE, "City"),
    
    THROUGH(302, Boolean.TRUE, "Through"),
    ;

    private Integer code;

    private Boolean status;

    private String message;
}
