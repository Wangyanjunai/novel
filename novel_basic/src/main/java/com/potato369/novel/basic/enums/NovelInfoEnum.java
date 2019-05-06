/**
 * <pre>
 * Project Name: novel
 * File Name: NovelInfoEnum.java
 * Package Name: com.potato369.novel.enums
 * Create Date: 2018年12月18日 下午2:49:37
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.basic.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @ClassName NovelInfoEnum
 * @Desc 描述此类实现的功能作用
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月18日 下午2:49:37
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum NovelInfoEnum implements CodeEnum<Object> {

    NOT_SELECTED(0, Boolean.FALSE, "否"),

    SELECTED(1, Boolean.TRUE, "是"),

    NOT_PRIVATED(0, Boolean.FALSE, "否"),

    PRIVATED(1, Boolean.TRUE, "是"),

    NOT_CACHE(0, Boolean.FALSE, "否"),

    CACHE(1, Boolean.TRUE, "是"),

    NOT_DELETE(0, Boolean.FALSE, "否"),

    DELETE(1, Boolean.TRUE, "是"),

    LANG_CN(86, Boolean.TRUE, "zh-cn"),

    LANG_EN(44, Boolean.TRUE, "en"),

    NO_READ(0, Boolean.FALSE, "否"),

    HAVE_READ(1, Boolean.TRUE, "是"),

    NOVEL_STATUS_UPDATING(1, Boolean.FALSE, "连载中"),

    NOVEL_STATUS_FINISHED(0, Boolean.TRUE, "已完成"),

    ;

    private Integer code;

    private Boolean status;

    private String message;
}

