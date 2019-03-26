package com.potato369.novel.utils;

import com.potato369.novel.basic.enums.CodeEnum;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName EnumUtil
 * @Desc 枚举转换工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:05
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class EnumUtil {

    public static <T extends CodeEnum<?>> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
