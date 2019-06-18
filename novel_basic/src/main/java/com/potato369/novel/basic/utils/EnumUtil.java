package com.potato369.novel.basic.utils;

import com.potato369.novel.basic.enums.CodeEnum;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.utils
 * @ClassName EnumUtil
 * @Desc 枚举类处理工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/18 13:49
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class EnumUtil {

    public static <T extends CodeEnum<?>> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
